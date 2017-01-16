package com.zhh.sqlite.tools;

import java.lang.reflect.Method;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:52
 * 
 */
public class ReflectUtil {
	
	/**
	 * 获取实体类一个成员变量的值
	 */
	public static Object getValue(Object entity, String fieldName) {
		Object value = null;
		try {
			Class<?> clazz = entity.getClass();
			String methodName = "get" + StrUtil.getUpperCharAt(fieldName, 0);
			Method method = clazz.getMethod(methodName);
			value = method.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void setValue(Object entity, String fieldName, Object value) {
		try {
			Class<?> clazz = entity.getClass();
			Class<?> type = clazz.getDeclaredField(fieldName).getType();
			String methodName = "set" + StrUtil.getUpperCharAt(fieldName, 0);
			Method method = clazz.getMethod(methodName, type);
			method.invoke(entity, new Object[] {value});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
