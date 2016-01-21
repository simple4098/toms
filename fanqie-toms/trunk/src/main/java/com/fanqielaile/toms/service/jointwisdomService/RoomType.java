package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 房型
 */
@XmlRootElement(name = "RoomType", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomType {
    private String numberOfUnits;//房型库存是否满足需求，strng，可传实际剩余库存或true、false
    private String roomTypeCode;//房型代码
    private RoomDescription description;

    @XmlElement(name = "RoomDescription")
    public RoomDescription getDescription() {
        return description;
    }

    public void setDescription(RoomDescription description) {
        this.description = description;
    }

    @XmlAttribute(name = "NumberOfUnits")
    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    @XmlAttribute(name = "RoomTypeCode")
    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }
}
