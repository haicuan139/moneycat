package com.emperises.monercat.database;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;

public interface DatabaseInterface {

	SQLiteDatabase getDatabase();
	void createTableDatabaseForClass(Class<?> classz);
	void createTableDatabaseForListClass(List<Class<?>> classz);
	void insertDataForObjs(List<DomainObject> objs) throws IllegalArgumentException, IllegalAccessException;
	Cursor queryCursor(Class<?> cls,String[] columns , String where, String[] selectionArgs, String orderBy , String limit);
	Object queryDatabaseForClass(Class<?> classz , String[] columns , String where ,String[] selectionArgs, String orderBy , String limit) throws InstantiationException, IllegalAccessException;
	Object queryDatabaseForClass(Class<?> classz , String where ,String[] selectionArgs, String orderBy , String limit) throws InstantiationException, IllegalAccessException;
	List<Object> queryDatabaseForClassToList(List<Class<?>> classz , String[] columns , String where, String[] selectionArgs, String orderBy,String limit) throws InstantiationException, IllegalAccessException;
	Object queryDatabaseForClass(Class<?> classz , String[] columns , String where ,String[] selectionArgs) throws InstantiationException, IllegalAccessException;
	Object queryDatabaseForClass(Class<?> classz , String where ,String[] selectionArgs) throws InstantiationException, IllegalAccessException;
	void update(String tabName ,Object obj, String whereClause , String[] whereArgs) throws IllegalArgumentException, IllegalAccessException;
}
