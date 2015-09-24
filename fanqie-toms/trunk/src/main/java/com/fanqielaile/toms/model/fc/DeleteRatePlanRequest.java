package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.*;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/24
 * @version: v2.0.0
 */
@XmlRootElement(name = "Request")
@XmlType(propOrder={"header","deleteRatePlanInfoRequest"})
public class DeleteRatePlanRequest extends FcRequest{
    private Header header;
    private DeleteRatePlanInfoRequest deleteRatePlanInfoRequest;
    @XmlElement(name = "Header")
    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "DeleteRatePlanRequest")
    public DeleteRatePlanInfoRequest getDeleteRatePlanInfoRequest() {
        return deleteRatePlanInfoRequest;
    }

    public void setDeleteRatePlanInfoRequest(DeleteRatePlanInfoRequest deleteRatePlanInfoRequest) {
        this.deleteRatePlanInfoRequest = deleteRatePlanInfoRequest;
    }
}
