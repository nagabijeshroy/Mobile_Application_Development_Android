
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetJSONAsyncStoryList extends AsyncTask<String, Void, ArrayList<Story>> {

	ProgressDialog pd;
	Idata activity;
	public GetJSONAsyncStoryList(Idata activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog((Context) activity);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(((Context) activity).getResources().getString(R.string.loading_stories));
		pd.setCancelable(false);
		pd.show();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Story> doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.connect();

			int statusCode = con.getResponseCode();

			if (statusCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

				StringBuilder sb = new StringBuilder();

				String line = "";

				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				return JsonUtil.parseStoryJson(sb.toString());

			}
		} catch (Exception e) {

		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Story> result) {
		if (result != null) {
			pd.dismiss();
			activity.populateStoryList(result);
		}
	}
	public static interface Idata{
		public void populateList(ArrayList<TopicPerson> result);
		public void populateStoryList(ArrayList<Story> result);
	}

}
