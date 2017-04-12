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
@RpcService(version = "sample.hello2")
public class DemoNioServiceImplV2 implements DemoNioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoNioServiceImplV2.class);
    @Override
    public String sayHello(String name) {
        LOGGER.info("已调用sayHello2");
        return "您好! " + name;
    }

    @Override
    public String sayHello(DemoPerson person) {
        return "您好! " + person.getName() + " " + person.getPhone();
    }

    @Override
    public int doSomething(Date requestTime, int index) {
        return 0;
    }

    @Override
    public void voidReturnSomething(String name) {
        System.out.println(name + "成功调用无法回值的方法");
    }
}
