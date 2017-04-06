package com.kitchen.rpc.client;

import com.kitchen.rpc.client.cache.ClientChannelCache;
import com.kitchen.rpc.common.RequestMode;
import com.kitchen.rpc.registry.RpcServiceDiscovery;
import com.kitchen.rpc.registry.zookeeper.discovery.ZooKeeperServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * RPC客户端请求代理类
 *
 * @date 2016-12-18
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RpcClientProxy {
    private static boolean OPEN_RPC = true;
    /**
     * 固定的服务提供者地址
     * 格式：[ip或域名:prot]
     */
    private String[] serviceAddress;

    /**
     * 服务注册中心的地址
     */
    private String registryCenterAddress;


    /**
     * 设置固定的服务提供者地址，使用此方式将不会再服务注册中心查找
     * @param serviceAddress
     */
    public RpcClientProxy(String[] serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    /**
     * 初始化注册中心，在注册中心查找服务提供者
     * @param registryCenterAddress
     */
    public RpcClientProxy(String registryCenterAddress, boolean open) {
        OPEN_RPC = open;
        if (OPEN_RPC) {
            this.registryCenterAddress = registryCenterAddress;

            RpcServiceDiscovery rpcServiceDiscovery = new ZooKeeperServiceDiscovery(registryCenterAddress);
            ClientChannelCache.setRpcServiceDiscovery(rpcServiceDiscovery);
        } else {
            System.out.println("未启用RPC服务，如需启用RPC服务，请在rpc-consumer.properties中设置rpc.consumer.open为true");
        }
    }

    /**
     * 创建指定服务接口的远程服务实例
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public static <T> T createSync(final Class<?> interfaceClass) {
        return createSync(interfaceClass, "");
    }

    /**
     * 创建指定服务接口和版本的远程服务实例
     * @param interfaceClass
     * @param serviceVersion
     * @param <T>
     * @return
     */
    public static <T> T createSync(final Class<?> interfaceClass, final String serviceVersion) {
        InvocationHandler proxyHandler = new RpcClientProxyHandler(serviceVersion, RequestMode.SYNC);
        return createObject(interfaceClass, proxyHandler);
    }

    /**
     * 创建指定服务接口的异步远程服务实例
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public static <T> T createAsync(final Class<?> interfaceClass) {
        return createAsync(interfaceClass, "");
    }

    /**
     * 创建指定服务接口和版本的远程服务实例
     * @param interfaceClass
     * @param serviceVersion
     * @param <T>
     * @return
     */
    public static <T> T createAsync(final Class<?> interfaceClass, final String serviceVersion) {
        InvocationHandler proxyHandler = new RpcClientProxyHandler(serviceVersion, RequestMode.ASYNC);
        return createObject(interfaceClass, proxyHandler);
    }

    /**
     * 创建指定服务接口的异步远程服务实例，并设置回调处理
     * @param interfaceClass
     * @param callbacks
     * @param <T>
     * @return
     */
    public static <T> T createAsyncCallback(final Class<?> interfaceClass, RpcClientCallback... callbacks) {
        return createAsyncCallback(interfaceClass, "", callbacks);
    }

    /**
     * 创建指定服务接口和版本的异步远程服务实例，并设置回调处理
     * @param interfaceClass
     * @param serviceVersion
     * @param callbacks
     * @param <T>
     * @return
     */
    public static <T> T createAsyncCallback(final Class<?> interfaceClass, final String serviceVersion, RpcClientCallback... callbacks) {
        InvocationHandler proxyHandler = new RpcClientProxyHandler(serviceVersion, RequestMode.ASYNC_CALLBACK, callbacks);
        return createObject(interfaceClass, proxyHandler);
    }

    @SuppressWarnings("unchecked")
    private static  <T> T createObject(final Class<?> interfaceClass, InvocationHandler proxyHandler) {
        T object = null;
        if (OPEN_RPC) {
            ClassLoader classLoader = interfaceClass.getClassLoader();
            Class<?>[] interfaceList = new Class<?>[]{
                    interfaceClass
            };

            // 创建动态代理对象
            object = (T) Proxy.newProxyInstance(
                    classLoader,
                    interfaceList,
                    proxyHandler
            );
        } else {
            System.out.println("未启用RPC服务，createObject方法无法创建动态代理对象");
        }

        return object;
    }

    /**
     * 关闭 RPC Client
     */
    public static void stop() {
        if (OPEN_RPC) {
            System.out.println("关闭RPC相关资源");

            if (ClientChannelCache.getRpcServiceDiscovery() != null) {
                ClientChannelCache.getRpcServiceDiscovery().stop();
            }

            if (ClientChannelCache.isRuning()) {
                ClientChannelCache.getInstance().stop();
            }
        }
    }
}
