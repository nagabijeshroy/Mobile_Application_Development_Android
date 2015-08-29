/**
 * Homework - 3
 * Naga Bijesh Roy Raya
 * James Budday
 * Shyam Mohan Raman
 */


package com.example.homework3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


/* 
 * need to work on how to connect to the server and what data needs to be fed and what data will be received.
 * progress dialog that will run when the delete api call happens.
 */


public class MainActivity extends Activity {
	ProgressDialog progressdialog;
	RequestParams requestparams;
	BufferedReader reader;
	String deleteUrl = "http://dev.theappsdr.com/apis/trivia/deleteAll.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
				
		findViewById(R.id.Start).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
				startActivity(intent);
			}
		});
		
		
		findViewById(R.id.quizQ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,CreateQuestionActivity.class);
				startActivity(intent);
			}
		});
		
		findViewById(R.id.main_b_delete).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//http post method call using an Async task.
				
				new AlertDialog.Builder(MainActivity.this).setTitle(getResources().getString(R.string.main_del_alert_title))
				.setMessage(getResources().getString(R.string.main_del_alert_msg))
				.setPositiveButton(getResources().getString(R.string.main_del_alert_positive), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						requestparams = new RequestParams("POST", deleteUrl);
						requestparams.addParam("gid","qdkk1TFaWODX6vpQdQYr");						// add gid here
						new rundelete().execute(requestparams);
						
					}
				}).setNegativeButton(getResources().getString(R.string.main_del_alert_negative), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
				
				
			}
		});
		
		findViewById(R.id.Exit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
		/* don't know what the deleteall questions method call will return. need to find that first 
		 * Assuming that it takes the delete URL + group ID as input.*/
		class rundelete extends AsyncTask<RequestParams, Void, String> {
			
			@Override
			protected void onPreExecute() {
				progressdialog = new ProgressDialog(MainActivity.this);
				progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressdialog.setCancelable(true);
				progressdialog.setMax(100);
				progressdialog.setTitle("Deleting Questions");
				progressdialog.show();
			}

			@Override
			protected void onPostExecute(String result) {
				try {
				progressdialog.dismiss();
				if(result.equalsIgnoreCase("enter the value you receive when you run the delete message successfully."))
					Log.d("hw3","success" + result);
				else{
					Log.d("hw3","nothing returned");
				}
				}catch(Exception e){
					e.printStackTrace();
				}
					
			}

			

			@Override
			protected String doInBackground(RequestParams... params) {
				try{
					HttpURLConnection con = params[0].setupConnection();
					reader = new BufferedReader(new InputStreamReader(con.getInputStream())); //used for xmlparsing Json parsing
					StringBuilder sb = new StringBuilder();
					String line ="";
					while((line = reader.readLine()) !=null){
						sb.append(line + "\n");
					}
					return sb.toString();
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				finally{
					try {
						if(reader != null){
							reader.close();
						}
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return null;
			
				
			}
			
		}
	}

