package com.kitchen.rpc.client.config;

/**
 * RPC客户端配置
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-22
 */
public class RpcClientConfig {
    /**
     * 连接通道池最大连接数
     *
     * 定义了每个RPC服务提供者的最大并发数（一个RPC服务提供者对应一个FixedChannelPool连接池）
     */
    public final static int CHANNEL_POOL_MAX_CONNECTIONS = 10240;

    /**
     * NioEventLoopGroup（客户端IO处理线程池）的默认线程数量
     * 若设置为0，则使用Netty的默认线程数策略：线程数=当前服务器的处理器核数*2
     *
     * N核服务器，通过执行业务的单线程分析出本地计算时间为x，等待时间为y，则工作线程数（线程池线程数）设置为 N*(x+y)/x，能让CPU的利用率最大化。
     * 一般来说，非CPU密集型的业务（加解密、压缩解压缩、搜索排序等业务是CPU密集型的业务），瓶颈都在后端数据库，本地CPU计算的时间很少，所以设置几十或者几百个工作线程也都是可能的。
     */
    public static int RPC_CLIENT_IO_THREADS = 512;
}
