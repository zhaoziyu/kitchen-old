package com.kitchen.rpc.server.deploy.host;

/**
 * 枚举主机（ip）的加载方式，与api-provider中rpc-provider.properties的rpc.provider.host.type参数一一对应
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum HostFetchType {
    FIX,
    PREFIX,
    CARD,
    WAN,
    DEFAULT;
}
