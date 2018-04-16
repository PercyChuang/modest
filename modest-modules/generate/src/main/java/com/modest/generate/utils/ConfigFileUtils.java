package com.modest.generate.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.modest.generate.config.Config;

public class ConfigFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(ConfigFileUtils.class);

	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public static Map<String,Object> getSaveMap(Map<String,Object> save,String key){
		 String[] keys = key.split("\\."); //key后半部份的小数据。appender.entity.name
		 Map ret = save;
		 if(keys.length>0){
			 for(int i=0;i<keys.length-1;i++){  //for除最后一个以外的结果 appender entity
				 String strKey = keys[i];
				 Map tmpMap = (Map)ret.get(strKey); //到map当中去获取值
				 if(tmpMap==null){
					 tmpMap = new HashMap<String,Object>();
					 ret.put(strKey, tmpMap);    //把值put到map即：{appender:{}}
				 }
				 ret = tmpMap;
			 } 
		 }
		 return ret;
	 }
	
	 
	 public static Config getConfig(String configFile){
		InputStream is = RefectUtils.class.getClassLoader().getResourceAsStream(configFile);
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
	    Properties conf = new Properties();
		try {
			conf.load(bf);
			Map<String,Object> map = new HashMap<String,Object>();
			for (Object key : conf.keySet()) {
				String strKey = key.toString();
				String strValue = (String)conf.get(key);
				strValue = StringUtils.substVars(strValue,conf); //填充值
				if(strKey.indexOf("generate.")==0){
					String str = StringUtils.removePri(strKey,"generate."); //得到key的后单部份。
					String[] ss = str.split("\\."); //分成小数组 //key后半部份的小数据。appender.entity.name
					if(ss.length==1){
						map.put(ss[0],strValue);
					}else{
						Map<String,Object> save = getSaveMap(map,str);
						save.put(ss[ss.length-1],strValue); //name:{}
					}
				}
			}
			return JSONObject.parseObject(JSONObject.toJSONString(map),Config.class);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				if (bf != null) {
					bf.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}
		return null;
	 }

	
}
