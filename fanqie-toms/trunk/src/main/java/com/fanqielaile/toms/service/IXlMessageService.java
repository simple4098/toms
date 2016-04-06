package com.fanqielaile.toms.service;

import java.util.List;

import com.fanqielaile.toms.dto.xl.ChangePriceMessageDto;
import com.fanqielaile.toms.model.MessageParam;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
 * DESC : 西旅定制服务
 * @author : xkj
 * @date : 2015/3/24
 * @version: v1.0.0
 */
public interface IXlMessageService {
	/**
	 * DESC : 根据指定条件获取未读改价消息条数
	 * @author : xkj
	 * @param param
	 * 			from to 不传查询所有时间范围内的消息
	 * @param user 
	 * 			companyId
	 * @return
	 */
	Integer getNoReadMessage(MessageParam param, UserInfo user);
	/**
	 * DESC : 根据指定条件获取改价消息列表
	 * @author : xkj
	 * @param param
	 * @param user 
	 * 			companyId
	 * @param pageBounds 
	 * 			page limit
	 * @return
	 */
	List<ChangePriceMessageDto> getChangePriceList(MessageParam param,UserInfo user, PageBounds pageBounds);
	/**
	 * DESC : 将所有未读改价消息置为已读
	 * @author : xkj
	 * @param param
	 * @param user 
	 * 			companyId
	 * @return
	 */
	void setChangPriceMsgStatus(MessageParam param, UserInfo user);
	/**
	 * DESC : 插入改价消息记录
	 * @author : xkj
	 * @param param
	 */
	void insertChangPriceMsg(ChangePriceMessageDto message);
	/**
	 * DESC : 根据jsonStr，添加改价消息属性
	 * @author : xkj
	 * @param content
	 * @return
	 */
	ChangePriceMessageDto initChangePriceMessageParam(String jsonStr);
	/**
	 * DESC : 根据list的改价信息id，将改价消息的状态置为已读
	 * @author : xkj
	 * @param list
	 */
	void setChangPriceMsgStatus(List<ChangePriceMessageDto> list);

}
