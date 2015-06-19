package com.fanqielaile.toms.helper;

import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.dom4j.Element;

/**
 * Created by wangdayin on 2015/6/19.
 * 订单处理方法
 */
public class OrderMethodHelper {
    //得到接口信息中的用户信息
    public static UserInfo getUserNameAndPassword(String xmlStr) throws Exception {
        //根节点
        Element element = XmlDeal.dealXmlStr(xmlStr);
        //得到用户信息节点
        Element authenticationToken = element.element("AuthenticationToken");
        //得到username信息
        String username = authenticationToken.element("Username").getText();
        //得到密码信息
        String password = authenticationToken.element("Password").getText();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username);
        userInfo.setPassword(password);
        return userInfo;
    }

    /**
     * 封装订单对象
     *
     * @param element
     * @return
     */
    public static Order getOrder(Element element) {
        Order order = new Order();
        order.setChannelOrderCode(element.element("TaoBaoOrderId").getText());
        order.setInnId(Integer.parseInt(element.elementText("HotelId")));
        order.setOrderStatus(OrderStatus.ACCEPT);
        return order;
    }
}
