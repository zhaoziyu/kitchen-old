package com.kitchen.rpc.registry.policy.impl;

import com.kitchen.rpc.registry.policy.BaseServiceAddressPolicy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 负载均衡策略：随机（Random Robin）
 * 随机获取一个服务地址
 *
 * @date 2016-12-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ServiceAddressByRandom extends BaseServiceAddressPolicy {
    @Override
    public String getAddress(LinkedHashMap<String, Integer> addressMap) {
        if (addressMap == null || addressMap.size() == 0) {
            return null;
        }
        ArrayList<String> addressList = new ArrayList<>();
        addressList.addAll(addressMap.keySet());

        String address;
        int size = addressList.size();
        if (size == 1) {
            address = addressList.get(0);
        } else {
            address = addressList.get(ThreadLocalRandom.current().nextInt(size));
        }
        return address;
    }
}
