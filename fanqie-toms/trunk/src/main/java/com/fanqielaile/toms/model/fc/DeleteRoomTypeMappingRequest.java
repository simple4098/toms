package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/25
 * @version: v1.0.0
 */
@XmlRootElement(name = "Request")
@XmlType(propOrder = {"header","deleteRoomTypeMappingInfoRequest"})
public class DeleteRoomTypeMappingRequest extends FcRequest {
    private Header header;

    private DeleteRoomTypeMappingInfoRequest deleteRoomTypeMappingInfoRequest;

    public DeleteRoomTypeMappingRequest() {
    }

    public DeleteRoomTypeMappingRequest(Header header, DeleteRoomTypeMappingInfoRequest deleteRoomTypeMappingInfoRequest) {
        this.header = header;
        this.deleteRoomTypeMappingInfoRequest = deleteRoomTypeMappingInfoRequest;
    }

    @XmlElement(name = "Header")
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "DeleteRoomTypeMappingRequest")
    public DeleteRoomTypeMappingInfoRequest getDeleteRoomTypeMappingInfoRequest() {
        return deleteRoomTypeMappingInfoRequest;
    }

    public void setDeleteRoomTypeMappingInfoRequest(DeleteRoomTypeMappingInfoRequest deleteRoomTypeMappingInfoRequest) {
        this.deleteRoomTypeMappingInfoRequest = deleteRoomTypeMappingInfoRequest;
    }
}
