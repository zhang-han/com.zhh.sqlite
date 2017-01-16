package com.zhh.sqlite.tools;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:58
 * 
 */
public class StrUtil {
	/**
	 * 把字符串的指定字母大写
	 */
	public static String getUpperCharAt(String str, int index) {
		String result = null;
		int count = str.length();
		if(count > index) {
			String start = str.substring(0, index);
			String at = (str.charAt(index) + "").toUpperCase();
			String end = str.substring(index+1);
			result = start + at + end;
		}
		return result;
	}
	
	/**
	 * 把字符串中的特殊字符转义符转换回特殊字符
	 */
	public static String specialCharParse(String str)
    {
		if(str != null && str != "") {
			str = str.replace("#bfh#", "%");
	        str = str.replace("#and#", "&");
		}
		return str;
    }
}
