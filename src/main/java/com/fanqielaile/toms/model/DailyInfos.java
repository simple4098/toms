package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangdayin on 2015/6/19.
 * 每日价格信息
 */
public class DailyInfos extends Domain {
    //时间
    private Date day;
    //价格
    private BigDecimal price;
    //订单ID
    private String orderId;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
