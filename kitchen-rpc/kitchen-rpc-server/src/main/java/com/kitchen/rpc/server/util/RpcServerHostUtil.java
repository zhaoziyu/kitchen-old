package com.kitchen.rpc.server.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 当前RPC服务主机的信息
 *
 * @date 2016-12-09
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcServerHostUtil {

    /**
     * 获取当前RPC服务主机的IP地址
     * @return
     */
    public static String getHostIp() {
        String hostIp = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostIp = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostIp;
    }
}
