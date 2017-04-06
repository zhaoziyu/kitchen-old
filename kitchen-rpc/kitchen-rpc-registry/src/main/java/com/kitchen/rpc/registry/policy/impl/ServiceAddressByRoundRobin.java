package com.kitchen.rpc.registry.policy.impl;

import com.kitchen.rpc.registry.policy.BaseServiceAddressPolicy;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 负载均衡策略：轮询（Round Robin）
 * 轮询调度算法的原理是每一次把来自用户的请求轮流分配给内部中的服务器，从1开始，直到N(内部服务器个数)，然后重新开始循环。算法的优点是其简洁性，它无需记录当前所有连接的状态，所以它是一种无状态调度。
 *
 * 适用场景：每个服务提供者都提供相同的服务菜单
 *
 * 优点：试图做到请求转移的绝对均衡。
 * 缺点：为了做到请求转移的绝对均衡，必须付出相当大的代价，因为为了保证pos变量修改的互斥性，需要引入重量级的悲观锁synchronized，这将会导致该段轮询代码的并发吞吐量发生明显的下降
 * 缺点：对于相同的服务器地址列表可以起到轮询作用，但系统中每个服务的地址列表不一定相同，这种情况下，会导致获取的服务器地址并不是轮询的
 *
 * @date 2017-02-23
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ServiceAddressByRoundRobin extends BaseServiceAddressPolicy {
    // 记录当前轮询的索引
    private static Integer pos = 0;

    @Override
    public String getAddress(LinkedHashMap<String, Integer> addressMap) {
        if (addressMap == null || addressMap.size() == 0) {
            return null;
        }
        ArrayList<String> addressList = new ArrayList<>();
        addressList.addAll(addressMap.keySet());

        String address;
        synchronized (pos) {
            if (pos >= addressList.size()) {
                pos = 0;
            }
            address = addressList.get(pos);
            pos ++;
        }
        return address;
    }
}
