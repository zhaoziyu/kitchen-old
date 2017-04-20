package com.kitchen.rpc.server.deploy.port.fix;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.port.PortFetcher;
import com.kitchen.rpc.server.deploy.port.tool.PortVerifier;
import org.javatuples.Pair;

/**
 * 指定固定的端口号
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class FixPortFetcher implements PortFetcher {
    @Override
    public Integer getPort(Integer portArg) throws ProviderDeployException {
        // 验证传入的端口号是否可用
        Pair<Boolean, String> result = PortVerifier.availability(portArg);
        if (result.getValue0()) {
            return portArg;
        } else {
            throw new ProviderDeployException("初始化异常：" + result.getValue1());
        }
    }
}
