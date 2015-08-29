package com.mad.inclass07;

public class PhotoList {
	private long photoID;
	private String photoName, photoURL, ownerName;
	public PhotoList( long photoId, String photoName, String photoURL, String ownerName) {
		super();
		this.photoID = photoId;
		this.photoName = photoName;
		this.photoURL = photoURL;
		this.ownerName = ownerName;
	}
	public PhotoList(){
		
	}
	public long getPhotoID() {
		return photoID;
	}
	public void setPhotoID(long photoID) {
		this.photoID = photoID;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	@Override
	public String toString() {
		return "PhotoList [photoID=" + photoID + ", photoName=" + photoName + ", photoURL=" + photoURL + ", ownerName=" + ownerName + "]";
	}
	
	
}
