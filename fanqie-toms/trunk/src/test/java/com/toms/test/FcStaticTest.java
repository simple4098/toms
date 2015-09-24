
package com.toms.test;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dao.IFcAreaDao;
import com.fanqielaile.toms.dao.IFcCityDao;
import com.fanqielaile.toms.dao.IFcProvinceDao;
import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.CurrencyType;
import com.fanqielaile.toms.enums.OperateType;
import com.fanqielaile.toms.enums.PayMethod;
import com.fanqielaile.toms.model.fc.FcArea;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml","/conf/spring/spring-security.xml"})
public class FcStaticTest {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtil.class);

    @Resource
    private IFcCityDao fcCityDao;
    @Resource
    private IFcProvinceDao fcProvinceDao;
    @Resource
    private IFcAreaDao fcAreaDao;


    @Test
    @Ignore
    public void test() throws Exception {
        String province = "http://static.fangcang.com/static/hotelapi/area/province_cn.xml";
        String city = "http://static.fangcang.com/static/hotelapi/area/city_cn.xml";
        String country = "http://static.fangcang.com/static/hotelapi/area/district_cn.xml";
       /* String xml = HttpClientUtil.httpGets(province, null);
        List<FcProvince> list = XmlDeal.pareFcProvince(xml);
        for (FcProvince fcProvince:list){
            fcProvinceDao.saveFcProvince(fcProvince);
        }*/

        /*String xml = HttpClientUtil.httpGets(country, null);
        List<FcArea> list = XmlDeal.pareFcArea(xml);
        for (FcArea fcArea:list){
            fcAreaDao.saveFcArea(fcArea);
        }*/
    }
    @Test
    @Ignore
    public void beanToXML() throws JAXBException {
        List<HotelInfo> hotelInfoList = new ArrayList<HotelInfo>();
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setFcHotelId(111);
        hotelInfo.setSpHotelId("111test");
        hotelInfo.setFcHotelName("zhang san");
        hotelInfoList.add(hotelInfo);
        AddHotelMappingRequest addHotelMappingRequest = new AddHotelMappingRequest();
        //addHotelMappingRequest.setHotelList(hotelInfoList);
        Header header = new Header(RequestType.addHotelMapping,"S10085349","security_test_S10085349");
        AddHotelRequest addHotelRequest = new AddHotelRequest(header,addHotelMappingRequest);
        try {
            JAXBContext context = JAXBContext.newInstance(AddHotelRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(addHotelRequest, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void GetHotelRequest() throws JAXBException {
        GetHotelInfoRequest getHotelInfoRequest = new GetHotelInfoRequest();
        getHotelInfoRequest.setFcHotelIds("112324,112275");
        Header header = new Header(RequestType.getHotelInfo,"S10085349","security_test_S10085349");
        GetHotelRequest getHotelRequest = new GetHotelRequest(header,getHotelInfoRequest);
        try {
            String s = FcUtil.fcRequest(getHotelRequest);
            System.out.println(s);
            try {
                String gets = HttpClientUtil.httpPost("http://www.fangcang.org/USP/api_v1/getHotelInfo", s);
                System.out.println(gets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    @Test
    @Ignore
    public void addHotelRequest() throws Exception {
        List<HotelInfo> list = new ArrayList<HotelInfo>();
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setFcHotelId(113327);
        hotelInfo.setSpHotelId("12346");
        hotelInfo.setFcHotelName("林芝明珠大酒店");
  /*      HotelInfo hotelInfo1 = new HotelInfo();
        hotelInfo1.setFcHotelId(2323);
        hotelInfo1.setSpHotelId("11");*/
        list.add(hotelInfo);

        AddHotelMappingListRequest listRequest = new AddHotelMappingListRequest();
        listRequest.setHotelList(list);
        AddHotelMappingRequest hotelMappingRequest = new AddHotelMappingRequest();
        hotelMappingRequest.setListRequest(listRequest);
        Header header = new Header(RequestType.addHotelMapping,"S10085349","security_test_S10085349");
        AddHotelRequest hotelRequest = new AddHotelRequest(header,hotelMappingRequest);
        try {
            String s = FcUtil.fcRequest(hotelRequest);
            System.out.println(s);
            try {
                String gets = HttpClientUtil.httpPost("http://www.fangcang.org/USP/api_v1/addHotelMapping", s);
                System.out.println(gets);
                Response response = XmlDeal.pareFcResult(gets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void syncRatePlanRequest() throws JAXBException {
        SyncRatePlanRequest syncRatePlanRequest = new SyncRatePlanRequest();
        Header header = new Header(RequestType.syncRatePlan, "S10085349", "security_test_S10085349");
        syncRatePlanRequest.setHeader(header);
        SyncRatePlanRequestInfo syncRatePlanRequestInfo = new SyncRatePlanRequestInfo();
        syncRatePlanRequestInfo.setSpRoomTypeId("169149");
        syncRatePlanRequestInfo.setSpHotelId("110320");
        syncRatePlanRequestInfo.setOperateType(OperateType.NEW);
        List<RatePlan> ratePlanList = new ArrayList<>();
        RatePlan ratePlan = new RatePlan();
        ratePlan.setBedType(BedType.BigBed);
        ratePlan.setCurrency(CurrencyType.CNY);
        ratePlan.setPayMethod(PayMethod.pay);
        ratePlan.setSpRatePlanId("sp123");
        ratePlan.setSpRatePlanName("提前7天预订");
        ratePlanList.add(ratePlan);
        RatePlan ratePlan1 = new RatePlan();
        ratePlan1.setBedType(BedType.BigBed);
        ratePlan1.setCurrency(CurrencyType.CNY);
        ratePlan1.setPayMethod(PayMethod.pay);
        ratePlan1.setSpRatePlanId("sp123456");
        ratePlan1.setSpRatePlanName("提前7天预订111");
        ratePlanList.add(ratePlan1);
        syncRatePlanRequestInfo.setRatePlanList(ratePlanList);
        syncRatePlanRequest.setSyncRatePlanRequest(syncRatePlanRequestInfo);
        System.out.println(FcUtil.fcRequest(syncRatePlanRequest));
    }

}

