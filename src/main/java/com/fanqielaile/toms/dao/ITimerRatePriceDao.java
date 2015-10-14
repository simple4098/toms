package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.TimerRatePrice;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/10/13
 * @version: v1.0.0
 */
public interface ITimerRatePriceDao {

    void  saveTimerRatePrice(TimerRatePrice timerRatePrice);

    List<TimerRatePrice> selectTimerRatePrice(TimerRatePrice timerRatePrice);

    void  deletedTimerRatePrice(TimerRatePrice timerRatePrice);
}
