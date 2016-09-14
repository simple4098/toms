package com.fanqielaile.toms.enums;

/**
 * Create by jame
 * Date: 2016/9/6 11:09
 * Version: 1.0
 * Description: 阐述
 * resultCode		int	Y	结果代码: 0.成功；101.其它错误；102.缺少必要参数；103.参数无效；104.系统内部异常;
 */
public enum CtripHomeStayResultCodeEnum {
    SUCCESS(0),
    OTHER_ERROR(101),
    MISSING_PARAM(102),
    PARAM_INVALID(103),
    SYSTEM_ERROR(104);


    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    CtripHomeStayResultCodeEnum(int value) {
        this.value = value;
    }
}
