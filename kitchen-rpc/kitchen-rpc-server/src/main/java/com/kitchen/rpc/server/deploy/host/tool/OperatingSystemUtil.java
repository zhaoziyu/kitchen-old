package com.kitchen.rpc.server.deploy.host.tool;

/**
 * 本地操作系统相关工具类
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class OperatingSystemUtil {

    /**
     * 获取当前系统的类型枚举
     *
     * @return
     */
    public static OperatingSystemType getCurOperatingSystemType() {
        String osName = System.getProperty("os.name");
        osName = osName.toLowerCase();
        if (osName.startsWith("win")) {
            return OperatingSystemType.WINDOWS;
        } else if (osName.startsWith("linux")) {
            return OperatingSystemType.LINUX;
        }
        return OperatingSystemType.UNKNOWN;
    }

}
