package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

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
   List<UserInfoDto> selectUserInfos(@Param("companyId") String companyId);

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

   /**
    * 修改用户的数据权限
    *
    * @param id
    * @param dataPermission
    * @return
    */
   int updateUserDataPermission(@Param("id") String id, @Param("dataPermission") int dataPermission);

   /**
    * 查询公司其他员工
    *
    * @param userInfo
    * @return
    */
   List<UserInfo> selectOtherUserInfoById(UserInfo userInfo);

   /**
    * 分页测试
    *
    * @param u
    * @param pageBounds
    * @return
    */
   List<UserInfo> selectUserInfoByPage(UserInfo u, PageBounds pageBounds);

   /**
    * 分页查询公司下属
    *
    * @param companyId
    * @param pageBounds
    * @return
    */
   List<UserInfoDto> selectUserInfoByPagination(@Param("companyId") String companyId, PageBounds pageBounds);

   void deleteUserInfoByCompanyId(@Param("companyId")String companyId);
}
