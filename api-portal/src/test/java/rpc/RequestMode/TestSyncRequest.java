package rpc.RequestMode;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.SpringConfig;

/**
 * 测试最简单的同步请求
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TestSyncRequest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);

        Thread.sleep(2000);

        DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
        String result = helloService.sayHello("世界");
        System.out.println(result);

        System.exit(1);
    }
}
