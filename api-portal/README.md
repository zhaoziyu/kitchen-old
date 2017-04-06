# api-portal

* 这是一个对外提供开放接口的模块，主要负责控制层的逻辑，不包含具体业务逻辑的实现；  
* api-portal可以部署多套，上层通过Nginx进行负载均衡；  

## 多种使用方式
可以依赖rpc-client，进行分布式服务的调用（api-portal将作为服务的消费方存在）
可以依赖core-business，注入所需的业务逻辑层（Service）接口的实现，基于单点MVC架构的方式进行接口开发

## 其它
不建议把业务逻辑层的处理代码写在api-portal中