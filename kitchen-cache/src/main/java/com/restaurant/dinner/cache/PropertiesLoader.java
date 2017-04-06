package com.restaurant.dinner.cache;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存模块属性配置加载器
 *
 * @date 2017-01-01
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Component
class PropertiesLoader {
    private static Map<String, String> properties;

    public static Boolean LOCAL_DEFAULT_OPEN = true;             //（默认）是否开启本地缓存

    public static Boolean REDIS_DEFAULT_OPEN = false;            //（默认）是否开启Redis缓存
    public static String REDIS_DEFAULT_IP = "127.0.0.1";        //（默认）Redis服务器IP地址
    public static int REDIS_DEFAULT_PORT = 6379;                 //（默认）Redis服务器端口
    public static int REDIS_DEFAULT_MAX_TOTAL = 8;               //（默认）最大连接数
    public static int REDIS_DEFAULT_MAX_IDLE = 8;                //（默认）最大空闲数
    public static int REDIS_DEFAULT_MAX_WAIT_MILLIS = 0;         //（默认）从连接池等待获取Jedis连接的超时时间
    public static int REDIS_DEFAULT_CONNECTION_TIMEOUT = 2000;   //（默认）从连接池等待获取Jedis连接的超时时间
    public static String REDIS_DEFAULT_PASSWORD = null;          //（默认）密码


    public static String KEY_LOCAL_OPEN = "kitchen.cache.local.open";

    public static String KEY_REDIS_OPEN = "kitchen.cache.redis.open";
    public static String KEY_REDIS_IP = "kitchen.cache.redis.ip";
    public static String KEY_REDIS_PORT = "kitchen.cache.redis.port";
    public static String KEY_REDIS_MAX_TOTAL = "kitchen.cache.redis.max_total";
    public static String KEY_REDIS_MAX_IDLE = "kitchen.cache.redis.max_idle";
    public static String KEY_REDIS_MAX_WAIT_MILLIS = "kitchen.cache.redis.max_wait_millis";
    public static String KEY_REDIS_PASSWORD = "kitchen.cache.redis.password";
    public static String KEY_REDIS_CONNECTION_TIMEOUT = "kitchen.cache.redis.connection.timeout";

    static {
        properties = new ConcurrentHashMap<>();
        // 加载配置文件
        InputStream input = PropertiesLoader.class.getResourceAsStream("/cache.properties");
        if (input != null) {
            Properties prop = new Properties();
            try {
                prop.load(input);
                Iterator<String> iterator = prop.stringPropertyNames().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    properties.put(key, prop.getProperty(key));
                }
            } catch (IOException e) {
                System.out.println("cache.properties配置文件读取异常");
                e.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("cache.properties配置文件不存在");
        }

        // 初始化缓存
        CacheHelperOnLocal.initLocalCache();
        try {
            RedisConnectionPool.initRedis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getProperties() {
        return properties;
    }
}
