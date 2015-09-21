package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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

    private AddHotelMappingListRequest listRequest;


    @XmlElement(name = "HotelList")
    public AddHotelMappingListRequest getListRequest() {
        return listRequest;
    }

    public void setListRequest(AddHotelMappingListRequest listRequest) {
        this.listRequest = listRequest;
    }


}
