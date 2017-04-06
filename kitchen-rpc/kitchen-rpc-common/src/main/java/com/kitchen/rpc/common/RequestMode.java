package com.kitchen.rpc.common;

/**
 * RPC客户端的请求类型（方式）
 *
 * @date 2016-12-21
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum RequestMode {
    //同步（发送请求后，同步等待返回的结果）
    SYNC,

    //异步（发送后请求后，不等待返回结果，直接回收连接，服务提供者也不会再回写响应结果至发送端）
    ASYNC,
    //异步（发送请求后，通过回调函数处理返回结果）
    ASYNC_CALLBACK
}