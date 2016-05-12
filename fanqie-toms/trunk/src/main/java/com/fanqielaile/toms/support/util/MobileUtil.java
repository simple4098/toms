package com.fanqielaile.toms.support.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;

/**
 * DESC : 电话util
 * @author : xkj
 * @data : 2016/5/3
 * @version: v1.0.0
 */
public class MobileUtil {
	
	/**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByGet (String callurl,String charset) throws IOException{   
        String result = "";   
        URL url = new URL(callurl);   
        URLConnection connection = url.openConnection();   
        connection.connect();   
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));   
        String line;   
        while((line = reader.readLine())!= null){    
            result += line;   
            result += "\n";
        }
        return result;
    }
    
    /**
     * 根据手机号获取归属地信息
     * @param mobile 手机号
     * @return
     */
	public static Map<String, String> getRegion (String mobile) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("1\\d{10}");
		Matcher matcher = pattern.matcher(mobile);
		String url;
		if (matcher.matches()) {
			// 外部接口1
			url = ResourceBundleUtil.getString("mobile.region.url") + mobile;
			Document document;
			String retmsg;
			try {
				String result = callUrlByGet(url, "GBK");
				StringReader stringReader = new StringReader(result);
				InputSource inputSource = new InputSource(stringReader);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				document = documentBuilder.parse(inputSource);
				retmsg = document.getElementsByTagName("retmsg").item(0).getFirstChild().getNodeValue();
			} catch (Exception e) {
				return getRegionTwo(mobile);
			}
			if (retmsg.equals("OK")) {
				String province = document.getElementsByTagName("province").item(0).getFirstChild().getNodeValue()
						.trim();
				String city = document.getElementsByTagName("city").item(0).getFirstChild().getNodeValue().trim();
				if (province.equals("-") || city.equals("-")) {
					return getRegionTwo(mobile);
				} else {
					map.put("province", province);
					map.put("city", city);
					return map;
				}
			} else {
				return getRegionTwo(mobile);
			}
		} else {
			throw new Exception("手机号格式不正确");
		}
	}
	/**
	 * 根据手机号获取归属地信息api2
	 * @param mobile 手机号
	 * @return
	 */
	public static Map<String, String> getRegionTwo (String mobile) throws Exception{
		Map<String,String> map = new HashMap<>();
		String url = ResourceBundleUtil.getString("mobile.region.url.two") + mobile+"&key="+ResourceBundleUtil.getString("mobile.region.key");
		String result = callUrlByGet(url, "UTF-8");
		JSONObject object = JSONObject.parseObject(result);
		if(object.getString("resultcode").equals("200")){
			JSONObject data = object.getJSONObject("result");
			map.put("province", data.getString("province"));
			map.put("city", data.getString("city"));
			return map;
		}else{
			throw new Exception("获取该手机号归属地出错,message:"+object.getString("reason"));
		}
	}
}
