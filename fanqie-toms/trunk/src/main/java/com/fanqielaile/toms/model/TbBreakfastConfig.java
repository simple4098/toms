package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.BreakfastType;

/**
 * DESC : 淘宝含早配置
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public class TbBreakfastConfig extends Domain {
    //公司id
    private String companyId;
    //含早类型
    private BreakfastType breakfastType;
    //含早的值
    private Long breakfastValue;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public BreakfastType getBreakfastType() {
        return breakfastType;
    }

    public void setBreakfastType(BreakfastType breakfastType) {
        this.breakfastType = breakfastType;
    }

    public Long getBreakfastValue() {
        return breakfastValue;
    }

    public void setBreakfastValue(Long breakfastValue) {
        this.breakfastValue = breakfastValue;
    }
}
