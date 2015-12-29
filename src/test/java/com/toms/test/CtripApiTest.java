package com.toms.test;

import com.fanqie.bean.enums.*;
import com.fanqie.bean.request.room_info.RoomInfoItem;
import com.fanqie.bean.request.room_info.RoomInfoRequest;
import com.fanqie.bean.request.room_info.SetRoomInfoRequest;
import com.fanqie.bean.request.room_price.*;
import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/12/24
 * @version: v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml","/conf/spring/spring-security.xml"})
public class CtripApiTest {
    @Resource
    private ICtripRoomService ctripRoomService;

    @Test
    public void roomTest(){
        Company company = new Company();
        company.setOtaId(777);
        company.setUserAccount("FQFCDX");
        company.setUserPassword("fqfcdx");
        company.setId("60978e73-851b-429d-9cf4-415300a64739");
        OtaInfoRefDto otaInfo = new OtaInfoRefDto();
        otaInfo.setUserId("181");
        otaInfo.setUsedPriceModel(UsedPriceModel.MAI2DI);
        otaInfo.setXcUserName("zhilianjishuzhuanshu");
        otaInfo.setXcPassword("zhilianzhuanshu11!!");
        List<CtripRoomTypeMapping> roomTypeMappingList = new ArrayList<CtripRoomTypeMapping>();
        CtripRoomTypeMapping ctripRoomTypeMapping = new CtripRoomTypeMapping();
        ctripRoomTypeMapping.setId("142");
        ctripRoomTypeMapping.setInnId("4065");
        ctripRoomTypeMapping.setCtripChildHotelId("451026");
        ctripRoomTypeMapping.setCtripChildRoomTypeId("3087443");
        ctripRoomTypeMapping.setTomRoomTypeId("87337");
        roomTypeMappingList.add(ctripRoomTypeMapping);
        ctripRoomService.updateRoomPrice(company,otaInfo,roomTypeMappingList,true);
    }
    @Test
    public void test() throws JAXBException {

        RoomPriceRequest roomPriceRequest = new RoomPriceRequest();

        SetAdaperRoomPriceRequest setAdaperRoomPriceRequest = new SetAdaperRoomPriceRequest();
        setAdaperRoomPriceRequest.setHotelID(433939);
        setAdaperRoomPriceRequest.setPriority("dsdsd");
        setAdaperRoomPriceRequest.setSubmitor("dsds1q11");
        setAdaperRoomPriceRequest.setTitle("title 标题");

        Price price = new Price(212d,4545d,44d,54d,1);
        List<Price> prices = new ArrayList<>();
        prices.add(price);
        List<SetRoomPriceItem> setRoomPriceItems = new ArrayList<>();
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setApplicability(" Applicability ");
        priceInfo.setBlanceType(CtripBalanceType.PP);
        priceInfo.setBreakfast(1);
        priceInfo.setPrice(prices);

        List<PriceInfo> priceInfos = new ArrayList<>();
        priceInfos.add(priceInfo);
        SetRoomPriceItem setRoomPriceItem = new SetRoomPriceItem();
        setRoomPriceItem.setCurrency(CtripCurrency.CNY);
        setRoomPriceItem.setStartDate(TomsUtil.getDateStringFormat(new Date()));
        setRoomPriceItem.setEndDate(TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 1)));
        setRoomPriceItem.setRoomID(3073497);
        setRoomPriceItem.setPriceInfo(priceInfos);

        setRoomPriceItems.add(setRoomPriceItem);
        setAdaperRoomPriceRequest.setSetRoomPriceItems(setRoomPriceItems);



        Authentication authentication = new Authentication("zhilianjishuzhuanshu","zhilianzhuanshu11!!");
        RequestType requestType = new RequestType(CtripRequestType.SetRoomInfo, CtripVersion.V12);
        HeaderInfo headerInfo = new  HeaderInfo("181","Ctrip.com",false,authentication,requestType);
        roomPriceRequest.setSetAdaperRoomPriceRequest(setAdaperRoomPriceRequest);
        roomPriceRequest.setHeaderInfo(headerInfo);
        String s = FcUtil.fcRequest(roomPriceRequest);
        String xml = CtripHttpClient.execute(s);
        System.out.println(s);
        System.out.println(xml);

        System.out.print(String.valueOf(false));

    }

    @Test
    public void test1() throws JAXBException {
        Authentication authentication = new Authentication("zhilianjishuzhuanshu","zhilianzhuanshu11!!");
        RequestType requestType = new RequestType(CtripRequestType.SetRoomInfo, CtripVersion.V12);
        HeaderInfo headerInfo = new  HeaderInfo("181","Ctrip.com",false,authentication,requestType);

        List<RoomInfoItem> roomInfoItems = new ArrayList<>();
        RoomInfoItem roomInfoItem = new RoomInfoItem(DeductType.C, CtripConstants.reserveTime,
                CheckType.C,CtripConstants.lateReserveTime,
                CtripConstants.guaranteeLCT,"",AllNeedGuarantee.T,1,
                CtripRoomStatus.G,CtripConstants.holdDeadline,
                ChangeDefault.F,CtripConstants.userLimited,CtripConstants.prepayLCT);
        roomInfoItems.add(roomInfoItem);
        SetRoomInfoRequest setRoomInfoRequest = new SetRoomInfoRequest(roomInfoItems,3073497,TomsUtil.getDateStringFormat(new Date()),TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 1)));
        RoomInfoRequest roomInfoRequest = new RoomInfoRequest(setRoomInfoRequest,headerInfo);
        String fcRequest = FcUtil.fcRequest(roomInfoRequest);
        System.out.println(fcRequest);
        String xml = CtripHttpClient.execute(fcRequest);
        RequestResponse response= (RequestResponse) FcUtil.xMLStringToBean(xml);
        System.out.println(xml);

    }
}
