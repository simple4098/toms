package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtherConsumerInfoDao;
import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.OtherConsumerFunction;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOtherConsumerInfoService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
    public List<OtherConsumerInfoDto> findOtherConsumerInfo(String companyId,String consumerInfoId) {
        List<OtherConsumerInfoDto> list = new ArrayList<>();
        List<OtherConsumerInfoDto> consumerInfos = null;
        if (StringUtils.isNotEmpty(consumerInfoId)){
            consumerInfos = otherConsumerInfoDao.selectConsumerInfoById(consumerInfoId);
        }else {
            consumerInfos = otherConsumerInfoDao.selectConsumerInfo(companyId, null);
        }
        if (!CollectionUtils.isEmpty(consumerInfos)){
            OtherConsumerInfoDto consumerInfoDto = null;
            for (OtherConsumerInfoDto info:consumerInfos){
                consumerInfoDto = new OtherConsumerInfoDto();
                BeanUtils.copyProperties(info, consumerInfoDto);
                List<OtherConsumerInfoDto> infoDtoList = otherConsumerInfoDao.selectConsumerInfo(companyId, info.getId());
                if (!CollectionUtils.isEmpty(infoDtoList)){
                    for (OtherConsumerInfoDto infoDto:infoDtoList){
                        infoDto.setConsumerProjectName(info.getConsumerProjectName());
                    }
                }
                consumerInfoDto.setOtherList(infoDtoList);
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

    @Override
    public void saveOtherConsumerInfo(OtherConsumerInfoDto priceRecordJsonBeans,UserInfo currentUser)throws Exception {
        Result result = new Result();
        priceRecordJsonBeans.setCompanyId(currentUser.getCompanyId());
        priceRecordJsonBeans.setCreatorId(currentUser.getId());
        priceRecordJsonBeans.setModifierId(currentUser.getId());
        OtherConsumerFunction otherConsumerFunction = otherConsumerInfoDao.selectFunction(currentUser.getCompanyId());
        List<OtherConsumerInfoDto> list = otherConsumerInfoDao.selectConsumerInfo(priceRecordJsonBeans.getCompanyId(), null);
        if (CollectionUtils.isEmpty(list) || (!CollectionUtils.isEmpty(list) && list.size()<5)){
            priceRecordJsonBeans.setConsumerFunId(otherConsumerFunction.getId());
            priceRecordJsonBeans.setLevel(1);
            OtherConsumerInfoDto consumerInfoDto = otherConsumerInfoDao.selectConsumerInfoByProjectName(currentUser.getCompanyId(),priceRecordJsonBeans.getConsumerProjectName());
            if (consumerInfoDto!=null){
                throw  new TomsRuntimeException("项目名称不能相同");
            }
            otherConsumerInfoDao.saveConsumerInfo(priceRecordJsonBeans);
            List<OtherConsumerInfoDto> otherList = priceRecordJsonBeans.getOtherList();
            if (!CollectionUtils.isEmpty(otherList) && otherList.size()<=5) {
                for (OtherConsumerInfoDto dto : otherList) {
                    OtherConsumerInfoDto otherConsumerInfoDto = otherConsumerInfoDao.selectConsumerInfoByPriceName(currentUser.getCompanyId(), dto.getPriceName(),priceRecordJsonBeans.getUuid());
                    if (otherConsumerInfoDto!=null){
                        throw  new TomsRuntimeException("价格名称不能相同");
                    }
                    dto.setConsumerFunId(otherConsumerFunction.getId());
                    dto.setParentId(priceRecordJsonBeans.getUuid());
                    dto.setLevel(2);
                    dto.setCompanyId(currentUser.getCompanyId());
                    otherConsumerInfoDao.saveConsumerInfo(dto);
                }
                result.setStatus(Constants.SUCCESS200);
            }else {
                throw  new TomsRuntimeException("不能超过5个消费价格类型");
            }
        }else {
            throw  new TomsRuntimeException("不能超过5个消费项目");
        }
    }

    @Override
    public void updateOtherConsumerInfo(OtherConsumerInfoDto priceRecordJsonBeans, UserInfo currentUser)throws Exception {
        priceRecordJsonBeans.setCompanyId(currentUser.getCompanyId());
        priceRecordJsonBeans.setCreatorId(currentUser.getId());
        priceRecordJsonBeans.setModifierId(currentUser.getId());
        OtherConsumerInfoDto otherConsumerInfoDto = otherConsumerInfoDao.selectConsumerInfoById(priceRecordJsonBeans.getId()).get(0);
        OtherConsumerInfoDto projectName = otherConsumerInfoDao.selectConsumerInfoByProjectName(currentUser.getCompanyId(),priceRecordJsonBeans.getConsumerProjectName());
        if (projectName!=null && !projectName.getId().equals(priceRecordJsonBeans.getId())){
            throw  new TomsRuntimeException("项目名称不能相同!");
        }
        otherConsumerInfoDao.updateOtherConsumerInfo(priceRecordJsonBeans);
        otherConsumerInfoDao.removeConsumerInfoByParentId(priceRecordJsonBeans.getId());
        List<OtherConsumerInfoDto> otherList = priceRecordJsonBeans.getOtherList();
        boolean b = TomsUtil.otherConsumer(otherList);
        if (!b){
            throw  new TomsRuntimeException("价格名称不能相同");
        }
        for (OtherConsumerInfoDto dto:otherList){
            OtherConsumerInfoDto consumerInfoDto = otherConsumerInfoDao.selectConsumerInfoByPriceName(currentUser.getCompanyId(), dto.getPriceName(),priceRecordJsonBeans.getId());
            if (StringUtils.isNotEmpty(dto.getId())){
                otherConsumerInfoDao.updateOtherConsumerInfo(dto);
            }else {
                if (consumerInfoDto!=null){
                    throw  new TomsRuntimeException("价格名称不能相同");
                }
                dto.setParentId(priceRecordJsonBeans.getId());
                dto.setLevel(2);
                dto.setConsumerFunId(otherConsumerInfoDto.getConsumerFunId());
                dto.setCompanyId(otherConsumerInfoDto.getCompanyId());
                otherConsumerInfoDao.saveConsumerInfo(dto);
            }

        }
    }

    @Override
    public void deleteOtherConsumerInfo(String consumerInfoId) throws Exception {
    	//获取该消费项目订单记录数
        otherConsumerInfoDao.deleteOtherConsumerInfo(consumerInfoId);
    }
    
    @Override
    public OtherConsumerInfoDto findChildOtherConsumerInfo(String companyId) {
        OtherConsumerInfoDto otherConsumerInfoDto = new OtherConsumerInfoDto();
        List<OtherConsumerInfoDto> list = new ArrayList<>();
        List<OtherConsumerInfoDto> consumerInfos  = otherConsumerInfoDao.selectConsumerInfo(companyId, null);
        if (!CollectionUtils.isEmpty(consumerInfos)){
            for (OtherConsumerInfoDto info:consumerInfos){
                List<OtherConsumerInfoDto> infoDtoList = otherConsumerInfoDao.selectConsumerInfo(companyId, info.getId());
                if (!CollectionUtils.isEmpty(infoDtoList)){
                    for (OtherConsumerInfoDto infoDto:infoDtoList){
                        infoDto.setConsumerProjectName(info.getConsumerProjectName());
                    }
                }

                list.addAll(infoDtoList);
            }
            otherConsumerInfoDto.setOtherList(list);
        }
        return otherConsumerInfoDto;
    }

    @Override
    public void updateFunction(String companyId, String status) {
        OtherConsumerFunction otherConsumerFunction = otherConsumerInfoDao.selectFunction(companyId);
        if (otherConsumerFunction!=null){
            otherConsumerFunction.setStatus(Boolean.valueOf(status));
            otherConsumerInfoDao.updateFunction(otherConsumerFunction);
        }else {
            otherConsumerFunction = new OtherConsumerFunction();
            otherConsumerFunction.setStatus(Boolean.valueOf(status));
            otherConsumerFunction.setCompanyId(companyId);
            otherConsumerInfoDao.insertConsumerFunction(otherConsumerFunction);
        }
    }

	@Override
	public Integer getOrderRecordCount(String consumerInfoId) {
		return otherConsumerInfoDao.getOrderRecordCount(consumerInfoId);
	}
}
