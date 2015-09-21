package com.fanqielaile.toms.model.fc;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/17
 * @version: v1.0.0
 */
public class Response {

    private String ResultCode;
    private String ResultNo;
    private String ResultMsg;

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultNo() {
        return ResultNo;
    }

    public void setResultNo(String resultNo) {
        ResultNo = resultNo;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }
}
