package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IUserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Service
public class UserInfoService implements IUserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public int createUserInfo(UserInfo userInfo) {
        userInfo.setId(UUID.randomUUID().toString());
        return this.userInfoDao.insertUserInfo(userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        UserInfo userInfo = this.userInfoDao.selectUserInfoByLoginName(loginName);
        Role role = userInfo.getRole();
        userInfo.setAuthorities(new HashSet<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(role.getRoleKey()))));
        return userInfo;
    }
}
