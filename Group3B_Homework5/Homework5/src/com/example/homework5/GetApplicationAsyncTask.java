/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */


package com.example.homework5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetApplicationAsyncTask extends AsyncTask<String, Void, ArrayList<Application>>{
	
	
	ProgressDialog pd;
	Idata activity;
	public GetApplicationAsyncTask(Idata activity) {
		this.activity = activity;
	}
	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog((Context) activity);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(((Context) activity).getResources().getString(R.string.loading_application));
		pd.setCancelable(false);
		pd.show();
		super.onPreExecute();
	}
	@Override
	protected ArrayList<Application> doInBackground(String... params) {

		try {
			URL url = new URL(params[0]);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			int statusCode = connection.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();
				return PullParser.parseApplications(in);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Application> result) {
		if (result != null) {
			pd.dismiss();
			activity.populateList(result);
		}

	}
	public static interface Idata{
		public void populateList(ArrayList<Application> result);
	}
}
