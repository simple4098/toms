package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "Request")
public class AddRoomTypeRequest  extends FcRequest {
    @XmlElement(name = "Header")
    public Header header;
   @XmlElement(name = "AddRoomTypeMappingRequest")
    public AddRoomTypeMappingRequest addRoomTypeMappingRequest;

    public AddRoomTypeRequest() {
    }

    public AddRoomTypeRequest(Header header, AddRoomTypeMappingRequest addRoomTypeMappingRequest) {
        this.header = header;
        this.addRoomTypeMappingRequest = addRoomTypeMappingRequest;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setAddRoomTypeMappingRequest(AddRoomTypeMappingRequest addRoomTypeMappingRequest) {
        this.addRoomTypeMappingRequest = addRoomTypeMappingRequest;
    }
}
