package com.mad.inclass07;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Photo implements Serializable{


	String url, owner, title;
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Photo(int id, String url, String owner, String title) {
		super();
		this.id = id;
		this.url = url;
		this.owner = owner;
		this.title = title;
	}
	
	public Photo(){
		
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		
		return title;
	}
	
}
	