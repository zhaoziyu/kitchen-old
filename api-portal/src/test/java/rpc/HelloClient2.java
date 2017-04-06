package rpc;

import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class HelloClient2 {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);

        Thread.sleep(2000);

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(() -> {
                try {
                    DemoService demoRpcService = RpcClientProxy.createSync(DemoService.class);
                    DemoPerson result = demoRpcService.getDemoPerson(1);

                    if (result != null) {
                        System.out.println(result.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }
}
