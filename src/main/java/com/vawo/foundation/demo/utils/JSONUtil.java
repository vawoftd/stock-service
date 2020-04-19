package com.vawo.foundation.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 国际化支持 Created by zhangcong on 2018年05月10日
 *
 */
@SuppressWarnings("all")
public class JSONUtil {

	private static final SerializeConfig config;
	static {
		config = new SerializeConfig();
		// config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
		// // 使用和json-lib兼容的日期输出格式
		// config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
		// // 使用和json-lib兼容的日期输出格式

		config.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		config.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		config.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		
		//BigDecimalSerializer  dd = new BigDecimalSerializer ("");
		

	}

	private static final SerializerFeature[] features = { 
			SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
			//SerializerFeature.

	};

	public static final Object parse(String jsonStr){
		return JSON.parse(jsonStr);
	}
	
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config,features);
	}
	
//	public static String toJSONStringKeyFirstCharLowerCase(Object object){
//		NameFilter filter = new IgnoreFirstCharCaseNameFilter();
//    	SerializeWriter out = new SerializeWriter(features);
//    	JSONSerializer serializer = new JSONSerializer(out,config);
//    	serializer.getNameFilters().add(filter);
//    	serializer.write(object);
//    	return out.toString();
//	}

	public static final String toJSONString(Object object, SerializeFilter filter, SerializerFeature... features) {
		return JSON.toJSONString(object, filter, features);
	}

	public static String toJSONStringWithDateFormat(Object object, String dateFormat) {
		return JSON.toJSONStringWithDateFormat(object, dateFormat, features);
	}

	public static JSONObject parseObject(String jsonStr) {

		return JSON.parseObject(jsonStr);
	}

	public static final <T> T parseObject(String jsonStr, Class<T> clazz, Feature... features) {
		return JSON.parseObject(jsonStr, clazz, features);
	}

	public static final <T> T parseObject(String jsonStr, Type clazz, Feature... features) {
		return (T)JSON.parseObject(jsonStr, clazz, features);
	}

	public static JSONArray parseArray(String jsonStr) {

		return JSON.parseArray(jsonStr);
	}

	public static final <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	public static final List<Object> parseArray(String jsonStr, Type[] types) {
		return JSON.parseArray(jsonStr, types);
	}

	public static final <T> T toJavaObject(JSON json, Class<T> clazz) {
		
		return JSON.toJavaObject(json, clazz);
	}
	
	public static void main(String[] args) {
		Map map = new HashMap();
		Double d =null;
		
		map.put("sss", d);
		System.out.println(toJSONString(map));
		System.out.println(toJSONStringWithDateFormat(map, "yyyy-MM-dd"));
		
	}

}
