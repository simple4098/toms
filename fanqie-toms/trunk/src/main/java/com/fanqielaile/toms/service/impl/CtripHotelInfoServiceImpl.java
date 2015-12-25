package com.fanqielaile.toms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.enums.CtripVersion;
import com.fanqie.bean.request.*;
import com.fanqie.bean.request.room_price.Authentication;
import com.fanqie.bean.request.room_price.HeaderInfo;
import com.fanqie.bean.request.room_price.RequestType;
import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqie.bean.response.ResponsePage;
import com.fanqie.util.CtripHttpClient;
import com.fanqielaile.toms.dao.CtripHotelRoomTypeDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.service.CtripHotelRoomTypeService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlCtripUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fanqielaile.toms.dao.CtripHotelInfoDao;
import com.fanqielaile.toms.service.CtripHotelInfoService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class CtripHotelInfoServiceImpl implements CtripHotelInfoService{
	private static Logger logger = LoggerFactory.getLogger(CtripHotelInfoServiceImpl.class);
	
    @Resource
	private CtripHotelInfoDao   ctripHotelInfoDao;
	@Resource
	private IOtaInfoDao otaInfoDao;
	@Resource
	private CtripHotelRoomTypeDao ctripHotelRoomTypeDao;
	
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

	@Override
	public Map<String, Object> getHotelInfo() throws Exception {
		//查询携程所有对接的公司信息，得到appkey=供应商编码
		List<OtaInfoRefDto> otaInfoRefDtos = this.otaInfoDao.selectOtaCompanyRefByOtaType(OtaType.XC.name());
		if (ArrayUtils.isNotEmpty(otaInfoRefDtos.toArray())) {
			for (OtaInfoRefDto o : otaInfoRefDtos) {
				//循环调用携程获取酒店信息接口
				//1.组装调用接口参数
				GetHotelInfoRequest hotelInfoRequest = new GetHotelInfoRequest();
				GetHotelInfoParam getHotelInfoParam = new GetHotelInfoParam();
				HeaderInfo headerInfo = new HeaderInfo();
				Authentication authentication = new Authentication();
				RequestType requestType = new RequestType();
				//组装请求类型
				requestType.setName(CtripRequestType.GetHotelInfo.name());
				requestType.setVersion(CtripVersion.V12.getValue());
				headerInfo.setRequestType(requestType);
				//组装用户信息
				authentication.setUserName(o.getXcUserName());
				authentication.setPassword(o.getXcPassword());
				headerInfo.setAuthentication(authentication);
				//组装头部信息
				headerInfo.setAsyncRequest(false);
				//TODO 以后从数据库当中查询
				headerInfo.setRequestorId("Ctrip.com");
				headerInfo.setUserID(o.getUserId());
				hotelInfoRequest.setHeaderInfo(headerInfo);
				getHotelInfoParam.setCurrentPage(1);
				getHotelInfoParam.setSupplierId(o.getAppKey());
				hotelInfoRequest.setHeaderInfo(headerInfo);
				hotelInfoRequest.setGetHotelInfoParam(getHotelInfoParam);
				//调用携程获取酒店信息接口方法
				getCtripHotelInfoMethod(hotelInfoRequest, o);
			}
		}
		return null;
	}

	/**
	 * 调用携程获取酒店信息方法
	 *
	 * @param hotelInfoRequest
	 */
	private void getCtripHotelInfoMethod(GetHotelInfoRequest hotelInfoRequest, OtaInfoRefDto otaInfoRefDto) throws Exception {
		if (null != hotelInfoRequest) {
			logger.info("获取携程酒店基础信息，传递参数=>" + hotelInfoRequest.toString());
			String execute = CtripHttpClient.execute(FcUtil.fcRequest(hotelInfoRequest));
			logger.info("获取携程酒店基础信息，返回值=>" + execute);
			//解析响应xml
			//获取headerInfo中的resultCode
			String resultCode = XmlCtripUtil.getResultCode(execute);
			//判断响应是否成功
			if (Constants.RESULT_CODE.equals(resultCode)) {
				//响应成功
				//解析xml成对象
				CtripHotelInfo ctripHotelInfo = XmlCtripUtil.getCtripHotelInfo(execute);
				//循环调用所有信息
				List<CtripHotelInfo> ctripHotelInfoList = foreachGetCtripHotelInfoByPage(hotelInfoRequest, ctripHotelInfo.getPage());
				//将所有页数的hotelinfo组装成一个list
				List<CtripHotelInfo> ctripHotelInfos = getHotelInfoList(ctripHotelInfoList);
				//调用携程mapping1.2接口获取母酒店和母房型信息
				List<CtripHotelInfo> ctripHotelInfosAndRoomTypeInfoList = getCtripHotelInfoAndRoomTypeInfo(ctripHotelInfos, otaInfoRefDto);
				//更新本地数据库
				updateCtripHotelInfoMethod(ctripHotelInfosAndRoomTypeInfoList);
			} else {
				//响应失败
				logger.info("解析携程酒店信息返回xml中的resultCode为false");
			}
		}
	}

	/**
	 * 调用携程mapping1.2接口获取酒店母酒店母房型信息
	 *
	 * @param ctripHotelInfos
	 * @return
	 */
	private List<CtripHotelInfo> getCtripHotelInfoAndRoomTypeInfo(List<CtripHotelInfo> ctripHotelInfos, OtaInfoRefDto otaInfoRefDto) throws Exception {
		List<CtripHotelInfo> result = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(ctripHotelInfos.toArray())) {
			for (CtripHotelInfo ctripHotelInfo : ctripHotelInfos) {
				//一个一个获取母酒店和母房型信息
				//1.组装获取的参数
				GetMappingInfoRequest getMappingInfoRequest = makeGetMappingInfoParam(ctripHotelInfo, otaInfoRefDto);
				//2.参数转换为xml
				String mappingInfoXml = FcUtil.fcRequest(getMappingInfoRequest);
				logger.info("调用携程1.2接口传递的参数=>" + mappingInfoXml);
				//3.调用携程获取mappinginfo1.2接口
				String execute = CtripHttpClient.execute(mappingInfoXml);
				logger.info("调用携程1.2接口返回值=>" + execute);
				//4.解析1.2返回的xml
				String resultCode = XmlCtripUtil.getResultCode(execute);
				if (Constants.RESULT_CODE.equals(resultCode)) {
					//响应成功，解析xml
					CtripHotelInfo c = XmlCtripUtil.getCtripHotelInfoAndRoomTypeInfo(execute, ctripHotelInfo);
					result.add(c);
				}
			}
		}
		return result;
	}

	/**
	 * 组装请求参数
	 *
	 * @param ctripHotelInfo
	 * @return
	 */
	private GetMappingInfoRequest makeGetMappingInfoParam(CtripHotelInfo ctripHotelInfo, OtaInfoRefDto otaInfoRefDto) {
		GetMappingInfoRequest getMappingInfoRequest = new GetMappingInfoRequest();
		HeaderInfo headerInfo = new HeaderInfo();
		//组装头部参数
		Authentication authentication = new Authentication();
		authentication.setUserName(otaInfoRefDto.getXcUserName());
		authentication.setPassword(otaInfoRefDto.getXcPassword());
		RequestType requestType = new RequestType();
		requestType.setVersion(CtripVersion.V12.getValue());
		requestType.setName(CtripRequestType.GetMappingInfo.name());
		headerInfo.setUserID(otaInfoRefDto.getUserId());
		headerInfo.setAuthentication(authentication);
		headerInfo.setRequestType(requestType);
		headerInfo.setAsyncRequest(false);
		headerInfo.setRequestorId("Ctrip.com");
		//组装头部请求参数完成
		//组装请求的参数
		getMappingInfoRequest.setHeaderInfo(headerInfo);
		GetMappingInfoRequestList getMappingInfoRequestList = new GetMappingInfoRequestList();
		getMappingInfoRequestList.setGetMappingInfoType(getMappingInfoRequestList.getUNMAPPING());
		getMappingInfoRequestList.setSupplierId(otaInfoRefDto.getAppKey());
		//封装请求的hotel
		List<GetMapingInfoParam> getMapingInfoParamList = new ArrayList<>();
		GetMapingInfoParam getMapingInfoParam = new GetMapingInfoParam();
		getMapingInfoParam.setHotel(ctripHotelInfo.getChildHotelId());
		getMapingInfoParamList.add(getMapingInfoParam);
		getMappingInfoRequestList.setGetMapingInfoParamList(getMapingInfoParamList);
		getMappingInfoRequest.setGetMappingInfoRequestList(getMappingInfoRequestList);
		return getMappingInfoRequest;
	}

	/**
	 * 将所有分页信息组装成一个酒店信息
	 *
	 * @param ctripHotelInfoList
	 * @return
	 */
	private List<CtripHotelInfo> getHotelInfoList(List<CtripHotelInfo> ctripHotelInfoList) {
		List<CtripHotelInfo> result = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(ctripHotelInfoList.toArray())) {
			for (CtripHotelInfo ctripHotelInfo : ctripHotelInfoList) {
				//页数循环
				if (ArrayUtils.isNotEmpty(ctripHotelInfo.getCtripHotelInfos().toArray())) {
					for (CtripHotelInfo c : ctripHotelInfo.getCtripHotelInfos()) {
						//每页对象循环
						CtripHotelInfo hotelInfo = new CtripHotelInfo();
						hotelInfo.setCountryName(c.getCountryName());
						hotelInfo.setHotelName(c.getHotelName());
						hotelInfo.setCityName(c.getCityName());
						hotelInfo.setAddress(c.getAddress());
						hotelInfo.setTelephone(c.getTelephone());
						hotelInfo.setChildHotelId(c.getChildHotelId());
						hotelInfo.setParentHotelId(c.getParentHotelId());
						result.add(hotelInfo);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 分页调用携程获取酒店信息
	 *
	 * @param hotelInfoRequest
	 * @param page
	 * @return
	 * @throws Exception
	 */
	private List<CtripHotelInfo> foreachGetCtripHotelInfoByPage(GetHotelInfoRequest hotelInfoRequest, ResponsePage page) throws Exception {
		List<CtripHotelInfo> result = new ArrayList<>();
		for (int i = 1; i <= page.getTotalPage(); i++) {
			GetHotelInfoParam getHotelInfoParam = hotelInfoRequest.getGetHotelInfoParam();
			getHotelInfoParam.setCurrentPage(i);
			logger.info("获取携程酒店基础信息，传递参数=>" + hotelInfoRequest.toString());
			String execute = CtripHttpClient.execute(FcUtil.fcRequest(hotelInfoRequest));
			logger.info("获取携程酒店基础信息，返回值=>" + execute);
			//解析响应xml
			//获取headerInfo中的resultCode
			String resultCode = XmlCtripUtil.getResultCode(execute);
			//判断响应是否成功
			if (Constants.RESULT_CODE.equals(resultCode)) {
				//响应成功
				//解析xml成对象
				CtripHotelInfo ctripHotelInfo = XmlCtripUtil.getCtripHotelInfo(execute);
				result.add(ctripHotelInfo);
			} else {
				//响应失败
				logger.info("解析携程酒店信息返回xml中的resultCode为false");
			}
		}
		return result;
	}

	/**
	 * 根据携程返回数据更新本地数据
	 *
	 * @param ctripHotelInfoList
	 */
	private void updateCtripHotelInfoMethod(List<CtripHotelInfo> ctripHotelInfoList) {
		if (ArrayUtils.isNotEmpty(ctripHotelInfoList.toArray())) {
			for (CtripHotelInfo ctripHotelInfo : ctripHotelInfoList) {
				//1.判断在toms本地库中是否存在母酒店信息
				CtripHotelInfo hotelInfo = this.ctripHotelInfoDao.findByParentHotelId(ctripHotelInfo.getParentHotelId());
				if (null != hotelInfo) {
					//存在更新数据库
					this.ctripHotelInfoDao.updateHotelInfo(ctripHotelInfo);
				} else {
					//1.不存在新增携程酒店信息
					this.ctripHotelInfoDao.saveHotelInfo(ctripHotelInfo);
					//2.新增母房型信息
					this.ctripHotelRoomTypeDao.insertCtripHotelRoomTypeByCtripHotel(ctripHotelInfo);
				}
			}
		}
	}
}
