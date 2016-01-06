package com.fanqielaile.toms.service;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBException;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by wangdayin on 2016/1/4.
 */
@WebService(name = "ICtripOrderCxfService")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED)
public interface ICtripOrderCxfService {
    @WebMethod(operationName = "Invoke", action = "urn:CtripOrderCxfServiceImplwsdl#Invoke")
    @WebResult
    public String Invoke(@WebParam(name = "xml") String xml, @WebParam(name = "Invoketype") String Invoketype) throws Exception;
}
