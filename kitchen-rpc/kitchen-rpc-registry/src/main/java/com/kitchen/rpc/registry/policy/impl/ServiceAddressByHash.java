package com.kitchen.rpc.registry.policy.impl;

import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.registry.policy.BaseServiceAddressPolicy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 负载均衡策略：源地址哈希（Hash）
 * 源地址哈希的思想是根据获取客户端的IP地址，通过哈希函数计算得到的一个数值，用该数值对服务器列表的大小进行取模运算，得到的结果便是客服端要访问服务器的序号。采用源地址哈希法进行负载均衡，同一IP地址的客户端，当后端服务器列表不变时，它每次都会映射到同一台后端服务器进行访问。
 *
 * 适用场景：要求请求会话一致的业务（例如（非真实使用场景）：用户请求接口1完成了1/2的业务，并在服务提供端记录了状态，请求接口2时，调用接口1的处理结果，完成了全部业务处理）
 *
 * 优点：保证了相同客户端IP地址的请求会发送到同一台服务器进行处理，直到服务器列表变更。根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。
 * 缺点：除非集群中服务器的非常稳定，基本不会上下线，否则一旦有服务器上线、下线，那么通过源地址哈希算法路由到的服务器是服务器上线、下线前路由到的服务器的概率非常低，如果是session则取不到session，如果是缓存则可能引发"雪崩"。
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-16
 */
public class ServiceAddressByHash extends BaseServiceAddressPolicy {
    @Override
    public String getAddress(LinkedHashMap<String, Integer> addressMap) {
        RpcRequest request = this.getRpcRequest();
        if (request != null && request.getRequestIp() != null && !request.getRequestIp().isEmpty()) {
            // 源地址Hash
            // 取得IP地址列表
            Set<String> keySet = addressMap.keySet();
            ArrayList<String> addressList = new ArrayList<String>();
            addressList.addAll(keySet);

            // 通过客户端的ip，取得它的Hash值，对服务器列表的大小取模，结果便是选用的服务器在服务器列表中的索引值
            int hashCode = request.getRequestIp().hashCode();
            hashCode = Math.abs(hashCode);// 取绝对值
            int serverListSize = addressList.size();
            int index = hashCode % serverListSize;

            return addressList.get(index);
        } else {
            // 随机
            BaseServiceAddressPolicy policy = new ServiceAddressByRandom();
            return policy.getAddress(addressMap);
        }
    }
}
