##### DAO:data access object
数据访问对象。此对象用于访问数据库，通常和PO结合使用，DAO中包含了各种数据库的操作方法。通过它的方法,结合PO对数据库进行相关的操作。

在kitchen框架中，DAO层的代码由MyBatis自动生成，生成后的为\***Mapper接口和\***Mapper.xml，通常保存在core-business模块的dao包中；

注意：不建议在同一个core-business中连接多个数据库，尽量做到在业务内垂直划分数据库，每个微服务负责一个高内聚、低耦合的业务模块，不同数据库之间的数据交换可以通过消息框架来修改。  
若需要在同一个core-business中连接多个数据库时，需注意以下几点：
- dao包下的Mapper.xml和Mapper类需要按数据库分别放到不同的包下，例如：有dao->db1和dao-db2两个包，分别对应db1和db2两个数据库，相关的mapper文件需要分别放到这两个包内
- 修改`mybatis-datasource.xml`和`db-config.properties`配置文件：设置两个数据库连接，并配置分别指向不同的mapper顶级包