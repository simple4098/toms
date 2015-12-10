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
//    @Ignore
    public void testCreateOrder() throws Exception {
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request>   <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/>   <CreateHotelOrderRequest> <SpHotelId>46149</SpHotelId> <SpRoomTypeId>105751</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-11</CheckInDate> <CheckOutDate>2015-11-12</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>94.4</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC345555343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-11</SaleDate> <SalePrice>94.4</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>测试人</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>46149</SpHotelId> <SpRoomTypeId>105751</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-14</CheckInDate> <CheckOutDate>2015-11-15</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>228.8</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC3455553452611143</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-14</SaleDate> <SalePrice>228.8</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>测试人</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>3698</SpHotelId> <SpRoomTypeId>99094</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-16</CheckInDate> <CheckOutDate>2015-11-17</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>147.84</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC34537845e4455343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-16</SaleDate> <SalePrice>147.84</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>abcd</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>3698</SpHotelId> <SpRoomTypeId>99093</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-17</CheckInDate> <CheckOutDate>2015-11-18</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>328.80</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC345332344552e55343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-17</SaleDate> <SalePrice>328.80</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>tom</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>3698</SpHotelId> <SpRoomTypeId>416143</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-17</CheckInDate> <CheckOutDate>2015-11-18</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>211.2</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC345555453334343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-17</SaleDate> <SalePrice>211.2</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>admin</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>\n";
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>3698</SpHotelId> <SpRoomTypeId>99094</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-11-25</CheckInDate> <CheckOutDate>2015-11-26</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>118</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC3963145e343</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-11-25</SaleDate> <SalePrice>118</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>ceshi</GuestName> <Nationality>国籍可选</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>联系人姓名</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark 备注信息</Remark><SpecialRequirement>特殊要求</SpecialRequirement><ReservedItem>附加设置</ReservedItem></CreateHotelOrderRequest></Request>";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <Request> <Header TimeStamp=\"2015-10-09 13:41:56\" PartnerCode=\"F01202154\" RequestType=\"createHotelOrder\" Signature=\"FC7A7D7342E7D4E71FEC53E1902258D9\"/> <CreateHotelOrderRequest> <SpHotelId>14381</SpHotelId> <SpRoomTypeId>113684</SpRoomTypeId> <SpRatePlanId>P908787</SpRatePlanId> <BedType>1000000</BedType> <CheckInDate>2015-12-06</CheckInDate> <CheckOutDate>2015-12-07</CheckOutDate> <RoomNum>1</RoomNum> <ArrivalTime></ArrivalTime> <LatestArrivalTime></LatestArrivalTime> <TotalAmount>150</TotalAmount> <Currency>CNY</Currency> <FcOrderId>FC392147443</FcOrderId> <SalePriceList> <SalePriceItem> <SaleDate>2015-12-06</SaleDate> <SalePrice>150</SalePrice> <BreakfastType>1</BreakfastType> <BreakfastNum>2</BreakfastNum> </SalePriceItem> </SalePriceList> <ConfirmType>1</ConfirmType> <GuestInfoList> <GuestInfo> <GuestName>???</GuestName> <Nationality>????</Nationality> </GuestInfo> </GuestInfoList> <Contacter> <LinkMan>?????</LinkMan> <LinkManPhone>13800138000</LinkManPhone> <Email>11@qq.com</Email></Contacter><Remark>remark ????</Remark><SpecialRequirement>测试</SpecialRequirement><ReservedItem>????</ReservedItem></CreateHotelOrderRequest></Request>";
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
