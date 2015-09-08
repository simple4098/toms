package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.PriceModel;
import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.TomsConstants;
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
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.domain.Rate;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoom;
import com.taobao.api.domain.XRoomType;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jaxen.function.NamespaceUriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC : 添加/获取/更新 酒店
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Service("tbService")
/*@LogModule("TP 推酒店房型service")*/
public class TBService implements ITPService {
    private static  final Logger log = LoggerFactory.getLogger(TBService.class);
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
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
  /*  @Resource
    private BusinLogClient businLogClient;*/

    /**
     * 想淘宝添加/更新酒店
     * @param tbParam 参数
     */
    @Override
    /*@Log(descr ="酒店更新、增加")*/
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
       /* String event = TomsUtil.event(tbParam);
        log.info("event:"+event);
        try {
            businLog.setDescr("TP推酒店");
            businLog.setEvent(event);
            businLogClient.save(businLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }*/
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(),company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.INN_INFO);
        String innInfoGet = HttpClientUtil.httpGets(inn_info, null);
        JSONObject jsonInn = JSONObject.fromObject(innInfoGet);
        XHotel xHotel = null;
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        OtaTaoBaoArea andArea = null;
        //客栈
        if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(tbParam.getInnId());

            if (!StringUtils.isEmpty(omsInnDto.getCity())){
                andArea = taoBaoAreaDao.findCityAndArea(omsInnDto.getCity());
            }
            //推客栈、如果存在再获取客栈
            //xHotel = TBXHotelUtil.hotelAdd(otaInfo, omsInnDto, andArea);
            xHotel = TBXHotelUtil.hotelAddOrUpdate(otaInfo, omsInnDto, andArea);
            if (xHotel!=null) {
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
                otaInnOta = otaInnOtaDao.selectOtaInnOtaByHid(xHotel.getHid(),company.getId(),otaInfo.getOtaInfoId());
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
                //添加更新宝贝
                updateOrAddRoom(tbParam,otaInfo,otaPriceModel,otaInnOta,andArea);
            }else {
                log.debug(" 推送淘宝客栈失败!");
            }
        }else {
            log.debug("无客栈信息!");
        }

    }

    //添加更新宝贝
    private   void updateOrAddRoom(TBParam tbParam,OtaInfoRefDto otaInfo,OtaPriceModelDto otaPriceModel,OtaInnOtaDto otaInnOta,OtaTaoBaoArea andArea)throws Exception{
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
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        //房型
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(), Long.valueOf(otaInnOta.getWgHid()), r, otaInfo);
                log.info("updateOrAddRoom xRoomType:" + xRoomType);
                if (xRoomType!=null){
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.selectBangInnRoomByRidAndCompanyId(String.valueOf( xRoomType.getRid()),company.getId());
                    if (otaBangInnRoomDto==null){
                        OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModelId, otaInnOtaId, xRoomType.getRid());
                        otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    }
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus(), otaInnOta, andArea);
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAddOrUpdate(otaInfo, r);
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(xRoomType.getRid());
                    if (innRoomTypeGoodsDto==null){
                        OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOtaId, String.valueOf(xRoomType.getRid()));
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
                    log.debug(" 推房型失败~");
                }
            }
        }else {
            log.debug("无房型信息!");
        }
    }
    @Override
   /* @Log(descr ="酒店酒店删除、解绑")*/
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
    }

    @Override
    public void updateHotel(OtaInfoRefDto o,TBParam tbParam) throws Exception {
       /* String event = TomsUtil.event(tbParam);
        log.info("event:"+event);
        try {
            businLog.setDescr("定时更新房型信息");
            businLog.setEvent(event);
            businLogClient.save(businLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }*/
        log.info("====同步 start====");
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
            StringBuilder stringBuilder = null;
            List<PriceModel> priceModelList = null;
            PriceModel priceModel = null;
            for (ProxyInns proxyInns:list){
                log.info("客栈id:"+proxyInns.getInnId());
                //if (proxyInns.getInnId().equals(4905) || proxyInns.getInnId().equals(14284)){
                //if (proxyInns.getInnId().equals(26042) ){
                    stringBuilder = new StringBuilder();
                    pricePatterns = proxyInns.getPricePatterns();
                    tbParam.setInnId(String.valueOf(proxyInns.getInnId()));
                    priceModelList = new ArrayList();
                    for (PricePattern p:pricePatterns){
                        priceModel = new PriceModel();
                        if (p.getPattern().equals(1)){
                            tbParam.setAccountIdDi(String.valueOf(p.getAccountId()));
                            stringBuilder.append("DI,");
                            priceModel.setAccountId(String.valueOf(p.getAccountId()));
                            priceModel.setPattern("DI");
                        }
                        if (p.getPattern().equals(2)){
                            tbParam.setAccountId(String.valueOf(p.getAccountId()));
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
                    if (stringBuilder.indexOf("MAI")!=-1){
                        tbParam.setsJiaModel("MAI");
                    }else {
                        continue;
                    }
                    //更新酒店
                    updateOrAddHotel(tbParam, o);
               //}
            }
        }
    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto o, List<PushRoom> pushRoomList) throws Exception {
        for (PushRoom pushRoom: pushRoomList){
           Integer roomTypeId = pushRoom.getRoomType().getRoomTypeId();
            //查询客栈是否是上架状态
            BangInn bangInn =  bangInnDao.selectBangInnByParam(o.getCompanyId(), o.getOtaInfoId(), pushRoom.getRoomType().getAccountId());
            //验证此房型是不是在数据库存在
            OtaInnRoomTypeGoodsDto good  = goodsDao.selectGoodsByRoomTypeIdAndCompany(o.getCompanyId(), roomTypeId);
            if ( bangInn!=null){
                if (good!=null){
                    OtaPriceModelDto priceModel = priceModelDao.findOtaPriceModelByWgOtaId(good.getOtaWgId());
                    log.info("roomTypeId:"+pushRoom.getRoomType().getRoomTypeId() +" roomTypeName："+pushRoom.getRoomType().getRoomTypeName());
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(o.getCompanyId(), roomTypeId,  o.getOtaInfoId()));
                    TBXHotelUtil.updateHotelPushRoom(o, pushRoom, priceModel,priceDto);
                } else {
                    log.info("此房型还没有上架 roomTypeId:"+pushRoom.getRoomType().getRoomTypeId());
                }
            }else {
                log.info("此客栈已经下架AccountId:"+ pushRoom.getRoomType().getAccountId());
            }
        }
    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto o, OtaRoomPriceDto roomPriceDto)throws Exception{
        Integer innId = roomPriceDto.getInnId();
        String companyId = roomPriceDto.getCompanyId();
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, innId);
        List<RoomTypeInfo> list = otaRoomPriceService.obtOmsRoomInfo(bangInn);
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(),companyId,roomPriceDto.getOtaInfoId());
        if (otaInnOta!=null && otaInnOta.getSj()==0){
            throw  new TomsRuntimeException("此客栈已经下架!");
        }
        OtaPriceModelDto priceModelDto = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
        //房型
        if (!CollectionUtils.isEmpty(list)){
            XRoom xRoom = null;
            Rate rate = null;
            String obtInventory= "";
            String obtInventoryRate= "";
            for (RoomTypeInfo r:list){
                xRoom = TBXHotelUtil.roomGet(r.getRoomTypeId(), o);
                rate = TBXHotelUtil.rateGet(o, r);
                if (xRoom!=null && rate!=null ){
                    if (r.getRoomDetail()!= null){
                        obtInventory = TomsUtil.obtInventory(r);
                        xRoom.setInventory(obtInventory);
                        log.info("宝贝roomTypeId：" + r.getRoomTypeId() + " 同步到tp店" + obtInventory);
                        TBXHotelUtil.roomUpdate(o, xRoom);
                        OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(companyId, r.getRoomTypeId(), roomPriceDto.getOtaInfoId()));
                        log.info("处理之前的价格json：" + TomsUtil.obtInventoryRate(r,priceModelDto));
                        obtInventoryRate = TomsUtil.obtInventoryRate(r,priceModelDto,priceDto);
                        log.info("处理之后的价格json：" + obtInventoryRate);
                        rate.setInventoryPrice(obtInventoryRate);
                        TBXHotelUtil.rateUpdate(o, r, rate);
                    }else {
                        log.info("r.getRoomTypeId()：" + r.getRoomTypeId()+" 此房型无房价信息");
                    }
                }
            }
        }else {
            throw new TomsRuntimeException("无房型信息!");
        }
    }


}
