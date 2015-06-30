package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 客栈标签
 * Created by wangdayin on 2015/5/12.
 */
public class InnLabel extends Domain {
    //标签名称
    @NotBlank(message = "标签名称不能为空")
    private String labelName;
    //排序字段
    private int indexed;
    //所属公司
    private String companyId;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getIndexed() {
        return indexed;
    }

    public void setIndexed(int indexed) {
        this.indexed = indexed;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
