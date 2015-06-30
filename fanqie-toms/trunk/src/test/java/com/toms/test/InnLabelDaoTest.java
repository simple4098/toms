package com.toms.test;


import com.fanqielaile.toms.dao.InnLabelDao;
import com.fanqielaile.toms.model.InnLabel;
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
@ContextConfiguration(locations = {"/conf/spring/spring-content.xml", "/conf/mybatis/sqlMapConfig.xml"})
public class InnLabelDaoTest {
    @Resource
    private InnLabelDao innLabelDao;

    @Test
    @Ignore
    public void testInsert() {
        InnLabel innLabel = new InnLabel();
        innLabel.setId(UUID.randomUUID().toString());
        innLabel.setIndexed(1);
        innLabel.setLabelName("ceshi");
        innLabel.setCompanyId(UUID.randomUUID().toString());
        int i = this.innLabelDao.insertInnLabel(innLabel);
        System.out.println("=======>" + 1);
    }

    @Test
    @Ignore
    public void testSelectInnLabels() {
        List<InnLabel> innLabelList = this.innLabelDao.selectInnLabelsByCompanyId(null);
        System.out.println("=====>" + innLabelList.size());
    }

    @Test
    @Ignore
    public void testUpdateInnLabel() {
        InnLabel innLabel = new InnLabel();
        innLabel.setId("d51c1bad-56a4-420b-aea2-fcace22af546");
        innLabel.setLabelName("ceshiceshi");
        int i = this.innLabelDao.updateLabelById(innLabel);
        System.out.println("======>" + i);
    }

}
