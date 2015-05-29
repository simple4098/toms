package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.InnCustomer;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.service.IOperateTrendService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
@Service
public class OperateTrendService implements IOperateTrendService {

    @Override
    public CustomerDto findCustomer(ParamDto paramDto)throws Exception{
        String kf = HttpClientUtil.httpPost(CommonApi.KF, paramDto);
        String kf_d = HttpClientUtil.httpPost(CommonApi.KF_D, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(kf);
        JSONObject kfDObject = JSONObject.fromObject(kf_d);
        Object rows = kfDObject.get("rows");
        Pagination pagination = JacksonUtil.json2obj(kfDObject.get("pagination").toString(), Pagination.class);
        List<InnCustomer> innCustomer  = JacksonUtil.json2list(rows.toString(), InnCustomer.class);
        Integer totalCityNum =(Integer) jsonObject.get("totalCityNum");
        Integer totalNum =(Integer)jsonObject.get("totalNum");
        return new CustomerDto(totalCityNum,totalNum,innCustomer,pagination);
    }
}
