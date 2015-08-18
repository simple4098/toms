package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "Request")
public class AddHotelRequest {
    @XmlElement(name = "Header")
    public Header header;
   @XmlElement(name = "AddHotelMappingRequest")
    public AddHotelMappingRequest addHotelMappingRequest;

    public AddHotelRequest() {
    }

    public AddHotelRequest(Header header, AddHotelMappingRequest baseRequest) {
        this.header = header;
        this.addHotelMappingRequest = baseRequest;
    }

    /*public Header getHeader() {
        return header;
    }*/

    public void setHeader(Header header) {
        this.header = header;
    }

   /* public AddHotelMappingRequest getAddHotelMappingRequest() {
        return addHotelMappingRequest;
    }*/

    public void setAddHotelMappingRequest(AddHotelMappingRequest addHotelMappingRequest) {
        this.addHotelMappingRequest = addHotelMappingRequest;
    }
}
