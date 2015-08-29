package com.mad.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
	static final String DB_NAME = "myphotos.db";
	static final int DB_VERSION = 2;
	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("testtable","created");
		PhotosTable.onCreate(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		PhotosTable.onUpgrade(db, oldVersion, newVersion);		
	}
	
}
