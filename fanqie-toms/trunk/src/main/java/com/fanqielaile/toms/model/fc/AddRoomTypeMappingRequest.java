package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * DESC :房型映射
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
/*@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"FcHotelId","SpHotelId","RoomTypeList"})*/
@XmlRootElement(name = "AddRoomTypeMappingRequest")
public class AddRoomTypeMappingRequest {
    private  Integer FcHotelId;
    private  String SpHotelId;
    private AddRoomTypeMappingListRequest RoomTypeList;
    @XmlElement(name = "FcHotelId")
    public Integer getFcHotelId() {
        return FcHotelId;
    }

    public void setFcHotelId(Integer fcHotelId) {
        FcHotelId = fcHotelId;
    }

    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return SpHotelId;
    }

    public void setSpHotelId(String spHotelId) {
        SpHotelId = spHotelId;
    }

    @XmlElement(name = "RoomTypeList")
    public AddRoomTypeMappingListRequest getRoomTypeList() {
        return RoomTypeList;
    }

    public void setRoomTypeList(AddRoomTypeMappingListRequest roomTypeList) {
        RoomTypeList = roomTypeList;
    }
}
