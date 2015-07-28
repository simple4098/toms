package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC : oms 调的接口
 * @author : 番茄木-ZLin
 * @data : 2015/7/27
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/fanqieService")
public class RouterRestController {
    private static  final Logger log = LoggerFactory.getLogger(RouterRestController.class);
    @Resource
    private IOtaInfoService otaInfoService;

    @RequestMapping("/PushRoomType")
    @ResponseBody
    public Object pushRoomType(@RequestParam String pushXml){
        List<PushRoom> pushRoomList = null;
        Result result = new Result();
        try {
            pushRoomList = XmlDeal.getPushRoom(pushXml);
            List<OtaInfoDto> infoDtoList = otaInfoService.findOtaInfoList();
            ITPService service = null;
            for (OtaInfoDto o:infoDtoList){
                service = o.getOtaType().create();
                service.updateHotelRoom(o, pushRoomList);
            }
            result.setMessage(CommonApi.MESSAGE_SUCCESS);
            result.setStatus("200");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus("400");
        }

        return result;
    }
}
