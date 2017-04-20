package com.kitchen.rpc.server.deploy.port;

import com.kitchen.rpc.server.exception.ProviderDeployException;

/**
 * 查找宿主主机端口的接口
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface PortFetcher {

    /**
     * 获取可用的端口号
     *
     * @param portArg 对应rpc-provider.properties中的rpc.provider.port参数
     * @return
     */
    Integer getPort(Integer portArg) throws ProviderDeployException;

}
