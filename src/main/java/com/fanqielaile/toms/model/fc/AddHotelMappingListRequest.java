package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/17
 * @version: v1.0.0
 */
@XmlRootElement(name = "HotelList")
public class AddHotelMappingListRequest {
    private List<HotelInfo> HotelList;

    @XmlElement(name = "HotelInfo")
    public List<HotelInfo> getHotelList() {
        return HotelList;
    }

    public void setHotelList(List<HotelInfo> hotelList) {
        HotelList = hotelList;
    }
}
