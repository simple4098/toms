package com.fanqielaile.toms.support.tb;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param company 公司对象，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelAdd(Company company,InnDto innDto,OtaTaoBaoArea andArea){
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
    public static XHotel hotelGet(Long hid,Company company) {
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
     * @param company 公司信息
     */
    public static XRoomType addRoomType(String innId,String outerId,Long hid,RoomTypeInfo roomTypeInfo,Company company){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(outerId);
        req.setOutHid(innId);
        req.setHid(hid);
        req.setName(roomTypeInfo.getRoomTypeName());
        req.setArea(String.valueOf(roomTypeInfo.getRoomArea()));
        req.setFloor(String.valueOf(roomTypeInfo.getFloorNum()));
        req.setBedSize(String.valueOf(roomTypeInfo.getBedLen()));
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
    public static XRoomType getRoomType(Long rid,Company company){
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
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    @Deprecated
    public  static Long  roomAdd(Integer outerId,Long hid,Long rid,RoomTypeInfo roomTypeInfo,Company company){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomAddRequest req=new XhotelRoomAddRequest();
        req.setHid(hid);
        req.setRid(rid);
        req.setOutRid(String.valueOf(outerId));
        req.setTitle(roomTypeInfo.getRoomTypeName());
        req.setGuide(roomTypeInfo.getRoomInfo());
        req.setDesc(roomTypeInfo.getRoomInfo());
        //房型图片
        if (!CollectionUtils.isEmpty(roomTypeInfo.getImgList())){
            OmsImg omsImg = roomTypeInfo.getImgList().get(0);
            String imgUrl = CommonApi.IMG_URL.concat(omsImg.getImgUrl());
            req.setPic(new FileItem(imgUrl));
        }
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<Inventory> list = new ArrayList<Inventory>();
            for (RoomDetail  r:roomTypeInfo.getRoomDetail()){
                Inventory inventory = new Inventory();
                inventory.setDate(r.getRoomDate());
                inventory.setQuota(r.getRoomNum());
                list.add(inventory);
            }
            String json = JacksonUtil.obj2json(list);
            req.setInventory(json);
        }

        try {
            XhotelRoomAddResponse response = client.execute(req , company.getSessionKey());
            log.info("roomAdd:" + response.getBody());
            return response.getGid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }
    /**
     * 添加商品
     * @param outerId pms系统中的房型id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public  static Long  roomUpdate(Integer outerId,RoomTypeInfo roomTypeInfo,Company company,RoomSwitchCalStatus status){
        log.info("start roomUpdate roomTypeId:" +outerId );
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRoomUpdateRequest req=new XhotelRoomUpdateRequest();
        req.setOutRid(String.valueOf(outerId));
        req.setTitle(roomTypeInfo.getRoomTypeName());
        req.setGuide(roomTypeInfo.getRoomInfo());
        req.setDesc(roomTypeInfo.getRoomInfo());
        //房型图片
        if (!CollectionUtils.isEmpty(roomTypeInfo.getImgList())){
            OmsImg omsImg = roomTypeInfo.getImgList().get(0);
            String imgUrl = CommonApi.IMG_URL.concat(omsImg.getImgUrl());
            req.setPic(new FileItem(imgUrl));
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
                inventory.setQuota(r.getRoomNum()==null?0:r.getRoomNum());
                list.add(inventory);
                roomSwitchCals.add(roomSwitchCal);
            }
            String json = JacksonUtil.obj2json(list);
            String roomSwitchJson = JacksonUtil.obj2json(roomSwitchCals);
            req.setInventory(json);
            //开关状态 1 上架  2 下架  3 删除
            req.setRoomSwitchCal(roomSwitchJson);
            log.info("roomSwitchJson:" +roomSwitchJson);
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
     * 添加酒店RP
     * @param company
     */
    public static Long ratePlanAdd(Company company,String innName){
        TaobaoClient client=new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        req.setRateplanCode(innName.concat("ratePlanCode"));
        req.setName(innName.concat("[不含早],价格优惠哟"));
        req.setPaymentType(1L);
        req.setBreakfastCount(0L);
        req.setCancelPolicy("{\"cancelPolicyType\":1}");
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
     * 更新库存
     * @param company
     * @param gid
     * @param rpid
     * @param roomTypeInfo
     * @param priceModelDto
     * @param deleted 库存日历开关， true 关闭； false 打开
     * @return
     */
    public static String rateUpdate(Company company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto,boolean deleted){
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
     * 修改订单状态
     *
     * @param order
     * @param company
     */
    public static String orderUpdate(Order order, Company company, long optType) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(CommonApi.TB_URL, company.getAppKey(), company.getAppSecret());
        XhotelOrderUpdateRequest req = new XhotelOrderUpdateRequest();
        req.setTid(Long.parseLong(order.getChannelOrderCode()));
        req.setOptType(optType);
        XhotelOrderUpdateResponse response = client.execute(req, company.getSessionKey());
        return response.getResult();
    }
}
