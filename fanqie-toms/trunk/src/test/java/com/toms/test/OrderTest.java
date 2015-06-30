package com.toms.test;

import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/6/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class OrderTest {
    @Resource
    private IOrderService orderService;

    @Test
    @Ignore
    public void testAddOrder() throws Exception {
        String xmlStr = "<BookRQ><TaoBaoOrderId>1387784033263</TaoBaoOrderId><TaoBaoHotelId>16513059123</TaoBaoHotelId><HotelId>8291</HotelId><TaoBaoRoomTypeId>35366099123</TaoBaoRoomTypeId><RoomTypeId>46273</RoomTypeId><TaoBaoRatePlanId>123000123</TaoBaoRatePlanId><RatePlanCode>7797550e-24f4-4878-aa56-2a610859fa70</RatePlanCode><TaoBaoGid>1234567890</TaoBaoGid><CheckIn>2015-07-29</CheckIn><CheckOut>2015-07-30</CheckOut><EarliestArriveTime>2015-07-29 20:00:00</EarliestArriveTime><LatestArriveTime>2015-07-30 22:00:00</LatestArriveTime><RoomNum>1</RoomNum><TotalPrice>9100</TotalPrice><ContactName>测试联系人</ContactName><ContactTel>13920682209</ContactTel><PaymentType>5</PaymentType><DailyInfos><DailyInfo><Day>2016-07-29</Day><Price>4550</Price></DailyInfo><DailyInfo><Day>2015-07-30</Day><Price>4550</Price></DailyInfo></DailyInfos><OrderGuests><OrderGuest><Name>入住人1</Name><RoomPos>1</RoomPos></OrderGuest><OrderGuest><Name>入住人2</Name><RoomPos>1</RoomPos></OrderGuest></OrderGuests><Comment>测试</Comment></BookRQ>";
        this.orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testCancelOrder() throws Exception {
        String xml = "<CancelRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao125484778-1387789907859</CreateToken></AuthenticationToken><OrderId>cdb7b8bf-277c-4d83-bca9-203a9f675cc3</OrderId><Reason>reason</Reason><CancelId>1387789907859</CancelId></CancelRQ>";
        this.orderService.cancelOrder(xml, ChannelSource.TAOBAO);
    }

    @Test
//    @Ignore
    public void testCreateOrder() throws Exception {
        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>b8f5096b-c4ef-44f2-9414-91b56b032dd0</OrderId><TaoBaoOrderId>1387784033263</TaoBaoOrderId><AlipayTradeNo>2013123111001001020000378012</AlipayTradeNo><Payment>10000</Payment></PaySuccessRQ>";
        this.orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO);
    }
}
