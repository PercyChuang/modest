<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${dao.packagePath}.${dao.className}">
	
	
<!-- Result Map-->
<resultMap id="BaseResultMap" type="${entity.packagePath}.${entity.className}" >

<#list entity.attributes as attribute>
	private ${attribute.className} ${attribute.attributeName}; <#if attribute.comment??>//${attribute.comment}</#if>
</#list>
</resultMap>
       
<!-- $!{tableName} table all fields -->
<sql id="Base_Column_List" >
	 $!{SQL.columnFields}
</sql>
   
   
<!-- 查询条件 -->
<sql id="Example_Where_Clause">
where 1=1
<trim  suffixOverrides="," >
#foreach($item in $!{columnDatas})
	#set($testStr = "condition."+$!item.formatColumnName + " != null and condition." + $!item.formatColumnName + " != ''")
	#if($!item.dataType == 'String')
		#set($testStr = $!testStr + " and condition." + $!item.formatColumnName + " != ''")
 	#end
<if test="$!testStr" >
	    and $!item.columnName =  #{condition.$!item.formatColumnName}
	</if>
#end
</trim>
</sql>
   

<!-- 插入记录 -->
<insert id="add" parameterType="Object" >
#if  ($keyType =='02')
<selectKey resultType="java.lang.Integer" order="AFTER" keyProperty="id">
	SELECT LAST_INSERT_ID()
  </selectKey>
#end
  $!{SQL.insert}
</insert>

<!-- 根据id，修改记录-->  
 <update id="update" parameterType="Object" >
  $!{SQL.update}
 </update>
 
 <!-- 修改记录，只修改只不为空的字段 -->
<update id="updateBySelective" parameterType="Object" >
	$!{SQL.updateSelective}
</update>

<!-- 删除记录 -->
<delete id="delete" parameterType="Object">
	$!{SQL.delete}
</delete>
 
<!-- 根据id查询 ${codeName} -->
<select id="queryById"  resultMap="BaseResultMap" parameterType="Object">
	$!{SQL.selectById}
</select>

<!-- ${codeName} 列表总数-->
<select id="queryByCount" resultType="java.lang.Integer"  parameterType="Object">
	select count(1) from ${tableName} 
	<include refid="Example_Where_Clause"/>
</select>
  	
<!-- 查询${codeName}列表 -->
<select id="queryByList" resultMap="BaseResultMap"  parameterType="Object">
	select 
	<include refid="Base_Column_List"/>
	from ${tableName} 
	<include refid="Example_Where_Clause"/>
	<if test="orderByClause != null and orderByClause != ''" >
       order by  ${orderByClause}
    </if>
    <if test="limitClause != null and limitClause != ''" >
       ${limitClause}
    </if>
</select>
  	
</mapper>