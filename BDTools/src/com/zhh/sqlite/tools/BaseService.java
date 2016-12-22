package com.zhh.sqlite.tools;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class BaseService {
		/** 插入一个表（实体类）的数据  */
	public static <T> T insert(T entity) {
		Class<?> clazz = entity.getClass();
		String tableName = clazz.getAnnotation(Table.class).name();
		String id = clazz.getAnnotation(Id.class).name();
		Object idValue = ReflectUtil.getValue(entity, id);
		Field[] fieldArray = clazz.getDeclaredFields();
		
		ContentValues content = new ContentValues();
		String fieldName;
		Field field;
		Object value;
		for(int i=0;i<fieldArray.length;i++) {
			field = fieldArray[i];
			fieldName = field.getName();
			if(fieldName.equals(id)) {
				if(idValue != null) {
					//TODO		此处转Long型已改，之前为(Long)idValue
					content.put(id, Long.valueOf((String) idValue));
				}
			} else {
				value = ReflectUtil.getValue(entity, fieldName);
				if(value instanceof String) {
					content.put(fieldName, (String)value);
				} else if(value instanceof Integer) {
					content.put(fieldName, (Integer)value);
				} else if(value instanceof Long) {
					content.put(fieldName, (Long)value);
				} else if(value instanceof Double) {
					content.put(fieldName, (Double)value);
				}else if(value == null) {
					content.putNull(fieldName);
				}
			}
		}

		Long primaryKeyValue = DataBaseFactory.getDb().insert(tableName, null, content);
		ReflectUtil.setValue(entity, id, primaryKeyValue);
		return entity;
	}
	/** 插入多个表（实体类）的数据  */
	public static <T> List<T> insert(List<T> entitys) {
		if(entitys != null && entitys.size() != 0) {
			List<T> list = new ArrayList<T>();
			for(T entity : entitys) {
				list.add(insert(entity));
			}
			return list;
		}
		return null;
	}
	/** 更新一个表（实体类）的数据  */
	public static <T> Integer update(T entity) {
		Class<?> clazz = entity.getClass();
		String tableName = clazz.getAnnotation(Table.class).name();
		String id = clazz.getAnnotation(Id.class).name();
		Field[] fieldArray = clazz.getDeclaredFields();
		
		ContentValues content = new ContentValues();
		
		String fieldName;
		Field field;
		Object value;
		for(int i=0;i<fieldArray.length;i++) {
			field = fieldArray[i];
			fieldName = field.getName();
			value = ReflectUtil.getValue(entity, fieldName);
			if(!fieldName.equals(id)) {
				if(value instanceof String) {
					content.put(fieldName, (String)value);
				} else if(value instanceof Integer) {
					content.put(fieldName, (Integer)value);
				} else if(value instanceof Long) {
					content.put(fieldName, (Long)value);
				} else if(value instanceof Double) {
					content.put(fieldName, (Double)value);
				} else if(value == null) {
					content.putNull(fieldName);
				}
				
			}
		}
		Object primaryKeyValue = ReflectUtil.getValue(entity, id);
		Integer result = DataBaseFactory.getDb().update(tableName, content, id+"=?", new String[] {primaryKeyValue.toString()});
		return result;
	}
	/** 查询一个表（实体类）的数据   第一个为Id，第二个为表（实体类）  */
	public static <T> T findOne(Serializable id, Class<?> clazz) {
		String tableName = clazz.getAnnotation(Table.class).name();
		String idName = clazz.getAnnotation(Id.class).name();
		Field[] fieldArray = clazz.getDeclaredFields();
		
		String sql = "select * from " + tableName + " where " + idName + " = " + id;
		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, null);
		
		T entity = null;
		
		String fieldName;
		Field field;
		Class<?> type;
		int index;
		if(cursor.moveToNext()) {
			try {
				entity = (T) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for(int i=0;i<fieldArray.length;i++) {
				field = fieldArray[i];
				fieldName = field.getName();
				type = field.getType();
				index = cursor.getColumnIndex(fieldName);
				Object value = null;
				if(type.equals(String.class)) {
					value = cursor.getString(index);
				} else if(type.equals(Integer.class)) {
					value = cursor.getInt(index);
				} else if(type.equals(Long.class)) {
					value = cursor.getLong(index);
				} else if(type.equals(Double.class)) {
					value = cursor.getDouble(index);
				}
				ReflectUtil.setValue(entity, fieldName, value);
			}
		}
		cursor.close();
		return entity;
	}
	/** 查询一个表（实体类）的所有数据  */
	public static <T> List<T> findAll(Class<?> clazz) {
		String tableName = clazz.getAnnotation(Table.class).name();
		Field[] fieldArray = clazz.getDeclaredFields();
		
		String sql = "select * from " + tableName;
		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, null);
		
		String fieldName;
		Field field;
		Class<?> type;
		int index;
		T entity = null;
		List<T> list = new ArrayList<T>();
		while(cursor.moveToNext()) {
			try {
				entity = (T) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for(int i=0;i<fieldArray.length;i++) {
				field = fieldArray[i];
				fieldName = field.getName();
				type = field.getType();
				index = cursor.getColumnIndex(fieldName);
				Object value = null;
				if(type.equals(String.class)) {
					value = cursor.getString(index);
				} else if(type.equals(Integer.class)) {
					value = cursor.getInt(index);
				} else if(type.equals(Long.class)) {
					value = cursor.getLong(index);
				} else if(type.equals(Double.class)) {
					value = cursor.getDouble(index);
				}
				ReflectUtil.setValue(entity, fieldName, value);
			}
			list.add(entity);
		}
		cursor.close();
		return list.size() != 0 ? list : null;
	}
	/** 删除一个表（实体类）的某一个数据数据      第一个为Id，第二个为表明（实体类）  */
	public static Integer deleteOne(Serializable id, Class<?> clazz) {
		String tableName = clazz.getAnnotation(Table.class).name();
		String idName = clazz.getAnnotation(Id.class).name();
		Integer result = DataBaseFactory.getDb().delete(tableName, idName+"=?", new String[] {id.toString()});
		return result;
	}
	/** 删除一个表（实体类）的多个数据数据       第一个为Id的集合，第二个为表名（实体类）  */
	public static void deleteMore(List<Serializable> ids, Class<?> clazz) {
		if(ids != null && ids.size() != 0) {
			for(Serializable id : ids) {
				deleteOne(id, clazz);
			}
		}
	}
	/** 删除一个表（实体类）的数据  */
	public static void deleteAll(Class<?> clazz) {
		String tableName = clazz.getAnnotation(Table.class).name();
		DataBaseFactory.getDb().delete(tableName, null, null);
	}
	/** 查询一个表（实体类）的数据    第一个为sql语句，第二个为sql中占位符的值，第三个为表名（实体类）  */
	public static <T> List<T> queryForEntitys(String sql, String[] paramValues, Class<?> clazz) {
		Field[] fieldArray = clazz.getDeclaredFields();
		
		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, paramValues);
		
		String fieldName;
		Field field;
		Class<?> type;
		int index;
		T entity = null;
		List<T> list = new ArrayList<T>();
		while(cursor.moveToNext()) {
			try {
				entity = (T) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for(int i=0;i<fieldArray.length;i++) {
				field = fieldArray[i];
				fieldName = field.getName();
				type = field.getType();
				index = cursor.getColumnIndex(fieldName);
				if(index != -1) {
					Object value = null;
					if(type.equals(String.class)) {
						value = cursor.getString(index);
					} else if(type.equals(Integer.class)) {
						value = cursor.getInt(index);
					} else if(type.equals(Long.class)) {
						value = cursor.getLong(index);
					} else if(type.equals(Double.class)) {
						value = cursor.getDouble(index);
					}
					ReflectUtil.setValue(entity, fieldName, value);
				}
			}
			list.add(entity);
		}
		cursor.close();
		return list.size() != 0 ? list : null;
	}
	/** 查询一个表（实体类）的一条数据数据    第一个为sql语句，第二个为sql中占位符的值，第三个为表名（实体类） */
	public static <T> T queryForEntity(String sql, String[] paramValues, Class<?> clazz) {
		Field[] fieldArray = clazz.getDeclaredFields();
		
		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, paramValues);
		if(cursor.getCount() > 1) {
			throw new RuntimeException("记录超过一条!");
		}
		
		T entity = null;
		
		String fieldName;
		Field field;
		Class<?> type;
		int index;
		if(cursor.moveToNext()) {
			try {
				entity = (T) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for(int i=0;i<fieldArray.length;i++) {
				field = fieldArray[i];
				fieldName = field.getName();
				type = field.getType();
				index = cursor.getColumnIndex(fieldName);
				if(index != -1) {
					Object value = null;
					if(type.equals(String.class)) {
						value = cursor.getString(index);
					} else if(type.equals(Integer.class)) {
						value = cursor.getInt(index);
					} else if(type.equals(Long.class)) {
						value = cursor.getLong(index);
					} else if(type.equals(Double.class)) {
						value = cursor.getDouble(index);
					}
					ReflectUtil.setValue(entity, fieldName, value);
				}
			}
		}
		cursor.close();
		return entity;
	}
	/** 查询一个表（实体类）的数据    第一个为sql语句，第二个为sql中占位符的值  返回list<map> 适合复杂查询  */
	@SuppressLint("NewApi")
	public static List<Map<String, Object>> queryForMaps(String sql, String[] paramValues) {

		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, paramValues);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map;
		int index; 
		int type;
		while(cursor.moveToNext()) {
			map = new HashMap<String, Object>();
			String[] names = cursor.getColumnNames();
			for(String name : names) {
				index = cursor.getColumnIndex(name);
				type = cursor.getType(index);
				if(type == 0) {//null
					map.put(name, null);
				} else if(type == 1) {
					map.put(name, cursor.getLong(index));
				} else if(type == 2) {
					map.put(name, cursor.getDouble(index));
				} else if(type == 3) {
					map.put(name, cursor.getString(index));
				}
			}
			list.add(map);
		}
		cursor.close();
		return list.size() != 0 ? list : null;
	}
	/** 查询一个表（实体类）的数据    第一个为sql语句，第二个为sql中占位符的值  返回list<map> 适合复杂查询  */
	
	@SuppressLint("NewApi")
	public static Map<String, Object> queryForMap(String sql, String[] paramValues) {
		Cursor cursor = DataBaseFactory.getDb().rawQuery(sql, paramValues);
		if(cursor.getCount() > 1) {
			throw new RuntimeException("记录超过一条!");
		}
		
		Map<String, Object> map = null;
		int index; 
		int type;
		if(cursor.moveToNext()) {
			map = new HashMap<String, Object>();
			String[] names = cursor.getColumnNames();
			for(String name : names) {
				index = cursor.getColumnIndex(name);
				type = cursor.getType(index);
				if(type == 0) {//null
					map.put(name, null);
				} else if(type == 1) {
					map.put(name, cursor.getLong(index));
				} else if(type == 2) {
					map.put(name, cursor.getDouble(index));
				} else if(type == 3) {
					map.put(name, cursor.getString(index));
				}
			}
		}
		cursor.close();
		return map;
	}
}
