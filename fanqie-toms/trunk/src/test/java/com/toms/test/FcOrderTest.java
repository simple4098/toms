package com.toms.test;

import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.service.IOrderService;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wangdayin on 2015/9/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class FcOrderTest {
    @Resource
    private IOrderService orderService;

    @Test
    @Ignore
    public void testOrderCheckAvail() throws IOException {
//        String xml = "<?xml version=\"1.0\"\tencoding=\"utf-8\"?> <Request>   <Header TimeStamp=\"2015-05-09\t13:41:56\" PartnerCode=\"F01202154\" RequestType=\"checkRoomAvail\" Signature=\"D1D201EEDCCD984AA87228869D60E353\"/><CheckRoomAvailRequest><SpHotelId>39202</SpHotelId><SpRoomTypeId>102561</SpRoomTypeId><SpRatePlanId>P908787</SpRatePlanId><CheckInDate>2015-10-10</CheckInDate><CheckOutDate>2015-10-11</CheckOutDate><RoomNum>1</RoomNum></CheckRoomAvailRequest></Request>";
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><Request>\n" +
                "  <Header TimeStamp=\"2015-11-02 17:40:16\" PartnerCode=\"S10046872\" RequestType=\"checkRoomAvail\" Signature=\"573F2E48169F5194410C6AA7BD7BC4D7\"/>\n" +
                "  <CheckRoomAvailRequest>\n" +
                "    <SpHotelId>14824</SpHotelId>\n" +
                "    <SpRoomTypeId>112810</SpRoomTypeId>\n" +
                "    <SpRatePlanId>10007</SpRatePlanId>\n" +
                "    <CheckInDate>2015-11-11</CheckInDate>\n" +
                "    <CheckOutDate>2015-11-12</CheckOutDate>\n" +
                "    <RoomNum>1</RoomNum>\n" +
                "  </CheckRoomAvailRequest>\n" +
                "</Request>";
        CheckRoomAvailResponse checkRoomAvailResponse = this.orderService.checkRoomAvail(xml);
        System.out.println("==============>" + checkRoomAvailResponse.toString());
    }

    @Test
    @Ignore
    public void testCreateOrder() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request>   <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/>   <CreateHotelOrderRequest> <SpHotelId>46149</SpHotelId> <SpRoomTypeId>105751</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-11</CheckInDate> <CheckOutDate>2015-11-12</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>94.4</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC345555343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-11</SaleDate> <SalePrice>94.4</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>测试人</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
        Map<String, Object> fcHotelOrder = this.orderService.createFcHotelOrder(xml);
        System.out.println(fcHotelOrder.toString());
    }

    @Test
    @Ignore
    public void testCancelFcOrder() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request>    <Header TimeStamp=\"2014-02-16 15:36:48\" PartnerCode=\"F01202154\"  RequestType= \"cancelHotelOrder\" Signature=\"484D30CBF4F167CBC803BF5A6AAEF1A8\"/>    <CancelHotelOrderRequest>       <SpOrderId>77918027-79fc-420a-ac21-4e4ca6654d44</SpOrderId>       <CancelReason>退改申请原因</CancelReason>    </CancelHotelOrderRequest> </Request>";
        this.orderService.cancelFcHotelOrder(xml);
    }

    @Test
    @Ignore
    public void testSearchFcOrderStatus() throws Exception {
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request>     <Header TimeStamp=\"2015-05-09 15:19:40\" PartnerCode=\"F01114815\" RequestType= \"getOrderStatus\" Signature=\"498C4A8D16628530A20FC678E0614D49\"/> <GetOrderStatusRequest> <SpOrderId>77918027-79fc-420a-ac21-4e4ca6654d44</SpOrderId> </GetOrderStatusRequest> </Request>";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Request>\n" +
                "  <Header TimeStamp=\"2015-11-03 11:27:21\" PartnerCode=\"S10046872\" RequestType=\"getOrderStatus\" Signature=\"BCB9B50464C929A73D5B62662FEDC1BC\"/>\n" +
                "  <GetOrderStatusRequest>\n" +
                "    <SpOrderId>9e353cc7-b945-4ac5-8c36-67707ae6bc3e</SpOrderId>\n" +
                "  </GetOrderStatusRequest>\n" +
                "</Request>";
        GetOrderStatusResponse fcOrderStatus = this.orderService.getFcOrderStatus(xml);
//        System.out.println(fcOrderStatus.toString());
    }
}
