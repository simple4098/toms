/*

package com.toms.test;

import com.fanqie.core.dto.PriceModel;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.TomsConstants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
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
import java.util.*;


*/
/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 *//*


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
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource(name = "tbService")
    private ITPService tbService;



    */
/*@Test
    public void  newInnTest() throws Exception {
        String s ="3570193376" +
                ",3570193376" +
                ",3570193376" +
                ",3569233376" +
                ",3569233376" +
                ",3570193376" +
                ",3569233376" +
                ",3570193376" +
                ",3569233376" +
                ",3569233376" +
                ",3569233376" +
                ",3569233376" +
                ",3569233376" +
                ",3569233376" +
                ",3569233376" +
                ",3641032376" +
                ",3641032376" +
                ",3641032376" +
                ",3641032376" +
                ",3641032376" +
                ",3641032376" +
                ",3649363376" +
                ",3649363376" +
                ",3649363376" +
                ",3649363376" +
                ",3649363376" +
                ",3650891376" +
                ",3650891376" +
                ",3658378376" +
                ",3658378376" +
                ",3656418376" +
                ",3656418376" +
                ",3657072376" +
                ",3656418376" +
                ",3656418376" +
                ",3656418376" +
                ",3657072376" +
                ",3657072376" +
                ",3657072376" +
                ",3658378376" +
                ",3658378376" +
                ",3658378376" +
                ",3656418376" +
                ",3656418376" +
                ",3656418376" +
                ",3656418376" +
                ",3658378376" +
                ",3656418376" +
                ",3658432376" +
                ",3658432376" +
                ",3658432376" +
                ",3658432376" +
                ",3658432376" +
                ",3658432376" +
                ",3658432376" +
                ",3691516376" +
                ",3690536376" +
                ",3690536376" +
                ",3690536376" +
                ",3708757376" +
                ",3708757376" +
                ",3708757376" +
                ",3708757376" +
                ",3708757376" +
                ",3708757376" +
                ",3715504376" +
                ",3715504376" +
                ",3729172376" +
                ",3729172376" +
                ",3729172376" +
                ",3737825376" +
                ",3737825376" +
                ",3737825376" +
                ",3742737376" +
                ",3739932376" +
                ",3739932376" +
                ",3739932376" +
                ",3739932376" +
                ",3739932376" +
                ",3742737376" +
                ",3739932376" +
                ",3767035376" +
                ",3767035376" +
                ",3767035376" +
                ",3701421376" +
                ",3825233376" +
                ",3825233376" +
                ",3825233376" +
                ",3828320376" +
                ",3828320376" +
                ",3828320376" +
                ",3701421376" +
                ",3828320376" +
                ",3828320376" +
                ",3828320376" +
                ",3825233376" +
                ",3828320376" +
                ",3828320376" +
                ",3828320376" +
                ",3828320376" +
                ",3825361376" +
                ",3828320376" +
                ",3825372376" +
                ",3825385376" +
                ",3825385376" +
                ",3825372376" +
                ",3825372376" +
                ",3825377376" +
                ",3825385376" +
                ",3825385376" +
                ",3825385376" +
                ",3825385376" +
                ",3825385376" +
                ",3826379376" +
                ",3825372376" +
                ",3828363376" +
                ",3825385376" +
                ",3828363376" +
                ",3828363376" +
                ",3825385376" +
                ",3825385376" +
                ",3826379376" +
                ",3825385376";

        String[] split = s.split(",");
        List<String> list = Arrays.asList(split);
        Set<String> set = new HashSet<String>();
        for (String v:list){
            set.add(v);
        }
        List<OtaInfoRefDto> otaInfoRefDtoList = otaInfoDao.selectAllOtaByCompany("89894098");
        OtaInfoRefDto o = otaInfoRefDtoList.get(0);
        TBParam tbParam = null;
        PriceModel price1 = null;
        for (String v:set){
            OtaInnOtaDto otaDto = otaInnOtaDao.selectOtaInnOtaByHid(Long.valueOf(v), "d0392bc8-131c-8989-846e-c81c66011111", "1");
            if (otaDto!=null && otaDto.getSj()==1){
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId("d0392bc8-131c-8989-846e-c81c66011111", otaDto.getInnId());
                if (bangInn!=null && bangInn.getSj()==1){
                    String accountId = String.valueOf(bangInn.getAccountId());
                    List<PriceModel> priceModelArray = new ArrayList<PriceModel>();
                    price1 =new PriceModel();
                    price1.setAccountId(accountId);
                    price1.setPattern("MAI");
                    priceModelArray.add(price1);
                    tbParam = new TBParam();
                    tbParam.setAccountId(accountId);
                    tbParam.setInnId(String.valueOf(bangInn.getInnId()));
                    tbParam.setOtaId("903");
                    tbParam.setPriceModel("MAI");
                    tbParam.setSj(true);
                    tbParam.setsJiaModel("MAI");
                    tbParam.setCompanyCode("89894098");
                    tbParam.setDeleted(false);
                    tbParam.setPriceModelArray(priceModelArray);
                    tbService.updateOrAddHotel(tbParam,o);
                }else {
                    System.out.println("下架的innId:"+otaDto.getInnId());
                }
            }else {
                System.out.println("下架的innId:"+otaDto.getInnId());
            }

        }


    }*//*


   */
/* @Test
    public void innTest() throws Exception {
        BangInnDto bangInnDto = new BangInnDto();
        bangInnDto.setCompanyId("d0392bc8-131c-8989-846e-c81c66011111");
        List<BangInn> bangInns = bangInnDao.selectBangInn(bangInnDto);
        List<OtaInfoRefDto> otaInfoRefDtoList = otaInfoDao.selectAllOtaByCompany("89894098");
        for (OtaInfoRefDto otaInfoRefDto:otaInfoRefDtoList){
            TBParam tbParam = null;
            PriceModel price1 = null;
            for (BangInn bangInn:bangInns){
                String accountId = String.valueOf(bangInn.getAccountId());
                List<PriceModel> priceModelArray = new ArrayList<PriceModel>();
                price1 =new PriceModel();
                price1.setAccountId(accountId);
                price1.setPattern("MAI");
                priceModelArray.add(price1);
                tbParam = new TBParam();
                tbParam.setAccountId(accountId);
                tbParam.setInnId(String.valueOf(bangInn.getInnId()));
                tbParam.setOtaId("903");
                tbParam.setPriceModel("MAI");
                tbParam.setSj(true);
                tbParam.setsJiaModel("MAI");
                tbParam.setCompanyCode("89894098");
                tbParam.setDeleted(false);
                tbParam.setPriceModelArray(priceModelArray);
                tbService.updateOrAddHotel(tbParam,otaInfoRefDto);
            }
        }



    }
*//*


    @Test
    public void test() throws Exception {
        OtaInfoRefDto otaInfo = new OtaInfoRefDto();
        otaInfo.setAppKey("23192376");
        otaInfo.setAppSecret("c2e9acffbdf281c93b167601781cd228");
        otaInfo.setSessionKey("6102327e52f6e56b3f3a9aa57fab8ac2b4c18c1fe06d70e2555889376");
        */
/*otaInfo.setAppKey("1023192376");
        otaInfo.setAppSecret("sandboxfbdf281c93b167601781cd228");
        otaInfo.setSessionKey("6102630889b6592676681403674c57dec774131f5d37e973636630123");*//*

        otaInfo.setOtaInfoId("1");
        //String innId = "7060";
        String innId = "52457";
        String companyCode = "89894098";
        //String accountId = "14339";
        String accountId = "32664";
        String otaId = "903";
        String priceModel = "MAI";
        String shangJiaModel = "MAI";
        boolean deleted=false;
        boolean isSj=true;
        List<PriceModel> priceModelArray = new ArrayList<PriceModel>();
        PriceModel price1 = new PriceModel();
        price1.setAccountId("32664");
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
        String roomTypeUrl = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), accountId, CommonApi.ROOM_TYPE);
        String innInfoUrl = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), accountId, CommonApi.INN_INFO);
        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("903" + s + "TB" + "tb");
        //<property name="roomType" value="http://oms.fanqiele.com/api/getRoomType"></property>
        // <property name="innInfo" value="http://oms.fanqiele.com/api/getInnInfo"></property>
        //String inn_info ="http://oms.fanqiele.com/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        //String room_type ="http://oms.fanqiele.com/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-08-27&to=2015-10-26"+"&signature="+signature;
        //String inn_info ="http://192.168.1.158:8888/api/getInnInfo?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&signature="+signature;
        //String room_type ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+otaId+"&accountId="+accountId+"&from=2015-07-28&to=2015-08-27"+"&signature="+signature;
        String httpGets1 = HttpClientUtil.httpGets(innInfoUrl, null);
        String httpGets = HttpClientUtil.httpGets(roomTypeUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        JSONObject jsonInn = JSONObject.fromObject(httpGets1);
        XHotel xHotel = null;
        //Long rpid = null;
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        OtaTaoBaoArea andArea = null;
        //客栈
        if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
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
                otaInnOta = otaInnOtaDao.selectOtaInnOtaByHid(xHotel.getHid(),company.getId(),otaInfo.getOtaInfoId());
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
                //未绑定
                BangInnDto bangInnDto = null;
                if (bangInn==null){
                    bangInnDto = BangInnDto.toDto(company.getId(), tbParam,  omsInnDto);
                    bangInnDao.createBangInn(bangInnDto);
                    //已绑定
                }else {
                    BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
                    bangInnDao.updateBangInnTp(bangInn);
                }
                String bangInnId = bangInn==null?bangInnDto.getUuid():bangInn.getId();
                if (otaInnOta==null){
                    otaInnOta = OtaInnOtaDto.toDto(xHotel.getHid(), omsInnDto.getInnName(), company.getId(), tbParam,bangInnId,otaInfo.getOtaInfoId());
                    otaInnOta.setSj(tbParam.isSj()?1:0);
                    otaInnOtaDao.saveOtaInnOta(otaInnOta);
                    otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                    priceModelDao.savePriceModel(otaPriceModel);
                }else {
                    otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
                    otaInnOta.setSj(tbParam.isSj()?1:0);
                    otaInnOtaDao.updateOtaInnOta(otaInnOta);
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
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
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
                    Long gid = TBXHotelUtil.roomAddOrUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus(),otaInnOta,andArea);
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
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), r.getRoomTypeId(), otaInfo.getOtaInfoId()));
                    TBXHotelUtil.rateAddOrUpdate(otaInfo, gid, rpid, r, otaPriceModel, !tbParam.isSj(),priceDto);
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
        OtaInfoRefDto o = new OtaInfoRefDto();
        o.setCompanyCode("11111111");
        TBParam tbParam = new TBParam();
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        tbParam.setCompanyCode(o.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        tbParam.setSj(true);
        String saleListUrl = DcUtil.omsQueryProxySaleListUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ProxySaleList);
        String roomTypeGets = HttpClientUtil.httpGets(saleListUrl,null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) ){
            List<ProxyInns> list = JacksonUtil.json2list(jsonObject.get("proxyInns").toString(), ProxyInns.class);
            List<PricePattern> pricePatterns = null;
            StringBuilder stringBuilder = new StringBuilder();
            List<PriceModel> priceModelList = null;
            PriceModel priceModel = null;
            //stringBuilder = new StringBuilder();
            for (ProxyInns proxyInns:list){

                pricePatterns = proxyInns.getPricePatterns();
                tbParam.setInnId(String.valueOf(proxyInns.getInnId()));
                System.out.println("客栈id:"+proxyInns.getInnId());
                stringBuilder.append(proxyInns.getInnId()).append(",");
               */
/* priceModelList = new ArrayList();
                for (PricePattern p:pricePatterns){
                    priceModel = new PriceModel();
                    if (p.getPattern().equals(1)){
                        tbParam.setAccountIdDi(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("DI");
                        stringBuilder.append("DI,");
                        priceModel.setAccountId(String.valueOf(p.getAccountId()));
                        priceModel.setPattern("DI");
                    }
                    if (p.getPattern().equals(2)){
                        tbParam.setAccountId(String.valueOf(p.getAccountId()));
                        tbParam.setsJiaModel("MAI");
                        stringBuilder.append("MAI,");
                        priceModel.setAccountId(String.valueOf(p.getAccountId()));
                        priceModel.setPattern("MAI");
                    }
                    priceModelList.add(priceModel);
                }
                tbParam.setPriceModelArray(priceModelList);
                if (stringBuilder.toString().lastIndexOf(",")!=-1){
                    stringBuilder.deleteCharAt(stringBuilder.length()-1);
                }
                tbParam.setPriceModel(stringBuilder.toString());
                System.out.println(tbParam.toString());*//*

                //更新酒店
                //updateOrAddHotel(tbParam, o);
            }
            if (stringBuilder.toString().lastIndexOf(",")!=-1){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            System.out.println(stringBuilder.toString());
        }
    }

    @Test
    public void test5() throws Exception {
        OtaInfoRefDto otaInfo = new OtaInfoRefDto();
        otaInfo.setAppKey("1023192376");
        otaInfo.setAppSecret("sandboxfbdf281c93b167601781cd228");
        otaInfo.setSessionKey("6102630889b6592676681403674c57dec774131f5d37e973636630123");
        otaInfo.setCompanyId("88888888");
        otaInfo.setOtaInfoId("1");
        String xml ="<PushRoomType><list><RoomType><AccountId>123</AccountId><RoomTypeId>1018376</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2015-08-01</RoomDate><RoomPrice>11.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>11</RoomNum></RoomDetail><RoomDetail><RoomDate>2015-08-02</RoomDate><RoomPrice>22.0</RoomPrice><PriRoomPrice>22.0</PriRoomPrice><RoomNum>22</RoomNum></RoomDetail></RoomDetails></RoomType></list></PushRoomType>";
        //String xml ="<PushRoomType><list><RoomType><AccountId>123</AccountId><RoomTypeId>1018376</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2015-08-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail><RoomDetail><RoomDate>2015-08-02</RoomDate><RoomPrice>1.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>2</RoomNum></RoomDetail></RoomDetails></RoomType><RoomType><AccountId>123</AccountId><RoomTypeId>1018376</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2015-08-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail><RoomDetail><RoomDate>2015-08-02</RoomDate><RoomPrice>1.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>2</RoomNum></RoomDetail></RoomDetails></RoomType></list></PushRoomType>";
        List<PushRoom> pushRoomList = XmlDeal.getPushRoom(xml);
        for (PushRoom pushRoom: pushRoomList){
            //验证此房型是不是在数据库存在
            OtaInnRoomTypeGoodsDto good  = goodsDao.selectGoodsByRoomTypeIdAndCompany(otaInfo.getCompanyId(), pushRoom.getRoomType().getRoomTypeId());
            OtaPriceModelDto priceModel = priceModelDao.findOtaPriceModelByWgOtaId(good.getOtaWgId());
            if (good!=null){
                log.info("roomTypeId:"+pushRoom.getRoomType().getRoomTypeId() +" roomTypeName："+pushRoom.getRoomType().getRoomTypeName());
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(otaInfo.getCompanyId(), pushRoom.getRoomType().getRoomTypeId(),  otaInfo.getOtaInfoId()));
                //TBXHotelUtil.updateHotelPushRoom(otaInfo, pushRoom, priceModel,priceDto);

            } else {
                log.info("此房型还没有上架 roomTypeId:"+pushRoom.getRoomType().getRoomTypeId());
            }
        }
       */
/* for (PushRoom r:pushRoomList){
            TBXHotelUtil.updateHotelPushRoom(otaInfo,r);
        }*//*


    }
}

*/
