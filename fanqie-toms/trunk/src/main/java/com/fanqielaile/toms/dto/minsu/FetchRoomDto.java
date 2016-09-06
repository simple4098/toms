package com.fanqielaile.toms.dto.minsu;


import java.util.List;

import com.fanqielaile.toms.model.minsu.RoomInfo;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class FetchRoomDto extends BaseResultDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="房源列表信息",required=true)
	private List<RoomInfo> rooms;

    public List<RoomInfo> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomInfo> rooms) {
        this.rooms = rooms;
    }
}
