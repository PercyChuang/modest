package com.modest.util;

public class MobileUtil {
	/**
	 * 将手机中间四位用*号替换
	 * @param mobile
	 * @return
	 */
	public static String fuzzyMobile(String mobile){
		if(mobile != null && mobile.length() == 11){
			StringBuilder fuzzy = new StringBuilder();
			for(int i = 0; i < mobile.length(); i++){
				if(i > 2 && i < 7){
					fuzzy.append("*");
				}else{
					fuzzy.append(mobile.charAt(i));
				}
			}
			return fuzzy.toString();
		}
		return mobile;
	}
	
	/**  
	 * 是否合法手机号码
	 * @param mobile
	 * @return 
	 */
	public static boolean isValidMobile(String mobile){
		if(mobile != null && mobile.length() == 11){
			for(int i = 0 ; i < 11; i++){
				if(mobile.charAt(i) < '0' || mobile.charAt(i) > '9'){
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
