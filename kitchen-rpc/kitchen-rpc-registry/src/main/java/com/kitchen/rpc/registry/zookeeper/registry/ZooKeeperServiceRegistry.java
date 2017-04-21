package com.kitchen.rpc.registry.zookeeper.registry;

import com.kitchen.rpc.registry.RpcServiceRegistry;
import com.kitchen.rpc.registry.zookeeper.ZooKeeperConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.RetryNTimes;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ZooKeeperServiceRegistry implements RpcServiceRegistry {

    public ZooKeeperServiceRegistry(String zkAddress) {
        // 创建 ZooKeeper 客户端
        RetryPolicy retryPolicy = new RetryNTimes(Integer.MAX_VALUE, 5000); // 尝试无限次重连，每5秒尝试一次
        RegistryCurator.connectionZooKeeperServer(zkAddress, retryPolicy);

        // 链接状态监听器
        RegistryConnectionStateListener connectionStateListener = new RegistryConnectionStateListener(this);
        RegistryCurator.addConnectionStateListener(connectionStateListener);

        // 设置Curator不打印日志，详见：org.apache.curator.ConnectionState
        System.setProperty("curator-dont-log-connection-problems", "true");
    }

    @Override
    public void register(String serviceName, String nodeData) {
        try {
            // 创建 service 节点（持久）
            String servicePath = ZooKeeperConfig.ZK_REGISTRY_PATH + "/" + serviceName;
            if (!RegistryCurator.checkNodeExist(servicePath)) {
                RegistryCurator.createFixNode(servicePath);
            }
            // 创建 address 节点（临时）
            String addressPath = servicePath + "/address-";
            addressPath = RegistryCurator.createTempSeqNode(addressPath);
            RegistryCurator.setNodeData(addressPath, nodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        RegistryCurator.disconnectionZooKeeperServer();
    }


}
