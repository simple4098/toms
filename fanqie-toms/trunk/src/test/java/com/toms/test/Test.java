package com.toms.test;

import com.fanqielaile.toms.enums.ChannelSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Random;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class Test {
    public static void main(String[] args) throws DocumentException {
//        System.out.println(UUID.randomUUID());
        String xmlstr = "<BookRQ><AuthenticationToken><Username>taobao</Username><Password>taobao</Password></AuthenticationToken></BookRQ>";
        Document document = DocumentHelper.parseText(xmlstr);
        Element element = document.getRootElement();
        System.out.println(element.getName());
        Element authenticationToken = element.element("AuthenticationToken");
        String username = authenticationToken.elementText("Username");
        System.out.println(username);
    }
}
