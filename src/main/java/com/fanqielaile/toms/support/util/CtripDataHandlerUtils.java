package com.fanqielaile.toms.support.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.support.SpringContextUtil;

public class CtripDataHandlerUtils {
	
	
	

	public static void checkChildHotelId(CtripHotelInfo hotelInfo,
			List<CtripRoomTypeMapping> crms, String ctripMasterHotelId) throws CtripDataException {
		if(null!=hotelInfo){ // 说明之前数据库存在当初携程反正的子酒店ID
			  if(!hotelInfo.getParentHotelId().equals(ctripMasterHotelId)){//  判断数据库里面存在的子酒店对应的母酒店ID，是否就是当前我们操作的母酒店
				  CtripRoomTypeMappingDao crmd = (CtripRoomTypeMappingDao) SpringContextUtil.getBean("ctripRoomTypeMappingDao");
				  for (CtripRoomTypeMapping ctripRoomTypeMapping : crms) {
					  if(StringUtils.isNotEmpty(ctripRoomTypeMapping.getId()))
						  crmd.deleteById(ctripRoomTypeMapping.getId());
				  }
				  throw new CtripDataException("携程酒店ID返回错误masterHotel:"+ctripMasterHotelId+"新的childHotelId:"+hotelInfo.getChildHotelId());
			  }
		}
	}
}
