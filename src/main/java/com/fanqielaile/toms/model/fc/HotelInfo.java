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
@XmlRootElement(name = "HotelInfo")
public class HotelInfo {
    //房仓酒店id
    private Integer FcHotelId;
    //房仓酒店名称
    private String FcHotelName;
    //供应商酒店id
    private String SpHotelId;

    @XmlElement(name = "FcHotelId")
    public Integer getFcHotelId() {
        return FcHotelId;
    }

    public void setFcHotelId(Integer fcHotelId) {
        FcHotelId = fcHotelId;
    }

    @XmlElement(name = "FcHotelName")
    public String getFcHotelName() {
        return FcHotelName;
    }

    public void setFcHotelName(String fcHotelName) {
        FcHotelName = fcHotelName;
    }

    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return SpHotelId;
    }

    public void setSpHotelId(String spHotelId) {
        SpHotelId = spHotelId;
    }
}
