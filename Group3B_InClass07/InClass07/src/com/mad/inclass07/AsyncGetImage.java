package com.mad.inclass07;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncGetImage extends AsyncTask<String, Void, Bitmap>{
	DetailsActivity activity;
	public AsyncGetImage(DetailsActivity activity)
	{
		this.activity = activity;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		activity.loadimage(result);
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		try{
		URL url = new URL(params[0]);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		
		Log.d("connection status", con.getResponseCode()+"");
		
		if( con.getResponseCode() == HttpURLConnection.HTTP_OK){
			
			Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
			return image;	
		}
		}
		catch(Exception e){
			
		}
		return null;
	}

	
}
