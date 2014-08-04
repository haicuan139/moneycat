package com.emperises.monercat.database;

import java.lang.reflect.Field;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.emperises.monercat.domain.DomainObject;
import com.emperises.monercat.utils.Logger;

public class DatabaseUtil {
	/**
	 * 根据类创建数据库表
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
						String fieldName = f.getName();
						values.put(fieldName, (String)value);
						f.setAccessible(false);
					}
				}
				db.insert(tableName, null, values);
			}else{
				Logger.i("DATABASE", "插入数据时，数据对象不能为空");
			}
			
		}
	}
}
