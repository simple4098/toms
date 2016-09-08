package com.fanqielaile.toms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fanqielaile.toms.dto.minsu.FetchRoomDto;
import com.fanqielaile.toms.model.homestay.Address;
import com.fanqielaile.toms.model.homestay.Bed;
import com.fanqielaile.toms.model.homestay.Deposit;
import com.fanqielaile.toms.model.homestay.Geo;
import com.fanqielaile.toms.model.homestay.OmsFetchRoomVo;
import com.fanqielaile.toms.model.homestay.Owner;
import com.fanqielaile.toms.model.homestay.RoomInfo;
import com.fanqielaile.toms.model.homestay.bo.FetchRoomBo;
import com.fanqielaile.toms.service.IMinsuRoomInfoService;
import com.fanqielaile.toms.util.HomeStayConstants;
@Service("minsuRoomInfoService")
public class MinsuRoomInfoService implements IMinsuRoomInfoService{
	
	@Override
	public FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo) {
		FetchRoomDto dto = new FetchRoomDto();
		//TODO 从OMS 获取按房源查询bean
		List<OmsFetchRoomVo> list= new ArrayList<>();
		List<RoomInfo> rooms = new ArrayList<>();
		for (OmsFetchRoomVo omsFetchRoomVo : list) {
			RoomInfo roomInfo = initRoomInfo(omsFetchRoomVo);
			rooms.add(roomInfo);
		}
		
		dto.setRooms(rooms);
		return null;
	}

	private RoomInfo initRoomInfo(OmsFetchRoomVo omsFetchRoomVo) {
		RoomInfo roomInfo = new RoomInfo();
		
		roomInfo.setDescription(omsFetchRoomVo.getRoomInfo());
		roomInfo.setFloor(omsFetchRoomVo.getFloorNum());
		roomInfo.setMaxBookingDays(HomeStayConstants.maxBookingDays);//最大预定天数
		roomInfo.setMinBookingDays(HomeStayConstants.minBookingDays);//
		roomInfo.setHouseSize(omsFetchRoomVo.getRoomArea());
		roomInfo.setName(omsFetchRoomVo.getRoomTypeName());
		roomInfo.setPrice(omsFetchRoomVo.getPrice());
		roomInfo.setHouseModel(HomeStayConstants.houseModel);//户型
		roomInfo.setCheckInTime(HomeStayConstants.checkInTime);//最早
		roomInfo.setCheckOutTime(HomeStayConstants.checkOutTime);//最晚入住
		roomInfo.setInvoiceType(HomeStayConstants.invoiceType);//发票
		roomInfo.setHasLandlord(HomeStayConstants.hasLandlord);//房东合住
		roomInfo.setRefundDays(HomeStayConstants.refundDays);//全额退款提前天数;-1：表示不可退，0：表示随时退，1:表示需要提前一天退，等
		roomInfo.setUpdateTime(omsFetchRoomVo.getUpdateTime());
		roomInfo.setRoomId(0l+omsFetchRoomVo.getRoomTypeId());
		roomInfo.setRentSize(omsFetchRoomVo.getRoomArea());
		roomInfo.setInstantBook(HomeStayConstants.instantBook);//0=非即时预订/需要房东确认，1=即时预订/及时确认
		roomInfo.setReceptionHours(HomeStayConstants.receptionHours);//接待时间
		
		roomInfo.setImages(omsFetchRoomVo.getImages());//TODO 图片
		roomInfo.setDiscount(null);//TODO 折扣
		roomInfo.setTradingRules(null);//TODO 交易规则
		roomInfo.setRentType(null);//TODO
		roomInfo.setRoomUrl(null);//TODO  roomURL
		roomInfo.setMaxGuests(null);//TODO 最大人数
		roomInfo.setOnlinePayRatio(null);//TODO 线上支付百分比,如:50

		roomInfo.setState(null);
		roomInfo.setLandmark(null);//
		roomInfo.setTotalFloor(null);//
		roomInfo.setUserule(null);
		roomInfo.setSurrounding(null);
		roomInfo.setTraffic(null);
		roomInfo.setBookingCount(null);//该房源累计被预定间夜数 
		roomInfo.setGoodRate(null);//好评率
		roomInfo.setFacilities(null);//配套设施
		
		//房东信息
		Owner owner = new Owner();
		owner.setId(omsFetchRoomVo.getInnId());
		owner.setNickName(omsFetchRoomVo.getInnName());
		owner.setAvatarUrl(HomeStayConstants.avatarUrl);
		roomInfo.setOwner(owner);
		
		//地址
		Address address = new Address();
		address.setAddr(omsFetchRoomVo.getAddress());
		address.setCity(omsFetchRoomVo.getCity());
		roomInfo.setAddress(address);
		
		//床位
		List<Bed> beds = new ArrayList<>(1);
		Bed bed = new Bed();
		bed.setNumOfBeds(omsFetchRoomVo.getBedNum());
		bed.setSize(omsFetchRoomVo.getBedLen()+"*"+omsFetchRoomVo.getBedWid());
		bed.setType(omsFetchRoomVo.getBedType());
		beds.add(bed);
		roomInfo.setBeds(beds);
		
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

}

