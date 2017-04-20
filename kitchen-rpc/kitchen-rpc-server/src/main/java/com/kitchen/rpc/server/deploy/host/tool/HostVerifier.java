package com.kitchen.rpc.server.deploy.host.tool;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP地址检查者，负责IP地址相关的验证和操作
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class HostVerifier {
    /**
     * 验证是否为格式合法的IPv4地址
     * @param ip 需要验证的ip地址
     * @return 格式是否合法
     */
    public static boolean isIPv4(String ip) {
        if(ip.length() < 7 || ip.length() > 15 || "".equals(ip)) {
            return false;
        }
        String rexp =
                "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(ip);

        return mat.find();
    }

    /**
     * 从NetworkInterface列表中查找出所有IPv4的地址
     *
     * @param networkInterfaces
     * @return
     */
    public static List<String> findIPv4List(List<NetworkInterface> networkInterfaces) {
        List<String> matchingIpList = new ArrayList<>();
        for (NetworkInterface ni : networkInterfaces) {
            List<InterfaceAddress> addresses = ni.getInterfaceAddresses();
            for (InterfaceAddress address : addresses) {
                String ip = address.getAddress().getHostAddress();
                if (HostVerifier.isIPv4(ip)) {
                    matchingIpList.add(ip);
                }
            }
        }
        return matchingIpList;
    }
}
