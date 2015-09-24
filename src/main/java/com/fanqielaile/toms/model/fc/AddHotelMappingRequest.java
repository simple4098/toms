package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */


public class AddHotelMappingRequest  {

    private List<HotelInfo> HotelList;

    @XmlElementWrapper(name = "HotelList")
    @XmlElement(name = "HotelInfo")
    public List<HotelInfo> getHotelList() {
        return HotelList;
    }

    public void setHotelList(List<HotelInfo> hotelList) {
        HotelList = hotelList;
    }


}
