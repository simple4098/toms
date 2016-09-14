package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/12 14:49
 * Version: 1.0
 * Description: 阐述
 */
public class CancelOrderRefundVo {
    private Integer amount;
    private String desc;

    public CancelOrderRefundVo() {
        super();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
