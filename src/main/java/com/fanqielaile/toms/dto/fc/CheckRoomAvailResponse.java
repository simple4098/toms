package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.SaleItem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2015/8/31.
 * 试订单相应对象
 */
@XmlRootElement(name = "CheckRoomAvailResponse")
public class CheckRoomAvailResponse {
    //合作方酒店id
    private String canBook;
    //合作方房型id
    private String canImmediate;
    //合作方价格计划id
    private String spRatePlanId;
    //售价信息
    private List<SaleItem> saleItems;


    @XmlElement(name = "CanBook")
    public String getCanBook() {
        return canBook;
    }

    public void setCanBook(String canBook) {
        this.canBook = canBook;
    }

    @XmlElement(name = "CanImmediate")
    public String getCanImmediate() {
        return canImmediate;
    }

    public void setCanImmediate(String canImmediate) {
        this.canImmediate = canImmediate;
    }

    @XmlElement(name = "SpRatePlanId")
    public String getSpRatePlanId() {
        return spRatePlanId;
    }

    public void setSpRatePlanId(String spRatePlanId) {
        this.spRatePlanId = spRatePlanId;
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
                "canBook='" + canBook + '\'' +
                ", canImmediate='" + canImmediate + '\'' +
                ", spRatePlanId='" + spRatePlanId + '\'' +
                ", saleItems=" + saleItems +
                '}';
    }
}
