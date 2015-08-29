/*
 * Naga Bijesh Roy Raya
 * Shyam Mohan Raman
 */

package com.example.homework5;

import java.io.Serializable;

public class Application implements Serializable {

	String appTitle, developerName, url, smallPhotoUrl, largePhotoUrl;
	double price;
	int id;
	boolean isFavorite,isShared;
	String sharedWith;

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public boolean isShared() {
		return isShared;
	}

	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}

	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}

	public Application(String appTitle, String developerName, String url, String smallPhotoUrl, String largePhotoUrl, double price, int id) {
		super();
		this.appTitle = appTitle;
		this.developerName = developerName;
		this.url = url;
		this.smallPhotoUrl = smallPhotoUrl;
		this.largePhotoUrl = largePhotoUrl;
		this.price = price;
		this.id = id;
	}

	public Application() {
		// TODO Auto-generated constructor stub
	}

	public String getAppTitle() {
		return appTitle;
	}
	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}

	public void setSmallPhotoUrl(String smallPhotoUrl) {
		this.smallPhotoUrl = smallPhotoUrl;
	}

	public String getLargePhotoUrl() {
		return largePhotoUrl;
	}

	public void setLargePhotoUrl(String largePhotoUrl) {
		this.largePhotoUrl = largePhotoUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
