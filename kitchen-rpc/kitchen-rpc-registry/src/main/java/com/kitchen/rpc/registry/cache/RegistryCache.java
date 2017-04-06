package com.kitchen.rpc.registry.cache;

import com.kitchen.rpc.registry.RpcServiceRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * RPC服务注册地址缓存类
 * 存放（缓存）服务提供者内部的“服务名”与“服务对象”之间的映射关系
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RegistryCache {
    private static String serverAddress = "";
    private static Integer serverWeight = 1;
    private static Map<String, Object> handlerMap = new HashMap<>();


    public static void put(String key, Object value) {
        handlerMap.put(key, value);
    }
    public static Map<String, Object> get() {
        return handlerMap;
    }
    public static Object get(String key) {
        return handlerMap.get(key);
    }

    public static void address(String address) {
        serverAddress = address;
    }
    public static String address() {
        return serverAddress;
    }

    public static void weight(Integer weigth) {
        serverWeight = weigth;
    }
    public static Integer weight() {
        return serverWeight;
    }

    /**
     * 注册缓存中的 RPC 服务地址
     */
    public static void registryCacheService(RpcServiceRegistry rpcServiceRegistry) {
        if (rpcServiceRegistry != null) {
            for (String interfaceName : handlerMap.keySet()) {
                // 注册的服务节点的数据：“ip:port|权重”
                String registeValue = serverAddress + "|" + serverWeight;
                rpcServiceRegistry.register(interfaceName, registeValue);
                System.out.println("服务提供者("+ serverAddress +"): 已注册["+interfaceName+"]服务至ZooKeeper服务器");
            }
        }
    }
}
