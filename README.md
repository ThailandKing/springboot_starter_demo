---
title: SpringBoot自定义starter
date: 2020-03-06 10:35:10
tags: [springboot]
---

## 一、Spring Boot Starter

- 一个完整的Spring Boot Starter可能包含以下组件 ：
- **autoconfigure**模块：包含自动配置的代码 
- **starter**模块：提供对**autoconfigure**模块的依赖，以及一些其它的依赖 
- 简而言之，starter应该提供使用该库所需的一切 

<!-- more -->

## 二、自定义Starter

### 1、创建Maven工程

- 导入依赖：starter autoconfigure模块的依赖、自定义实现功能所需的依赖

```xml
<dependencies>
    <!--starter autoconfigure模块的依赖-->
    <!--starter-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <!--自动配置-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>

    <!--配置解析-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>

    <!--提供对autoconfigure模块的依赖，以及一些其它的依赖-->
    <!--web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <!--fastjson-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.28</version>
    </dependency>

    <!--aop-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```

### 2、创建配置实体

```java
@ConfigurationProperties("my-log")
@Data
public class MyLogProperty {
    //是否开启
    private boolean enabled;
    //拥有者，特殊说明
    private String owner;
}
```

### 3、实现功能逻辑

```java
@Aspect
@Component
@Slf4j
public class LogInsertAspect {

    private final MyLogProperty myLogProperty;

    public LogInsertAspect(MyLogProperty myLogProperty) {
        this.myLogProperty = myLogProperty;
    }

    @Pointcut("@annotation(com.it.shw.hello.annotation.MyLog)")
    public void logInsert() {

    }

    @AfterReturning("logInsert()")
    public void doAfter(JoinPoint joinPoint) {
        // 判断是否启用
        if (myLogProperty != null && myLogProperty.isEnabled()) {
            // 写入日志
            log.info("自定义starter。。。。");
        }
    }
}
```

### 4、创建配置类

- 实例化配置实体类
- 实例化功能实现类

```java
@Configuration
@EnableConfigurationProperties(MyLogProperty.class)
public class MyLogAutoConfiguration {

    private final MyLogProperty myLogProperty;

    public MyLogAutoConfiguration(MyLogProperty myLogProperty) {
        this.myLogProperty = myLogProperty;
    }

    @Bean
    public LogInsertAspect logInsertAspect() {
        return new LogInsertAspect(myLogProperty);
    }
}
```

### 5、配置启动扫描

- resources目录下创建META-INF文件夹
- spring.factories

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.it.shw.hello.config.MyLogAutoConfiguration
```

### 6、安装到本地仓库

```java
mvn clean install
```

## 三、使用

### 1、创建Maven工程

- 导入自定义starter依赖

```xml
<dependencies>
    <!--引入mylog模块-->
    <dependency>
        <groupId>com.it.shw</groupId>
        <artifactId>hello-spring-boot-starter-autoconfigure</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### 2、添加配置

```properties
#ev
spring.profiles.active=dev
#port
server.port=9024
#my-log
my-log.enabled=true
my-log.owner=shw
```

### 3、测试使用

```java
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @MyLog(desc = "测试没有参数日志记录")
    @GetMapping(value = "/param/no")
    public String testNoParams() {
        return "ok";
    }

    @MyLog(desc = "测试有RequestBody参数日志记录")
    @PostMapping(value = "/param/body")
    public Student testWithParams(@RequestBody Student student) {
        return student;
    }

    @MyLog(desc = "测试有PathVariable参数日志记录")
    @GetMapping(value = "/param/{id}")
    public Long testWithParams(@PathVariable Long id) {
        return id;
    }
}
```

### 4、效果展示

- 无参数日志记录

```java
2020-03-06 16:10:26.511  INFO 9908 --- [nio-9024-exec-1] com.it.shw.hello.aspect.LogInsertAspect  : 
标识：shw
描述：测试没有参数日志记录
方法: testNoParams
参数: null
调用者：admin
时间：2020-03-06 04:10:26
```

- RequestBody参数日志记录

```java
2020-03-06 16:10:34.031  INFO 9908 --- [nio-9024-exec-2] com.it.shw.hello.aspect.LogInsertAspect  : 
标识：shw
描述：测试有RequestBody参数日志记录
方法: testWithParams
参数: [{"id":1,"name":"wgt","pass":"wgt123456"}]
调用者：admin
时间：2020-03-06 04:10:34
```

- PathVariable参数日志记录

```java
2020-03-06 16:10:36.496  INFO 9908 --- [nio-9024-exec-3] com.it.shw.hello.aspect.LogInsertAspect  : 
标识：shw
描述：测试有PathVariable参数日志记录
方法: testWithParams
参数: [1]
调用者：admin
时间：2020-03-06 04:10:36
```

## 源码链接

- https://github.com/ThailandKing/springboot_starter_demo.git

