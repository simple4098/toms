package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OrderStatus;

import java.util.Date;

/**
 * Created by wangdayin on 2015/9/7.
 * 订单操作记录
 */
public class OrderOperationRecord extends Domain {
    //订单ID
    private String orderId;
    //订单修改之前状态
    private OrderStatus beforeStatus;
    //订单修改之后的状态
    private OrderStatus afterStatus;
    //修改人
    private String modifyPerson;
    //创建时间
    private Date createdDate;
    //备注
    private String content;


    public OrderOperationRecord(String orderId, OrderStatus beforeStatus, OrderStatus afterStatus, String content, String modifyPerson) {
        this.orderId = orderId;
        this.beforeStatus = beforeStatus;
        this.afterStatus = afterStatus;
        this.content = content;
        this.modifyPerson = modifyPerson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(OrderStatus beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public OrderStatus getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(OrderStatus afterStatus) {
        this.afterStatus = afterStatus;
    }

    public String getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(String modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
