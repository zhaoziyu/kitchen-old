package com.kitchen.rpc.server.deploy.host.fix;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.host.HostFetcher;
import com.kitchen.rpc.server.deploy.host.tool.HostVerifier;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 指定固定的IP地址；例如：192.168.10.10
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class FixHostFetcher implements HostFetcher {
    @Override
    public String getIp(String hostArg) throws ProviderDeployException {
        // 验证传入的IP地址的格式
        if (!HostVerifier.isIPv4(hostArg)) {
            throw new ProviderDeployException("初始化错误：IP地址（" + hostArg + "）无效，格式错误");
        }
        // 验证传入的IP地址的有效性
        boolean isUseable = false;
        try {
            isUseable = InetAddress.getByName(hostArg).isReachable(2000);
        } catch (IOException e) {
            throw new ProviderDeployException("初始化错误：IP地址（" + hostArg + "）无效");
        }
        if (isUseable) {
            return hostArg;
        } else {
            throw new ProviderDeployException("初始化错误：IP地址（" + hostArg + "）连接失败");
        }
    }
}
