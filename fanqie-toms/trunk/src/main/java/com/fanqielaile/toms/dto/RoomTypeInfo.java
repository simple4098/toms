package com.fanqielaile.toms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/3
 * @version: v1.0.0
 */
public class RoomTypeInfo implements Serializable

    {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private Integer accountId; /* 开通记录编号 */

        private Integer omsInnId; /* 客栈基本信息id */

        private Integer innId;/*客栈id*/
        private String innName; /* 客栈名称 */

        private Integer roomTypeId; /* 房型id */
        private Integer otaRoomTypeId; /* ota房型id */

        private Double sellingPrice; /* 默认现价 */
        private Double initialPrice; /* 默认原始价格 */
        private Double weekSellingPrice; /* 周末现价 */
        private Double weekInitialPrice; /* 周末原始价格 */
        private String roomTypeName; /* 房间类型名称 */
        private Double roomArea; /* 房间面积 */
        private Integer bedNum; /* 床数 */
        private Integer bedLen; /* 床长 */
        private Integer bedWid; /* 床宽 */
        private Integer floorNum; /* 楼层数 */

        private Integer recommend;

        @JsonIgnore
        private String facilities; /* 基本设施 */
        private String roomInfo; /* 房间简介 */
        private Integer bindingRoomNumber;/*绑定的房间数*/

        private List<FacilitiesVo> facilitiesMap;/* 基本设施  key value 形式*/

        private List<OmsImg> imgList; /* 房型图片 */
        private List<RoomDetail> roomDetail; /* 房型价格详细信息 */


    public Integer getRecommend() {
        return recommend;
    }
    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }
    public List<FacilitiesVo> getFacilitiesMap() {
        return facilitiesMap;
    }
    public void setFacilitiesMap(List<FacilitiesVo> facilitiesMap) {
        this.facilitiesMap = facilitiesMap;
    }
    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    public Integer getOmsInnId() {
        return omsInnId;
    }
    public void setOmsInnId(Integer omsInnId) {
        this.omsInnId = omsInnId;
    }
    public String getInnName() {
        return innName;
    }
    public void setInnName(String innName) {
        this.innName = innName;
    }
    public Integer getRoomTypeId() {
        return roomTypeId;
    }
    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    public Integer getOtaRoomTypeId() {
        return otaRoomTypeId;
    }
    public void setOtaRoomTypeId(Integer otaRoomTypeId) {
        this.otaRoomTypeId = otaRoomTypeId;
    }
    public Double getSellingPrice() {
        return sellingPrice;
    }
    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public Double getInitialPrice() {
        return initialPrice;
    }
    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }
    public Double getWeekSellingPrice() {
        return weekSellingPrice;
    }
    public void setWeekSellingPrice(Double weekSellingPrice) {
        this.weekSellingPrice = weekSellingPrice;
    }
    public Double getWeekInitialPrice() {
        return weekInitialPrice;
    }
    public void setWeekInitialPrice(Double weekInitialPrice) {
        this.weekInitialPrice = weekInitialPrice;
    }
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
    public Double getRoomArea() {
        return roomArea;
    }
    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }
    public Integer getBedNum() {
        return bedNum;
    }
    public void setBedNum(Integer bedNum) {
        this.bedNum = bedNum;
    }
    public Integer getBedLen() {
        return bedLen;
    }
    public void setBedLen(Integer bedLen) {
        this.bedLen = bedLen;
    }
    public Integer getBedWid() {
        return bedWid;
    }
    public void setBedWid(Integer bedWid) {
        this.bedWid = bedWid;
    }
    public Integer getFloorNum() {
        return floorNum;
    }
    public void setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
    }
    public String getFacilities() {
        return facilities;
    }
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    public String getRoomInfo() {
        return roomInfo;
    }
    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }
    public List<OmsImg> getImgList() {
        return imgList;
    }
    public void setImgList(List<OmsImg> imgList) {
        this.imgList = imgList;
    }
    public List<RoomDetail> getRoomDetail() {
        return roomDetail;
    }
    public void setRoomDetail(List<RoomDetail> roomDetail) {
        this.roomDetail = roomDetail;
    }
    public Integer getBindingRoomNumber() {
        return bindingRoomNumber;
    }
    public void setBindingRoomNumber(Integer bindingRoomNumber) {
        this.bindingRoomNumber = bindingRoomNumber;
    }
    public Integer getInnId() {
        return innId;
    }
    public void setInnId(Integer innId) {
        this.innId = innId;
    }


}
