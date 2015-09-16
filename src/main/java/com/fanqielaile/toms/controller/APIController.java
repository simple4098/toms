package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.service.ICommissionService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FileDealUtil;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.ResourceBundleUtil;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * DESC : 对接TB ... controller
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
    @Resource
    private IOrderService orderService;

    /**
     * 客栈上架、下架
     * @param tbParam
     * @return
     */
    @RequestMapping(value = "/hotel/update",method = RequestMethod.POST)
    @ResponseBody
    public Object hotel(TBParam tbParam){
        JsonModel jsonModel = new JsonModel(true,Constants.MESSAGE_SUCCESS);
        boolean validateParam = DcUtil.validateParam(tbParam);
        if (!validateParam){
            jsonModel.setMessage(Constants.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        List<OtaInfoRefDto> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
        try {
            ITPService service = null;
            for (OtaInfoRefDto o:list){
                service = o.getOtaType().create();
                service.updateOrAddHotel(tbParam,o);
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
    public Object del(TBParam tbParam){
        JsonModel jsonModel = new JsonModel(true,Constants.MESSAGE_SUCCESS);
        if ( StringUtils.isEmpty(tbParam.getCompanyCode()) || StringUtils.isEmpty(tbParam.getInnId())){
            jsonModel.setMessage(Constants.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        List<OtaInfoRefDto> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
        try {
            ITPService service = null;
            for (OtaInfoRefDto o:list){
                service = o.getOtaType().create();
                service.deleteHotel(tbParam, o);
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
    public Object hotelTimer(final TBParam tbParam ){
        JsonModel jsonModel = new JsonModel(true,Constants.MESSAGE_SUCCESS);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<OtaInfoRefDto> infoDtoList = otaInfoService.findOtaInfoList();
                try {
                    ITPService service = null;
                    for (OtaInfoRefDto o:infoDtoList){
                        service = o.getOtaType().create();
                        service.updateHotel(o ,tbParam);
                    }
                } catch (Exception e) {
                   throw  new TomsRuntimeException("同步房型失败",e);
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return  jsonModel;
    }

    /**
     * 更新客栈的佣金
     * @param tbParam
     */
    @RequestMapping("/commission/update")
    @ResponseBody
    public Object commissionUpdate(TBParam tbParam){
        JsonModel jsonModel = new JsonModel(true,Constants.MESSAGE_SUCCESS);
        if(!StringUtils.isEmpty(tbParam.getCompanyCode()) && !StringUtils.isEmpty(tbParam.getCommissionType())){
            commissionService.updateCommission(tbParam);
        }else {
            jsonModel.setMessage(Constants.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        return  jsonModel;
    }

    /**
     * 获取天下房仓增量酒店，房型信息
     */
    @RequestMapping("/get_fc_hotel_info")
    public void getFcHotelInfo() throws DocumentException {
        log.info("======开始获取天下房仓增量数据========");
        UploadStatus uploadStatus = this.orderService.getFcAddHotelInfo();
        //不在一个事务里面处理
        if (uploadStatus.equals(UploadStatus.Upload_New_File_Success)) {
            //先删除zip包
            File zipFile = new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd") + "\\" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip");
            zipFile.delete();
            //再删除文件和目录
            FileDealUtil.deleteDir(new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd")));
        }
        log.info("=====处理增量数据结束========");
    }
}
