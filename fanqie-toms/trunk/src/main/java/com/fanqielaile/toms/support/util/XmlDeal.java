package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.model.Order;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.math.BigDecimal;

/**
 * Created by wangdayin on 2015/6/19.
 * xml解析
 */
public class XmlDeal {
    /**
     * 解析xml信息
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Element dealXmlStr(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
        return element;
    }

    /**
     * 得到xml信息的根节点名称
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static String getRootElementString(String xmlStr) throws Exception {
        Document document = DocumentHelper.parseText(xmlStr);
        //得到跟节点信息
        Element element = document.getRootElement();
        return element.getName();
    }

    /**
     * 解析xml信息得到order对象
     *
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Order getOrder(String xmlStr) throws Exception {
        Order order = new Order();
        Element element = dealXmlStr(xmlStr);
        order.setId(element.elementText("OrderId"));
        //获取到淘宝订单号
        String taoBaoOrderId = element.elementText("TaoBaoOrderId");
        order.setChannelOrderCode(taoBaoOrderId);
        //获取支付宝交易号
        String alipayTradeNo = element.elementText("AlipayTradeNo");
        order.setAlipayTradeNo(alipayTradeNo);
        //支付金额
        BigDecimal payment = element.elementText("Payment") == null ? new BigDecimal(0) : new BigDecimal(element.elementText("Payment")).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        order.setPayment(payment);
        //取消订单原因
        order.setReason(element.elementText("Reason"));
        return order;
    }
}
