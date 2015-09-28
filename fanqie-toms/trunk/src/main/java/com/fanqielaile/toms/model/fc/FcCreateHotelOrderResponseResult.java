package com.fanqielaile.toms.model.fc;

import com.fanqielaile.toms.dto.fc.CreateHotelOrderResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/28.
 */
@XmlRootElement(name = "Response")
public class FcCreateHotelOrderResponseResult {
    private String resultFlag;
    private String resultMsg;

    private CreateHotelOrderResponse createHotelOrderResponse;

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

    @XmlElement(name = "CreateHotelOrderResponse")
    public CreateHotelOrderResponse getCreateHotelOrderResponse() {
        return createHotelOrderResponse;
    }

    public void setCreateHotelOrderResponse(CreateHotelOrderResponse createHotelOrderResponse) {
        this.createHotelOrderResponse = createHotelOrderResponse;
    }

    @Override
    public String toString() {
        return "FcCreateHotelOrderResponseResult{" +
                "resultFlag='" + resultFlag + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", createHotelOrderResponse=" + createHotelOrderResponse +
                '}';
    }
}
