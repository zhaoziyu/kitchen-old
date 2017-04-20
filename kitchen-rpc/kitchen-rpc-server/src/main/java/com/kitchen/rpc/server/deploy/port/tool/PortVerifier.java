package com.kitchen.rpc.server.deploy.port.tool;

import org.javatuples.Pair;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 端口检查者，用于对端口在宿主主机的有效性进行验证
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class PortVerifier {
    private static final int PORT_BEGIN = 0;
    private static final int PORT_END = 65535;

    /**
     * 对端口在宿主主机的有效性进行验证
     *
     * @param port 所需验证的端口
     * @return 是否在宿主主机中有效（没有被其它程序占用）
     */
    public static Pair<Boolean, String> availability(int port) {
        boolean pass = true;
        String msg = "";

        if (port < PORT_BEGIN || port > PORT_END) {
            msg = "端口无效，超出有效端口范围";
            pass = false;
        } else {
            try {
                new ServerSocket(port);
            } catch (IOException e) {
                msg = "端口（" + port + "）无效，已被其他程序占用";
                pass = false;
            }
        }

        return new Pair<>(pass, msg);
    }
}
