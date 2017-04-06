package com.kitchen.rpc.client.cache;

import com.kitchen.rpc.client.config.RpcClientConfig;
import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.common.util.RpcStringUtil;
import com.kitchen.rpc.registry.RpcServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.pool.FixedChannelPool;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Netty客户端连接通道管理
 *
 * @date 2016-12-18
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ClientChannelCache {
    /**
     * 运行状态
     */
    private volatile static boolean RUNING = false;
    /**
     * RPC连接通道池缓存
     */
    private Map<String, FixedChannelPool> channelPoolMap;

    /**
     * RPC服务搜索器
     */
    private static RpcServiceDiscovery rpcServiceDiscovery;

    public static void setRpcServiceDiscovery(RpcServiceDiscovery discovery) {
        rpcServiceDiscovery = discovery;
    }

    public static RpcServiceDiscovery getRpcServiceDiscovery() {
        return rpcServiceDiscovery;
    }

    private volatile static ClientChannelCache channelCache;

    private ClientChannelCache() {
        channelPoolMap = new ConcurrentHashMap<>();
    }

    public static ClientChannelCache getInstance() {
        if (channelCache == null) {
            RUNING = true;
            synchronized (ClientChannelCache.class) {
                if (channelCache == null) {
                    channelCache = new ClientChannelCache();
                }
            }
        }
        return channelCache;
    }

    public static boolean isRuning() {
        return RUNING;
    }

    public Channel getChannel(String interfaceName, String serviceVersion, RpcRequest rpcRequest) {
        if (!RUNING) {
            return null;
        }
        Channel channel = null;
        String serviceAddress = getServiceAddress(interfaceName, serviceVersion, rpcRequest);
        FixedChannelPool channelPool;
        if (!channelPoolMap.containsKey(serviceAddress)) {
            // 若其它线程正在创建相同的连接，则让其它线程等待
            synchronized (channelPoolMap) {
                if (channelPoolMap.containsKey(serviceAddress)) {
                    channelPool = channelPoolMap.get(serviceAddress);
                } else {
                    Bootstrap bootstrap = ClientBoostrapFactory.createNewBoostrap(serviceAddress);
                    RpcClientChannelPoolHandler handler = new RpcClientChannelPoolHandler();
                    channelPool = new FixedChannelPool(bootstrap, handler, RpcClientConfig.CHANNEL_POOL_MAX_CONNECTIONS);
                    channelPoolMap.putIfAbsent(serviceAddress, channelPool);
                }
            }
        } else {
            channelPool = channelPoolMap.get(serviceAddress);
        }

        try {
            channel = channelPool.acquire().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return channel;
    }

    public void releaseChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
        String serviceAddress = remoteAddress.getHostName() + ":" + remoteAddress.getPort();

        FixedChannelPool channelPool = channelPoolMap.get(serviceAddress);

        channelPool.release(channel);
    }


    /**
     * 根据服务名称和服务版本获取服务地址
     * 保证返回的服务地址不为空（若为空则抛出运行时异常）
     */
    private String getServiceAddress(String interfaceName, String serviceVersion, RpcRequest rpcRequest) {
        String serviceAddress = "";
        if (rpcServiceDiscovery != null) {
            if (RpcStringUtil.isNotEmpty(serviceVersion)) {
                interfaceName += "-" + serviceVersion;
            }
            serviceAddress = rpcServiceDiscovery.discover(interfaceName, rpcRequest);
        }
        if (RpcStringUtil.isEmpty(serviceAddress)) {
            throw new RuntimeException("server address is empty");
        }
        return serviceAddress;
    }

    public void stop() {
        RUNING = false;

        for (String address : channelPoolMap.keySet()) {
            channelPoolMap.get(address).close();
            System.out.println("关闭[" + address + "]Channel Pool");
        }

        ClientBoostrapFactory.stopEventLoopGroup();
    }
}
