package com.fanqielaile.toms.service.jointwisdomService;


import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.ResponseWrapper;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(name = "IJointWisdomCxfService", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface IJointWisdomCxfService {
    @WebResult(name = "OTA_HotelAvailRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
    @WebMethod(operationName = "CheckAvailability", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#CheckAvailability")
    public JointWisdomAvailCheckOrderSuccessResponse CheckAvailability(@WebParam(name = "OTA_HotelAvailRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05", partName = "OTA_HotelAvailRQ") OTAHotelAvailRQ hotelAvailRQ) throws Exception;

    @WebResult(name = "OTA_HotelResRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
    @WebMethod(operationName = "ProcessReservationRequest", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#ProcessReservationRequest")
    public JointWisdomAddOrderSuccessResponse ProcessReservationRequest(@WebParam(name = "OTA_HotelResRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05") OTAHotelResRQ otaHotelResRQ) throws Exception;
}

