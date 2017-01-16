package com.zhh.json.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:23
 * 
 */
public class JsonUtil {
	
	//把JsonArray的字符串转换成List<Map<String, Object>>
	public static List<Map<String, Object>> parseJsonArrayStrToListForMaps(String jsonArrayStr) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			if(jsonArrayStr != null) {
				JSONArray jsonArray = new JSONArray(jsonArrayStr);
				Map<String, Object> map = null;
				for(int j=0;j<jsonArray.length();j++) {
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					map = parseJsonObjectStrToMap(jsonObject.toString());
					if(map != null) {
						list.add(map);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(list.size() == 0) {
			return null;
		}
		return list;
	}
	
	//把JsonObject的字符串转换成Map<String, Object>
	public static Map<String, Object> parseJsonObjectStrToMap(String jsonObjectStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(jsonObjectStr != null) {
				JSONObject jsonObject = new JSONObject(jsonObjectStr);
				for(int j=0;j<jsonObject.length();j++) {
					Iterator<String> iterator = jsonObject.keys();
					while(iterator.hasNext()) {
						String key = iterator.next();
						Object value = jsonObject.get(key);
						map.put(key, value);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(map.size() == 0) {
			return null;
		}
		return map;
	}

	//把List<Map<String, Object>>的字符串转换成JsonArray
	public static String parseListForMapsToJsonArrayStr(List<Map<String, Object>> list) {
		String jsonArrayStr = null;
		if(list != null && list.size() != 0) {
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = null;
			Object value = null;
			for(Map<String, Object> map : list) {
				jsonObject = new JSONObject();
				Set<String> set = map.keySet();
				for(String key : set) {
					value = map.get(key);
					if(value != null) {
						try {
							jsonObject.put(key, value.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				if(jsonObject.length() != 0) {
					jsonArray.put(jsonObject);
				}
			}
			jsonArrayStr = jsonArray.toString();
		}
		
		return jsonArrayStr;
	}
	
	//把Map<String, Object>的字符串转换成JsonObject
	public static String parseMapToJsonObjectStr(Map<String, Object> map) {
		String result = null;
		if(map != null && map.keySet().size() != 0) {
			Set<String> set = map.keySet();
			JSONObject jsonObject = new JSONObject();
			Object value = null;
			for(String key : set) {
				value = map.get(key);
				if(value != null) {
					try {
						jsonObject.put(key, value.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			if(jsonObject.length() != 0) {
				result = jsonObject.toString();
			}
		}
		return result;
	}
}
