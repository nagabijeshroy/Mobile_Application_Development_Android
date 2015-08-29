
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mad.homework04.GetJSONAsyncStoryList.Idata;

public class StoriesActivity extends Activity implements Idata {

	static TopicPerson topicPerson = null;
	ListView storiesListView;
	final static String STORY = "story";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stories);
		String type = (String) getIntent().getExtras().get(ListActivity.ITEM_TYPE);
		if (type.equalsIgnoreCase("list")) {
			topicPerson = (TopicPerson) getIntent().getExtras().getSerializable(ListActivity.ITEM_ID);
			String url = "http://api.npr.org/query?id="
					+ topicPerson.getId()
					+ "&fields=title,teaser,storyDate,show,byline,text,audio,image,textWithHtml,listText,pullQuote,parent,relatedLink,album,artist,product,transcript,correction,all&dateType=story&output=JSON&numResults=25&apiKey=MDE4MzE0MTc2MDE0MjQyNzg5MTgwMTU2Nw001";
			new GetJSONAsyncStoryList(this).execute(url);
		} else if (type.equalsIgnoreCase("favorites")) {
			if (MainActivity.storiesList != null && MainActivity.storiesList.size() != 0) {
				storiesListView = (ListView) findViewById(R.id.listViewStoryActivity);
				StoryAdapter adapter = new StoryAdapter(StoriesActivity.this, R.layout.activity_story_list, MainActivity.storiesList);
				storiesListView.setAdapter(adapter);
				adapter.setNotifyOnChange(true);
				storiesListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(StoriesActivity.this, StoryActivity.class);
						intent.putExtra(STORY, MainActivity.storiesList.get(position));
						startActivityForResult(intent, 101);
					}
				});
			} else {
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stories, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.history) {
			MainActivity.storiesList.clear();
			SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
			Editor editor = sharedpreferences.edit();
			editor.putString(MainActivity.SHARED_PREFERENCES_KEY, "");
			editor.commit();
			Toast.makeText(StoriesActivity.this, getResources().getString(R.string.clear_favorites), Toast.LENGTH_SHORT).show();
			recreate();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void populateList(ArrayList<TopicPerson> result) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		recreate();
	}

	@Override
	public void populateStoryList(final ArrayList<Story> result) {
		storiesListView = (ListView) findViewById(R.id.listViewStoryActivity);
		StoryAdapter adapter = new StoryAdapter(StoriesActivity.this, R.layout.activity_story_list, result);
		storiesListView.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		storiesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(StoriesActivity.this, StoryActivity.class);
				intent.putExtra(STORY, result.get(position));
				startActivityForResult(intent, 101);
			}
		});
	}
}
