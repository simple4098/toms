package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.service.ICommissionService;
import com.fanqielaile.toms.service.ITBService;
import com.fanqielaile.toms.support.util.JsonModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * DESC : 对接TB controller
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/api")
public class APIController extends BaseController {
    private static  final Logger log = LoggerFactory.getLogger(APIController.class);
    @Resource
    private ITBService tbService;
    @Resource
    private ICommissionService commissionService;

    @RequestMapping("/hotel/update")
    public JsonModel hotel(TBParam tbParam){
        JsonModel jsonModel = new JsonModel(true,CommonApi.MESSAGE_SUCCESS);
        boolean validateParam = DcUtil.validateParam(tbParam);
        if (!validateParam){
            jsonModel.setMessage(CommonApi.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        try {
            tbService.hotelAddOrUpdate(tbParam);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return  jsonModel;
    }

    @RequestMapping("/commission/update")
    @ResponseBody
    public Object commissionUpdate(TBParam tbParam){
        tbParam.setCompanyCode("11111111");
        tbParam.setCommissionPercent(new BigDecimal(0.5));
        tbParam.setCommissionType("MAI,DI");
        JsonModel jsonModel = new JsonModel(true,CommonApi.MESSAGE_SUCCESS);
        if(!StringUtils.isEmpty(tbParam.getCompanyCode()) && !StringUtils.isEmpty(tbParam.getCommissionType())){
            commissionService.updateCommission(tbParam);
        }else {
            jsonModel.setMessage(CommonApi.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        return  jsonModel;
    }
}
