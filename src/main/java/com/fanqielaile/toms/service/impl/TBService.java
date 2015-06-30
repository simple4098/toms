package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.Constants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.service.ITBService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.JsonModel;
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
import java.util.Date;
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
    public JsonModel hotelAddOrUpdate(TBParam tbParam,BusinLog businLog) throws IOException {
        JsonModel jsonModel = new JsonModel();
        //String innId = "7060";
        /*String innId = "22490";
        String companyCode = "11111111";
        //String accountId = "14339";
        String accountId = "16310";
        String otaId = "101";
        String priceModel = "MAI,DI";
        String shangJiaModel = "MAI";
        boolean deleted=false;
        boolean isSj=true;
        tbParam.setAccountId(accountId);
        tbParam.setInnId(innId);
        tbParam.setOtaId(otaId);
        tbParam.setPriceModel(priceModel);
        tbParam.setSj(isSj);
        tbParam.setsJiaModel(shangJiaModel);
        tbParam.setDeleted(deleted);*/
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        String room_type = DcUtil.omsUrl(company.getOtaId(),company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String inn_info = DcUtil.omsUrl(company.getOtaId(),company.getUserAccount(),company.getUserPassword(),tbParam.getAccountId(), CommonApi.INN_INFO);
        //String s = String.valueOf(new Date().getTime());
        //String signature = DcUtil.obtMd5("101" + s + "XZ" + "xz123456");
        //String inn_info ="http://192.168.1.158:8888/api/getInnInfo?timestamp="+s+"&otaId="+tbParam.getOtaId()+"&accountId="+tbParam.getAccountId()+"&signature="+signature;
        //String room_type ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+tbParam.getOtaId()+"&accountId="+tbParam.getAccountId()+"&from=2015-06-30&to=2015-07-23"+"&signature="+signature;
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
                xHotel = TBXHotelUtil.hotelGet(Long.valueOf(otaInnOta.getWgHid()),company);
            }
        }
        //房型
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(),String.valueOf(r.getRoomTypeId()), xHotel.getHid(), r, company);
                if (xRoomType!=null){
                    OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModel.getUuid(), otaInnOta.getUuid(), xRoomType.getRid());
                    otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    //添加商品
                    Long gid = TBXHotelUtil.roomUpdate(r.getRoomTypeId(), xHotel.getHid(), xRoomType.getRid(), r, company);
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAdd(company, r.getRoomTypeName()+r.getRoomTypeId());
                    OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOta.getUuid(),String.valueOf(xRoomType.getRid()));
                    goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    //保存商品关联信息
                    TBXHotelUtil.rateUpdate(company, gid, rpid, r,otaPriceModel,tbParam.isDeleted());
                }else {
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.findOtaBangInnRoom(otaInnOta.getId(), r.getRoomTypeId());
                    XRoomType roomType = TBXHotelUtil.getRoomType(Long.valueOf(otaBangInnRoomDto.getrId()), company);
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(roomType.getRid());
                    //保存商品关联信息
                    TBXHotelUtil.rateUpdate(company, Long.valueOf(innRoomTypeGoodsDto.getGid()), Long.valueOf(innRoomTypeGoodsDto.getRpid()), r,otaPriceModel,tbParam.isDeleted());
                }
            }
        }
        try {
            businLog.setDescr("TP推酒店");
            businLogClient.save(businLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return jsonModel;

    }

    @Override
    public XHotel hotelGet(Long hid,Company company, InnDto innDto) {

        return null;
    }
}
