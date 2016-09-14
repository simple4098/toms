package com.fanqielaile.toms.vo.oms;

/**
 * Create by jame
 * Date: 2016/9/7 10:24
 * Version: 1.0
 * Description: oms接口接口returnBase
 */
public class ReturnBaseVo {
    private String message;
    private Integer status;
    private String type;

    public ReturnBaseVo() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
