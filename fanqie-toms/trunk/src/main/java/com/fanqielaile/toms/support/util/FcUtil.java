package com.fanqielaile.toms.support.util;

import com.fanqielaile.toms.model.fc.FcRequest;
import com.fanqielaile.toms.model.fc.GetHotelRequest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * DESC : 房仓对象转化成xml字符串
 * @author : 番茄木-ZLin
 * @data : 2015/9/6
 * @version: v1.0.0
 */
public  class FcUtil<T extends FcRequest> {

    public static   <T>  String  fcRequest(T t) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter fw = new StringWriter();
        marshaller.marshal(t,fw);
        return  fw.toString();
    }
}
