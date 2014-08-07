package com.emperises.monercat.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Logger;

public class DatabaseUtil {
	/**
	 * 根据字节码创建数据库表，与字段
	 * @param classz
	 * @param db
	 */
	public static void createTableDatabaseForClass(Class<?> classz , SQLiteDatabase db){
		List<Class<?>> ls = new ArrayList<Class<?>>();
		ls.add(classz);
		createTableDatabaseForListClass(ls, db);
	}
	/**
	 * 根据字节码列表创建数据库表，与字段
	 * @param classz
	 * @param db
	 */
	public static void createTableDatabaseForListClass(List<Class<?>> classz , SQLiteDatabase db){
		String sql = "CREATE TABLE IF NOT EXISTS ";
		String dataType = " TEXT ";
		String l = "(";
		String r = ");";
		for(Class<?> obj: classz){
			StringBuffer sb = new StringBuffer(sql);
			Field[] fields = obj.getDeclaredFields();
			String tableName = obj.getSimpleName();
			sb.append("'"+tableName+"'");
			sb.append(l);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];//获得字段
				if(!field.getName().equals("serialVersionUID")){
				field.setAccessible(true);//开启权限
				String fieldName = field.getName();//获得字段名称
				sb.append(fieldName+" ");
				sb.append(dataType+",");
				field.setAccessible(false);
				}
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			sb.append(r);
			Logger.i("SQL", "语句:"+sb.toString());
			//执行SQL语句
			db.execSQL(sb.toString());//创建表完成
		}
	}
	/**
	 * 将一个有效的数据对象中的字段值插入到指定的数据表中
	 * 前提是调用createTableDatabaseForListClass方法时,创建该表。
	 * @param objs
	 * @param db
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void insertDataForObjs(List<DomainObject> objs , SQLiteDatabase db) throws IllegalArgumentException, IllegalAccessException{
		for (DomainObject domainObject : objs) {
			if(domainObject != null){
				Field[] fs = domainObject.getClass().getDeclaredFields();
				String tableName = domainObject.getClass().getSimpleName();
				ContentValues values = new ContentValues();
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					if(!f.getName().equals("serialVersionUID")){
						f.setAccessible(true);//开启权限
						Object value = f.get(domainObject);
						if(value != null){
							String fieldName = f.getName();
							values.put(fieldName, (String)value);
						}
						f.setAccessible(false);
					}
				}
				db.insert(tableName, null, values);
			}else{
				Logger.i("DATABASE", "插入数据时，数据对象不能为空");
			}
			
		}
	}
	/**
	 * 获得一个查询Cursor
	 * @param db
	 * @param where
	 * @return
	 */
	public static Cursor queryCursor(Class<?> cls,SQLiteDatabase db , String[] columns , String where, String[] selectionArgs, String orderBy , String limit){
		//获得表名
		String tableName = cls.getSimpleName();
		return db.query(tableName, columns, where+"=?", selectionArgs, null, null, orderBy,limit);
	}
	/**
	 * 通过字节码获得数据库中的数据，返回指定字节码类的实例
	 * 前提数据库表与字段通过createTableDatabaseForListClass创建
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object queryDatabaseForClass(Class<?> classz , SQLiteDatabase db , String[] columns , String where ,String[] selectionArgs, String orderBy , String limit) throws InstantiationException, IllegalAccessException{
		
		List<Class<?>> cls = new ArrayList<Class<?>>();
		cls.add(classz);
		return queryDatabaseForClassToList(cls, db, columns, where, selectionArgs, orderBy, limit);
	}
	/**
	 * 通过字节码获得数据库中的数据，返回指定字节码类的实例
	 * 前提数据库表与字段通过createTableDatabaseForListClass创建
	 * 不要指定字段的操作
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object queryDatabaseForClass(Class<?> classz , SQLiteDatabase db , String where ,String[] selectionArgs, String orderBy , String limit) throws InstantiationException, IllegalAccessException{
		
		List<Class<?>> cls = new ArrayList<Class<?>>();
		cls.add(classz);
		return queryDatabaseForClassToList(cls, db, null, where, selectionArgs, orderBy, limit);
	}
	/**
	 * 通过字节码集合获得数据库中的数据,返回指定字节码实例集合
	 * 前提数据库表与字段通过createTableDatabaseForListClass创建
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static List<Object> queryDatabaseForClassToList(List<Class<?>> classz , SQLiteDatabase db , String[] columns , String where, String[] selectionArgs, String orderBy,String limit) throws InstantiationException, IllegalAccessException{
		List<Object> objs = new ArrayList<Object>(); 
		for (Class<?> cls : classz) {
			Object obj = cls.newInstance();
			Cursor cursor = queryCursor(cls, db, columns, where, selectionArgs, orderBy, limit);
			while (cursor.moveToNext()) {
				Field[] fields = cls.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field f = fields[i];
					if(!f.getName().equals("serialVersionUID")){
						f.setAccessible(true);
						String fieldName = f.getName();
						String value = cursor.getString(cursor.getColumnIndex(fieldName));//获得数据库数据值
						f.set(obj, value);
						f.setAccessible(false);
					}
				}
			}
			cursor.close();
			objs.add(obj);
		}
		return objs;
	}
	
	/**
	 * 无附加条件查询出指定标识的数据
	 * @param classz
	 * @param db
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object queryDatabaseForClass(Class<?> classz , SQLiteDatabase db , String[] columns , String where ,String[] selectionArgs) throws InstantiationException, IllegalAccessException{
		List<Class<?>> cls = new ArrayList<Class<?>>();
		cls.add(classz);
		return queryDatabaseForClassToList(cls, db, columns, where, selectionArgs, null, null);
	}
	/**
	 * 无附加条件查询出指定标识的数据,不需要指定返回某个字段
	 * @param classz
	 * @param db
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object queryDatabaseForClass(Class<?> classz , SQLiteDatabase db , String where ,String[] selectionArgs) throws InstantiationException, IllegalAccessException{
		List<Class<?>> cls = new ArrayList<Class<?>>();
		cls.add(classz);
		return queryDatabaseForClassToList(cls, db, null, where, selectionArgs, null, null);
	}
	
	public static void deleteData(String tabName , SQLiteDatabase db , String whereClause , String[] whereArgs){
		db.delete(tabName, whereClause+"=?", whereArgs);
	}
	
	public static void update(String tabName ,Object obj, SQLiteDatabase db , String whereClause , String[] whereArgs) throws IllegalArgumentException, IllegalAccessException{
		if(obj != null){
			ContentValues  values = new ContentValues();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if(!field.getName().equals("serialVersionUID")){
					field.setAccessible(true);
					Object value =  field.get(obj);
					if(value != null){
						values.put(field.getName(), (String)value);
					}
					field.setAccessible(false);
				}
			}
			db.update(tabName, values, whereClause + "=?", whereArgs);
		}else{
			Logger.i("SQL", "更新数据时,数据对象不能为空");
		}
	}
}
