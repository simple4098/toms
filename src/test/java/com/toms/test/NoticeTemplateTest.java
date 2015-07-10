package com.toms.test;

import com.fanqielaile.toms.dao.NoticeTemplateDao;
import com.fanqielaile.toms.model.NoticeTemplate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class NoticeTemplateTest {
    @Resource
    private NoticeTemplateDao noticeTemplateDao;

    @Test
    @Ignore
    public void testInsert() {
        NoticeTemplate noticeTemplate = new NoticeTemplate();
        noticeTemplate.setId(UUID.randomUUID().toString());
        noticeTemplate.setNoticeTitle("notice title");
        noticeTemplate.setNoticeContent("notice content");
        int i = this.noticeTemplateDao.insertNoticeTemplate(noticeTemplate);
        System.out.println("========>" + i);
    }

    @Test
    @Ignore
    public void testFindNoticeTemplates() {
        List<NoticeTemplate> noticeTemplates = this.noticeTemplateDao.selectNoticeTemplates(null);
        System.out.println("size=======>" + noticeTemplates.size());
    }

    @Test
    @Ignore
    public void testFindById() {
        NoticeTemplate noticeTemplate = this.noticeTemplateDao.selectNoticeTemplateById("6d65b662-999c-4cb2-8cbd-bd510ed331ca");
        System.out.println(noticeTemplate.getNoticeTitle());
    }

    @Test
    @Ignore
    public void testUpdate() {
        NoticeTemplate noticeTemplate = new NoticeTemplate();
        noticeTemplate.setId("6d65b662-999c-4cb2-8cbd-bd510ed331ca");
        noticeTemplate.setNoticeTitle("notice title1");
        noticeTemplate.setNoticeContent("notice content1");
        int i = this.noticeTemplateDao.updateNoticeTemplate(noticeTemplate);
        System.out.println("=====>" + i);
    }

    @Test
    @Ignore
    public void testDelete() {
        int i = this.noticeTemplateDao.deleteNoticeTemplateById("6d65b662-999c-4cb2-8cbd-bd510ed331ca");
        System.out.println("===>" + i);
    }

}
