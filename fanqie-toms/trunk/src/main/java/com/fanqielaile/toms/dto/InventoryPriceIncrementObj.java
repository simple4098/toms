package com.fanqielaile.toms.dto;

/**
 * DESC : 淘宝价格增量对象
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class InventoryPriceIncrementObj{
    private String out_rid;
    private String rateplan_code;
    private InventoryPrice data;

    public InventoryPrice getData() {
        return data;
    }

    public void setData(InventoryPrice data) {
        this.data = data;
    }

    public String getOut_rid() {
        return out_rid;
    }

    public void setOut_rid(String out_rid) {
        this.out_rid = out_rid;
    }

    public String getRateplan_code() {
        return rateplan_code;
    }

    public void setRateplan_code(String rateplan_code) {
        this.rateplan_code = rateplan_code;
    }
}
