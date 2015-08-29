package com.example.inclass5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

public class FlickerUtil extends AsyncTask<String, Void, ArrayList<String>> {
	static int i = 0;

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		URL url;
		try {
			url = new URL(params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream in = con.getInputStream();
				return SaxParser.FlickrSAXParser.ParseFlickrXML(in);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		if (result != null) {
			FlickrActivity.mainPhotoList = result;
			Log.d("test", "Data" + result.get(0));
		}

		else {
			Log.d("test", "no arraylist contents received");
		}

		super.onPostExecute(result);
	}

}
