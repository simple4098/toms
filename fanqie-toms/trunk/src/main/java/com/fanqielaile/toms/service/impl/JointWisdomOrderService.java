package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.jw.enums.OrderResponseType;
import com.fanqie.jw.enums.Version;
import com.fanqie.qunar.enums.ResultStatus;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import com.fanqielaile.toms.service.IJointWisdomOrderService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.service.jointwisdomService.*;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlJointWisdomUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangdayin on 2016/1/11.
 */
@Service
public class JointWisdomOrderService implements IJointWisdomOrderService {
    private static Logger logger = LoggerFactory.getLogger(JointWisdomOrderService.class);

    @Resource
    private IJointWisdomInnRoomDao jointWisdomInnRoomDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private DailyInfosDao dailyInfosDao;
    @Resource
    private DictionaryDao dictionaryDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private OrderGuestsDao orderGuestsDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaBangInnRoomDao bangInnRoomDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private IOtaInnRoomTypeGoodsDao otaInnRoomTypeGoodsDao;
    @Resource
    private IRoomTypeService roomTypeService;
    @Resource
    private IOrderConfigDao orderConfigDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private OrderOperationRecordDao orderOperationRecordDao;
    @Resource
    private IOtaCommissionPercentDao otaCommissionPercentDao;
    @Resource
    private IOrderService orderService;
    @Resource
    private IFcRatePlanDao ratePlanDao;

    @Override
    public Map<String, Object> dealAvailCheckOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        try {
            //解析xml得到order的查询对象
            Order availOrder = XmlJointWisdomUtil.getJointWisdomAvailOrder(xml);
            MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, xml, "众荟试订单传入参数"));
            //检查试订单日期
            if (availOrder.getLiveTime().getTime() > availOrder.getLeaveTime().getTime()) {
                JointWisdomAvailCheckOrderSuccessResponse errorResult = new JointWisdomAvailCheckOrderSuccessResponse();
                JointWisdomAvailCheckOrderErrorResponse basicError = errorResult.getBasicError("试订单入住时间必须小于等于离店时间");
                MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, JacksonUtil.obj2json(basicError), "众荟试订单返回值"));
                map.put("data", basicError);
                map.put("status", true);
                return map;
            }
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.ZH.name());

            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CHECK_ORDER.name());
            //查询mapping房型信息
            JointWisdomInnRoomMappingDto jointWisdomInnRoomMappingDto = this.jointWisdomInnRoomDao.selectRoomMappingByInnIdAndRoomTypeId(availOrder);
            //查询公司信息
            Company company = this.companyDao.selectCompanyById(jointWisdomInnRoomMappingDto.getCompanyId());
            //查询绑定客栈信息
            BangInn bangInn = this.bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), jointWisdomInnRoomMappingDto.getInnId());
            //2.判断当前公司使用什么价格模式
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(company.getId(), otaInfoRefDto.getId());
            //判断是否有房型
            if (null != availOrder.getRoomTypeCode()) {
                //查询价格计划
                OtaRatePlan otaRatePlan = this.ratePlanDao.selectRatePlanByCompanyIdAndOtaIdAndRateCode(company.getId(), otaInfoRefDto.getId(), jointWisdomInnRoomMappingDto.getRatePlanCode());
                //设置roomTypeId
                availOrder.setRoomTypeId(String.valueOf(jointWisdomInnRoomMappingDto.getRoomTypeId()));
                //酒店id
                availOrder.setInnId(jointWisdomInnRoomMappingDto.getInnId());
                //处理一下离店日期，不需要包含最后一天
                availOrder.setLeaveTime(DateUtil.addDay(availOrder.getLeaveTime(), -1));
                logger.info("众荟试订单oms接口传递参数=>" + availOrder.toRoomAvail(company, availOrder).toString());
                MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, availOrder.getChannelOrderCode(), availOrder.getId(), availOrder.getOmsOrderCode(), availOrder.getOrderStatus(), availOrder.getOrderStatus(), availOrder.getFeeStatus(), availOrder.toRoomAvail(company, availOrder).toString(), null, availOrder.getInnId(), availOrder.getInnCode(), "众荟试订单，oms传入值"));
                String response = HttpClientUtil.httpGetRoomAvail(dictionary.getUrl(), availOrder.toRoomAvail(company, availOrder));
                JSONObject jsonObject = JSONObject.fromObject(response);
                logger.info("众荟试订单oms接口返回值=>" + response.toString());
                MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, availOrder.getChannelOrderCode(), availOrder.getId(), availOrder.getOmsOrderCode(), availOrder.getOrderStatus(), availOrder.getOrderStatus(), availOrder.getFeeStatus(), availOrder.toRoomAvail(company, availOrder).toString(), response.toString(), availOrder.getInnId(), availOrder.getInnCode(), "众荟试订单，oms返回值"));
                //解析返回数据
                if ("200".equals(jsonObject.get("status").toString())) {
                    //查询当前的价格模式
                    BigDecimal percent = BigDecimal.ZERO;

                    if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                        //查询价格比例
                        OtaCommissionPercentDto commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), otaInfo.getUsedPriceModel().name()));
                        percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
                    }
                    //查询加减价
                    OtaRoomPriceDto otaRoomPriceDto = this.otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(jointWisdomInnRoomMappingDto.getRoomTypeId()), otaInfo.getOtaInfoId()));
                    List<RoomDetail> roomDetails = (List<RoomDetail>) JSONArray.toList(jsonObject.getJSONArray("data"), RoomDetail.class);
                    //转换oms房型信息为toms的每日入住信息
                    List<DailyInfos> dailyInfos = OrderMethodHelper.toDailyInfos(roomDetails);
                    //组装返回参数
                    //1.组装房型
                    RoomType roomType = new RoomType();
                    roomType.setRoomTypeCode(jointWisdomInnRoomMappingDto.getRoomTypeIdCode());
                    boolean canBook = false;
                    //判断是否可预定
                    for (DailyInfos dailyInfos1 : dailyInfos) {
                        if (dailyInfos1.getRoomNum() < availOrder.getHomeAmount()) {
                            canBook = false;
                            break;
                        } else {
                            canBook = true;
                        }
                    }
                    if (canBook) {
                        roomType.setNumberOfUnits("true");
                    } else {
                        roomType.setNumberOfUnits("false");
                        map.put("status", false);
                        map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("房型剩余放量不满足预定，请重试"));
                        return map;
                    }
                    RoomDescription roomDescription = new RoomDescription();
                    roomDescription.setName(jointWisdomInnRoomMappingDto.getRoomTypeName());
                    Text text = new Text();
                    text.setValue(jointWisdomInnRoomMappingDto.getRoomTypeName());
                    text.setLanguage("en-US");
                    roomDescription.setText(text);
                    roomType.setDescription(roomDescription);
                    //2.组装价格计划
                    RatePlan ratePlan = new RatePlan();
                    ratePlan.setRatePlanCategory(ratePlan.getRatePlanCategory());
                    ratePlan.setRatePlanCode(String.valueOf(otaRatePlan.getRatePlanCode()));
                    RatePlanDescription ratePlanDescription = new RatePlanDescription();
                    Text textRatePlan = new Text();
                    textRatePlan.setLanguage("en-US");
                    textRatePlan.setValue(otaRatePlan.getRatePlanName());
                    ratePlanDescription.setText(textRatePlan);
                    ratePlanDescription.setName(otaRatePlan.getRatePlanName());
                    ratePlan.setRatePlanDescription(ratePlanDescription);
                    //3.组装房型与价格计划的对应关系
                    RoomRate roomRate = new RoomRate();
                    roomRate.setRatePlanCode(String.valueOf(otaRatePlan.getRatePlanCode()));
                    roomRate.setRoomTypeCode(jointWisdomInnRoomMappingDto.getRoomTypeIdCode());
                    List<Rate> rateList = new ArrayList<>();
                    BigDecimal totalPrice = BigDecimal.ZERO;
                    if (ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                        for (DailyInfos dailyInfos1 : dailyInfos) {
                            Rate rate = new Rate();
                            rate.setEffectiveDate(DateUtil.format(dailyInfos1.getDay(), "yyyy-MM-dd"));
                            rate.setExpireDate(DateUtil.format(DateUtil.addDay(dailyInfos1.getDay(), 1), "yyyy-MM-dd"));
                            Base base = new Base();
                            //判断当前客栈的价格模式
                            if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                                base.setAmountAfterTax(String.valueOf(TomsUtil.getPriceRoundUp(dailyInfos1.getPrice().multiply(new BigDecimal(1).subtract(percent)).doubleValue())));
                            } else {
                                base.setAmountAfterTax(String.valueOf(dailyInfos1.getPrice()));
                            }
                            //设置加减价
                            //设置加减价
                            if (null != otaRoomPriceDto && dailyInfos1.getDay().getTime() >= otaRoomPriceDto.getStartDate().getTime() && dailyInfos1.getDay().getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                                base.setAmountAfterTax(String.valueOf(BigDecimal.valueOf(Double.parseDouble(base.getAmountAfterTax())).add(BigDecimal.valueOf(otaRoomPriceDto.getValue()))));
                            }
                            base.setAmountBeforeTax(base.getAmountAfterTax());
                            base.setCurrencyCode(base.getCurrencyCode());
                            Taxes taxes = new Taxes();
                            taxes.setCurrencyCode(base.getCurrencyCode());
                            base.setTaxes(taxes);
                            rate.setBase(base);
                            rateList.add(rate);
                            totalPrice = totalPrice.add(BigDecimal.valueOf(Double.valueOf(base.getAmountAfterTax())));
                        }
                    }
                    roomRate.setRates(rateList);
                    Total total = new Total();
                    total.setCurrencyCode("RMB");
                    total.setAmountBeforeTax(String.valueOf(totalPrice));
                    total.setAmountAfterTax(String.valueOf(totalPrice));
                    roomRate.setTotal(total);
                    List<RoomRate> roomRateList = new ArrayList<>();
                    roomRateList.add(roomRate);
                    JointWisdomAvailCheckOrderSuccessResponse responseResult = new JointWisdomAvailCheckOrderSuccessResponse();
                    responseResult.setEchoToken(responseResult.getEchoToken());
                    responseResult.setTimeStamp(responseResult.getTimeStamp());
                    responseResult.setSuccess("success");
                    responseResult.setVersion(responseResult.getVersion());
//                    responseResult.setXmlns(responseResult.getXmlns());
                    RoomStay roomStay = new RoomStay();
                    //房型与价格对应关系
                    roomStay.setRoomRates(roomRateList);
                    //价格计划
                    List<RatePlan> ratePlanList = new ArrayList<>();
                    ratePlanList.add(ratePlan);
                    roomStay.setRatePlans(ratePlanList);
                    //房型
                    List<RoomType> roomTypeList = new ArrayList<>();
                    roomTypeList.add(roomType);
                    roomStay.setRoomTypes(roomTypeList);
                    List<GuestCount> guestCountList = new ArrayList<>();
                    GuestCount guestCount = new GuestCount();
                    guestCount.setCount(String.valueOf(availOrder.getHomeAmount()));
                    guestCount.setAgeQualifyingCode("10");
                    guestCountList.add(guestCount);
                    roomStay.setGuestCounts(guestCountList);
                    BasicPropertyInfo basicPropertyInfo = new BasicPropertyInfo();
                    basicPropertyInfo.setHotelName(bangInn.getInnName());
                    basicPropertyInfo.setHotelCode(availOrder.getInnCode());
                    roomStay.setBasicPropertyInfo(basicPropertyInfo);

                    TimeSpan timeSpan = new TimeSpan();
                    timeSpan.setStart(DateUtil.format(availOrder.getLiveTime(), "yyyy-MM-dd"));
                    timeSpan.setEnd(DateUtil.format(availOrder.getLeaveTime(), "yyyy-MM-dd"));
                    roomStay.setTimeSpan(timeSpan);
                    List<RoomStay> roomStayList = new ArrayList<>();
                    roomStayList.add(roomStay);
                    responseResult.setRoomStays(roomStayList);
                    map.put("status", true);
                    map.put("data", responseResult);
                    MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, availOrder.getChannelOrderCode(), availOrder.getId(), availOrder.getOmsOrderCode(), availOrder.getOrderStatus(), availOrder.getOrderStatus(), availOrder.getFeeStatus(), availOrder.toRoomAvail(company, availOrder).toString(), JacksonUtil.obj2json(responseResult), availOrder.getInnId(), availOrder.getInnCode(), "众荟试订单，toms返回值"));
                    return map;
                }
            } else {
                if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel()) || UsedPriceModel.MAI.equals(otaInfo.getUsedPriceModel())) {
                    bangInn.setAccountId(bangInn.getAccountId());
                } else {
                    bangInn.setAccountId(bangInn.getAccountIdDi());
                }
                String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
                String roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.roomStatus);

                List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
                List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
                InnRoomHelper.updateRoomTypeInfo(list, statusDetails);
                //刷选数据
                if (ArrayUtils.isNotEmpty(list.toArray())) {
                    for (RoomTypeInfo roomTypeInfo : list) {
                        List<RoomDetail> roomDetailList = new ArrayList<>();
                        if (ArrayUtils.isNotEmpty(roomTypeInfo.getRoomDetail().toArray())) {
                            for (RoomDetail roomDetail : roomTypeInfo.getRoomDetail()) {
                                Date parse = DateUtil.parse(roomDetail.getRoomDate(), "yyyy-MM-dd");
                                if (parse.getTime() >= availOrder.getLiveTime().getTime() && parse.getTime() < availOrder.getLeaveTime().getTime()) {
                                    roomDetailList.add(roomDetail);
                                }
                            }
                        }
                        roomTypeInfo.setRoomDetail(roomDetailList);
                    }
                }
                //组装返回参数
                RoomStay roomStay = new RoomStay();
                //房型与价格计划对应关系
                List<RoomRate> roomRateList = new ArrayList<>();
                //房型list
                List<RoomType> roomTypeList = new ArrayList<>();
                //价格计划
                List<RatePlan> ratePlanList = new ArrayList<>();
                if (ArrayUtils.isNotEmpty(list.toArray())) {
                    OtaRatePlan otaRatePlan = new OtaRatePlan();
                    outer:
                    for (RoomTypeInfo roomTypeInfo : list) {
                        //查询房型信息toms
                        availOrder.setRoomTypeId(String.valueOf(roomTypeInfo.getRoomTypeId()));
                        availOrder.setRoomTypeIdInt(roomTypeInfo.getRoomTypeId());
                        JointWisdomInnRoomMappingDto wisdomInnRoomMappingDto = this.jointWisdomInnRoomDao.selectRoomMappingByInnIdAndRoomTypeId(availOrder);
                        if (null == wisdomInnRoomMappingDto) {
                            continue outer;
                        }
                        //查询价格计划
                        otaRatePlan = this.ratePlanDao.selectRatePlanByCompanyIdAndOtaIdAndRateCode(company.getId(), otaInfoRefDto.getId(), jointWisdomInnRoomMappingDto.getRatePlanCode());
                        //房型
                        RoomType roomType = new RoomType();
                        roomType.setRoomTypeCode(wisdomInnRoomMappingDto.getRoomTypeIdCode());
                        RoomDescription roomDescription = new RoomDescription();
                        roomDescription.setName(roomTypeInfo.getRoomTypeName());
                        Text text = new Text();
                        text.setValue(roomTypeInfo.getRoomTypeName());
                        text.setLanguage("en-US");
                        roomDescription.setText(text);
                        roomType.setDescription(roomDescription);
                        boolean isCanbook = true;
                        //价格计划与房型对应
                        RoomRate roomRate = new RoomRate();
                        roomRate.setRoomTypeCode(wisdomInnRoomMappingDto.getRoomTypeIdCode());
                        roomRate.setRatePlanCode(wisdomInnRoomMappingDto.getRatePlanCode());
                        List<Rate> rateList = new ArrayList<>();
                        BigDecimal totalPrice = BigDecimal.ZERO;
                        if (ArrayUtils.isNotEmpty(roomTypeInfo.getRoomDetail().toArray())) {
                            for (RoomDetail detail : roomTypeInfo.getRoomDetail()) {
                                //是否可预定
                                if (isCanbook) {
                                    if (detail.getRoomNum() >= availOrder.getHomeAmount()) {
                                        isCanbook = true;
                                    } else {
                                        isCanbook = false;
                                    }
                                }
                                //得到试订单的日期，每天的日期
                                List<Date> dateList = DateUtil.getDateEntrysByDifferenceDate(availOrder.getLiveTime(), availOrder.getLeaveTime());
                                //查询当前的价格模式
                                BigDecimal percent = BigDecimal.ZERO;

                                if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                                    //查询价格比例
                                    OtaCommissionPercentDto commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), otaInfo.getUsedPriceModel().name()));
                                    percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
                                }
                                //查询加减价
                                OtaRoomPriceDto otaRoomPriceDto = this.otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(jointWisdomInnRoomMappingDto.getRoomTypeId()), otaInfo.getOtaInfoId()));


                                Rate rate = new Rate();
                                rate.setEffectiveDate(DateUtil.format(DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
                                rate.setExpireDate(DateUtil.format(DateUtil.addDay(DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd"), 1), "yyyy-MM-dd"));
                                Base base = new Base();
                                base.setCurrencyCode(base.getCurrencyCode());
                                //判断当前客栈的价格模式
                                if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                                    base.setAmountAfterTax(String.valueOf(TomsUtil.getPriceRoundUp(BigDecimal.valueOf(detail.getRoomPrice()).multiply(new BigDecimal(1).subtract(percent)).doubleValue())));
                                } else {
                                    base.setAmountAfterTax(String.valueOf(detail.getRoomPrice()));
                                }
                                //设置加减价
                                //设置加减价
                                if (null != otaRoomPriceDto && DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd").getTime() >= otaRoomPriceDto.getStartDate().getTime() && DateUtil.parseDate(detail.getRoomDate(), "yyyy-MM-dd").getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                                    base.setAmountAfterTax(String.valueOf(BigDecimal.valueOf(Double.parseDouble(base.getAmountAfterTax())).add(BigDecimal.valueOf(otaRoomPriceDto.getValue()))));
                                }
                                base.setAmountBeforeTax(base.getAmountAfterTax());
                                base.setCurrencyCode(base.getCurrencyCode());
                                Taxes taxes = new Taxes();
                                taxes.setCurrencyCode(base.getCurrencyCode());
                                base.setTaxes(taxes);
                                rate.setBase(base);
                                rateList.add(rate);
                                totalPrice = totalPrice.add(BigDecimal.valueOf(Double.valueOf(base.getAmountAfterTax())));

                            }
                        } else {
                            map.put("status", false);
                            map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("客栈中不存在满足预定的房型，请重试"));
                            return map;
                        }
                        roomRate.setRates(rateList);
                        Total total = new Total();
                        total.setAmountAfterTax(String.valueOf(totalPrice));
                        total.setAmountBeforeTax(total.getAmountAfterTax());
                        total.setCurrencyCode("RMB");
                        if (isCanbook) {
                            roomRate.setTotal(total);
                            roomRateList.add(roomRate);
                            //加载房型
                            roomType.setNumberOfUnits(isCanbook ? "true" : "false");
                            roomTypeList.add(roomType);
                        }
                    }
                    //价格计划
                    RatePlan ratePlan = new RatePlan();
                    ratePlan.setRatePlanCategory(ratePlan.getRatePlanCategory());
                    ratePlan.setRatePlanCode(otaRatePlan.getRatePlanCode());
                    RatePlanDescription ratePlanDescription = new RatePlanDescription();
                    ratePlanDescription.setName(otaRatePlan.getRatePlanName());
                    Text rateText = new Text();
                    rateText.setLanguage("en-US");
                    rateText.setValue(otaRatePlan.getRatePlanName());
                    ratePlanDescription.setText(rateText);
                    ratePlan.setRatePlanDescription(ratePlanDescription);
                    ratePlanList.add(ratePlan);
                }
                roomStay.setRoomTypes(roomTypeList);
                if (ArrayUtils.isEmpty(roomTypeList.toArray())) {
                    map.put("status", false);
                    map.put("data", new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("房型剩余放量不足，请重试"));
                    return map;
                }
                roomStay.setRoomRates(roomRateList);
                roomStay.setRatePlans(ratePlanList);
                List<GuestCount> guestCountList = new ArrayList<>();
                GuestCount guestCount = new GuestCount();
                guestCount.setCount(String.valueOf(availOrder.getHomeAmount()));
                guestCount.setAgeQualifyingCode("10");
                guestCountList.add(guestCount);
                roomStay.setGuestCounts(guestCountList);
                JointWisdomAvailCheckOrderSuccessResponse responseResult = new JointWisdomAvailCheckOrderSuccessResponse();
                responseResult.setEchoToken(responseResult.getEchoToken());
                responseResult.setTimeStamp(responseResult.getTimeStamp());
                responseResult.setSuccess("success");
                responseResult.setVersion(responseResult.getVersion());
//                responseResult.setXmlns(responseResult.getXmlns());

                BasicPropertyInfo basicPropertyInfo = new BasicPropertyInfo();
                basicPropertyInfo.setHotelName(bangInn.getInnName());
                basicPropertyInfo.setHotelCode(availOrder.getInnCode());
                roomStay.setBasicPropertyInfo(basicPropertyInfo);

                TimeSpan timeSpan = new TimeSpan();
                timeSpan.setStart(DateUtil.format(availOrder.getLiveTime(), "yyyy-MM-dd"));
                timeSpan.setEnd(DateUtil.format(availOrder.getLeaveTime(), "yyyy-MM-dd"));
                roomStay.setTimeSpan(timeSpan);
                List<RoomStay> roomStayList = new ArrayList<>();
                roomStayList.add(roomStay);
                responseResult.setRoomStays(roomStayList);
                map.put("status", true);
                map.put("data", responseResult);
                MessageCenterUtils.savePushTomsOrderLog(availOrder.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.ZH, availOrder.getChannelOrderCode(), availOrder.getId(), availOrder.getOmsOrderCode(), availOrder.getOrderStatus(), availOrder.getOrderStatus(), availOrder.getFeeStatus(), availOrder.toRoomAvail(company, availOrder).toString(), JacksonUtil.obj2json(responseResult), availOrder.getInnId(), availOrder.getInnCode(), "众荟试订单，toms返回值"));
                return map;
            }
        } catch (Exception e) {
            JointWisdomAvailCheckOrderSuccessResponse errorResult = new JointWisdomAvailCheckOrderSuccessResponse();
            JointWisdomAvailCheckOrderErrorResponse basicError = errorResult.getBasicError("处理众荟试订单异常");
            logger.info("对接众荟试订单出错,返回值=>" + basicError.toString() + e);
            map.put("status", false);
            map.put("data", basicError);
            return map;
        }
        return null;
    }


    @Override
    public Map<String, Object> dealAddOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解析xml得到订单对象
        Order order = XmlJointWisdomUtil.getAddOrder(xml);
        logger.info("众荟下单单号为：" + order.getChannelOrderCode());
        OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.ZH.name());
        //查询mapping房型信息
        JointWisdomInnRoomMappingDto jointWisdomInnRoomMappingDto = this.jointWisdomInnRoomDao.selectRoomMappingByInnIdAndRoomTypeId(order);
        //查询公司信息
        Company company = this.companyDao.selectCompanyById(jointWisdomInnRoomMappingDto.getCompanyId());
        order.setCompanyId(company.getId());
        //设置toms的房型与客栈id
        order.setRoomTypeId(String.valueOf(jointWisdomInnRoomMappingDto.getRoomTypeId()));
        order.setRoomTypeIdInt(jointWisdomInnRoomMappingDto.getRoomTypeId());
        order.setInnId(jointWisdomInnRoomMappingDto.getInnId());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "众荟创建订单传入参数"));
        //1.创建toms本地订单
        order.setXmlData(xml);
        //1.1创建toms本地订单，验证订单是否存在
        //验证订单是否存在
        Order checkOrder = this.orderDao.selectOrderByChannelOrderCodeAndSource(order);
        if (null != checkOrder) {
            String orderStatusMethod = this.orderService.getOrderStatusMethod(checkOrder);
            //解析返回值
            if (StringUtils.isNotEmpty(orderStatusMethod)) {
                net.sf.json.JSONObject jsonObjectOmsSearchOrder = net.sf.json.JSONObject.fromObject(orderStatusMethod);
                if (jsonObjectOmsSearchOrder.get("status").equals(200)) {
                    String omsOrderStatus = (String) jsonObjectOmsSearchOrder.get("orderStatus");
                    if (omsOrderStatus.equals("1")) {
                        //下单成功
                        //预定成功
                        JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
                        result.setMessage("预定成功");
                        result.setVersion(Version.v1003.getText());
                        result.setResponseType(OrderResponseType.Committed.name());
                        result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
                        map.put("status", true);
                        map.put("data", result);
                        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(result), order.getInnId(), order.getInnCode(), "众荟创建订单,toms返回值"));
                    } else if (omsOrderStatus.equals("0")) {
                        checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                        orderService.cancelOrderMethod(checkOrder);
                        map.put("status", false);
                        map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));

                    } else {
                        map.put("status", false);
                        map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));

                    }
                } else {
                    //调用oms取消订单
                    checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                    //调用渠道，oms取消订单接口
                    orderService.cancelOrderMethod(checkOrder);
                    map.put("status", false);
                    map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));

                }
            } else {
                checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                orderService.cancelOrderMethod(checkOrder);
                map.put("status", false);
                map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));

            }
            return map;
        }
        this.orderService.createOrderMethod(order.getChannelSource(), order);

        //查询当前公司设置的下单是自动或者手动
        //1.判断当前订单客栈属于哪个公司，查找公司设置的下单规则
        OrderConfig orderConfig = new OrderConfig(jointWisdomInnRoomMappingDto.getOtaInfoId(), company.getId(), Integer.valueOf(order.getInnId()));
        OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
        JsonModel jsonModel = null;
        if (null == orderConfigDto || 0 == orderConfigDto.getStatus()) {
            //自动下单
            //设置订单状态为：接受
            order.setOrderStatus(OrderStatus.ACCEPT);
            //设置订单房型信息
            order.setRoomTypeName(jointWisdomInnRoomMappingDto.getRoomTypeName());
            jsonModel = this.orderService.payBackDealMethod(order, new UserInfo(), OtaType.ZH.name());
        } else {
            //手动下单,手动下单修改订单状态为待处理
            order.setOrderStatus(OrderStatus.NOT_DEAL);
            //待处理订单写入付款金额和付款码
            order.setFeeStatus(FeeStatus.PAID);
            this.orderDao.updateOrderStatusAndFeeStatus(order);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "众荟下单", ChannelSource.ZH.name()));
            jsonModel = new JsonModel(true, "付款成功");
        }
        //封装返回对象
        if (jsonModel.isSuccess()) {
            //预定成功
            JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
            result.setMessage("预定成功");
            result.setVersion(Version.v1003.getText());
            result.setResponseType(OrderResponseType.Committed.name());
            result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
            map.put("status", true);
            map.put("data", result);
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(result), order.getInnId(), order.getInnCode(), "众荟创建订单,toms返回值"));
            return map;
        } else {
            //预定失败
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError(jsonModel.getMessage() + "  预定失败", Version.v1003.getText(), OrderResponseType.Committed.name()));
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(new JointWisdomAddOrderSuccessResponse().getBasicError(jsonModel.getMessage() + "  预定失败", Version.v1003.getText(), OrderResponseType.Committed.name())), order.getInnId(), order.getInnCode(), "众荟创建订单,toms返回值"));
            return map;

        }
    }

    @Override
    public Map<String, Object> dealCancelOrder(String xml) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解析xml
        Order order = XmlJointWisdomUtil.getCancelOrder(xml);
        logger.info("众荟取消订单号为：" + order.getChannelOrderCode());
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(order.getId());
        MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, null, orderParamDto.getInnId(), orderParamDto.getInnCode(), "众荟取消订单传入参数"));
        if (null != orderParamDto) {
            //取消订单，同步
            JsonModel jsonModel = this.orderService.cancelOrderMethod(orderParamDto);
            if (jsonModel.isSuccess()) {
                //预定成功
                JointWisdomAddOrderSuccessResponse result = new JointWisdomAddOrderSuccessResponse();
                result.setMessage("取消订单成功");
                result.setVersion(Version.v1003.getText());
                result.setResponseType(OrderResponseType.Cancelled.name());
                result.setHotelReservations(result.getHotelReservationResult(order.getChannelOrderCode(), order.getId()));
                map.put("status", true);
                map.put("data", result);
                MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, JacksonUtil.obj2json(result), orderParamDto.getInnId(), orderParamDto.getInnCode(), "众荟取消订单返回值"));
                return map;
            } else {
                map.put("status", false);
                map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("酒店拒绝取消订单", Version.v1003.getText(), OrderResponseType.Cancelled.name()));
                MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, JacksonUtil.obj2json(new JointWisdomAddOrderSuccessResponse().getBasicError("酒店拒绝取消订单", Version.v1003.getText(), OrderResponseType.Cancelled.name())), orderParamDto.getInnId(), orderParamDto.getInnCode(), "众荟取消订单返回值"));
                return map;
            }
        } else {
            map.put("status", false);
            map.put("data", new JointWisdomAddOrderSuccessResponse().getBasicError("订单不存在", Version.v1003.getText(), OrderResponseType.Cancelled.name()));
            MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, JacksonUtil.obj2json(new JointWisdomAddOrderSuccessResponse().getBasicError("订单不存在", Version.v1003.getText(), OrderResponseType.Cancelled.name())), orderParamDto.getInnId(), orderParamDto.getInnCode(), "众荟取消订单返回值"));
            return map;
        }
    }
}
