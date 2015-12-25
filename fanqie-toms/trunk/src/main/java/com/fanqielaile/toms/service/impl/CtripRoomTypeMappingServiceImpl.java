package com.fanqielaile.toms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.service.CtripRoomTypeMappingService;

@Service
public class CtripRoomTypeMappingServiceImpl implements  CtripRoomTypeMappingService{

	
	@Resource
	private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;
	
	@Override
	public List<CtripRoomTypeMapping> findRoomTypeMapping(String companyId,
			String childHotelId) {
		return ctripRoomTypeMappingDao.findRoomTypeMapping(companyId, childHotelId);
	}

	


}
