package com.kitchen.rpc.server.deploy.port;

/**
 * 枚举主机端口（port）的加载方式，与api-provider中rpc-provider.properties的rpc.provider.port.type参数一一对应
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum PortFetchType {
    FIX,
    RANDOM,
    DEFAULT
}
