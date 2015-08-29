package com.mad.inclass07;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDataManager {
	Context mContext;
	private DatabaseOpenHelper dbOpenHelper;
	private SQLiteDatabase db;
	private PhotoDAO photoDAO;

	public DatabaseDataManager(Context mContext){
		this.mContext = mContext;
		dbOpenHelper = new DatabaseOpenHelper(this.mContext);
		db = dbOpenHelper.getWritableDatabase();
		photoDAO = new PhotoDAO(db);
	}

	public void close(){
		if(db != null){
			db.close();
		}
	}

	public PhotoDAO getPhotoDAO(){
		return this.photoDAO;
	}
	public long savePhoto(PhotoList note){
		return this.photoDAO.save(note);
	}
	public boolean updatePhoto(PhotoList note){
		return this.photoDAO.update(note);
	}	 	
	public boolean deletePhoto(PhotoList note){
		return this.photoDAO.delete(note);
	}
	public PhotoList getPhoto(long id){
		return this.photoDAO.get(id);
	}	 	
	public List<PhotoList> getAllPhotos(){
		return this.photoDAO.getAll();
	}

}
