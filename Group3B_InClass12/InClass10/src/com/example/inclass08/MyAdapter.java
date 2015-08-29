package com.example.inclass08;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	private ArrayList<ToDoList> mDataset;
	Activity activity;

	// Provide a reference to the views for each data item
	// Complex data items may need more than one view per item, and
	// you provide access to all the views for a data item in a view holder
	public static class ViewHolder extends RecyclerView.ViewHolder {
		// each data item is just a string in this case
		public TextView mTextView;
		public ImageView editButton;
		public ImageView discardButton;

		public ViewHolder(View v) {
			super(v);
			mTextView = (TextView) v.findViewById(R.id.textViewData);
			editButton = (ImageView) v.findViewById(R.id.imageViewEdit);
			discardButton = (ImageView) v.findViewById(R.id.imageViewDiscard);
		}
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public MyAdapter(ArrayList<ToDoList> todoArrayList, Activity activity) {
		mDataset = todoArrayList;
		this.activity = activity;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
		Log.d("Error", viewType + "");
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder vh, final int viewType) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		vh.editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				ParseUser currentUser = ParseUser.getCurrentUser();
				ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
				query.getInBackground(mDataset.get(viewType).getObjectId(), new GetCallback<ParseObject>() {
					@Override
					public void done(ParseObject object, ParseException e) {
						final ParseUser currentUser = ParseUser.getCurrentUser();
						AlertDialog.Builder builder = new AlertDialog.Builder(activity);
						// Get the layout inflater
						builder.setTitle("Edit the Item");
						LayoutInflater inflater = activity.getLayoutInflater();

						// Inflate and set the layout for the dialog
						// Pass null as the parent view because its going in the
						// dialog
						// layout
						final View alertView = inflater.inflate(R.layout.alert_layout, null);
						final EditText itemText = (EditText) alertView.findViewById(R.id.itemName);
						ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
						query.getInBackground(mDataset.get(viewType).getObjectId(), new GetCallback<ParseObject>() {
							@Override
							public void done(ParseObject object, ParseException e) {
								if (e == null) {
									ToDoList todo = new ToDoList(object.getObjectId(), (String) object.get("item"));
									itemText.setText(todo.getItem().toString());
								} else {
									Log.d("Error", e.getMessage());
								}
							}
						});
						builder.setView(alertView)
						// Add action buttons
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										if (itemText.getText() != null && !itemText.getText().toString().equals("")) {
											ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
											query.getInBackground(mDataset.get(viewType).getObjectId(), new GetCallback<ParseObject>() {
												@Override
												public void done(ParseObject object, ParseException e) {
													if (e == null) {
														object.put("item", itemText.getText().toString());
														object.saveInBackground();
														activity.recreate();
													}
												}
											});
										} else {
											Toast.makeText(activity, "Item Text Cannot be empty", Toast.LENGTH_LONG).show();
										}
									}
								}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
									}
								}).show();

					}
				});
			}
		});
		vh.discardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Error", viewType + "" + mDataset.get(viewType).getObjectId());
				ParseUser currentUser = ParseUser.getCurrentUser();
				ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
				query.getInBackground(mDataset.get(viewType).getObjectId(), new GetCallback<ParseObject>() {
					@Override
					public void done(ParseObject object, ParseException e) {
						if (e == null) {
							object.deleteInBackground();
							activity.recreate();
						} else {
							Log.d("Error", e.getMessage());
						}
					}
				});
			}
		});
		vh.mTextView.setText(mDataset.get(viewType).getItem());

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mDataset.size();
	}

}