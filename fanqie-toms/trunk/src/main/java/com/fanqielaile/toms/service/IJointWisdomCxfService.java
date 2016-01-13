package com.fanqielaile.toms.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(name = "IJointWisdomCxfService")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED)
public interface IJointWisdomCxfService {
    @WebMethod(operationName = "CheckAvailability", action = "urn:HTNG_SeamlessShopAndBookService#CheckAvailability")
    @WebResult
    public String CheckAvailability(@WebParam(name = "xml") String xml) throws Exception;

    @WebMethod(operationName = "ProcessReservationRequest", action = "urn:HTNG_SeamlessShopAndBookService#ProcessReservationRequest")
    @WebResult
    public String ProcessReservationRequest(@WebParam(name = "xml") String xml) throws Exception;
}

