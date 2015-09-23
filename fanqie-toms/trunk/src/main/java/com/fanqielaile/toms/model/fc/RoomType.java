package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "RoomType")
public class RoomType {
    private Long FcRoomTypeId;
    private String FcRoomTypeName;
    private String SpRoomTypeId;
    private String SpRoomTypeName;

    @XmlElement(name = "FcRoomTypeId")
    public Long getFcRoomTypeId() {
        return FcRoomTypeId;
    }

    public void setFcRoomTypeId(Long fcRoomTypeId) {
        FcRoomTypeId = fcRoomTypeId;
    }

    @XmlElement(name = "FcRoomTypeName")
    public String getFcRoomTypeName() {
        return FcRoomTypeName;
    }

    public void setFcRoomTypeName(String fcRoomTypeName) {
        FcRoomTypeName = fcRoomTypeName;
    }

    @XmlElement(name = "SpRoomTypeId")
    public String getSpRoomTypeId() {
        return SpRoomTypeId;
    }

    public void setSpRoomTypeId(String spRoomTypeId) {
        SpRoomTypeId = spRoomTypeId;
    }

    @XmlElement(name = "SpRoomTypeName")
    public String getSpRoomTypeName() {
        return SpRoomTypeName;
    }

    public void setSpRoomTypeName(String spRoomTypeName) {
        SpRoomTypeName = spRoomTypeName;
    }
}
