package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/24
 * @version: v1.0.0
 */
@XmlRootElement(name = "DeleteRatePlanRequest")
@XmlType(propOrder = {"spHotelId","spRoomTypeId","ratePlanInfoList"})
public class DeleteRatePlanInfoRequest {
    //合作方酒店id
    private String spHotelId;
    //合作方房型id
    private String spRoomTypeId;

    private List<RatePlan> ratePlanInfoList;

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

    @XmlElementWrapper(name = "RatePlanInfoList")
    @XmlElement(name = "RatePlanInfo")
    public List<RatePlan> getRatePlanInfoList() {
        return ratePlanInfoList;
    }

    public void setRatePlanInfoList(List<RatePlan> ratePlanInfoList) {
        this.ratePlanInfoList = ratePlanInfoList;
    }
}
