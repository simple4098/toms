package com.toms.test;


import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String text ="<PushRoomType><list><RoomType><AccountId>123</AccountId><RoomTypeId>456</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2013-01-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail><RoomDetail><RoomDate>2014-01-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail></RoomDetails></RoomType><RoomType><AccountId>123</AccountId><RoomTypeId>456</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2013-01-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail><RoomDetail><RoomDate>2014-01-01</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail></RoomDetails></RoomType></list></PushRoomType>";
       /* Element element = XmlDeal.dealXmlStr(text);
        System.out.println(element.getName());
        String rootElementName = XmlDeal.getRootElementString(text);
        System.out.println(rootElementName);*/

//        long begin = System.currentTimeMillis();
//        List<PushRoom> pushRoom = XmlDeal.getPushRoom(text);
//        long after = System.currentTimeMillis();
//        System.out.println("DOM用时"+(after-begin)+"毫秒");

        /*String[] weekDaysName = {"日", "一", "二", "三", "四", "五", "六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(DcUtil.dayOfWeek(intWeek));*/
        List<String> dateList = new ArrayList<String>();
        dateList.add("2015-05-06");
        dateList.add("2015-06-06");
        dateList.add("2015-07-06");
//        String string = TomsUtil.listDateToStr(dateList);
//        System.out.println(string);
    }
}
