package com.toms.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.osm.mobile.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * DESC :
 *
 * @author : xkj
 * @data : 2016/4/29
 * @version: v5.0.0
 */
public class MobileRegionTest {
	/**
     * 归属地查询
     * @param mobile
     * @return mobileAddress
     */
    @SuppressWarnings("unused")
//    private static String getLocationByMobile(final String mobile) throws ParserConfigurationException, SAXException, IOException{ 
//        String MOBILEURL = "http://www.youdao.com/smartresult-xml/search.s?type=mobile&q="; 
//        String result = callUrlByGet(MOBILEURL + mobile, "GBK");
//        StringReader stringReader = new StringReader(result); 
//        InputSource inputSource = new InputSource(stringReader); 
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
//        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
//        Document document = documentBuilder.parse(inputSource);
// 
//        if (!(document.getElementsByTagName("location").item(0) == null)) {
//            return document.getElementsByTagName("location").item(0).getFirstChild().getNodeValue();
//        }else{
//            return "无此号记录！";
//        }
//    }
    /**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByGet(String callurl,String charset){   
        String result = "";   
        try {   
            URL url = new URL(callurl);   
            URLConnection connection = url.openConnection();   
            connection.connect();   
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));   
            String line;   
            while((line = reader.readLine())!= null){    
                result += line;   
                result += "\n";
            }
        } catch (Exception e) {   
            e.printStackTrace();   
            return "";
        }
        return result;
    }
	@Test
	public void getRegion () throws Exception{
		 Pattern pattern = Pattern.compile("1\\d{10}");
	        Matcher matcher = pattern.matcher("13888888888");
	        if(matcher.matches()){
	            String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=" + "13888888888";
	            String result = callUrlByGet(url,"GBK");
	            StringReader stringReader = new StringReader(result); 
	            InputSource inputSource = new InputSource(stringReader); 
	            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
	            Document document = documentBuilder.parse(inputSource);
	            String retmsg = document.getElementsByTagName("retmsg").item(0).getFirstChild().getNodeValue();
	            if(retmsg.equals("OK")){
	                String supplier = document.getElementsByTagName("supplier").item(0).getFirstChild().getNodeValue().trim();
	                String province = document.getElementsByTagName("province").item(0).getFirstChild().getNodeValue().trim();
	                String city = document.getElementsByTagName("city").item(0).getFirstChild().getNodeValue().trim();
	                if (province.equals("-") || city.equals("-")) {

//	                    return (tel + "," + supplier + ","+ getLocationByMobile(tel));
//	                	System.out.println(getLocationByMobile("13888888888") + "," + supplier);
	                }else {

//	                    return (tel + "," + supplier + ","+ province + city);
	                	System.out.println(province + city + "," + supplier );
	                }

	            }else {
	            	System.out.println("无此号记录！");

	            }

	        }else{
	        	System.out.println("手机号码格式错误！");

	        }
	}
}
