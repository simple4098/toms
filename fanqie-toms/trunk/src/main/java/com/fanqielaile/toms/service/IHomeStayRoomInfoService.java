package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.homestay.BookingCheckDto;
import com.fanqielaile.toms.dto.homestay.FetchRoomDto;
import com.fanqielaile.toms.dto.homestay.GetRoomStatusDto;
import com.fanqielaile.toms.model.homestay.bo.BookingCheckBo;
import com.fanqielaile.toms.model.homestay.bo.FetchRoomBo;
import com.fanqielaile.toms.model.homestay.bo.GetRoomStatusBo;

/**
 * 民宿房源服务
* @Description: 
* @author LZQ
* @date 2016年9月6日 下午2:05:38
 */
public interface IHomeStayRoomInfoService {
	/**
	 * 查询房源
	 * @param fetchRoomBo
	 * @return
	 */
	FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo);
	
	/**
	 * 查询房态
	 * @param roomStatusBo
	 * @return
	 */
	GetRoomStatusDto getRoomStatus(GetRoomStatusBo roomStatusBo);

	/**
	 * 可预订检查
	 * @param bookingCheckBo
	 * @return
	 */
	BookingCheckDto bookingCheck(BookingCheckBo bookingCheckBo);
}

