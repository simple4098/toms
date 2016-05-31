package com.toms.test;

import com.fanqielaile.toms.dao.OrderDao;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.model.OtaPendingOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class OtaPendingOrderTest {
    @Resource
    private OrderDao orderDao;

    @Test
    @Ignore
    public void testInsert() {
        OtaPendingOrder otaPendingOrder = new OtaPendingOrder();
        otaPendingOrder.setId(otaPendingOrder.getUuid());
        otaPendingOrder.setOrderId("23423423423");
        otaPendingOrder.setReasonDesc("dafd");
        otaPendingOrder.setModifyStatus(OrderStatus.ACCEPT);
        this.orderDao.insertOtaPendingOrder(otaPendingOrder);
    }

}
