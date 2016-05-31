package com.fanqielaile.toms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.TBParam;
import com.fanqie.qunar.enums.OptCode;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ParamJson;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.*;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.*;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DESC : 对接OTA ... controller
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/api")
public class APIController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(APIController.class);
    private final Lock lock = new ReentrantLock();
    @Resource
    private ICommissionService commissionService;
    @Resource
    private IOtaInfoService otaInfoService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IExceptionOrderService exceptionOrderService;


    /**
     * 客栈上架、下架
     *
     * @param tbParam 上架的参数（见文档）
     */
    @RequestMapping(value = "/hotel/update", method = RequestMethod.POST)
    @ResponseBody
    public Object hotel(TBParam tbParam) {
        JsonModel jsonModel = new JsonModel(true, Constants.MESSAGE_SUCCESS);
        boolean validateParam = DcUtil.validateParam(tbParam);
        log.info("推送参数APIController：" + tbParam.toString() + " 企业唯一code" + tbParam.getCompanyCode() + " accountIdDi:" + tbParam.getAccountIdDi());
        if (!validateParam) {
            jsonModel.setMessage(Constants.MESSAGE_ERROR);
            jsonModel.setSuccess(false);
            return jsonModel;
        }
        try {
            List<OtaInfoRefDto> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
            ITPService service = null;
            for (OtaInfoRefDto o : list) {
                service = o.getOtaType().create();
                service.updateOrAddHotel(tbParam, o);
            }
        } catch (Exception e) {
            jsonModel.setMessage(e.getMessage());
            jsonModel.setSuccess(false);
            log.error(e.getMessage(), e);
        }
        return jsonModel;
    }

    /**
     * 删除客栈
     * @param tbParam 绿番茄参数
     *//*
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
    }*/

    /**
     * 定时更新酒店
     */
    @RequestMapping("/hotel/timer")
    @ResponseBody
    public Object hotelTimer(final TBParam tbParam) {
        JsonModel jsonModel = new JsonModel(true, Constants.MESSAGE_SUCCESS);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<OtaInfoRefDto> infoDtoList = otaInfoService.findOtaInfoList();
                if (StringUtils.isNotEmpty(tbParam.getCompanyCode())) {
                    infoDtoList = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
                }
                try {
                    ITPService service = null;
                    for (OtaInfoRefDto o : infoDtoList) {
                        service = o.getOtaType().create();
                        service.updateHotel(o, tbParam);
                    }
                } catch (Exception e) {
                    throw new TomsRuntimeException("同步房型失败", e);
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return jsonModel;
    }

    /**
     * 定时更新未成功酒店
     */
    @RequestMapping("/hotelFailTimer")
    @ResponseBody
    public Object hotelFailTimer() {
        JsonModel jsonModel = new JsonModel(true, Constants.MESSAGE_SUCCESS);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<OtaInfoRefDto> infoDtoList = otaInfoService.findOtaInfoList();
                try {
                    ITPService service = null;
                    for (OtaInfoRefDto o : infoDtoList) {
                        log.info("hotelFailTimer START:" + JacksonUtil.obj2json(o));
                        service = o.getOtaType().create();
                        service.updateHotelFailTimer(o);
                        log.info("hotelFailTimer end:" + JacksonUtil.obj2json(o));
                    }
                } catch (Exception e) {
                    log.error("定时更新未成功酒店失败", e);
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return jsonModel;
    }

    /**
     * 更新客栈的佣金
     */
    @RequestMapping("/commission/update")
    @ResponseBody
    public Object commissionUpdate(String param) {
        JsonModel jsonModel = new JsonModel(true, Constants.MESSAGE_SUCCESS);
        ParamJson paramJson = JacksonUtil.json2obj(param, ParamJson.class);
        try {
            if (paramJson != null && !StringUtils.isEmpty(paramJson.getCompanyCode())) {
                commissionService.updateCommission(paramJson);
            } else {
                jsonModel.setMessage(Constants.MESSAGE_ERROR);
                jsonModel.setSuccess(false);
                return jsonModel;
            }
        } catch (Exception e) {
            jsonModel.setMessage(e.getMessage());
            jsonModel.setSuccess(false);
        }
        return jsonModel;
    }

    /**
     * 更新客栈房型下架
     */
    @RequestMapping("/sellingRoomType")
    @ResponseBody
    public Object sellingRoomType(final String from, final String to) {
        JsonModel jsonModel = new JsonModel(true, Constants.MESSAGE_SUCCESS);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<OtaInfoRefDto> infoDtoList = otaInfoService.findOtaInfoList();
                try {
                    ITPService service = null;
                    for (OtaInfoRefDto o : infoDtoList) {
                        service = o.getOtaType().create();
                        service.sellingRoomType(from, to, o);
                    }
                } catch (Exception e) {
                    throw new TomsRuntimeException("定时更新客栈下架房型", e);
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        return jsonModel;
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

    /**
     * 得到异常订单信息
     */
    @RequestMapping("/get_exception_order")
    @ResponseBody
    public Object taskCycle() {
        log.info(new Date() + "开始执行定时任务=======>");
        try {
            Map<String, String> map = TomsUtil.getFifteenDate();

            List<Order> exceptionOrderList = this.orderService.findExceptionOrderList(map);
            Order order = new Order().getOrderToExceptionOrder(exceptionOrderList);
            log.info("插入异常订单一共" + (null == order.getExceptionOrderList() ? 0 : order.getExceptionOrderList().size()));
            if (ArrayUtils.isNotEmpty(order.getExceptionOrderList().toArray())) {
                //插入之前先删除已经存在异常订单
                this.exceptionOrderService.deleteExceptionOrder(order);
                //插入异常订单
                this.exceptionOrderService.createExceptionOrder(order);
                //发送微信
                MessageCenterUtils.sendWeiXin("发现异常单,一共：" + order.getExceptionOrderList().size() + "单，请及时检查");
            }
        } catch (Exception e) {
            log.info("处理异常订单信息异常" + e);
            return false;
        }
        log.info(new Date() + "结束执行定时任务======>");
        return true;
    }

    /**
     * 定时任务执行同步订单状态
     *
     * @return
     */
    @RequestMapping(value = "/deal_order_status")
    @ResponseBody
    public Object taskDealOrderStatus() {
        log.info(new Date() + "开始执行同步订单状态定时任务======>");
        try {
            List<Order> orderList = this.orderService.findOtaPendingOrder();
            if (CollectionUtils.isNotEmpty(orderList)) {
                this.orderService.dealPendingOrderMethod(orderList);
            }
        } catch (Exception e) {
            log.error("执行同步订单状态定时任务异常！" + e);
            return false;
        }
        log.info(new Date() + "结束执行同步订单状态定时任务======>");
        return true;
    }

}
