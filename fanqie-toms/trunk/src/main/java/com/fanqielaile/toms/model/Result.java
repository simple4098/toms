package com.fanqielaile.toms.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/6/19.
 * 返回对象
 */
@XmlRootElement(name = "Result")
public class Result {
    private String message;
    private String resultCode;
    private String status;
    private String taoBaoOrderId;
    private String orderId;//toms订单号

    @XmlElement(name = "OrderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @XmlElement(name = "TaoBaoOrderId")
    public String getTaoBaoOrderId() {
        return taoBaoOrderId;
    }

    public void setTaoBaoOrderId(String taoBaoOrderId) {
        this.taoBaoOrderId = taoBaoOrderId;
    }

    @XmlElement(name = "Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "ResultCode")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", status='" + status + '\'' +
                ", taoBaoOrderId='" + taoBaoOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
