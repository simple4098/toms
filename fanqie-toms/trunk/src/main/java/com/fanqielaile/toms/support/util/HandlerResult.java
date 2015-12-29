package com.fanqielaile.toms.support.util;

import javax.xml.bind.JAXBException;

import com.fanqie.bean.response.RequestResponse;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.exception.RequestCtripException;

public class HandlerResult {
	
	private static final int SUCCESS = 0;
	
	/**
	 *  处理携程返回
	 * @param resp
	 * @throws JAXBException
	 * @throws RequestCtripException 
	 * @throws CtripDataException 
	 */
	public static  void handerResultCode(String resp) throws JAXBException, RequestCtripException{
		RequestResponse rr  =   FcUtil.xMLStringToBean(resp);
		int  respCpde = rr.getRequestResult().getResultCode();
		if(respCpde!=SUCCESS){
			throw new RequestCtripException(rr.getRequestResult().getMessage());
		}
		
	}

}
