package com.kitchen.rpc.registry.policy.impl;

import com.kitchen.rpc.registry.policy.BaseServiceAddressPolicy;

import java.util.*;

/**
 * 负载均衡策略：加权轮询（Weight Round Robin）
 * 不同的后端服务器可能机器的配置和当前系统的负载并不相同，因此它们的抗压能力也不相同。给配置高、负载低的机器配置更高的权重，让其处理更多的请；而配置低、负载高的机器，给其分配较低的权重，降低其系统负载，加权轮询能很好地处理这一问题，并将请求顺序且按照权重分配到后端。
 *
 * 适用场景：每个服务提供者都提供相同的服务菜单
 *
 * 缺点：对于相同的服务器地址列表可以起到轮询作用，但系统中每个服务的地址列表不一定相同，这种情况下，会导致获取的服务器地址并不是轮询的
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-16
 */
public class ServiceAddressByWeightRoundRobin extends BaseServiceAddressPolicy {
    // 记录当前轮询的索引
    private static Integer pos = 0;

    @Override
    public String getAddress(LinkedHashMap<String, Integer> addressMap) {
        // 取得IP地址列表
        Set<String> keySet = addressMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        List<String> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            String server = iterator.next();
            int weight = addressMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }

        String address;
        synchronized (pos) {
            if (pos >= serverList.size()) {
                pos = 0;
            }
            address = serverList.get(pos);
            pos ++;
        }

        return address;
    }
}
