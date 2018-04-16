package com.modest.generate.appender;

import java.io.File;

import com.modest.generate.config.AppenderConfig;
import com.modest.generate.model.FileVo;
import com.modest.generate.model.JavaVo;
import com.modest.generate.utils.StringUtils;

public class JavaFileAppender implements BaseAppender{

	@Override
	public FileVo doInvoke(AppenderConfig appender) {
		JavaVo javaVo = new JavaVo();
		String fullName = appender.getName();
		String shortName = fullName.substring(fullName.lastIndexOf(".")+1);
		String strPackage = fullName.substring(0,fullName.lastIndexOf("."));
		String aliasName = StringUtils.lowerCaseFirstChar(shortName);
		String packagePath = strPackage.replace(".",File.separator);
		
		javaVo.setFileName(shortName+".java");
		javaVo.setFilePath(appender.getPath()+File.separator+packagePath);
		javaVo.setTemplateFile(appender.getTemplate());
		
		javaVo.setAliasName(aliasName);
		javaVo.setFullName(fullName);
		javaVo.setShortName(shortName);
		javaVo.setStrPackage(strPackage);
		return javaVo;
	}

}
