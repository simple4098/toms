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
@XmlAccessorType(XmlAccessType.FIELD)*/
@XmlRootElement(name = "AddRoomTypeMappingRequest")
@XmlType(propOrder={"fcHotelId","spHotelId","roomTypeList"})
public class AddRoomTypeMappingRequest {
    private  Integer fcHotelId;
    private  String spHotelId;
    private List<RoomType> roomTypeList;
    @XmlElement(name = "FcHotelId")
    public Integer getFcHotelId() {
        return fcHotelId;
    }

    public void setFcHotelId(Integer fcHotelId) {
        this.fcHotelId = fcHotelId;
    }

    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return spHotelId;
    }

    public void setSpHotelId(String fpHotelId) {
        this.spHotelId = fpHotelId;
    }

    @XmlElementWrapper(name = "RoomTypeList")
    @XmlElement(name = "RoomType")
    public List<RoomType> getRoomTypeList() {
        return roomTypeList;
    }

    public void setRoomTypeList(List<RoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }
}
