package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.minsu.FetchRoomDto;
import com.fanqielaile.toms.model.minsu.bo.FetchRoomBo;

/**
 * 民宿房源服务
* @Description: 
* @author LZQ
* @date 2016年9月6日 下午2:05:38
 */
public interface IMinsuRoomInfoService {
	/**
	 * 查询房源
	 * @param fetchRoomBo
	 * @return
	 */
	FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo);
}

