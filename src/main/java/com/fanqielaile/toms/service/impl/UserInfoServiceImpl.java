package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public int createUserInfo(UserInfo userInfo) {
        return this.userInfoDao.insertUserInfo(userInfo);
    }
}
