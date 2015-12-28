package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IFcRatePlanDao;
import com.fanqielaile.toms.dao.IFcRoomTypeFqDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.service.IFcRatePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
@Service
public class FcRatePlanService implements IFcRatePlanService {
    private static final Logger log = LoggerFactory.getLogger(FcRatePlanService.class);
    @Resource
    private IFcRatePlanDao fcRatePlanDao;
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;
    @Resource
    private IOtaInfoDao otaInfoDao;

    @Override
    public void saveFcRatePlan(OtaRatePlan otaRatePlan) {
        List<OtaRatePlan> ratePlans = fcRatePlanDao.selectFcRatePlan(otaRatePlan);
        if (CollectionUtils.isEmpty(ratePlans)){
            fcRatePlanDao.insertFcRatePlan(otaRatePlan);
        }

    }

    @Override
    public List<OtaRatePlan> findFcRatePlan(OtaRatePlan otaRatePlan) {
        return fcRatePlanDao.selectFcRatePlan(otaRatePlan);
    }

    @Override
    public void deletedRatePlan(String companyId,String ratePlanId)throws Exception{

        /*OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.FC.name());*/
        List<FcRoomTypeFqDto> list = fcRoomTypeFqDao.selectFcRoomTypeFqByRatePlanId(ratePlanId);
        if (!CollectionUtils.isEmpty(list)){
            throw  new Exception("此价格计划已经在使用,请勿删除");
            /*List<RatePlan> ratePlanInfoList = null;
            RatePlan ratePlan = null;
            DeleteRatePlanInfoRequest deleteRatePlanInfoRequest=null;
            DeleteRatePlanRequest deleteRatePlanRequest = null;
            Header header = null;
            for (FcRoomTypeFqDto roomTypeFqDto:list){
                ratePlanInfoList = new ArrayList<RatePlan>();
                ratePlan = new RatePlan();
                ratePlan.setSpRatePlanId(roomTypeFqDto.getFcRatePlanDto().getRatePlanId());
                ratePlanInfoList.add(ratePlan);

                deleteRatePlanInfoRequest = new DeleteRatePlanInfoRequest();
                deleteRatePlanInfoRequest.setSpHotelId(roomTypeFqDto.getInnId());
                deleteRatePlanInfoRequest.setSpRoomTypeId(roomTypeFqDto.getFqRoomTypeId());
                deleteRatePlanInfoRequest.setRatePlanInfoList(ratePlanInfoList);
                deleteRatePlanRequest = new DeleteRatePlanRequest();
                header = new Header(RequestType.deleteRatePlan, dto.getAppKey(), dto.getAppSecret());
                deleteRatePlanRequest.setHeader(header);
                deleteRatePlanRequest.setDeleteRatePlanInfoRequest(deleteRatePlanInfoRequest);
                String xml = FcUtil.fcRequest(deleteRatePlanRequest);
                log.info("删除价格计划xml:"+xml);
                String result = HttpClientUtil.httpPost(CommonApi.FcDelSyncRatePlanUrl, xml);
                log.info("fc result :"+result);
                Response response = XmlDeal.pareFcResult(result);
                if (Constants.FcResultNo.equals(response.getResultNo())){
                    fcRatePlanDao.deletedRatePlan(ratePlanId);
                    fcRoomTypeFqDao.updateRoomTypeFqRatePlan(roomTypeFqDto.getId(), null);
                    fcRoomTypeFqDao.updateRoomTypeFqSj(roomTypeFqDto.getId(), Constants.FC_XJ);
                }else {
                    throw  new Exception("删除价格计划失败:"+response.getResultMsg());
                }

            }*/
        }else {
            fcRatePlanDao.deletedRatePlan(ratePlanId);
        }

    }
}
