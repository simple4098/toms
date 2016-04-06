package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.math.BigDecimal;

/**
 * Created by wangdayin on 2016/3/30.
 * 订单其他费用明细
 */
public class OrderOtherPrice extends Domain {
    //订单id
    private String orderId;
    //其他费用详细id
    private String otherConsumerInfoId;
    //其他费用项目名称
    private String consumerProjectName;
    //级别
    private int leven;
    //其他费用名称
    private String priceName;
    //价格
    private BigDecimal price;
    //数量
    private int nums;

    public String getConsumerProjectName() {
        return consumerProjectName;
    }

    public void setConsumerProjectName(String consumerProjectName) {
        this.consumerProjectName = consumerProjectName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOtherConsumerInfoId() {
        return otherConsumerInfoId;
    }

    public void setOtherConsumerInfoId(String otherConsumerInfoId) {
        this.otherConsumerInfoId = otherConsumerInfoId;
    }

    public int getLeven() {
        return leven;
    }

    public void setLeven(int leven) {
        this.leven = leven;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }
}
