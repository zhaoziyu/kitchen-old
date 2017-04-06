package rpc.RequestMode;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.SpringConfig;

/**
 * 测试最简单的异步无返回结果的请求
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TestAsyncRequest {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);

        DemoNioService helloService = RpcClientProxy.createAsync(DemoNioService.class);
        helloService.voidReturnSomething("00:01");
        helloService.voidReturnSomething("00:02");

        System.out.println("调用完方法直接返回，查看服务器处理结果");
        Thread.sleep(100);// TODO 连接如果直接断了，服务器则不会处理请求
        System.exit(1);
    }
}
