package com.toms.test;


import com.fanqie.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

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
        String test = "null8005,6354,13901,27050,8373,5913,5912,6352,22144,22753,24128,25796,26902,";
        Integer i = 3457;
        System.out.println(test.contains(i.toString()));
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
