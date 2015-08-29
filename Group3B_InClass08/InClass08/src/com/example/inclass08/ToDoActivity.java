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
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoActivity extends Activity {

	ListView todoListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		todoListView = (ListView) findViewById(R.id.listView1);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ToDo");
			query.whereEqualTo("username", currentUser.getUsername());
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> todoList, ParseException e) {
					if (e != null) {
						ArrayList<String> todoArrayList = new ArrayList<String>();
						
						 for(ParseObject listItem : todoList){ 
						 todoArrayList.add((String) listItem.get("item"));
						 }
						 ArrayAdapter<String> adapter = new	ArrayAdapter<String>(ToDoActivity.this,android.R.layout.simple_list_item_1,todoArrayList);
						 adapter.setNotifyOnChange(true);
						 todoListView.setAdapter(adapter);
						 
					} else {

					}
				}
			});
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
		if (id == R.id.action_add) {
			final ParseUser currentUser = ParseUser.getCurrentUser();
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
								recreate();
							} else {
								Toast.makeText(ToDoActivity.this, "Item Text Cannot be empty", Toast.LENGTH_LONG).show();
							}
						}
					}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					}).show();
		} else if (id == R.id.action_logout) {
			ParseUser.logOut();
			Intent intent = new Intent(ToDoActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
