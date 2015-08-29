package com.mad.inclass07;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotoDAO {
	private SQLiteDatabase db;
	public PhotoDAO(SQLiteDatabase db){
		this.db = db;
	}
	
	public long save(PhotoList photoList){
		ContentValues values = new ContentValues();
		values.put(PhotosTable.COLUMN_ID, photoList.getPhotoID());
		values.put(PhotosTable.COLUMN_PHOTO_NAME, photoList.getPhotoName());
		values.put(PhotosTable.COLUMN_PHOTO_URL, photoList.getPhotoURL());
		values.put(PhotosTable.COLUMN_OWNER_NAME, photoList.getOwnerName());
		return db.insert(PhotosTable.TABLENAME, null, values);
	}
	
	public boolean update(PhotoList photoList){
		ContentValues values = new ContentValues();
		values.put(PhotosTable.COLUMN_PHOTO_NAME, photoList.getPhotoName());
		values.put(PhotosTable.COLUMN_PHOTO_URL, photoList.getPhotoURL());
		values.put(PhotosTable.COLUMN_OWNER_NAME, photoList.getOwnerName());;
		return db.update(PhotosTable.TABLENAME, values, PhotosTable.COLUMN_ID + "=?", new String[]{photoList.getPhotoID()+""}) > 0;
	}
	
	public boolean delete(PhotoList photoList){
		
		return db.delete(PhotosTable.TABLENAME, PhotosTable.COLUMN_ID + "=?", new String[]{photoList.getPhotoID()+""}) > 0;
	}
	
	@SuppressLint("NewApi")
	public PhotoList get(long id){
		PhotoList note = null;
		Cursor c = db.query(true, PhotosTable.TABLENAME, new String[]{PhotosTable.COLUMN_ID,PhotosTable.COLUMN_PHOTO_NAME,PhotosTable.COLUMN_PHOTO_URL,PhotosTable.COLUMN_OWNER_NAME}, 
				PhotosTable.COLUMN_ID+"=? ", new String[]{id+""}, null, null, null, null, null);
		if(c != null && c.moveToFirst()){
			 note = buildNoteFromCursor(c);
			 if(!c.isClosed()){
				 c.close();
		 	 }	
	 	 }	 	
		
		return note;
	}
	
	public List<PhotoList> getAll(){
		List<PhotoList> notes = new ArrayList<PhotoList>();
		Cursor c = db.query(PhotosTable.TABLENAME, new String[]{PhotosTable.COLUMN_ID,PhotosTable.COLUMN_PHOTO_NAME,PhotosTable.COLUMN_PHOTO_URL,PhotosTable.COLUMN_OWNER_NAME},
				null, null, null, null, null);
		
		if(c != null && c.moveToFirst()){
			do{ 
			PhotoList note = buildNoteFromCursor(c);
			if(note != null){
				 notes.add(note);
			 }	
			}while(c.moveToNext());
			 
			 if(!c.isClosed()){
				 c.close();
		 	 }	
	 	 }	 	
		
		return notes;
	}
	
	private PhotoList buildNoteFromCursor(Cursor c){
		PhotoList photoList = null;
		if(c != null){
			photoList = new PhotoList();
			photoList.setPhotoID(c.getLong(0));
			photoList.setPhotoName(c.getString(1));
			photoList.setPhotoURL(c.getString(2));
			photoList.setOwnerName(c.getString(3));
	 	 }
	 	 return photoList;
	}
}
