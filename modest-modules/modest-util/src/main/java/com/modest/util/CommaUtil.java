package com.modest.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * 逗号分隔的工具
 */
public class CommaUtil {
		
	/**  
	 * @Title 逗号分隔数据转数组
	 * @param commaStr 逗号分隔数据
	 * @return 若输入commaStr为null则返回null
	 */
	public static String[] commaToArray(String commaStr){
		if(commaStr != null){
			return commaStr.split(",");
		}
		return null;
	}
	
	/**  
	 * @Title: 逗号分隔数据转List
	 * @param commaStr 逗号分隔数据
	 * @return 若输入commaStr为null则返回null
	 */
	public static List<String> commaToList(String commaStr){
		if(commaStr != null){
			String[] strArr = commaStr.split(",");
			if(strArr != null){
				List<String> list = new ArrayList<String>();
				for(String str : strArr){
					list.add(str);
				}
				return list;
			}
		}
		
		return null;
	}

	/**  
	 * @Title: List转逗号分隔数据(尾部无逗号)
	 * @Description:
	 * @param list
	 * @return 若输入参数list为null,则返回null
	 * 示例：
	 * List<String> list = new ArrayList<String>(); <br/>
	 * list.add("1"); <br/> 
	 * list.add("2"); <br/>
	 * list.add("3"); <br/>
	 * String commaStr = listToComma(list); // "1,2,3"
	 * 
	 */
	public static String listToComma(List<String> list){
		if(list != null){
			StringBuilder commaStr = new StringBuilder();
			for(int i = 0; i < list.size(); i++){
				commaStr.append(list.get(i));
				if(i < list.size() - 1){
					commaStr.append(",");
				}
			}
			return commaStr.toString();
		}
		
		return null;
	}

	/**  
	 * @Title: 逗号分隔数据去重
	 * @param commaStr 逗号分隔数据
	 * @return 若输入commaStr为null则返回null
	 * @input 1,2,3,3
	 * @output 1,2,3
	 */
	public static String removeRepeated(String commaStr){
		if(commaStr != null){
			List<String> list = new ArrayList<String>(new LinkedHashSet<String>(commaToList(commaStr)));
			return listToComma(list);
		}
		return null;
	}

	/**  
	 * @Title: 逗号分隔数据去重
	 * @param commaStr 逗号分隔数据
	 * @return 若输入commaStr为null则返回null
	 * @input ,1,2,
	 * @output 1,2
	 */
	public static String trim(String commaStr){
		if(commaStr != null){
			int startPos = 0;
			for(int i = 0; i < commaStr.length(); i++){
				if(commaStr.charAt(i) == ','){
					startPos++;
				}else{
					break;
				}
			}
			
			int endPos = commaStr.length();
			for(int j = commaStr.length() - 1; j >= 0; j--){
				if(commaStr.charAt(j) == ','){
					endPos--;
				}else{
					break;
				}
			}
			
			if(endPos > startPos){
				return commaStr.substring(startPos, endPos);
			}else{
				return "";
			}
		}
		return null;
	}	
	
	/**  
	 * @Title: 逗号分隔数据是否包含val
	 * @param commaStr 逗号分隔数据
	 * @param val 待检测数据
	 * @return 若输入commaStr为null则返回null
	 * @input ("1,2","1")=>true;("1,2","3")=>false
	 * @output true,false
	 */
	public static boolean contains(String commaStr, String val){
		List<String> list = commaToList(commaStr);
		if(list != null && list.size() > 0){
			return list.contains(val);
		}
		return false;
	}
	
	public static void main(String[] args) {
		String commaStr = "1,2,3";
		System.out.println(commaToArray(commaStr));
		System.out.println(commaToList(commaStr));
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		System.out.println(listToComma(list));
		
		commaStr = "1,2,3,3,4";
		System.out.println("去重:" + commaStr + "=>" + removeRepeated(commaStr));
		
		commaStr = ",,1,2,,";
		System.out.println("trim:" + commaStr + "=>" + trim(commaStr));
		
		commaStr = ",,";
		System.out.println("trim:" + commaStr + "=>" + trim(commaStr));
		
		commaStr = "1,2";
		System.out.println("contains:" + commaStr + "=>" + contains(commaStr, "1"));
		
		commaStr = "1,2";
		System.out.println("contains:" + commaStr + "=>" + contains(commaStr, "3"));
		
		commaStr = "1,2";
		System.out.println("contains:" + commaStr + "=>" + contains(commaStr, null));
		
		commaStr = null;
		System.out.println("contains:" + commaStr + "=>" + contains(commaStr, null));
	}
}
