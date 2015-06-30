package com.fanqielaile.toms.support.listener;

import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.event.TomsApplicationEvent;
import com.fanqielaile.toms.support.security.ReloadableRequestMapResolver;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;

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
        //获取修改时的对象
        Role role = (Role) event.getObjsource();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (null != authentication) {
            //获取当前缓存中的对象
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserInfo) {
                UserInfo userInfo = (UserInfo) principal;
                //判断当前缓存当中的值，是否与修改值一样，如果不一样修改缓存中的值
                if (userInfo.getDataPermission() != role.getDataPermission()) {
                    userInfo.setDataPermission(role.getDataPermission());
                }
            }
        }
    }

    public void setRequestMapResolver(ReloadableRequestMapResolver requestMapResolver) {
        this.requestMapResolver = requestMapResolver;
    }
}
