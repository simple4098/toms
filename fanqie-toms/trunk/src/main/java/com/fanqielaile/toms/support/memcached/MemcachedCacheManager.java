package com.fanqielaile.toms.support.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by wangdayin on 2015/7/17.
 */
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {
    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();
    private MemcachedClient memcachedClient;

    public MemcachedCacheManager() {
    }

    public MemcachedCacheManager(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Collection<Cache> values = cacheMap.values();
        return values;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache == null) {
            Integer expire = expireMap.get(name);
            if (expire == null) {
                expire = 0;
                expireMap.put(name, expire);
            }
            //cache标准接口的Memcahe实现类
            cache = new MemcachedCache(name, expire.intValue(), memcachedClient);
            cacheMap.put(name, cache);
        }
        return cache;
    }

    public void clear() {
        MemcachedCache cache = new MemcachedCache(null, 0, memcachedClient);
        cache.clear();
    }

    public void evict(String value, String key) {
        MemcachedCache cache = new MemcachedCache(value, 0, memcachedClient);
        cache.evict(key);
    }

}
