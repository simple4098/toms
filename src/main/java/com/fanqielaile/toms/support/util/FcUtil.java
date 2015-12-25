package com.fanqielaile.toms.support.util;


import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.OtaRequest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * DESC : 房仓对象转化成xml字符串
 * @author : 番茄木-ZLin
 * @data : 2015/9/6
 * @version: v1.0.0
 */
public  class FcUtil<T extends OtaRequest> {

    public static   <T>  String  fcRequest(T t) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xm头声明信
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        StringWriter fw = new StringWriter();
        marshaller.marshal(t,fw);
        return  fw.toString();
    }

    /**
     * 响应xml转化成 RequestResponse
     * @param xml 响应xml
     */
    public static RequestResponse xMLStringToBean(String xml){
        try {
            JAXBContext context = JAXBContext.newInstance(RequestResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            RequestResponse response = (RequestResponse)unmarshaller.unmarshal(new StringReader(xml));
            return  response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
