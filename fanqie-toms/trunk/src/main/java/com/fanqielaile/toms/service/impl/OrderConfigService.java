package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.IOrderConfigDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OrderConfigDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.OrderConfig;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderConfigService;
import com.fanqielaile.toms.support.util.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/18
 * @version: v1.0.0
 */
@Service
public class OrderConfigService implements IOrderConfigService {
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOrderConfigDao orderConfigDao;
    @Resource
    private IOtaInfoDao otaInfoDao;

    @Override
    public List<OrderConfigDto> findOrderConfigByCompanyId( List<OtaInfoRefDto> list,UserInfo userInfo, PageBounds pageBounds) {
        List<OrderConfigDto> configDtoList = bangInnDao.selectBangInnList(userInfo, pageBounds);
        List<String> value = null;
        for (OrderConfigDto configDto:configDtoList){
            value = new ArrayList<String>();
            for (OtaInfoRefDto infoRefDto:list){
                configDto.setOtaInfoId(infoRefDto.getOtaInfoId());
                OrderConfigDto config = orderConfigDao.selectOrderConfigByOtaInfoId(configDto);
                if (config!=null && 1==config.getStatus()){
                    value.add(Constants.SD);
                }else {
                    value.add(Constants.ZD);
                }
            }
            configDto.setValue(value);
        }
        return configDtoList;
    }

    @Override
    public List<OrderConfigDto> findOrderConfigByCompanyIdAndInnId(String companyId, String innId) {
        List<OrderConfigDto> list = new ArrayList<OrderConfigDto>();
        List<OtaInfoRefDto> otaInfoRefDtoList = otaInfoDao.selectOtaInfoListByCompanyId(companyId);
        OrderConfig orderConfig=null;
        for (OtaInfoRefDto otaInfoRefDto:otaInfoRefDtoList){
            orderConfig = new OrderConfig(otaInfoRefDto.getOtaInfoId(),companyId,Integer.valueOf(innId));
            OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
            if (orderConfigDto==null){
                orderConfigDto = new OrderConfigDto(otaInfoRefDto.getOtaInfoId(),0,otaInfoRefDto.getOtaInfo());
                orderConfigDto.setOtaType(otaInfoRefDto.getOtaType());
            }
            list.add(orderConfigDto);
        }
        return list;
    }

    @Override
    public void saveOrderConfig(List<OrderConfigDto> list,String innId, String companyId) throws Exception {
         //删除之前的数据
         orderConfigDao.deletedOrderConfigByInnIdAndCompanyId(Integer.valueOf(innId),companyId);
         for (OrderConfigDto orderConfigDto:list){
             orderConfigDao.saveOrderConfig(orderConfigDto);
         }
    }

    @Override
    public List<OrderConfigDto> findFangPriceConfigByCompanyId(List<OtaInfoRefDto> list,  UserInfo currentUser, PageBounds pageBounds) {
        return  bangInnDao.selectBangInnList(currentUser, pageBounds);
    }


}
