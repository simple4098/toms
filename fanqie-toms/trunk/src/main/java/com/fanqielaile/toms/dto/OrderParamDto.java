package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.Order;

import java.math.BigDecimal;

/**
 * Created by wangdayin on 2015/7/6.
 */
public class OrderParamDto extends Order {
    //房型名称
    private String roomTypeName;
    //客栈名称
    private String innName;
    //开始时间
    private String beginDate;
    //结束时间
    private String endDate;
    //日期类型
    private String searchType;
    //已接受订单数量
    private int acceptOrder = 0;
    //所有的总价
    private BigDecimal allTotalPrice = BigDecimal.ZERO;
    //所有预付金额
    private BigDecimal allPrePrice = BigDecimal.ZERO;
    //所有成本价
    private BigDecimal allCostPrice = BigDecimal.ZERO;
    //ota佣金
    private BigDecimal allPayPrice = BigDecimal.ZERO;
    //订单状态
    private String orderStatusDesc;
    //订单状态字符串
    private String orderStatusString;

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public String getOrderStatusDesc() {
        return getOrderStatus().getText();
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public int getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(int acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    public BigDecimal getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(BigDecimal allTotalPrice) {
        this.allTotalPrice = allTotalPrice;
    }

    public BigDecimal getAllPrePrice() {
        return allPrePrice;
    }

    public void setAllPrePrice(BigDecimal allPrePrice) {
        this.allPrePrice = allPrePrice;
    }

    public BigDecimal getAllCostPrice() {
        return allCostPrice;
    }

    public void setAllCostPrice(BigDecimal allCostPrice) {
        this.allCostPrice = allCostPrice;
    }

    public BigDecimal getAllPayPrice() {
        return allPayPrice;
    }

    public void setAllPayPrice(BigDecimal allPayPrice) {
        this.allPayPrice = allPayPrice;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    @Override
    public String getRoomTypeName() {
        return roomTypeName;
    }

    @Override
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
}
