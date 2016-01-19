package com.fanqielaile.toms.support.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;

/**
 * Created by wangdayin on 2016/1/19.
 */
public class JointWisdomInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private SAAJInInterceptor saa = new SAAJInInterceptor();

    public JointWisdomInterceptor() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        SOAPMessage mess = message.getContent(SOAPMessage.class);

        if (mess == null) {

            saa.handleMessage(message);

            mess = message.getContent(SOAPMessage.class);

        }


        SOAPBody body = null;

        try {

            body = mess.getSOAPBody();

        } catch (SOAPException e) {

            e.printStackTrace();

        }

        if (body == null) {

            return;

        }

        try {

            //读取自定义的节点
//            SOAPElement soapElement = body.addChildElement("ser:CheckAvailability");
//            System.out.println("添加节点成功"+soapElement.toString());
//            NodeList nodes = head.getElementsByTagName("tns:spId");

//            NodeList nodepass = head.getElementsByTagName("tns:spPassword");

            //获取节点值，简单认证

            /*if (nodes.item(0).getTextContent().equals("wdw")) {

                if (nodepass.item(0).getTextContent().equals("wdwsb")) {

                    System.out.println("认证成功");

                }

            } else {

                SOAPException soapExc = new SOAPException("认证错误");

                throw new Fault(soapExc);

            }*/


        } catch (Exception e) {

            SOAPException soapExc = new SOAPException("解析传入参数错误");

            throw new Fault(soapExc);

        }
    }
}
