package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IPushCreditService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * DESC : 手动执行
 * @author : 番茄木-ZLin
 * @data : 2015/10/13
 * @version: v1.0.0
 */
@Controller()
@RequestMapping("/mt")
public class MtController {

    private static final Logger logger = LoggerFactory.getLogger(MtController.class);
    @Resource
    private IPushCreditService pushCreditService;
    @RequestMapping("/view")
    public String mt(){

        return "/mt_view";
    }

    @RequestMapping("/credit")
    @ResponseBody
    public Object pushCredit(String innIds){
        Result result = new Result();
        logger.info("手动更新信用住:"+innIds);
        if (StringUtils.isNotEmpty(innIds)){
            String[] split = innIds.split(",");
            if (split.length<=50){
                try {
                    pushCreditService.pushCredit(innIds);
                    result.setStatus(Constants.SUCCESS200);
                } catch (Exception e) {
                    result.setStatus(Constants.ERROR400);
                    result.setMessage(e.getMessage());
                }
            }else {
                result.setStatus(Constants.ERROR400);
                result.setMessage("innId 不能超过50个");
            }
        }else {
            result.setStatus(Constants.ERROR400);
            result.setMessage("innId 不能为空");
        }
        return result;
    }
}
