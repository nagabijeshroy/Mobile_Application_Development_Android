package com.mad.inclass03;

import java.io.Serializable;

public class Student implements Serializable{
	private String name,emailAddress,programmingLanguage;
	private boolean searchable;
	private int mood;
	public Student(String name, String emailAddress,
			String programmingLanguage, boolean searchable, int mood) {
		super();
		this.name = name;
		this.emailAddress = emailAddress;
		this.programmingLanguage = programmingLanguage;
		this.searchable = searchable;
		this.mood = mood;
	}
	public Student() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getProgrammingLanguage() {
		return programmingLanguage;
	}
	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public int getMood() {
		return mood;
	}
	public void setMood(int mood) {
		this.mood = mood;
	}
	
}
