package com.fanqielaile.toms.dao;

import java.util.List;

import com.fanqielaile.toms.dto.xl.ChangePriceMessageDto;
import com.fanqielaile.toms.model.MessageParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface MessageDao {
	/**
	 * DESC : 获取一段时间内未读改价消息
	 * @author : xkj
	 * @param
	 * 		from to companyId
	 * @return
	 */
	Integer getNoReadChangePriceMessage(MessageParam param);
	/**
	 * DESC : 根据给定条件统计未读改价消息条数
	 * @author : xkj
	 * @param param 
	 * 			from to companyId status
	 * @return
	 */
	Integer getChangePriceMessageCount(MessageParam param);
	/**
	 * DESC : 根据给定条件统计未读改价消息条数
	 * @author : xkj
	 * @param param 
	 * 			companyId
	 * @param pageBounds
	 * 			page limit
	 * @return
	 */
	List<ChangePriceMessageDto> getChangePriceMessageList(MessageParam param, PageBounds pageBounds);
	/**
	 * DESC : 将所有的改价消息状态置为已读
	 * @author : xkj
	 * @param param 
	 * 			companyId status 
	 */
	void setChangPriceMsgStatus(MessageParam param);
	/**
	 * DESC : 插入改价消息记录
	 * @author : xkj
	 * @param message 
	 */
	void insertChangPriceMsg(ChangePriceMessageDto message);
	/**
	 * DESC : 根据list的改价信息id，将改价消息的状态置为已读
	 * @author : xkj
	 * @param list
	 */
	void setChangPriceMsgStatusById(List<ChangePriceMessageDto> list);
}
