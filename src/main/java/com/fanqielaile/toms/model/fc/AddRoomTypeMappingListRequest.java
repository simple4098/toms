package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * DESC :房型映射
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
/*@XmlRootElement(name = "RoomTypeList")*/
public class AddRoomTypeMappingListRequest {

    private List<RoomType> RoomTypeList;

    @XmlElement(name = "RoomType")
    public List<RoomType> getRoomTypeList() {
        return RoomTypeList;
    }

    public void setRoomTypeList(List<RoomType> roomTypeList) {
        RoomTypeList = roomTypeList;
    }
}
