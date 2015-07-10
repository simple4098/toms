/*
package com.toms.test;

import com.fanqie.core.dto.PriceModel;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.Constants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml","/conf/spring/spring-security.xml"})
public class TBTest {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtil.class);

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
    @Resource
    private IOtaInnRoomTypeGoodsDao goodsDao;
    @Resource
    private IOtaInfoDao otaInfoDao;

    @Test
    public void test() throws IOException {
        OtaInfo otaInfo = new OtaInfo();
        otaInfo.setAppKey("23192376");
        otaInfo.setAppSecret("c2e9acffbdf281c93b167601781cd228");
        otaInfo.setSessionKey("61020029981c0bc9a0d28328c154c869258136962d6557e2555889376");
        //String innId = "7060";
        String innId = "22634";
        String companyCode = "11111111";
        //String accountId = "14339";
        String accountId = "1000721";
        String otaId = "903";
        String priceModel = "MAI,DI";
        String shangJiaModel = "MAI";
        boolean deleted=false;
        boolean isSj=true;
        List<PriceModel> priceModelArray = new ArrayList<PriceModel>();
        PriceModel price1 = new PriceModel();
        price1.setAccountId("1000721");
        price1.setPattern("MAI");
        PriceModel price2 = new PriceModel();
        price2.setAccountId("4162");
        price2.setPattern("DI");
        priceModelArray.add(price1);
        priceModelArray.add(price2);
        TBParam tbParam = new TBParam();
        tbParam.setAccountId(accountId);
        tbParam.setInnId(innId);
        tbParam.setOtaId(otaId);
        tbParam.setPriceModel(priceModel);
        tbParam.setSj(isSj);
        tbParam.setsJiaModel(shangJiaModel);
        tbParam.setCompanyCode(companyCode);
        tbParam.setDeleted(deleted);
        tbParam.setPriceModelArray(priceModelArray);
        Company company = companyDao.selectCompanyByCompanyCode(companyCode);
        String roomTypeUrl = DcUtil.omsUrl(company.getOtaId(),company.getUserAccount(),company.getUserPassword(),accountId, CommonApi.ROOM_TYPE);
        String innInfoUrl = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), accountId, CommonApi.INN_INFO);
        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("903" + s + "TB" + "tb");
        String inn_info ="http://192.168.1.158:8888/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        String room_type ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-07-09&to=2015-08-06"+"&signature="+signature;
        String httpGets1 = HttpClientUtil.httpGets(inn_info, null);
        String httpGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        JSONObject jsonInn = JSONObject.fromObject(httpGets1);
        XHotel xHotel = null;
        //Long rpid = null;
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        //客栈
        if (Constants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(innId);
            OtaTaoBaoArea andArea = null;
            if (!StringUtils.isEmpty(omsInnDto.getCity())){
                andArea = taoBaoAreaDao.findCityAndArea(omsInnDto.getCity());
            }
            if (!StringUtils.isEmpty(omsInnDto.getCounty())){
                andArea = taoBaoAreaDao.findCountyAndCity(andArea.getCityCode(), omsInnDto.getCounty());
            }
            xHotel = TBXHotelUtil.hotelAdd(otaInfo, omsInnDto,andArea);
            if (xHotel!=null) {
                otaInnOta = OtaInnOtaDto.toDto(xHotel.getHid(), omsInnDto.getInnName(), company.getId(), tbParam);
                otaInnOtaDao.saveOtaInnOta(otaInnOta);
                otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                priceModelDao.savePriceModel(otaPriceModel);
                BangInnDto bangInnDto = BangInnDto.toDto(company.getId(),tbParam,otaInnOta.getUuid(),omsInnDto.getInnName());
                bangInnDao.createBangInn(bangInnDto);
            }else {
                otaInnOta =  otaInnOtaDao.findOtaInnOtaByParams(tbParam);
                otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
                //xHotel = TBXHotelUtil.hotelGet(Long.valueOf(otaInnOta.getWgHid()),company);
            }
        }

        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(innId,String.valueOf(r.getRoomTypeId()), Long.valueOf(otaInnOta.getWgHid()), r, otaInfo);
                if (xRoomType!=null){
                    OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(innId, r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModel.getUuid(), otaInnOta.getUuid(), xRoomType.getRid());
                    otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo,tbParam.getStatus());
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAdd(otaInfo, r);
                    OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(innId, r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOta.getUuid(),String.valueOf(xRoomType.getRid()));
                    goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    //保存商品关联信息
                    TBXHotelUtil.rateUpdate(otaInfo, gid, rpid, r,otaPriceModel,!tbParam.isSj());
                }else {
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.findOtaBangInnRoom(otaInnOta.getId(), r.getRoomTypeId());
                    //XRoomType roomType = TBXHotelUtil.getRoomType(Long.valueOf(otaBangInnRoomDto.getrId()), company);
                    TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus());
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(Long.valueOf(otaBangInnRoomDto.getrId()));
                    goodsDao.updateRoomTypeGoodsProductDate(innRoomTypeGoodsDto.getId());
                    //保存商品关联信息
                    if (DcUtil.isEmpty(innRoomTypeGoodsDto.getGid()) &&DcUtil.isEmpty(innRoomTypeGoodsDto.getRpid())){
                        TBXHotelUtil.rateUpdate(otaInfo, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r,otaPriceModel,!tbParam.isSj());
                    }
                }
            }
        }

    }



    @Test
    @Ignore
    public void  test1(){
        TBParam tbParam = new TBParam();
        tbParam.setCompanyCode("11111111");
        tbParam.setCommissionPercent(new BigDecimal(1.5));
        tbParam.setCommissionType("MAI,DI");
        List<String> list = otaInnOtaDao.findOtaInnOtaIdsByCompanyCode(tbParam.getCompanyCode());
        if (!CollectionUtils.isEmpty(list) && tbParam.getCommissionPercent()!=null){
            StringBuilder listIds = new StringBuilder();
            for (String v:list){
                listIds.append("\'").append(v).append("\'").append(",");
            }
            listIds.deleteCharAt(listIds.length()-1);
            otaInnOtaDao.updateOtaInnOtaCommission(list,tbParam.getCommissionPercent(),tbParam.getCommissionType());
        }
    }



    @Test
    @Ignore
    public void  test4() throws IOException {

        TBParam tbParam  =  new TBParam();
        tbParam.setCompanyCode("11111111");
        Company company = companyDao.selectCompanyByCompanyCode("11111111");
        String saleListUrl = DcUtil.omsQueryProxySaleListUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ProxySaleList);
        String roomTypeGets = HttpClientUtil.httpGets(saleListUrl,null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) ){
            List<ProxyInns> list = JacksonUtil.json2list(jsonObject.get("proxyInns").toString(), ProxyInns.class);
            List<PricePattern> pricePatterns = null;
            for (ProxyInns proxyInns:list){
                pricePatterns = proxyInns.getPricePatterns();
                tbParam.setInnId(String.valueOf(proxyInns.getInnId()));
                for (PricePattern p:pricePatterns){
                    if (p.getPattern().equals(1)){
                        tbParam.setAccountIdDi(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("DI");
                    }
                    if (p.getPattern().equals(2)){
                        tbParam.setAccountId(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("MAI");
                    }
                }
                //更新酒店
                //updateOrAddHotel( tbParam,  businLog, o);

            }
        }
    }
}
*/
