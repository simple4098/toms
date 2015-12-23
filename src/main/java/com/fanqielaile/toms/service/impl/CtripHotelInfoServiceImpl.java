package com.fanqielaile.toms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fanqielaile.toms.dao.CtripHotelInfoDao;
import com.fanqielaile.toms.dto.ctrip.CtripHotelInfo;
import com.fanqielaile.toms.service.CtripHotelInfoService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class CtripHotelInfoServiceImpl implements CtripHotelInfoService{

	
    @Resource
	private CtripHotelInfoDao   ctripHotelInfoDao;
	
	@Override
	public void insert(CtripHotelInfo ctripHotelInfo) {
		ctripHotelInfoDao.insert(ctripHotelInfo);
		
	}

	@Override
	public List<CtripHotelInfo> findCtripHotelByName(String name) {
		return ctripHotelInfoDao.findByHotelName(name);
	}

	@Override
	public List<CtripHotelInfo> findCtripHotelByPage(String name,
			PageBounds pageBounds) {
		return ctripHotelInfoDao.findHotelInfoByPage(name, pageBounds);
	}

	@Override
	public CtripHotelInfo findCtripHotelInfoByParentHotelId(String parentHotelId) {
		return ctripHotelInfoDao.findByParentHotelId(parentHotelId);
	}

}
