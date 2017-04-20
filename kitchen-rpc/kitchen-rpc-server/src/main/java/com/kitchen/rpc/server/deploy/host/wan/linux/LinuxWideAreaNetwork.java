package com.kitchen.rpc.server.deploy.host.wan.linux;

import com.kitchen.rpc.server.exception.ProviderDeployException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Linux系统下，获取公网（WAN）IP地址的工具
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-04-19
 */
public class LinuxWideAreaNetwork {
    /**
     * 在Linux平台中获取广域网IP地址
     *
     * @return
     */
    public static String getWanIp() throws ProviderDeployException {
        String ip = null;
        LineNumberReader input = null;
        // 获取当前类的路径
        String clsPath = LinuxWideAreaNetwork.class.getResource("/").getPath();
        Process process = null;
        String line = "";
        try {
            Runtime.getRuntime().exec("dos2unix " + clsPath + "get_wan_address.sh");
            process = Runtime.getRuntime().exec("sh " + clsPath + "get_wan_address.sh");
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            input = new LineNumberReader(ir);
            if ((line = input.readLine()) != null) {
                ip = line;
            }
        } catch (IOException e) {
            throw new ProviderDeployException("初始化异常：获取Linux公网IP地址失败");
        }

        return ip;
    }

}
