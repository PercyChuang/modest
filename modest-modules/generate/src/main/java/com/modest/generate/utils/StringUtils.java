package com.modest.generate.utils;

import java.util.Map;

public class StringUtils {
	private final static String splitStr = "_";
	private static String DELIM_START = "${";
	private static char   DELIM_STOP  = '}';
	private static int DELIM_START_LEN = 2;
	private static int DELIM_STOP_LEN  = 1;
	
	public static boolean isNotBlank(String str){
		return str!=null&&!"".equals(str.trim());
	}
	
	public static String removePri(String src,String pri){
		if(src.indexOf(pri)==0){
		   return src.substring(src.indexOf(pri)+pri.length());
		}
		return src;
	}
	
	
	public static String upperCaseFirstChar(String str) {
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	public static String lowerCaseFirstChar(String str) {
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
	 public static String camelToUnderline(String param) {
			if (param == null || "".equals(param.trim())) {
				return "";
			}
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (Character.isUpperCase(c)) {
					sb.append(splitStr);
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}

		public static String underlineToCamel(String param) {
			if (param == null || "".equals(param.trim())) {
				return "";
			}
			if(!param.contains(splitStr)){
	   	    	return param; 
	   	    }
			param = param.toLowerCase();
			int len = param.length();
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				char c = param.charAt(i);
				if (c == splitStr.charAt(0)) {
					if (++i < len) {
						sb.append(Character.toUpperCase(param.charAt(i)));
					}
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}
	
	public static String getSetMethodName(String fieldName) {
		return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
	}

	public static String getGetMethodName(String fieldName) {
		return "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
	}
	
	public static String getGetMethodName(String fieldName,Class<?> type) {
		return getGetMethodName(fieldName,type.getName());
	}
	public static String getGetMethodName(String fieldName,String classType) {
		if(Boolean.class.getName().equals(classType)){
			return "is"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		}
		return getGetMethodName(fieldName);
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String substVars(String val, Map props) throws IllegalArgumentException {
	    StringBuffer sbuf = new StringBuffer();
	    int i = 0;
	    int j, k;
	    while(true) {
		      j=val.indexOf(DELIM_START, i); //在字符串中查看${所在的下标
		      if(j == -1) {					 //如果不存在
					if(i==0) { 
					  return val;			 //直接返回
					} else {				 //下面这段代码无用
						  sbuf.append(val.substring(i, val.length()));
						  return sbuf.toString();
					}
		      } else {
					sbuf.append(val.substring(i, j));   //首次添加0 到${位置的字符串
					k = val.indexOf(DELIM_STOP, j);     //从结束下标开始 定位结束标记所在位置
					if(k == -1) {						//找不到，则报错
					  throw new IllegalArgumentException('"'+val+"\" has no closing brace. Opening brace at position " + j+ '.');
					} else {
					  j += DELIM_START_LEN;				//+2 (${)所在位置
					  String key = val.substring(j, k); //取出符号${}中的key
					  Object obj = props.get(key);		//从配置中取出key的值
					  String replacement = obj==null?null:obj.toString();
					  if(replacement != null) {         //对key的值再进行复选择
					    String recursiveReplacement = substVars(replacement, props);
					    sbuf.append(recursiveReplacement);//加到结果中。
					  }else{
						sbuf.append(DELIM_START+key+DELIM_STOP); //如果取不到key的值，放回原val中。
					  }
					  i = k + DELIM_STOP_LEN;  //下一次开始从}位开始。
		 			}
		      }
	     }
	}
}
