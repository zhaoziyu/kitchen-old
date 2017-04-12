package com.restaurant.dinner.provider.service.impl.demo;

import com.kitchen.rpc.common.RpcService;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Demo
 *
 * @date 2016-11-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@RpcService // 若 RPC 接口拥有多个实现类，则需要在 RpcService 注解中指定 version 属性加以区分
public class DemoNioServiceImpl implements DemoNioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoNioServiceImpl.class);

    @Override
    public String sayHello(String name) {
        LOGGER.info("已调用sayHello");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello! " + name;
    }

    @Override
    public String sayHello(DemoPerson person) {
        return "Hello! " + person.getName() + " " + person.getPhone();
    }

    @Override
    public int doSomething(Date requestTime, int index) {
        long curTime = new Date().getTime();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("provider：线程" + index + " 请求到达时间：" + curTime + " 处理时长：" + (curTime - requestTime.getTime()));

        return 1;
    }

    @Override
    public void voidReturnSomething(String name) {
        System.out.println(name + "成功调用无反回值的方法");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "调用的无返回值方法处理完成");
    }
}
