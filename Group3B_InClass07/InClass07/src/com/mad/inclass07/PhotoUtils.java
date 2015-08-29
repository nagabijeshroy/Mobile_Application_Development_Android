package com.mad.inclass07;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PhotoUtils {
	static ArrayList<Photo> parsePhoto(String in) {
		ArrayList<Photo> PhotoList = new ArrayList<Photo>();

		try {
			JSONObject root = new JSONObject(in);

			JSONArray PhotoJSONArray = root.getJSONArray("photos");

			for (int i = 0; i < PhotoJSONArray.length(); i++) {
				JSONObject PhotoJSONObject = PhotoJSONArray.getJSONObject(i);
				Photo photo = new Photo();
				photo.setId(Integer.parseInt(PhotoJSONObject.getString("id")));
				photo.setTitle(PhotoJSONObject.getString("name"));
				photo.setOwner(PhotoJSONObject.getJSONObject("user").getString("fullname"));
				photo.setUrl(PhotoJSONObject.getString("image_url"));
				Log.d("demo", photo.getUrl());
				PhotoList.add(photo);
			}
			return PhotoList;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
