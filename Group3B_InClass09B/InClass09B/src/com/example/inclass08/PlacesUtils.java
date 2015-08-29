package com.example.inclass08;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesUtils {
 static Places parsePlaces(String in){
	 
	 try {
		 JSONObject root = new JSONObject(in);
			
			JSONArray PhotoJSONArray = root.getJSONArray("results");

			JSONObject geometry = PhotoJSONArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
			Places places = new Places();
			places.setLat(Double.parseDouble(geometry.getString("lat")));
			places.setLng(Double.parseDouble(geometry.getString("lng")));
		return places;
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	return null;
	 
 }
}
