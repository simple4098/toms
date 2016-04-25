package com.toms.test;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.tb.TBXHotelUtilPromotion;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class OrderTest {
    @Resource
    private IOrderService orderService;
    @Resource
    private OrderDao orderDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private RoleDao roleDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IOtaBangInnRoomDao otaBangInnRoomDao;

    @Test
    @Ignore
    public void testAddTBOrder() throws Exception {
        String xmlStr = "<BookRQ><TaoBaoOrderId>13877840338999</TaoBaoOrderId><TaoBaoHotelId>16568171123</TaoBaoHotelId><HotelId>26062</HotelId><TaoBaoRoomTypeId>35553709123</TaoBaoRoomTypeId><RoomTypeId>1018425</RoomTypeId><TaoBaoRatePlanId>4161001123</TaoBaoRatePlanId><RatePlanCode>1019240</RatePlanCode><TaoBaoGid>12136001123</TaoBaoGid><CheckIn>2015-08-30</CheckIn><CheckOut>2015-08-31</CheckOut><EarliestArriveTime>2015-08-27 20:00:00</EarliestArriveTime><LatestArriveTime>2016-08-28 22:00:00</LatestArriveTime><RoomNum>1</RoomNum><TotalPrice>102</TotalPrice><ContactName>测试联系人</ContactName><ContactTel>13920682209</ContactTel><PaymentType>5</PaymentType><DailyInfos><DailyInfo><Day>2015-08-29</Day><Price>102</Price></DailyInfo></DailyInfos><OrderGuests><OrderGuest><Name>入住人1</Name><RoomPos>1</RoomPos></OrderGuest></OrderGuests><Comment>测试</Comment></BookRQ>";
        this.orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testCancelOrder() throws Exception {
//        String xml = "<CancelRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao125484778-1387789907859</CreateToken></AuthenticationToken><OrderId>cdb7b8bf-277c-4d83-bca9-203a9f675cc3</OrderId><Reason>reason</Reason><CancelId>1387789907859</CancelId></CancelRQ>";
        String xml = "<QueryRefundRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao125484778-1387789907859</CreateToken></AuthenticationToken><OrderId>20150814-5</OrderId><Reason>reason</Reason><CancelId>1387789907859</CancelId></QueryRefundRQ>";
        this.orderService.dealPayBackMethod(xml, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testFindOrderStatus() throws Exception {
        String xml = "<QueryStatusRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>436e4b4f-c0b4-4c81-a6d9-08af74e85a64</OrderId><TaoBaoOrderId>1230123213</TaoBaoOrderId></QueryStatusRQ>";
        this.orderService.findOrderStatus(xml, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testCreateOrder() throws Exception {
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password><CreateToken>taobao1230123213-1387792484913</CreateToken></AuthenticationToken><OrderId>b8f5096b-c4ef-44f2-9414-91b56b032dd0</OrderId><TaoBaoOrderId>1387784033263</TaoBaoOrderId><AlipayTradeNo>2013123111001001020000378012</AlipayTradeNo><Payment>10000</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao193617029605469-1435806074926</CreateToken></AuthenticationToken><OrderId>436e4b4f-c0b4-4c81-a6d9-08af74e85a64</OrderId><TaoBaoOrderId>193617029605469</TaoBaoOrderId><Payment>120000</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1137788503530651-1436252626566</CreateToken></AuthenticationToken><OrderId>7908a6ee-2495-4796-91b9-0e158af11deb</OrderId><TaoBaoOrderId>1137788503530651</TaoBaoOrderId><AlipayTradeNo>2015070721001001390223142895</AlipayTradeNo><Payment>30000</Payment></PaySuccessRQ>";
        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1145176960720651-1436413019307</CreateToken></AuthenticationToken><OrderId>dd5e1f0c-b4b6-4c68-a9cb-8f393fc9c5f3</OrderId><TaoBaoOrderId>1387784033895</TaoBaoOrderId><AlipayTradeNo>2015070921001001390225055373</AlipayTradeNo><Payment>2800</Payment></PaySuccessRQ>";
//        String xmlStr = "<PaySuccessRQ><AuthenticationToken><Username>feiniao</Username><Password>111111</Password><CreateToken>taobao1145774889820651-1436422573079</CreateToken></AuthenticationToken><OrderId>ff6597b9-fac0-49ec-8310-11f662df1b65</OrderId><TaoBaoOrderId>1145774889820651</TaoBaoOrderId><AlipayTradeNo>2015070921001001390224890600</AlipayTradeNo><Payment>100</Payment></PaySuccessRQ>";
        this.orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO);
    }

    @Test
    @Ignore
    public void testUpdateOrder() throws Exception {
        Order order = this.orderDao.selectOrderByIdAndChannelSource("11988898-ece7-4d91-ad1e-d0469b0db1f8", ChannelSource.TAOBAO);
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType("70c0a1ad-63a4-4d8f-b9f1-774366e0ee10", OtaType.CREDIT.name());
        String result = TBXHotelUtilPromotion.orderUpdate(order, otaInfo, 1L);
        System.out.println(result.toString());
    }

    @Test
//    @Ignore
    public void testImage() throws IOException {
        List<BangInn> bangInns = this.bangInnService.findBangInnImages("d0392bc8-131c-8989-846e-c81c66011111");
        if (ArrayUtils.isNotEmpty(bangInns.toArray())) {
            for (BangInn bangInn : bangInns) {
                //hid
                //查询客栈绑定的客栈ID
                OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.TB.name());
                OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(), "d0392bc8-131c-8989-846e-c81c66011111", otaInfoRefDto.getId());
                //写入酒店信息
                if (null != otaInfoRefDto) {
                    if (null != otaInnOtaDto.getWgHid()) {
                        bangInn.setOtaWgId(otaInnOtaDto.getWgHid());
                        if (null != bangInn.getInnDto()) {
                            if (ArrayUtils.isNotEmpty(bangInn.getInnDto().getImgList().toArray())) {
                                String imageUrl = "";
                                for (OmsImg omsImg : bangInn.getInnDto().getImgList()) {
                                    imageUrl += CommonApi.IMG_URL + omsImg.getImgUrl() + ",";
                                }
                                //写入酒店的图片信息
                                System.out.println("insert hotel ===>");
                                System.out.println("innId===>" + bangInn.getInnId());
                                this.roleDao.insertInfoImage(new ImageInfo(bangInn.getOtaWgId() + "||" + imageUrl));
                                //写入房型信息
                                List<RoomTypeInfo> roomTypeInfos = this.bangInnService.findBangInnRoomImage((BangInnDto) bangInn);
                                if (ArrayUtils.isNotEmpty(roomTypeInfos.toArray())) {
                                    for (RoomTypeInfo roomTypeInfo : roomTypeInfos) {
                                        OtaBangInnRoomDto otaBangInnRoomDto = this.otaBangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(bangInn.getInnId(), roomTypeInfo.getRoomTypeId(), "d0392bc8-131c-8989-846e-c81c66011111");
                                        String roomImageUrl = "";
                                        if (ArrayUtils.isNotEmpty(roomTypeInfo.getImgList().toArray()) && null != otaBangInnRoomDto) {
                                            for (OmsImg omsImg : roomTypeInfo.getImgList()) {
                                                roomImageUrl += CommonApi.IMG_URL + omsImg.getImgUrl() + ",";
                                            }
                                            //写入数据库
                                            System.out.println("insert  room ===>");
                                            System.out.println("===innId=" + bangInn.getInnId() + "  otabanginnroom==" + otaBangInnRoomDto.getrId());
                                            this.roleDao.insertInfoImage(new ImageInfo(bangInn.getOtaWgId() + "|" + otaBangInnRoomDto.getrId() + "|" + roomImageUrl));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

