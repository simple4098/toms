package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
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
    void createUserInfo(UserInfo userInfo, List<Permission> permissionIdList);

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
    List<UserInfoDto> findUserInfos(String companyId);

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
    void modifyUserInfo(UserInfo userInfo);

    /**
     * 删除员工
     *
     * @param id
     * @return
     */
    void removeUserInfo(String id, String replaceUserid);

    /**
     * 更新用户权限
     *
     * @param userInfo
     * @param permissionsList
     */
    void modifyUserPermission(UserInfo userInfo, List<Permission> permissionsList, int dataPermission);

    /**
     * 查询公司其他员工
     *
     * @param userInfo
     * @return
     */
    List<UserInfo> findOtherUserInfoById(UserInfo userInfo);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void removeUserInfo(String id);

    /**
     * 分页查询公司下属
     *
     * @param companyId
     * @param pageBounds
     * @return
     */
    List<UserInfoDto> findUserInfoByPage(String companyId, PageBounds pageBounds);

}
