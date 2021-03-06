package com.restaurant.dinner.provider.service.impl.demo;

import com.kitchen.rpc.common.RpcService;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Demo
 *
 * @date 2016-12-26
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@RpcService
public class DemoServiceRpcImpl implements DemoService {

    @Resource(name="demoServiceImpl")
    private DemoService demoService;

    @Override
    public DemoPerson getDemoPerson(int userId) {
        System.out.println("业务处理中 " + new Date());
        return demoService.getDemoPerson(userId);
    }
}
