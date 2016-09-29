package com.fanqielaile.toms.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanqie.util.DateUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.bo.ctrip.homestay.BookingCheckBo;
import com.fanqielaile.toms.bo.ctrip.homestay.FetchRoomBo;
import com.fanqielaile.toms.bo.ctrip.homestay.GetRoomStatusBo;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.homestay.BookingCheckDto;
import com.fanqielaile.toms.dto.homestay.FetchRoomDto;
import com.fanqielaile.toms.dto.homestay.GetRoomStatusDto;
import com.fanqielaile.toms.enums.OmsBedType;
import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.exception.BusinessException;
import com.fanqielaile.toms.exception.SystemException;
import com.fanqielaile.toms.model.homestay.Address;
import com.fanqielaile.toms.model.homestay.Bed;
import com.fanqielaile.toms.model.homestay.Deposit;
import com.fanqielaile.toms.model.homestay.Geo;
import com.fanqielaile.toms.model.homestay.Image;
import com.fanqielaile.toms.model.homestay.OmsFetchRoomVo;
import com.fanqielaile.toms.model.homestay.OmsImg;
import com.fanqielaile.toms.model.homestay.Owner;
import com.fanqielaile.toms.model.homestay.RoomInfo;
import com.fanqielaile.toms.model.homestay.RoomStatusData;
import com.fanqielaile.toms.model.oms.bo.OmsGetRoomStatusBo;
import com.fanqielaile.toms.model.oms.vo.OmsRoomInfo;
import com.fanqielaile.toms.model.oms.vo.OmsRoomDetail;
import com.fanqielaile.toms.service.IHomeStayRoomInfoService;
import com.fanqielaile.toms.support.util.ResourceBundleUtil;
import com.fanqielaile.toms.util.Constants;
import com.fanqielaile.toms.util.HomeStayConstants;
import com.fanqielaile.toms.util.HomeStayHttpUtil;
import com.ibm.icu.math.BigDecimal;
@Service("homeStayRoomInfoService")
public class HomeStayRoomInfoService implements IHomeStayRoomInfoService{
	private Logger log = LoggerFactory.getLogger(HomeStayRoomInfoService.class);
	@Override
	public FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo) {
		FetchRoomDto dto = new FetchRoomDto(ResultCode.SUCCESS);
		
		List<OmsFetchRoomVo> list= getRoomFromOMS(fetchRoomBo);
		List<RoomInfo> rooms = new ArrayList<>();
		for (OmsFetchRoomVo omsFetchRoomVo : list) {
			RoomInfo roomInfo = initRoomInfo(omsFetchRoomVo);
			rooms.add(roomInfo);
		}
		dto.setRooms(rooms);
		return dto;
	}

	/**
	 * 从OMS 获取按房源查询bean
	 * @param fetchRoomBo
	 * @return
	 */
	private List<OmsFetchRoomVo> getRoomFromOMS(FetchRoomBo fetchRoomBo) {
		JSONObject object =HomeStayHttpUtil.doHttp(CommonApi.homeStayFetchRoom, fetchRoomBo);
		Integer status = object.getInteger("status");
		String message = object.getString("message");
		if(status == Constants.HTTP_SUCCESS){
			String listStr = object.getString("list");
			List<OmsFetchRoomVo> list = JSONArray.parseArray(listStr, OmsFetchRoomVo.class);
			return list;
		}else{
			log.error("【向OMS搜索房源失败】【错误状态信息:{}】",message);
			throw new BusinessException(ResultCode.OTHER_EXCEPTION.getCode(), message);
		}
		
	}

	private RoomInfo initRoomInfo(OmsFetchRoomVo omsFetchRoomVo) {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setRoomId(0l+omsFetchRoomVo.getOtaRoomTypeId());
		roomInfo.setDescription(omsFetchRoomVo.getRoomInfo());
		roomInfo.setFloor(omsFetchRoomVo.getFloorNum());
		roomInfo.setMaxBookingDays(HomeStayConstants.maxBookingDays);//最大预定天数
		roomInfo.setMinBookingDays(HomeStayConstants.minBookingDays);//
		roomInfo.setHouseSize(omsFetchRoomVo.getRoomArea());
		roomInfo.setName(omsFetchRoomVo.getInnName()+"-"+omsFetchRoomVo.getRoomTypeName());
		roomInfo.setPrice(omsFetchRoomVo.getPrice());
		roomInfo.setHouseModel(HomeStayConstants.houseModel);//户型
		roomInfo.setCheckInTime(HomeStayConstants.checkInTime);//最早
		roomInfo.setCheckOutTime(HomeStayConstants.checkOutTime);//最晚入住
		roomInfo.setInvoiceType(HomeStayConstants.invoiceType);//发票
		roomInfo.setHasLandlord(HomeStayConstants.hasLandlord);//房东合住
		roomInfo.setRefundDays(HomeStayConstants.refundDays);//全额退款提前天数;-1：表示不可退，0：表示随时退，1:表示需要提前一天退，等
		roomInfo.setUpdateTime(omsFetchRoomVo.getUpdateTime()==null?"":omsFetchRoomVo.getUpdateTime());
		
		roomInfo.setRentSize(omsFetchRoomVo.getRoomArea());
		roomInfo.setInstantBook(HomeStayConstants.instantBook);//0=非即时预订/需要房东确认，1=即时预订/及时确认
		roomInfo.setReceptionHours(HomeStayConstants.receptionHours);//接待时间
		roomInfo.setOnlinePayRatio(HomeStayConstants.OnlinePayRatio);//线上支付百分比,如:50
		
		String bedType = omsFetchRoomVo.getBedType();
		int rentType = HomeStayConstants.bedType.equals(bedType)?3:1;
		roomInfo.setRentType(rentType);//出租类型

		roomInfo.setState(omsFetchRoomVo.getStatus());
		roomInfo.setTradingRules(HomeStayConstants.tradingRules);//交易规则
		roomInfo.setDiscount(null);//折扣
		roomInfo.setRoomUrl(null);//roomURL
		roomInfo.setLandmark(null);//
		roomInfo.setTotalFloor(null);//
		roomInfo.setUserule(null);
		roomInfo.setSurrounding(null);
		roomInfo.setTraffic(null);
		roomInfo.setBookingCount(null);//该房源累计被预定间夜数 
		roomInfo.setGoodRate(null);//好评率
		roomInfo.setFacilities(null);//配套设施
		
		roomInfo.setImages(changeImg(omsFetchRoomVo.getImages()));// 图片
		//房东信息
		Owner owner = new Owner();
		owner.setId(omsFetchRoomVo.getInnId());
		owner.setNickName(omsFetchRoomVo.getInnName());
		owner.setAvatarUrl(ResourceBundleUtil.getString("homestay.owner.url"));
		owner.setTel(omsFetchRoomVo.getFrontPhone());
		roomInfo.setOwner(owner);
		
		//地址
		Address address = new Address();
		address.setAddr(omsFetchRoomVo.getAddress());
		address.setCity(omsFetchRoomVo.getCity());
		roomInfo.setAddress(address);
		
		//床位 并根据床位计算最大入住人数
		setBedsInfo(omsFetchRoomVo,roomInfo);
		
		Geo geo = new Geo();//经纬度
		geo.setLatitude(omsFetchRoomVo.getLatitude());
		geo.setLongitude(omsFetchRoomVo.getLongitude());
		roomInfo.setGeo(geo);
		
		Deposit deposit = new Deposit();
		deposit.setAmount(HomeStayConstants.depositAmount);
		deposit.setType(HomeStayConstants.depositType);
		roomInfo.setDeposit(deposit);// 押金
		return roomInfo;
	}

	private void setBedsInfo(OmsFetchRoomVo omsFetchRoomVo, RoomInfo roomInfo) {
		List<Bed> beds = null;
		int maxGuests = 1;
		if(omsFetchRoomVo.getBedLen()==null
				|| omsFetchRoomVo.getBedWid()==null
				|| omsFetchRoomVo.getBedType()==null
				|| omsFetchRoomVo.getBedNum()==null){
			roomInfo.setBeds(null);
		}else{
			beds = new ArrayList<>(1);
			Bed bed = new Bed();
			bed.setNumOfBeds(omsFetchRoomVo.getBedNum());
			bed.setSize(omsFetchRoomVo.getBedLen()+"*"+omsFetchRoomVo.getBedWid());
			bed.setType(omsFetchRoomVo.getBedType());
			beds.add(bed);

			String bedTypeValue = omsFetchRoomVo.getBedTypeValue();
			if(OmsBedType.Bed.equals(bedTypeValue)){
				maxGuests = OmsBedType.Bed.getNumber();
			}else
			if(OmsBedType.SignleBed.equals(bedTypeValue)){
				maxGuests = OmsBedType.SignleBed.getNumber();
			}else
			if(OmsBedType.ThreeBed.equals(bedTypeValue)){
				maxGuests = OmsBedType.ThreeBed.getNumber();
			}else
				maxGuests = OmsBedType.Other.getNumber();
		}
		roomInfo.setMaxGuests(maxGuests);
		roomInfo.setBeds(beds);
	}

	private List<Image> changeImg(List<OmsImg> images) {
		if(images == null){
			return null;
		}
		List<Image> imgs= new ArrayList<>(3);
		for (OmsImg omsImg : images) {
			Image img = new Image();
			img.setImageUrl(CommonApi.IMG_URL+omsImg.getImgUrl());
			img.setName(omsImg.getImgName());
			imgs.add(img);
		}
		return imgs;
	}

	@Override
	public GetRoomStatusDto getRoomStatus(GetRoomStatusBo roomStatusBo) {
		GetRoomStatusDto dto = new GetRoomStatusDto(ResultCode.SUCCESS);
		int otaRoomTypeId = roomStatusBo.getRoomId();
		//根据 otaRoomTypeId 向 oms 获取 accountId
		int accountId = getAccountId(otaRoomTypeId);
		//oms 查询房态参数
		OmsGetRoomStatusBo statusBo = changeRoomStatusBo(roomStatusBo,accountId);
		//oms 查询房态
		List<OmsRoomInfo> roomInfos = getRoomStatusFromOms(statusBo);
		//转换成民宿接口房态
		String rate = ResourceBundleUtil.getString("homestay.price.rate");
		for (OmsRoomInfo omsRoomInfo : roomInfos) {
			if(omsRoomInfo.getRoomTypeId() == otaRoomTypeId){
				dto.setRoomId(otaRoomTypeId+"");
				List<RoomStatusData> roomDetails = new ArrayList<>();
				List<OmsRoomDetail> details = omsRoomInfo.getRoomDetail();
				for (OmsRoomDetail omsRoomDetail : details) {
					RoomStatusData data = new RoomStatusData();
					data.setDate(omsRoomDetail.getRoomDate());
					int price = new BigDecimal(omsRoomDetail.getRoomPrice()).multiply(BigDecimal.valueOf(100)).intValue();
					data.setPrice(price);
					int orignPrice = (int) (price*Double.valueOf(rate));
					data.setOriginPrice(orignPrice);
					data.setStock(omsRoomDetail.getRoomNum());
					roomDetails.add(data);
				}
				dto.setData(roomDetails);
			}
		}
		return dto;
	}

	private List<OmsRoomInfo> getRoomStatusFromOms(OmsGetRoomStatusBo statusBo) {
		JSONObject object =HomeStayHttpUtil.doHttp(CommonApi.queryRoomStatus, statusBo);
		Integer status = object.getInteger("status");
		String message = object.getString("message");
		if(status == Constants.HTTP_SUCCESS){
			String listStr = object.getString("list");
			List<OmsRoomInfo> list = JSONArray.parseArray(listStr, OmsRoomInfo.class);
			return list;
		}else{
			log.error("【向OMS获取房源状态失败】【accountId:{}】【错误状态信息:{}】",statusBo.getAccountId(),message);
			throw new BusinessException(ResultCode.OTHER_EXCEPTION.getCode(), message);
		}
	}

	private OmsGetRoomStatusBo changeRoomStatusBo(GetRoomStatusBo roomStatusBo,int accountId) {
		OmsGetRoomStatusBo bo = new OmsGetRoomStatusBo();
		bo.setAccountId(accountId+"");
		bo.setFrom(roomStatusBo.getStartTime());
		bo.setOtaId(roomStatusBo.getOtaId());
		bo.setSignature(roomStatusBo.getSignature());
		bo.setTimestamp(roomStatusBo.getTimestamp());
		bo.setTo(roomStatusBo.getEndTime());
		return bo;
	}

	private int getAccountId(int otaRoomTypeId) {
		int accountId ;
		JSONObject jsonobject = new JSONObject();
		String result;
		try {
			jsonobject.put("otaRoomTypeId", otaRoomTypeId+"");
			result =HttpClientUtil.httpKvPost(CommonApi.queryOtaRoomType, jsonobject.toJSONString());
		} catch (Exception e) {
			log.error("【向OMS获取otaRoomType信息错误】【otaRoomTypeId:"+otaRoomTypeId+"】",e);
			throw new SystemException(ResultCode.SYSTEM_EXCEPTION.getCode(), ResultCode.SYSTEM_EXCEPTION.getMessage());
		}
		JSONObject object = JSONObject.parseObject(result);
		Integer status = object.getInteger("status");
		String message = object.getString("message");
		if(status == Constants.HTTP_SUCCESS){
			String roomStr = object.getString("roomtypeInfo");
			JSONObject roomObject = JSONObject.parseObject(roomStr);
			accountId = roomObject.getInteger("accountId");
			return accountId;
		}else{
			log.error("【向OMS获取otaRoomType信息错误】【otaRoomTypeId:{}】【错误状态信息:{}】",otaRoomTypeId,message);
			throw new BusinessException(ResultCode.OTHER_EXCEPTION.getCode(), message);
		}
	}

	@Override
	public BookingCheckDto bookingCheck(BookingCheckBo bookingCheckBo) {
		BookingCheckDto dto = new BookingCheckDto(ResultCode.SUCCESS);
		int otaRoomTypeId = bookingCheckBo.getRoomId();
		//根据 otaRoomTypeId 向 oms 获取 accountId
		int accountId = getAccountId(otaRoomTypeId);
		//oms 查询房态参数
		OmsGetRoomStatusBo statusBo = changeRoomStatusBo(bookingCheckBo,accountId);
		//oms 查询房态
		List<OmsRoomInfo> roomInfos = getRoomStatusFromOms(statusBo);
		boolean flag = true;
		int totalAmount = 0;
		int quantity = bookingCheckBo.getQuantity();
		//检查是否可预订  可预订并计算价格
		for (OmsRoomInfo omsRoomInfo : roomInfos) {
			//找到指定房型
			if(omsRoomInfo.getRoomTypeId() ==otaRoomTypeId){
				List<OmsRoomDetail> list = omsRoomInfo.getRoomDetail();
				for (OmsRoomDetail omsRoomDetail : list) {
					if(quantity > omsRoomDetail.getRoomNum()){
						flag = false;
						break;
					}
					totalAmount +=  quantity * Double.parseDouble(omsRoomDetail.getRoomPrice())*100;
				}
			}
		}
		//可预订
		if(flag){
			dto.setBookingStatus(HomeStayConstants.BOOKING_STATUS_YES);
			dto.setTotalAmount(totalAmount);
			dto.setOfflineAmount(0);
			dto.setOnlineAmount(totalAmount);
			dto.setRate(HomeStayConstants.OnlinePayRatio);
			dto.setDerateAmount(0);
			dto.setOriginAmount(totalAmount);
			Deposit deposit = new Deposit();
			deposit.setAmount(HomeStayConstants.depositAmount);
			deposit.setType(HomeStayConstants.depositType);
			dto.setDeposit(deposit);
		//不可预订
		}else{
			dto.setBookingStatus(HomeStayConstants.BOOKING_STATUS_NO);
		}
		return dto;
	}
	
	private OmsGetRoomStatusBo changeRoomStatusBo(BookingCheckBo bookingCheckBo, int accountId) {
		OmsGetRoomStatusBo bo = new OmsGetRoomStatusBo();
		bo.setAccountId(accountId+"");
		bo.setFrom(bookingCheckBo.getCheckIn());
		bo.setOtaId(bookingCheckBo.getOtaId());
		bo.setSignature(bookingCheckBo.getSignature());
		bo.setTimestamp(bookingCheckBo.getTimestamp());
		//结束日期为 离店日期的前一天
		bo.setTo(DateUtil.format(DateUtil.addDay(DateUtil.parseDate(bookingCheckBo.getCheckOut()), -1)));
		//bo.setTo(bookingCheckBo.getCheckOut());
		return bo;
	}

}

