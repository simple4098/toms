package com.fanqielaile.toms.service;

import com.fanqie.jw.dto.RoomPrice;
import com.fanqie.jw.dto.Inventory;

/**
 *   众荟 API 相关操作
 */
public interface JointWisdomARIService {

    //CurrencyCode:默认传CNY
    String CURRENCY_CODE = "CNY";

    // AgeQualifyingCode:10 成人
    String AGE_QUALIFYING_CODE = "10";

    //Definitive,默认值
    String COUNT_TYPE = "2";

    /**
     *  推送房价
     * @param roomPrice
     *            房价关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */

    void  pushRoomPrice(RoomPrice roomPrice)  throws Exception ;


    /**
     *  推送库存
     * @param inventory
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    void  pushRoomInventory(Inventory inventory) throws Exception;


}
