package com.toms.test;

import com.fanqielaile.toms.dao.RoleDao;
import com.fanqielaile.toms.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-content.xml", "/conf/mybatis/sqlMapConfig.xml"})
public class RoleTest {
    @Resource
    private RoleDao roleDao;

    @Test
    public void insertRole() {
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setRoleName("admin");
        role.setRoleKey("ROLE_admin");
        this.roleDao.insertRole(role);
    }
}
