package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DESC : 酒店基本信息查询接口
 * @author : 番茄木-ZLin
 * @data : 2015/9/2
 * @version: v1.0.0
 */
@XmlRootElement(name = "GetHotelInfoRequest")
public class GetHotelInfoRequest {

    public String  FcHotelIds;

    @XmlElement(name = "FcHotelIds")
    public String getFcHotelIds() {
        return FcHotelIds;
    }

    public void setFcHotelIds(String fcHotelIds) {
        FcHotelIds = fcHotelIds;
    }
}
