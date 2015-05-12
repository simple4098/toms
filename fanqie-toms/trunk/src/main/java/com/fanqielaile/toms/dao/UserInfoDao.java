package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface UserInfoDao {
   int insertUserInfo(UserInfo userInfo);

   UserInfo selectUserInfoByLoginName(@Param("loginName") String loginName);
}
