package com.emperises.monercat.database;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;

public class DatabaseImpl implements DatabaseInterface {

	private SQLiteDatabase db;

	public DatabaseImpl(Context context , List<Class<?>> objs) {
		DatabaseHelper mDatabaseHelper = new DatabaseHelper(context, objs);
		this.db = mDatabaseHelper.getWritableDatabase();
	}
	@Override
	public SQLiteDatabase getDatabase() {
		return db;
	}

	@Override
	public void createTableDatabaseForClass(Class<?> classz) {
		DatabaseUtil.createTableDatabaseForClass(classz, db);
	}

	@Override
	public void createTableDatabaseForListClass(List<Class<?>> classz) {
		DatabaseUtil.createTableDatabaseForListClass(classz, db);
	}

	@Override
	public void insertDataForObjs(List<DomainObject> objs)
			throws IllegalArgumentException, IllegalAccessException {
		DatabaseUtil.insertDataForObjs(objs, db);
	}

	@Override
	public Cursor queryCursor(Class<?> cls, String[] columns, String where,
			String[] selectionArgs, String orderBy, String limit) {

		return DatabaseUtil.queryCursor(cls, db, columns, where, selectionArgs,
				orderBy, limit);
	}

	@Override
	public Object queryDatabaseForClass(Class<?> classz, String[] columns,
			String where, String[] selectionArgs, String orderBy, String limit)
			throws InstantiationException, IllegalAccessException {

		return DatabaseUtil.queryDatabaseForClass(classz, db, columns, where,
				selectionArgs, orderBy, limit);
	}

	@Override
	public Object queryDatabaseForClass(Class<?> classz, String where,
			String[] selectionArgs, String orderBy, String limit)
			throws InstantiationException, IllegalAccessException {

		return DatabaseUtil.queryDatabaseForClass(classz, db, where,
				selectionArgs, orderBy, limit);
	}

	@Override
	public List<Object> queryDatabaseForClassToList(List<Class<?>> classz,
			String[] columns, String where, String[] selectionArgs,
			String orderBy, String limit) throws InstantiationException,
			IllegalAccessException {

		return DatabaseUtil.queryDatabaseForClassToList(classz, db, columns,
				where, selectionArgs, orderBy, limit);
	}

	@Override
	public Object queryDatabaseForClass(Class<?> classz, String[] columns,
			String where, String[] selectionArgs)
			throws InstantiationException, IllegalAccessException {

		return DatabaseUtil.queryDatabaseForClass(classz, db, columns, where,
				selectionArgs);
	}

	@Override
	public Object queryDatabaseForClass(Class<?> classz, String where,
			String[] selectionArgs) throws InstantiationException,
			IllegalAccessException {

		return DatabaseUtil.queryDatabaseForClass(classz, db, where,
				selectionArgs);
	}

	@Override
	public void update(String tabName, Object obj, String whereClause,
			String[] whereArgs) throws IllegalArgumentException,
			IllegalAccessException {
		DatabaseUtil.update(tabName, obj, db, whereClause, whereArgs);
	}
}
