package com.restaurant.dinner.api.recipe.demo;

import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;

import java.util.Date;

/**
 * Demo
 *
 * @date 2016-12-21
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface DemoNioService {
    String sayHello(String name);

    String sayHello(DemoPerson person);

    int doSomething(Date requestTime, int index);

    void voidReturnSomething(String name);
}
