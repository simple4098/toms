package com.fanqielaile.toms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fanqielaile.toms.dao.MessageDao;
import com.fanqielaile.toms.dto.xl.ChangePriceMessageDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.MessageParam;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.IXlMessageService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
/**
 * DESC : 西旅定制实现类
 */
@Service
public class XlMessageService implements IXlMessageService {
	
	@Resource
	MessageDao messageDao;
	@Resource
	ICompanyService companyService;

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Integer getNoReadMessage(MessageParam param,UserInfo user) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		param.setDataPermission(user.getDataPermission());
		param.setUserId(user.getId());
		param.setStatus(false);
		param.setCompanyType(user.getCompanyType().name());
		return messageDao.getChangePriceMessageCount(param);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<ChangePriceMessageDto> getChangePriceList(MessageParam param,UserInfo user, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		param.setDataPermission(user.getDataPermission());
		param.setUserId(user.getId());
		param.setCompanyType(user.getCompanyType().name());
		return messageDao.getChangePriceMessageList(param,pageBounds);
	}

	@Override
	public void setChangPriceMsgStatus(MessageParam param, UserInfo user) {
		// TODO Auto-generated method stub
		param.setCompanyId(user.getCompanyId());
		param.setDataPermission(user.getDataPermission());
		param.setUserId(user.getId());
		param.setStatus(true);
		param.setCompanyType(user.getCompanyType().name());
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
		JSONObject jsonObject=JSONObject.parseObject(jsonStr);
		ChangePriceMessageDto message = new ChangePriceMessageDto();
		message.setInnId(jsonObject.getInteger("innId"));
		message.setInnName(jsonObject.getString("innName"));
		message.setContext(jsonObject.getString("context"));
		if(jsonObject.getString("userCode").equals("DX") || jsonObject.getString("userCode").equals("XYZ")){
			message.setCompanyType("SALE");
		}else if(jsonObject.getString("userCode").equals("KFPT")){
			message.setCompanyType("OPEN");
		}
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
