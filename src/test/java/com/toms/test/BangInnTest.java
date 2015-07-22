package com.toms.test;

import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.model.BangInn;
import net.sf.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class BangInnTest {
    @Resource
    private BangInnDao bangInnDao;

    @Test
    @Ignore
    public void insertBangInnTest() {
        BangInn bangInn = new BangInn();
        bangInn.setId(UUID.randomUUID().toString());
        bangInn.setBangDate(new Date());
        bangInn.setCompanyId("d0392bc8-131c-48a4-846e-c81c66097781");
        bangInn.setCode("11111");
        bangInn.setInnCode("122222");
        bangInn.setInnLabelId("d51c1bad-56a4-420b-aea2-fcace22af546");
        bangInn.setInnName("番茄来了");
        bangInn.setMobile("13800138000");
        bangInnDao.insertBangInn(bangInn);
    }

    @Test
    @Ignore
    public void testImageGet() throws IOException {
        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("903" + s + "TB" + "tb");
        String inn_info = "http://192.168.1.158:8888/api/getInnInfo?timestamp=" + s + "&otaId=" + 903 + "&accountId=" + 1000722 + "&signature=" + signature;
        String httpGets1 = HttpClientUtil.httpGets(inn_info, null);
        JSONObject jsonInn = JSONObject.fromObject(httpGets1);
        System.out.println(jsonInn);
    }
}
