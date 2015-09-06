
package com.toms.test;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dao.IFcAreaDao;
import com.fanqielaile.toms.dao.IFcCityDao;
import com.fanqielaile.toms.dao.IFcProvinceDao;
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
    public void beanToXML() throws JAXBException {
        List<HotelInfo> hotelInfoList = new ArrayList<HotelInfo>();
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setFcHotelId(111);
        hotelInfo.setSpHotelId("111test");
        hotelInfo.setFcHotelName("zhang san");
        hotelInfoList.add(hotelInfo);
        AddHotelMappingRequest addHotelMappingRequest = new AddHotelMappingRequest();
        addHotelMappingRequest.setHotelList(hotelInfoList);
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
    public void GetHotelRequest() throws JAXBException {
        GetHotelInfoRequest getHotelInfoRequest = new GetHotelInfoRequest();
        getHotelInfoRequest.setFcHotelIds("112324");
        Header header = new Header(RequestType.addHotelMapping,"S10085349","security_test_S10085349");
        GetHotelRequest getHotelRequest = new GetHotelRequest(header,getHotelInfoRequest);

        /*XStream xStream = new XStream(new DomDriver("utf-8"));
        xStream.processAnnotations(GetHotelRequest.class);
        System.out.println(xStream.toXML(getHotelInfoRequest));*/
        try {
           /* JAXBContext context = JAXBContext.newInstance(GetHotelRequest.class);
            Marshaller marshaller = context.createMarshaller();
            StringWriter fw = new StringWriter();
            marshaller.marshal(getHotelRequest,fw);
            String string = fw.toString();*/

            String s = FcUtil.fcRequest(getHotelRequest);
            System.out.println(s);

           /* try {
                String gets = HttpClientUtil.httpPost("http://www.fangcang.org/USP/api_v1/getHotelInfo", string);
                System.out.println(gets);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

