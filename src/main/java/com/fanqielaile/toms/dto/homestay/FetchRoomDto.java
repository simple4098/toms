package com.fanqielaile.toms.dto.homestay;


import java.util.List;

import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.model.homestay.RoomInfo;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class FetchRoomDto extends BaseResultDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FetchRoomDto() {
		super();
	}

	public FetchRoomDto(ResultCode code) {
		super(code);
	}

	public FetchRoomDto(String resultMessage, String resultCode) {
		super(resultMessage, resultCode);
	}

	@ApiModelProperty(value="房源列表信息",required=true)
	private List<RoomInfo> rooms;

    public List<RoomInfo> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomInfo> rooms) {
        this.rooms = rooms;
    }
}
