package com.fanqielaile.toms.model.fc;


import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/8/31.
 */
@XmlRootElement(name = "Response")
public class FCcheckRoomAvailResponseResult {

    private String resultFlag;
    private String resultMsg;
    private CheckRoomAvailResponse checkRoomAvailResponse;

    @XmlElement(name = "CheckRoomAvailResponse")
    public CheckRoomAvailResponse getCheckRoomAvailResponse() {
        return checkRoomAvailResponse;
    }

    public void setCheckRoomAvailResponse(CheckRoomAvailResponse checkRoomAvailResponse) {
        this.checkRoomAvailResponse = checkRoomAvailResponse;
    }

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

    @Override
    public String toString() {
        return "FCcheckRoomAvailResponseResult{" +
                "resultFlag='" + resultFlag + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
