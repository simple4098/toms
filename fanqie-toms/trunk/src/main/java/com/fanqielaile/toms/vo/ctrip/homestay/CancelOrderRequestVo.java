package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/12 14:47
 * Version: 1.0
 * Description: 阐述
 */
public class CancelOrderRequestVo {
    private Long orderId;
    private Integer cancelType;  //处理类型：1.检查是否可以取消；2.取消订单；


    public CancelOrderRequestVo() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCancelType() {
        return cancelType;
    }

    public void setCancelType(Integer cancelType) {
        this.cancelType = cancelType;
    }
}
