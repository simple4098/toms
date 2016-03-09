/**
 * All rights owned by Niehui Technology
 * File name : com.qttecx.qlms.com.log.thread.GetLogThread.java
 */
package com.fanqielaile.toms.support.thread;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.service.impl.OtaInfoService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

public class SynchronousThread extends Thread {
    private static Logger log = Logger.getLogger(SynchronousThread.class);
    @SuppressWarnings("unchecked")
    private int taskNum;
    @Resource
    private IOtaInfoService otaInfoService;
    @Resource
    private StringRedisTemplate redisTemplate;
    public SynchronousThread(WebApplicationContext context, int num) {
        super();
        this.taskNum = num;
        redisTemplate = context.getBean("redisTemplate", StringRedisTemplate.class);
        otaInfoService = context.getBean("otaInfoService", OtaInfoService.class);
    }
    @Override
    public void run() {
    	log.info("线程名称："+Thread.currentThread().getName());
        while (true) {
            try {
                String value = redisTemplate.execute(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        List<byte[]> bytes = connection.bLPop(2, Constants.REDIS.getBytes(Charset.forName("UTF-8")));
                        if (bytes!=null){
                            for (byte[] b:bytes){
                                String s = new String(b);
                                if (!Constants.REDIS.equals(s)){
                                    return s;
                                }
                            }
                        }
                        return null;
                    }
                });
                if (value!=null){
                    log.info("======获取队列的数据=============:"+value);
                    TBParam tbParam = JacksonUtil.json2obj(value, TBParam.class);
                    log.info("公司code"+tbParam.getCompanyCode()+"线程名称："+Thread.currentThread().getName()+" 客栈id:"+tbParam.getInnId());
                    boolean validateParam = DcUtil.validateParam(tbParam);
                    log.info("validateParam:"+validateParam+"推送参数APIController："+tbParam.toString()+" 企业唯一code"+tbParam.getCompanyCode()+" accountIdDi:"+tbParam.getAccountIdDi());
                    if (validateParam){
                        long date = System.currentTimeMillis();
                        List<OtaInfoRefDto> list = otaInfoService.findAllOtaByCompany(tbParam.getCompanyCode());
                        log.info("=====otaInfoService.findAllOtaByCompany执行时间(毫秒):"+(System.currentTimeMillis()-date));
                        for (OtaInfoRefDto o : list) {
                            ITPService service =  o.getOtaType().create();
                            try {
                                service.updateOrAddHotel(tbParam, o);
                            } catch (Exception e) {
                                log.error("EventHelperTask异常,", e);
                            }
                        }
                    }
                }
            }catch (Exception e) {
                log.error("批量执行队列异常",e);
            }
        }

    }
}
