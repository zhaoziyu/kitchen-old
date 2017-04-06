package com.kitchen.rpc.registry.zookeeper;

import com.kitchen.rpc.registry.policy.LoadBalancePolicyType;

/**
 * ZooKeeper配置
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ZooKeeperConfig {
    public static int ZK_SESSION_TIMEOUT = 5000;          //ZooKeeper 会话超时或过期设置
    public static int ZK_CONNECTION_TIMEOUT = 1000;       //ZooKeeper 请求超时设置

    public static String ZK_REGISTRY_PATH = "/registry";

    public static LoadBalancePolicyType GET_SERVICE_ADDRESS_POLICY = LoadBalancePolicyType.Random;//获取服务地址时的负载均衡策略（随机获取）
}
