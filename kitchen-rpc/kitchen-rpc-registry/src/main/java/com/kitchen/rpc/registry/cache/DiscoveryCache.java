package com.kitchen.rpc.registry.cache;

import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.registry.policy.BaseServiceAddressPolicy;
import com.kitchen.rpc.registry.policy.impl.*;
import com.kitchen.rpc.registry.zookeeper.ZooKeeperConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 服务探寻器缓存
 *
 * @date 2016-12-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DiscoveryCache {

    // 客户端发现的服务地址缓存至内存
    private static HashMap<String, LinkedHashMap<String, Integer>> SERVICE_ADDRESS_CACHE = new HashMap<>();

    private static BaseServiceAddressPolicy policy;

    static {
        // TODO 优化——通过Spring注入不同的获取策略
        switch (ZooKeeperConfig.GET_SERVICE_ADDRESS_POLICY) {
            case Random:
                // 适用场景不限
                policy = new ServiceAddressByRandom();
                break;
            case RoundRobin:
                // 适用场景：每个服务提供者都提供相同的服务菜单
                policy = new ServiceAddressByRoundRobin();
                break;
            case WeightRandom:
                // 适用场景不限
                policy = new ServiceAddressByWeightRandom();
                break;
            case WeightRoundRobin:
                // 适用场景：每个服务提供者都提供相同的服务菜单
                policy = new ServiceAddressByWeightRoundRobin();
                break;
            case IpAddressHash:
                // 适用场景：要求请求会话一致的业务
                policy = new ServiceAddressByHash();
                break;
            default:
                policy = new ServiceAddressByRandom();
                break;
        }
    }

    /**
     * 获取服务的访问地址（URL）
     * @param serviceName
     * @return
     */
    public static String getServiceAddress(String serviceName, RpcRequest rpcRequest) {
        if (SERVICE_ADDRESS_CACHE == null) {
            return null;
        }
        // 深拷贝
        LinkedHashMap<String, Integer> addressMap = (LinkedHashMap<String, Integer>) SERVICE_ADDRESS_CACHE.get(serviceName).clone();
        policy.setRequest(rpcRequest);
        String address = policy.getAddress(addressMap);

        return address;
    }

    public static void setServiceAddressCache(HashMap<String, LinkedHashMap<String, Integer>> newCache) {
        synchronized (SERVICE_ADDRESS_CACHE) {
            SERVICE_ADDRESS_CACHE.clear();
            SERVICE_ADDRESS_CACHE.putAll(newCache);
        }
    }
}
