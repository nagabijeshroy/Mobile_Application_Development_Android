
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.homework04;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	Button programsButton;
	Button favoritesButton;
	Button topicsButton;
	Button exitButton;
	final static String LIST_OPTION = "list";
	public static final String SHARED_PREFERENCES_KEY="favorites";
	public static final String MY_PREFERENCES="preferences";
	static ArrayList<Story> storiesList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		programsButton = (Button) findViewById(R.id.buttonPrograms);
		topicsButton = (Button) findViewById(R.id.buttonTopics);
		favoritesButton = (Button) findViewById(R.id.buttonFavorites);
		exitButton = (Button) findViewById(R.id.buttonExit);
		programsButton.setTextColor(Color.WHITE);
		programsButton.setBackgroundColor(Color.DKGRAY);
		topicsButton.setTextColor(Color.WHITE);
		topicsButton.setBackgroundColor(Color.DKGRAY);
		favoritesButton.setTextColor(Color.WHITE);
		favoritesButton.setBackgroundColor(Color.DKGRAY);
		exitButton.setTextColor(Color.WHITE);
		exitButton.setBackgroundColor(Color.DKGRAY);
		storiesList = new ArrayList<Story>();
		SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
		Gson gson = new Gson();
		try {
			JSONArray jsonArray = new JSONArray(sharedpreferences.getString(MainActivity.SHARED_PREFERENCES_KEY, ""));
			Log.d("json", jsonArray.length()+"");
			if(jsonArray.length()>0){
				for (int i = 0; i < jsonArray.length(); i++) {
					Story story = gson.fromJson(jsonArray.getJSONObject(i).toString(),Story.class);
					storiesList.add(story);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
		programsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				intent.putExtra(MainActivity.LIST_OPTION, "programs");
				startActivity(intent);
			}
		});
		topicsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				intent.putExtra(MainActivity.LIST_OPTION, "topics");
				startActivity(intent);
			}
		});
		favoritesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MainActivity.storiesList!=null && MainActivity.storiesList.size() != 0) {
					Intent intent = new Intent(MainActivity.this, StoriesActivity.class);
					intent.putExtra(ListActivity.ITEM_TYPE, "favorites");
					startActivity(intent);
				}else{
					Toast.makeText(MainActivity.this, getResources().getString(R.string.no_favorites), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
