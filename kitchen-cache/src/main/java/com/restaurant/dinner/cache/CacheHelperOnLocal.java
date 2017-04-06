package com.restaurant.dinner.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存操作类
 * 只提供最基本的操作，其它高级操作需从业务逻辑中实现
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class CacheHelperOnLocal {
    private static Map<String, Object> cacheMap = null;

    protected static void initLocalCache() {
        Map<String, String> properties = PropertiesLoader.getProperties();

        boolean isOpen = PropertiesLoader.LOCAL_DEFAULT_OPEN;
        if (properties.containsKey(PropertiesLoader.KEY_LOCAL_OPEN)) {
            try {
                isOpen = Boolean.parseBoolean(properties.get(PropertiesLoader.KEY_LOCAL_OPEN));
            } catch (Exception e) {
            }
        }
        if (isOpen) {
            System.out.println("Kitchen缓存模块：本地Map缓存开启，开始初始化");
            cacheMap = new ConcurrentHashMap<>();
        } else {
            System.out.println("Kitchen缓存模块：未开启本地Map缓存，如需使用，请在配置文件(.properties)中配置相关属性，启用本地缓存");
        }
    }

    public static void set(String key, Object value) {
        if (cacheMap != null) {
            cacheMap.put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) throws ClassCastException {
        if (cacheMap != null) {
            T obj;
            try {
                obj = (T) cacheMap.get(key);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassCastException("CacheHelperOnLocal.get():泛型中的对象类型错误");
            }
            return obj;
        }
        return null;
    }

    public static boolean isExist(String key) {
        if (cacheMap != null) {
            return cacheMap.containsKey(key);
        }
        return false;
    }

    public static void delete(String key) {
        if (cacheMap != null) {
            cacheMap.remove(key);
        }
    }
}
