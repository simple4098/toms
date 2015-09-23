package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IFcRatePlanDao;
import com.fanqielaile.toms.dao.IFcRoomTypeFqDao;
import com.fanqielaile.toms.dto.fc.FcRatePlanDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.model.fc.FcRatePlan;
import com.fanqielaile.toms.model.fc.FcRoomTypeFq;
import com.fanqielaile.toms.service.IFcRoomTypeFqService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/23
 * @version: v1.0.0
 */
@Service
public class FcRoomTypeFqService implements IFcRoomTypeFqService {
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;
    @Resource
    private IFcRatePlanDao fcRatePlanDao;

    @Override
    public List<FcRoomTypeFqDto> findFcRoomTypeFq(FcRoomTypeFqDto fcRoomTypeFq) {
        Assert.hasText(fcRoomTypeFq.getCompanyId());
        Assert.hasText(fcRoomTypeFq.getInnId());
        Assert.hasText(fcRoomTypeFq.getOtaInfoId());
        return fcRoomTypeFqDao.selectFcRoomTypeFq(fcRoomTypeFq);
    }

    @Override
    public void updateRoomTypeRatePlan(String fcRoomTypeFqId, String ratePlanId)throws Exception{
        FcRoomTypeFq fcRoomTypeFq = fcRoomTypeFqDao.selectFcRoomTypeFqById(fcRoomTypeFqId);
        FcRatePlan fcRatePlan =  fcRatePlanDao.selectFcRatePlanById(ratePlanId);
        String innId = fcRoomTypeFq.getInnId();
        String roomTypeId =fcRoomTypeFq.getFqRoomTypeId();
        //todo 同步到房仓

        fcRoomTypeFqDao.updateRoomTypeFqRatePlan(fcRoomTypeFqId,ratePlanId);


    }
}
