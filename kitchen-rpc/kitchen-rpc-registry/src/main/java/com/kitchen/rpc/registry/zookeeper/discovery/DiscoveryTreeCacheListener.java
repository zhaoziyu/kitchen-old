package com.kitchen.rpc.registry.zookeeper.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * 监听ZooKeeper的节点状态
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-03-15
 */
public class DiscoveryTreeCacheListener implements TreeCacheListener {
    @Override
    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
        boolean updated = false;
        switch (treeCacheEvent.getType()) {
            case NODE_ADDED:
                System.out.println("[新增]节点: " + treeCacheEvent.getData().getPath()+" , 数据: "+new String(treeCacheEvent.getData().getData()));
                updated = true;
                break;
            case NODE_UPDATED:
                System.out.println("[更新]节点: " + treeCacheEvent.getData().getPath()+" , 数据: "+new String(treeCacheEvent.getData().getData()));
                updated = true;
                break;
            case NODE_REMOVED:
                System.out.println("[移除]节点: " + treeCacheEvent.getData().getPath());
                updated = true;
                break;
            default:
                break;
        }
        if (updated) {
            // 刷新Discovery缓存
            ZooKeeperServiceDiscovery.subscribeAllServiceAddress();
        }
    }
}
