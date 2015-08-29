/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */
package com.example.homework5;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ToDoActivity extends Activity implements GetApplicationAsyncTask.Idata {

	ListView appListView;
	static ArrayList<Application> appListFetched = null;
	final static String APP_DATA = "appData";
	final static String username = "username";
	final static String appTitle = "appTitle";
	final static String developerName = "developerName";
	final static String url = "url";
	final static String largePhotoUrl = "largePhotoUrl";
	final static String price = "price";
	final static String id = "id";
	final static String isFavorite = "isFavorite";
	final static String isShared = "isShared";
	final static String sharedWith = "sharedWith";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		appListView = (ListView) findViewById(R.id.listView1);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			String url = "";
			url = "https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml";
			new GetApplicationAsyncTask(ToDoActivity.this).execute(url);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.view_all) {
			/*final ParseUser currentUser = ParseUser.getCurrentUser();
			AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
			// Get the layout inflater
			builder.setTitle("Add an Item");
			LayoutInflater inflater = ToDoActivity.this.getLayoutInflater();

			// Inflate and set the layout for the dialog
			// Pass null as the parent view because its going in the dialog
			// layout
			final View alertView = inflater.inflate(R.layout.alert_layout, null);
			builder.setView(alertView)
			// Add action buttons
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							
							EditText itemText = (EditText) alertView.findViewById(R.id.itemName);
							if (itemText.getText()!=null && !itemText.getText().toString().equals("")) {
								ParseObject todoObject = new ParseObject("ToDo");
								todoObject.put("item", itemText.getText().toString());
								todoObject.put("username", currentUser.getUsername());
								todoObject.saveInBackground();
								recreate();
							} else {
								Toast.makeText(ToDoActivity.this, "Item Text Cannot be empty", Toast.LENGTH_LONG).show();
							}
						}
					}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					}).show();
*/		
		populateList(appListFetched);	
		} else if (id == R.id.logout) {
			ParseUser.logOut();
			Intent intent = new Intent(ToDoActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}else if(id == R.id.view_favorites){
			populateList(0);
		}else if(id == R.id.clear_favorites){
			clearList(0);			
		}else if(id == R.id.view_shared){
			populateList(1);
		}else if(id == R.id.clear_shared){
			clearList(1);
		}

		return super.onOptionsItemSelected(item);
	}

	private void clearList(final int i) {
		ParseQuery<ParseObject> query = null ;
		if(i==0){
			query= ParseQuery.getQuery("Application");
			ParseUser currentUser = ParseUser.getCurrentUser();
			query.whereEqualTo(username, currentUser.getUsername());
			query.whereEqualTo(isFavorite,true);
		}else if(i==1){
			query = ParseQuery.getQuery("Application2");
			ParseUser currentUser = ParseUser.getCurrentUser();
			query.whereEqualTo(isShared,true);
			query.whereEqualTo(sharedWith, currentUser.getEmail());
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				for(ParseObject listItem : objects){
					
					//listItem.deleteInBackground();
					// Increment the current value of the quantity key by 1
					if(i==0){
						listItem.deleteInBackground();
					}else if(i==1){
						listItem.deleteInBackground();
					}

					// Save
				}
				if(objects.size()!=0){
				if(i==1){
					Toast.makeText(ToDoActivity.this, getResources().getString(R.string.clear_shared), Toast.LENGTH_LONG).show();
					recreate();
				}else{
					Toast.makeText(ToDoActivity.this, getResources().getString(R.string.clear_favorites), Toast.LENGTH_LONG).show();
					recreate();
				}
				}else{
					if(i==1){
						Toast.makeText(ToDoActivity.this, getResources().getString(R.string.no_shared), Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ToDoActivity.this, getResources().getString(R.string.no_favorites), Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	private void populateList(int option) {
		ParseQuery<ParseObject> query = null;
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if(option==0){
			query = ParseQuery.getQuery("Application");
			query.whereEqualTo(isFavorite,true);
			query.whereEqualTo(username, currentUser.getUsername());
		}else if(option==1){
			query = ParseQuery.getQuery("Application2");
			query.whereEqualTo(isShared,true);
			query.whereEqualTo(sharedWith, currentUser.getEmail());
			//query.whereEqualTo(ToDoActivity.sharedWith, currentUser.getUsername());
		}
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> todoList, ParseException e) {
				if (e == null) {
					ArrayList<Application> applicationList = new ArrayList<Application>();
					 for(ParseObject listItem : todoList){ 
						 Application application = new Application();
						 application.setAppTitle((String) listItem.get(appTitle));
						 application.setDeveloperName((String) listItem.get(developerName));
						 application.setShared(listItem.getBoolean(isShared));
						 application.setFavorite(listItem.getBoolean(isFavorite));
						 application.setSharedWith((String)listItem.get(sharedWith));
						 application.setId(listItem.getInt(ToDoActivity.id)); 
						 application.setPrice(listItem.getDouble(price));
						 application.setUrl((String)listItem.get(url));
						 application.setLargePhotoUrl((String)listItem.get(largePhotoUrl));
						 applicationList.add(application);
					 }
					 populateFavList(applicationList);
					/* ArrayAdapter<String> adapter = new	ArrayAdapter<String>(ToDoActivity.this,android.R.layout.simple_list_item_1,todoArrayList);
					 adapter.setNotifyOnChange(true);
					 todoListView.setAdapter(adapter);*/
				} else {

				}
				
			}
		});		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==102){
			populateList(0);
		}
	}

	@Override
	public void populateList(final ArrayList<Application> result) {
		if (result != null) {
			Log.d("size", result.size() + "");
			appListFetched = result;
			appListView = (ListView) findViewById(R.id.listView1);
			ApplicationAdapter adapter = new ApplicationAdapter(ToDoActivity.this, R.layout.apps_list_layout, result);
			appListView.setAdapter(adapter);
			adapter.setNotifyOnChange(true);
			appListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ToDoActivity.this, PreviewActivity.class);
					intent.putExtra(APP_DATA, result.get(position));
					startActivityForResult(intent, 101);
				}
			});
		}
	}
		public void populateFavList(final ArrayList<Application> result) {
			if (result != null) {
				Log.d("size", result.size() + "");
				appListView = (ListView) findViewById(R.id.listView1);
				ApplicationAdapter adapter = new ApplicationAdapter(ToDoActivity.this, R.layout.apps_list_layout, result);
				appListView.setAdapter(adapter);
				adapter.setNotifyOnChange(true);
				appListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									Intent intent = new Intent(ToDoActivity.this, PreviewActivity.class);
						intent.putExtra(APP_DATA, result.get(position));
						startActivityForResult(intent, 102);
					}
				});
			}
	}
}
