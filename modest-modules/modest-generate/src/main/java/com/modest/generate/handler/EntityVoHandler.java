package com.modest.generate.handler;

import com.modest.generate.config.ConfigFactory;
import com.modest.generate.config.TableConfig;
import com.modest.generate.model.Column;
import com.modest.generate.model.EntityAttributeVo;
import com.modest.generate.model.EntityVo;
import com.modest.generate.model.SqlResultSet;
import com.modest.generate.model.Table;
import com.modest.generate.utils.StringUtils;
import com.modest.generate.utils.JavaTypeResolverUtils.JdbcTypeInformation;

public class EntityVoHandler {
	
	private static TableConfig tableConfig = ConfigFactory.config.getTable();
	
	public static EntityVo createVo(SqlResultSet resultSet){
		String baseName = resultSet.getName();
		if(StringUtils.isNotBlank(tableConfig.getSymbol())){
			baseName = resultSet.getName().replace(tableConfig.getSymbol(),"");
		}
		if(StringUtils.isNotBlank(tableConfig.getTablePri())){
			String pri = tableConfig.getTablePri();
			if(baseName.indexOf(pri)!=-1){
				baseName = baseName.substring(baseName.indexOf(pri)+pri.length());
			}
		}
		baseName = StringUtils.upperCaseFirstChar(StringUtils.underlineToCamel(baseName));
		
		EntityVo vo = new EntityVo();
		vo.setName(baseName);
		vo.setAliasName(StringUtils.lowerCaseFirstChar(baseName));
		for(Column column:resultSet.getColumns()){
			EntityAttributeVo attribute = convertColumn(column);
			vo.getAttributes().add(attribute);
		}
		vo.setTable(resultSet);
		return vo;
	}
	public static EntityVo createVo(Table table){
		String baseName = table.getName();
		if(StringUtils.isNotBlank(tableConfig.getSymbol())){
			baseName = table.getName().replace(tableConfig.getSymbol(),"");
		}
		if(StringUtils.isNotBlank(tableConfig.getTablePri())){
			String pri = tableConfig.getTablePri();
			if(baseName.indexOf(pri)!=-1){
				baseName = baseName.substring(baseName.indexOf(pri)+pri.length());
			}
		}
		baseName = StringUtils.upperCaseFirstChar(StringUtils.underlineToCamel(baseName.toLowerCase()));
		EntityVo vo = new EntityVo();
		vo.setName(baseName);
		vo.setAliasName(StringUtils.lowerCaseFirstChar(baseName));
		for(Column column:table.getColumns()){
			EntityAttributeVo attribute = convertColumn(column);
			attribute.setColumn(column);
			vo.getAttributes().add(attribute);
		}
		EntityAttributeVo primaryKey = convertColumn(table.getPrimaryKey());
		vo.setPrimaryKey(primaryKey);
		vo.setTable(table);
		return vo;
	}
	
	private static EntityAttributeVo convertColumn (Column column){
		String name = column.getColumnName();
		if(StringUtils.isNotBlank(tableConfig.getColumnPri())&&name.indexOf(tableConfig.getColumnPri())==0){
			int length = tableConfig.getColumnPri().length();
			name = name.substring(length);
		}
		name = StringUtils.lowerCaseFirstChar(StringUtils.underlineToCamel(name));
		JdbcTypeInformation type = column.getType();
		String comment = column.getColumnComment();
		EntityAttributeVo vo = new EntityAttributeVo();
		String packagePath = type.getJavaPackage();
		String className = type.getJavaShortName();
		String fullName = type.getJavaFullName()==null?"":type.getJavaFullName();
		vo.setAttributeName(name);
		vo.setComment(comment);
		vo.setStrPackage(packagePath);
		vo.setShortName(className);
		vo.setFullName(fullName);
		vo.setAliasName(StringUtils.lowerCaseFirstChar(name));
		vo.setColumn(column);
		return vo;
	}
	
}
