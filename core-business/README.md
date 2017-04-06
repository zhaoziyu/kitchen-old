# core-business

负责业务逻辑层和数据持久化层的代码；  
注意：若需要支持分布式提高并发量和处理效率，则业务逻辑层的接口（Service的接口）需要放在core-interface中，并由相关的api-provider来实现；


项目初级阶段，建议在Kitchen框架的所有模块中，只有core-business负责数据持久化（数据库操作）  
对于功能复杂的项目，在后期可以将core-business拆分为多个，分别负责不同的业务模块
