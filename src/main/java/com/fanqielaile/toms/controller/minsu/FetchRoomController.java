
package com.fanqielaile.toms.controller.minsu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanqielaile.toms.controller.BaseController;
/**
 * 民宿房源接口
* @Description: 
* @author LZQ
* @date 2016年9月6日 上午9:42:28
 */
import com.fanqielaile.toms.dto.minsu.FetchRoomDto;
import com.fanqielaile.toms.model.minsu.bo.FetchRoomBo;
import com.fanqielaile.toms.service.IMinsuRoomInfoService;
@Controller
@RequestMapping("/minsu")
public class FetchRoomController extends BaseController{
	@Autowired
	private IMinsuRoomInfoService minsuRoomInfoService;
	@RequestMapping(value="/fetchRoom",method = {RequestMethod.GET})
	@ResponseBody
	public FetchRoomDto fetchRoom(FetchRoomBo fetchRoomBo){
		checkParam(fetchRoomBo);
		return minsuRoomInfoService.fetchRoom(fetchRoomBo);
	}
	private void checkParam(FetchRoomBo fetchRoomBo) {
		// TODO Auto-generated method stub
		
	}
	
}

