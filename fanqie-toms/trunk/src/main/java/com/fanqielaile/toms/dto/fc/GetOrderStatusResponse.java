package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.FCcheckRoomAvailResponseResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/2.
 * 天下房仓查询订单状态相应对象
 */
@XmlRootElement(name = "GetOrderStatusResponse")
public class GetOrderStatusResponse {
    //合作方订单编码
    private String spOrderId;
    //订单状态
    private Integer orderStatus;

    @XmlElement(name = "SpOrderId")
    public String getSpOrderId() {
        return spOrderId;
    }

    public void setSpOrderId(String spOrderId) {
        this.spOrderId = spOrderId;
    }

    @XmlElement(name = "OrderStatus")
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "GetOrderStatusResponse{" +
                "spOrderId='" + spOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
