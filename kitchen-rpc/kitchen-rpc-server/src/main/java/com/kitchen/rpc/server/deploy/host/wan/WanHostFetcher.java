package com.kitchen.rpc.server.deploy.host.wan;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.host.HostFetcher;
import com.kitchen.rpc.server.deploy.host.tool.OperatingSystemType;
import com.kitchen.rpc.server.deploy.host.tool.OperatingSystemUtil;
import com.kitchen.rpc.server.deploy.host.wan.linux.LinuxWideAreaNetwork;
import com.kitchen.rpc.server.deploy.host.wan.windows.WideAreaNetworkSiteEnum;
import com.kitchen.rpc.server.deploy.host.wan.windows.WindowsWideAreaNetwork;

import java.io.IOException;

/**
 * 获取广域网（WAN网）IP地址，无需填写rpc.provider.host；
 * 适用于连接广域网的宿主主机
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class WanHostFetcher implements HostFetcher {
    @Override
    public String getIp(String hostArg) throws ProviderDeployException {
        String WAN_IP = "";
        OperatingSystemType osType = OperatingSystemUtil.getCurOperatingSystemType();
        switch (osType) {
            case WINDOWS:
                String providerUrl = WideAreaNetworkSiteEnum.IP138.getUrl();
                try {
                    WAN_IP = WindowsWideAreaNetwork.getWanIp(providerUrl);
                } catch (IOException e) {
                    throw new ProviderDeployException("初始化异常：从公网地址查询服务站点（" + providerUrl + "）获取公网IP失败，未获取到有效的WAN网地址");
                }
                break;
            case LINUX:
                WAN_IP = LinuxWideAreaNetwork.getWanIp();
                break;
            case UNKNOWN:
                break;
        }
        return WAN_IP;
    }
}
