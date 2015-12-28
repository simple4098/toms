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
import com.fanqie.util.CtripHttpClient;
import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.service.CtripRoomTypeMappingService;
import com.fanqielaile.toms.support.util.FcUtil;

@Service
public class CtripRoomTypeMappingServiceImpl implements  CtripRoomTypeMappingService{

	private static  final Logger LOGGER = LoggerFactory.getLogger(CtripRoomTypeMappingServiceImpl.class);
	@Resource
	private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;
	
	@Resource
	private IOtaInfoDao  otaInfoDao;
	
	@Override
	public List<CtripRoomTypeMapping> findRoomTypeMapping(String companyId,
			String childHotelId) {
		return ctripRoomTypeMappingDao.findRoomTypeMapping(companyId, childHotelId);
	}

	@Override
	public void updateMappingPlanCode(String companyId,String ratePlanCode,String planCodeName, String mappingId) throws JAXBException {
		CtripRoomTypeMapping  mapping =  ctripRoomTypeMappingDao.findById(Integer.parseInt(mappingId));
		
		OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
		SetMappingInfoRequest st = new SetMappingInfoRequest();
		HeaderInfo headerInfo = new HeaderInfo();
		Authentication authentication = new Authentication();
		RequestType requestType = new RequestType();
		
		//组装请求类型
		requestType.setName(CtripRequestType.SetMappingInfo.name());
		requestType.setVersion(CtripVersion.V12.getValue());
		headerInfo.setRequestType(requestType);
		
		//组装用户信息
		authentication.setUserName(dto.getXcUserName());
		authentication.setPassword(dto.getXcPassword());
		headerInfo.setAuthentication(authentication);
		headerInfo.setAsyncRequest(false);
		headerInfo.setRequestorId("Ctrip.com");
		headerInfo.setUserID("181");
		st.setHeaderInfo(headerInfo);
		SetMappingInfoRequestParams sip = new SetMappingInfoRequestParams(SetMappingInfoRequestParams.TYPE_UPDATE_ROOM_RALATION);
		sip.setHotelGroupHotelCode(mapping.getInnId());
		sip.setRoom(mapping.getCtripChildHotelId());
		sip.setHotelGroupHotelCode(mapping.getInnId());
		sip.setHotelGroupRoomTypeCode(mapping.getTomRoomTypeId());
		sip.setHotelGroupRoomName(mapping.getTomRoomTypeName());
		sip.setSupplierID("18");
		st.setInfoRequestParams(sip);
		sip.setRatePlanCode(ratePlanCode);
		String xml2 = FcUtil.fcRequest(st);
		LOGGER.info("修改酒店PlanCode"+mappingId+"的-->request:"+xml2);
		String mappingResponse = CtripHttpClient.execute(xml2);
		LOGGER.info("修改酒店PlanCode"+mappingId+"的-->response:"+mappingResponse);
		ctripRoomTypeMappingDao.updateMappingRatePlanCode(mappingId, ratePlanCode, planCodeName);
		LOGGER.info("修改完成");
	}

	


}
