package com.fanqielaile.toms.controller;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * DESC : oms 调的接口
 *
 * @author : 番茄木-ZLin
 * @data : 2015/7/27
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/fanqieService")
public class RouterRestController {
    private static final Logger log = LoggerFactory.getLogger(RouterRestController.class);
    @Resource
    private IOtaInfoService otaInfoService;
    @Resource
    private IOrderService orderService;

    @RequestMapping("/PushRoomType")
    @ResponseBody
    public Object pushRoomType(@RequestParam String pushXml) {
        List<PushRoom> pushRoomList = null;
        Result result = new Result();
        try {
            pushRoomList = XmlDeal.getPushRoom(pushXml);
            log.info("===========start=============="+DateUtil.format(new Date(),"yyyyMMddHHmm")+"accountIdList:"+ TomsUtil.pushXml(pushRoomList));
            log.info(pushXml);
            List<OtaInfoRefDto> infoDtoList = otaInfoService.findOtaInfoList();
            ITPService service = null;
            for (OtaInfoRefDto o : infoDtoList) {
                service = o.getOtaType().create();
                service.updateHotelRoom(o, pushRoomList);
            }
            result.setMessage(Constants.MESSAGE_SUCCESS);
            result.setStatus("200");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("400");
        }

        return result;
    }

    /**
     * 推送订单状态
     *
     * @param pushXml
     * @return
     */
    @RequestMapping(value = "/OrderStatus")
    @ResponseBody
    public Object pushOrderStatus(@RequestParam String pushXml) {
        Result result = new Result();
        try {
            this.orderService.pushOrderStatusMethod(pushXml);
            result.setResultCode("200");
            result.setMessage(Constants.MESSAGE_SUCCESS);
        } catch (Exception e) {
            log.info("推送订单状态出错," + e);
            result.setMessage(e.getMessage());
            result.setResultCode("400");
        }
        return result;
    }
}
