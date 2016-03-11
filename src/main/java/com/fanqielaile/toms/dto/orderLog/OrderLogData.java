package com.fanqielaile.toms.dto.orderLog;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.FeeStatus;
import com.fanqielaile.toms.enums.OrderStatus;

import java.util.Date;

/**
 * Created by wangdayin on 2016/3/1.
 */
public class OrderLogData {
    //渠道来源
    private ChannelSource channelSource;
    //渠道订单号
    private String channelOrderCode;
    //toms订单号
    private String tomsOrderCode;
    //oms订单号
    private String omsOrderCode;
    //日志创建时间
    private Date operateTime = new Date();
    //订单状态变更之前
    private OrderStatus beforeOrderStatus;
    //订单状态变更之后
    private OrderStatus afterOrderStatus;
    //请求参数
    private String requestParam;
    //返回参数
    private String responseParam;
    //客栈id
    private Integer innId;
    //客栈code
    private String innCode;
    //备注信息
    private String content;
    //付款状态
    private FeeStatus feeStatus;

    public OrderLogData() {
    }

    public OrderLogData(ChannelSource channelSource, String requestParam, String content) {
        this.channelSource = channelSource;
        this.requestParam = requestParam;
        this.content = content;
    }


    public OrderLogData(ChannelSource channelSource, String channelOrderCode, String tomsOrderCode, String omsOrderCode, OrderStatus beforeOrderStatus, OrderStatus afterOrderStatus, FeeStatus feeStatus, String requestParam, String responseParam, Integer innId, String innCode, String content) {
        this.channelSource = channelSource;
        this.channelOrderCode = channelOrderCode;
        this.tomsOrderCode = tomsOrderCode;
        this.omsOrderCode = omsOrderCode;
        this.beforeOrderStatus = beforeOrderStatus;
        this.afterOrderStatus = afterOrderStatus;
        this.feeStatus = feeStatus;
        this.requestParam = requestParam;
        this.responseParam = responseParam;
        this.innId = innId;
        this.innCode = innCode;
        this.content = content;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public ChannelSource getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(ChannelSource channelSource) {
        this.channelSource = channelSource;
    }

    public String getInnCode() {
        return innCode;
    }

    public void setInnCode(String innCode) {
        this.innCode = innCode;
    }

    public String getChannelOrderCode() {
        return channelOrderCode;
    }

    public void setChannelOrderCode(String channelOrderCode) {
        this.channelOrderCode = channelOrderCode;
    }

    public String getTomsOrderCode() {
        return tomsOrderCode;
    }

    public void setTomsOrderCode(String tomsOrderCode) {
        this.tomsOrderCode = tomsOrderCode;
    }

    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public OrderStatus getBeforeOrderStatus() {
        return beforeOrderStatus;
    }

    public void setBeforeOrderStatus(OrderStatus beforeOrderStatus) {
        this.beforeOrderStatus = beforeOrderStatus;
    }

    public OrderStatus getAfterOrderStatus() {
        return afterOrderStatus;
    }

    public void setAfterOrderStatus(OrderStatus afterOrderStatus) {
        this.afterOrderStatus = afterOrderStatus;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
