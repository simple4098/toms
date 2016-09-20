package com.fanqielaile.toms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.enums.ResultCode;
import com.fanqielaile.toms.exception.BusinessException;
import com.fanqielaile.toms.exception.SystemException;

public class HomeStayHttpUtil {
	private static Logger log = LoggerFactory.getLogger(HomeStayHttpUtil.class);
	public static JSONObject doHttp(String url,Object param){
		String httpResult = "";
		JSONObject object;
		try {
			httpResult = HttpClientUtil.httpKvPost(url, JSON.toJSONString(param));
			object = JSONObject.parseObject(httpResult);
		} catch (Exception e) {
			log.error("【向OMS发送HTTP请求失败】",e);
			throw new SystemException(ResultCode.SYSTEM_EXCEPTION.getCode(), ResultCode.SYSTEM_EXCEPTION.getMessage());
		}
		Integer status = object.getInteger("status");
		String message = object.getString("message");
		if(status == Constants.HTTP_SUCCESS){
			return object;
		}else
		if(status == Constants.HTTP_412){
			throw new BusinessException(ResultCode.TIME_OUT);
		}else
		if(status == Constants.HTTP_413){
			throw new BusinessException(ResultCode.SIFNATURE_ERROR);
		}else{
			log.error("【向OMS发送HTTP请求失败】【错误状态信息:{}】",message);
			throw new BusinessException(ResultCode.OTHER_EXCEPTION.getCode(), message);
		}
	}

}

