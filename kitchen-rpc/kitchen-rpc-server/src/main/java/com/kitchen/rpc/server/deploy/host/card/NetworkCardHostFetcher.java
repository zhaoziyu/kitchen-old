package com.kitchen.rpc.server.deploy.host.card;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.host.HostFetcher;
import com.kitchen.rpc.server.deploy.host.tool.HostVerifier;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 指定网卡名称获取IP地址，支持模糊匹配；
 * 例如：wlan、net4；
 * 不支持获取“本地环回网卡”的地址（即127.0.0.1）；
 * 不支持获取虚拟网卡地址；
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class NetworkCardHostFetcher implements HostFetcher {
    @Override
    public String getIp(String hostArg) throws ProviderDeployException {
        String networkCardName = hostArg;
        Enumeration<NetworkInterface> enumeration = null;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }

        List<NetworkInterface> matchingList = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            NetworkInterface networkInterface = enumeration.nextElement();
            String curCardName = networkInterface.getName();
            // 比较名称是否匹配
            if (curCardName.indexOf(networkCardName) != -1) {
                try {
                    // 检查网络接口设备是否开启 && 不是本地环回地址 && 不是虚拟设备 && 存在地址列表
                    if (networkInterface.isUp()
                            && !networkInterface.isLoopback()
                            && !networkInterface.isVirtual()
                            && networkInterface.getInterfaceAddresses().size() > 0) {
                        // 加入匹配清单
                        matchingList.add(networkInterface);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }

        List<String> matchingIpList = HostVerifier.findIPv4List(matchingList);

        if (matchingIpList.size() == 0) {
            throw new ProviderDeployException("初始化失败：根据网卡名称（" + networkCardName + "）,未找到有效的IP地址");
        } else if (matchingIpList.size() == 1) {
            return matchingIpList.get(0);
        } else {
            throw new ProviderDeployException("初始化失败：根据网卡名称（" + networkCardName + "）,找到多个有效的IP地址");
        }
    }
}
