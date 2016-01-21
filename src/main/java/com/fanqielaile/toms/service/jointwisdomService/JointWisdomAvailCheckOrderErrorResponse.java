package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 */
@XmlRootElement(name = "OTA_HotelAvailRS", namespace = "http://www.opentravel.org/OTA/2003/05")
public class JointWisdomAvailCheckOrderErrorResponse extends OtaRequest {
    private Error errors;

    @XmlElement(name = "Errors")
    public Error getErrors() {
        return errors;
    }

    public void setErrors(Error errors) {
        this.errors = errors;
    }


}
