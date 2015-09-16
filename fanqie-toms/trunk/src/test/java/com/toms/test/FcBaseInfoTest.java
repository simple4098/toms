package com.toms.test;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dao.IFcHotelInfoDao;
import com.fanqielaile.toms.dao.IFcRoomTypeInfoDao;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FileDealUtil;
import com.fanqielaile.toms.support.util.ResourceBundleUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2015/9/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml", "/conf/spring/spring-security.xml"})
public class FcBaseInfoTest {
    @Resource
    private IFcHotelInfoDao fcHotelInfoDao;
    @Resource
    private IFcRoomTypeInfoDao fcRoomTypeInfoDao;
    @Resource
    private IOrderService orderService;


    @Test
    public void testDownLoadFromFc() throws DocumentException {
        this.orderService.getFcAddHotelInfo();
//        FileDealUtil.deleteDir(new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd")));

    }

    /**
     * 解析全量天下房仓数据
     *
     * @throws DocumentException
     */

    @Test
    @Ignore
    public void testMakeFcBaseInfo() throws DocumentException {
        File file = new File("f:/all");
        if (file.isDirectory()) {
            System.out.println(file.list().length);
            File[] f = file.listFiles();
            for (int i = 0; i < file.list().length; i++) {
                if (f[i].isDirectory()) {
                    System.out.println(f[i].list().length);
                } else {
                    System.out.println("=====>" + f[i].getName());
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(f[i]);
                    FcHotelInfoDto fcHotelInfoDto = dealXml(document);
                    this.fcHotelInfoDao.insertFcHotelInfo(fcHotelInfoDto);
                    this.fcRoomTypeInfoDao.insertFcRoomTypeInfo(fcHotelInfoDto);
                }
            }
        }
    }


    private FcHotelInfoDto dealXml(Document document) {
        FcHotelInfoDto fcHotelInfoDto = new FcHotelInfoDto();
        Element element = document.getRootElement();
        fcHotelInfoDto.setHotelId(element.elementText("HotelId"));
        fcHotelInfoDto.setHotelName(element.elementText("HotelChnName"));
        fcHotelInfoDto.setHotelAddress(element.elementText("ChnAddress"));
        fcHotelInfoDto.setTelephone(element.elementText("Telephone"));
        fcHotelInfoDto.setWebsiteUrl(element.elementText("WebSiteURL"));
        fcHotelInfoDto.setHotelStar(Integer.parseInt(element.elementText("HotelStar")));
        fcHotelInfoDto.setCity(element.elementText("City"));
        fcHotelInfoDto.setDistinct(element.elementText("Distinct"));
        fcHotelInfoDto.setBusiness(element.elementText("Business"));
        fcHotelInfoDto.setFcRoomTypeInfos(getRoomTypeInfos(element, fcHotelInfoDto.getHotelId()));
        return fcHotelInfoDto;
    }

    private List<FcRoomTypeInfo> getRoomTypeInfos(Element element, String hotelId) {
        List<FcRoomTypeInfo> result = new ArrayList<>();
        List<Element> elements = element.element("Rooms").elements("RoomType");
        if (ArrayUtils.isNotEmpty(elements.toArray())) {
            for (Element e : elements) {
                FcRoomTypeInfo fcRoomTypeInfo = new FcRoomTypeInfo();
                fcRoomTypeInfo.setHotelId(hotelId);
                fcRoomTypeInfo.setRoomTypeId(e.elementText("RoomtypeId"));
                fcRoomTypeInfo.setRoomTypeName(e.elementText("RoomTypeName"));
                fcRoomTypeInfo.setBedType(e.elementText("BedType"));
                result.add(fcRoomTypeInfo);
            }
        }
        return result;
    }
}
