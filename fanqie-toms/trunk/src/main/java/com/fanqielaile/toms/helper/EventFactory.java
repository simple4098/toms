package com.fanqielaile.toms.helper;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/3/15
 * @version: v1.0.0
 */
@Component
public class EventFactory {
    private static  final Logger log = LoggerFactory.getLogger(EventFactory.class);
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IOtaInfoService otaInfoService;

    public void pushEvent(JSONObject jsonObject){
        String bizType = jsonObject.getString("bizType");
        String content = jsonObject.getString("content");
        if (Constants.INN_UP_DOWN.equals(bizType)){
            log.info("=====队列增加数据=================="+content);
            ListOperations<String, String> operations = redisTemplate.opsForList();
            operations.leftPush(Constants.REDIS,content);
        }
        if (Constants.CREDIT.equals(bizType)){
            List<OtaInfoRefDto> list = otaInfoService.findAllOtaByType(OtaType.CREDIT);
            ITPService service = null;
            TBParam tbParam = null;
            for (OtaInfoRefDto infoRefDto:list){
                tbParam = JacksonUtil.json2obj(content,TBParam.class);
                TomsUtil.toCredit(tbParam,infoRefDto.getCompanyCode());
                log.info("================加入信用住的客栈参数:"+JacksonUtil.obj2json(tbParam));
                service = infoRefDto.getOtaType().create();
                try {
                    service.updateOrAddHotel(tbParam,infoRefDto);
                } catch (Exception e) {
                    log.error("信用住异常",e);
                }
            }
        }
    }
}
