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
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.service.ITBService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import com.tomato.framework.log.annotation.Log;
import com.tomato.framework.log.annotation.LogModule;
import com.tomato.framework.log.client.BusinLogClient;
import com.tomato.log.model.BusinLog;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * DESC : 添加/获取/更新 酒店
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Service
@LogModule("TP 推酒店房型service")
public class TBService implements ITBService {
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
    private BusinLogClient businLogClient;

    /**
     * 想淘宝添加/更新酒店
     * @param tbParam 参数
     */
    @Override
    @Log(descr ="酒店更新、增加")
    public void updateOrAddHotel(TBParam tbParam, BusinLog businLog) throws Exception {
        String event = TomsUtil.event(tbParam);
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String inn_info = DcUtil.omsUrl(company.getOtaId(),company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.INN_INFO);
        String innInfoGet = HttpClientUtil.httpGets(inn_info, null);
        String roomTypeGets = HttpClientUtil.httpGets(room_type,null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
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
            xHotel = TBXHotelUtil.hotelAdd(company, omsInnDto, andArea);
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
        }else {
            throw new TomsRuntimeException("无客栈信息!");
        }
        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(),String.valueOf(r.getRoomTypeId()), Long.valueOf(otaInnOta.getWgHid()), r, company);
                if (xRoomType!=null){
                    OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModel.getUuid(), otaInnOta.getUuid(), xRoomType.getRid());
                    otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), Long.valueOf(otaInnOta.getWgHid()), xRoomType.getRid(), r, company,tbParam.getStatus());
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAdd(company, r.getRoomTypeName()+r.getRoomTypeId());
                    OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOta.getUuid(),String.valueOf(xRoomType.getRid()));
                    goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    //保存商品关联信息
                    TBXHotelUtil.rateUpdate(company, gid, rpid, r,otaPriceModel,!tbParam.isSj());
                }else {
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.findOtaBangInnRoom(otaInnOta.getId(), r.getRoomTypeId());
                   // XRoomType roomType = TBXHotelUtil.getRoomType(Long.valueOf(otaBangInnRoomDto.getrId()), company);
                    TBXHotelUtil.roomUpdate(r.getRoomTypeId(),Long.valueOf(otaInnOta.getWgHid()),Long.valueOf(otaBangInnRoomDto.getrId()), r, company, tbParam.getStatus());
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(Long.valueOf(otaBangInnRoomDto.getrId()));
                    //保存商品关联信息
                    if (DcUtil.isEmpty(innRoomTypeGoodsDto.getGid()) &&DcUtil.isEmpty(innRoomTypeGoodsDto.getRpid())) {
                        TBXHotelUtil.rateUpdate(company, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r, otaPriceModel, !tbParam.isSj());
                    }
                }
            }
        }else {
            throw new TomsRuntimeException("无房型信息!");
        }
        try {
            businLog.setDescr("TP推酒店");
            businLog.setEvent(event);
            businLogClient.save(businLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    @Log(descr ="酒店酒店删除、解绑")
    public void deleteHotel(TBParam tbParam, BusinLog businLog) throws Exception {
        //公司基本信息
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        //客栈绑定信息
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
        if (bangInn!=null){
            //解除此客在此企业的绑定关系
            bangInnDao.deleteBangInnByCompanyIdAndInnId(company.getId(), bangInn.getInnId());
            DcUtil.hotelParam(tbParam,bangInn.getAccountId(),company.getOtaId());
            //
            OtaInnOtaDto otaInnOta = otaInnOtaDao.findOtaInnOtaByParams(tbParam);
            //XHotel xHotel = TBXHotelUtil.hotelGet(Long.valueOf(otaInnOta.getWgHid()), company);
            OtaPriceModelDto otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
            String room_type = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.ROOM_TYPE);
            String roomTypeGets = HttpClientUtil.httpGets(room_type,null);
            JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
            if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
                List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
                for (RoomTypeInfo r:list){
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.findOtaBangInnRoom(otaInnOta.getId(), r.getRoomTypeId());
                    TBXHotelUtil.roomUpdate(r.getRoomTypeId(),Long.valueOf(otaInnOta.getWgHid()),Long.valueOf(otaBangInnRoomDto.getrId()), r, company, RoomSwitchCalStatus.DEL);
                    //XRoomType roomType = TBXHotelUtil.getRoomType(Long.valueOf(otaBangInnRoomDto.getrId()), company);
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(Long.valueOf(otaBangInnRoomDto.getrId()));
                    //更新库存
                    if (DcUtil.isEmpty(innRoomTypeGoodsDto.getGid()) &&DcUtil.isEmpty(innRoomTypeGoodsDto.getRpid())) {
                        TBXHotelUtil.rateUpdate(company, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r, otaPriceModel, true);
                    }
                }
            }
        }else {
            throw new TomsRuntimeException("删除失败,此客栈没有绑定此企业!");
        }

    }


}
