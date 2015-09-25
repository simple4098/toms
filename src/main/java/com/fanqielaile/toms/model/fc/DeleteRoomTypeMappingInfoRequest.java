package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/25
 * @version: v1.0.0
 */
@XmlRootElement(name = "DeleteRoomTypeMappingRequest")
@XmlType(propOrder={"spHotelId","list"})
public class DeleteRoomTypeMappingInfoRequest {

    private String spHotelId;

    private List<DeleteRoomType> list;

    @XmlElement(name = "SpHotelId")
    public String getSpHotelId() {
        return spHotelId;
    }

    public void setSpHotelId(String spHotelId) {
        this.spHotelId = spHotelId;
    }

    @XmlElement(name = "RoomTypeIdList")
    public List<DeleteRoomType> getList() {
        return list;
    }

    public void setList(List<DeleteRoomType> list) {
        this.list = list;
    }
}
