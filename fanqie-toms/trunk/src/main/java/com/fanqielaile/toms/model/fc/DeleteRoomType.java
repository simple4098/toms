package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/25
 * @version: v1.0.0
 */
public class DeleteRoomType {

    private String   fcRoomTypeId;

    @XmlElement(name = "SpRoomTypeId")
    public String getFcRoomTypeId() {
        return fcRoomTypeId;
    }

    public void setFcRoomTypeId(String fcRoomTypeId) {
        this.fcRoomTypeId = fcRoomTypeId;
    }
}
