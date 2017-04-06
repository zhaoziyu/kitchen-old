package com.kitchen.rpc.registry.zookeeper.discovery;

import com.kitchen.rpc.registry.zookeeper.ZooKeeperConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * ZooKeeper连接状态监听器
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-15
 */
public class DiscoveryConnectionStateListener implements ConnectionStateListener {
    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.CONNECTED) {
            System.out.println("已连接ZooKeeper");

            ZooKeeperServiceDiscovery.subscribeAllServiceAddress();
            ZooKeeperServiceDiscovery.watcherPath(ZooKeeperConfig.ZK_REGISTRY_PATH);

        } else if (connectionState == ConnectionState.LOST) {
            try {
                System.out.println("连接丢失，正在尝试重连");
                // 关闭ZooKeeper节点检查者
                DiscoveryCurator.destroyZooKeeperTreeCache();
                // 关闭ZooKeeper连接
                DiscoveryCurator.disconnectionZooKeeperServer();

                ZooKeeperServiceDiscovery.initZooKeeperClient();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
