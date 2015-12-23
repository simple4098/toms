package com.fanqielaile.toms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fanqie.ctrip.custom.handler.SetMappingInfoByChildHandler;
import com.fanqie.ctrip.custom.request.SetMappingInfoRequest;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.CtripHotelRoomTypeDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.dto.ctrip.CtripHotelRoomType;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.CtripHotelRoomTypeService;

@Service
public class CtripHotelRoomTypeServiceImpl implements CtripHotelRoomTypeService{

	
    @Resource
	private CtripHotelRoomTypeDao ctripHotelRoomTypeDao;
    
    @Resource
    private IOtaInfoDao otaInfoDao;
    
    @Resource
    private CompanyDao companyDao;
    
    @Resource
    private BangInnDao bangInnDao;
    
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;

	@Override
	public List<CtripHotelRoomType> findByCtripParentHotelId(
			String ctripParentHotelId) {
		return ctripHotelRoomTypeDao.findByCtripParentHotelId(ctripParentHotelId);
	}

	@Override
	public void updateRoomBypeRelation(String companyId,String json,String innId,String fcHotelId){
		  List<MatchRoomType> matchRoomType = JacksonUtil.json2list(json, MatchRoomType.class);   // 新的关系
		  Company company = companyDao.selectCompanyById(companyId);
	        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.XC.name());
	        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
	        															//  OMS酒店ID，代销商ID ，渠道
	        OtaInnOtaDto innOtaDto = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(), companyId, dto.getOtaInfoId());  //   之前的绑定
		// TODO  1. 删除以前的关系
	        if(null!=innOtaDto){
	        	String childId =  innOtaDto.getWgHid();
	        	
	        	
	        	
	        	
	        }
		
		
		
		// TODO  2. 上传新的关系
		
		// TODO  3. 保存关系

		
		
	}
	


}
