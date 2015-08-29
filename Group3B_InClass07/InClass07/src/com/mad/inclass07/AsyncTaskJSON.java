package com.mad.inclass07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskJSON extends AsyncTask<String, Void, ArrayList<Photo>> {

	passdatatoMain jsonactivity;
	ProgressDialog pd;
	GalleryActivity activity;

	public AsyncTaskJSON(GalleryActivity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(activity);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(activity.getResources().getString(R.string.loading));
		pd.setCancelable(false);
		pd.show();
	}

	public AsyncTaskJSON(passdatatoMain jsonactivity) {
		this.jsonactivity = jsonactivity;
	}

	@Override
	protected void onPostExecute(ArrayList<Photo> result) {
		if (result != null) {
			Log.d("demo",""+result.get(0).getUrl());
			pd.dismiss();
			activity.populateList(result);
		}
	}

	@Override
	protected ArrayList<Photo> doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);
			Log.d("demo", "status code = " + url);
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
				return PhotoUtils.parsePhoto(sb.toString());
			}
		} catch (Exception e) {

		}

		return null;
	}

	public interface passdatatoMain {
		public void populateList(ArrayList<Photo> photo);
	}

}
