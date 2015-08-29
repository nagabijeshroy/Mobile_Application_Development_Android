package com.example.inclass08;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskJSON extends AsyncTask<String, Void, Places> {

	passdatatoMain jsonactivity;

	
	

	public AsyncTaskJSON(passdatatoMain jsonactivity) {
		super();
		this.jsonactivity = jsonactivity;
	}

	@Override
	protected void onPostExecute(Places result) {
		if (result != null) {
			jsonactivity.populateList(result);
		}
	}

	@Override
	protected Places doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.connect();

			int statusCode = con.getResponseCode();
			Log.d("demo", "status code = " + statusCode);

			if (statusCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				StringBuilder sb = new StringBuilder();

				String line = "";

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				// Log.d("demo",sb.toString());
				return PlacesUtils.parsePlaces(sb.toString());

			}
		} catch (Exception e) {

		}

		return null;
	}

	public interface passdatatoMain {
		public void populateList(Places places);
	}

}
