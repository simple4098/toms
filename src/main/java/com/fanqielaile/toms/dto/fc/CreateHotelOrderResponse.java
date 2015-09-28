package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.FCcheckRoomAvailResponseResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 天下房仓创建订单响应对象
 * Created by wangdayin on 2015/9/1.
 */
@XmlRootElement(name = "CreateHotelOrderResponse")
public class CreateHotelOrderResponse {
    //合作方的订单号
    private String spOrderId;
    //订单状态
    private Integer orderStatus;
    //房仓订单号
    private String fcOrderId;

    @XmlElement(name = "SpOrderId")
    public String getSpOrderId() {
        return spOrderId;
    }

    public void setSpOrderId(String spOrderId) {
        this.spOrderId = spOrderId;
    }

    @XmlElement(name = "FcOrderId")
    public String getFcOrderId() {
        return fcOrderId;
    }

    public void setFcOrderId(String fcOrderId) {
        this.fcOrderId = fcOrderId;
    }

    @XmlElement(name = "OrderStatus")
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
