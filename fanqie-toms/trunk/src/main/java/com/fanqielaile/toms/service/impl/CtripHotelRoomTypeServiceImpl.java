package com.fanqielaile.toms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.enums.CtripVersion;
import com.fanqie.bean.request.mapping.HotelGroupInterfaceRoomTypeListRequest;
import com.fanqie.bean.request.mapping.HotelGroupInterfaceRoomTypeListRequestParams;
import com.fanqie.bean.request.mapping.SetMappingInfoRequest;
import com.fanqie.bean.request.mapping.SetMappingInfoRequestParams;
import com.fanqie.bean.request.room_price.HeaderInfo;
import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqie.bean.response.CtripHotelRoomType;
import com.fanqie.bean.response.HotelGroupInterfaceRoomTypeEntity;
import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.CtripHotelInfoDao;
import com.fanqielaile.toms.dao.CtripHotelRoomTypeDao;
import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dao.IFcRatePlanDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.dao.IOtaPriceModelDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.dto.OtaPriceModelDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.exception.RequestCtripException;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import com.fanqielaile.toms.service.CtripHotelRoomTypeService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.CtripDataHandlerUtils;
import com.fanqielaile.toms.support.util.CtripMappingBy;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.HandlerResult;

@Service
public class CtripHotelRoomTypeServiceImpl implements CtripHotelRoomTypeService{

	
	private final static Logger LOGGER  = Logger.getLogger(CtripHotelRoomTypeServiceImpl.class);
	
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
    
    @Resource
    private CtripRoomTypeMappingDao  ctripRoomTypeMappingDao;
    
    @Resource
    private CtripHotelInfoDao   ctripHotelInfoDao;
    
    @Resource
    private IFcRatePlanDao  fcRatePlanDao;
    
    @Resource
    private IOtaPriceModelDao  iOtaPriceModelDao;
    
    

	@Override
	public List<CtripHotelRoomType> findByCtripParentHotelId(
			String ctripParentHotelId) {
		return ctripHotelRoomTypeDao.findByCtripParentHotelId(ctripParentHotelId);
	}
	
	@Override
	public void deletePreviousRoomMapping(String childHotelId,String innId,String comanpyId) throws RequestCtripException, JAXBException {
		deleteCtripRoomMapping(childHotelId,comanpyId);
		ctripRoomTypeMappingDao.updateRoomMappingDelete(innId, childHotelId);
	}
	
	@Override
	public void deleteCtripRoomMapping(String childHotelId,String companyId)
			throws RequestCtripException, JAXBException {
		OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
		SetMappingInfoRequest st = new SetMappingInfoRequest();
		HeaderInfo headerInfo = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
        headerInfo.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.SetMappingInfo, CtripVersion.V12);
		st.setHeaderInfo(headerInfo);
		SetMappingInfoRequestParams sip = new SetMappingInfoRequestParams(SetMappingInfoRequestParams.TYPE_DELETE_WITH_PRICCE);
		sip.setHotel(childHotelId);
		sip.setMappingType(1);
		sip.setSupplierID(dto.getAppKey());
		st.setInfoRequestParams(sip);
		String xml2 = FcUtil.fcRequest(st);
		LOGGER.info("删除酒店"+childHotelId+"的绑定关系--request:"+xml2);
		String response = CtripHttpClient.execute(xml2);
		LOGGER.info("删除酒店"+childHotelId+"的绑定关系--response:"+response);
		HandlerResult.handerResultCode(response);
	}

	
	
	@Override
	public void cannelMappingAll(String companyId, String innId,
			String masterHotelId) throws RequestCtripException, JAXBException {
			BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
			OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
			OtaInnOtaDto innOtaDto = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(), companyId, dto.getOtaInfoId()); 
			if(null!=innOtaDto){// 以前绑定的关系
				String childId =  innOtaDto.getWgHid();
				deletePreviousRoomMapping(childId, innId,companyId);
				otaInnOtaDao.deletedOtaInnOtaById(innOtaDto.getId());
			}
			if(StringUtils.isNotBlank(masterHotelId)){
				CtripHotelInfo newHotel = ctripHotelInfoDao.findByParentHotelId(masterHotelId);
				if( StringUtils.isNotBlank( newHotel.getChildHotelId())){
					LOGGER.info("删除新绑定的携程母酒店Mapping,母id:"+masterHotelId+",子id:"+newHotel.getChildHotelId());
					deleteCtripRoomMapping(newHotel.getChildHotelId(), companyId);
				}
			}
	}
	
	
	@Override
	public List<CtripRoomTypeMapping> updateRoomBypeRelation(String companyId,String json,String innId,String ctripMasterHotelId)  throws RequestCtripException, JAXBException, CtripDataException{
		List<MatchRoomType> matchRoomTypes = JacksonUtil.json2list(json, MatchRoomType.class);   // 新的关系
		Company company = companyDao.selectCompanyById(companyId);
		BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
		OtaRatePlan ratePlan = fcRatePlanDao.selectDefaultCtripRatePlan();
		OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
		String priceModel = dto.getUsedPriceModel().name();
		//众荟ota信息
		OtaInfoRefDto zHOtaDto = otaInfoDao.selectOtaInfoByType(OtaType.ZH.name());
		//众荟价格计划code
		OtaRatePlan otaRatePlan = fcRatePlanDao.selectRatePlanByCompanyIdOtaIdDefault(company.getId(), zHOtaDto.getId());
		cannelMappingAll(companyId, innId, ctripMasterHotelId);
		List<CtripRoomTypeMapping> crms = new ArrayList<CtripRoomTypeMapping>();
		if(null!=matchRoomTypes && !matchRoomTypes.isEmpty()){
			for (MatchRoomType matchRoomType : matchRoomTypes) {
				if(StringUtils.isNotBlank(matchRoomType.getFcRoomTypeId())  && 
						StringUtils.isNotBlank(matchRoomType.getFcRoomTypeId())
						){
					addNewRoomTypeMappingToCtrip(otaRatePlan,innId, ctripMasterHotelId, matchRoomType,company);
					crms.add(CtripMappingBy.toMappingBy(company,otaRatePlan, innId,ctripMasterHotelId, matchRoomType,ratePlan,dto));
				}
			}
			String wgId = saveMapping(crms,ctripMasterHotelId);
			if(StringUtils.isNotBlank(wgId)){
				CtripHotelInfo hotelInfo = ctripHotelInfoDao.findByChildHotelId(wgId);
				CtripDataHandlerUtils.checkChildHotelId(hotelInfo,crms,ctripMasterHotelId);
				OtaInnOtaDto otaInnOtaDto = new OtaInnOtaDto();
				otaInnOtaDto.setWgHid(wgId);
				otaInnOtaDto.setCommissionPercent(new BigDecimal(1.1));
				otaInnOtaDto.setCompanyId(companyId);
				otaInnOtaDto.setAliasInnName(bangInn.getInnName());
				otaInnOtaDto.setOtaId(company.getOtaId().toString());
				otaInnOtaDto.setPriceModel(priceModel);
				otaInnOtaDto.setsJiaModel(priceModel) ;
				otaInnOtaDto.setDeleted(0);
				otaInnOtaDto.setBangInnId(bangInn.getId());
				otaInnOtaDto.setOtaInfoId(dto.getOtaInfoId());
				otaInnOtaDto.setInnId(Integer.parseInt(innId));
				otaInnOtaDto.setSj(0);
				otaInnOtaDao.saveOtaInnOta(otaInnOtaDto);
				OtaPriceModelDto opm = OtaPriceModelDto.toDto(otaInnOtaDto.getUuid());
				iOtaPriceModelDao.savePriceModel(opm);
				LOGGER.info("修改母房型:"+ctripMasterHotelId+"的子房型ID为-->"+wgId);
				ctripHotelInfoDao.updateChildHotelId(wgId, ctripMasterHotelId);
				LOGGER.info("请求接口，同步房价数据");
			}
			LOGGER.info("绑定结束");
			
		}
		return crms;
	}
	
	@Override
	public String saveMapping(List<CtripRoomTypeMapping> crms,String ctripMasterHotelId){
		String hotelId = null;
		for (CtripRoomTypeMapping ctripRoomTypeMapping : crms) {
			ctripRoomTypeMappingDao.saveRoomTypeMapping(ctripRoomTypeMapping);
			ctripHotelRoomTypeDao.updateChildRoomId(ctripRoomTypeMapping.getCtripChildRoomTypeId(), ctripRoomTypeMapping.getCtripMasterRoomId());
			hotelId = ctripRoomTypeMapping.getCtripChildHotelId();
		}
		
		return hotelId;
	}
	
	@Override
	public void addNewRoomTypeMappingToCtrip(OtaRatePlan otaRatePlan,String innId, String ctripMasterHotelId,
			MatchRoomType matchRoomType,Company company) throws RequestCtripException, JAXBException {
	
		OtaRatePlan ratePlan = fcRatePlanDao.selectDefaultCtripRatePlan(); //  默认的价格计划
		if(null==ratePlan){
			throw new RuntimeException("请先设计默认的价格计划");
		}
		CtripHotelInfo ctripHotelInfo =  ctripHotelInfoDao.findByParentHotelId(ctripMasterHotelId);
		OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.XC.name());
		SetMappingInfoRequest mapping = new SetMappingInfoRequest();
		HeaderInfo headerInfo = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
		headerInfo.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.SetMappingInfo, CtripVersion.V12);
		mapping.setHeaderInfo(headerInfo);
		SetMappingInfoRequestParams sip = new SetMappingInfoRequestParams(SetMappingInfoRequestParams.TYPE_ADD_ROOM_RALATION);
		if(StringUtils.isNotBlank(ctripHotelInfo.getChildHotelId())){//有子酒店
			sip.setHotel(ctripHotelInfo.getChildHotelId());
		//	String masterRoomId = matchRoomType.getFcRoomTypeId().replace("\n", "");
		//	Integer  ctripMasterRoomId = Integer.parseInt(masterRoomId);
			
			CtripHotelRoomType ctripHotelRoomType = ctripHotelRoomTypeDao.findByCtripParentHotelIdAndRoomTypeId(ctripMasterHotelId,matchRoomType.getFcRoomTypeId());
			if(StringUtils.isNotBlank(ctripHotelRoomType.getChildRoomId())){//有子房型(3.1)
				sip.setRoom(ctripHotelRoomType.getChildRoomId());
			}else{// 没有子房型 （3.3）
				sip.setMasterRoom(matchRoomType.getFcRoomTypeId());
				sip.setMasterHotel(ctripMasterHotelId);
				sip.setRatePlanCode(ratePlan.getRatePlanCode().toString());
			}
		}else{
			sip.setMasterHotel(ctripMasterHotelId);
			sip.setMasterRoom(matchRoomType.getFcRoomTypeId());
			sip.setRatePlanCode(ratePlan.getRatePlanCode().toString());
		}
		sip.setHotelGroupRoomName(matchRoomType.getRoomTypeName());
		sip.setHotelGroupRoomTypeCode(company.getOtaId()+"_"+matchRoomType.getRoomTypeId());
		sip.setHotelGroupHotelCode(company.getOtaId()+"_"+innId);
		sip.setSupplierID(dto.getAppKey());
		sip.setHotelGroupRatePlanCode(otaRatePlan.getRatePlanCode());
		mapping.setInfoRequestParams(sip);
		String mappingXml = FcUtil.fcRequest(mapping);
		LOGGER.info("新增酒店"+innId+"的房型绑定关系-->request:"+mappingXml);
		String mappingResponse = CtripHttpClient.execute(mappingXml);
		LOGGER.info("新增酒店"+innId+"的房型绑定关系-->response:"+mappingResponse);
		HandlerResult.handerResultCode(mappingResponse);
		
	}

	@Override
	public void updateBatchRoomMapping(String companyId) throws CtripDataException, RequestCtripException, JAXBException {
		OtaRatePlan ratePlan = fcRatePlanDao.selectDefaultCtripRatePlan(); //  默认的价格计划
		if(null==ratePlan){
			throw new RuntimeException("请先设计默认的价格计划");
		}
		
		Map<String, List<CtripRoomTypeMapping>> mapMappings = new HashMap<String, List<CtripRoomTypeMapping>>();
		List<CtripRoomTypeMapping> allMapping = 	ctripRoomTypeMappingDao.findAll();
		for (CtripRoomTypeMapping ctripRoomTypeMapping : allMapping) {
			if(mapMappings.containsKey(ctripRoomTypeMapping.getCtripChildHotelId())){
				mapMappings.get(ctripRoomTypeMapping.getCtripChildHotelId()).add(ctripRoomTypeMapping);
			}else{
				List<CtripRoomTypeMapping> innRoomMapings = new ArrayList<CtripRoomTypeMapping>();
				innRoomMapings.add(ctripRoomTypeMapping);
				mapMappings.put(ctripRoomTypeMapping.getCtripChildHotelId(), innRoomMapings);
			}
		}
		Company company = companyDao.selectCompanyById(companyId);
		for (Entry<String, List<CtripRoomTypeMapping>> entry : mapMappings.entrySet()) {
			deleteCtripRoomMapping(entry.getKey(), companyId);
			 for (CtripRoomTypeMapping ctripRoomTypeMapping : entry.getValue()) {
					OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.XC.name());
				    SetMappingInfoRequest mapping = new SetMappingInfoRequest();
					HeaderInfo headerInfo = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
					headerInfo.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.SetMappingInfo, CtripVersion.V12);
					mapping.setHeaderInfo(headerInfo);
					SetMappingInfoRequestParams sip = new SetMappingInfoRequestParams(SetMappingInfoRequestParams.TYPE_ADD_ROOM_RALATION);
					sip.setHotel(ctripRoomTypeMapping.getCtripChildHotelId());
					sip.setRoom(ctripRoomTypeMapping.getCtripChildRoomTypeId());
					sip.setHotelGroupRoomName(ctripRoomTypeMapping.getTomRoomTypeName());
					//-----------需要修改的字段---------------------
					sip.setHotelGroupRoomTypeCode(company.getOtaId()+"_"+ctripRoomTypeMapping.getTomRoomTypeId());
					sip.setHotelGroupHotelCode(company.getOtaId()+"_"+ctripRoomTypeMapping.getInnId());
					//----------------------------------------------
					sip.setSupplierID(dto.getAppKey());
					mapping.setInfoRequestParams(sip);
				//	sip.setRatePlanCode(ratePlan.getRatePlanCode().toString());
					//------------新增字段-------------------
					sip.setHotelGroupRatePlanCode("P_XCB");
					//---------------------------------------
					
						String mappingXml  = FcUtil.fcRequest(mapping);
						LOGGER.info("新增酒店"+ctripRoomTypeMapping.getInnId()+"的房型绑定关系-->request:"+mappingXml);
						String mappingResponse = CtripHttpClient.execute(mappingXml);
						LOGGER.info("新增酒店"+ctripRoomTypeMapping.getInnId()+"的房型绑定关系-->response:"+mappingResponse);
						HandlerResult.handerResultCode(mappingResponse);

						//---------验证--------
						HotelGroupInterfaceRoomTypeListRequest  request = new HotelGroupInterfaceRoomTypeListRequest();
						HeaderInfo headerInfo2 = new  HeaderInfo(dto.getUserId(), CtripConstants.requestorId,false);
				        headerInfo2.build(dto.getXcUserName(),dto.getXcPassword(),CtripRequestType.GetCtripRoomTypeInfo, CtripVersion.V12);
						request.setHeaderInfo(headerInfo2);
						HotelGroupInterfaceRoomTypeListRequestParams params = new HotelGroupInterfaceRoomTypeListRequestParams();
						params.setHotelGroupHotelCode(sip.getHotelGroupHotelCode());
						params.setHotelGroupRoomTypeCode(sip.getHotelGroupRoomTypeCode());
						request.setParams(params);
						String getMappingRoomRequest  = FcUtil.fcRequest(request);
						LOGGER.info("获取酒店子房型"+sip.getHotelGroupHotelCode()+"request -->"+getMappingRoomRequest);
						String getMappingRoomResponse =CtripHttpClient.execute(getMappingRoomRequest);
						LOGGER.info("获取酒店子房型"+sip.getHotelGroupHotelCode()+","
								+ "房型ID:"+sip.getHotelGroupRoomTypeCode()+"response --->"+getMappingRoomResponse);
						RequestResponse rr  =   FcUtil.xMLStringToBean(getMappingRoomResponse);
						List<HotelGroupInterfaceRoomTypeEntity> list =  rr.getRequestResult().getResponse().getHotelGroupInterfaceRoomTypeListResponse().getHotelGroupInterfaceRoomTypeList();
						if(null==list || list.isEmpty()){
							throw new CtripDataException("携程获取子房型出错：innId:"+sip.getHotelGroupHotelCode() +
									"房型ID:"+sip.getHotelGroupRoomTypeCode());
						}
						//-------------------------------------------验证结束----------------------------
						ctripRoomTypeMappingDao.updateNewCode(ctripRoomTypeMapping.getId(), company.getOtaId()+"_"+ctripRoomTypeMapping.getInnId(),
								company.getOtaId()+"_"+ctripRoomTypeMapping.getTomRoomTypeId());
			 }	
		}
		
	}


}
