package com.restaurant.dinner.provider;

import com.kitchen.rpc.server.RpcServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RPC服务提供者的启动类（入口类）
 *
 * @date 2016-11-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ProviderBootstrap {

    public static void main(String[] args) {
        // 注册关闭监听
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // 退出前关闭 RPC Server
                RpcServer.shutdownGracefully();
            }
        });
        // 启动RPC服务端
        RpcServer.APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("spring-provider.xml");
    }
}
