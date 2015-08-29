/**
 * Homework 2 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.homework2;

import java.io.Serializable;

public class Task implements Serializable{

	private static final long serialVersionUID = 6830442380188730706L;
	private String title;
	private String day;
	private String month;
	private String year;
	private String hour;
	private String minutes;
	private String amPm;
	private String priority;
	private String id;
	
	public Task() {
		super();
	}

	public Task(String title, String day, String month, String year, String hour, String minutes, String amPm, String priority) {
		super();
		this.title = title;
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minutes = minutes;
		this.amPm = amPm;
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getAmPm() {
		return amPm;
	}

	public void setAmPm(String amPm) {
		this.amPm = amPm;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTimeText(){
		String timeString, minuteS;
		int hourI = Integer.parseInt(hour), minuteI = Integer.parseInt(minutes);
		if (hourI > 12) {
			hourI = hourI - 12;
		} else if (hourI == 0) {
			hourI = 12;
		} else if (hourI == 12) {
		}
		if (minuteI < 10){
			minuteS = "0" + minuteI;
		}else{
			minuteS = minuteI + "";
		}
		timeString = hourI + ":" + minuteS + " " + amPm;
		
		return timeString;
	}

}
