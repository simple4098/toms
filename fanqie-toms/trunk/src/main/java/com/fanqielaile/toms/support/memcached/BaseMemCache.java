package com.fanqielaile.toms.support.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * MemcachedClient实现
 *
 * @author lei
 */
public class BaseMemCache {
    private static Logger log = Logger.getLogger(BaseMemCache.class);

    private Set<String> keySet = new HashSet<String>();
    private final String name;
    private final int expire;
    private final MemcachedClient memcachedClient;

    public BaseMemCache(String name, int expire, MemcachedClient memcachedClient) {
        this.name = name;
        this.expire = expire;
        this.memcachedClient = memcachedClient;
    }

    public Object get(String key) {
        Object value = null;
        try {
            key = this.getKey(key);
            value = memcachedClient.get(key);
        } catch (TimeoutException e) {
            log.warn("获取 Memcached 缓存超时", e);
        } catch (InterruptedException e) {
            log.warn("获取 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("获取 Memcached 缓存错误", e);
        }
        return value;
    }

    public void put(String key, Object value) {
        if (value == null)
            return;

        try {
            key = this.getKey(key);
            memcachedClient.setWithNoReply(key, expire, value);
            keySet.add(key);
        } catch (InterruptedException e) {
            log.warn("更新 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("更新 Memcached 缓存错误", e);
        }
    }

    public void clear() {
        for (String key : keySet) {
            try {
                memcachedClient.deleteWithNoReply(this.getKey(key));
            } catch (InterruptedException e) {
                log.warn("删除 Memcached 缓存被中断", e);
            } catch (MemcachedException e) {
                log.warn("删除 Memcached 缓存错误", e);
            }
        }
    }

    public void flush() {
        try {
            memcachedClient.flushAll();
        } catch (TimeoutException e) {
            log.warn("删除 Memcached 超时", e);
        } catch (InterruptedException e) {
            log.warn("删除 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("删除 Memcached 缓存错误", e);
        }
    }

    public void delete(String key) {
        try {
            key = this.getKey(key);
            memcachedClient.deleteWithNoReply(key);
        } catch (InterruptedException e) {
            log.warn("删除 Memcached 缓存被中断", e);
        } catch (MemcachedException e) {
            log.warn("删除 Memcached 缓存错误", e);
        }
    }

    private String getKey(String key) {
        return name + "_" + key;
    }
}
