package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.enums.OperateType;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by wangdayin on 2015/9/24.
 */
@XmlRootElement(name = "SyncRatePlanRequest")
@XmlType(propOrder = {"spHotelId", "operateType", "spRoomTypeId", "ratePlanList"})
public class SyncRatePlanRequestInfo {
    //合作方酒店id
    private String spHotelId;
    //操作类型：NEW新建；UPD更新
    private OperateType operateType;
    //合作方房型id
    private String spRoomTypeId;
    //合作方价格计划id
    private List<RatePlan> ratePlanList;

    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return spHotelId;
    }

    public void setSpHotelId(String spHotelId) {
        this.spHotelId = spHotelId;
    }

    @XmlElement(name = "OperateType")
    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    @XmlElementWrapper(name = "RatePlanList")
    @XmlElement(name = "RatePlan")
    public List<RatePlan> getRatePlanList() {
        return ratePlanList;
    }

    @XmlElement(name = "SpRoomTypeId")
    public String getSpRoomTypeId() {
        return spRoomTypeId;
    }

    public void setSpRoomTypeId(String spRoomTypeId) {
        this.spRoomTypeId = spRoomTypeId;
    }

    public void setRatePlanList(List<RatePlan> ratePlanList) {
        this.ratePlanList = ratePlanList;
    }
}
