package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.*;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "Request")
@XmlType(propOrder = {"header","addHotelMappingRequest"})
public class AddHotelRequest extends FcRequest {

    private Header header;

    private AddHotelMappingRequest addHotelMappingRequest;

    public AddHotelRequest() {
    }

    public AddHotelRequest(Header header, AddHotelMappingRequest baseRequest) {
        this.header = header;
        this.addHotelMappingRequest = baseRequest;
    }

    @XmlElement(name = "Header")
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "AddHotelMappingRequest")
    public AddHotelMappingRequest getAddHotelMappingRequest() {
        return addHotelMappingRequest;
    }

    public void setAddHotelMappingRequest(AddHotelMappingRequest addHotelMappingRequest) {
        this.addHotelMappingRequest = addHotelMappingRequest;
    }
}
