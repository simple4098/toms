package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.FcResult;
import com.fanqielaile.toms.model.fc.SaleItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2015/8/31.
 * 试订单相应对象
 */
@XmlRootElement(name = "CheckRoomAvailResponse")
public class CheckRoomAvailResponse extends FcResult {
    //合作方酒店id
    private String spHotelId;
    //合作方房型id
    private String spRoomTypeId;
    //合作方价格计划id
    private String spRatePlanId;
    //入住日期
    private String checkInDate;
    //离店日期
    private String checkOutDate;
    //间数
    private Integer roomNum;
    //售价信息
    private List<SaleItem> saleItems;


    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return spHotelId;
    }

    public void setSpHotelId(String spHotelId) {
        this.spHotelId = spHotelId;
    }

    @XmlElement(name = "SpRoomTypeId")
    public String getSpRoomTypeId() {
        return spRoomTypeId;
    }

    public void setSpRoomTypeId(String spRoomTypeId) {
        this.spRoomTypeId = spRoomTypeId;
    }

    @XmlElement(name = "SpRatePlanId")
    public String getSpRatePlanId() {
        return spRatePlanId;
    }

    public void setSpRatePlanId(String spRatePlanId) {
        this.spRatePlanId = spRatePlanId;
    }

    @XmlElement(name = "CheckInDate")
    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    @XmlElement(name = "CheckOutDate")
    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @XmlElement(name = "RoomNum")
    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    @XmlElement(name = "SaleItemList")
    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    @Override
    public String toString() {
        return "CheckRoomAvailResponse{" +
                "spHotelId='" + spHotelId + '\'' +
                ", spRoomTypeId='" + spRoomTypeId + '\'' +
                ", spRatePlanId='" + spRatePlanId + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", roomNum=" + roomNum +
                ", saleItems=" + saleItems.toString() +
                '}';
    }
}
