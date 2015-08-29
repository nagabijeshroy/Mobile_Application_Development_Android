/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */

package com.example.homework5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

public class PreviewActivity extends Activity {

	ImageView imageDiaplayed;
	TextView textDisplayed;
	Application application = null;
	ImageView ratingImage;
	ImageView shareImage;
	boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		application = (Application) getIntent().getExtras().getSerializable(ToDoActivity.APP_DATA);
		textDisplayed = (TextView) findViewById(R.id.textViewappTitle);
		imageDiaplayed = (ImageView) findViewById(R.id.imageViewImageUrl);
		textDisplayed.setText(application.getAppTitle());
		ratingImage = (ImageView) findViewById(R.id.imageViewRating);
		shareImage = (ImageView) findViewById(R.id.imageViewSocialShare);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Application");
		ParseUser currentUser = ParseUser.getCurrentUser();
		query.whereEqualTo(ToDoActivity.username, currentUser.getUsername());
		query.whereEqualTo(ToDoActivity.isFavorite, true);
		query.whereEqualTo(ToDoActivity.id, application.getId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				for (ParseObject listItem : objects) {
					if (listItem.getInt(ToDoActivity.id) == application.getId()) {
						ratingImage.setImageResource(R.drawable.rating_important);
						break;
					}
				}
			}
		});
		ratingImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Application");
				final ParseUser currentUser = ParseUser.getCurrentUser();
				query.whereEqualTo(ToDoActivity.username, currentUser.getUsername());
				query.whereEqualTo(ToDoActivity.id, application.getId());
				flag = true;
				query.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if (e == null) {
							ParseObject applicationObject = new ParseObject("Application");
							for (ParseObject listItem : objects) {
								if (listItem.getInt(ToDoActivity.id) == application.getId()&&listItem.getBoolean(ToDoActivity.isFavorite)==true) {
									//listItem.put(ToDoActivity.isFavorite, false);
									ratingImage.setImageResource(R.drawable.rating_not_important);
									//listItem.saveInBackground();
									applicationObject = listItem;
									listItem.deleteInBackground();
									flag = false;
									break;
								}
							}
							/*for(ParseObject listItem : objects){
								if (listItem.getInt(ToDoActivity.id) == application.getId()) {
									applicationObject = listItem;
									break;
								}
							}*/
							if (flag) {
								application.setFavorite(true);
								if (currentUser.getUsername() != null) {
									applicationObject.put("username", currentUser.getUsername());
								} else {
									applicationObject.put("username", "");
								}
								if (application.getAppTitle() != null) {
									applicationObject.put("appTitle", application.getAppTitle());
								} else {
									applicationObject.put("appTitle", "");
								}
								if (application.getDeveloperName() != null) {
									applicationObject.put("developerName", application.getDeveloperName());
								} else {
									applicationObject.put("developerName", "");
								}
								if (application.getUrl() != null) {
									applicationObject.put("url", application.getUrl());
								} else {
									applicationObject.put("url", "");
								}
								if (application.getLargePhotoUrl() != null) {
									applicationObject.put("largePhotoUrl", application.getLargePhotoUrl());
								} else {
									applicationObject.put("largePhotoUrl", "");
								}
								try {
									applicationObject.put("price", application.getPrice());
									applicationObject.put("id", application.getId());
									applicationObject.put("isFavorite", application.isFavorite());
									applicationObject.put("isShared", application.isShared());
								} catch (Exception ex) {

								}
								if (application.getSharedWith() != null) {
									applicationObject.put("sharedWith", application.getSharedWith());
								} else {
									applicationObject.put("sharedWith", "");
								}
								applicationObject.saveInBackground();
								ratingImage.setImageResource(R.drawable.rating_important);
							} else {

							}

						}
					}
				});
			}
		});
		if (application.getLargePhotoUrl() != null && application.getLargePhotoUrl().length() != 0) {
			Picasso.with(PreviewActivity.this).load(application.getLargePhotoUrl()).into(imageDiaplayed);
		}
		imageDiaplayed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (application.getUrl() != null) {
					String uri = application.getUrl();
					Intent intent = new Intent(PreviewActivity.this, WebViewActivity.class);
					intent.putExtra("url",uri);
					startActivity(intent);
				} else {
					Toast.makeText(PreviewActivity.this, getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
				}
			}
		});
		shareImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final ArrayList<ParseUser> usersList = new ArrayList<ParseUser>();
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.findInBackground(new FindCallback<ParseUser>() {
					
					@Override
					public void done(List<ParseUser> objects, ParseException e) {
						if(e==null){
							for(ParseUser listItem : objects){
								usersList.add(listItem);
							}
							final String[] lastNames = new String[usersList.size()];
							Collections.sort(usersList,new CustomComparator());
							int i=0;
							for (ParseUser parseObj : usersList) {
								lastNames[i] = parseObj.getString("firstName")+parseObj.getString("lastName");
								i++;
							}
							Log.d("Total Users count", usersList.size()+"");
							AlertDialog.Builder builder = new AlertDialog.Builder(PreviewActivity.this);
						    builder.setTitle(R.string.users)
						           .setItems(lastNames, new DialogInterface.OnClickListener() {
						               public void onClick(DialogInterface dialog, int which) {
						            	   final ParseObject obj = usersList.get(which);
						            	   ParseUser currentUser = ParseUser.getCurrentUser();
						            	   ParseQuery<ParseObject> query = ParseQuery.getQuery("Application");
						            	   query.whereEqualTo(ToDoActivity.username, currentUser.getUsername());
						            	   query.whereEqualTo(ToDoActivity.id, application.getId());
						            	   query.findInBackground(new FindCallback<ParseObject>() {
											@Override
											public void done(List<ParseObject> objects, ParseException e) {
												if(e==null){
													/*if(objects!=null && objects.size()!=0){
														ParseObject updateObj = objects.get(0);
														updateObj.put(ToDoActivity.isShared, true);
														updateObj.put(ToDoActivity.sharedWith, obj.getInt(ToDoActivity.username)+"");
														updateObj.saveInBackground(new SaveCallback() {
															@Override
															public void done(ParseException e) {
																if(e==null){
																	Toast.makeText(PreviewActivity.this, "Shared with "+obj.getString("firstName"), Toast.LENGTH_LONG).show();
																}
															}
														});
													}else{*/
														ParseObject newObject = new ParseObject("Application2");
														ParseUser currentUser = ParseUser.getCurrentUser();
														application.setShared(true);
														application.setSharedWith(obj.getString("username"));
														if (currentUser.getUsername() != null) {
															newObject.put("username", currentUser.getUsername());
														} else {
															newObject.put("username", "");
														}
														if (application.getAppTitle() != null) {
															newObject.put("appTitle", application.getAppTitle());
														} else {
															newObject.put("appTitle", "");
														}
														if (application.getDeveloperName() != null) {
															newObject.put("developerName", application.getDeveloperName());
														} else {
															newObject.put("developerName", "");
														}
														if (application.getUrl() != null) {
															newObject.put("url", application.getUrl());
														} else {
															newObject.put("url", "");
														}
														if (application.getLargePhotoUrl() != null) {
															newObject.put("largePhotoUrl", application.getLargePhotoUrl());
														} else {
															newObject.put("largePhotoUrl", "");
														}
														try {
															newObject.put("price", application.getPrice());
															newObject.put("id", application.getId());
															newObject.put("isFavorite", application.isFavorite());
															newObject.put("isShared", application.isShared());
														} catch (Exception ex) {

														}
														if (application.getSharedWith() != null) {
															newObject.put("sharedWith", application.getSharedWith());
														} else {
															newObject.put("sharedWith", "");
														}
														newObject.saveInBackground(new SaveCallback() {
															
															@Override
															public void done(ParseException e) {
																Toast.makeText(PreviewActivity.this, "Shared with "+obj.getString("firstName"), Toast.LENGTH_LONG).show();															
															}
														});
												}
											}
										});
						           }
						    }).show();
						}
					}
				});
				
			}
		});
	}
	class CustomComparator implements Comparator<ParseUser> {

		@Override
		public int compare(ParseUser lhs, ParseUser rhs) {
			
			return lhs.getString("lastName").compareToIgnoreCase(rhs.getString("lastName"));
		}
	}
}
