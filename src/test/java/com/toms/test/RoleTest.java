package com.toms.test;

import com.fanqielaile.toms.dao.PermissionDao;
import com.fanqielaile.toms.dao.RoleDao;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.model.Role;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangdayin on 2015/5/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class RoleTest {
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;

    @Test
    @Ignore
    public void insertRole() {
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setRoleName("admin");
        role.setRoleKey("ROLE_admin");
        this.roleDao.insertRole(role);
    }

    @Test
    @Ignore
    public void insertRolePermission() {
        Role role = this.roleDao.selectRoleById("8a0219eb-88e8-4cd9-9b25-a6ebd6ada402");
        List<Permission> permissionList = permissionDao.selectPermissionRoles();
        Role role1 = new Role();
        role1.setRolePermissionRoleId(role.getId());
        role1.setPermissionList(permissionList);
        this.roleDao.insertPermissionsForRole(role1);
    }
}
