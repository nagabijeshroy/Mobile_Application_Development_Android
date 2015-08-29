package com.example.inclass08;

import java.io.Serializable;

public class ToDoList implements Serializable {

	String objectId;
	String item;
	public ToDoList() {
		
	}
	
	public ToDoList(String objectId, String item) {
		this.objectId = objectId;
		this.item = item;
	}

	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
}
