/**
 * 
 * ITCS5180 - InClass04
 * Group3B_InClass04
 * 1. Naga Bijesh roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.inclass04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ProgressDialog progressDialog;
	TextView textViewValue;
	TextView textViewComplexity;
	static int progressValue = 1;
	static double averageCount = 0.0;
	ExecutorService threadPoolExecutor;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarComplexity);
		textViewValue = (TextView) findViewById(R.id.textViewComplexityValue);
		seekBar.setProgress(progressValue);
		textViewValue.setText(progressValue + " " + getResources().getString(R.string.text_value_number));
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				textViewValue.setText(progress + " " + getResources().getString(R.string.text_value_number));
				progressValue = progress;
			}
		});
		Button asyncTask = (Button) findViewById(R.id.buttonAsyncBtn);
		asyncTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (progressValue == 0) {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.error_title), Toast.LENGTH_LONG).show();
				} else {
					new DoWork().execute();
				}
			}
		});
		Button threadBtn = (Button) findViewById(R.id.buttonNumberThreadBtn);
		
		threadPoolExecutor = Executors.newFixedThreadPool(2);
		handler = new Handler(new Handler.Callback() {
		
			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {
				case DoWorkHandler.STATUS_START:
					progressDialog.show();
					break;
				case DoWorkHandler.STATUS_STEP:
					progressDialog.setProgress(msg.getData().getInt("PROGRESS"));
					break;
				case DoWorkHandler.STATUS_STOP:
					progressDialog.dismiss();
					textViewComplexity = (TextView) findViewById(R.id.textViewNumberValueText);
					textViewComplexity.setText(averageCount + "");
					break;
				}
				return false;
			}
		});
		threadBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (progressValue == 0) {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.error_title), Toast.LENGTH_LONG).show();
				} else {
					progressDialog = new ProgressDialog(MainActivity.this);
					progressDialog.setMessage(getResources().getString(R.string.progress_title));
					progressDialog.setMax(progressValue);
					progressDialog.setCancelable(false);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					threadPoolExecutor.execute(new DoWorkHandler());
				}
			}
		});
	}

	class DoWorkHandler implements Runnable {
		static final int STATUS_START = 0x01;
		static final int STATUS_STEP = 0x02;
		static final int STATUS_STOP = 0x03;

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = STATUS_START;
			averageCount = 0.0;
			handler.sendMessage(msg);
			for (int i = 0; i < progressValue; i++) {
				averageCount += HeavyWork.getNumber();
				msg = new Message();
				msg.what = STATUS_STEP;
				Bundle data = new Bundle();
				data.putInt("PROGRESS", i + 1);
				msg.setData(data);
				handler.sendMessage(msg);
			}
			averageCount /= progressValue;
			msg = new Message();
			msg.what = STATUS_STOP;
			handler.sendMessage(msg);
		}

	}

	class DoWork extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			averageCount = 0.0;
			for (int i = 0; i < progressValue; i++) {
				averageCount += HeavyWork.getNumber();
				publishProgress(i + 1);
			}
			averageCount /= progressValue;
			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage(getResources().getString(R.string.progress_title));
			progressDialog.setMax(progressValue);
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			textViewComplexity = (TextView) findViewById(R.id.textViewNumberValueText);
			textViewComplexity.setText(averageCount + "");
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.d("test", "Progress Value" + values[0]);
			progressDialog.setProgress(values[0]);
		}
	}
}
