package com.kitchen.rpc.registry.policy;

import com.kitchen.rpc.registry.policy.impl.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 各种负载均衡算法的测试
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-16
 */
public class TestLoadBalance {
    public static void main(String[] args) {
        BaseServiceAddressPolicy policy;
        switch (LoadBalancePolicyType.IpAddressHash) {
            case Random:
                policy = new ServiceAddressByRandom();
                break;
            case RoundRobin:
                policy = new ServiceAddressByRoundRobin();
                break;
            case WeightRandom:
                policy = new ServiceAddressByWeightRandom();
                break;
            case WeightRoundRobin:
                policy = new ServiceAddressByWeightRoundRobin();
                break;
            case IpAddressHash:
                policy = new ServiceAddressByHash();
                break;
            default:
                policy = new ServiceAddressByRandom();
                break;
        }

        LinkedHashMap<String, Integer> addressMap = new LinkedHashMap<>();
        addressMap.put("111.111.111.111", 1);
        addressMap.put("555.111.111.111", 5);
        addressMap.put("444.111.111.111", 4);
        addressMap.put("333.111.111.111", 3);
        addressMap.put("222.111.111.111", 2);

        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < 15000; i++) {
            String address = policy.getAddress(addressMap);
            if (result.containsKey(address)) {
                int count = result.get(address) + 1;
                result.put(address, count);
            } else {
                result.put(address, 1);
            }
            System.out.println(address);
        }

        Iterator iterator = result.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println("地址：" + entry.getKey() + " 获得次数：" + entry.getValue());
        }
    }
}
