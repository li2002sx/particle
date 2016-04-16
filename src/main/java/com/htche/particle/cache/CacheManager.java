package com.htche.particle.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Title: CacheManager
 * @Package: com.htche.particle.cache
 * @Description: (用一句话描述该文件做什么)
 * @author: jankie.li@Mtime.com
 * @date: 2016/4/15 14:09
 * @version: V1.0
 */
public class CacheManager {
    private static Map<String, Object> caches;

    private CacheManager() {}

    static {
        caches = new HashMap<String, Object>();
    }

    //用于保存缓存
    public static void addCache(String key, Object value) {
        caches.put(key, value);
    }

    //用于得到缓存
    public static Object getCache(String key) {
        return caches.get(key);
    }

    //用于清除缓存信息
    public static void clearCache() {
        caches.clear();
    }

    //用于清除指定的缓存信息
    public static void removeCache(String key) {
        caches.remove(key);
    }
}
