package rpc;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * 测试
 *
 * @date 2016-11-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class MultithreadingClient1 {
    private static int index = 0;
    private static int success = 0;
    private static int users = 2;
    private static void showInfo() {
        success++;
        System.out.println("成功：" + success);
    }
    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);
        //RpcClientProxy rpcProxy = context.getBean(RpcClientProxy.class);

        // TODO 单台并发至少做到百万级（服务处理时间在500ms），增大ZooKeeper的处理量，后端应当有排队机制（参考策略：设置最大并发量，超出进入队列）
        for (int i = 0; i < users; i++) {
            index = i + 1;
            Thread t = new Thread(() -> {
                try {
                    DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
                    success += helloService.doSomething(new Date(), index);
                    showInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        System.out.println(users + " 名用户发起请求");

        DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
        success += helloService.doSomething(new Date(), index);
        showInfo();

        System.exit(0);
    }
}
