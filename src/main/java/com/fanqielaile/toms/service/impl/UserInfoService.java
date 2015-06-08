package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.RoleDao;
import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.event.TomsApplicationEvent;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.listener.RolePermissionChangeListener;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.commons.lang3.ArrayUtils;
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
    @Resource
    private RolePermissionChangeListener rolePermissionChangeListener;

    @Override
    public void createUserInfo(UserInfo userInfo, List<Permission> permissionIdlist) {
        try {
            if (StringUtils.isNotEmpty(userInfo.getLoginName())) {
                UserInfo userInfo1 = userInfoDao.selectUserInfoByLoginName(userInfo.getLoginName());
                if (null == userInfo1) {
                    //创建角色
                    Role role = completeRole();
                    this.roleDao.insertRole(role);
                    //创建用户
                    userInfo.setId(userInfo.getUuid());
                    userInfo.setRoleId(role.getId());
                    //设置密码
                    String passwordEncode = passwordEncoder.encodePassword(userInfo.getPassword(), null);
                    userInfo.setPassword(passwordEncode);
                    userInfoDao.insertUserInfo(userInfo);
                    //删除角色与权限的关系
                    this.roleDao.deletePermissionsOfRole(role.getId());
                    //新增角色与权限关系
                    this.roleDao.insertPermissionsForRole(completeRolePermission(permissionIdlist, role));
                    rolePermissionChangeListener.onApplicationEvent(new TomsApplicationEvent(completeRolePermission(permissionIdlist, role)));
                }
            }
        } catch (Exception e) {
            throw new TomsRuntimeException("新增用户失败", e);
        }
    }

    /*新增角色与权限关系的处理方法*/
    private Role completeRolePermission(List<Permission> permissionIdlist, Role role) {
        Role rolePermission = new Role();
        rolePermission.setId(rolePermission.getUuid());
        rolePermission.setRolePermissionRoleId(role.getId());
        rolePermission.setPermissionList(permissionIdlist);
        rolePermission.setCreatedDate(new Date());
        rolePermission.setUpdatedDate(new Date());
        return rolePermission;
    }

    /*处理新增角色的方法*/
    private Role completeRole() {
        Role role = new Role();
        role.setId(role.getUuid());
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
        return role;
    }

    @Override
    public UserInfo findUserInfoByLoginName(String loginName) {
        return userInfoDao.selectUserInfoByLoginName(loginName);
    }

    @Override
    public List<UserInfoDto> findUserInfos(String companyId) {
        //该用户是否有管理的客栈
        List<UserInfoDto> infoDtoList = userInfoDao.selectUserInfos(companyId);
        if (ArrayUtils.isNotEmpty(infoDtoList.toArray())) {
            for (UserInfoDto userInfoDto : infoDtoList) {
                List<BangInn> bangInnList = this.bangInnDao.selectBangInnByUserId(userInfoDto.getId());
                if (ArrayUtils.isNotEmpty(bangInnList.toArray())) {
                    userInfoDto.setIsHaveInn(true);
                }
            }
        }
        return infoDtoList;
    }

    @Override
    public UserInfo findUserInfoById(String id) {
        return userInfoDao.selectUserInfoById(id);
    }

    @Override
    public void modifyUserInfo(UserInfo userInfo) {
        UserInfo userInfo1 = userInfoDao.selectUserInfoById(userInfo.getId());
        if (null != userInfo1) {
            userInfo.setPassword(passwordEncoder.encodePassword(userInfo.getPassword(), null));
            userInfoDao.updateUserInfo(userInfo);
        } else {
            throw new TomsRuntimeException("修改员工信息失败，没有找到员工信息");
        }
    }

    @Override
    public void removeUserInfo(String id, String replaceUserId) {
        UserInfo userInfo = userInfoDao.selectUserInfoById(id);
        if (userInfo != null) {
            //删除员工之前将员工名下的管理客栈转移
            this.bangInnDao.updateBangInnUserId(id, replaceUserId);
            //删除员工
            userInfoDao.deleteUserInfo(id);
        } else {
            throw new TomsRuntimeException("删除员工失败,没有该员工信息");
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
        rolePermissionChangeListener.onApplicationEvent(new TomsApplicationEvent(rolePermission));
    }

    @Override
    public List<UserInfo> findOtherUserInfoById(UserInfo userInfo) {
        return this.userInfoDao.selectOtherUserInfoById(userInfo);
    }

    @Override
    public void removeUserInfo(String id) {
        UserInfo userInfo = this.userInfoDao.selectUserInfoById(id);
        if (userInfo != null) {
            this.userInfoDao.deleteUserInfo(id);
        } else {
            throw new TomsRuntimeException("删除员工失败，没有该员工信息");
        }
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

    @Override
    public List<UserInfoDto> findUserInfoByPage(String companyId, PageBounds pageBounds) {
        //该用户是否有管理的客栈
        List<UserInfoDto> infoDtoList = userInfoDao.selectUserInfoByPagination(companyId, pageBounds);
        if (ArrayUtils.isNotEmpty(infoDtoList.toArray())) {
            for (UserInfoDto userInfoDto : infoDtoList) {
                List<BangInn> bangInnList = this.bangInnDao.selectBangInnByUserId(userInfoDto.getId());
                if (ArrayUtils.isNotEmpty(bangInnList.toArray())) {
                    userInfoDto.setIsHaveInn(true);
                }
            }
        }
        return infoDtoList;
    }
}
