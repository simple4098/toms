package com.toms.test;

import com.fanqie.util.*;
import com.fanqielaile.toms.common.TBParam;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.ITBService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml","/conf/spring/spring-security.xml"})
public class TBTest {
    @Resource
    private ITBService tbService;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaPriceModelDao priceModelDao;
    @Resource
    private IOtaTaoBaoAreaDao taoBaoAreaDao;
    @Resource
    private IOtaBangInnRoomDao otaBangInnRoomDao;

    @Test
    public void test() throws IOException {
        //String innId = "7060";
        String innId = "22080";
        String companyCode = "11111111";
        //String accountId = "14339";
        String accountId = "14358";
        String otaId = "105";
        String priceModel = "MAI,DI";
        String shangJiaModel = "MAI";
        boolean deleted=false;
        boolean isSj=true;
        TBParam tbParam = new TBParam();
        Company company = companyDao.selectCompanyByCompanyCode(companyCode);
        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("105" + s + "MT" + "mt123456");
        String inn_info ="http://192.168.1.158:8888/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        String room_type ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-06-24&to=2015-07-23"+"&signature="+signature;
        String httpGets1 = HttpClientUtil.httpGets(inn_info, null);
        String httpGets = HttpClientUtil.httpGets(room_type,null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        JSONObject jsonInn = JSONObject.fromObject(httpGets1);
        //客栈
      /*  if (Constants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(innId);
            OtaTaoBaoArea andArea = taoBaoAreaDao.findCityAndArea("大理");
            XHotel xHotel = TBXHotelUtil.hotelAdd(company, omsInnDto,andArea);
            if (xHotel!=null) {
                OtaInnOtaDto otaInnOta = OtaInnOtaDto.toDto(xHotel.getHid(), omsInnDto.getInnName(), company.getId(), tbParam);
                otaInnOtaDao.saveOtaInnOta(otaInnOta);
                OtaPriceModelDto otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                priceModelDao.savePriceModel(otaPriceModel);
                BangInnDto bangInnDto = BangInnDto.toDto(company.getId(),tbParam,otaInnOta.getUuid(),omsInnDto.getInnName());
                bangInnDao.createBangInn(bangInnDto);
            }
        }*/

        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            String priceModelId = "098356aa-5c02-430b-b4ac-d436f7fcaa6f";
            String wgOtaId = "a44b3d2f-bb26-4ed8-8c53-000ee40c8455";
           for (RoomTypeInfo r:list){
               XRoomType xRoomType = TBXHotelUtil.addRoomType(String.valueOf(r.getRoomTypeId()), 16498001123l, r, company);
               if (xRoomType!=null){
                   OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(innId, r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), priceModelId, wgOtaId, xRoomType.getRid());
                   otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                   //添加商品 //todo

                   //保存商品关联信息 //todo
               }
           }
        }

    }

}
