# **SpringBoot** 与 **SpringSecurity** 整合

### 一、各依赖版本

| 版本依赖                         | 版本号        | 说明                                                         |
| -------------------------------- | :------------ | ------------------------------------------------------------ |
| JDK                              | 1.8           |                                                              |
| SpringBoot                       | 2.1.16        |                                                              |
| Druid                            | 1.1.21        |                                                              |
| Mybatis                          | 2.1.3         |                                                              |
| Mybatis-typehandlers-jsr310      | 1.0.2         | 解决Mybatis 对 Java8时间类型LocalDate的支持MyBatis版本如果低于3.4.0的话需要另外配置 |
| thymeleaf-extras-springsecurity5 | 3.0.4.RELEASE |                                                              |
| Fastjson                         | 1.2.62        |                                                              |
| Commons-pool2                    | 2.4.2         | SpringBoot2.0后集成Redis需要引入commons-pool2                |

### 二、整合步骤

1. 引入依赖

   ```xml
   <!-- 1、引入 SpringBoot 与 web 依赖 -->
   <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.1.16.RELEASE</version>
           <relativePath/>
   </parent>
   <dependency>
        <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   
   <!-- 2、引入 SpringSecurity 依赖 -->
   <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   
   <!-- 3、引入 Mybatis 相关依赖 -->
   <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
   </dependency>
   <!-- 解决 Mybatis 对 Java8 时间类型 LocalDate 的支持, MyBatis版本如果低于3.4.0的话需要另外配置这里不做过多描述(具体度娘) -->
   <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-typehandlers-jsr310</artifactId>
               <version>1.0.2</version>
   </dependency>
   
   <!-- 4、引入 Mysql 连接依赖 -->
   <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <scope>runtime</scope>
   </dependency>
   
   <!-- 5、引入 Druid 数据连接池依赖 -->
   <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>druid</artifactId>
           <version>1.1.21</version>
   </dependency>
   
   <!-- 6、引入 SpringSecurity 与 Thymeleaf 依赖 -->
   <dependency>
           <groupId>org.thymeleaf.extras</groupId>
           <artifactId>thymeleaf-extras-springsecurity5</artifactId>
           <version>3.0.4.RELEASE</version>
   </dependency>
   ```
   
   完整[pom.xml](.\pom.xml)
   
2. Yml配置

   ```yml
   #端口号配置
   server:
     port: 8090
      servlet: #session 过期时间配置
       session:
         timeout: 10s #此处10s不会真的生效，原因：SpringBoot 已对 session 做了限制 当配置时间小于1分钟时则默认是一分钟，也就是说必须大于1分钟才会生效
         cookie: #cookie保护机制
           http-only: true #禁止js 脚本访问cookie
           secure: false 	#如为true 仅通过https连接发送cookie,http无法携带cookie
   
   #Mysql与Druid配置
   spring:
     datasource:
       username: root
       password: 543216
       type: com.alibaba.druid.pool.DruidDataSource
       driver-class-name: com.mysql.cj.jdbc.Driver
       #Mysql配置 使用Druid作为连接池
       # useTimezone=true&serverTimezone=Asia/Shanghai 解决数据库插入时间差八小时
       url: jdbc:mysql://localhost:3306/quicklearn_sys?useUnicode=true&characterEncoding=UTF8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useTimezone=true&serverTimezone=Asia/Shanghai
       druid:
         # 连接池的配置信息
         # 初始化大小，最小，最大
         initial-size: 5
         min-idle: 5
         maxActive: 20
         # 配置获取连接等待超时的时间
         maxWait: 60000
         # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
         timeBetweenEvictionRunsMillis: 60000
         # 配置一个连接在池中最小生存的时间，单位是毫秒
         minEvictableIdleTimeMillis: 300000
         validationQuery: SELECT 1
         testWhileIdle: true
         testOnBorrow: false
         testOnReturn: false
         # 打开PSCache，并且指定每个连接上PSCache的大小
         poolPreparedStatements: true
         maxPoolPreparedStatementPerConnectionSize: 20
         # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
         filters: stat,wall,slf4j
         # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
         connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
         # 配置DruidStatFilter
         web-stat-filter:
           enabled: true
           url-pattern: "/*"
           exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
         # 配置DruidStatViewServlet
         stat-view-servlet:
           url-pattern: "/druid/*"
           # IP白名单(没有配置或者为空，则允许所有访问)
           allow: 127.0.0.1,192.168.163.1
           # IP黑名单 (存在共同时，deny优先于allow)
           deny: 192.168.1.73
           #  禁用HTML页面上的“Reset All”功能
           reset-enable: false
           # 登录名
           login-username: admin
           # 登录密码
           login-password: 123456
      	#thymeleaf 配置
   	thymeleaf:
           cache: false
           prefix: classpath:/templates/
           suffix: .html
           encoding: UTF-8
           servlet:
             content-type: text/html
             
   #MyBatis配置
   mybatis:
     type-aliases-package: com.learn.es.pojo   #别名配置
     
   #sql打印
   logging:
     level:
       com.learn.es.mapper: debug
   ```

3. 配置SpringSecurity

   ```java
   /**
    * 要实现SpringSecurity的配置需要编写一个配置类继承自 WebSecurityConfigurerAdapter
    * 重写三个方法：
    * 1、configure(HttpSecurity http) 登录认证的基本配置
    * 2、configure(AuthenticationManagerBuilder auth) 授权的配置
    * 3、configure(WebSecurity web) 静态资源的访问配置
    */
   public class SecurityConfig extends WebSecurityConfigurerAdapter{
       
       @Override
       protected void configure(HttpSecurity http) throws Exception {
           ....
       }
       
        @Override
       protected void configure(AuthenticationManagerBuilder auth) throws Exception {
           ....
       }
       
       @Override
       public void configure(WebSecurity web) throws Exception {
           ....
       }
       
       
   }
   ```

   

