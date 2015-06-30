package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.InnCustomer;
import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOperateTrendService;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
@Service
public class OperateTrendService implements IOperateTrendService {

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
        OperateTrend operateTrend = JacksonUtil.json2obj(gets, OperateTrend.class);
        return operateTrend;
    }

    @Override
    public Map<String, Object> findQsDetail(ParamDto paramDto, UserInfo currentUser) throws Exception {
        paramDto.setUserId(currentUser.getId());
        paramDto.setCompanyId(currentUser.getCompanyId());
        paramDto.setDataPermission(currentUser.getDataPermission() == 1);
        String gets = HttpClientUtil.httpGets(CommonApi.QSDetail, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(gets);
        Map<String, Object> result = (Map<String, Object>)jsonObject.get("result");
        return result;
    }
}
