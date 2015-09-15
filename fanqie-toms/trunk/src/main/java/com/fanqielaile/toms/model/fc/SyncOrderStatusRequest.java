package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/8.
 * 天下房仓推送订单状态对象
 */
@XmlRootElement(name = "SyncOrderStatusRequest")
public class SyncOrderStatusRequest {
    //TOMS订单ID
    private String orderId;
    //订单状态
    private String orderStatus;


    @XmlElement(name = "SpOrderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @XmlElement(name = "OrderStatus")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
