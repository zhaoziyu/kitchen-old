package com.kitchen.rpc.registry.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Curator（ZooKeeper客户端）的通用方法封装
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class CommonCuratorTool {

    /**
     * 创建ZooKeeper客户端，并开启与ZooKeeper服务器的连接
     *
     * @param zooKeeperAddress ZooKeeper服务器地址，格式：ip:port
     */
    public static CuratorFramework connectionZooKeeperServer(String zooKeeperAddress, RetryPolicy retryPolicy) {
        // 创建 ZooKeeper 客户端
        CuratorFramework zooKeeperClient = CuratorFrameworkFactory.newClient(
                zooKeeperAddress,
                ZooKeeperConfig.ZK_SESSION_TIMEOUT,
                ZooKeeperConfig.ZK_CONNECTION_TIMEOUT,
                retryPolicy
        );
        return zooKeeperClient;
    }

    /**
     * 添加ZooKeeper连接状态监听器
     *
     * @param connectionStateListener 监听器
     */
    public static void addConnectionStateListener(CuratorFramework zooKeeperClient, ConnectionStateListener connectionStateListener) {
        // 添加链接状态监听器
        zooKeeperClient.getConnectionStateListenable().addListener(connectionStateListener);
    }

    /**
     * 断开ZooKeeper的客户端与服务器的连接
     */
    public static void disconnectionZooKeeperServer(CuratorFramework zooKeeperClient) {
        if (zooKeeperClient != null) {
            zooKeeperClient.close();
            zooKeeperClient = null;
            System.out.println("已关闭ZooKeeper连接");
        }
    }

    /**
     * 检查节点是否存在
     */
    public static boolean checkNodeExist(CuratorFramework zookeeperClient, String path) throws Exception {
        Stat stat = zookeeperClient.checkExists().forPath(path);
        if (stat == null) {
            return false;
        }
        return true;
    }

    /**
     * 创建永久节点
     * 创建节点后，不删除就永久存在
     */
    public static void createFixNode(CuratorFramework zookeeperClient, String path) throws Exception {
        CreateMode createMode = CreateMode.PERSISTENT;
        zookeeperClient.create().creatingParentsIfNeeded().withMode(createMode).forPath(path);
    }

    /**
     * 创建临时序列节点
     * 创建后，节点path末尾会追加一个10位数的单调递增的序列，回话结束节点会自动删除
     */
    public static String createTempSeqNode(CuratorFramework zookeeperClient, String path) throws Exception {
        CreateMode createMode = CreateMode.EPHEMERAL_SEQUENTIAL;
        return zookeeperClient.create().creatingParentsIfNeeded().withMode(createMode).forPath(path);
    }

    /**
     * 设置节点的数据
     */
    public static void setNodeData(CuratorFramework zookeeperClient, String path, String data) throws Exception {
        zookeeperClient.setData().forPath(path, data.getBytes());
    }

    /**
     * 获取某个节点下的子节点
     *
     * @param path
     * @return
     */
    public static List<String> getChildrenNode(CuratorFramework zookeeperClient, String path) throws Exception {
        return zookeeperClient.getChildren().forPath(path);
    }

    /**
     * 获取某个节点下的数据
     *
     * @param path
     * @return
     */
    public static String getNodeData(CuratorFramework zookeeperClient, String path) throws Exception {
        return new String(zookeeperClient.getData().forPath(path));
    }
}
