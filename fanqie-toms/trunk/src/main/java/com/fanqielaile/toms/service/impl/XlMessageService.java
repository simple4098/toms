package com.fanqielaile.toms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dao.MessageDao;
import com.fanqielaile.toms.dto.xl.ChangePriceMessageDto;
import com.fanqielaile.toms.model.MessageParam;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IXlMessageService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
/**
 * DESC : 西旅定制实现类
 */
@Service
public class XlMessageService implements IXlMessageService {
	
	@Resource
	MessageDao messageDao;

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Integer getNoReadMessage(MessageParam param,UserInfo user) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		param.setStatus(false);
		return messageDao.getChangePriceMessageCount(param);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<ChangePriceMessageDto> getChangePriceList(MessageParam param,UserInfo user, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		return messageDao.getChangePriceMessageList(param,pageBounds);
	}

	@Override
	public void setChangPriceMsgStatus(MessageParam param, UserInfo user) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		param.setStatus(true);
		messageDao.setChangPriceMsgStatus(param);
		return ;
	}

	@Override
	public void insertChangPriceMsg(ChangePriceMessageDto message) {
		// TODO Auto-generated method stub
		messageDao.insertChangPriceMsg(message);
	}

	@Override
	public ChangePriceMessageDto initChangePriceMessageParam(String jsonStr) {
		// TODO Auto-generated method stub
		ChangePriceMessageDto message=JacksonUtil.json2obj(jsonStr, ChangePriceMessageDto.class);
		message.setStatus(false);
		message.setId(UUID.randomUUID().toString());
		return message;
	}

	@Override
	public void setChangPriceMsgStatus(List<ChangePriceMessageDto> list) {
		// TODO Auto-generated method stub
		messageDao.setChangPriceMsgStatusById(list);
	}
	
}
