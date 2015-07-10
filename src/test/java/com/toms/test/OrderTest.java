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
    @Ignore
    public void testFindOrderStatus() throws Exception {
        String xml = "<QueryStatusRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>436e4b4f-c0b4-4c81-a6d9-08af74e85a64</OrderId><TaoBaoOrderId>1230123213</TaoBaoOrderId></QueryStatusRQ>";
        this.orderService.findOrderStatus(xml, ChannelSource.TAOBAO);
    }

    @Test
//    @Ignore
    public void testCreateOrder() throws Exception {
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>b8f5096b-c4ef-44f2-9414-91b56b032dd0</OrderId><TaoBaoOrderId>1387784033263</TaoBaoOrderId><AlipayTradeNo>2013123111001001020000378012</AlipayTradeNo><Payment>10000</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao193617029605469-1435806074926</CreateToken></AuthenticationToken><OrderId>436e4b4f-c0b4-4c81-a6d9-08af74e85a64</OrderId><TaoBaoOrderId>193617029605469</TaoBaoOrderId><Payment>120000</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1137788503530651-1436252626566</CreateToken></AuthenticationToken><OrderId>7908a6ee-2495-4796-91b9-0e158af11deb</OrderId><TaoBaoOrderId>1137788503530651</TaoBaoOrderId><AlipayTradeNo>2015070721001001390223142895</AlipayTradeNo><Payment>30000</Payment></PaySuccessRQ>";
        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1145176960720651-1436413019307</CreateToken></AuthenticationToken><OrderId>34925fa6-b4c1-4915-8455-063817ede34f</OrderId><TaoBaoOrderId>1145176960720651</TaoBaoOrderId><AlipayTradeNo>2015070921001001390225055373</AlipayTradeNo><Payment>200</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1145774889820651-1436422573079</CreateToken></AuthenticationToken><OrderId>ff6597b9-fac0-49ec-8310-11f662df1b65</OrderId><TaoBaoOrderId>1145774889820651</TaoBaoOrderId><AlipayTradeNo>2015070921001001390224890600</AlipayTradeNo><Payment>100</Payment></PaySuccessRQ>";
        this.orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO);
    }
}
