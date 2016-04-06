package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtherConsumerInfoDao;
import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.OtherConsumerFunction;
import com.fanqielaile.toms.model.OtherConsumerInfo;
import com.fanqielaile.toms.service.IOtherConsumerInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/29
 * @version: v1.0.0
 */
@Service
public class OtherConsumerInfoService implements IOtherConsumerInfoService {
    @Resource
    private IOtherConsumerInfoDao otherConsumerInfoDao;

    @Override
    public List<OtherConsumerInfoDto> findOtherConsumerInfo(String companyId) {
        List<OtherConsumerInfoDto> list = new ArrayList<>();
        List<OtherConsumerInfo> consumerInfos = otherConsumerInfoDao.selectConsumerInfo(companyId, null);
        if (!CollectionUtils.isEmpty(consumerInfos)){
            OtherConsumerInfoDto consumerInfoDto = null;
            for (OtherConsumerInfo info:consumerInfos){
                consumerInfoDto = new OtherConsumerInfoDto();
                BeanUtils.copyProperties(info, consumerInfoDto);
                List<OtherConsumerInfo> infos = otherConsumerInfoDao.selectConsumerInfo(companyId, info.getId());
                consumerInfoDto.setOtherList(infos);
                list.add(consumerInfoDto);
            }
        }
        return list;
    }

    @Override
    public boolean findOtherConsumerFunction(String companyId) {
        OtherConsumerFunction otherConsumerFunction = otherConsumerInfoDao.selectFunction(companyId);
        if (otherConsumerFunction!=null && otherConsumerFunction.getStatus()){
            return true;
        }
        return false;
    }
}
