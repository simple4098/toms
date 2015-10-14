package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.dto.InnDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC :房仓酒店信息
 * @author : 番茄木-ZLin
 * @data : 2015/9/30
 * @version: v1.0.0
 */
public class FcInnInfoDto extends InnDto {

    private String cityCode;
    private String provinceCode;
    private String distinctCode;
    private String businessCode;
    //开业时间
    private Date praciceDate;
    //装修时间
    private Date fitmentDate;

    private List<FcInnImg> fcInnImgList;

    private List<FcRoomTypeDtoInfo> roomTypeInfoList;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getDistinctCode() {
        return distinctCode;
    }

    public void setDistinctCode(String distinctCode) {
        this.distinctCode = distinctCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Date getPraciceDate() {
        return praciceDate;
    }

    public void setPraciceDate(Date praciceDate) {
        this.praciceDate = praciceDate;
    }

    public Date getFitmentDate() {
        return fitmentDate;
    }

    public void setFitmentDate(Date fitmentDate) {
        this.fitmentDate = fitmentDate;
    }

    public List<FcInnImg> getFcInnImgList() {
        return fcInnImgList;
    }

    public void setFcInnImgList(List<FcInnImg> fcInnImgList) {
        this.fcInnImgList = fcInnImgList;
    }

    public List<FcRoomTypeDtoInfo> getRoomTypeInfoList() {
        return roomTypeInfoList;
    }

    public void setRoomTypeInfoList(List<FcRoomTypeDtoInfo> roomTypeInfoList) {
        this.roomTypeInfoList = roomTypeInfoList;
    }

    public Map toMap() {
       /* String json = JacksonUtil.objectToString(this);
        Map<String, ExportInnRoomType> typeMap = JacksonUtil.json2map(json, ExportInnRoomType.class);*/
        Map map = new HashMap();
        map.put("innId", getInnId());
        map.put("innName", getInnName());
        map.put("brandName", getBrandName());
        map.put("frontPhone", getFrontPhone());
        map.put("provinceCode", getProvinceCode());
        map.put("cityCode", getCityCode());
        map.put("businessCode", getBusinessCode());
        map.put("distinctCode", getDistinctCode());
        map.put("roomNum",getRoomNum());
        map.put("addr",getAddr());
        map.put("innInfo",getInnInfo());
        map.put("baiduLon",getBaiduLon());
        map.put("baiduLat",getBaiduLat());
        map.put("txLon",getTxLon());
        map.put("txLat",getTxLat());
        map.put("innInfo",getInnInfo());
        return map;
    }
}
