package com.fanqielaile.toms.support;

import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.WarningType;
import org.opentravel.ota._2003._05.WarningsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class HandlerJointWisdomResult {


    private final static Logger LOGGER = LoggerFactory.getLogger(HandlerJointWisdomResult.class);

    /**
     *  处理众荟返回的错误 或者 警告信息
     * @param result
     */
    public static  void  handler(List<Object> result){
        for (Object object : result) {
            if(object  instanceof ErrorsType){
                ErrorsType et = (ErrorsType)object;
                List<ErrorType> errors = et.getError();
                for (ErrorType errorType : errors) {
                    String errorMsg = "出错了:" + errorType.getValue();
                    LOGGER.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                    
                }
            }else if(object  instanceof WarningsType){
                WarningsType et = (WarningsType)object;
                List<WarningType>   warns =  et.getWarning();
                for (WarningType warningType : warns) {
                    String warnMsg = "出现警告:" + warningType.getValue();
                    LOGGER.error(warnMsg);
                    throw new RuntimeException(warnMsg);
                }
            }else{
                String unknow = "未知的对象："+object;
                LOGGER.error(unknow);
                throw new RuntimeException(unknow);
            }
        }
        LOGGER.info("未发现异常信息");
    }

}
