package rpc;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class HelloClient3 {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);
        //RpcClientProxy rpcProxy = context.getBean(RpcClientProxy.class);

        int loopCount = 100;

        long start = System.currentTimeMillis();

        DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
        for (int i = 0; i < loopCount; i++) {
            String result = helloService.sayHello("World");
            System.out.println(result);
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("loop: " + loopCount);
        System.out.println("time: " + time + "ms");
        System.out.println("tps: " + (double) loopCount / ((double) time / 1000));

        System.exit(0);
    }
}
