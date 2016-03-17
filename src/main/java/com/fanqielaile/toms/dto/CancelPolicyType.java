package com.fanqielaile.toms.dto;

/**
 * DESC : 价格计划众取消类型
 * @author : 番茄木-zhangLin
 * @data : 2016/3/15
 * @version: v1.0.0
 */
public class CancelPolicyType {

    //取消类型 0 随意取消 1 不能取消 2
    private Integer cancelPolicyType;
    //从入住当天24点往前推算，提前多少小时能取消
    private Integer timeBefore;
    public Integer getCancelPolicyType() {
        return cancelPolicyType;
    }
    public void setCancelPolicyType(Integer cancelPolicyType) {
        this.cancelPolicyType = cancelPolicyType;
    }
    public Integer getTimeBefore() {
        return timeBefore;
    }
    public void setTimeBefore(int timeBefore) {
        this.timeBefore = timeBefore;
    }
}
