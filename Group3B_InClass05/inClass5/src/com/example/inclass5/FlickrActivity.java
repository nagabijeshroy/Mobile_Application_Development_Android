package com.example.inclass5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class FlickrActivity extends Activity {
	Boolean checkParsingType = false;
	static ArrayList<String> mainPhotoList = new ArrayList<String>();
	static Bitmap image;
	static String urlString = null;
	static int i = 0;
	ImageView im1;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flickr);
		Switch parsingcheck = (Switch) findViewById(R.id.switch1);
		parsingcheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checkParsingType = isChecked;
				Log.d("test",isChecked+"");
			}
		});
		im1 = (ImageView) findViewById(R.id.imageView1);
		findViewById(R.id.imageButtonNext).setFocusable(false);
		findViewById(R.id.imageButtonNext).setEnabled(false);
		findViewById(R.id.imageButtonNext).setFocusableInTouchMode(false);
		findViewById(R.id.imageButtonPrev).setFocusable(false);
		findViewById(R.id.imageButtonPrev).setEnabled(false);
		findViewById(R.id.imageButtonPrev).setFocusableInTouchMode(false);
		final EditText textView = (EditText) findViewById(R.id.editText1);
		mainPhotoList = new ArrayList<String>();
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				ConnectivityManager cv = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = cv.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					i = 0;
					if (textView.getText().toString().length() != 0) {
						urlString = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=d03156e273626dba8d629deffc40642c&text="
								+ textView.getText().toString() + "&extras=url_m&per_page=20&format=rest";
						findViewById(R.id.imageButtonNext).setFocusable(true);
						findViewById(R.id.imageButtonNext).setFocusableInTouchMode(true);
						findViewById(R.id.imageButtonNext).setEnabled(true);						
						findViewById(R.id.imageButtonPrev).setFocusable(true);
						findViewById(R.id.imageButtonPrev).setFocusableInTouchMode(true);
						findViewById(R.id.imageButtonPrev).setEnabled(true);
						new FlickerUtil().execute(urlString);

					} else {
						Toast.makeText(FlickrActivity.this, "Search cannot be null", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(FlickrActivity.this, "No internet", Toast.LENGTH_LONG).show();
				}
			}
		});

		findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (i>=0 && i < (mainPhotoList.size()-1)) {
					i++;
					new FetchImage().execute(mainPhotoList.get(i));
				}else{
					i=0;
					new FetchImage().execute(mainPhotoList.get(i));
				}
			}
		});
		findViewById(R.id.imageButtonPrev).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (i>=0 && i< mainPhotoList.size()) {
					new FetchImage().execute(mainPhotoList.get(i));
					i--;
				}else{
					i=mainPhotoList.size()-1;
					new FetchImage().execute(mainPhotoList.get(i));
				}
			}
		});

	}
	class FlickerUtil extends AsyncTask<String, Void, ArrayList<String>> {

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
					
					if (checkParsingType) {
						return SaxParser.FlickrSAXParser.ParseFlickrXML(in);
					} else {
						return PullParser.parsePersons(in);
					}
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}catch (XmlPullParserException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(FlickrActivity.this);
			pd.setCancelable(false);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Loading");
			pd.show();
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			if (result != null) {
				pd.dismiss();
				mainPhotoList = result;
				new FetchImage().execute(result.get(i));
			} else {
				Log.d("test", "no arraylist contents received");
			}
			super.onPostExecute(result);
		}

	}

	class FetchImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				pd.dismiss();
				im1.setImageBitmap(result);
			} else {
				Log.d("test", "no data received FetchImage");
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			pd.show();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con;
				con = (HttpURLConnection) url.openConnection();
				Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
				return image;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}

			return null;
		}

	}
}
