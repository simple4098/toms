package com.fanqielaile.toms.support.util;


import com.fanqie.bean.response.*;
import com.fanqie.support.OtaRequest;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC : 房仓对象转化成xml字符串
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/6
 * @version: v1.0.0
 */
public class FcUtil<T extends OtaRequest> {

    private static final Logger log = LoggerFactory.getLogger(FcUtil.class);
    public static <T> String fcRequest(T t) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(t.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        StringWriter fw = new StringWriter();
        marshaller.marshal(t, fw);
        return fw.toString();
    }

    /**
     * 响应xml转化成 RequestResponse
     * @param xml 响应xml
     */
    public static RequestResponse xMLStringToBean(String xml) throws JAXBException {
        if (!StringUtils.isEmpty(xml)) {
                try {
                    RequestResponse response1 = new RequestResponse();
                    Element element = XmlDeal.dealXmlStr(xml);
                    String message = element.element("RequestResult").element("Message").getText();
                    String resultCode = element.element("RequestResult").element("ResultCode").getText();
                    Element responseElement1 = element.element("RequestResult").element("Response");
                    Response response = new Response();
                    if (responseElement1!=null){
                        Element interfaceRoomTypeListResponse = responseElement1.element("HotelGroupInterfaceRoomTypeListResponse");
                        if (interfaceRoomTypeListResponse!=null){
                            List<Element> elements = interfaceRoomTypeListResponse.element("HotelGroupInterfaceRoomTypeList").elements("HotelGroupInterfaceRoomTypeEntity");
                            if (elements!=null){
                                HotelGroupInterfaceRoomTypeListResponse roomTypeListResponse = new HotelGroupInterfaceRoomTypeListResponse();
                                HotelGroupInterfaceRoomTypeEntity entity = null;
                                List<HotelGroupInterfaceRoomTypeEntity> HotelGroupInterfaceRoomTypeList = new ArrayList<>();
                                for (Element e:elements){
                                    entity = new HotelGroupInterfaceRoomTypeEntity();
                                    entity.setHotelGroupRoomTypeCode(e.element("HotelGroupRoomTypeCode").getText());;
                                    entity.setHotel(e.element("Hotel").getText());
                                    entity.setRoom(e.element("Room").getText());
                                    HotelGroupInterfaceRoomTypeList.add(entity);
                                }
                                roomTypeListResponse.setHotelGroupInterfaceRoomTypeList(HotelGroupInterfaceRoomTypeList);
                                response.setHotelGroupInterfaceRoomTypeListResponse(roomTypeListResponse);

                            }else {
                                log.info("xml 异常");
                            }
                        }else {
                            log.info("HotelGroupInterfaceRoomTypeListResponse 为空");
                        }
                    }
                    RequestResult requestResult = new RequestResult();
                    requestResult.setMessage(message);
                    requestResult.setResultCode(resultCode!=null?Integer.valueOf(resultCode):-1);
                    requestResult.setResponse(response);
                    response1.setRequestResult(requestResult);
                    return response1;
                } catch (Exception e) {
                    log.error("解析xml异常:",e);
                }

        }
            /*JAXBContext context = JAXBContext.newInstance(RequestResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            RequestResponse response = (RequestResponse) unmarshaller.unmarshal(new StringReader(xml));
            return response;*/
        return null;
    }
}
