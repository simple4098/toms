package com.fanqielaile.toms.service;

import com.fanqie.jw.request.availCheckOrder.OTA_HotelAvailRQ;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(name = "IJointWisdomCxfService")
public interface IJointWisdomCxfService {
    @WebResult
    @WebMethod
    public String CheckAvailability(@WebParam(name = "OTA_HotelAvailRQ") OTA_HotelAvailRQ hotelAvailRQ) throws Exception;

//    @WebMethod(operationName = "ProcessReservationRequest", action = "urn:HTNG_SeamlessShopAndBookService#ProcessReservationRequest")
//    @WebResult
//    public String ProcessReservationRequest(@WebParam(name = "xml") String xml) throws Exception;
}

