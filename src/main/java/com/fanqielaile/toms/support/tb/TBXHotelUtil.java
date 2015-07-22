package com.fanqielaile.toms.support.tb;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.util.Constants;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.PropertiesUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.util.ImgUtil;
import com.fanqielaile.toms.support.util.TPServiceUtil;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    /**
     * 想淘宝添加酒店
     * @param company 渠道信息，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     * @SEE          hotelAddOrUpdate(...)
     */
    @Deprecated
    public static XHotel hotelAdd(OtaInfo company,InnDto innDto,OtaTaoBaoArea andArea){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelAddRequest req=new XhotelAddRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        if (andArea!=null){
            req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000);
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
        }
        req.setAddress(innDto.getAddr());
        try {
            XhotelAddResponse response = client.execute(req , company.getSessionKey());
            log.info("hotelAdd:" +response.getXhotel());
            return response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }

    //更新酒店
    public static XHotel hotelUpdate(OtaInfo company,InnDto innDto,OtaTaoBaoArea andArea) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelUpdateRequest req=new XhotelUpdateRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        req.setTel(innDto.getFrontPhone());
        if (andArea!=null){
            req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000);
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
        }
        req.setAddress(innDto.getAddr());
        XhotelUpdateResponse response = client.execute(req , company.getSessionKey());
        if (!StringUtils.isEmpty(response.getSubCode())) {
            return hotelGet(company,innDto.getInnId());
        }
        log.info("hotelUpdate:" + response.getXhotel());
        return response.getXhotel();
    }
    /**
     * 往淘宝添加、更新 酒店
     * @param company 渠道信息，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelAddOrUpdate(OtaInfo company,InnDto innDto,OtaTaoBaoArea andArea) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelAddRequest req=new XhotelAddRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        req.setTel(innDto.getFrontPhone());
        if (andArea!=null){
            req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000);
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100);
        }
        req.setAddress(innDto.getAddr());
        XhotelAddResponse response = client.execute(req , company.getSessionKey());
        //存在
        if (Constants.HOTEL_EXIST.equals(response.getSubCode())) {
            return   hotelUpdate(company,innDto,andArea);
        }
        log.info("hotelAdd:" +response.getXhotel());
        return response.getXhotel();
    }

    /**
     * 获取酒店
     * @param company
     * @return
     */

    public static XHotel hotelGet(OtaInfo company,String innId) {
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
     *添加房型
     * @param innId pms系统中的客栈id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 渠道信息
     */
    public static XRoomType addRoomType(String innId,Long hid,RoomTypeInfo roomTypeInfo,OtaInfo company) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setOutHid(innId);
        req.setHid(hid);
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
            XhotelRoomtypeAddResponse response = client.execute(req , company.getSessionKey());
            if (Constants.ROOM_TYPE_EXIST.equals(response.getSubCode())) {
                return  updateRoomType(company, roomTypeInfo);
            }
            log.info("addRoomType:" + response.getXroomtype());
            return response.getXroomtype();
    }

    /**
     * 获取房型信息
     */
    public static XRoomType getRoomType(OtaInfo company,RoomTypeInfo roomTypeInfo){
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
    public static XRoomType updateRoomType(OtaInfo company,RoomTypeInfo roomTypeInfo) throws ApiException {
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
        XhotelRoomtypeUpdateResponse response = client.execute(req ,  company.getSessionKey());
        if (!StringUtils.isEmpty(response.getSubCode())) {
            return getRoomType(company,roomTypeInfo);
        }
        log.info("updateRoomType:" + response.getXroomtype());
        return response.getXroomtype();
    }

    /**
     * 获取宝贝信息
     * @param outerId
     * @param company
     */
    public static Long roomGet(Integer outerId,OtaInfo company) throws ApiException {
        log.info("roomGet outerId:" +outerId );
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomGetRequest req=new XhotelRoomGetRequest();
        req.setGid(Long.valueOf(outerId));
        XhotelRoomGetResponse response = client.execute(req , company.getSessionKey());
        if (!StringUtils.isEmpty(response.getSubCode())){
            return null;
        }
        return  response.getRoom().getGid();
    }

    public static Long roomDel(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfo company,RoomSwitchCalStatus status) throws Exception {
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
            log.info("roomSwitchJson:" + roomSwitchJson);
            log.info("Inventory:" +json);
        }
        XhotelRoomUpdateResponse response = client.execute(req , company.getSessionKey());
        log.info("roomDel:" + response.getGid());
        log.info("roomDel bady:" + response.getBody());
        if (!StringUtils.isEmpty(response.getSubCode())) {
            return  roomGet(outerId, company);
        }
        return response.getGid();
    }
    /**
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public  static Long  roomUpdate(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfo company,RoomSwitchCalStatus status,OtaInnOtaDto otaInnOta,OtaTaoBaoArea andArea) throws Exception, ApiException {
        log.info("start roomUpdate roomTypeId:" +outerId );
        String receiptOtherTypeDesc = null;
        String guide = null;
        StringBuilder builder = new StringBuilder();
        builder.append( andArea!=null?andArea.getCityName():"").append(" ").append(otaInnOta!=null?otaInnOta.getAliasInnName():"").append(" ").append(roomTypeInfo.getRoomTypeName());
        log.debug("roomUpdate title:"+builder.toString());
        try {
            receiptOtherTypeDesc = PropertiesUtil.readFile("/data.properties", "tb.receiptOtherTypeDesc");
            guide = PropertiesUtil.readFile("/data.properties", "tb.guide");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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
            log.info("图片地址:" +imgUrl +" imgName:"+imgName);
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
            log.info("roomSwitchJson:" + roomSwitchJson);
            log.info("Inventory:" +json);
        }
            XhotelRoomUpdateResponse response = client.execute(req , company.getSessionKey());
        log.info("roomUpdate:" + response.getGid());
        log.info("roomUpdate bady:" + response.getBody());
            if (!StringUtils.isEmpty(response.getSubCode())) {
                return  roomGet(outerId, company);
            }
            return response.getGid();
    }

    /**
     * 添加酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param company
     */
    public static Long ratePlanAdd(OtaInfo company,RoomTypeInfo r) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        req.setName("[不含早],价格优惠哟".concat(r.getRoomTypeName()));
        //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
        req.setPaymentType(1L);
        //0：不含早1：含单早2：含双早N：含N早（1-99可选）
        req.setBreakfastCount(0L);
        //req.setCancelPolicy("{\"cancelPolicyType\":5,\"policyInfo\":{\"timeBefore\":168}}");
        //2.表示不能退{"cancelPolicyType":2}
        req.setCancelPolicy("{\"cancelPolicyType\":2}");
        req.setStatus(1L);
        XhotelRateplanAddResponse response = client.execute(req , company.getSessionKey());
        if (!StringUtils.isEmpty(response.getSubCode())){
                return ratePlanUpdate(company, r);
        }
        log.info("ratePlanAdd:" +response.getRpid());
        return response.getRpid();

    }
    /**
     * 获取酒店rp
     * @param company
     */
    public static RatePlan getRatePlan(OtaInfo company,RoomTypeInfo r) throws ApiException {
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
    public static Long ratePlanUpdate(OtaInfo company,RoomTypeInfo r) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanUpdateRequest req=new XhotelRateplanUpdateRequest();
        req.setRateplanCode(String.valueOf(r.getRoomTypeId()));
        req.setName("[不含早],价格优惠哟".concat(r.getRoomTypeName()));
        //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
        req.setPaymentType(1L);
        //0：不含早1：含单早2：含双早N：含N早（1-99可选）
        req.setBreakfastCount(0L);
        req.setCancelPolicy("{\"cancelPolicyType\":2}");
        req.setStatus(1L);
        XhotelRateplanUpdateResponse response = client.execute(req , company.getSessionKey());
        log.info("ratePlanUpdate:" + response.getRpid());
        return response.getRpid();
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
    public static  String  rateAddOrUpdate(OtaInfo company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto,boolean sj) throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateAddRequest req=new XhotelRateAddRequest();
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
                rate.setPrice(price * 100);
                list.add(rate);
            }
            inventory.setInventory_price(list);
            String json = JacksonUtil.obj2json(inventory);
            req.setInventoryPrice(json);
            log.info("rateAddOrUpdate InventoryPrice:" + json);
        }
        XhotelRateAddResponse response = client.execute(req ,  company.getSessionKey());
        if (Constants.RATE_REPEAT_ERROR.equals(response.getSubCode())){
           return rateUpdate(company,gid,rpid,roomTypeInfo,priceModelDto,sj);
        }
        log.info("rateAddOrUpdate:" + response.getGidAndRpid());
        return  response.getGidAndRpid();

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
    public static String rateUpdate(OtaInfo company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto,boolean deleted) throws ApiException {
        log.info("rateUpdate gid:" + gid +" rateUpdate rpid:"+rpid);
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setGid(gid);
        req.setRpid(rpid);
        req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setRateplanCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<InventoryRate> list = new ArrayList<InventoryRate>();
            //List<RateSwitchCal> rateSwitchCalList = new ArrayList<RateSwitchCal>();
            InventoryPrice inventory = new InventoryPrice();
            //RateSwitchCal rateSwitchCal = null;
            InventoryRate rate = null;
            double price = 0;
            for (RoomDetail r:roomTypeInfo.getRoomDetail()){
                rate = new InventoryRate();
                //rateSwitchCal = new RateSwitchCal(r.getRoomDate(),deleted?0:1);
                rate.setDate(r.getRoomDate());
                //rate.setQuota(r.getRoomNum()==null?0:r.getRoomNum());
                price = new BigDecimal(r.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                rate.setPrice(price*100);
                list.add(rate);
               // rateSwitchCalList.add(rateSwitchCal);
            }
            inventory.setInventory_price(list);
            String json = JacksonUtil.obj2json(inventory);
            //String rateSwitchCalJson = JacksonUtil.obj2json(rateSwitchCalList);
            req.setInventoryPrice(json);
            //req.setRateSwitchCal(rateSwitchCalJson);
            //log.info("rateUpdate RateSwitchCal:" + rateSwitchCalJson);
            log.info("rateUpdate inventoryPrice:" + json);
        }
        XhotelRateUpdateResponse response = client.execute(req , company.getSessionKey());
        log.info("rateUpdate:" +  response.getGidAndRpid());
        return  response.getGidAndRpid();
    }
    /**
     * 获取库存
     * @param company
     * @param gid
     * @param rpid
     * @param roomTypeInfo
     *
     * @return
     */
    public static Rate rateGet(OtaInfo company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateGetRequest req=new XhotelRateGetRequest();
        req.setGid(gid);
        req.setRpid(rpid);
        req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
        req.setRateplanCode(String.valueOf(roomTypeInfo.getRoomTypeId()));
        try {
            XhotelRateGetResponse response = client.execute(req , company.getSessionKey());
            log.info("rateGet:" +  response.getRate());
           return  response.getRate();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    /**
     * 修改订单状态
     *
     * @param order
     * @param company
     */
    public static String orderUpdate(Order order, OtaInfo company, long optType) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderUpdateRequest req = new XhotelOrderUpdateRequest();
        req.setTid(Long.parseLong(order.getChannelOrderCode()));
        req.setOptType(optType);
        XhotelOrderUpdateResponse response = client.execute(req, company.getSessionKey());
        return response.getResult();
    }
}
