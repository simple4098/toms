package com.fanqielaile.toms.dto;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/15
 * @version: v1.0.0
 */
public class RatePlanConfig {
    // 早餐份数
    private Integer breakfastCount;
    //担保类型，只支持： 0 无担保 1 全天首晚担保 2 全天全额担保
    private Integer guaranteeType;
    //退订政策
    private CancelPolicyType cancelPolicy;

    public Integer getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(Integer breakfastCount) {
        this.breakfastCount = breakfastCount;
    }

    public Integer getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(Integer guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public CancelPolicyType getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(CancelPolicyType cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }
}
