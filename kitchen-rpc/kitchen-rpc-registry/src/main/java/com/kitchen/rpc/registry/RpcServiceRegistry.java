package com.kitchen.rpc.registry;

/**
 * RPC服务注册器接口
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface RpcServiceRegistry {
    /**
     * 注册服务名称与服务地址
     *
     * @param serviceName    服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName, String serviceAddress);

    /**
     * 关闭相关资源
     */
    void stop();
}
