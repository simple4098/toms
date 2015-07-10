package com.fanqielaile.toms.support.tb;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.PropertiesUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.fanqielaile.toms.support.util.TPServiceUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Rate;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoom;
import com.taobao.api.domain.XRoomType;
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
     */
    public static XHotel hotelAdd(OtaInfo company,InnDto innDto,OtaTaoBaoArea andArea){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelAddRequest req=new XhotelAddRequest();
        req.setOuterId(innDto.getInnId());
        req.setName(innDto.getBrandName());
        req.setUsedName(innDto.getInnName());
        if (andArea!=null){
            req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):110000l);
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : 110100l);
        }
        req.setAddress(innDto.getAddr());
        try {
            XhotelAddResponse response = client.execute(req , company.getSessionKey());
           // System.out.println("hotelAdd:" + response.getBody());
            log.info("hotelAdd:" + response.getBody());
            return response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     * 获取酒店
     * @param company
     * @return
     */
    public static XHotel hotelGet(Long hid,OtaInfo company) {
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelGetRequest req=new XhotelGetRequest();
        req.setHid(hid);
        try {
            XhotelGetResponse response = client.execute(req , company.getSessionKey());
            log.info("hotelGet:" + response.getBody());
            return  response.getXhotel();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     *添加房型
     * @param innId pms系统中的客栈id
     * @param outerId pms系统中房型id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 渠道信息
     */
    public static XRoomType addRoomType(String innId,String outerId,Long hid,RoomTypeInfo roomTypeInfo,OtaInfo company){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(outerId);
        req.setOutHid(innId);
        req.setHid(hid);
        req.setName(roomTypeInfo.getRoomTypeName());
        req.setArea(String.valueOf(roomTypeInfo.getRoomArea()));
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        req.setBedSize(String.valueOf(roomTypeInfo.getBedLen()));
        List<FacilitiesVo> facilitiesMap = roomTypeInfo.getFacilitiesMap();
        //服务设施
        req.setService(TPServiceUtil.jsonService(facilitiesMap));
        try {
            XhotelRoomtypeAddResponse response = client.execute(req , company.getSessionKey());
            log.info("addRoomType:" + response.getBody());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }

    /**
     * 获取房型信息
     * @param rid rid tp店房型id
     */
    public static XRoomType getRoomType(Long rid,OtaInfo company){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeGetRequest req=new XhotelRoomtypeGetRequest();
        req.setRid(rid);
        try {
            XhotelRoomtypeGetResponse response = client.execute(req , company.getSessionKey());
            log.info("getRoomType:" + response.getBody());
            return response.getXroomtype();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    /**
     * 获取宝贝信息
     * @param outerId
     * @param company
     */
    public static XRoom roomGet(Integer outerId,OtaInfo company){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomGetRequest req=new XhotelRoomGetRequest();
        req.setGid(Long.valueOf(outerId));
        try {
            XhotelRoomGetResponse response = client.execute(req , company.getSessionKey());
            return  response.getRoom();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public  static Long  roomUpdate(Integer outerId,RoomTypeInfo roomTypeInfo,OtaInfo company,RoomSwitchCalStatus status) throws IOException {
        log.info("start roomUpdate roomTypeId:" +outerId );
        String receiptOtherTypeDesc = null;
        try {
            receiptOtherTypeDesc = PropertiesUtil.readFile("/data.properties", "tb.receiptOtherTypeDesc");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setOutRid(String.valueOf(outerId));
        req.setTitle(roomTypeInfo.getRoomTypeName());
        req.setDesc(roomTypeInfo.getRoomInfo());
        //提供发票
        req.setHasReceipt(true);
        req.setReceiptType("B");
        req.setReceiptOtherTypeDesc(receiptOtherTypeDesc);
        //房型图片
        if (!CollectionUtils.isEmpty(roomTypeInfo.getImgList())){
            OmsImg omsImg = roomTypeInfo.getImgList().get(0);
            String imgUrl = CommonApi.IMG_URL.concat(omsImg.getImgUrl());
            String imgName = StringUtils.substring(omsImg.getImgUrl(), omsImg.getImgUrl().lastIndexOf("/"));
            byte[] bytes = null;
            try {
                log.info("图片地址:" +imgUrl );
                bytes = HttpClientUtil.readImg(imgUrl);
            } catch (Exception e) {
                log.error("获取宝贝图片异常:" + e.getMessage());
            }
            req.setPic(new FileItem(imgName,bytes,"image/jpeg"));
        }
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<Inventory> list = new ArrayList<Inventory>();
            List<RoomSwitchCal> roomSwitchCals = new ArrayList<RoomSwitchCal>();
            Inventory inventory =null;
            RoomSwitchCal roomSwitchCal=null;
            for (RoomDetail  r:roomTypeInfo.getRoomDetail()){
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

        try {
            XhotelRoomUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("roomUpdate:" + response.getBody());
            return response.getGid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    /**
     * 添加酒店RP,7天以内可以取消， 7天以外不可以取消
     * @param company
     */
    public static Long ratePlanAdd(OtaInfo company,RoomTypeInfo r){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        req.setRateplanCode((r.getRoomTypeName() + r.getRoomTypeId()).concat("ratePlanCode"));
        req.setName("[不含早],价格优惠哟".concat(r.getRoomTypeName()));
        //支付类型，只支持：1：预付5：现付6: 信用住。其中5,6两种类型需要申请权限
        req.setPaymentType(1L);
        //0：不含早1：含单早2：含双早N：含N早（1-99可选）
        req.setBreakfastCount(0L);
        req.setCancelPolicy("{\"cancelPolicyType\":5,\"policyInfo\":{\"timeBefore\":168}}");
        req.setStatus(1L);
        try {
            XhotelRateplanAddResponse response = client.execute(req , company.getSessionKey());
            log.info("ratePlanAdd:" + response.getBody());
            return response.getRpid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    /**
     * 添加商品的库存
     * @param company 公司信息
     * @param gid 商品id
     * @param rpid 商品计划id
     * @param roomTypeInfo 房型信息
     * @param priceModelDto 价格策略
     */
    public static  String  rateAdd(OtaInfo company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto){
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
            log.info("rateAdd InventoryPrice:" + json);
        }

        try {
            XhotelRateAddResponse response = client.execute(req ,  company.getSessionKey());
            log.info("rateAdd:" + response.getBody());
            return  response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
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
    public static String rateUpdate(OtaInfo company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto,boolean deleted){

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
            List<RateSwitchCal> rateSwitchCalList = new ArrayList<RateSwitchCal>();
            InventoryPrice inventory = new InventoryPrice();
            RateSwitchCal rateSwitchCal = null;
            InventoryRate rate = null;
            double price = 0;
            for (RoomDetail  r:roomTypeInfo.getRoomDetail()){
                rate = new InventoryRate();
                rateSwitchCal = new RateSwitchCal(r.getRoomDate(),deleted?0:1);
                rate.setDate(r.getRoomDate());
                rate.setQuota(r.getRoomNum()==null?0:r.getRoomNum());
                price = new BigDecimal(r.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                rate.setPrice(price*100);
                list.add(rate);
                rateSwitchCalList.add(rateSwitchCal);
            }
            inventory.setInventory_price(list);
            String json = JacksonUtil.obj2json(inventory);
            String rateSwitchCalJson = JacksonUtil.obj2json(rateSwitchCalList);
            req.setInventoryPrice(json);
            req.setRateSwitchCal(rateSwitchCalJson);
            log.info("rateUpdate RateSwitchCal:" + rateSwitchCalJson);
            log.info("rateUpdate inventoryPrice:" + json);
        }

        try {
            XhotelRateUpdateResponse response = client.execute(req , company.getSessionKey());
            log.info("rateUpdate:" + response.getBody());
           return  response.getGidAndRpid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
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
            log.info("rateGet:" + response.getBody());
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
