package com.fanqielaile.toms.dto;

/**
 * DESC :库存房间某一天的状态
 * @author : 番茄木-ZLin
 * @data : 2015/6/29
 * @version: v1.0.0
 */
public class RateSwitchCal {
    private  String date;
    private  int rate_status;

    public RateSwitchCal(String date, int rate_status) {
        this.date = date;
        this.rate_status = rate_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRate_status() {
        return rate_status;
    }

    public void setRate_status(int rate_status) {
        this.rate_status = rate_status;
    }
}
