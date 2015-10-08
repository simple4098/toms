package com.fanqielaile.toms.dto.fc;

import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.RoomTypeInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/30
 * @version: v1.0.0
 */
public class ExportInnRoomType {

    private String innName;
    private String address;
    private String telPhone;
    private String cityName;
    private String distinct;
    private String business;
    private List<RoomTypeInfo> roomList;

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public List<RoomTypeInfo> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomTypeInfo> roomList) {
        this.roomList = roomList;
    }

    public Map toMap() {
       /* String json = JacksonUtil.objectToString(this);
        Map<String, ExportInnRoomType> typeMap = JacksonUtil.json2map(json, ExportInnRoomType.class);*/
        Map map = new HashMap();
        map.put("innName", getInnName());
        map.put("address", getAddress());
        map.put("telPhone", getTelPhone());
        map.put("cityName", getCityName());
        map.put("distinct", getDistinct());
        map.put("business", getBusiness());
        return map;
    }
}
