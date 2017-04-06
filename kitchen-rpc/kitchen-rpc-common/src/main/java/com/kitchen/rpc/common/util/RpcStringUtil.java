package com.kitchen.rpc.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcStringUtil {
    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 分割固定格式的字符串
     */
    private static String[] split(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    public static String getHost(String address) {
        String[] addressArray = RpcStringUtil.split(address, ":");
        String ip = addressArray[0];
        return ip;
    }
    public static int getPort(String address) {
        String[] addressArray = RpcStringUtil.split(address, ":");
        int port = Integer.parseInt(addressArray[1]);
        return port;
    }
}
