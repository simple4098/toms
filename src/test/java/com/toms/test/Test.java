package com.toms.test;

import com.fanqielaile.toms.enums.ChannelSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class Test {
    public static void main(String[] args) throws DocumentException {
//        System.out.println(UUID.randomUUID());
//        String xmlstr = "<BookRQ><DailyInfos><DailyInfo><Day>2013-12-24</Day><Price>17800</Price></DailyInfo><DailyInfo><Day>2013-12-25</Day><Price>46050</Price></DailyInfo></DailyInfos></BookRQ>";
//        Document document = DocumentHelper.parseText(xmlstr);
//        Element element = document.getRootElement();
//        System.out.println(element.getName());
//        List<Element> authenticationToken = element.element("DailyInfos").elements("DailyInfo");
//        for (Element e : authenticationToken) {
//            System.out.println(e.element("Day").getText());
//        }
        Date date = new Date();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        System.out.println(date.getTime());
    }
}
