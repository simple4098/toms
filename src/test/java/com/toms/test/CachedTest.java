package com.toms.test;

import com.fanqielaile.toms.service.impl.UserInfoService;
import com.fanqielaile.toms.support.memcached.MemcachedCacheManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.Cache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-memcached.xml"})
public class CachedTest {
    @Resource
    private MemcachedCacheManager cacheManager;


    @Test
    @Ignore
    public void testCached() {
        Cache cache = cacheManager.getCache("test");
        cache.clear();
        cache.put("test", "test_v");
        cache.put("test_v", "test_1");
        Cache.ValueWrapper test = cache.get("test");
        System.out.println("=============>" + test.get());
    }

    @Test
    @Ignore
    public void testCachedService() {
        Cache ca = cacheManager.getCache("toms_user");
    }
}
