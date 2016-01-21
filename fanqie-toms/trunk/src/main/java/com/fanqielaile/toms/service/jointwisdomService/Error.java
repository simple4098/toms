package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 */
@XmlRootElement(name = "Errors", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Error extends OtaRequest {
    private String error;

    @XmlElement(name = "Error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
