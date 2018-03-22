导入，一般结合springMVC使用的话，需要配置：
 <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="defaultEncoding" value="utf-8"/>
        <property name="maxUploadSize" value="10485760000"/>
        <property name="maxInMemorySize" value="40960"/>
 </bean>
 
 然后在你的controller的方法里形参用MultipartFile来接收：()
@RequestParam(value = "uploadPhoneFile", required = false) MultipartFile uploadPhoneFile,
@RequestParam(value = "uploadMessageFile", required = false) MultipartFile uploadMessageFile, 
HttpServletRequest httpServletRequest
 
 当然也可以从MultipartHttpServletRequest里面去获取，如：
 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
 // 获得文件：   
 MultipartFile file = multipartRequest.getFile(" file ");  