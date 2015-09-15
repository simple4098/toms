package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.enums.BreakfastType;
import com.fanqielaile.toms.enums.FcBreakFastType;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangdayin on 2015/6/19.
 * 每日价格信息
 */
public class DailyInfos extends Domain {
    //时间
    private Date day;
    //价格
    private BigDecimal price;
    //订单ID
    private String orderId;
    //售价
    private BigDecimal costPrice;
    //时间展示
    private String dayDesc;
    //早餐类型（可选）
    private FcBreakFastType breakfastType;
    //早餐数量
    private Integer breakfastNum;

    public FcBreakFastType getBreakfastType() {
        return breakfastType;
    }

    public void setBreakfastType(FcBreakFastType breakfastType) {
        this.breakfastType = breakfastType;
    }

    public Integer getBreakfastNum() {
        return breakfastNum;
    }

    public void setBreakfastNum(Integer breakfastNum) {
        this.breakfastNum = breakfastNum;
    }

    public String getDayDesc() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDay());
        return DateUtil.formatDateToString(getDay(), "yyyy-MM-dd") + " " + DcUtil.dayOfWeek(new DateTime(getDay()).getDayOfWeek());
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
