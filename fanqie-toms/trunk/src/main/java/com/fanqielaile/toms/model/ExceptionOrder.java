package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.FeeStatus;
import com.fanqielaile.toms.enums.OrderStatus;

/**
 * Created by wangdayin on 2016/1/28.
 */
public class ExceptionOrder extends Domain {
    //订单修改之后的状态
    private OrderStatus modifierStatus;
    //订单id
    private String orderId;
    //付款状态
    private FeeStatus feeStatus;

    public ExceptionOrder() {
    }

    public ExceptionOrder(OrderStatus modifierStatus, String orderId, FeeStatus feeStatus) {
        this.modifierStatus = modifierStatus;
        this.orderId = orderId;
        this.feeStatus = feeStatus;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getModifierStatus() {
        return modifierStatus;
    }

    public void setModifierStatus(OrderStatus modifierStatus) {
        this.modifierStatus = modifierStatus;
    }
}
