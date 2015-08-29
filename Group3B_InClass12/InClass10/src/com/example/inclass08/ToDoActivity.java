package com.example.inclass08;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.inclass08.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
					if (e == null) {
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

	
}
