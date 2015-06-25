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
        String xmlStr1 = "<OrderGuests><OrderGuest><Name>入住人1</Name><RoomPos>1</RoomPos></OrderGuest><OrderGuest><Name>入住人2</Name><RoomPos>1</RoomPos></OrderGuest></OrderGuests>";
        String xmlStr = "<BookRQ><TaoBaoOrderId>1387784033263</TaoBaoOrderId><TaoBaoHotelId>123456789</TaoBaoHotelId><HotelId>80</HotelId><TaoBaoRoomTypeId>123456978</TaoBaoRoomTypeId><RoomTypeId>ST</RoomTypeId><TaoBaoRatePlanId>123000123</TaoBaoRatePlanId><RatePlanCode>VIP</RatePlanCode><TaoBaoGid>1234567890</TaoBaoGid><CheckIn>2013-12-24</CheckIn><CheckOut>2013-12-26</CheckOut><EarliestArriveTime>2013-12-24 20:00:00</EarliestArriveTime><LatestArriveTime>2013-12-24 22:00:00</LatestArriveTime><RoomNum>1</RoomNum><TotalPrice>63850</TotalPrice><ContactName>测试联系人</ContactName><ContactTel>13920682209</ContactTel><PaymentType>5</PaymentType><DailyInfos><DailyInfo><Day>2013-12-24</Day><Price>17800</Price></DailyInfo><DailyInfo><Day>2013-12-25</Day><Price>46050</Price></DailyInfo></DailyInfos><OrderGuests><OrderGuest><Name>入住人1</Name><RoomPos>1</RoomPos></OrderGuest><OrderGuest><Name>入住人2</Name><RoomPos>1</RoomPos></OrderGuest></OrderGuests><Comment>测试</Comment></BookRQ>";
        this.orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
    }

    @Test
//    @Ignore
    public void testCancelOrder() throws Exception {
        String xml = "<CancelRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao125484778-1387789907859</CreateToken></AuthenticationToken><OrderId>d0500e54-1365-4859-8909-74982a3c8a07</OrderId><Reason>reason</Reason><CancelId>1387789907859</CancelId></CancelRQ>";
        this.orderService.cancelOrder(xml, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testCreateOrder() throws Exception {
        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>ec49aba0-dfed-4759-bfa3-9895c24d0d86</OrderId><TaoBaoOrderId>1230123213</TaoBaoOrderId><AlipayTradeNo>2013123111001001020000378012</AlipayTradeNo><Payment>10000</Payment></PaySuccessRQ>";
        this.orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO, new UserInfo());
    }
}
