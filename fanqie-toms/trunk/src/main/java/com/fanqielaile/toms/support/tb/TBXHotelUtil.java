package com.fanqielaile.toms.support.tb;

import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaTaoBaoArea;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelGetRequest;
import com.taobao.api.request.XhotelRoomAddRequest;
import com.taobao.api.request.XhotelRoomtypeAddRequest;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelGetResponse;
import com.taobao.api.response.XhotelRoomAddResponse;
import com.taobao.api.response.XhotelRoomtypeAddResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
            req.setProvince(!StringUtils.isEmpty(andArea.getProvinceCode())?Long.valueOf(andArea.getProvinceCode()):null);
            req.setCity(!StringUtils.isEmpty(andArea.getCityCode()) ? Long.valueOf(andArea.getCityCode()) : null);
        }
        req.setAddress(innDto.getAddr());
        try {
            XhotelAddResponse response = client.execute(req , company.getSessionKey());
            System.out.println("==========response.getBody()=" + response.getBody());
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
     *
     * @param outerId pms系统中房型id
     * @param hid 酒店id
     * @param roomTypeInfo 房型信息
     * @param company 公司信息
     */
    public static XRoomType addRoomType(String outerId,Long hid,RoomTypeInfo roomTypeInfo,Company company){
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelRoomtypeAddRequest req=new XhotelRoomtypeAddRequest();
        req.setOuterId(outerId);
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

    public  static Long  roomAdd(Company company){
        TaobaoClient client=new DefaultTaobaoClient(url, company.getAppKey(), company.getAppSecret());
        XhotelRoomAddRequest req=new XhotelRoomAddRequest();
        req.setHid(16498001123L);
        req.setRid(35354003123L);
        req.setTitle("商品room");
        try {
            XhotelRoomAddResponse response = client.execute(req , company.getSessionKey());
            System.out.println("response.getBody()=" + response.getBody());
            return response.getGid();
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }
        return  null;

    }
}
