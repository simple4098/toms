package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2015/9/24.
 * 天下房仓同步价格信息数据
 */
@XmlRootElement(name = "SyncRateInfoRequest")
public class SyncRateInfoDataRequest {
    //合作方酒店id
    private String spHotelId;
    //合作方房型id
    private String spRoomTypeId;
    //合作方价格计划id
    private String spRatePlanId;
    //价格信息
    private List<SaleInfo> saleInfoList;

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

    @XmlElementWrapper(name = "SaleInfoList")
    @XmlElement(name = "SaleInfo")
    public List<SaleInfo> getSaleInfoList() {
        return saleInfoList;
    }

    public void setSaleInfoList(List<SaleInfo> saleInfoList) {
        this.saleInfoList = saleInfoList;
    }
}
