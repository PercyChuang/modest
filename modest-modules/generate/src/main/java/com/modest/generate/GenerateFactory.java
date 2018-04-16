package com.modest.generate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.modest.generate.appender.BaseAppender;
import com.modest.generate.appender.FileAppender;
import com.modest.generate.config.AppenderConfig;
import com.modest.generate.config.ConfigFactory;
import com.modest.generate.db.SqlParseImpl;
import com.modest.generate.db.TableParseImpl;
import com.modest.generate.handler.EntityVoHandler;
import com.modest.generate.model.EntityVo;
import com.modest.generate.model.FileVo;
import com.modest.generate.model.SqlResultSet;
import com.modest.generate.model.Table;
import com.modest.generate.utils.FreemarkUtils;
import com.modest.generate.utils.PropertiesUtils;
import com.modest.generate.utils.StringUtils;


public class GenerateFactory {
	
	
	private static void init(Map<String, Object> save,String type,AppenderConfig appender) throws Exception{
		BaseAppender  appenderHandler = null;
		if(StringUtils.isNotBlank(appender.getHandler())){
			appenderHandler = (BaseAppender) Class.forName(appender.getHandler()).newInstance();
		}else{
			appenderHandler = new FileAppender();
		}
		if(appenderHandler!=null){
			PropertiesUtils.refresh(appender,PropertiesUtils.obj2Map(save));
			FileVo fileVo = appenderHandler.doInvoke(appender);
			save.put(type, fileVo);
		}
	}
	
	private static void init(Map<String, Object> save) throws Exception{
		Map<String, AppenderConfig> src = new HashMap<String, AppenderConfig>();
		src.putAll(ConfigFactory.config.getAppender());
		while(src.size()>0){
			Object[] arr = src.keySet().toArray();
			for(int i=0;i<arr.length;i++){
				String type = arr[i].toString();
				AppenderConfig appender = src.get(type);
				String strDependency = appender.getDependency();
				if(StringUtils.isNotBlank(strDependency)){
					String[] dependencys = strDependency.split(",");
					boolean isCanInit = true;
					for(String dependency:dependencys){
						if(save.get(dependency)==null){
							if(src.get(dependency)==null){
								throw new IllegalArgumentException("dependency:"+dependency+" not exists!");
							}
							isCanInit = false;
						}
					}
					if(isCanInit){
						init(save,type,appender);
						src.remove(type);
					}
				}else{
					init(save,type,appender);
					src.remove(type);
				}
			}
		}
	}
	
	
	public static void codeGenerate(String configFile) throws Exception {
		ConfigFactory.init(configFile);
		if("sql".equals(ConfigFactory.config.getType())){
			String sqlFile =  ConfigFactory.config.getTarget();
			if(sqlFile!=null){
				File file = new File(sqlFile);
				String str = FileUtils.readFileToString(file,"UTF-8");
				Pattern p = Pattern.compile("\\{(.*)\\}=\\{([\\s\\S]*?)\\}");
				Matcher m = p.matcher(str);
				FreemarkUtils.init(ConfigFactory.config.getTemplateDir());
				while (m.find()){
					String voName = m.group(1);
					String sqlStr = m.group(2);
					SqlResultSet resultSet = (new SqlParseImpl()).getSqlResultSet(voName, sqlStr);
					EntityVo vo = EntityVoHandler.createVo(resultSet);
					Map<String, Object> root = new HashMap<String, Object>();
					root.put("sqlStr",sqlStr);
					root.put("vo", vo);
					root.put("generate", ConfigFactory.config);
					init(root);
					FreemarkUtils.WriterPage(root);
					ConfigFactory.reset();
				}
			}
		}else{
			String strTables =  ConfigFactory.config.getTarget();
			if(strTables!=null){
				String[] tableNames = strTables.split(",");
				FreemarkUtils.init(ConfigFactory.config.getTemplateDir());
				for(String tableName:tableNames){
					Table table = (new TableParseImpl()).getTable(tableName);
					EntityVo vo = EntityVoHandler.createVo(table);
					Map<String, Object> root = new HashMap<String, Object>();
					root.put("vo", vo);
					root.put("generate", ConfigFactory.config);
					init(root);
					String jsonStr = JSON.toJSONString(root);
					System.out.println(jsonStr);
					FreemarkUtils.WriterPage(root);
					ConfigFactory.reset();
				}
			}
		}
		
	}
	
	


}