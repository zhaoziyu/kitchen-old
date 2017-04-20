package com.kitchen.rpc.server.config;

/**
 * RPC服务器的配置
 *
 * @date 2016-12-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcServerConfig {

    /**
     * 默认的RPC服务端口号
     *
     * 当未在rpc-provider.properties中指定rpc.provider.port.type参数为default时，使用此端口号提供服务
     */
    public static int DEFAULT_PORT = 8999;

    /**
     * 默认的RPC服务IP地址
     *
     * 当未在rpc-provider.properties中指定rpc.provider.host.type参数为default时，使用此IP提供服务
     */
    public static String DEFAULT_HOST = "127.0.0.1";

    /**
     * backlog：TCP参数
     * 内核为此套接口排队的最大连接个数
     * TCP未连接队列和已连接队列总和的最大值，需要根据实际场景和网络状况进行灵活设置
     */
    public static int CHANNEL_BACKLOG = 1024;

    /**
     * NioEventLoopGroup（work group - 业务处理线程池）的默认线程数量
     * 若设置为0，则使用Netty的默认线程数策略：线程数=当前服务器的处理器核数*2
     *
     * N核服务器，通过执行业务的单线程分析出本地计算时间为x，等待时间为y，则工作线程数（线程池线程数）设置为 N*(x+y)/x，能让CPU的利用率最大化。
     * 一般来说，非CPU密集型的业务（加解密、压缩解压缩、搜索排序等业务是CPU密集型的业务），瓶颈都在后端数据库，本地CPU计算的时间很少，所以设置几十或者几百个工作线程也都是可能的。
     */
    public static int RPC_SERVER_WORK_THREADS = 16;
}
