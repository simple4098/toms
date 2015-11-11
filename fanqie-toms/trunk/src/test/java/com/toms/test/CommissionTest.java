
package com.toms.test;

import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.ParamJson;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.OperateType;
import com.fanqielaile.toms.enums.PayMethod;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.service.ICommissionService;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang3.StringUtils;
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
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
public class CommissionTest {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtil.class);

    @Resource
    private IFcCityDao fcCityDao;
    @Resource
    private IFcProvinceDao fcProvinceDao;
    @Resource
    private IFcAreaDao fcAreaDao;
    @Resource
    private IFcHotelInfoDao fcHotelInfoDao;
    @Resource
    private IFcRoomTypeInfoDao fcRoomTypeInfoDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private ICommissionService commissionService;


    @Test
    public void test() throws Exception {

        String param = "{\"companyCode\":\"89894098\", \"commission\":{\"MAI\":10,\"MAI2DI\":20}}";
        ParamJson paramJson = JacksonUtil.json2obj(param, ParamJson.class);
        try {
            if(paramJson!=null && !StringUtils.isEmpty(paramJson.getCompanyCode())){
                commissionService.updateCommission(paramJson);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

