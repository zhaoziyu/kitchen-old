package com.restaurant.dinner.request.proxy.demo;

import com.restaurant.dinner.request.GlobalConfig;
import com.restaurant.dinner.request.api.DemoService;
import com.restaurant.dinner.request.domain.DemoDomain;
import com.restaurant.dinner.request.proxy.BaseHttpProxy;

import java.io.IOException;

/**
 * HTTP请求代理示例
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DemoServiceHttpProxy extends BaseHttpProxy {
    private DemoService demoService;

    public DemoServiceHttpProxy() {
        super(GlobalConfig.ROOT_PATH_HTTP);

        demoService = this.createApiInstance(DemoService.class);
    }

    public DemoDomain test(String id) {
        try {
            return demoService.test(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
