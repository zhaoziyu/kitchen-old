package common.security;

import com.restaurant.dinner.market.common.security.KitEncryptionCustom;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 测试PBKDF2加密算法
 *
 * @date 2017-01-01
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class TestPBKDF2 {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(() -> {
                try {
                    System.out.println(KitEncryptionCustom.encrypt("admin"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        /*Pair<String, String> triplet = KitEncryptionPBKDF2.encryptByRandomSalt("admin");
        System.out.println(triplet);
        System.out.println(KitEncryptionPBKDF2.encryptByFixSalt("admin", triplet.getValue0()));*/
        Thread.sleep(10000);
    }
}
