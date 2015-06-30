package com.toms.test;

import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.session.RowBounds;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangdayin on 2015/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml"})
public class UserInfoTest {
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private Md5PasswordEncoder passwordEncoder;

    @Test
//    @Ignore
    public void testPage() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "ok");
        List<UserInfo> userInfos = this.userInfoDao.selectUserInfoByPage(new UserInfo(), new PageBounds(1, 1));
        System.out.println("==========>" + userInfos.size());
    }
    @Test
    @Ignore
    public void testInsert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setLoginName("test");
        userInfo.setCreatedDate(new Date());
        userInfo.setDataPermission(1);
        userInfo.setPassword(passwordEncoder.encodePassword("111111", null));
        int i = userInfoDao.insertUserInfo(userInfo);
        System.out.println("=====>"+i);
    }

    @Test
    @Ignore
    public void testUpdateDataPermission() {
        this.userInfoDao.updateUserDataPermission("dd9c2648-3b79-4e61-87d3-18079e9256d3", 1);
    }
}
