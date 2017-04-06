# KITCHEN

## 简介

通用的Java后端开发基础框架 ，一个支持业务量由少到多 开发团队逐步扩大 组织形态多样话的一个框架 这是框架最大的特色  （即支持初创团队的快速开发 又支持产品达到一定规模时的分布式部署 且中间切换过程平滑）  
这只是一个基础架构，只能作为一个项目或产品的起点，根据业务特点的不同，架构也需要做出一些调整，架构是一个不断演进的过程，随着业务的发展（横向、纵向），架构的策略也需要做出相应的调整

### 目标
> 提高开发效率  
> 降低开发难度  
> 支持分布式开发团队的开发工作（管理协作请使用“开发团队管理平台”）
> 成为一个无存在感的框架（在需要用到的地方才会出现，且使用方法是符合正常开发逻辑和思维方式的）

### 适用于

- 互联网产品  
通过移动端或Web端调用接口服务的方式开发产品，前端团队负责移动端或Web端的开发，后端团队负责API接口的开发；

- 企业信息管理系统、后台管理  
对于业务量较小的管理系统，只需使用【manage】工程即可；  
如果业务量较大，则使用分布式架构方式开发接口服务，供管理系统调用（可以将业务量较大的接口提炼出来单独实现）；

- 云端（SaaS）信息管理平台（例如项目管理、营销管理、办公OA、团队管理，等各种管理方式的云服务化）

- 接口服务、企业微服务架构  
对于业务量较小或开发时间紧张的项目，可以使用【api-portal】工程，实现MVC架构的接口服务，随着业务量的扩展，逐步重构到分布式架构；    
对于业务量较大的项目，使用【api-core及provider、consumer相关项目】，实现分布式架构的接口服务；

### 支持（非功能性）
- 服务器端的分布式（通讯-NIO、存储-HDFS、缓存-Redis、事务）开发和部署
- 大规模并发处理

### 支持（开发场景）
写支持的开发场景 单人 多部门 分布式 这些场景关联的架构模块和用法 策略等

### 开发环境

JDK：1.8  
开发工具：IntelliJ IDEA  
数据库：MySQL 5.6  
应用容器：Apache Tomcat 9+
Maven：3.x.x  
Git：码云

### 搭建开发环境

#### 安装JDK 8

根据安装包的步骤安装即可

#### 安装配置Maven 3
- 解压maven
- 系统环境变量中，配置JAVA_HOME：jdk的安装目录
- 系统环境变量中，配置M2_HOME：maven的解压目录
- 系统环境变量中，Path配置中追加：%JAVA_HOME%\bin;%M2_HOME%\bin;
- 系统环境变量中，新增classpath：.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar

命令行(cmd)中输入：“java -version” 查看输出版本是否与JDK安装版本一致;  
命令行(cmd)中输入：“mvn -version” 查看输出版本是否与Maven安装版本一致;

#### 安装Redis（Windows）
- 打开https://github.com/ServiceStack/redis-windows
- 下载redis-latest.zip
- 解压到指定目录即可

#### 配置Git环境
安装本地的Git客户端  

#### 安装IntelliJ IDEA
破解方式1（在线生成注册码）：http://idea.iteblog.com/  
破解方式2（配置License server）：http://idea.iteblog.com/key.php

#### 安装IntelliJ IDEA的“码云”插件
从IDEA的插件仓库搜索GitOSC下载并安装即可  
找到IDEA-->File-->Settings-->Version Control-->码云：设置用户名密码
找到IDEA-->File-->Settings-->Version Control-->Git：设置git.exe的安装路径


#### 从Git中下载 [kitchen基础框架]代码
> Git地址：https://git.oschina.net/sChef/kitchen.git

#### 配置Tomcat
Tomcat 9 以上  
- 官网下载后解压至任意目录  
- IDEA中配置Run/Debug Configurations，点击加号，选择Defaults-->Tomcat Server-->Local  
- 设置本机Tomcat路径  
- 设置代码更新方式  
- 将工程中的web类型的模块加到Tomcat中
- 设置访问路径前缀为：http://127.0.0.1:port（注意下方的HTTP Server Settings的HTTP port也需要修改成一样的）  
- 修改本机Tomcat配置： <Connector port="8080" protocol="HTTP/1.1" URIEncoding="UTF-8" connectionTimeout="20000" redirectPort="8443" /> conf/server.xml中增加 URIEncoding="UTF-8" 支持get方式的中文编解码  
- ~~依赖子模块的Web(war)项目，在IDEA环境中调试运行时，应当在Run/Debug Configurations中设置Before Launch（使用Maven命令进行打包、安装的操作）
新增maven命令clean和install  
去除Make和Build *** artifact命令
最终顺序如下：  
1、Run Maven Goal 'kitchen: clean' （添加Maven的clean命令）  
2、Run Maven Goal 'kitchen: install' （添加Maven的install命令）~~
- 依赖子模块的Web(war)项目，在IDEA环境中使用默认的运行配置：  
1、Make  
2、Build 'xxxxx:war exploded' artifact  

#### 配置 ZooKeeper（可选）
@解压ZooKeeper  
@将zookeeper/conf目录下的zoo_sample.cfg文件修改为zoo.cfg  
@配置zoo.cfg中的参数，例如将maxClientCnxns=0（设置为0）  
@运行zkServer.cmd（Windows）或zkServer.sh（Linux）

### IDEA中快速部署和运行（使用jetty运行，无需配置Tomcat，适用于演示和汇报）

#### 前提
数据库已启动，并在工程中配置完成数据库的config.properties  

> 启动ZooKeeper  
运行ZooKeeper目录下的bin --> zkServer.cmd

> 启动Redis  
Windows：命令行输入redis-server.exe redis.windows.conf

> 启动服务Provider（可选，采用分布式开发时启动）  
运行Maven的clean命令  
运行Maven的install命令  
拷贝打包好的jar包和lib目录至本机的任意目录  
（Windows）打开cmd，进入jar包所存放目录，输入命令：java -jar ******.jar   
  
> 启动接口服务api-portal（可选，采用分布式开发时启动）
运行Maven下Plugins中，jetty的jetty:run-exploded命令

> 启动后台管理   
运行Maven下Plugins中，jetty的jetty:run-exploded命令

> 启动业务站点  
运行Maven下Plugins中，jetty的jetty:run-exploded命令



### 扩展一个接口服务Provider

### 扩展一个接口代理服务器

## 系统架构图

## 约定

### 附件：《框架设计 - 模块描述，依赖关系》
### 附件：《开发者指南》
### 附件：《部署指南 - 单机部署和分布式部署》
### 附件：《常见问题》
如何复制一个provider新工程？

### 附件：《接口开发最佳实践》 接口的命名 接口如何设计以满足各种前端
### 附件：《并发编程指南》
### 附件：《Kitchen基础框架RPC实现及性能调优指南》
### 附件：《ZooKeeper集群部署和配置》
