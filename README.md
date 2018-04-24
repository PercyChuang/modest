# modest


## 什么是MODEST?
MODEST是将JAVA项目常用技术进行模块化，规范化，统一约束，组件独立维护的一个大整合。<br>
通过它你可快速构建JAVA WEB、微服务或JAVA普通项目；众多技术代码以组件或工具类形式写好，配置即用。<br>

### 1.在pom.xml中引入
 ```
  <parent>
      <groupId>com.modest</groupId>
      <artifactId>modest-parent</artifactId>
      <version>0.0.1</version>
  </parent>
  <dependency>
      <groupId>com.modest</groupId>
      <artifactId>modest-starter</artifactId>
      <version>0.0.1</version>
  </dependency>
   ```
 ### 2.在你的spring配置文件中加入
 ```
 <import resource="classpath*:modest.xml" />
 <bean class="com.modest.core.config.ScannerConfigurer">
		<property name="basePackages">
			<array>
				<value>写你dao所在路经</value>
			</array>
		</property>
	</bean>
  ```
### 3.在你的src/main/resources下创建conf文件 用于存放properties配置文件
*默认在conf下找配置文件，生产环境可配置到任意位置，详见demo*<br>
### 4.在你的src/main/resources下创建sqlmap文件 用于存放mybatis的映射xml文件
### 5.在你的conf文件下创建任意名字配置文件，里面加入jdbc配置。
 ```
  jdbc.write.url=写库地址<br>
  jdbc.write.pool.size.max=50<br>
  jdbc.write.username=xxx<br>
  jdbc.write.password=xxx<br>
  jdbc.write.driverclass=com.mysql.jdbc.Driver<br>
  
  jdbc.read.url=写库地址<br>
  jdbc.read.pool.size.max=50<br>
  jdbc.read.username=xxx<br>
  jdbc.read.password=xxx<br>
  jdbc.read.driverclass=com.mysql.jdbc.Driver<br>
  ```
  
  *上面部份配置结束后，你即可以开始操作数据库写业务代码啦，默认支持读写分离。<br>*
  *详细见simple demo<br>*
  *后续会上传更多组件和文档。<br>*
  

 
