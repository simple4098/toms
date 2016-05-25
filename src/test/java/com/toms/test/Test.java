package com.toms.test;


import com.alibaba.fastjson.JSONObject;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.TomsUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class Test {
    public static void main(String[] args) throws Exception {
       /* Random r=new Random();
        int nextInt = r.nextInt(90000);
        int i=nextInt+10000;
        System.out.println(i);
        System.out.println(nextInt);*/

        /*CurrencyCode[] values = CurrencyCode.values();
        for (CurrencyCode c:values){
            System.out.println(c.name()+"-"+c.getValue());
        }*/
//        System.out.println(getMounthFirstDay("2015-11-10").toString());
//        System.out.println(getLastDay("2015-11-10"));
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <FcRequest>    <Header TimeStamp=\"2014-02-16 15:36:48\" PartnerCode=\"F01202154\"  RequestType= \"cancelHotelOrder\" Signature=\"484D30CBF4F167CBC803BF5A6AAEF1A8\"/>    <CancelHotelOrderRequest>       <SpOrderId>77918027-79fc-420a-ac21-4e4ca6654d44</SpOrderId>       <CancelReason>退改申请原因</CancelReason>    </CancelHotelOrderRequest> </FcRequest>";
//        Element element = XmlDeal.dealXmlStr(xml);
//        Element header = element.element("Header");
//        System.out.println(Calendar.getInstance().getTime());
//        Order order = new Order();
//        order.setId("1605860805110771");
//        OtaInfoRefDto otaInfoRefDto = new OtaInfoRefDto();
//        otaInfoRefDto.setAppKey("23192376");
//        otaInfoRefDto.setAppSecret("c2e9acffbdf281c93b167601781cd228");
//        otaInfoRefDto.setSessionKey("6101d17e6a86e1e2ad2dea0c013764ef01cc94f1eaace162555889376");
//        String xml = TBXHotelUtil.searchOrderStatus(order, otaInfoRefDto);
//        JSONObject jsonObject = JSONObject.parseObject(xml);
//        JSONObject object = jsonObject.getJSONObject("xhotel_order_search_response");
//        System.out.println(xml);
    }

    /**
     * 获取上一月的最后一天和开始一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getMounthFirstDay(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(date, "yyyy-MM-dd"));
        calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 当月第一天
     *
     * @return
     */
    private static String getFirstDay(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(date, "yyyy-MM-dd"));
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        return str.toString();

    }

    /**
     * 当月最后一天
     *
     * @return
     */
    private static String getLastDay(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(date, "yyyy-MM-dd"));
        Date theDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();
        return day_last;
    }
}
