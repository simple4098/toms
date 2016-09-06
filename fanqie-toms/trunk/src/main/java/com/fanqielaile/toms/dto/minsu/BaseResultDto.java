package com.fanqielaile.toms.dto.minsu;

import java.io.Serializable;

import com.fanqielaile.toms.enums.ResultCode;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/9/2.
 */
public class BaseResultDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="错误信息状态码",required=true)
	private String resultCode;
	@ApiModelProperty(value="错误信息描述",required=true)
    private String resultMessage;
    
    public BaseResultDto(String resultMessage, String resultCode) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
    }
    public BaseResultDto(ResultCode code) {
        this.resultMessage = code.getMessage();
        this.resultCode = code.getCode();
    }
    public BaseResultDto() {
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
