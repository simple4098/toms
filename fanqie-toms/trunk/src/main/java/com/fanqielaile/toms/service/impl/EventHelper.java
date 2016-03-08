package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.TimerRateType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.TimerRatePrice;
import com.fanqielaile.toms.service.IEventHelper;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.CallableBean;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/7
 * @version: v1.0.0
 */
@Service
public class EventHelper implements IEventHelper {
    private static  final Logger log = LoggerFactory.getLogger(EventHelper.class);
    @Resource
    private IOtaInfoService otaInfoService;

    @Override
    public void pushEvent(JSONObject jsonObject) throws Exception {
        String bizType = jsonObject.getString("bizType");
        if (Constants.INN_UP_DOWN.equals(bizType)){
            String content = jsonObject.getString("content");
            TBParam tbParam = JacksonUtil.json2obj(content, TBParam.class);
            boolean validateParam = DcUtil.validateParam(tbParam);
            log.info("validateParam:"+validateParam+"推送参数APIController："+tbParam.toString()+" 企业唯一code"+tbParam.getCompanyCode()+" accountIdDi:"+tbParam.getAccountIdDi());
            if (!validateParam){
                return ;
            }
            int size = ResourceBundleUtil.getInt("threadNum");
            List<OtaInfoRefDto> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
            ExecutorService es = Executors.newFixedThreadPool(size);
            CompletionService cs = new ExecutorCompletionService(es);
            for (OtaInfoRefDto o : list) {
                cs.submit(getTask(tbParam,o));
            }
            es.shutdown();
        }

    }

    private Callable getTask(final TBParam tbParam, final OtaInfoRefDto o) {
        return new Callable<CallableBean>() {
            @Override
            public CallableBean call()  {
                ITPService service =  o.getOtaType().create();
                try {
                    service.updateOrAddHotel(tbParam, o);
                } catch (Exception e) {
                    log.error("EventHelper异常,",e);
                }
                return null;
            }
        };

    }
}
