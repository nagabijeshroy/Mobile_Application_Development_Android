
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity implements GetJsonAsyncList.Idata{
	ListView listView;
	static String itemType="";
	final static String ITEM_ID = "item";
	final static String ITEM_TYPE = "itemType";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		String option = (String) getIntent().getExtras().get(MainActivity.LIST_OPTION);
		String url = "";
		itemType = option;
		if(option.equalsIgnoreCase("programs")){
			url="http://api.npr.org/list?id=3004&output=JSON";
			new GetJsonAsyncList(this).execute(url);
		}else if(option.equalsIgnoreCase("topics")){
			url="http://api.npr.org/list?id=3002&output=JSON";
			new GetJsonAsyncList(this).execute(url);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void populateList(final ArrayList<TopicPerson> result) {
	listView = (ListView)findViewById(R.id.listViewListActivity);
		
		ArrayAdapter<ArrayList<TopicPerson>> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, result);
		
		adapter.setNotifyOnChange(true);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ListActivity.this, StoriesActivity.class);
				intent.putExtra(ListActivity.ITEM_ID, result.get(position));
				intent.putExtra(ListActivity.ITEM_TYPE,"list");
				startActivity(intent);
			}
			
		});
				
	}

	@Override
	public void populateStoryList(ArrayList<Story> result) {
		// TODO Auto-generated method stub
		
	}
}
