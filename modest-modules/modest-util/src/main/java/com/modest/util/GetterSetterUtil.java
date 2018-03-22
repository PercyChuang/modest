package com.modest.util;

public class GetterSetterUtil {
	/**
	 * 首字母大写
	 */
	public static String toUpperCaseFirstChar(String s) {
		return Character.toUpperCase(s.charAt(0)) + "" + s.substring(1).toString();
	}
	
	/**
	 * 首字母小写
	 */
	public static String toLowerCaseFirstChar(String s){
		return Character.toLowerCase(s.charAt(0)) + "" + s.substring(1).toString();
	}
	
	/**
	 * 大驼峰命名法(PascalCase)(帕斯卡命名法)
	 */
	public static String upperCamelCase(String underline){
		StringBuilder sb = new StringBuilder();
		String[] slice = underline.split("_");
		for(String s : slice){
			sb.append(toUpperCaseFirstChar(s));
		}
		return sb.toString();
	}
	
	/**
	 * 小驼峰命名法(camelCase)(骆驼命名法)
	 */
	public static String lowerCamelCase(String underline){
		return toLowerCaseFirstChar(upperCamelCase(underline));
	}
	
	/**
	 * 下划线命名法(Hungarian)(匈牙利命名法)
	 */
	public static String lowerUnderline(String camelCase){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < camelCase.length(); i++){
			char c = camelCase.charAt(i);
			if(Character.isUpperCase(c)){
				if(i != 0){
					sb.append("_");
				}
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成GETTER方法名
	 */
	public static String toGetterMethodName(String fieldName){
		return "get" + toUpperCaseFirstChar(fieldName);
	}
	
	/**
	 * 生成SETTER方法
	 */
	public static String toSetterMethodName(String fieldName){
		return "set" + toUpperCaseFirstChar(fieldName);
	}
	
	/**
	 * 提取字段名
	 */
	public static String toFieldName(String getterOrSetterMethodName){
		return toLowerCaseFirstChar(getterOrSetterMethodName.substring(3));
	}
	
	
	public static void main(String[] args) {
		System.out.println(toUpperCaseFirstChar("about"));
		System.out.println(toLowerCaseFirstChar("About"));
		System.out.println(upperCamelCase("error_code"));
		System.out.println(lowerCamelCase("error_code"));
		System.out.println(lowerUnderline("ErrorCode"));
		System.out.println(toGetterMethodName("age"));
		System.out.println(toSetterMethodName("age"));
		System.out.println(toFieldName("getAge"));
		System.out.println(toFieldName("setAge"));
	}
}
