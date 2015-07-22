
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
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
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


/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/conf/spring/spring-test-content.xml", "/conf/mybatis/sqlMapConfig.xml","/conf/spring/spring-security.xml"})
public class TBTestNew {
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
    @Ignore
    public void test() throws Exception {
        OtaInfo otaInfo = new OtaInfo();
        otaInfo.setAppKey("23192376");
        otaInfo.setAppSecret("c2e9acffbdf281c93b167601781cd228");
        otaInfo.setSessionKey("61008211bcf5e745e81bb59a3cf641d974ebb69d186733c2555889376");
        //String innId = "7060";
        String innId = "14284";
        String companyCode = "11111111";
        //String accountId = "14339";
        String accountId = "23442";
        String otaId = "903";
        String priceModel = "MAI,DI";
        String shangJiaModel = "MAI";
        boolean deleted=false;
        boolean isSj=true;
        List<PriceModel> priceModelArray = new ArrayList<PriceModel>();
        PriceModel price1 = new PriceModel();
        price1.setAccountId("23442");
        price1.setPattern("MAI");
        priceModelArray.add(price1);
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
        //<property name="roomType" value="http://oms.fanqiele.com/api/getRoomType"></property>
        // <property name="innInfo" value="http://oms.fanqiele.com/api/getInnInfo"></property>
        String inn_info ="http://oms.fanqiele.com/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        String room_type ="http://oms.fanqiele.com/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-07-22&to=2015-09-21"+"&signature="+signature;
        //String inn_info ="http://192.168.1.158:8888/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        //String room_type ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-07-22&to=2015-08-22"+"&signature="+signature;
        String httpGets1 = HttpClientUtil.httpGets(inn_info, null);
        String httpGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        JSONObject jsonInn = JSONObject.fromObject(httpGets1);
        XHotel xHotel = null;
        //Long rpid = null;
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        OtaTaoBaoArea andArea = null;
        //客栈
        if (Constants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(innId);

            if (!StringUtils.isEmpty(omsInnDto.getCity())){
                andArea = taoBaoAreaDao.findCityAndArea(omsInnDto.getCity());
            }
            if (!StringUtils.isEmpty(omsInnDto.getCounty())){
                andArea = taoBaoAreaDao.findCountyAndCity(andArea.getCityCode(), omsInnDto.getCounty());
            }
            xHotel = TBXHotelUtil.hotelAddOrUpdate(otaInfo, omsInnDto, andArea);
            if (xHotel!=null) {
                otaInnOta = otaInnOtaDao.selectOtaInnOtaByHid(xHotel.getHid(),company.getId());
                if (otaInnOta==null){
                    otaInnOta = OtaInnOtaDto.toDto(xHotel.getHid(), omsInnDto.getInnName(), company.getId(), tbParam);
                    otaInnOtaDao.saveOtaInnOta(otaInnOta);
                    otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                    priceModelDao.savePriceModel(otaPriceModel);
                    BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
                    if (bangInn==null){
                        BangInnDto bangInnDto = BangInnDto.toDto(company.getId(), tbParam, otaInnOta.getUuid(), omsInnDto);
                        bangInnDao.createBangInn(bangInnDto);
                    }else {
                        BangInnDto.toUpdateDto(bangInn, tbParam, otaInnOta.getUuid(), omsInnDto);
                        bangInn.setInnName(omsInnDto.getInnName());
                        bangInnDao.updateBangInnTp(bangInn);
                    }
                }else {
                    //otaInnOta =  otaInnOtaDao.findOtaInnOtaByParams(tbParam);
                    otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
                    BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
                    if (bangInn==null){
                        BangInnDto bangInnDto = BangInnDto.toDto(company.getId(), tbParam, otaInnOta.getId(), omsInnDto);
                        bangInnDao.createBangInn(bangInnDto);
                    }else {
                        BangInnDto.toUpdateDto(bangInn, tbParam, otaInnOta.getId(), omsInnDto);

                        bangInnDao.updateBangInnTp(bangInn);
                    }
                }
            }else {
                throw  new TomsRuntimeException(" 推送淘宝客栈失败!");
            }
        }

        String otaPriceModelId="";
        String otaInnOtaId="";
        if (StringUtils.isEmpty(otaPriceModel.getId())){
            otaPriceModelId = otaPriceModel.getUuid();
        }else {
            otaPriceModelId = otaPriceModel.getId();
        }
        if (StringUtils.isEmpty(otaInnOta.getId())){
            otaInnOtaId = otaInnOta.getUuid();
        }else {
            otaInnOtaId = otaInnOta.getId();
        }
        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(), Long.valueOf(otaInnOta.getWgHid()), r, otaInfo);
                log.info("updateOrAddRoom xRoomType:" + xRoomType);
                if (xRoomType!=null){
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.selectBangInnRoomByRidAndCompanyId(String.valueOf( xRoomType.getRid()),company.getId());
                    if (otaBangInnRoomDto==null){
                        OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(),  otaPriceModelId, otaInnOtaId, xRoomType.getRid());
                        otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    }
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus(),otaInnOta,andArea);
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAdd(otaInfo, r);
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(xRoomType.getRid());
                    if (innRoomTypeGoodsDto==null){
                        OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(),otaInnOtaId, String.valueOf(xRoomType.getRid()));
                        goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    }else {
                        if (gid!=null){
                            innRoomTypeGoodsDto.setGid(String.valueOf(gid));
                        }
                        if (rpid!=null){
                            innRoomTypeGoodsDto.setRpid(String.valueOf(rpid));
                        }
                        if (xRoomType.getRid()!=null){
                            innRoomTypeGoodsDto.setRid(String.valueOf(xRoomType.getRid()));
                        }
                        goodsDao.updateRoomTypeGoodsProductDate(innRoomTypeGoodsDto);
                    }
                    TBXHotelUtil.rateAddOrUpdate(otaInfo, gid, rpid, r, otaPriceModel, !tbParam.isSj());
                }else {
                    throw  new RuntimeException(" 推房型失败~");
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
            StringBuilder stringBuilder = null;
            for (ProxyInns proxyInns:list){
                stringBuilder = new StringBuilder();
                pricePatterns = proxyInns.getPricePatterns();
                tbParam.setInnId(String.valueOf(proxyInns.getInnId()));
                for (PricePattern p:pricePatterns){
                    if (p.getPattern().equals(1)){
                        tbParam.setAccountIdDi(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("DI");
                        stringBuilder.append("DI,");
                    }
                    if (p.getPattern().equals(2)){
                        tbParam.setAccountId(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("MAI");
                        stringBuilder.append("MAI,");
                    }
                }
                if (stringBuilder.toString().lastIndexOf(",")!=-1){
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                }
                tbParam.setPriceModel(stringBuilder.toString());
                //更新酒店
                //updateOrAddHotel(tbParam, o);

            }
        }
    }
}

