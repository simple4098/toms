package com.fanqielaile.toms.common;

/**
 * DESC : 请求数据中心接口
 * @author : 番茄木-ZLin
 * @data : 2015/5/27
 * @version: v1.0.0
 */
public class CommonApi {
    public static   String QS = "";
    public static   String QSDetail = "";
    public static   String KF = "";
    public static   String KF_D = "";
    public static   String ORDER = "";
    public static   String ActiveInn = "";
    public static   String ROOM_TYPE="";
    public static   String INN_INFO="";
    public static   String TB_URL="";
    public static   String IMG_URL="";
    public static   String ProxySaleList="";
    public static   String proxyOtaPercent="";
    public static   String FcAddHotelMappingUrl;
    public static   String fcHotelInfo;
    public static   String FcAddRoomTypeMappingUrl;
    public static   String FcSyncRatePlanUrl;
    public static   String FcDelSyncRatePlanUrl;
    public static   String FcSyncRateInfoUrl;
    public static   String FcDeleteRoomTypeUrl;
    public static   String FcDeleteHotelMappingUrl;
    public static   String checkRoom;
    public static   String roomStatus;
    public static String pmsOrderStatus;
    public static   String sellingRoomType;
    public static String qunarOrderOpt;
    public static String cancelCreditOrder;


    public  String getSellingRoomType() {
        return sellingRoomType;
    }

    public  void setSellingRoomType(String sellingRoomType) {
        CommonApi.sellingRoomType = sellingRoomType;
    }

    public  String getRoomStatus() {
        return roomStatus;
    }

    public  void setRoomStatus(String roomStatus) {
        CommonApi.roomStatus = roomStatus;
    }

    public  String getFcHotelInfo() {
        return fcHotelInfo;
    }

    public  void setFcHotelInfo(String fcHotelInfo) {
        CommonApi.fcHotelInfo = fcHotelInfo;
    }

    public  String getCheckRoom() {
        return checkRoom;
    }

    public  void setCheckRoom(String checkRoom) {
        CommonApi.checkRoom = checkRoom;
    }

    public  String getProxyOtaPercent() {
        return proxyOtaPercent;
    }

    public  void setProxyOtaPercent(String proxyOtaPercent) {
        CommonApi.proxyOtaPercent = proxyOtaPercent;
    }

    public  String getFcDeleteHotelMappingUrl() {
        return FcDeleteHotelMappingUrl;
    }

    public  void setFcDeleteHotelMappingUrl(String fcDeleteHotelMappingUrl) {
        FcDeleteHotelMappingUrl = fcDeleteHotelMappingUrl;
    }

    public  String getFcDeleteRoomTypeUrl() {
        return FcDeleteRoomTypeUrl;
    }

    public  void setFcDeleteRoomTypeUrl(String fcDeleteRoomTypeUrl) {
        FcDeleteRoomTypeUrl = fcDeleteRoomTypeUrl;
    }

    public  String getFcSyncRateInfoUrl() {
        return FcSyncRateInfoUrl;
    }

    public  void setFcSyncRateInfoUrl(String fcSyncRateInfoUrl) {
        FcSyncRateInfoUrl = fcSyncRateInfoUrl;
    }

    public  String getFcDelSyncRatePlanUrl() {
        return FcDelSyncRatePlanUrl;
    }

    public  void setFcDelSyncRatePlanUrl(String fcDelSyncRatePlanUrl) {
        FcDelSyncRatePlanUrl = fcDelSyncRatePlanUrl;
    }

    public  String getFcSyncRatePlanUrl() {
        return FcSyncRatePlanUrl;
    }

    public  void setFcSyncRatePlanUrl(String fcSyncRatePlanUrl) {
        FcSyncRatePlanUrl = fcSyncRatePlanUrl;
    }

    public  String getFcAddHotelMappingUrl() {
        return FcAddHotelMappingUrl;
    }
    public  void setFcAddHotelMappingUrl(String fcAddHotelMappingUrl) {
        FcAddHotelMappingUrl = fcAddHotelMappingUrl;
    }

    public  String getFcAddRoomTypeMappingUrl() {
        return FcAddRoomTypeMappingUrl;
    }

    public  void setFcAddRoomTypeMappingUrl(String fcAddRoomTypeMappingUrl) {
        FcAddRoomTypeMappingUrl = fcAddRoomTypeMappingUrl;
    }

    public static String getImgUrl() {
        return IMG_URL;
    }

    public  void setImgUrl(String imgUrl) {
        IMG_URL = imgUrl;
    }

    public static String getProxySaleList() {
        return ProxySaleList;
    }

    public  void setProxySaleList(String proxySaleList) {
        ProxySaleList = proxySaleList;
    }

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

    public void setPmsOrderStatus(String pmsOrderStatus) {
        this.pmsOrderStatus = pmsOrderStatus;
    }

    public String getPmsOrderStatus() {
        return pmsOrderStatus;
    }

    public void setQunarOrderOpt(String qunarOrderOpt) {
        this.qunarOrderOpt = qunarOrderOpt;
    }

    public String getQunarOrderOpt() {
        return qunarOrderOpt;
    }

	public static String getCancelCreditOrder() {
		return cancelCreditOrder;
	}

	public  void setCancelCreditOrder(String cancelCreditOrder) {
		CommonApi.cancelCreditOrder = cancelCreditOrder;
	}
}
