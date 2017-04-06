package com.restaurant.dinner.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Redis连接池
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
class RedisConnectionPool {
    private static JedisPool jedisPool;

    protected static void initRedis() throws Exception {
        Map<String, String> properties = PropertiesLoader.getProperties();
        boolean isOpen = PropertiesLoader.REDIS_DEFAULT_OPEN;
        if (properties.containsKey(PropertiesLoader.KEY_REDIS_OPEN)) {
            try {
                isOpen = Boolean.parseBoolean(properties.get(PropertiesLoader.KEY_REDIS_OPEN));
            } catch (Exception e) {
            }
        }

        if (isOpen) {
            System.out.println("Kitchen缓存模块：Redis缓存启用，开始初始化");

            String password = PropertiesLoader.REDIS_DEFAULT_PASSWORD;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_PASSWORD)) {
                String propPwd = properties.get(PropertiesLoader.KEY_REDIS_PASSWORD);
                if (propPwd != null && !propPwd.isEmpty()) {
                    password = propPwd;
                }
            }
            if (password == null) {
                throw new Exception("cache.properties中未配置Redis密码");
            }

            String ip = PropertiesLoader.REDIS_DEFAULT_IP;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_IP)) {
                String propId = properties.get(PropertiesLoader.KEY_REDIS_IP);
                if (propId != null && !propId.isEmpty()) {
                    ip = propId;
                }
            }
            int port = PropertiesLoader.REDIS_DEFAULT_PORT;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_PORT)) {
                Integer propPort = Integer.parseInt(properties.get(PropertiesLoader.KEY_REDIS_PORT));
                if (propPort != null && propPort.intValue() > 0) {
                    port = propPort;
                }
            }
            System.out.println("Redis Host:" + ip + ":" + port);

            int maxTotal = PropertiesLoader.REDIS_DEFAULT_MAX_TOTAL;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_MAX_TOTAL)) {
                Integer propMaxTotal = Integer.parseInt(properties.get(PropertiesLoader.KEY_REDIS_MAX_TOTAL));
                if (propMaxTotal != null && propMaxTotal.intValue() >= 0) {
                    maxTotal = propMaxTotal;
                }
            }
            System.out.println("Redis Client最大连接数:" + maxTotal);

            int maxIdle = PropertiesLoader.REDIS_DEFAULT_MAX_IDLE;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_MAX_IDLE)) {
                Integer propMaxIdle = Integer.parseInt(properties.get(PropertiesLoader.KEY_REDIS_MAX_IDLE));
                if (propMaxIdle != null && propMaxIdle.intValue() >= 0) {
                    maxIdle = propMaxIdle;
                }
            }
            System.out.println("Redis Client最大空闲数:" + maxIdle);

            int maxWaitMillis = PropertiesLoader.REDIS_DEFAULT_MAX_WAIT_MILLIS;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_MAX_WAIT_MILLIS)) {
                Integer propMaxWaitMillis = Integer.parseInt(properties.get(PropertiesLoader.KEY_REDIS_MAX_WAIT_MILLIS));
                if (propMaxWaitMillis != null && propMaxWaitMillis.intValue() >= 0) {
                    maxWaitMillis = propMaxWaitMillis;
                }
            }
            System.out.println("Redis Client获取Jedis超时时间:" + maxWaitMillis + "毫秒");

            int connectionTimeout = PropertiesLoader.REDIS_DEFAULT_CONNECTION_TIMEOUT;
            if (properties.containsKey(PropertiesLoader.KEY_REDIS_CONNECTION_TIMEOUT)) {
                Integer propConnectionTimeout = Integer.parseInt(properties.get(PropertiesLoader.KEY_REDIS_CONNECTION_TIMEOUT));
                if (propConnectionTimeout != null && propConnectionTimeout.intValue() >= 0) {
                    connectionTimeout = propConnectionTimeout;
                }
            }
            System.out.println("Redis Client连接超时时间:" + connectionTimeout + "毫秒");

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWaitMillis);
            config.setTestOnBorrow(true);//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

            jedisPool = new JedisPool(config, ip, port, connectionTimeout, password);
        } else {
            System.out.println("Kitchen缓存模块：未启用Redis缓存，如需使用Redis缓存，请在配置文件(.properties)中配置相关属性，启用Redis缓存");
        }
    }

    /**
     * 从Jedis资源池中获取Jedis对象
     * @return
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 释放Jedis对象到Jedis资源池
     * @param jedis
     */
    public static void releaseJedis(Jedis jedis) {
        jedis.close();
    }

    @Override
    protected void finalize() throws java.lang.Throwable {
        System.out.println("Redis连接池资源清理");
        jedisPool.close();

        super.finalize();
    }

}
