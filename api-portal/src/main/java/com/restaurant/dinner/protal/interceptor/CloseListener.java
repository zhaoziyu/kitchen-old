package com.restaurant.dinner.protal.interceptor;

import com.kitchen.rpc.client.RpcClientProxy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 接口模块关闭的监听器
 *
 * @date 2016-12-26
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class CloseListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent closedEvent) {
        System.out.println("应用正在退出......");
        RpcClientProxy.stop();

        System.out.println("5秒等待RPC相关资源的清理");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("资源清理完毕");
    }
}
