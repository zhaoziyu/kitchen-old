package com.kitchen.rpc.server.deploy.port.def;

import com.kitchen.rpc.server.config.RpcServerConfig;
import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.port.PortFetcher;
import com.kitchen.rpc.server.deploy.port.tool.PortVerifier;
import org.javatuples.Pair;

/**
 * 系统指定默认端口号8999
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DefaultPortFetcher implements PortFetcher {
    @Override
    public Integer getPort(Integer portArg) throws ProviderDeployException {
        // 验证默认端口是否可用
        Pair<Boolean, String> result = PortVerifier.availability(RpcServerConfig.DEFAULT_PORT);
        if (result.getValue0()) {
            return RpcServerConfig.DEFAULT_PORT;
        } else {
            throw new ProviderDeployException(result.getValue1());
        }
    }
}
