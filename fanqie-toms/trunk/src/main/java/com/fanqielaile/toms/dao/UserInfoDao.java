package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface UserInfoDao {
   /**
    * 新增用户（员工）
    *
    * @param userInfo
    * @return
    */
   int insertUserInfo(UserInfo userInfo);

   /**
    * 根据登陆账户查询用户
    * @param loginName 登录名
    * @return
    */
   UserInfo selectUserInfoByLoginName(@Param("loginName") String loginName);

   /**
    * 根据公司ID查询下属员工
    *
    * @param companyId
    * @return
    */
   List<UserInfo> selectUserInfos(@Param("companyId") String companyId);

   /**
    * 根据ID查询用户
    *
    * @param id
    * @return
    */
   UserInfo selectUserInfoById(@Param("id") String id);

   /**
    * 更新用户信息
    *
    * @param userInfo
    * @return
    */
   int updateUserInfo(UserInfo userInfo);

   /**
    * 删除用户
    *
    * @param id
    * @return
    */
   int deleteUserInfo(@Param("id")String id);

   int updateUserDataPermission(@Param("id") String id, @Param("dataPermission") int dataPermission);
}
