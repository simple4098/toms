package com.fanqielaile.toms.dto;


/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/7/6
 * @version: v1.0.0
 */
public class PricePattern {
    private Integer  accountId;
    // 1:底价模式 2:卖价模式
    private Integer  pattern;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }
}
