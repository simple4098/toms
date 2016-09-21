
package com.fanqielaile.toms.controller.homestay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.controller.BaseController;
import com.fanqielaile.toms.dto.homestay.BookingCheckDto;
import com.fanqielaile.toms.dto.homestay.FetchRoomDto;
import com.fanqielaile.toms.dto.homestay.GetRoomStatusDto;
import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.exception.BusinessException;
import com.fanqielaile.toms.model.homestay.bo.BookingCheckBo;
import com.fanqielaile.toms.model.homestay.bo.FetchRoomBo;
import com.fanqielaile.toms.model.homestay.bo.GetRoomStatusBo;
import com.fanqielaile.toms.service.IHomeStayRoomInfoService;
import com.fanqielaile.toms.util.HomeStayConstants;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("/homestay")
public class HomeStayController extends BaseController{
	private Logger log = LoggerFactory.getLogger(HomeStayController.class);
	@Autowired
	private IHomeStayRoomInfoService homeStayRoomInfoService;
	
	@ApiOperation( value = "民宿房源查询", notes = "民宿房源查询",httpMethod = "GET",produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name="timestamp",required=true,value="时间戳",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="signature",required=true,value="秘钥",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="otaId",required=true,value="渠道Id",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="city",required=true,value="城市",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="roomId",required=false,value="房型Id",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="minPrice",required=false,value="最低价",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="maxPrice",required=false,value="最高价",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="updateTime",required=false,value="修改时间",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="pageIndex",required=true,value="翻页页码",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="pageSize",required=true,value="每页条数",dataType="String",paramType = "query")
	})
	@RequestMapping(value="/fetchRoom",method = {RequestMethod.GET})
	@ResponseBody
	public FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo){
		checkFetchRooParam(fetchRoomBo);
		return homeStayRoomInfoService.fetchRoom(fetchRoomBo);
	}
	
	
	@ApiOperation( value = "民宿房态查询", notes = "民宿房态查询",httpMethod = "GET",produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name="timestamp",required=true,value="时间戳",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="signature",required=true,value="秘钥",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="otaId",required=true,value="渠道Id",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="roomId",required=true,value="房型Id",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="startTime",required=true,value="入住时间",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="endTime",required=true,value="离开时间",dataType="String",paramType = "query")
	})
	@RequestMapping(value="/getRoomStatus",method = {RequestMethod.GET})
	@ResponseBody
	public GetRoomStatusDto getRoomStatus(GetRoomStatusBo roomStatusBo){
		checkRoomStatusParam(roomStatusBo);
		return homeStayRoomInfoService.getRoomStatus(roomStatusBo);
	}
	
	@ApiOperation( value = "民宿可预订检查", notes = "民宿可预订检查",httpMethod = "GET",produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name="timestamp",required=true,value="时间戳",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="signature",required=true,value="秘钥",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="otaId",required=true,value="渠道Id",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="checkIn",required=true,value="入住时间",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="checkOut",required=true,value="离开时间",dataType="String",paramType = "query"),
		@ApiImplicitParam(name="numOfGuests",required=true,value="最大入住人数",dataType="Integer",paramType = "query"),
		@ApiImplicitParam(name="quantity",required=true,value="数量",dataType="Integer",paramType = "query"),
		@ApiImplicitParam(name="roomId",required=true,value="房间号",dataType="Integer",paramType = "query")
	})
	@RequestMapping(value="/bookingCheck",method = {RequestMethod.GET})
	@ResponseBody
	public BookingCheckDto bookingCheck(BookingCheckBo bookingCheckBo){
		checkBookingCheckParam(bookingCheckBo);
		return homeStayRoomInfoService.bookingCheck(bookingCheckBo);
	}
	
	
	
	private void checkBookingCheckParam(BookingCheckBo bookingCheckBo) {
		if(bookingCheckBo == null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"请传入请求参数");
		}
		if(bookingCheckBo.getRoomId() == null || bookingCheckBo.getRoomId()<0){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入roomTypeId有误");
		}
		if(bookingCheckBo.getQuantity() == null || bookingCheckBo.getRoomId()<0){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入quantity有误");
		}
		if(bookingCheckBo.getCheckIn()==null
				|| bookingCheckBo.getCheckOut()==null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"缺少时间参数");
		}
		try {
			DateUtil.parseDate(bookingCheckBo.getCheckIn());
			DateUtil.parseDate(bookingCheckBo.getCheckOut());
			
		} catch (Exception e) {
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"时间参数有误");
		}
		
	}
	
	private void checkRoomStatusParam(GetRoomStatusBo roomStatusBo) {
		if(roomStatusBo == null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"请传入请求参数");
		}
		if(roomStatusBo.getRoomId() == null || roomStatusBo.getRoomId()<0){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入roomTypeId有误");
		}
		if(roomStatusBo.getStartTime()==null
				|| roomStatusBo.getStartTime()==null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"缺少时间参数");
		}
		
	}

	private void checkFetchRooParam(FetchRoomBo fetchRoomBo) {
		if(fetchRoomBo == null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"请传入请求参数");
		}
		//检查城市参数
		if(fetchRoomBo.getCity()==null){
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入城市参数异常");
		}
		//检查分页参数
		try {
			int pageSize = Integer.parseInt(fetchRoomBo.getPageSize());
			pageSize = pageSize>=HomeStayConstants.DEFAULE_PAGE_SIZE?HomeStayConstants.DEFAULE_PAGE_SIZE:pageSize;
			fetchRoomBo.setPageSize(pageSize+"");
		} catch (NumberFormatException e) {
			log.warn("【民宿获取房源】【传入分页参数异常】",e);
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入分页参数异常");
		}
		//检查 初始化最大价
		if(fetchRoomBo.getMaxPrice() != null){
			try {
				int maxPrice = Integer.parseInt(fetchRoomBo.getMaxPrice());
				maxPrice = maxPrice/100;
				fetchRoomBo.setMaxPrice(maxPrice+"");
			} catch (NumberFormatException e) {
				log.warn("【民宿获取房源】【传入maxPrice参数错误】",e);
				throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入分页参数异常");
			}
		}
		//检查 并转换最低价
		if(fetchRoomBo.getMinPrice() != null){
			try {
				int minPrice = Integer.parseInt(fetchRoomBo.getMinPrice());
				minPrice = minPrice/100;
				fetchRoomBo.setMinPrice(minPrice+"");
			} catch (NumberFormatException e) {
				log.warn("【民宿获取房源】【传入minPrice参数错误】",e);
				throw new BusinessException(ResultCode.PARAM_ERROR.getCode(),"传入分页参数异常");
			}
		}
	}
	
}

