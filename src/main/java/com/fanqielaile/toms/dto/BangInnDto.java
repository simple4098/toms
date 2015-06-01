package com.fanqielaile.toms.dto;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.model.BangInn;

/**
 * Created by wangdayin on 2015/5/29.
 */
public class BangInnDto extends BangInn {
    //所属管理员
    private String userName;
    //客栈标签
    private String labelName;
    //绑定时间格式化
    private String bangDataFormat;

    public String getBangDataFormat() {
        if (getBangDate() != null) {
            return DateUtil.formatDateToString(getBangDate(), "yyyy-MM-dd");
        }
        return bangDataFormat;
    }

    public void setBangDataFormat(String bangDataFormat) {
        this.bangDataFormat = bangDataFormat;
    }

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
