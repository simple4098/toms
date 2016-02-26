package com.fanqielaile.toms.support.util;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.enums.CtripVersion;
import com.fanqie.bean.request.mapping.HotelGroupInterfaceRoomTypeListRequest;
import com.fanqie.bean.request.mapping.HotelGroupInterfaceRoomTypeListRequestParams;
import com.fanqie.bean.request.room_price.HeaderInfo;
import com.fanqie.bean.response.HotelGroupInterfaceRoomTypeEntity;
import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.exception.RequestCtripException;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Date;
import java.util.List;

public class CtripMappingBy {
	
	private final static Logger LOGGER  = Logger.getLogger(CtripMappingBy.class);
	
	/**
	 *   携程新增的MappingType转换为 我们自己的  CtripRoomTypeMapping 类型
	 * @param company 公司信息
	 * @param fqRateCode (番茄价格计划code，番茄用的是众荟的)
	 * @param innId 客栈ID
	 * @param ctripMasterHotelId 携程母酒店ID
	 * @param matchRoomType 新的关系对象
	 * @return  CtripRoomTypeMapping
	 * @throws RequestCtripException
	 * @throws JAXBException
	 * @throws CtripDataException 
	 */
	public static CtripRoomTypeMapping   toMappingBy(Company company,OtaRatePlan fqRateCode, String innId,
			String ctripMasterHotelId, MatchRoomType matchRoomType,OtaRatePlan ratePlan,OtaInfoRefDto dto) throws RequestCtripException, JAXBException, CtripDataException{
		
		HotelGroupInterfaceRoomTypeListRequest  request = new HotelGroupInterfaceRoomTypeListRequest();
		HeaderInfo headerInfo = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
        headerInfo.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.GetCtripRoomTypeInfo, CtripVersion.V12);
		request.setHeaderInfo(headerInfo);
		HotelGroupInterfaceRoomTypeListRequestParams params = new HotelGroupInterfaceRoomTypeListRequestParams();
		params.setHotelGroupHotelCode(innId);
		params.setHotelGroupRoomTypeCode(matchRoomType.getRoomTypeId());
		request.setParams(params);
		String getMappingRoomRequest  = FcUtil.fcRequest(request);
		LOGGER.info("获取酒店子房型"+innId+"request -->"+getMappingRoomRequest);
		String getMappingRoomResponse =CtripHttpClient.execute(getMappingRoomRequest);
		LOGGER.info("获取酒店子房型"+innId+",房型ID:"+matchRoomType.getRoomTypeId()+"response --->"+getMappingRoomResponse);
		RequestResponse rr  =   FcUtil.xMLStringToBean(getMappingRoomResponse);
		List<HotelGroupInterfaceRoomTypeEntity> list =  rr.getRequestResult().getResponse().getHotelGroupInterfaceRoomTypeListResponse().getHotelGroupInterfaceRoomTypeList();
		if(null==list || list.isEmpty()){
			throw new CtripDataException("携程获取子房型出错：innId:"+innId + "房型ID:"+matchRoomType.getRoomTypeId());
		}
		HotelGroupInterfaceRoomTypeEntity roomMapping = list.get(0);
		CtripRoomTypeMapping crm = new CtripRoomTypeMapping();
		crm.setBedLen(matchRoomType.getBedLen());
		crm.setBedNum(matchRoomType.getBedNum());
		crm.setBedWid(matchRoomType.getBedWid());
		crm.setCompanyId(company.getId());
		crm.setCreatedDate(new Date());
		crm.setCtripChildHotelId(roomMapping.getHotel());
		crm.setCtripChildRoomTypeId(roomMapping.getRoom());
		crm.setCtripRoomTypeName(matchRoomType.getFcRoomTypeName());
		crm.setTomRoomTypeId(matchRoomType.getRoomTypeId());
		crm.setInnId(innId);
		crm.setRatePlanCode(ratePlan.getRatePlanCode().toString());
		crm.setRatePlanCodeName(ratePlan.getCtripRatePlanName());
		crm.setDeleted(0);
		crm.setTomRoomTypeName(matchRoomType.getRoomTypeName());
		crm.setCtripMasterRoomId(matchRoomType.getFcRoomTypeId());//  保存当前操作的母房型
		crm.setRoomTypeCode(company.getOtaId()+"_"+matchRoomType.getRoomTypeId());
		crm.setInnCode(company.getOtaId()+"_"+innId);
		crm.setFanQieRatePlanCode(fqRateCode!=null?fqRateCode.getRatePlanCode().toString():"");
		return crm;
	}

}
