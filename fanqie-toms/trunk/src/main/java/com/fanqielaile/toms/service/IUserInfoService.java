package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface IUserInfoService extends UserDetailsService {
    /**
     * 新增用户
     *
     * @param userInfo
     * @return
     */
    boolean createUserInfo(UserInfo userInfo, List<String> permissionIdList);

    /**
     * 根据登陆账户查询用户
     *
     * @param loginName
     * @return
     */
    UserInfo findUserInfoByLoginName(String loginName);

    /**
     * 查询公司下的员工
     *
     * @param companyId
     * @return
     */
    List<UserInfo> findUserInfos(String companyId);

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    UserInfo findUserInfoById(String id);

    /**
     * 更新员工信息
     *
     * @param userInfo
     * @return
     */
    boolean modifyUserInfo(UserInfo userInfo);

    /**
     * 删除员工
     *
     * @param id
     * @return
     */
    boolean removeUserInfo(String id);
}
