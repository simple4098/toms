package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/28.
 */
@XmlRootElement(name = "Response")
public class FcGetOrderStatusResponseResult {
    private String resultFlag;
    private String resultMsg;

    private GetOrderStatusResponse getOrderStatusResponse;

    @XmlElement(name = "ResultFlag")
    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    @XmlElement(name = "ResultMsg")
    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @XmlElement(name = "GetOrderStatusResponse")
    public GetOrderStatusResponse getGetOrderStatusResponse() {
        return getOrderStatusResponse;
    }

    public void setGetOrderStatusResponse(GetOrderStatusResponse getOrderStatusResponse) {
        this.getOrderStatusResponse = getOrderStatusResponse;
    }

    @Override
    public String toString() {
        return "FcGetOrderStatusResponseResult{" +
                "resultFlag='" + resultFlag + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", getOrderStatusResponse=" + getOrderStatusResponse +
                '}';
    }
}
