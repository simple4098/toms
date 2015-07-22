package com.fanqielaile.toms.support.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * 符合spring Cache接口标准的mem实现
 *
 * @author lei
 */
public class MemcachedCache implements Cache {
    private final String name;
    private final MemcachedClient memcachedClient;
    private final BaseMemCache basememCache;

    public MemcachedCache(String name, int expire,
                          MemcachedClient memcachedClient) {
        this.name = name;
        this.memcachedClient = memcachedClient;
        this.basememCache = new BaseMemCache(name, expire, memcachedClient);
    }

    @Override
    public void clear() {
        basememCache.flush();
    }

    @Override
    public void evict(Object key) {
        basememCache.delete(String.valueOf(key));
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object value = basememCache.get(String.valueOf(key));
        if (value != null)
            wrapper = new SimpleValueWrapper(value);
        return wrapper;
    }

    @Override
    public Object get(Object key, Class type) {
        Object value = basememCache.get(String.valueOf(key));
        if (value != null && type != null && !type.isInstance(value))
            throw new IllegalStateException((new StringBuilder())
                    .append("Cached value is not of required type [")
                    .append(type.getName()).append("]: ").append(value)
                    .toString());
        else
            return value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public MemcachedClient getNativeCache() {
        return this.memcachedClient;
    }

    @Override
    public void put(Object key, Object value) {
        basememCache.put(key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return get(o);
    }

}
