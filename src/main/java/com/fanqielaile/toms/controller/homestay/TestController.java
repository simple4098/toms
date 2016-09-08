package com.fanqielaile.toms.controller.homestay;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fanqielaile.toms.dto.minsu.BaseResultDto;
import com.fanqielaile.toms.enums.ResultCode;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/homestay")
public class TestController {
	@ResponseBody
	@ApiOperation( value = "测试接口", notes = "测试接口(协议加密)",httpMethod = "GET",produces=MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value = "/test",method = {RequestMethod.GET,RequestMethod.POST})
	public BaseResultDto test(@ApiParam(value = "时间") @RequestParam(required = true)String timestamp,
			@ApiParam(value = "秘钥") @RequestParam(required = true)String signature,
			@ApiParam(value = "otaId") @RequestParam(required = true)String otaId){
		BaseResultDto dto = new BaseResultDto(ResultCode.SUCCESS);
		return dto;
	}
}

