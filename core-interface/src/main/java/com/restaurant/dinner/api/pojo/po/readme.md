PO:persistant object  
持久对象。可以看成是与数据库中的表相映射的java对象，最简单的PO就是对应数据库中某个表中的一条记录，多个记录可以用PO的集合。PO中应该不包含任何对数据库的操作。   

同DO概念一致
DO（Data Object）：与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。