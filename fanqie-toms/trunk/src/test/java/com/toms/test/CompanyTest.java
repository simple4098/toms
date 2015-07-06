package com.toms.test;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.PermissionDao;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.Permission;
import com.fanqielaile.toms.service.impl.TBService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class CompanyTest {
    @Resource
    private CompanyDao companyDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private TBService tbService;



    @Test
    @Ignore
    public void testInsertCompany() {
        Company company = new Company();
        company.setId(UUID.randomUUID().toString());
        company.setCompanyName("番茄来了");
        int i = this.companyDao.insertCompany(company);
        System.out.println("==========>" + i);
    }

    @Test
    @Ignore
    public void testInserCompanyPermission() {
        List<Permission> permissions = this.permissionDao.selectPermissionRoles();
        Company company = new Company();
        company.setId("d0392bc8-131c-48a4-846e-c81c66097781");
        company.setPermissionList(permissions);
        this.companyDao.insertCompanyPermission(company);
    }
}

