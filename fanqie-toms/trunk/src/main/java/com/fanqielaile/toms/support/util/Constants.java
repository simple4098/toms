package com.fanqielaile.toms.support.util;

import com.fanqie.util.PropertiesUtil;

/**
 * Created by wangdayin on 2015/5/12.
 */
public class Constants {
    //返回json状态
    public static final String STATUS = "status";
    //返回提示信息
    public static final String MESSAGE = "message";
    //返回正确
    public static final boolean SUCCESS = true;
    //返回错误
    public static final boolean ERROR = false;
    public static final String ERROR400 = "400";
    public static final String SUCCESS200 = "200";
    //数据
    public static final String DATA = "data";
    //TB用户名
    public static final String TBUserName = "TaoBao";
    //TB密码
    public static final String TBPassword = "TB123taobao";
    public static final String SD = "手动";
    public static final String ZD = "自动";
    public static final int tpPriceUnit = 100;
    //天下房仓备份ftp备份路径
    public static final String FcUploadUrl = "fcXmlDir";
    //天下房仓增量下载地址
    public static final String FcDownLoadUrl = "fcDownUrl";
    //天下房仓增量临时保存路径
    public static final String FcDownLoadSavePath = "fcDownLoadSavePath";
    public static final int ZERO_QUOTA = 0;
    //天下房仓返回值
    public static final String FcResultNo = "000";
    public static final String FcPartnerCodeNo = "108";
    public static final String FcSignatureNo = "103";
    public static final String FcPartnerCodeFailureNo = "002";
    //淘宝测试酒店
    public static final String TB_InnId = "test123456";
    public static final String OPTION = "===请选择===";

    public static final String MESSAGE_ERROR = "参数异常，请检查!";
    public static final String RESULT_CODE = "Success";
    public static final String MESSAGE_SUCCESS = "成功";
    public static final Integer FC_SJ = 1;
    public static final Integer FC_XJ = 0;
    public static final Integer FC_NOT_SJ = -1;
    public static final Integer FC_NOT_HAVE_ROOM = 3;
    public static final Integer FC_HAVE_ROOM = 1;
    public static final Integer FC_DAI_CHA = 2;
    public static final String MAI = "MAI";
    public static final String DI = "DI";
    public static final int timerThread = 150;
    public static final int onlyBedWidBedBig = 150;
    public static final int MAI_VALUE = 2;
    public static final int DI_VALUE = 1;
    
    public static final String OTA_CTRIP = "3";
    //众荟房态天数
    public static final int day = Integer.valueOf(PropertiesUtil.readFile("/data.properties","zh.day"));
    //public static final int day = 60;
}
