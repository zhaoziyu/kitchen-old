package com.kitchen.rpc.registry.zookeeper.registry;

import com.kitchen.rpc.registry.zookeeper.CommonCuratorTool;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * ZooKeeper注册者的客户端
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-15
 */
public class RegistryCurator {
    /**
     * ZooKeeper客户端
     */
    private static CuratorFramework ZOOKEEPER_CLIENT;

    /**
     * 创建ZooKeeper客户端，并开启与ZooKeeper服务器的连接
     *
     * @param zooKeeperAddress ZooKeeper服务器地址，格式：ip:port
     */
    public static void connectionZooKeeperServer(String zooKeeperAddress, RetryPolicy retryPolicy) {
        // 创建 ZooKeeper 客户端
        ZOOKEEPER_CLIENT = CommonCuratorTool.connectionZooKeeperServer(zooKeeperAddress, retryPolicy);
        ZOOKEEPER_CLIENT.start();
    }

    /**
     * 添加ZooKeeper连接状态监听器
     *
     * @param connectionStateListener 监听器
     */
    public static void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        // 添加链接状态监听器
        CommonCuratorTool.addConnectionStateListener(ZOOKEEPER_CLIENT, connectionStateListener);
    }

    /**
     * 断开ZooKeeper的客户端与服务器的连接
     */
    public static void disconnectionZooKeeperServer() {
        CommonCuratorTool.disconnectionZooKeeperServer(ZOOKEEPER_CLIENT);
    }

    /**
     * 检查节点是否存在
     */
    public static boolean checkNodeExist(String path) {
        try {
            return CommonCuratorTool.checkNodeExist(ZOOKEEPER_CLIENT, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建永久节点
     * 创建节点后，不删除就永久存在
     */
    public static void createFixNode(String path) {
        try {
            CommonCuratorTool.createFixNode(ZOOKEEPER_CLIENT, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建临时序列节点
     * 创建后，节点path末尾会追加一个10位数的单调递增的序列，回话结束节点会自动删除
     */
    public static String createTempSeqNode(String path) {
        try {
            return CommonCuratorTool.createTempSeqNode(ZOOKEEPER_CLIENT, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置节点的数据
     */
    public static void setNodeData(String path, String data) {
        try {
            CommonCuratorTool.setNodeData(ZOOKEEPER_CLIENT, path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
