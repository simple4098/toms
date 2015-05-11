package com.toms.test;

import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.model.UserInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-content.xml", "/conf/mybatis/sqlMapConfig.xml"})
public class UserInfoTest {
    @Resource
    private UserInfoDao userInfoDao;

    @Test
//    @Ignore
    public void testInsert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setLoginName("test");
        userInfo.setCreatedDate(new Date());
        userInfo.setDataPermission(1);

        int i = userInfoDao.insertUserInfo(userInfo);
        System.out.println("=====>"+i);
    }
}
