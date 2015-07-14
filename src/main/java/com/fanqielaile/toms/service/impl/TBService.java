package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.RoomSwitchCalStatus;
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
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
   /* @Resource
    private BusinLogClient businLogClient;*/

    /**
     * 想淘宝添加/更新酒店
     * @param tbParam 参数
     */
    @Override
   /* @Log(descr ="酒店更新、增加")*/
    public void updateOrAddHotel(TBParam tbParam, OtaInfo otaInfo) throws Exception {
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
        //客栈
        if (Constants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(tbParam.getInnId());
            OtaTaoBaoArea andArea = null;
            if (!StringUtils.isEmpty(omsInnDto.getCity())){
                andArea = taoBaoAreaDao.findCityAndArea(omsInnDto.getCity());
            }
            if (!StringUtils.isEmpty(omsInnDto.getCounty())){
                andArea = taoBaoAreaDao.findCountyAndCity(andArea.getCityCode(), omsInnDto.getCounty());
            }
            xHotel = TBXHotelUtil.hotelAdd(otaInfo, omsInnDto, andArea);
            if (xHotel!=null) {
                otaInnOta = OtaInnOtaDto.toDto(xHotel.getHid(), omsInnDto.getInnName(), company.getId(), tbParam);
                otaInnOtaDao.saveOtaInnOta(otaInnOta);
                otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                priceModelDao.savePriceModel(otaPriceModel);
                BangInnDto bangInnDto = BangInnDto.toDto(company.getId(),tbParam,otaInnOta.getUuid(),omsInnDto.getInnName());
                bangInnDao.createBangInn(bangInnDto);
            }else {
                log.info("更新 --  tbParam:"+tbParam.toString());
                otaInnOta =  otaInnOtaDao.findOtaInnOtaByParams(tbParam);
                otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
            }
        }else {
            throw new TomsRuntimeException("无客栈信息!");
        }
        //添加更新宝贝
        updateOrAddRoom(tbParam,otaInfo,otaPriceModel,otaInnOta);
    }

    //添加更新宝贝
    private   void updateOrAddRoom(TBParam tbParam,OtaInfo otaInfo,OtaPriceModelDto otaPriceModel,OtaInnOtaDto otaInnOta)throws Exception{
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(),String.valueOf(r.getRoomTypeId()), Long.valueOf(otaInnOta.getWgHid()), r, otaInfo);
                if (xRoomType!=null){
                    OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModel.getUuid(), otaInnOta.getUuid(), xRoomType.getRid());
                    otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo,tbParam.getStatus());
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAdd(otaInfo, r);
                    OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOta.getUuid(),String.valueOf(xRoomType.getRid()));
                    goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    TBXHotelUtil.rateAdd(otaInfo, gid, rpid, r, otaPriceModel);
                }else {
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.findOtaBangInnRoom(otaInnOta.getId(), r.getRoomTypeId());
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus());
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(Long.valueOf(otaBangInnRoomDto.getrId()));
                    goodsDao.updateRoomTypeGoodsProductDate(innRoomTypeGoodsDto.getId());
                    //保存商品关联信息
                    if (DcUtil.isEmpty(innRoomTypeGoodsDto.getGid()) && DcUtil.isEmpty(innRoomTypeGoodsDto.getRpid())) {
                        TBXHotelUtil.rateUpdate(otaInfo, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r, otaPriceModel, !tbParam.isSj());
                    }
                }
            }
        }else {
            throw new TomsRuntimeException("无房型信息!");
        }
    }
    @Override
   /* @Log(descr ="酒店酒店删除、解绑")*/
    public void deleteHotel(TBParam tbParam, OtaInfo otaInfo) throws Exception {
        /*String event = TomsUtil.event(tbParam);
        log.info("event:"+event);
        try {
            businLog.setDescr("酒店酒店删除、解绑");
            businLog.setEvent(event);
            businLogClient.save(businLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }*/
        //公司基本信息
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        //客栈绑定信息
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
        if (bangInn!=null){
            //解除此客在此企业的绑定关系
            bangInnDao.deleteBangInnByCompanyIdAndInnId(company.getId(), bangInn.getInnId());
            //删除客栈与渠道绑定关系
            otaInnOtaDao.deletedOtaInnOtaById(bangInn.getOtaWgId());
            //删除客栈房型关系
            otaBangInnRoomDao.deletedBangInnRoom(bangInn.getOtaWgId());
            //删除客栈宝贝关系
            goodsDao.deletedGoods(bangInn.getOtaWgId());
            DcUtil.hotelParam(tbParam,bangInn.getAccountId(),company.getOtaId());
            String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.ROOM_TYPE);
            String roomTypeGets = HttpClientUtil.httpGets(room_type,null);
            JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
            if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
                List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
                for (RoomTypeInfo r:list){
                    TBXHotelUtil.roomUpdate(r.getRoomTypeId(), r, otaInfo, RoomSwitchCalStatus.DEL);
                    //更新库存 --删除不用更新库存
                    /*if (DcUtil.isEmpty(innRoomTypeGoodsDto.getGid()) &&DcUtil.isEmpty(innRoomTypeGoodsDto.getRpid())) {
                        TBXHotelUtil.rateUpdate(company, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r, otaPriceModel, true);
                    }*/
                }
            }
        }else {
            throw new TomsRuntimeException("删除失败,此客栈没有绑定此企业!");
        }

    }

    @Override
    public void updateHotel(OtaInfoDto o,TBParam tbParam) throws Exception {
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        tbParam.setCompanyCode(o.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
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
                updateOrAddHotel( tbParam,   o);

            }
        }
    }


}
