package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.RoleDao;
import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangdayin on 2015/5/11.
 */
@Service
public class UserInfoService implements IUserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private Md5PasswordEncoder passwordEncoder;
    @Resource
    private BangInnDao bangInnDao;
    @Override
    public boolean createUserInfo(UserInfo userInfo, List<Permission> permissionIdlist) {
        if (StringUtils.isNotEmpty(userInfo.getLoginName())) {
            UserInfo userInfo1 = userInfoDao.selectUserInfoByLoginName(userInfo.getLoginName());
            if (null == userInfo1) {
                //创建角色
                Role role = new Role();
                role.setId(UUID.randomUUID().toString());
                //查询最大的indexed，设置role_key
                int index = this.roleDao.selectMaxIndex();
                if (StringUtils.isNotEmpty(index + "")) {
                    role.setIndexed(index + 1);
                } else {
                    role.setIndexed(0);
                }
                role.setRoleKey("ROLE_" + role.getIndexed());
                role.setRoleDesc(role.getRoleKey());
                role.setRoleName(role.getRoleKey());
                this.roleDao.insertRole(role);
                //创建用户
                userInfo.setId(UUID.randomUUID().toString());
                userInfo.setRoleId(role.getId());
                //设置密码
                userInfo.setPassword(passwordEncoder.encodePassword(userInfo.getPassword(), null));
                userInfoDao.insertUserInfo(userInfo);
                //创建角色与权限的关系
                this.roleDao.deletePermissionsOfRole(role.getId());
                Role rolePermission = new Role();
                rolePermission.setId(UUID.randomUUID().toString());
                rolePermission.setRolePermissionRoleId(role.getId());
//                rolePermission.setPermissions(new HashSet<String>(permissionIdlist));
                rolePermission.setPermissionList(permissionIdlist);
                rolePermission.setCreatedDate(new Date());
                rolePermission.setUpdatedDate(new Date());
                this.roleDao.insertPermissionsForRole(rolePermission);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserInfo findUserInfoByLoginName(String loginName) {
        return userInfoDao.selectUserInfoByLoginName(loginName);
    }

    @Override
    public List<UserInfo> findUserInfos(String companyId) {
        return userInfoDao.selectUserInfos(companyId);
    }

    @Override
    public UserInfo findUserInfoById(String id) {
        return userInfoDao.selectUserInfoById(id);
    }

    @Override
    public boolean modifyUserInfo(UserInfo userInfo) {
        UserInfo userInfo1 = userInfoDao.selectUserInfoById(userInfo.getId());
        if (null != userInfo1) {
            userInfo.setPassword(passwordEncoder.encodePassword(userInfo.getPassword(), null));
            userInfoDao.updateUserInfo(userInfo);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeUserInfo(String id, String replaceUserId) {
        UserInfo userInfo = userInfoDao.selectUserInfoById(id);
        if (userInfo != null) {
            //删除员工之前将员工名下的管理客栈转移
            this.bangInnDao.updateBangInnUserId(id, replaceUserId);
            //删除员工
            userInfoDao.deleteUserInfo(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modifyUserPermission(UserInfo userInfo, List<Permission> permissionsList, int dataPermission) {
        //创建角色与权限的关系
        this.roleDao.deletePermissionsOfRole(userInfo.getRoleId());
        Role rolePermission = new Role();
        rolePermission.setId(UUID.randomUUID().toString());
        rolePermission.setRolePermissionRoleId(userInfo.getRoleId());
        rolePermission.setPermissionList(permissionsList);
        this.roleDao.insertPermissionsForRole(rolePermission);
        //修改用户数据权限
        this.userInfoDao.updateUserDataPermission(userInfo.getId(), dataPermission);
    }

    @Override
    public List<UserInfo> findOtherUserInfoById(UserInfo userInfo) {
        return this.userInfoDao.selectOtherUserInfoById(userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        UserInfo userInfo = this.userInfoDao.selectUserInfoByLoginName(loginName);
        Role role = userInfo.getRole();
        if (null != role) {
            userInfo.setAuthorities(new HashSet<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(role.getRoleKey()))));
        }
        return userInfo;
    }
}
