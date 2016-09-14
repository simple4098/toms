package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/6 10:39
 * Version: 1.0
 * Description: 阐述
 * deposit		object
 * amount	int
 * type	int
 */
public class SubmitOrderDepositVo {
    private int amount;
    private int type;

    public SubmitOrderDepositVo() {
        super();
    }

    public SubmitOrderDepositVo(int amount, int type) {
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
