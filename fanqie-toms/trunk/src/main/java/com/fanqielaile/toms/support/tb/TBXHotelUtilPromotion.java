package com.fanqielaile.toms.support.tb;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.util.DateUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.PropertiesUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.TBType;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.exception.VettedOTAException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TPServiceUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.RatePlan;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * DESC : 淘宝对接API（升级版本）
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public class TBXHotelUtilPromotion {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtilPromotion.class);
    private TBXHotelUtilPromotion(){}


    /**
     * 酒店更新 XhotelUpdateRequest 封装
     * @param innDto 客栈信息
     */
    public static XhotelUpdateRequest xhotelUpdateRequest(InnDto innDto,OtaTaoBaoArea andArea){
        XhotelUpdateRequest req=new XhotelUpdateRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        req.setTel(innDto.getFrontPhone());
        req.setAddress(innDto.getAddr());
        req.setDescription(innDto.getInnInfo());
        req.setPics(TPServiceUtil.obtPics(innDto.getImgList()));
        //设施
        req.setHotelFacilities(TPServiceUtil.obtHotelFacilities(innDto.getFacilitiesMap()));
        if (andArea!=null){
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
        }
        return  req;
    }

    /**
     * 更新酒店信息
     * @param infoRefDto 渠道信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelUpdate(OtaInfoRefDto infoRefDto,InnDto innDto,OtaTaoBaoArea andArea)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelUpdateRequest req = xhotelUpdateRequest(innDto,andArea);
        if (StringUtils.isNotEmpty(infoRefDto.getVendorId())){
            req.setVendor(infoRefDto.getVendorId());
            log.info("hotelAdd companyCode"+infoRefDto.getCompanyCode()+"酒店request:"+JacksonUtil.obj2json(req));
        }
        try {
            XhotelUpdateResponse response = client.execute(req , infoRefDto.getSessionKey());
            log.info("hotelUpdate:" + response.getXhotel());
            return response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }


    /**
     * 往淘宝添加、更新 酒店
     * @param otaInfoRefDto 渠道信息，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelAddOrUpdate(OtaInfoRefDto otaInfoRefDto,InnDto innDto,OtaTaoBaoArea andArea)   {
        XHotel xHotel = hotelGet(otaInfoRefDto, innDto.getInnId());
        if(xHotel==null){
            TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, otaInfoRefDto.getAppKey(), otaInfoRefDto.getAppSecret());
            XhotelAddRequest req=new XhotelAddRequest();
            req.setOuterId(innDto.getInnId());
            req.setName(innDto.getBrandName());
            req.setUsedName(innDto.getInnName());
            req.setTel(innDto.getFrontPhone());
            req.setDescription(innDto.getInnInfo());
            req.setPics(TPServiceUtil.obtPics(innDto.getImgList()));
            if (StringUtils.isNotEmpty(otaInfoRefDto.getVendorId())){
                req.setVendor(otaInfoRefDto.getVendorId());
                log.info("taoBaoArea json:"+andArea!=null?JacksonUtil.obj2json(andArea):" 区域为null");
            }
            //客栈设施
            req.setHotelFacilities(TPServiceUtil.obtHotelFacilities(innDto.getFacilitiesMap()));
            if (andArea!=null){
                req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000);
                req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
                if (!StringUtils.isEmpty(andArea.getAreaCode())){
                    req.setDistrict(Long.valueOf(andArea.getAreaCode()));
                }
            }
            req.setAddress(innDto.getAddr());
            try {
                String obj2json = JacksonUtil.obj2json(req);
                log.info("hotelAdd companyCode"+otaInfoRefDto.getCompanyCode()+"酒店request:"+obj2json);
                XhotelAddResponse response = client.execute(req , otaInfoRefDto.getSessionKey());
                //存在
                if (TomsConstants.HOTEL_EXIST.equals(response.getSubCode())) {
                    return   hotelUpdate(otaInfoRefDto,innDto,andArea);
                }
                log.info("hotelAdd:" +response.getXhotel());
                return response.getXhotel();
            } catch (ApiException e) {
                log.error(e.getMessage());
            }
        }else {
            return   hotelUpdate(otaInfoRefDto,innDto,andArea);
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
        if (StringUtils.isNotEmpty(company.getVendorId())){
            req.setVendor(company.getVendorId());
        }
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
     * 添加房型
     * @param innId pms系统中的客栈id
     * @param roomTypeInfo 房型信息
     * @param company 渠道信息
     */
    public static XRoomType addRoomType(String innId,RoomTypeInfo roomTypeInfo,OtaInfoRefDto company)   {
        return  updateRoomType(company, roomTypeInfo,innId);
        /*TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setOutHid(innId);
        req.setName(roomTypeInfo.getRoomTypeName());
        //面积
        if (roomTypeInfo.getRoomArea()!=null){
            req.setArea(String.valueOf((int)Math.floor(roomTypeInfo.getRoomArea())));
        }
        //楼层
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        //床宽
        req.setBedSize(String.valueOf(roomTypeInfo.getBedWid()));
        req.setBedType(roomTypeInfo.getBedType());
        req.setInternet(TPServiceUtil.internet(roomTypeInfo.getFacilitiesMap()));
        //服务设施
        req.setService(TPServiceUtil.jsonService(roomTypeInfo.getFacilitiesMap()));
        //房型图片
        req.setPics(TPServiceUtil.obtPics(roomTypeInfo.getImgList()));
        try {
            XhotelRoomtypeAddResponse response = client.execute(req , company.getSessionKey());
          *//*  log.info("addRoomType:"+response.getMsg()+ " error code:"+response.getErrorCode());*//*
            if (TomsConstants.ROOM_TYPE_EXIST.equals(response.getSubCode())) {
                return  updateRoomType(company, roomTypeInfo,innId);
            }
            log.info("addRoomType:" + response.getXroomtype());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return null;*/
    }

    /**
     * 获取房型信息
     */
    public static XRoomType getRoomType(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeGetRequest req=new XhotelRoomtypeGetRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        if (StringUtils.isNotEmpty(company.getVendorId())){
            req.setVendor(company.getVendorId());
        }
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
     * 更新房型
     */
    public static XRoomType updateRoomType(OtaInfoRefDto otaInfoRefDto,RoomTypeInfo roomTypeInfo,String innId)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, otaInfoRefDto.getAppKey(), otaInfoRefDto.getAppSecret());
        XhotelRoomtypeUpdateRequest req=new XhotelRoomtypeUpdateRequest();
        req.setHotelCode(innId);
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setName(roomTypeInfo.getRoomTypeName());
        //面积
        if (roomTypeInfo.getRoomArea()!=null){
            req.setArea(String.valueOf((int) Math.floor(roomTypeInfo.getRoomArea())));
        }
        if (StringUtils.isNotEmpty(otaInfoRefDto.getVendorId())){
            req.setVendor(otaInfoRefDto.getVendorId());
        }
        //楼层
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        //床宽
        req.setBedSize(String.valueOf(roomTypeInfo.getBedWid()));
        req.setBedType(roomTypeInfo.getBedType());
        req.setInternet(TPServiceUtil.internet(roomTypeInfo.getFacilitiesMap()));
        //服务设施
        req.setService(TPServiceUtil.jsonService(roomTypeInfo.getFacilitiesMap()));
        //房型图片
        req.setPics(TPServiceUtil.obtPics(roomTypeInfo.getImgList()));
        try {
            XhotelRoomtypeUpdateResponse response = client.execute(req ,  otaInfoRefDto.getSessionKey());
            if (!StringUtils.isEmpty(response.getSubCode())) {
                return getRoomType(otaInfoRefDto,roomTypeInfo);
            }
            log.info("updateRoomType:" + response.getXroomtype());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }
    /**
     * 添加商品
     * @param roomTypeInfo 房型信息
     * @param otaInfoRefDto 渠道信息
     */
    public  static Long  roomUpdate(RoomTypeInfo roomTypeInfo,OtaInfoRefDto otaInfoRefDto,  RoomSwitchCalStatus status) throws Exception {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, otaInfoRefDto.getAppKey(), otaInfoRefDto.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setOutRid(roomTypeInfo.getRoomTypeId().toString());
        if (StringUtils.isNotEmpty(otaInfoRefDto.getVendorId())){
            req.setVendor(otaInfoRefDto.getVendorId());
        }
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            /*List<Inventory> list = new ArrayList<Inventory>();*/
            List<RoomSwitchCal> roomSwitchCals = new ArrayList<RoomSwitchCal>();
            //Inventory inventory =null;
            RoomSwitchCal roomSwitchCal=null;
            for (RoomDetail r:roomTypeInfo.getRoomDetail()){
                //inventory = new Inventory();
                roomSwitchCal = new RoomSwitchCal(r.getRoomDate());
                roomSwitchCal.setRoomSwitchCalStatus(status);
                /*inventory.setDate(r.getRoomDate());
                inventory.setQuota(r.getRoomNum() == null ? 0 : r.getRoomNum());
                inventory.setRoomSwitchCalStatus(status);
                list.add(inventory);*/
                roomSwitchCals.add(roomSwitchCal);
            }
           /* String json = JacksonUtil.obj2json(list);*/
           /* req.setInventory(json);*/
            String roomSwitchJson = JacksonUtil.obj2json(roomSwitchCals);
            //开关状态 1 上架  2 下架  3 删除
            req.setRoomSwitchCal(roomSwitchJson);
            /*log.info("库存json:" + json);*/
            log.info("房态json:"+roomSwitchJson);
        }
        XhotelRoomUpdateResponse response = client.execute(req , otaInfoRefDto.getSessionKey());
        log.info("===========roomUpdate Gid:"+response.getGid());
        return response.getGid();
    }


    /**
     * 添加酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param infoRefDto
     */
    public static Long ratePlanAdd(OtaInfoRefDto infoRefDto,RoomTypeInfo r) throws Exception {
        String ratePlanAdd = PropertiesUtil.readFile("/data.properties", "tb.ratePlanAdd");
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        if (StringUtils.isNotEmpty(infoRefDto.getVendorId())){
            req.setVendor(infoRefDto.getVendorId());
        }
        if (TBType.CREDIT.equals(infoRefDto.getTbType())){
            req.setRateplanCode(r.getRatePlanCode());
            req.setName(ratePlanAdd);
            //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
            req.setPaymentType(6l);
            //0：不含早1：含单早2：含双早N：含N早（1-99可选）
            req.setBreakfastCount(Long.valueOf(r.getRatePlanConfig().getBreakfastCount()));
            req.setCancelPolicy(JacksonUtil.obj2json(r.getRatePlanConfig().getCancelPolicy()));
        }else {
            req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
            req.setName(ratePlanAdd);
            //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
            req.setPaymentType(1L);
            //0：不含早1：含单早2：含双早N：含N早（1-99可选）
            req.setBreakfastCount(0L);
            //2.表示不能退{"cancelPolicyType":2}
            req.setCancelPolicy("{\"cancelPolicyType\":2}");
        }
        req.setStatus(1L);
        XhotelRateplanAddResponse response = client.execute(req , infoRefDto.getSessionKey());
        log.info("ratePlanAdd:"+response.getBody());
        return response.getRpid();
    }

    /**
     * 价格计划 添加或更新
     * @param otaInfoRefDto 渠道信息
     * @param roomTypeInfo 房型信息
     */
    public static Long ratePlanAddOrUpdate(OtaInfoRefDto otaInfoRefDto,RoomTypeInfo roomTypeInfo) throws Exception{
        return ratePlanUpdate(otaInfoRefDto,roomTypeInfo);
        /*RatePlan ratePlan = getRatePlan(otaInfoRefDto,roomTypeInfo);
        if (ratePlan==null){
            return  ratePlanAdd(otaInfoRefDto,roomTypeInfo);
        }else {
            return ratePlanUpdate(otaInfoRefDto,roomTypeInfo);
        }*/
    }

    /**
     * 更新酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param infoRefDto
     */
    public static Long ratePlanUpdate(OtaInfoRefDto infoRefDto,RoomTypeInfo r) throws Exception {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelRateplanUpdateRequest req=new XhotelRateplanUpdateRequest();
        if (StringUtils.isNotEmpty(infoRefDto.getVendorId())){
            req.setVendor(infoRefDto.getVendorId());
        }
        if (TBType.CREDIT.equals(infoRefDto.getTbType())){
            String ratePlanAdd = PropertiesUtil.readFile("/data.properties", "credit.tb.ratePlanName");
            req.setName(ratePlanAdd);
            req.setRateplanCode(r.getRatePlanCode());
            //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
            req.setPaymentType(6l);
            //0：不含早1：含单早2：含双早N：含N早（1-99可选）
            TomsUtil.obtRatePlanRequest(r.getRatePlanConfig(), req);
            /*req.setBreakfastCount(Long.valueOf(r.getRatePlanConfig().getBreakfastCount()));
            req.setCancelPolicy(JacksonUtil.obj2json(r.getRatePlanConfig().getCancelPolicy()));*/
        }else {
            String ratePlanAdd = PropertiesUtil.readFile("/data.properties", "tb.ratePlanAdd");
            req.setName(ratePlanAdd);
            req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
            //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
            req.setPaymentType(1L);
            //0：不含早1：含单早2：含双早N：含N早（1-99可选）
            req.setBreakfastCount(0L);
            req.setCancelPolicy("{\"cancelPolicyType\":2}");
        }
        req.setStatus(1L);
        try{
            XhotelRateplanUpdateResponse response = client.execute(req , infoRefDto.getSessionKey());
            log.info("ratePlanUpdate:" + response.getRpid());
            return response.getRpid();
        }catch (ApiException e){
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     * 获取酒店rp
     * @param otaInfoRefDto 渠道信息
     */
    public static RatePlan getRatePlan(OtaInfoRefDto otaInfoRefDto,RoomTypeInfo r) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, otaInfoRefDto.getAppKey(), otaInfoRefDto.getAppSecret());
        XhotelRateplanGetRequest req=new XhotelRateplanGetRequest();
        if (TBType.CREDIT.equals(otaInfoRefDto.getTbType())){
            req.setRateplanCode(r.getRatePlanCode());
        }else {
            req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        }
        XhotelRateplanGetResponse response = client.execute(req , otaInfoRefDto.getSessionKey());
        log.info("getRatePlan: " +response.getRateplan());
        return response.getRateplan();
    }



    /**
     * 添加商品的库存（价格）
     * @param company 公司信息
     * @param roomTypeInfo 房型信息
     * @param priceModelDto 价格策略
     */
    public static  String  rateAddOrUpdate(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo,
                                           OtaPriceModelDto priceModelDto,OtaRoomPriceDto priceDto,
                                           OtaCommissionPercentDto commission)   {
        // Rate rateGet = rateGet(company, roomTypeInfo);
       return rateUpdate(company, roomTypeInfo, priceModelDto, priceDto, commission);
    }
    /**
     * 更新库存/ 共享room 库存 ， 不用再rate更新库存
     * @param infoRefDto 渠道信息
     * @param roomTypeInfo 房型信息
     * @param priceModelDto 价格模式

     */
    public static String rateUpdate(OtaInfoRefDto infoRefDto,RoomTypeInfo roomTypeInfo,
                                    OtaPriceModelDto priceModelDto,OtaRoomPriceDto priceDto,
                                    OtaCommissionPercentDto commission)   {

        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
        if (StringUtils.isNotEmpty(infoRefDto.getVendorId())){
            req.setVendor(infoRefDto.getVendorId());
        }
        if (TBType.CREDIT.equals(infoRefDto.getTbType())){
            req.setRateplanCode(Constants.OMS_RATE_PLAN_CODE);
        }else {
            req.setRateplanCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
        }
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            InventoryPrice inventory = new InventoryPrice();
            List<InventoryRate> rateList = TomsUtil.inventory(roomTypeInfo.getRoomDetail(), priceDto, priceModelDto, commission);
            //todo 信用住关房
            inventory.setInventory_price(rateList);
            String json = JacksonUtil.obj2json(inventory);
            req.setInventoryPrice(json);
            log.info("价格和库存json:" + json);
        }
        try {
            log.info("rateUpdate request"+JacksonUtil.obj2json(req));
            XhotelRateUpdateResponse response = client.execute(req , infoRefDto.getSessionKey());
            log.info("rateUpdate response"+JacksonUtil.obj2json(response));
            return  response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    /*public static  void roomRateZero(OtaInfoRefDto otaInfoRefDto,List<InventoryRate> rateList){
        if (StringUtils.isNotEmpty(otaInfoRefDto.getVendorId())){
            String creditSwitchDate = PropertiesUtil.readFile("/data.properties", "credit.switch.room");
            if (StringUtils.isNotEmpty(creditSwitchDate)){
                String[] split = creditSwitchDate.split(",");
                List<String> dateList = Arrays.asList(split);
                for (InventoryRate inventoryRate:rateList){
                    boolean b = dateList.contains(inventoryRate.getDate());
                    if (b){
                        inventoryRate.setQuota(0);
                    }
                }

            }
        }
    }*/

    /**
     * 获取库存
     * @param company
     * @param roomTypeInfo
     * @return
     */
    /*public static Rate rateGet(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo){
        return rateGet(company,roomTypeInfo.getRoomTypeId());
    }*/

    /**
     * 获取库存
     * @return
     */
    /*public static Rate rateGet(OtaInfoRefDto infoRefDto,Integer roomTypeId){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelRateGetRequest req=new XhotelRateGetRequest();
        req.setOutRid(roomTypeId.toString());
        if (TBType.CREDIT.equals(infoRefDto.getTbType())){
            req.setRateplanCode(roomTypeId.toString());
        }else {
            req.setRateplanCode(roomTypeId.toString());
        }
        try {
            XhotelRateGetResponse response = client.execute(req , infoRefDto.getSessionKey());
            log.info("rateGet:" +  response.getRate());
            return  response.getRate();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }*/

   /* public static String rateUpdate(OtaInfoRefDto company,RoomTypeInfo roomTypeInfo,Rate rate)   {
        return rateUpdate(company,roomTypeInfo.getRoomTypeId(),rate);
    }*/

    /**
     * 更新库存价格
     * @param infoRefDto 渠道信息
     * @param roomTypeId 房型id
     * @param rate 库存对象
     */
    /*public static String rateUpdate(OtaInfoRefDto infoRefDto,Integer roomTypeId,Rate rate)   {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, infoRefDto.getAppKey(), infoRefDto.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setOutRid(String.valueOf(roomTypeId));
        req.setRateplanCode(String.valueOf(roomTypeId));
        req.setInventoryPrice(rate.getInventoryPrice());
        try {
            XhotelRateUpdateResponse response = client.execute(req , infoRefDto.getSessionKey());
            log.info("推送 rateUpdate:" +  response.getGidAndRpid());
            return response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        return  null;
    }*/

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

    /**
     * 全量更新 库存 和 价格
     * @param otaInfoRefDto 渠道信息
     * @param roomTypeInfo 房型信息  房型id + 房态集合
     * @param priceModel 价格模式
     * @param priceDto 加减价
     * @param commission 佣金比
     */
    public static boolean updateRoomRate(OtaInfoRefDto otaInfoRefDto,RoomTypeInfo roomTypeInfo,OtaPriceModelDto priceModel,
                                         OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission) throws Exception {
        //Long gId = roomUpdate(roomTypeInfo, otaInfoRefDto, RoomSwitchCalStatus.SJ);
        String gidAndRpId = rateUpdate(otaInfoRefDto,roomTypeInfo,priceModel,priceDto,commission);
        if (gidAndRpId==null){
            return  false;
        }
        return true;
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
    public static void roomRoomNumZeroUpdate(List<OtaInnRoomTypeGoodsDto> list, OtaInfoRefDto refDto) throws Exception {

        if (!CollectionUtils.isEmpty(list)){
            for (OtaInnRoomTypeGoodsDto dto:list){
                log.info("库存更新0,roomTypeId:"+dto.getRoomTypeId());
                List<RoomDetail> roomDetailList = TomsUtil.buildRoomDetail(dto.getRoomTypeId());
                RoomTypeInfo roomTypeInfo = TomsUtil.buildRoomTypeInfo(roomDetailList, dto.getRoomTypeId());
                roomUpdate(roomTypeInfo,refDto, RoomSwitchCalStatus.XJ);
                //updateRoomRate(refDto,roomTypeInfo,new OtaPriceModelDto(new BigDecimal(1)),null,null);
            }
        }
    }

    /**
     *  (价格推送接口（批量增量）)
     * @param otaInfoRefDto
     * @throws com.taobao.api.ApiException
     */
    public static void ratesUpdate(OtaInfoRefDto otaInfoRefDto,PushRoom pushRoom, OtaPriceModelDto priceModel,
                                     OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission) throws ApiException {
        InventoryPriceIncrementObj inventoryPriceIncrementObj = new InventoryPriceIncrementObj();
        String out_rid = pushRoom.getRoomType().getRoomTypeId().toString();
        inventoryPriceIncrementObj.setOut_rid(out_rid);
        if (StringUtils.isNotEmpty(otaInfoRefDto.getVendorId())){
            inventoryPriceIncrementObj.setVendor(otaInfoRefDto.getVendorId());
        }
        //todo
        if (TBType.CREDIT.equals(otaInfoRefDto.getTbType())){
            inventoryPriceIncrementObj.setRateplan_code(Constants.OMS_RATE_PLAN_CODE);
        }else {
            inventoryPriceIncrementObj.setRateplan_code(out_rid);
        }
        //库存对象
        List<InventoryRateIncrement> rateIncrementList = new ArrayList<InventoryRateIncrement>();
        InventoryRateIncrement inventoryRateIncrement =new InventoryRateIncrement(out_rid);
        rateIncrementList.add(inventoryRateIncrement);
        List<RoomDetail> roomDetails = pushRoom.getRoomDetails();
        int size = roomDetails.size();
        if (size<= Constants.tp_count){
            ratesRoom(roomDetails,priceModel,otaInfoRefDto,commission,priceDto,inventoryPriceIncrementObj/*,rateIncrementList,inventoryRateIncrement*/);
        }else {
            RoomTypeInfo roomTypeInfo = TomsUtil.buildRoomTypeInfo(roomDetails, Integer.valueOf(out_rid));
            try {
                updateRoomRate(otaInfoRefDto,roomTypeInfo,priceModel,priceDto,commission);
            } catch (Exception e) {
                log.error("价格、库存推送接口异常",e);
            }
        }
    }

    public static void ratesRoom(List<RoomDetail> roomDetails,OtaPriceModelDto priceModel,OtaInfoRefDto otaInfoRefDto,
                           OtaCommissionPercentDto commission,OtaRoomPriceDto priceDto,InventoryPriceIncrementObj inventoryPriceIncrementObj
                           /*List<InventoryRateIncrement> rateIncrementList,InventoryRateIncrement inventoryRateIncrement*/)throws ApiException{
        if (!CollectionUtils.isEmpty(roomDetails)){
            //价格对象
            InventoryPrice inventoryPrice = new InventoryPrice();
            InventoryPriceIncrement ratePrice = null;
            List<InventoryPriceIncrement> ratePriceList = new ArrayList<InventoryPriceIncrement>();
           /* Inventory inventory = null;*/
            /*List<Inventory>  inventoryList = new ArrayList<Inventory>();*/
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
               /* inventory = new Inventory(roomDate,roomNum);*/
                price = new BigDecimal(roomPrice).multiply(priceModel.getPriceModelValue()).doubleValue();
                //售价只有MAI2DI才展示
                if (commission!=null && commission.getsJiaModel().equals(UsedPriceModel.MAI2DI.name())){
                    price = TomsUtil.price(price, new BigDecimal(commission.getCommissionPercent()));
                }
                price = price* Constants.tpPriceUnit;
                //tp店价格为分，我们自己系统价格是元
                Date parseDate = DateUtil.parseDate(roomDate);
                //在设定的范围内才对价格进行处理
                if (priceDto!=null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
                    price = price + value;
                }
                ratePrice = new InventoryPriceIncrement(price,1);
                ratePrice.setDate(roomDate);
                ratePrice.setQuota(roomNum);
                ratePriceList.add(ratePrice);
               /* inventoryList.add(inventory);*/
            }
            inventoryPrice.setInventory_price(ratePriceList);
            inventoryPriceIncrementObj.setData(inventoryPrice);
            String obj2json = JacksonUtil.obj2json(inventoryPriceIncrementObj);
            log.info("================================及时推送价格==============================");
            log.info("["+obj2json+"]");

            TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, otaInfoRefDto.getAppKey(), otaInfoRefDto.getAppSecret());
            XhotelRatesIncrementRequest req = new XhotelRatesIncrementRequest();
            req.setRateInventoryPriceMap("[" + obj2json + "]");
            log.info("rate.update request"+JacksonUtil.obj2json(req));
            XhotelRatesIncrementResponse rsp = client.execute(req, otaInfoRefDto.getSessionKey());
            log.info("rate.update response"+JacksonUtil.obj2json(rsp));
            if (CollectionUtils.isEmpty(rsp.getGidAndRpids()) ){
                throw  new TomsRuntimeException(rsp.getMsg());
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

    /**
     * 更新酒店订单状态
     *
     * @return
     */
    public static String updateHotelOrderStatus(HotelOrderStatus hotelOrderStatus, OtaInfoRefDto company) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderAlipayfaceUpdateRequest req = new XhotelOrderAlipayfaceUpdateRequest();
        req.setTid(Long.valueOf(hotelOrderStatus.getTid()));
        req.setOptType(Long.valueOf(hotelOrderStatus.getOptType()));
        if (StringUtils.isNotEmpty(hotelOrderStatus.getReasonType())) {
            req.setReasonType(Long.valueOf(hotelOrderStatus.getReasonType()));
        } else {
            req.setReasonType(null);
        }
        req.setReasonText(hotelOrderStatus.getReasonText());
        req.setOutRoomNumber(hotelOrderStatus.getOutRoomNumber());
        req.setCheckinDate(hotelOrderStatus.getCheckinDate());
        req.setCheckoutDate(hotelOrderStatus.getCheckoutDate());
        if (StringUtils.isNotEmpty(hotelOrderStatus.getRooms() + "")) {
            req.setRooms(Long.valueOf(hotelOrderStatus.getRooms()));
        } else {
            req.setRooms(null);
        }
        req.setOutId(hotelOrderStatus.getOutId());
        XhotelOrderAlipayfaceUpdateResponse rsp = client.execute(req, company.getSessionKey());
        return rsp.getBody();
    }

    /**
     * 淘宝信用住结账接口
     *
     * @param hotelOrderPay
     * @param company
     * @return
     */
    public static String updateOrderPay(HotelOrderPay hotelOrderPay, OtaInfoRefDto company) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderAlipayfaceSettleRequest req = new XhotelOrderAlipayfaceSettleRequest();
        req.setMemo(hotelOrderPay.getMemo());
        req.setOutId(hotelOrderPay.getOutId());
        req.setRoomNo(hotelOrderPay.getRoomNo());
        req.setOtherFee(hotelOrderPay.getOtherFee().longValue());
        req.setTotalRoomFee(hotelOrderPay.getTotalRoomFee().longValue());
        req.setDailyPriceInfo(hotelOrderPay.getDailyPriceInfo());
        req.setCheckOut(DateUtil.parse(hotelOrderPay.getCheckOut(), "yyyy-MM-dd HH:mm:ss"));
        req.setTid(Long.valueOf(hotelOrderPay.getTid()));
        req.setOtherFeeDetail(hotelOrderPay.getOtherFeeDetail());
        List<XhotelOrderAlipayfaceSettleRequest.RoomSettleInfo> listRoomSettleInfo = new ArrayList<>();
        if (null != hotelOrderPay.getRoomSettleInfoList() && ArrayUtils.isNotEmpty(hotelOrderPay.getRoomSettleInfoList().toArray())) {
            for (RoomSettleInfo roomSettleInfo : hotelOrderPay.getRoomSettleInfoList()) {
                XhotelOrderAlipayfaceSettleRequest.RoomSettleInfo objRoomSettleInfo = new XhotelOrderAlipayfaceSettleRequest.RoomSettleInfo();
                objRoomSettleInfo.setRoomNo(roomSettleInfo.getRoomNo());
                objRoomSettleInfo.setRoomFee(roomSettleInfo.getRoomFee().longValue());
                objRoomSettleInfo.setRoomOtherFee(roomSettleInfo.getRoomOtherFee().longValue());
                objRoomSettleInfo.setRoomOtherFeeDetail(roomSettleInfo.getRoomOtherFeeDetail());
                objRoomSettleInfo.setRoomCheckOut(roomSettleInfo.getRoomCheckOut());
                objRoomSettleInfo.setRoomCheckIn(roomSettleInfo.getRoomCheckIn());
                objRoomSettleInfo.setDailyPriceInfo(roomSettleInfo.getDailyPriceInfo());
                objRoomSettleInfo.setRoomStatus(String.valueOf(roomSettleInfo.getRoomStatus()));
                listRoomSettleInfo.add(objRoomSettleInfo);
            }
        }
        req.setRoomSettleInfoList(listRoomSettleInfo);
        req.setContainGuarantee(Long.valueOf(hotelOrderPay.getContainGuarantee()));
        XhotelOrderAlipayfaceSettleResponse rsp = client.execute(req, company.getSessionKey());
        return rsp.getBody();
    }
}
