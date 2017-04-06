package com.kitchen.rpc.client.cache;

import com.kitchen.rpc.client.config.RpcClientConfig;
import com.kitchen.rpc.common.util.RpcStringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty客户端工厂类
 *
 * @date 2016-12-18
 * @author 赵梓彧 - kitchen_dev@163.com
 */
class ClientBoostrapFactory {
    /**
     * 客户端IO处理线程池
     * 默认线程数量：当前服务器的处理器核数 * 2
     *
     */
    private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup(RpcClientConfig.RPC_CLIENT_IO_THREADS);

    public static Bootstrap createNewBoostrap(String serviceAddress) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);

        // 连接设置
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);// 长连接

        String host = RpcStringUtil.getHost(serviceAddress);
        int port = RpcStringUtil.getPort(serviceAddress);
        bootstrap.remoteAddress(host, port);

        return bootstrap;
    }

    public static void stopEventLoopGroup() {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            System.out.println("NioEventLoopGroup：优雅关闭");
        }
    }
}
