package rpc;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试
 *
 * @date 2016-11-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class MultithreadingClient2 {
    private static int index = 0;
    private static int success = 0;

    private static void showInfo() {
        success++;
        System.out.println("成功：" + success);
    }

    private static void curTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(sdf.format(new Date()));
    }

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);
        //RpcClientProxy rpcProxy = context.getBean(RpcClientProxy.class);

        int threadNum = 3;
        final int requestNum = 1;
        Thread[] threads = new Thread[threadNum];

        Thread.sleep(1000);

        long startTime = System.currentTimeMillis();
        //benchmark for sync call
        for(int i = 0; i < threadNum; ++i){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    for (int i = 0; i < requestNum; i++) {
                        DemoNioService helloService = RpcClientProxy.createSync(DemoNioService.class);
                        success += helloService.doSomething(new Date(), index);
                        showInfo();
                    }
                }
            });
        }

        for(int i = 0; i < threadNum; ++i){
            threads[i].start();
        }
        for(int i=0; i<threads.length;i++){
            threads[i].join();
        }
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Sync call total-time-cost:%sms, req/s=%s",timeCost,((double)(requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        System.exit(0);
    }
}
