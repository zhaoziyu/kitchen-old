package com.kitchen.rpc.registry.zookeeper.discovery;

import com.kitchen.rpc.common.protocol.RpcRequest;
import com.kitchen.rpc.registry.RpcServiceDiscovery;
import com.kitchen.rpc.registry.cache.DiscoveryCache;
import com.kitchen.rpc.registry.zookeeper.ZooKeeperConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.*;

/**
 * 基于 ZooKeeper 的服务发现接口实现
 *
 * @date 2016-12-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ZooKeeperServiceDiscovery implements RpcServiceDiscovery {
    static String zkAddress;

    public ZooKeeperServiceDiscovery(String address) {
        zkAddress = address;
        try {
            initZooKeeperClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String discover(String serviceName, RpcRequest rpcRequest) {
        String serviceAddress = DiscoveryCache.getServiceAddress(serviceName, rpcRequest);

        return serviceAddress;
    }

    @Override
    public void stop() {
        // 关闭ZooKeeper节点检查者
        DiscoveryCurator.destroyZooKeeperTreeCache();
        // 关闭ZooKeeper连接
        DiscoveryCurator.disconnectionZooKeeperServer();
    }

    /**
     * ZooKeeper初始化
     *
     * @throws InterruptedException
     */
    public static void initZooKeeperClient() throws InterruptedException {
        System.out.println("连接ZooKeeper目录服务器中......");

        // 创建 ZooKeeper 客户端
        RetryPolicy retryPolicy = new RetryUntilElapsed(1000 * 60 * 10, 1000); // 尝试重连10分钟，每秒一次
        //RetryPolicy retryPolicy = new RetryNTimes(Integer.MAX_VALUE, 1000); // 尝试无限次重连，每一秒尝试一次
        DiscoveryCurator.connectionZooKeeperServer(zkAddress, retryPolicy);

        DiscoveryConnectionStateListener connectionStateListener = new DiscoveryConnectionStateListener();
        DiscoveryCurator.addConnectionStateListener(connectionStateListener);
    }

    /**
     * 订阅全部服务即服务提供者的访问地址
     */
    public static void subscribeAllServiceAddress() {
        System.out.println("订阅服务");
        HashMap<String, LinkedHashMap<String, Integer>> tempServiceAddressCache = new HashMap<>();
        try {
            String registryPath = ZooKeeperConfig.ZK_REGISTRY_PATH;
            if (!DiscoveryCurator.checkNodeExist(registryPath)) {
                throw new RuntimeException(String.format("ZooKeeper中不存在" + ZooKeeperConfig.ZK_REGISTRY_PATH + "目录"));
            }

            // 从ZooKeeper中获取服务列表
            List<String> servicePathList = DiscoveryCurator.getChildrenNode(registryPath);
            for (String servicePath : servicePathList) {
                List<String> addressPathList = DiscoveryCurator.getChildrenNode(registryPath + "/" + servicePath);
                LinkedHashMap<String, Integer> addressMap = new LinkedHashMap<>();
                for (String addressPath : addressPathList) {
                    String path = registryPath + "/" + servicePath + "/" + addressPath;
                    String data = DiscoveryCurator.getNodeData(path);

                    // 服务目录中保存的格式为：ip:port|权重
                    String [] arrData = data.split("\\|");
                    addressMap.put(arrData[0], Integer.parseInt(arrData[1]));
                }
                tempServiceAddressCache.put(servicePath, addressMap);

                // 输出订阅信息
                System.out.println("订阅了：" + servicePath + "服务");
                System.out.println("可用服务地址：");
                Iterator iterator = addressMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    System.out.println("地址：" + entry.getKey() + " 权重：" + entry.getValue());
                }
            }
            DiscoveryCache.setServiceAddressCache(tempServiceAddressCache);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 监听path路径下的所有子节点
     */
    public static void watcherPath(String path) {
        // 创建节点缓存
        DiscoveryCurator.createZooKeeperTreeCache(path);

        // 添加节点监听器
        DiscoveryTreeCacheListener treeCacheListener = new DiscoveryTreeCacheListener();
        DiscoveryCurator.addTreeCacheListener(treeCacheListener);
    }
}
