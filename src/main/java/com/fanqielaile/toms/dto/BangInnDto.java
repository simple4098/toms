package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.BangInn;

/**
 * Created by wangdayin on 2015/5/29.
 */
public class BangInnDto extends BangInn {
    //所属管理员
    private String userName;
    //客栈标签
    private String labelName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
