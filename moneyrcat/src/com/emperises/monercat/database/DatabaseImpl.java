package com.emperises.monercat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseImpl implements DatabaseInterface {

	private SQLiteDatabase mDb;
	public DatabaseImpl(Context context) {
		DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);
		mDb = mDatabaseHelper.getWritableDatabase();
	}
	
}
