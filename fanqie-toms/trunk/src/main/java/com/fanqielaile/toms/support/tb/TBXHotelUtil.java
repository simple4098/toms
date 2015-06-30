package com.fanqielaile.toms.support.tb;

import com.fanqie.util.DcUtil;
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
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC : 淘宝对接 API
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public class TBXHotelUtil {
    private static  final Logger log = LoggerFactory.getLogger(TBXHotelUtil.class);
    private static  final  String url = "http://gw.api.tbsandbox.com/router/rest";
    private TBXHotelUtil(){}

    /**
     * 想淘宝添加酒店
     * @param company 公司对象，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    public static XHotel hotelAdd(Company company,InnDto innDto,OtaTaoBaoArea andArea){
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
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
            System.out.println("hotelAdd:" + response.getBody());
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
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelGetRequest req=new XhotelGetRequest();
        req.setHid(hid);
        try {
            XhotelGetResponse response = client.execute(req , company.getSessionKey());
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
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
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
            System.out.println("========"+response.getBody());
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
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeGetRequest req=new XhotelRoomtypeGetRequest();
        req.setRid(rid);
        try {
            XhotelRoomtypeGetResponse response = client.execute(req , company.getSessionKey());
            System.out.println("==getRoomType:"+response.getBody());
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
    public  static Long  roomAdd(Integer outerId,Long hid,Long rid,RoomTypeInfo roomTypeInfo,Company company){
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
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
            System.out.println("roomAdd:" + response.getBody());
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
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelRateplanAddRequest req=new XhotelRateplanAddRequest();
        req.setRateplanCode(innName.concat("ratePlanCode"));
        req.setName(innName.concat("[不含早],价格优惠哟"));
        req.setPaymentType(1L);
        req.setBreakfastCount(0L);
        req.setCancelPolicy("{\"cancelPolicyType\":1}");
        req.setStatus(1L);
        try {
            XhotelRateplanAddResponse response = client.execute(req , company.getSessionKey());
            System.out.println("ratePlanAdd:"+response.getBody());
            return response.getRpid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;
    }

    public static String rateUpdate(Company company,Long gid,Long rpid,RoomTypeInfo roomTypeInfo, OtaPriceModelDto priceModelDto,boolean deleted){
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelRateUpdateRequest req=new XhotelRateUpdateRequest();
        req.setGid(gid);
        req.setRpid(rpid);
        req.setOutRid(String.valueOf(roomTypeInfo.getRoomTypeId()));
        //库存
        if (!CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
            List<InventoryRate> list = new ArrayList<InventoryRate>();
            List<RateSwitchCal> rateSwitchCalList = new ArrayList<RateSwitchCal>();
            InventoryPrice inventory = new InventoryPrice();
            RateSwitchCal rateSwitchCal = null;
            InventoryRate rate = null;
            for (RoomDetail  r:roomTypeInfo.getRoomDetail()){
                rate = new InventoryRate();
                rateSwitchCal = new RateSwitchCal(r.getRoomDate(),deleted?0:1);
                rate.setDate(r.getRoomDate());
                rate.setQuota(r.getRoomNum());
                double price = new BigDecimal(r.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                rate.setPrice(price*10);
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
            System.out.println("rateUpdate:"+response.getBody());
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
    public static String orderUpdate(Order order, Company company) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelOrderUpdateRequest req = new XhotelOrderUpdateRequest();
        req.setTid((long) Integer.parseInt(order.getChannelOrderCode()));
        req.setOptType(1L);
        XhotelOrderUpdateResponse response = client.execute(req, company.getSessionKey());
        return response.getResult();
    }
}
