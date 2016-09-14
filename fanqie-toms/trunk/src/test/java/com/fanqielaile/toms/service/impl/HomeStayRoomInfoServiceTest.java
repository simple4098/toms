package com.fanqielaile.toms.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fanqielaile.toms.model.homestay.bo.FetchRoomBo;
import com.fanqielaile.toms.model.homestay.bo.GetRoomStatusBo;
import com.fanqielaile.toms.service.IHomeStayRoomInfoService;
import com.fanqielaile.toms.util.PassWordUtil;
import com.toms.test.BaseUnitTest;

public class HomeStayRoomInfoServiceTest extends BaseUnitTest{
	private IHomeStayRoomInfoService homeStayRoomInfoService;
	@Test
	public void testFetchRoom() {
		FetchRoomBo stayRoomBo = new FetchRoomBo();
		stayRoomBo.setCity("成都");
		//stayRoomBo.setMaxPrice(maxPrice);
		//stayRoomBo.setPageIndex(pageIndex);
		stayRoomBo.setPageSize(50+"");
		//stayRoomBo.setRoomTd(roomTd);
		stayRoomBo.setUpdateTime("2015-06-10 10:00:00");
		//stayRoomBo.setOffset(offset);
		printlnResult(homeStayRoomInfoService.fetchRoom(stayRoomBo));
	}
	@Test
	public void testGetRoomStatus(){
		GetRoomStatusBo roomStatusBo = new GetRoomStatusBo();
		/*roomStatusBo.setEndTime(endTime);
		roomStatusBo.setOtaId(otaId);
		roomStatusBo.setRoomId(roomId);
		roomStatusBo.setSignature(signature);
		roomStatusBo.setTimestamp(timestamp);
		roomStatusBo.setStartTime(startTime);*/
		homeStayRoomInfoService.getRoomStatus(roomStatusBo);
	}
	@Test
	public void test3(){
		//homeStayRoomInfoService.getRoomStatus(roomStatusBo);
	}
	@Override
	protected void init() {
		homeStayRoomInfoService = (IHomeStayRoomInfoService) context.getBean("homeStayRoomInfoService");
		
	}

}

