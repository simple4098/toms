package com.fanqielaile.toms.dto;

import java.math.BigDecimal;

/**
 * DESC : 封装订单来源json数据对象
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
public class OrderDto {
    private BigDecimal value;
    private String name;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
