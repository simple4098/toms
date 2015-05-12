package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface IUserInfoService extends UserDetailsService {
    int createUserInfo(UserInfo userInfo);
}
