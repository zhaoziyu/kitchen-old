package com.kitchen.rpc.server.exception;

/**
 * 服务提供者（provider）的异常类
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ProviderDeployException extends Exception {
    public ProviderDeployException(String message) {
        super(message);
    }
}
