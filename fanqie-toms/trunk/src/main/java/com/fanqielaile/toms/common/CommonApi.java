package com.fanqielaile.toms.common;

/**
 * DESC : 请求数据中心接口
 * @author : 番茄木-ZLin
 * @data : 2015/5/27
 * @version: v1.0.0
 */
public class CommonApi {
    public static   String MESSAGE_ERROR="参数异常，请检查!";
    public static   String MESSAGE_SUCCESS="成功";
    public static   String QS = "";
    public static   String QSDetail = "";
    public static   String KF = "";
    public static   String KF_D = "";
    public static   String ORDER = "";
    public static   String ActiveInn = "";
    public static   String ROOM_TYPE="";
    public static   String INN_INFO="";
    public static   String TB_URL="";
    public static   String IMG_URL="http://img.fanqiele.com/";

    public static String getTbUrl() {
        return TB_URL;
    }

    public  void setTbUrl(String tbUrl) {
        TB_URL = tbUrl;
    }

    public static String getInnInfo() {
        return INN_INFO;
    }

    public  void setInnInfo(String innInfo) {
        INN_INFO = innInfo;
    }

    public static String getRoomType() {
        return ROOM_TYPE;
    }

    public  void setRoomType(String roomType) {
        CommonApi.ROOM_TYPE = roomType;
    }

    public static String getQSDetail() {
        return QSDetail;
    }

    public  void setQSDetail(String QSDetail) {
        CommonApi.QSDetail = QSDetail;
    }

    public static String getQS() {
        return QS;
    }

    public  void setQS(String QS) {
        CommonApi.QS = QS;
    }

    public static String getKF() {
        return KF;
    }

    public  void setKF(String KF) {
        CommonApi.KF = KF;
    }

    public static String getKfD() {
        return KF_D;
    }

    public  void setKfD(String kfD) {
        CommonApi.KF_D = kfD;
    }

    public static String getORDER() {
        return ORDER;
    }

    public  void setORDER(String ORDER) {
        CommonApi.ORDER = ORDER;
    }

    public static String getActiveInn() {
        return ActiveInn;
    }

    public  void setActiveInn(String activeInn) {
        ActiveInn = activeInn;
    }
}
