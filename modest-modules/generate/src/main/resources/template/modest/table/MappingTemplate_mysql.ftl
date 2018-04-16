<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${dao.fullName}">

	<!-- 以下代码由工具生成，请谨慎修改 -->
	<!-- 以下代码由工具生成，请谨慎修改 -->
	<!-- 以下代码由工具生成，请谨慎修改 -->
		
	<!-- Parameter Map -->
	<parameterMap type="${entity.fullName}" id="${entity.shortName}ParameterMap" />	
	
	<!-- Result Map-->
	<resultMap  type="${entity.fullName}" id="${entity.shortName}ResultMap">
		<#list vo.attributes as attribute>
		<result column="${attribute.column.columnName}" property="${attribute.attributeName}" jdbcType="${attribute.column.jdbcType}"/>
		</#list>
	</resultMap>
	
  	<sql id="Base_Column_List">
    	<trim suffixOverrides=",">
	  		<#list vo.attributes as attribute>
	    	${attribute.column.sqlName},
	    	</#list>
    	</trim>
  	</sql>  
  
   	<sql id="Base_Column_List_T">
    	<trim suffixOverrides=",">
	  		<#list vo.attributes as attribute>
	    	t.${attribute.column.sqlName},
	    	</#list>
    	</trim>
  	</sql> 
  	  	
  	<!-- 按主键获取 -->
  	<select id="getByPrimaryKey"  resultMap="${entity.shortName}ResultMap">
    	SELECT
    	<include refid="Base_Column_List" />
    	FROM ${vo.table.sqlName} 
    	WHERE ${vo.table.primaryKey.sqlName} = #${r'{'}0}
  	</select>	

	<!-- 按主键删除 -->
  	<delete id="deleteByPrimaryKey" >
    	DELETE 
    	FROM ${vo.table.sqlName}
    	WHERE ${vo.table.primaryKey.sqlName} = #${r'{'}0}
  	</delete>
  
  	<!-- 单个插入 -->
  	<insert id="add" parameterMap="${entity.shortName}ParameterMap" <#if vo.primaryKey ??>useGeneratedKeys="true" keyProperty="${vo.primaryKey.attributeName}"</#if> >
		INSERT INTO ${vo.table.sqlName}
		<trim prefix="(" suffix=")" suffixOverrides=",">
		<#list vo.attributes as attribute>
			<if test=" null != ${attribute.attributeName} <#if attribute.fullName == "java.lang.String">and ''!= ${attribute.attributeName}  </#if>">
				${attribute.column.sqlName},
			</if>
		</#list>
		</trim>
		
		<trim prefix="VALUES(" suffix=")" suffixOverrides=",">
		<#list vo.attributes as attribute>
			<if test=" null != ${attribute.attributeName} <#if attribute.fullName == "java.lang.String">and ''!= ${attribute.attributeName}  </#if>">
				#${r'{'}${attribute.attributeName}},
			</if>
		</#list>
		</trim>
	</insert>
  
  	<!-- 按条件更新 -->
  	<update id="update" parameterMap="${entity.shortName}ParameterMap">
		UPDATE ${vo.table.sqlName}
		<set>
			<trim suffixOverrides=",">
			<#list vo.attributes as attribute>
		    <#if !attribute.column.primaryKey>
				<if test=" null != ${attribute.attributeName} <#if attribute.fullName == "java.lang.String">and ''!= ${attribute.attributeName}  </#if>">
					${attribute.column.sqlName} = #${r'{'}${attribute.attributeName}},
				</if>
			</#if>
			</#list>
			</trim>
		</set>
		<where>
			<#list vo.attributes as attribute><#if attribute.column.primaryKey> ${attribute.column.sqlName} = #${r'{'}${attribute.attributeName}}</#if></#list>
		</where>
	</update>
  
  	<!-- 按条件更新 -->
   	<update id="updateWithEmpty" parameterMap="${entity.shortName}ParameterMap">
		UPDATE ${vo.table.sqlName}
		<set>
			<trim suffixOverrides=",">
			<#list vo.attributes as attribute>
			<#if !attribute.column.primaryKey>
				${attribute.column.sqlName} = #${r'{'}${attribute.attributeName}},
			</#if>
			</#list>
			</trim>
		</set>
		<where>
			<#list vo.attributes as attribute><#if attribute.column.primaryKey> ${attribute.column.sqlName} = #${r'{'}${attribute.attributeName}}</#if></#list>
		</where>
	</update>

	<!-- 批量删除 -->
  	<insert id="addBatch" parameterType="java.util.List">
		INSERT INTO ${vo.table.sqlName} 
		<trim prefix="(" suffix=")" >
			<include refid="Base_Column_List" />
		</trim>
		VALUES
		<foreach item="item" index="index" collection="list" separator=",">
			<trim prefix="(" suffix=")" suffixOverrides=",">
			<#list vo.attributes as attribute> 
				#${r'{'}item.${attribute.attributeName},jdbcType=${attribute.column.jdbcType}},
			</#list>
			</trim>
		</foreach>
	</insert>
 
 	<!-- 批量更新 -->
	<update id="updateBatch" parameterType="java.util.List">
    	<foreach collection="list" item="item" index="index" open="" close="" separator=";">
        UPDATE ${vo.table.sqlName}
        <set>
        	<trim suffixOverrides=",">
			<#list vo.attributes as attribute>
			<#if !attribute.column.primaryKey>
			<if test="item.${attribute.attributeName}!=null <#if attribute.fullName == "java.lang.String">and ''!= item.${attribute.attributeName}  </#if>">
				${attribute.column.sqlName} = #${r'{'}item.${attribute.attributeName},jdbcType=${attribute.column.jdbcType}},
			</if>
			</#if>
			</#list>
			</trim>
        </set>
        WHERE <#list vo.attributes as attribute><#if attribute.column.primaryKey>${attribute.column.sqlName} = #${r'{'}item.${attribute.attributeName}}</#if></#list>
     	</foreach>
	</update>

	<!-- 批量更新 -->
	<update id="updateBatchWithEmpty" parameterType="java.util.List">
    	<foreach collection="list" item="item" index="index" open="" close="" separator=";">
        UPDATE ${vo.table.sqlName}
        <set>
        	<trim suffixOverrides=",">
			<#list vo.attributes as attribute>
			<#if !attribute.column.primaryKey>
				${attribute.column.sqlName} = #${r'{'}item.${attribute.attributeName},jdbcType=${attribute.column.jdbcType}},
			</#if>
			</#list>
			</trim>
        </set>
        WHERE <#list vo.attributes as attribute><#if attribute.column.primaryKey>${attribute.column.sqlName} = #${r'{'}item.${attribute.attributeName}}</#if></#list>
     	</foreach>
	</update>         
	
	<!-- 批量删除 -->
	<delete id="deleteBatch" parameterType="Object">
		DELETE FROM ${vo.table.sqlName}
		<where>
			<foreach item="item" index="index" collection="array"  open="(" separator="or" close=") ">
			 	<#list vo.attributes as attribute><#if attribute.column.primaryKey> ${attribute.column.sqlName}  = #${r'{'}item}</#if></#list>
			</foreach>
		</where>
	</delete>

 	<sql id="Base_Condition">
		<#list vo.attributes as attribute>
		<if test=" null != ${attribute.attributeName} <#if attribute.fullName == "java.lang.String">and ''!= ${attribute.attributeName}  </#if>">
			AND	T.${attribute.column.sqlName} = #${r'{'}${attribute.attributeName}}
		</if>
		</#list>
 	</sql>
       
	<select id="list" resultMap="${entity.shortName}ResultMap"  >
		SELECT <include refid="Base_Column_List" /> FROM ${vo.table.sqlName} T WHERE 1=1
		<include refid="Base_Condition" />
	</select>

	<select id="query" resultMap="${entity.shortName}ResultMap"  >
		SELECT <include refid="Base_Column_List" /> FROM ${vo.table.sqlName} T WHERE 1=1
		<include refid="Base_Condition" />
	</select>

	<!-- 以上代码由工具生成，请谨慎修改 -->
	<!-- 以上代码由工具生成，请谨慎修改 -->
	<!-- 以上代码由工具生成，请谨慎修改 -->	
	
	<!-- 以下代码由工程师自行添加 -->
	<!-- 以下代码由工程师自行添加 -->
	<!-- 以下代码由工程师自行添加 -->
</mapper>