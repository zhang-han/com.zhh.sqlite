package com.zhh.sqlite.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作工具类OO
 * 
 * @author HDahan 2013上午11:29:37
 * 
 */
public class DataBaseFactory extends SQLiteOpenHelper {

	private static final String DBNAME = "sqlite-now-manage.db";
//	private static final int DBVERSION = 1;
	public static SQLiteDatabase db;
//	private Class<?>[] entityClasses = {};d
	private List<Class<?>> entityClasses = new ArrayList<Class<?>>();

	/**
	 * 构造方法，直接初始化一个SQLiteDatabase对象用来操作所有的数据相关方法
	 */
	public DataBaseFactory(Context context,int DBVERSION, List<Class<?>> list) {
		super(context, DBNAME, null, DBVERSION);
		entityClasses.addAll(list);
		db = getWritableDatabase();// getWritableDatabase()可以用于读写，如果getReadableDatabase()就只能进读的操作。
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String fieldName;
		Class<?> type;
		String tableName;
		String id;
		for(Class<?> clazz : entityClasses) {
			String execSql = "CREATE TABLE IF NOT EXISTS ";
			tableName = clazz.getAnnotation(Table.class).name();
			id = clazz.getAnnotation(Id.class).name();
			execSql += tableName + " (" + id + " INTEGER primary key autoincrement,";
			Field[] fieldArray = clazz.getDeclaredFields();
			for(Field field : fieldArray) {
				fieldName = field.getName();
				if(!fieldName.equals(id)) {
					type = field.getType();
					if(type.equals(String.class)) {
						execSql += fieldName + " TEXT,";
					} else if(type.equals(Integer.class) || type.equals(Long.class)) {
						execSql += fieldName + " INTEGER,";
					} else if(type.equals(Double.class)) {
						execSql += fieldName + " DOUBLE,";
					}
				}
			}
			execSql = execSql.substring(0, execSql.length()-1) + ")";
			db.execSQL(execSql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String tableName;
		List<Object> list = new ArrayList<Object>();
		for(Class<?> clazz : entityClasses) {
			tableName = clazz.getAnnotation(Table.class).name();
			list = BaseService.findAll(clazz);
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
			onCreate(db);
			BaseService.insert(list);
		}
	}

	public static SQLiteDatabase getDb() {
		return db;
	}

	public static void closeDB() {
		db.close();
	}

}
