package com.kitchen.rpc.server.deploy;

import com.kitchen.rpc.server.deploy.host.HostFetchType;
import com.kitchen.rpc.server.deploy.host.HostFetcher;
import com.kitchen.rpc.server.deploy.host.card.NetworkCardHostFetcher;
import com.kitchen.rpc.server.deploy.host.def.DefaultHostFetcher;
import com.kitchen.rpc.server.deploy.host.fix.FixHostFetcher;
import com.kitchen.rpc.server.deploy.host.prefix.PrefixHostFetcher;
import com.kitchen.rpc.server.deploy.host.wan.WanHostFetcher;
import com.kitchen.rpc.server.deploy.port.PortFetchType;
import com.kitchen.rpc.server.deploy.port.PortFetcher;
import com.kitchen.rpc.server.deploy.port.def.DefaultPortFetcher;
import com.kitchen.rpc.server.deploy.port.fix.FixPortFetcher;
import com.kitchen.rpc.server.deploy.port.random.RandomPortFetcher;

/**
 * 宿主主机发布信息查询的工厂类
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ServerDeployFetcherFactory {

    /**
     * 创建宿主主机IP地址的查询器
     *
     * @param type 指定IP地址查询方式
     * @return
     */
    public static HostFetcher createHostFetcher(String type) {
        if (type == null || type.isEmpty()) {
            throw new RuntimeException("初始化异常：未指定rpc.provider.host.type参数，请检查rpc-provider.properties");
        }
        type = type.toUpperCase();
        HostFetchType fetchType;
        try {
            fetchType = HostFetchType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("初始化异常：rpc.provider.host.type参数超出规定范围，请检查rpc-provider.properties");
        }

        HostFetcher hostFetcher = null;
        switch (fetchType) {
            case FIX:
                hostFetcher = new FixHostFetcher();
                break;
            case PREFIX:
                hostFetcher = new PrefixHostFetcher();
                break;
            case CARD:
                hostFetcher = new NetworkCardHostFetcher();
                break;
            case WAN:
                hostFetcher = new WanHostFetcher();
                break;
            case DEFAULT:
                hostFetcher = new DefaultHostFetcher();
                break;

        }
        return hostFetcher;
    }

    /**
     * 创建宿主主机端口的查询器
     *
     * @param type 指定端口查询方式
     * @return
     */
    public static PortFetcher createPortFetcher(String type) {
        PortFetchType fetchType;
        if (type == null || type.isEmpty()) {
            fetchType = PortFetchType.DEFAULT;
        } else {
            type = type.toUpperCase();
            try {
                fetchType = PortFetchType.valueOf(type);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("初始化异常：rpc.provider.port.type参数超出规定范围，请检查rpc-provider.properties");
            }
        }

        PortFetcher portFetcher = null;
        switch (fetchType) {
            case FIX:
                portFetcher = new FixPortFetcher();
                break;
            case RANDOM:
                portFetcher = new RandomPortFetcher();
                break;
            case DEFAULT:
                portFetcher = new DefaultPortFetcher();
                break;
        }
        return portFetcher;
    }

}
