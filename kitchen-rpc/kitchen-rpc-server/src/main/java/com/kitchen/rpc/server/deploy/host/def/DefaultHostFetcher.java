package com.kitchen.rpc.server.deploy.host.def;

import com.kitchen.rpc.server.config.RpcServerConfig;
import com.kitchen.rpc.server.deploy.host.HostFetcher;

/**
 * 使用默认IP地址（127.0.0.1）；
 * 通常在开发阶段的本机部署测试时使用；
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DefaultHostFetcher implements HostFetcher {
    @Override
    public String getIp(String hostArg) {
        return RpcServerConfig.DEFAULT_HOST;
    }
}
