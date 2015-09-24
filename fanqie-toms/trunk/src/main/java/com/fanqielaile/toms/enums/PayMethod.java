package com.fanqielaile.toms.enums;

/**
 * DESC :房仓支付类型
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public enum  PayMethod {
    pay("面付","1"),pre_pay("预付","2");
    private String value;
    private String desc;

    PayMethod(String desc,String value) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
