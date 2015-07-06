package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoDto;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.service.ICommissionService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITBService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.util.JsonModel;
import com.tomato.log.model.BusinLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private ICommissionService commissionService;
    @Resource
    private IOtaInfoService otaInfoService;

    /**
     * 客栈上架、下架
     * @param tbParam
     * @param businLog
     * @return
     */
    @RequestMapping(value = "/hotel/update",method = RequestMethod.POST)
    @ResponseBody
    public Object hotel(TBParam tbParam,BusinLog businLog){
        JsonModel jsonModel = new JsonModel(true,CommonApi.MESSAGE_SUCCESS);
        boolean validateParam = DcUtil.validateParam(tbParam);
        if (!validateParam){
            jsonModel.setMessage(CommonApi.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        List<OtaInfo> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
        try {
            ITPService service = null;
            for (OtaInfo o:list){
                service = o.getOtaType().create();
                service.updateOrAddHotel(tbParam, businLog,o);
            }
        } catch (Exception e) {
            jsonModel.setMessage(e.getMessage());
            jsonModel.setSuccess(false);
            log.error(e.getMessage(),e);
        }
        return  jsonModel;
    }

    /**
     * 删除客栈
     * @param tbParam 绿番茄参数
     */
    @RequestMapping("/hotel/del")
    @ResponseBody
    public Object del(TBParam tbParam,BusinLog businLog){
        JsonModel jsonModel = new JsonModel(true,CommonApi.MESSAGE_SUCCESS);
        if ( StringUtils.isEmpty(tbParam.getCompanyCode()) || StringUtils.isEmpty(tbParam.getInnId())){
            jsonModel.setMessage(CommonApi.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        List<OtaInfo> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
        try {
            ITPService service = null;
            for (OtaInfo o:list){
                service = o.getOtaType().create();
                service.deleteHotel(tbParam, businLog,o);
            }
        } catch (Exception e) {
            jsonModel.setMessage(e.getMessage());
            jsonModel.setSuccess(false);
            log.error(e.getMessage());
        }
        return  jsonModel;
    }

    /**
     * 定时更新酒店
     */
    @RequestMapping("/hotel/timer")
    @ResponseBody
    public Object hotelTimer(BusinLog businLog,TBParam tbParam){
        JsonModel jsonModel = new JsonModel(true,CommonApi.MESSAGE_SUCCESS);
        List<OtaInfoDto> infoDtoList = otaInfoService.findOtaInfoList();
        try {
            ITPService service = null;
            for (OtaInfoDto o:infoDtoList){
                service = o.getOtaType().create();
                service.updateHotel(o, businLog, tbParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonModel;
    }

    /**
     * 更新客栈的佣金
     * @param tbParam
     */
    @RequestMapping("/commission/update")
    @ResponseBody
    public Object commissionUpdate(TBParam tbParam){
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
