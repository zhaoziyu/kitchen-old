package com.kitchen.rpc.registry;

import com.kitchen.rpc.common.protocol.RpcRequest;

/**
 * RPC服务发现（探寻）器接口
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface RpcServiceDiscovery {
    /**
     * 根据服务名称查找服务地址
     *
     * @param serviceName 服务名称
     * @param rpcRequest RPC请求对象
     * @return 服务地址
     */
    String discover(String serviceName, RpcRequest rpcRequest);

    /**
     * 关闭相关资源
     */
    void stop();
}
