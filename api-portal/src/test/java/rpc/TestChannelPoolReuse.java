package rpc;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 测试客户端至服务端的RPC连接通道（Channel）的复用
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TestChannelPoolReuse {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        Thread.sleep(2000);
        System.out.println("------------------------开始并发测试------------------------" + sdf.format(new Date()));
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < 1000; i ++) {
            Thread thread = new Thread(() -> {
                try {
                    DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
                    String result = helloService.sayHello("世界");
                    System.out.println(result + " 时间：" + sdf.format(new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads.add(thread);
        }
        System.out.println("------------------------线程初始化完毕------------------------" + sdf.format(new Date()));
        for (Thread thread : threads) {
            thread.start();
        }
        System.out.println("------------------------线程全部启动完毕------------------------" + sdf.format(new Date()));
    }
}
