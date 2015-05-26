package com.fanqielaile.toms.support.tag;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Created by wangdayin on 2015/5/26.
 */
public abstract class BeanAvailableTag extends RequestContextAwareTag {
    private WebApplicationContext wac;

    @SuppressWarnings("unchecked")
    protected <T> T getBean(String beanName) {
        if (wac == null) {
            wac = getRequestContext().getWebApplicationContext();
        }
        return (T) wac.getBean(beanName);
    }

    protected <T> T getBean(Class<T> clazz) {
        if (wac == null) {
            wac = getRequestContext().getWebApplicationContext();
        }
        return (T) wac.getBean(clazz);
    }
}
