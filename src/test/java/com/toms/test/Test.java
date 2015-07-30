package com.toms.test;


import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.support.util.XmlDeal;

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

        long begin = System.currentTimeMillis();
        List<PushRoom> pushRoom = XmlDeal.getPushRoom(text);
        long after = System.currentTimeMillis();
        System.out.println("DOM用时"+(after-begin)+"毫秒");
    }
}
