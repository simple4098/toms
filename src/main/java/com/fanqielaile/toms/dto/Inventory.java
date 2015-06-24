package com.fanqielaile.toms.dto;

import java.util.Date;

/**
 * DESC : 房型库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class Inventory {
    private String date;

    private int quota;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}
