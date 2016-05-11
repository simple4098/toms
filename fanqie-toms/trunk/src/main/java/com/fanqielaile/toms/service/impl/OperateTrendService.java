package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.InnCustomer;
import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.OrderGuestsDao;
import com.fanqielaile.toms.dto.xl.CustomerAnalysisDto;
import com.fanqielaile.toms.dto.xl.CustomerParamDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOperateTrendService;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
@Service
public class OperateTrendService implements IOperateTrendService {
	
	@Resource
	private OrderGuestsDao orderGuestsDao;

    @Override
    public CustomerDto findCustomer(ParamDto paramDto,UserInfo userInfo)throws Exception{
        paramDto.setUserId(userInfo.getId());
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setDataPermission(userInfo.getDataPermission() == 1);
        DateTime dateTime = DateUtil.addDate(-1);
        paramDto.setStartDate((paramDto.getStartDate() == null ||paramDto.getStartDate()=="") ? DateUtil.formatDateToString(dateTime.toDate(), "yyyy-MM-dd"):paramDto.getStartDate());
        paramDto.setEndDate((paramDto.getEndDate() == null ||paramDto.getEndDate()=="") ? DateUtil.formatDateToString(dateTime.toDate(), "yyyy-MM-dd"):paramDto.getEndDate());
        String kf = HttpClientUtil.httpGets(CommonApi.KF, paramDto);
        String kf_d = HttpClientUtil.httpGets(CommonApi.KF_D, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(kf);
        JSONObject kfDObject = JSONObject.fromObject(kf_d);
        Pagination pagination = JacksonUtil.json2obj(kfDObject.get("pagination").toString(), Pagination.class);
        List<InnCustomer> innCustomer  = JacksonUtil.json2list(kfDObject.get("rows").toString(), InnCustomer.class);
        Integer totalCityNum =(Integer) jsonObject.get("totalCityNum");
        Integer totalNum =(Integer)jsonObject.get("totalNum");
        return new CustomerDto(totalCityNum,totalNum,innCustomer,pagination);
    }

    @Override
    public OperateTrend findOperateTrend(ParamDto paramDto, UserInfo currentUser) throws Exception {
        paramDto.setUserId(currentUser.getId());
        paramDto.setCompanyId(currentUser.getCompanyId());
        paramDto.setDataPermission(currentUser.getDataPermission() == 1);
        String gets = HttpClientUtil.httpGets(CommonApi.QS, paramDto);
        if(!StringUtils.isEmpty(gets)){
            return JacksonUtil.json2obj(gets, OperateTrend.class);
        }
        return null;

    }

    @Override
    public Map<String, Object> findQsDetail(ParamDto paramDto, UserInfo currentUser) throws Exception {
        paramDto.setUserId(currentUser.getId());
        paramDto.setCompanyId(currentUser.getCompanyId());
        paramDto.setDataPermission(currentUser.getDataPermission() == 1);
        String gets = HttpClientUtil.httpGets(CommonApi.QSDetail, paramDto);
        if(!StringUtils.isEmpty(gets)) {
            JSONObject jsonObject = JSONObject.fromObject(gets);
            return  (Map<String, Object>) jsonObject.get("result");
        }else {
            return null;
        }
    }

	@Override
	public List<CustomerAnalysisDto> selectProvinceGuestNumList(CustomerParamDto customerParamDto, UserInfo currentUser,
			PageBounds pageBounds) {
		return orderGuestsDao.selectProvinceGuestNumList(customerParamDto,currentUser,pageBounds);
	}

	@Override
	public List<CustomerAnalysisDto> selectCityGuestNumList(CustomerParamDto customerParamDto, UserInfo currentUser,
			PageBounds pageBounds) {
		return orderGuestsDao.selectCityGuestNumList(customerParamDto,currentUser,pageBounds);
	}

	@Override
	public void initCustomerParam(CustomerParamDto customerParamDto) {
		// TODO Auto-generated method stub
		if(customerParamDto.getCityPage() == null){
			customerParamDto.setCityPage(1);
			customerParamDto.setPage(1);
		}
		Date date = new Date();
		if(customerParamDto.getStartDate() == null || customerParamDto.getEndDate() == null){
			customerParamDto.setStartDate(DateUtils.format(date, "yyyy-MM")+"-01");
			customerParamDto.setEndDate(DateUtils.format(date, "yyyy-MM-dd"));
		}
	}

	@Override
	public Integer getTotalGuestCount(CustomerParamDto customerParamDto, UserInfo currentUser) {
		// TODO Auto-generated method stub
		return orderGuestsDao. getTotalGuestCount(customerParamDto,currentUser);
	}
}
