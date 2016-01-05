package com.fanqielaile.toms.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.enums.CtripVersion;
import com.fanqie.bean.request.mapping.SetMappingInfoRequest;
import com.fanqie.bean.request.mapping.SetMappingInfoRequestParams;
import com.fanqie.bean.request.room_price.Authentication;
import com.fanqie.bean.request.room_price.HeaderInfo;
import com.fanqie.bean.request.room_price.RequestType;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.exception.RequestCtripException;
import com.fanqielaile.toms.service.CtripRoomTypeMappingService;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.HandlerResult;

@Service
public class CtripRoomTypeMappingServiceImpl implements  CtripRoomTypeMappingService{

	private static  final Logger LOGGER = LoggerFactory.getLogger(CtripRoomTypeMappingServiceImpl.class);
	@Resource
	private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;
	
	@Resource
	private IOtaInfoDao  otaInfoDao;
	
	@Override
	public List<CtripRoomTypeMapping> findRoomTypeMapping(String companyId,
			String childHotelId,String innId) {
		return ctripRoomTypeMappingDao.findRoomTypeMapping(companyId, childHotelId,innId);
	}

	@Override
	public void updateMappingPlanCode(String companyId,String ratePlanCode,String planCodeName, String mappingId) throws JAXBException, RequestCtripException {
		CtripRoomTypeMapping  mapping =  ctripRoomTypeMappingDao.findById(Integer.parseInt(mappingId));
		OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
		SetMappingInfoRequest st = new SetMappingInfoRequest();
		HeaderInfo headerInfo = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
        headerInfo.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.SetMappingInfo, CtripVersion.V12);
		st.setHeaderInfo(headerInfo);
		SetMappingInfoRequestParams sip = new SetMappingInfoRequestParams(SetMappingInfoRequestParams.TYPE_UPDATE_ROOM_RALATION);
		sip.setHotelGroupHotelCode(mapping.getInnId());
		sip.setRoom(mapping.getCtripChildHotelId());
		sip.setHotelGroupHotelCode(mapping.getInnId());
		sip.setHotelGroupRoomTypeCode(mapping.getTomRoomTypeId());
		sip.setHotelGroupRoomName(mapping.getTomRoomTypeName());
		sip.setSupplierID(dto.getAppKey());
		st.setInfoRequestParams(sip);
		sip.setRatePlanCode(ratePlanCode);
		String mappingRequest = FcUtil.fcRequest(st);
		LOGGER.info("修改酒店PlanCode ,(mappingId):"+mappingId+"的-->request:"+mappingRequest);
		String mappingResponse = CtripHttpClient.execute(mappingRequest);
		LOGGER.info("修改酒店PlanCode(mappingId):"+mappingId+"的-->response:"+mappingResponse);
		ctripRoomTypeMappingDao.updateMappingRatePlanCode(mappingId, ratePlanCode, planCodeName);
		HandlerResult.handerResultCode(mappingResponse);
		LOGGER.info("修改完成");
	}

	


}
