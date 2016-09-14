package com.fanqielaile.toms.vo.oms;

/**
 * Create by jame
 * Date: 2016/9/7 10:29
 * Version: 1.0
 * Description: 阐述
 */
public class SubmitOrderReturnVo extends ReturnBaseVo{
    private String orderNo;

    public SubmitOrderReturnVo() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
