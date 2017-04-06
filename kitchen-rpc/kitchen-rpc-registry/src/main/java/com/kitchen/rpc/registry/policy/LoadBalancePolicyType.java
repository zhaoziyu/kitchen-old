package com.kitchen.rpc.registry.policy;

/**
 * 负载均衡策略类型
 *
 * @date 2016-12-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum LoadBalancePolicyType {
    Random,             // <随机> 适用场景不限
    RoundRobin,        // <轮询> 适用场景：每个服务提供者都提供相同的服务菜单
    WeightRandom,      // <加权随机> 适用场景不限
    WeightRoundRobin, // <加权轮询> 适用场景：每个服务提供者都提供相同的服务菜单
    IpAddressHash;     // <源地址哈希> 适用场景：要求请求会话一致的业务
}
