package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "Request")
@XmlType(propOrder = {"header","deleteHotelInfoRequest"})
public class DeleteHotelMappingRequest extends FcRequest {

    private Header header;

    private DeleteHotelInfoRequest deleteHotelInfoRequest;

    public DeleteHotelMappingRequest() {
    }

    public DeleteHotelMappingRequest(Header header, DeleteHotelInfoRequest baseRequest) {
        this.header = header;
        this.deleteHotelInfoRequest = baseRequest;
    }

    @XmlElement(name = "Header")
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "DeleteHotelMappingRequest")
    public DeleteHotelInfoRequest getDeleteHotelInfoRequest() {
        return deleteHotelInfoRequest;
    }

    public void setDeleteHotelInfoRequest(DeleteHotelInfoRequest deleteHotelInfoRequest) {
        this.deleteHotelInfoRequest = deleteHotelInfoRequest;
    }
}
