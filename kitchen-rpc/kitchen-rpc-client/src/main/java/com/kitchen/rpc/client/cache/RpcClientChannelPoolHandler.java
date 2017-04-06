package com.kitchen.rpc.client.cache;

import com.kitchen.rpc.client.RpcClientHandler;
import com.kitchen.rpc.common.codec.RpcDecoder;
import com.kitchen.rpc.common.codec.RpcEncoder;
import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.common.protocol.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;

/**
 * RPC连接通道池的处理器
 *
 * @date 2016-12-18
 * @author 赵梓彧 - kitchen_dev@163.com
 */
class RpcClientChannelPoolHandler implements ChannelPoolHandler {

    @Override
    public void channelReleased(Channel channel) throws Exception {
        System.out.println("释放连接到连接池：" + channel.id());
    }

    @Override
    public void channelAcquired(Channel channel) throws Exception {
        System.out.println("从连接池获取连接：" + channel.id());
    }

    @Override
    public void channelCreated(Channel channel) throws Exception {
        // 连接通道创建时，初始化pipeline
        channel.pipeline().addLast(new RpcEncoder(RpcRequest.class)); // 编码 RPC 请求
        //channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0)); //TODO 防止黏包
        channel.pipeline().addLast(new RpcDecoder(RpcResponse.class)); // 解码 RPC 响应
        channel.pipeline().addLast(new RpcClientHandler()); // 处理 RPC 响应
        System.out.println("创建新的连接通道：" + channel.id());
    }
}
