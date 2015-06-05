package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.InnCustomer;
import com.fanqie.core.dto.InnActiveDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dto.ActiveInnDto;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IInnActiveService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/1
 * @version: v1.0.0
 */
@Service
public class InnActiveService implements IInnActiveService {
    @Resource
    private BangInnDao bangInnDao;
    @Override
    public ActiveInnDto findActiveInnDto(ParamDto paramDto,UserInfo userInfo)throws Exception{
        List<Integer> innIds = new ArrayList<Integer>();
        StringBuilder sb = new StringBuilder();
        List<BangInnDto> bangInns = bangInnDao.selectBangInnListByUserInfo(userInfo,new PageBounds());
        for (BangInnDto bangInnDto:bangInns){
            innIds.add(bangInnDto.getInnId());
            sb.append(bangInnDto.getInnId()).append(",");
        }
        sb.deleteCharAt(sb.toString().length() - 1);
        paramDto.setInnInStr(sb.toString());
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setDataPermission(userInfo.getDataPermission() == 1);
        paramDto.setUserId(userInfo.getId());
        if (StringUtils.isEmpty(paramDto.getStartDate())){
            DateTime dateTime = DateUtil.addDate(-1);
            paramDto.setStartDate(DateUtil.formatDateToString(dateTime.toDate(),"yyyy-MM-dd"));
        }
        String post = HttpClientUtil.httpGets(CommonApi.ActiveInn, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(post);
        Object rows = jsonObject.get("rows");
        Object p = jsonObject.get("pagination");
        Pagination pagination = null;
        if (p!=null){
            pagination = JacksonUtil.json2obj(p.toString(), Pagination.class);
        }
        List<InnActiveDto> innCustomer = null;
        Integer maxLen = 0;
        if (rows!=null){
            innCustomer = JacksonUtil.json2list(rows.toString(), InnActiveDto.class);
            if(!CollectionUtils.isEmpty(innCustomer) && !CollectionUtils.isEmpty(innCustomer.get(0).getActiveList())){
                maxLen = innCustomer.get(0).getActiveList().size();
            }
        }
        return new ActiveInnDto(innCustomer,pagination,maxLen);
    }
}
