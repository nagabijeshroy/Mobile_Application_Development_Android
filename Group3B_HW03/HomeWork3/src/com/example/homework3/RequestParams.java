package com.example.homework3;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RequestParams {
	
	public void addParam(String key,String value){
		params.put(key, value);
	}

	public RequestParams(String method, String baseUrl) {
		super();
		this.method = method;
		this.baseUrl = baseUrl;
	}
	
	String method,baseUrl;
	
	HashMap<String,String> params = new HashMap<String,String>();
	
	public String getEncodedUrl(){
		return this.baseUrl + 
				"?" + getEncodedParams();
	}
	
	public HttpURLConnection setupConnection() {
		HttpURLConnection con = null;
		URL url = null;
		try{
		if(method.equals("GET")){
			url= new URL(getEncodedUrl());
			con= (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		}else
		{
			url = new URL(this.baseUrl);
			con= (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(getEncodedParams());
			writer.flush();
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
		
	}
	
	
	public String getEncodedParams(){
		StringBuilder sb = new StringBuilder();
		
		for(String key : params.keySet()){
			try {
				String value = URLEncoder.encode(params.get(key), "UTF-8");
				
				
				if(sb.length()>0){
					sb.append("&");
				}
				
				sb.append(key +"="+value);
				
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return sb.toString();
	}
	
}
