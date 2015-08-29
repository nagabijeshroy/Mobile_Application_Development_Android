package com.example.to_dolist;

import java.io.Serializable;

public class Task implements Serializable {
	private String title, priority;
	private int minute, hour, day, month, year;
	public Task(String title, String priority, int minute, int hour, int day,
			int month, int year) {
		super();
		this.title = title;
		this.priority = priority;
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Task [title=" + title + ", priority=" + priority + ", minute="
				+ minute + ", hour=" + hour + ", day=" + day + ", month="
				+ month + ", year=" + year + "]";
	}

	public String timeString(){
		String amPM;
		String minuteString;
		//correct for am/pm
		if (hour > 11) {
			if (hour == 12){
				
			}else{
				hour = hour - 12;
				
			}
			amPM = "PM";
		} else {
			//correct for hour zero describing 12AM
			if (hour == 0) {
				hour = 12;
			}
			amPM = "AM";
		}
		//correct for leading zero not displaying on small minutes
		if (minute < 10){
			minuteString = "0" + minute;
		}else{
			minuteString = minute + "";
		}
		return hour + " : " + minuteString + " " + amPM;
	
	}
	public String dateString(){
		
		return (month + 1) + " / " + day + " / "
					+ year;
	}
}