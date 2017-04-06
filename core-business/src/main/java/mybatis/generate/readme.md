MyBatis Generator工具的生成文件存放的目录

生成PO和dao相关的类和文件  

生成后：  
1、将mybatis.generate.dao中的类和xml移入core-business的dao.[相关模块]  
2、将mybatis.generate.po中的类移入core-interface的*.pojo.po.[相关模块]  
3、生成后的*Mapper.java中需要增加@Repository注解，以便Spring扫描到dao层资源  
  
注意：  
若之前对相同文件进行过一些方法的添加或改动，在覆盖之前注意保留  
移动过后需要检查一下Mapper.xml中引用的类路径是否正确  

建议：生成的PO对象应当都带有cloneSelf()方法，用于VO对象在继承PO对象后，对所继承的PO属性进行赋值操作  
