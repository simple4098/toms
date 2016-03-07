package com.fanqielaile.toms.support.tb;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.util.DateUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.PropertiesUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.exception.VettedOTAException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.ImgUtil;
import com.fanqielaile.toms.support.util.TPServiceUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.*;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC : 淘宝对接 API
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public class TBXHotelUtil {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtil.class);
    private TBXHotelUtil(){}


    //更新酒店
    public static XHotel hotelUpdate(OtaInfoRefDto company,InnDto innDto,OtaTaoBaoArea andArea)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelUpdateRequest req=new XhotelUpdateRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        req.setTel(innDto.getFrontPhone());
        req.setAddress(innDto.getAddr());
        try {
            XhotelUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("hotelUpdate:" + response.getXhotel());
            return response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }
    /**
     * 往淘宝添加、更新 酒店
     * @param company 渠道信息，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelAddOrUpdate(OtaInfoRefDto company,InnDto innDto,OtaTaoBaoArea andArea)   {
        XHotel xHotel = hotelGet(company, innDto.getInnId());
        if(xHotel==null){
            TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
            XhotelAddRequest req=new XhotelAddRequest();
            req.setOuterId(innDto.getInnId());
            req.setName(innDto.getBrandName());
            req.setUsedName(innDto.getInnName());
            req.setTel(innDto.getFrontPhone());
            if (andArea!=null){
                req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000);
                req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
                if (!StringUtils.isEmpty(andArea.getAreaCode())){
                    req.setDistrict(Long.valueOf(andArea.getAreaCode()));
                }
            }
            req.setAddress(innDto.getAddr());
            try {
                XhotelAddResponse response = client.execute(req , company.getSessionKey());
           /* log.info("hotelAddOrUpdate msg:"+response.getMsg()+" error code:"+response.getErrorCode());*/
                //存在
                if (TomsConstants.HOTEL_EXIST.equals(response.getSubCode())) {
                    return   hotelUpdate(company,innDto,andArea);
                }
                log.info("hotelAdd:" +response.getXhotel());
                return response.getXhotel();
            } catch (ApiException e) {
                log.error(e.getMessage());
            }
        }else {
            return   hotelUpdate(company,innDto,andArea);
        }

        return null;
    }

    /**
     * 获取酒店
     * @param company 公司信息
     */
    public static XHotel hotelGet(OtaInfoRefDto company,String innId) {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelGetRequest req=new XhotelGetRequest();
        req.setOuterId(innId);
        try {
            XhotelGetResponse response = client.execute(req , company.getSessionKey());
            log.info("hotelGet:" +  response.getXhotel());
            return  response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }
    /**
     * 获取酒店
     * @param company 公司信息
     */
    public static XHotel hotelGetHid(OtaInfoRefDto company,String hid) {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelGetRequest req=new XhotelGetRequest();
        req.setHid(Long.valueOf(hid));
        try {
            XhotelGetResponse response = client.execute(req , company.getSessionKey());
            log.info("hotelGet:" +  response.getXhotel());
            return  response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     *添加房型
     * @param innId pms系统中的客栈id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 渠道信息
     */
    public static XRoomType addRoomType(String innId,Long hid,RoomTypeInfo roomTypeInfo,OtaInfoRefDto company)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setOutHid(innId);
        req.setHid(hid);
        req.setName(roomTypeInfo.getRoomTypeName());
        //面积
        if (roomTypeInfo.getRoomArea()!=null){
            req.setArea(String.valueOf((int)Math.floor(roomTypeInfo.getRoomArea())));
        }
        //楼层
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        //床宽
        req.setBedSize(String.valueOf(roomTypeInfo.getBedWid()));
        List<FacilitiesVo> facilitiesMap = roomTypeInfo.getFacilitiesMap();
        //服务设施
        req.setService(TPServiceUtil.jsonService(facilitiesMap));
        try {
            XhotelRoomtypeAddResponse response = client.execute(req , company.getSessionKey());
          /*  log.info("addRoomType:"+response.getMsg()+ " error code:"+response.getErrorCode());*/
            if (TomsConstants.ROOM_TYPE_EXIST.equals(response.getSubCode())) {
                return  updateRoomType(company, roomTypeInfo);
            }
            log.info("addRoomType:" + response.getXroomtype());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取房型信息
     */
    public static XRoomType getRoomType(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeGetRequest req=new XhotelRoomtypeGetRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        try {
            XhotelRoomtypeGetResponse response = client.execute(req , company.getSessionKey());
            log.info("getRoomType:" + response.getXroomtype());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    /**
     * 获取房型信息
     */
    public static XRoomType updateRoomType(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeUpdateRequest req=new XhotelRoomtypeUpdateRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setName(roomTypeInfo.getRoomTypeName());
        //面积
        if (roomTypeInfo.getRoomArea()!=null){
            req.setArea(String.valueOf(roomTypeInfo.getRoomArea()).concat("平方米"));
        }
        //楼层
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        //床宽
        req.setBedSize(String.valueOf(roomTypeInfo.getBedWid()));
        List<FacilitiesVo> facilitiesMap = roomTypeInfo.getFacilitiesMap();
        //服务设施
        req.setService(TPServiceUtil.jsonService(facilitiesMap));
        try {
            XhotelRoomtypeUpdateResponse response = client.execute(req ,  company.getSessionKey());
            if (!StringUtils.isEmpty(response.getSubCode())) {
                return getRoomType(company,roomTypeInfo);
            }
            log.info("updateRoomType:" + response.getXroomtype());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    /**
     * 获取宝贝信息
     * @param outerId 房型id
     * @param company 渠道信息
     */
    public static XRoom roomGet(Integer outerId,OtaInfoRefDto company) throws ApiException {
        log.info("roomGet outerId:" +outerId );
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomGetRequest req=new XhotelRoomGetRequest();
        req.setOutRid(String.valueOf(outerId));
        XhotelRoomGetResponse response = null;
        try {
            response = client.execute(req , company.getSessionKey());
            if (!StringUtils.isEmpty(response.getSubCode())){
                return null;
            }
            return  response.getRoom();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    public static Long roomDel(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfoRefDto company,RoomSwitchCalStatus status) {
        log.info("roomDel:" +outerId );
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setOutRid(String.valueOf(outerId));
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<Inventory> list = new ArrayList<Inventory>();
            List<RoomSwitchCal> roomSwitchCals = new ArrayList<RoomSwitchCal>();
            Inventory inventory =null;
            RoomSwitchCal roomSwitchCal=null;
            for (RoomDetail r:roomTypeInfo.getRoomDetail()){
                inventory = new Inventory();
                roomSwitchCal = new RoomSwitchCal(r.getRoomDate());
                roomSwitchCal.setRoomSwitchCalStatus(status);
                inventory.setDate(r.getRoomDate());
                inventory.setQuota(r.getRoomNum() == null ? 0 : r.getRoomNum());
                inventory.setRoomSwitchCalStatus(status);
                list.add(inventory);
                roomSwitchCals.add(roomSwitchCal);
            }
            String json = JacksonUtil.obj2json(list);
            String roomSwitchJson = JacksonUtil.obj2json(roomSwitchCals);
            req.setInventory(json);
            //开关状态 1 上架  2 下架  3 删除
            req.setRoomSwitchCal(roomSwitchJson);
           /* log.info("roomSwitchJson:" + roomSwitchJson);
            log.info("Inventory:" +json);*/
        }
        XhotelRoomUpdateResponse response = null;
        try {
            response = client.execute(req , company.getSessionKey());
            log.info("roomDel:" + response.getGid());
            log.info("roomDel bady:" + response.getBody());
            if (!StringUtils.isEmpty(response.getSubCode())) {
                XRoom xRoom = roomGet(outerId, company);
                if (xRoom!=null){
                    return  xRoom.getGid();
                }else {
                    return null;
                }
            }
            return response.getGid();
        } catch (ApiException e) {
           log.error(e.getErrMsg());
        }
        return null;
    }
    /**
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public  static Long  roomUpdate(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfoRefDto company,RoomSwitchCalStatus status,OtaInnOtaDto otaInnOta,OtaTaoBaoArea andArea) throws Exception {
        log.info("start roomUpdate roomTypeId:" +outerId );
        String receiptOtherTypeDesc =  PropertiesUtil.readFile("/data.properties", "tb.receiptOtherTypeDesc");
        String guide =  PropertiesUtil.readFile("/data.properties", "tb.guide");
        StringBuilder builder = new StringBuilder();
        builder.append( andArea!=null?andArea.getCityName():"").append(" ").append(otaInnOta!=null?otaInnOta.getAliasInnName():"").append(" ").append(roomTypeInfo.getRoomTypeName());
        log.debug("roomUpdate title:"+builder.toString());
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setOutRid(String.valueOf(outerId));
        req.setTitle(builder.toString());
        req.setDesc(roomTypeInfo.getRoomInfo());
        //购买须知
        req.setGuide(guide);
        //提供发票
        req.setHasReceipt(true);
        req.setReceiptType("B");
        req.setReceiptOtherTypeDesc(receiptOtherTypeDesc);
        req.setReceiptInfo(receiptOtherTypeDesc);
        //房型图片
        if (!CollectionUtils.isEmpty(roomTypeInfo.getImgList())){
            OmsImg omsImg = roomTypeInfo.getImgList().get(0);
            String imgUrl = CommonApi.IMG_URL.concat(omsImg.getImgUrl());
            String imgName = StringUtils.substring(omsImg.getImgUrl(), omsImg.getImgUrl().lastIndexOf("/"));
            byte[] bytes = null;
          /*  log.info("图片地址:" +imgUrl +" imgName:"+imgName);*/
            //bytes = HttpClientUtil.readImg(imgUrl);
            bytes = ImgUtil.compressionImg(imgUrl);
            req.setPic(new FileItem(imgName,bytes,"image/jpeg"));
        }
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<Inventory> list = new ArrayList<Inventory>();
            List<RoomSwitchCal> roomSwitchCals = new ArrayList<RoomSwitchCal>();
            Inventory inventory =null;
            RoomSwitchCal roomSwitchCal=null;
            for (RoomDetail r:roomTypeInfo.getRoomDetail()){
                inventory = new Inventory();
                roomSwitchCal = new RoomSwitchCal(r.getRoomDate());
                roomSwitchCal.setRoomSwitchCalStatus(status);
                inventory.setDate(r.getRoomDate());
                inventory.setQuota(r.getRoomNum() == null ? 0 : r.getRoomNum());
                inventory.setRoomSwitchCalStatus(status);
                list.add(inventory);
                roomSwitchCals.add(roomSwitchCal);
            }
            String json = JacksonUtil.obj2json(list);
            String roomSwitchJson = JacksonUtil.obj2json(roomSwitchCals);
            req.setInventory(json);
            //开关状态 1 上架  2 下架  3 删除
            req.setRoomSwitchCal(roomSwitchJson);
          /*  log.info("roomSwitchJson:" + roomSwitchJson);
            log.info("Inventory:" +json);*/
        }
        try{
            XhotelRoomUpdateResponse response = client.execute(req , company.getSessionKey());
            /*log.info("roomUpdate:" + response.getGid());
            log.info("roomUpdate bady:" + response.getBody());*/
            if (!StringUtils.isEmpty(response.getSubCode())) {
                XRoom xRoom = roomGet(outerId, company);
                if (xRoom!=null){
                    return  xRoom.getGid();
                }else {
                    return null;
                }
            }
            return response.getGid();
        }catch (ApiException e){
            log.error(e.getMessage());
        }
        return null;

    }
    /**
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public  static Long  roomAddOrUpdate(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfoRefDto company,RoomSwitchCalStatus status,OtaInnOtaDto otaInnOta,OtaTaoBaoArea andArea) throws Exception {
        XRoom xRoom = roomGet(outerId, company);
        if (xRoom!=null){
            String receiptOtherTypeDesc =  PropertiesUtil.readFile("/data.properties", "tb.receiptOtherTypeDesc");
            String guide =  PropertiesUtil.readFile("/data.properties", "tb.guide");
            StringBuilder builder = new StringBuilder();
            builder.append( andArea!=null?andArea.getCityName():"").append(" ").append(otaInnOta!=null?otaInnOta.getAliasInnName():"").append(" ").append(roomTypeInfo.getRoomTypeName());
            log.debug("roomUpdate title:"+builder.toString());

            xRoom.setTitle(builder.toString());
            xRoom.setDesc(roomTypeInfo.getRoomInfo());
            //购买须知
            xRoom.setGuide(guide);
            //提供发票
            xRoom.setHasReceipt(true);
            xRoom.setReceiptType("B");
            xRoom.setReceiptOtherTypeDesc(receiptOtherTypeDesc);
            xRoom.setReceiptInfo(receiptOtherTypeDesc);

            //库存
            if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
                List<Inventory> list = new ArrayList<Inventory>();
                List<RoomSwitchCal> roomSwitchCals = new ArrayList<RoomSwitchCal>();
                Inventory inventory =null;
                RoomSwitchCal roomSwitchCal=null;
                for (RoomDetail r:roomTypeInfo.getRoomDetail()){
                    inventory = new Inventory();
                    roomSwitchCal = new RoomSwitchCal(r.getRoomDate());
                    roomSwitchCal.setRoomSwitchCalStatus(status);
                    inventory.setDate(r.getRoomDate());
                    inventory.setQuota(r.getRoomNum() == null ? 0 : r.getRoomNum());
                    inventory.setRoomSwitchCalStatus(status);
                    list.add(inventory);
                    roomSwitchCals.add(roomSwitchCal);
                }
                String json = JacksonUtil.obj2json(list);
                xRoom.setInventory(json);
              return   roomUpdate(company,xRoom);
            }
        }else {
          return    roomUpdate( outerId, roomTypeInfo, company, status, otaInnOta, andArea);
        }
        return  null;
    }

    /**
     * 添加酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param company
     */
    public static Long ratePlanAdd(OtaInfoRefDto company,RoomTypeInfo r) throws Exception {
        String ratePlanAdd = PropertiesUtil.readFile("/data.properties", "tb.ratePlanAdd");
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        req.setName(ratePlanAdd);
        //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
        req.setPaymentType(1L);
        //0：不含早1：含单早2：含双早N：含N早（1-99可选）
        req.setBreakfastCount(0L);
        //2.表示不能退{"cancelPolicyType":2}
        req.setCancelPolicy("{\"cancelPolicyType\":2}");
        req.setStatus(1L);
        XhotelRateplanAddResponse response = client.execute(req , company.getSessionKey());
        log.info("ratePlanAdd:"+response.getBody());
        return response.getRpid();
    }

    public static Long ratePlanAddOrUpdate(OtaInfoRefDto otaInfoRefDto,RoomTypeInfo r) throws Exception{
        RatePlan ratePlan = getRatePlan(otaInfoRefDto,r);
        if (ratePlan==null){
            return  ratePlanAdd(otaInfoRefDto,r);
        }else {
            return ratePlanUpdate(otaInfoRefDto,r);
        }
    }
    /**
     * 获取酒店rp
     * @param company
     */
    public static RatePlan getRatePlan(OtaInfoRefDto company,RoomTypeInfo r) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanGetRequest req=new XhotelRateplanGetRequest();
        req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        XhotelRateplanGetResponse response = client.execute(req , company.getSessionKey());
        log.info("getRatePlan: " +response.getRateplan());
        return response.getRateplan();
    }

    /**
     * 更新酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param company
     */
    public static Long ratePlanUpdate(OtaInfoRefDto company,RoomTypeInfo r) throws Exception {
        String ratePlanAdd = PropertiesUtil.readFile("/data.properties", "tb.ratePlanAdd");
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanUpdateRequest req=new XhotelRateplanUpdateRequest();
        req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        req.setName(ratePlanAdd);
        //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
        req.setPaymentType(1L);
        //0：不含早1：含单早2：含双早N：含N早（1-99可选）
        req.setBreakfastCount(0L);
        req.setCancelPolicy("{\"cancelPolicyType\":2}");
        req.setStatus(1L);
        try{
            XhotelRateplanUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("ratePlanUpdate:" + response.getRpid());
            return response.getRpid();
        }catch (ApiException e){
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     * 添加商品的库存
     * @param company 公司信息
     * @param gid 商品id
     * @param rpid 商品计划id
     * @param roomTypeInfo 房型信息
     * @param priceModelDto 价格策略
     * @param sj 库存日历开关， true 关闭； false 打开
     */
    public static  String  rateAddOrUpdate(OtaInfoRefDto company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo,
                                           OtaPriceModelDto priceModelDto,boolean sj ,OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission)   {

        Rate rateGet = rateGet(company, roomTypeInfo);
        if (rateGet==null) {
            TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
            XhotelRateAddRequest req = new XhotelRateAddRequest();
            req.setGid(gid);
            req.setRpid(rpid);
            req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
            req.setRateplanCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
            if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())) {
                List<InventoryRate> list = new ArrayList<InventoryRate>();
                InventoryPrice inventory = new InventoryPrice();
                InventoryRate rate = null;
                double price = 0;
                for (RoomDetail r : roomTypeInfo.getRoomDetail()) {
                    rate = new InventoryRate();
                    rate.setDate(r.getRoomDate());
                    price = new BigDecimal(r.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                    //tp店价格为分，我们自己系统价格是元
                    rate.setPrice(price * Constants.tpPriceUnit);
                    list.add(rate);
                }
                inventory.setInventory_price(list);
                String json = JacksonUtil.obj2json(inventory);
                req.setInventoryPrice(json);
            }
            XhotelRateAddResponse response = null;
            try {
                response = client.execute(req, company.getSessionKey());
                log.info("rateAddOrUpdate:" + response.getGidAndRpid());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return response.getGidAndRpid();
        }else {
            return rateUpdate(company, gid, rpid, roomTypeInfo, priceModelDto, sj, priceDto,commission);
        }

    }
    /**
     * 更新库存/ 共享room 库存 ， 不用再rate更新库存
     * @param company
     * @param gid
     * @param rpid
     * @param roomTypeInfo
     * @param priceModelDto
     * @param deleted 库存日历开关， true 关闭； false 打开
     * @return
     */
    public static String rateUpdate(OtaInfoRefDto company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo,
                                    OtaPriceModelDto priceModelDto,boolean deleted,OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission)   {
        log.info("rateUpdate gid:" + gid +" rateUpdate rpid:"+rpid);
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setGid(gid);
        req.setRpid(rpid);
        req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setRateplanCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            InventoryPrice inventory = new InventoryPrice();
            List<InventoryRate> rateList = TomsUtil.inventory(roomTypeInfo.getRoomDetail(), priceDto, priceModelDto, commission);
            inventory.setInventory_price(rateList);
            String json = JacksonUtil.obj2json(inventory);
            req.setInventoryPrice(json);
        }
        try {
            XhotelRateUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("rateUpdate:" +  response.getGidAndRpid());
            return  response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }
    /**
     * 获取库存
     * @param company
     * @param roomTypeInfo
     * @return
     */
    public static Rate rateGet(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo){
        return rateGet(company,roomTypeInfo.getRoomTypeId());
    }

    /**
     * 获取库存
     * @param company
     * @param roomTypeId 房型id
     * @return
     */
    public static Rate rateGet(OtaInfoRefDto company,Integer roomTypeId){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateGetRequest req=new XhotelRateGetRequest();
        req.setOutRid(String.valueOf(roomTypeId));
        req.setRateplanCode(String.valueOf(roomTypeId));
        try {
            XhotelRateGetResponse response = client.execute(req , company.getSessionKey());
            log.info("rateGet:" +  response.getRate());
            return  response.getRate();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    public static String rateUpdate(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo,Rate rate)   {
        return rateUpdate(company,roomTypeInfo.getRoomTypeId(),rate);
    }

    public static String rateUpdate(OtaInfoRefDto company,Integer roomTypeId,Rate rate)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setGid(rate.getGid());
        req.setRpid(rate.getRpid());
        req.setOutRid(String.valueOf(roomTypeId));
        req.setRateplanCode(String.valueOf(roomTypeId));
        req.setInventoryPrice(rate.getInventoryPrice());
        try {
            XhotelRateUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("推送 rateUpdate:" +  response.getGidAndRpid());
            return response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    /**
     * 修改订单状态
     *
     * @param order
     * @param company
     */
    public static String orderUpdate(Order order, OtaInfoRefDto company, long optType)   {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderUpdateRequest req = new XhotelOrderUpdateRequest();
        req.setTid(Long.parseLong(order.getChannelOrderCode()));
        req.setOptType(optType);
        try {
            XhotelOrderUpdateResponse  response = client.execute(req, company.getSessionKey());
            return response.getResult();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    public static void updateHotelPushRoom(OtaInfoRefDto o, PushRoom pushRoom,OtaPriceModelDto priceModel,  OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission) throws ApiException {
        log.info("---updateHotelPushRoom start---");
        XRoom xRoom = roomGet(pushRoom.getRoomType().getRoomTypeId(), o);
        Rate rate = rateGet(o, pushRoom.getRoomType());
        if (xRoom!=null && rate!=null ){
            String inventory = xRoom.getInventory();
            String inventoryPrice = rate.getInventoryPrice();
            List<Inventory> list = JacksonUtil.json2list(inventory, Inventory.class);
            InventoryPrice inventoryPrices = JacksonUtil.json2obj(inventoryPrice, InventoryPrice.class);
            List<InventoryRate> inventoryRateList = inventoryPrices.getInventory_price();
            List<RoomDetail> roomDetails = pushRoom.getRoomDetails();
            double price = 0;
            Double value = null;
            Date startDate = null;
            Date endDate = null;
            if (priceDto!=null) {
                value = priceDto.getValue() * Constants.tpPriceUnit;
                startDate = priceDto.getStartDate();
                endDate = priceDto.getEndDate();
            }
            for (RoomDetail roomDetail:roomDetails){
                String roomDate = roomDetail.getRoomDate();
                Integer roomNum = roomDetail.getRoomNum();
                Double  roomPrice = roomDetail.getRoomPrice();
                for (InventoryRate ratePrice:inventoryRateList){
                    if (ratePrice.getDate().equals(roomDate)){
                        price = new BigDecimal(roomPrice).multiply(priceModel.getPriceModelValue()).doubleValue();
                        //售价只有MAI2DI才展示
                        if (commission!=null && commission.getsJiaModel().equals(UsedPriceModel.MAI2DI.name())){
                            price = TomsUtil.price(price,new BigDecimal(commission.getCommissionPercent()));
                        }
                        price = price*Constants.tpPriceUnit;
                        //tp店价格为分，我们自己系统价格是元
                        Date parseDate = DateUtil.parseDate(roomDate);
                        //在设定的范围内才对价格进行处理
                        if (priceDto!=null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
                            price = price + value;
                        }
                        ratePrice.setPrice(price);
                    }
                }
                for (Inventory in:list){
                    if (in.getDate().equals(roomDate)){
                        in.setQuota(roomNum);
                    }
                }
            }
            String json = JacksonUtil.obj2json(list);
            inventoryPrices.setInventory_price(inventoryRateList);
            String inventoryRate = JacksonUtil.obj2json(inventoryPrices);
            xRoom.setInventory(json);
            rate.setInventoryPrice(inventoryRate);
            log.info("价格 json:" + inventoryRate);
            log.info("库存 json:" + json);
            roomUpdate(o, xRoom);
            rateUpdate(o,pushRoom.getRoomType(),rate);
        }
    }

    /**
     * 及时更新room
     */
    public  static Long  roomUpdate(OtaInfoRefDto company, XRoom xRoom)  {
        log.info("oms 及时更新房型库存 xRoom.getGid():" + xRoom.getGid());
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setGid(xRoom.getGid());
        req.setInventory(xRoom.getInventory());
        try {
            XhotelRoomUpdateResponse response = client.execute(req , company.getSessionKey());
           /* log.info("roomUpdate:" + response.getGid());
            log.info("roomUpdate bady:" + response.getBody());*/
            return response.getGid();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过去啊获取酒店信息
     * @param infoRefDto 渠道信息
     */
    public static void vettedOtaAppKey(OtaInfoRefDto infoRefDto,String innId)throws Exception {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelGetRequest req=new XhotelGetRequest();
        req.setOuterId(innId);
        XhotelGetResponse response = client.execute(req , infoRefDto.getSessionKey());
        if (!TomsConstants.HOTEL_NOT_EXIST.equals(response.getSubCode())){
            throw new VettedOTAException(response.getMsg());
        }
    }

    /**
     * 把集合里的roomTypeId房量设置为零
     * @param list 要更新房量为0的集合
     * @refDto 渠道信息
     */
    public static void roomRoomNumZeroUpdate(List<OtaInnRoomTypeGoodsDto> list, OtaInfoRefDto refDto) throws ApiException {

        if (!CollectionUtils.isEmpty(list)){
            for (OtaInnRoomTypeGoodsDto dto:list){
                XRoom xRoom = roomGet(dto.getRoomTypeId(), refDto);
                if (xRoom!=null){
                    String inventory = xRoom.getInventory();
                    List<Inventory> inventoryList = JacksonUtil.json2list(inventory, Inventory.class);
                    for (Inventory in:inventoryList){
                        in.setQuota(0);
                    }
                    String json = JacksonUtil.obj2json(inventoryList);
                    xRoom.setInventory(json);
                    log.info("库存设置为0："+json);
                    roomUpdate(refDto,xRoom);
                }
            }
        }
    }

    /**
     * 查询订单状态
     *
     * @return
     */
    public static String searchOrderStatus(Order order, OtaInfoRefDto company) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderSearchRequest req = new XhotelOrderSearchRequest();
        req.setOrderTids(order.getChannelOrderCode());
        req.setCreatedEnd(order.getLastestArriveTime());
        req.setCreatedStart(order.getEariestArriveTime());
        XhotelOrderSearchResponse rsp = client.execute(req, company.getSessionKey());
        return rsp.getBody();
    }
}
