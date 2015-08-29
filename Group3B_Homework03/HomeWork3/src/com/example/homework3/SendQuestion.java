package com.example.homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import android.os.AsyncTask;
import android.util.Log;

public class SendQuestion extends AsyncTask<RequestParams, Void, String>{
	@Override
	protected void onPostExecute(String result) {
		Log.d("done","Done");
		System.exit(0);
	}
	BufferedReader reader;
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
			sb.append("\nend");
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
