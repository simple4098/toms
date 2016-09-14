package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/12 14:48
 * Version: 1.0
 * Description: 阐述
 */
public class CancelOrderReturnVo extends ReturnBaseVo {
    private Integer statusId;
    private CancelOrderRefundVo refund;

    public CancelOrderReturnVo(){
        super();
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public CancelOrderRefundVo getRefund() {
        return refund;
    }

    public void setRefund(CancelOrderRefundVo refund) {
        this.refund = refund;
    }
}
