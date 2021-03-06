package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.UserInfoDto;
import com.fanqielaile.toms.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/11.
 */
public interface PermissionDao {
    /**
     * 查询所有的权限
     *
     * @return
     */
    List<Permission> selectPermissionRoles();

    /**
     * 根据公司ID查询公司具有的权限
     * @param companyId
     * @return
     */
    List<Permission> selectPermissionByCompanyId(@Param("companyId") String companyId);

    /**
     * 根据用户id查询权限列表
     *
     * @param userId
     * @return
     */
    List<Permission> selectPermissionByUserId(String userId);

    /**
     * 创建权限
     *
     * @param permission
     */
    void insertPermission(Permission permission);
    /**
     * 删除公司下员工不具有的权限
     *
     * @param userInfoDtos
     * @param deletePermissionIds
     */
    void deleteCompanyUserPermission(@Param("userInfos") List<UserInfoDto> userInfoDtos, @Param("deletePermissions") List<Permission> deletePermissionIds);
}
