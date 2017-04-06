package com.kitchen.rpc.client;

/**
 * RPC回调函数接口
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface RpcClientCallback {
    void success(Object result);

    void fail(Throwable e);
}
