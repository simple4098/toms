package com.fanqielaile.toms.util;

public class Constants {
	//0：未开通携程直连；1 酒店审核中 2 房型匹配 3 房型匹配审核中 4 携程直连已上架
	public final static  String NOT_DIRECT = "0";
	public final static  String INN_AUDIT_ING = "1";
	public final static  String ROOM_MATCH = "2";
	public final static  String ROOM_MATCH_AUDIT_ING = "3";
	public final static  String CTRIP_DIRECT = "4";

	public final static  String NOT_MATCH = "0";
	public final static  String MATCH = "1";

	//直连匹配账号状态
	public final static Integer USER_ACCOUNT_STATUS_NORMAL=0;
	public final static Integer USER_ACCOUNT_STATUS_INVALID=3;
	public final static Integer USER_ACCOUNT_STATUS_UNBUNDLING=4;

	public final static String orderStatus="未处理";
	public final static String DaiQueRen="0";
	public final static String DaiFenFang="5";
	public final static String PENDING="0,5";
	public final static String DaiFenFangV="待分房";
	public final static String DaiQueRenV="待确认";
	/**
	 * 客栈id
	 */
	public final static String OMS_INN_ID = "omsInnId";
	
	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 页面分页信息
	 */
	public static final Integer ROWS_DEFAULT = 10;		//默认分页行数
	public static final Integer PAGE_DEFAULT = 1;		//默认分页页数
	//线程数量
	public static final Integer THREAD_PUSH_NUM=10;

	public final static String ROOT = "ROOT";
	
	// http获取响应类型(all:所有，responseStr:网页字符串，cookies：网页cookies)
	public final static String HTTP_GET_TYPE_ALL = "all";
	public final static String HTTP_GET_TYPE_STRING = "responseStr";
	public final static String HTTP_GET_TYPE_COOKIES = "cookies";
	public final static int HTTP_SUCCESS = 200; //所有逻辑都正确
	public final static int HTTP_ERROR = 400; //访问正常但逻辑有误,app端要显示message信息
	public final static int HTTP_800 = 800;//订单重复 800
	public final static int HTTP_401 = 401;
	public final static int HTTP_404 = 404;
	public final static int HTTP_500 = 500;
	public final static int HTTP_411 = 411;//请求参数有误
	public final static int HTTP_412 = 412;//请求超时
	public final static int HTTP_413 = 413;//签名有误
	
	/** 返回key */
	public final static String STATUS = "status"; //状态
	public final static String MESSAGE = "message"; //状态说明
	public final static String TYPE = "type"; //状态类型
	public final static String CODE ="code";//返回状态码标志
	public final static String RESULT = "result";
	public final static String ERROR_CODE = "errorCode";
	public final static String DATA = "data";
	public final static String RESPONSE_FIELD_FLAG = "flag";
	/** 返回结果 */
	public final static String SUCCESS = "success";
	public final static String FAILURE = "fail";
	public final static String ORDER_REPEAT = "ORDER_REPEAT";
	public final static String ERROR_TIP_MSG = "请稍后再试";
	
	/** 图片上传临时文件目录 */
	public final static String IMG_TEMP_FOLDER = "imgTempFolder";
	public final static String IMG_FTP_FOLDER = "imgFtpFolder";
	
	/**腾讯云上传系统配置参数 **/
	public static final String APP_ID = "app.id";
	public static final String SECRET_ID = "secret.id";
	public static final String SECRET_KEY = "secret.key";
	public static final String BUCKET_NAME = "bucket.name";
	public static final String SPACE_NAME="space.name";
	
	
	
	/** 错误信息 */
	public final static String ERROR_LACK_PARAMETER = "参数不足";
	public final static String ERROR_MISS_SERREQUESTPARAMETERPARAMETER = "系统找不到请求的绑定参数";
	/** InnInfoController */
	public final static String ERROR_INNINFO_SAVEINNFO = "保存客栈失败，该客栈已存在";
	public final static String ERROR_INNINFO_GETINNINFO = "获取客栈信息出错";
	public final static String ERROR_INNINFO_SAVEORUPDATE = "保存修改客栈信息出错";
	public final static String ERROR_ROOMTYPE_GETROOMTYPE = "获取客栈房型信息出错";
	public final static String ERROR_ROOMTYPE_SAVEORUPDATE = "保存修改房型信息出错";
	public final static String ERROR_ROOMTYPE_DELETE = "删除房型出错";
	public final static String ERROR_ROOMTYPE_EXISTBIND = "该房型已与小站/美团对接，不可删除！<br>若要删除，" +
			"请先到小站/美团对接后台中解除该房型的对接关系。";
	public final static String ERROR_ROOMTYPE_UPDATETORECOMMEND = "修改推荐房型出错";
	public final static String ERROR_ROOMTYPE_ROOMNAMEREPEAT = "房型名称相同";
	
	
	
	public final static String ERROR_IMG_SAVEORUPDATEIMG = "保存/更新图片出错";
	public final static String ERROR_IMG_UPDATEALLIMG = "批量更新图片出错";
	
	public final static String ERROR_DIC_GETDIC = "获取数据字典出错";
	
	/** 图片上传 */
	public final static String ERROR_IMG_CUT = "图片裁剪出错";
	public final static String ERROR_IMG_UPLOAD = "图片上传出错";
	public final static String ERROR_IMG_DEL = "删除图片出错";
	public final static String ERROR_IMG_TEMPDEL = "删除临时图片出错";
	public final static String ERROR_UPLOAD_IMG = "上传图片大小超过规定限制2M";
	public final static String ERROR_UPLOAD_FILENOTFOUND = "系统找不到指定的路径";
	
	/** 下单 */
	public final static String ERROR_ORDER = "下单出错";
	public final static String ERROR_QUERYORDER = "查询订单出错";
	public final static String ERROR_CANCELORDER = "取消订单出错";
	/**房量改变*/
	public final static String ERROR_ROOMSCHANGE = "房量改变出错";
	
	/** 错误信息 */
	
	/** 通用记录状态 失效  逻辑删除 */
	public final static int STATUS_DELETE = 0;
	/** 通用记录状态 正常状态 */
	public final static int STATUS_NORMAL = 1;
	
	/**缓存常量start*/
	public static final String OMS_CACHE_NAMESPACE = "_OMS_&$#@!_";
	
	public static final String OMS_CACHE_TEST = "test";
	public static final String OMS_CACHE_OTA = "ota_$$";

	//图片上传信息
	public static final String OMS_CACHE_IMG_UPLOAD = "_$img_upload&&$_";

	//字典
	public static final String OMS_CACHE_DIC = "dic"; //字典空间名
	
	public static final String OMS_CACHE_DIC_INNTYPE = "innType";//客栈类型
	public static final String OMS_CACHE_DIC_FACILITIES = "facilities";//客栈设施
	public static final String OMS_CACHE_DIC_ROOMFACILITIES = "roomFacilities";//房型设施
	public static final String OMS_CACHE_DIC_IMGTYPE = "imgType";//图片类型
	
	//开通表
	public static final String OMS_CACHE_OPENAC = "openAcc"; //字典空间名
	public static final String OMS_CACHE_OPENAC_QBAI = "queryByAccountId";
	
	//otaInf缓存key
	public static final String OMS_CACHE_QUERY_OTA = "queryOtaInfoByPid";
	public static final String OMS_CACHE_OTA_PID = "ota_pid";
	/**缓存常量end*/
	

	public static final int MAX_PAGE_SIZE = 20;


	public final static String CODE_411_MESSAGE ="请求参数有误";
	public final static String CODE_412_MESSAGE ="请求超时";
	public final static String CODE_413_MESSAGE ="签名有误";


    public final static String OTA_ID_MT = "105";//美团
    public final static String OTA_ID_XZ="101";
	public final static String OTA_ID_KFPT="111";//公共平台
	public final static String OTA_ID_XC="2";//携程
	public final static String OTA_ID_YL="3";//艺龙
	public final static String OTA_ID_QN="1";//去哪儿
	public final static String OTA_ID_DX = "102";//代销平台
	/**
	 * ota类型直连
	 */
	public final static Integer OTA_TYPE_DIRECT =1;
	/**
	 * ota类型外挂
	 */
	public final static Integer OTA_TYPE_PlUG =2;
	public static final String SESSION_USERBEAN = "userBean";
	/**
	 * 跳转的错误页面
	 */
	public static final String ERROR_VIEW = "error";
	
	//session 存用户参数名称
    public static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";



    /**
	 * 返回页面路径
	 */
	//客栈信息
	public static final String ROOM_INFO="/omsFront/views/salesdata/roomInfo.html";
	//房间信息
	public static final String ROOM_TYPE_VIEW="/omsFront/views/salesdata/roomType.html";
	//ota列表
	public static final String OTA_INFO="/omsFront/views/otalist.html";
	//小站房态
	public static final String OTA_XZ_VIEW="/omsFront/views/otalist/innstatus_xz.html";
	//美团房态innstatus_xz
	public static final String OTA_MT_VIEW="/omsFront/views/otalist/innstatus_mt.html";
	//开放平台
	public static final String OTA_KFPT_VIEW="/omsFront/views/otalist/innstatus_kfpt.html";
	//代销平台
	public static final String OTA_DXPT_VIEW="/omsFront/views/otalist/innstatus_dxpt.html";
	//信用住平台
	public static final String OTA_XYZ_VIEW="/omsFront/view/otaList/innstatus_xyz.html";
	//直连订单页面
	public static final String ORDER_VIEW="/omsFront/views/order/order.html";
	
	//  开同代销状态
	public static final int   PROXY_SALE =5;

	public static final String FQ_="FQ_";



}
