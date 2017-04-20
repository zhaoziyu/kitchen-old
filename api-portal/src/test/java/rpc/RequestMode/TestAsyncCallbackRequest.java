package rpc.RequestMode;

import com.kitchen.rpc.client.RpcClientCallback;
import com.kitchen.rpc.client.RpcClientProxy;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoNioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.SpringConfig;

/**
 * 测试异步回调方式的请求
 *
 * @date 2016-12-22
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TestAsyncCallbackRequest {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(SpringConfig.configXmlName);

        String result = testAsyncCallback();

        if (result == null) {
            System.out.println("调用返回值为NULL");
        }
        System.out.println("调用完方法直接返回");
    }

    private static String testAsyncCallback() {
        DemoNioService helloService3 = RpcClientProxy.createAsyncCallback(DemoNioService.class, new RpcClientCallback() {
            @Override
            public void success(Object result) {
                String resultData = (String) result;
                System.out.println("回调方法打印结果：" + resultData);
            }

            @Override
            public void fail(Throwable e) {
                e.printStackTrace();
            }
        });
        DemoPerson demoPerson = new DemoPerson();
        demoPerson.setName("赵梓彧");
        demoPerson.setPhone("18805310950");
        String result = helloService3.sayHello(demoPerson);

        return result;
    }
}
