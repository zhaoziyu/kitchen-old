package com.kitchen.rpc.server.deploy.host.prefix;

import com.kitchen.rpc.server.exception.ProviderDeployException;
import com.kitchen.rpc.server.deploy.host.HostFetcher;
import com.kitchen.rpc.server.deploy.host.tool.HostVerifier;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 指定IP地址前缀，在多网卡且统一规划IP号段的环境下适用；
 * 例如：192.168.3、192等；
 * 不支持获取“本地环回网卡”的地址（即127.0.0.1）；
 * 不支持获取虚拟网卡地址；
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class PrefixHostFetcher implements HostFetcher {
    @Override
    public String getIp(String hostArg) throws ProviderDeployException {
        String ipPrefix = hostArg;
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

        List<String> matchingIPv4List = new ArrayList<>();
        List<String> iPv4List = HostVerifier.findIPv4List(matchingList);
        // 找到前缀为指定内容的IP
        for (String ip : iPv4List) {
            if (ip.startsWith(ipPrefix)) {
                matchingIPv4List.add(ip);
            }
        }
        if (matchingIPv4List.size() == 0) {
            throw new ProviderDeployException("初始化失败：未找到前缀为（" + ipPrefix + "）的有效IP地址");
        } else if (matchingIPv4List.size() == 1) {
            return matchingIPv4List.get(0);
        } else {
            throw new ProviderDeployException("初始化失败：找到多个前缀为（" + ipPrefix + "）的有效IP地址");
        }
    }
}
