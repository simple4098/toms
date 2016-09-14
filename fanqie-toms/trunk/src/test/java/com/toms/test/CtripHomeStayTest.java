package com.toms.test;

import com.alibaba.fastjson.JSON;
import com.fanqielaile.toms.vo.ctrip.homestay.SubmitOrderContactsVo;
import com.fanqielaile.toms.vo.ctrip.homestay.SubmitOrderGuestsVo;
import com.fanqielaile.toms.vo.ctrip.homestay.SubmitOrderRequestVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by jame
 * Date: 2016/9/6 17:10
 * Version: 1.0
 * Description: 阐述
 */
public class CtripHomeStayTest {

    public static void main(String[] args) {
        SubmitOrderRequestVo submitOrderParamVo = new SubmitOrderRequestVo();
        submitOrderParamVo.setCheckIn("2016-10-01");
        submitOrderParamVo.setCheckOut("2016-10-02");
        submitOrderParamVo.setRoomId(117250);
        submitOrderParamVo.setCtripOrderId(1111);
        submitOrderParamVo.setQuantity(1);
        submitOrderParamVo.setTotalAmount(20000);
        submitOrderParamVo.setOnlineAmount(10000);
        submitOrderParamVo.setOfflineAmount(10000);

        SubmitOrderContactsVo submitOrderContactsVo = new SubmitOrderContactsVo();
        submitOrderContactsVo.setName("aaa");
        submitOrderContactsVo.setMobile("158");
        submitOrderParamVo.setContacts(submitOrderContactsVo);

        List<SubmitOrderGuestsVo> guestsVoList = new ArrayList<>();
        SubmitOrderGuestsVo submitOrderGuestsVo = new SubmitOrderGuestsVo("jame", "510", 1);
        guestsVoList.add(submitOrderGuestsVo);
        submitOrderParamVo.setGuests(guestsVoList);
        System.out.println(JSON.toJSONString(submitOrderParamVo));
    }
}
