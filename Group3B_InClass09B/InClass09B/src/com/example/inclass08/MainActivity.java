package com.example.inclass08;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity implements AsyncTaskJSON.passdatatoMain {
	String URL = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int id = item.getItemId();

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		// Get the layout inflater
		builder.setTitle("Enter a Location");
		LayoutInflater inflater = MainActivity.this.getLayoutInflater();

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
						if (itemText.getText() != null && !itemText.getText().toString().equals("")) {
							URL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + itemText.getText().toString()
									+ "&key=AIzaSyCRlkEy_g1e7lhylCmFi8TlxN7YKg2PGhI";
							new AsyncTaskJSON(MainActivity.this).execute(URL);
							/*
							 * ParseQuery<ParseObject> query =
							 * ParseQuery.getQuery("GameScore");
							 * query.whereEqualTo("playerName",
							 * "Dan Stemkoski"); query.findInBackground(new
							 * FindCallback<ParseObject>() { public void
							 * done(List<ParseObject> scoreList, ParseException
							 * e) { if (e == null) { Log.d("score", "Retrieved "
							 * + scoreList.size() + " scores"); } else {
							 * Log.d("score", "Error: " + e.getMessage()); } }
							 * });
							 */
						} else {
							Toast.makeText(MainActivity.this, "Search Cannot be empty", Toast.LENGTH_LONG).show();
						}
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				}).show();

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void populateList(Places places) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("foodPlaces");
		query.whereWithinMiles("location", new ParseGeoPoint(places.getLat(), places.getLng()), 50);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, com.parse.ParseException e) {
				if (e == null) {
					GoogleMap gMap;
					gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
					for (ParseObject object : objects) {
						gMap.addMarker(new MarkerOptions().position(
								new LatLng(((ParseGeoPoint) object.get("location")).getLatitude(), ((ParseGeoPoint) object.get("location")).getLongitude()))
								.title(object.getString("name")));
					}
					
				}
			}

		});
	}

}
