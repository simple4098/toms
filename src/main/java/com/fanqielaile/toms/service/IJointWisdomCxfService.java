package com.fanqielaile.toms.service;

import com.fanqie.jw.request.availCheckOrder.OTAHotelAvailRQ;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface IJointWisdomCxfService {
    @WebResult
    @WebMethod(operationName = "CheckAvailability", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#CheckAvailability")
    public String CheckAvailability(@WebParam(name = "OTA_HotelAvailRQ", partName = "OTA_HotelAvailRQ") OTAHotelAvailRQ hotelAvailRQ) throws Exception;

//    @WebMethod(operationName = "ProcessReservationRequest", action = "urn:HTNG_SeamlessShopAndBookService#ProcessReservationRequest")
//    @WebResult
//    public String ProcessReservationRequest(@WebParam(name = "xml") String xml) throws Exception;
}

