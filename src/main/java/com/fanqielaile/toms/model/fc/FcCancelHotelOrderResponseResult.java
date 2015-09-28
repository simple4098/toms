package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by wangdayin on 2015/9/28.
 */
public class FcCancelHotelOrderResponseResult {
    private String resultFlag;
    private String resultMsg;

    private CancelHotelOrderResponse cancelHotelOrderResponse;

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

    @XmlElement(name = "CancelHotelOrderResponse")
    public CancelHotelOrderResponse getCancelHotelOrderResponse() {
        return cancelHotelOrderResponse;
    }

    public void setCancelHotelOrderResponse(CancelHotelOrderResponse cancelHotelOrderResponse) {
        this.cancelHotelOrderResponse = cancelHotelOrderResponse;
    }

    @Override
    public String toString() {
        return "FcCancelHotelOrderResponseResult{" +
                "resultFlag='" + resultFlag + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", cancelHotelOrderResponse=" + cancelHotelOrderResponse +
                '}';
    }
}
