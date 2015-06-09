package com.fanqielaile.toms.support.listener;

import com.fanqielaile.toms.support.event.TomsApplicationEvent;
import com.fanqielaile.toms.support.security.ReloadableRequestMapResolver;
import org.springframework.context.ApplicationListener;

/**
 * Created by wangdayin on 2015/6/5.
 */
public class RolePermissionChangeListener implements ApplicationListener<TomsApplicationEvent> {

    private ReloadableRequestMapResolver requestMapResolver;

    /**
     * 事件的处理方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(TomsApplicationEvent event) {
        requestMapResolver.refresh();
    }

    public void setRequestMapResolver(ReloadableRequestMapResolver requestMapResolver) {
        this.requestMapResolver = requestMapResolver;
    }
}
