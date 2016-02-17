package com.fanqielaile.toms.support.util;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/7/2
 * @version: v1.0.0
 */
public class TomsUtil {
    private void TomsUtil(){}

    public static String event(TBParam tbParam){
        if (tbParam!=null){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("innId",tbParam.getInnId());
            param.put("accountId",tbParam.getAccountId());
            param.put("json",tbParam.getPriceModelJson());
            param.put("isSj",tbParam.isSj());
            param.put("priceModel",tbParam.getPriceModel());
            param.put("otaId",tbParam.getOtaId());
            param.put("sJiaModel",tbParam.getsJiaModel());
            param.put("status",tbParam.getStatus());
            return JacksonUtil.obj2json(param);
        }
        return "";
    }

    //日期库存json字符串
    public static String obtInventory(RoomTypeInfo roomTypeInfo){
        Inventory inventory =null;
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (RoomDetail roomDetail:roomTypeInfo.getRoomDetail()){
            inventory = new Inventory();
            inventory.setDate(roomDetail.getRoomDate());
            inventory.setQuota(roomDetail.getRoomNum() == null ? 0 : roomDetail.getRoomNum());
            inventoryList.add(inventory);
        }
        return JacksonUtil.obj2json(inventoryList);
    }
    //日期库存json字符串
    public static String obtInventory( List<RoomDetail> roomDetailList){
        Inventory inventory =null;
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        if (!CollectionUtils.isEmpty(roomDetailList)){
            for (RoomDetail roomDetail:roomDetailList){
                inventory = new Inventory();
                inventory.setDate(roomDetail.getRoomDate());
                inventory.setQuota(roomDetail.getRoomNum() == null ? 0 : roomDetail.getRoomNum());
                inventoryList.add(inventory);
            }
            return JacksonUtil.obj2json(inventoryList);
        }
        return  null;
    }



    public static String obtInventoryRate(RoomTypeInfo r,OtaPriceModelDto priceModelDto){
        InventoryPrice inventoryPrice = new InventoryPrice();

        List<InventoryRate> inventory = inventory(r.getRoomDetail(), null, priceModelDto,null);
        inventoryPrice.setInventory_price(inventory);
        return  JacksonUtil.obj2json(inventoryPrice);
    }

    public static String obtInventoryRate(List<RoomDetail> roomDetail,OtaPriceModelDto priceModelDto){
        if (!CollectionUtils.isEmpty(roomDetail)){
            InventoryPrice inventoryPrice = new InventoryPrice();
            List<InventoryRate> inventory = inventory(roomDetail, null, priceModelDto,null);
            inventoryPrice.setInventory_price(inventory);
            return  JacksonUtil.obj2json(inventoryPrice);
        }
        return  null;
    }

    public static String obtInventoryRate(RoomTypeInfo r,OtaPriceModelDto priceModelDto,OtaRoomPriceDto priceDto,OtaCommissionPercent commission){
        InventoryPrice inventoryPrice = new InventoryPrice();

        List<InventoryRate> inventory = inventory(r.getRoomDetail(), priceDto, priceModelDto,commission);
        inventoryPrice.setInventory_price(inventory);
        return JacksonUtil.obj2json(inventoryPrice);
    }

    public   static List<InventoryRate>  inventory(List<RoomDetail> roomDetails,OtaRoomPriceDto priceDto,OtaPriceModelDto priceModelDto,OtaCommissionPercent commission){
        InventoryRate inventoryRate = null;
        double price = 0;
        Double value = null;
        Date startDate = null;
        Date endDate = null;
        if (priceDto!=null) {
            value = priceDto.getValue() * Constants.tpPriceUnit;
            startDate = priceDto.getStartDate();
            endDate = priceDto.getEndDate();
        }
        if (!CollectionUtils.isEmpty(roomDetails)){
            List<InventoryRate> inventoryRateList = new ArrayList<InventoryRate>();
            for (RoomDetail detail : roomDetails) {
                inventoryRate = new InventoryRate();
                inventoryRate.setDate(detail.getRoomDate());
                price = new BigDecimal(detail.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                //售价只有MAI2DI才展示
                if (commission!=null && commission.getsJiaModel().equals(UsedPriceModel.MAI2DI.name())){
                    price = TomsUtil.price(price,new BigDecimal(commission.getCommissionPercent()));
                }
                price = price*Constants.tpPriceUnit;
                Date parseDate = DateUtil.parseDate(detail.getRoomDate());
                //在设定的范围内才对价格进行处理
                if (priceDto!=null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
                    price = price + value;
                }
                //tp店价格为分，我们自己系统价格是元
                inventoryRate.setPrice(price);
                inventoryRateList.add(inventoryRate);
            }
            return inventoryRateList;
        }
        return null;

    }

    public static String obtInventoryRate(List<RoomDetail> roomDetail, OtaPriceModelDto priceModelDto, OtaRoomPriceDto priceDto, OtaCommissionPercent commission){
        InventoryPrice inventoryPrice = new InventoryPrice();
        List<InventoryRate> inventory = inventory(roomDetail, priceDto, priceModelDto,commission);
        inventoryPrice.setInventory_price(inventory);
        return JacksonUtil.obj2json(inventoryPrice);
    }

    public static List<OrderConfigDto> orderConfig(String innId, String[] otaInfoIds, UserInfo currentUser,HttpServletRequest request) {
        List<OrderConfigDto> list = new ArrayList<OrderConfigDto>();
        if (otaInfoIds!=null){
            OrderConfigDto orderConfigDto = null;
            for (String otaInfoId:otaInfoIds){
                orderConfigDto = new OrderConfigDto();
                String status = request.getParameter(Constants.STATUS + otaInfoId);
                orderConfigDto.setStatus(Integer.valueOf(status));
                orderConfigDto.setInnId(Integer.valueOf(innId));
                orderConfigDto.setOtaInfoId(otaInfoId);
                orderConfigDto.setCompanyId(currentUser.getCompanyId());
                orderConfigDto.setModifierId(currentUser.getId());
                list.add(orderConfigDto);
            }
        }
        return list;
    }

    public static  String  listDateToStr(List<String> date){
        StringBuilder buffer = new StringBuilder();
        if (!CollectionUtils.isEmpty(date)){
            for (String da:date){
                buffer.append(da).append(",");
            }
            if (buffer.length()!=0){
                buffer.deleteCharAt(buffer.length()-1);
            }
        }
        return buffer.toString();
    }

    public static List<String> strToList(String dateJson) {
        if (dateJson!=null && dateJson.length()!=0){
            String[] split = dateJson.split(",");
            return Arrays.asList(split);
        }
        return null;
    }

    public static TBParam toTbParam(BangInn bangInn,Company company,OtaInnOtaDto otaInnOtaDto){
        TBParam tbParam = new TBParam();
        tbParam.setInnId(String.valueOf(bangInn.getInnId()));
        tbParam.setAccountId(String.valueOf(bangInn.getAccountId()));
        tbParam.setCommissionPercent(otaInnOtaDto.getCommissionPercent());
        tbParam.setPriceModel(otaInnOtaDto.getPriceModel());
        tbParam.setSj(true);
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        tbParam.setsJiaModel(otaInnOtaDto.getsJiaModel());
        tbParam.setCompanyCode(company.getCompanyCode());
        return  tbParam;
    }


    public static double getPriceRoundUp(double price) {
        return new BigDecimal(price).setScale(0, BigDecimal.ROUND_UP).doubleValue();
    }


    //处理价格
    public static Double price(Double price,BigDecimal percentage){
        if (percentage!=null){
            BigDecimal bigDecimal = new BigDecimal(100);
            BigDecimal subtract = bigDecimal.subtract(percentage);
            Double divide = DcUtil.divide(subtract, bigDecimal);
            Double formatDouble = DcUtil.formatDouble(price * divide);
            price = formatDouble;
           /* return getPriceRoundUp(formatDouble);*/
        }
        return getPriceRoundUp(price);
    }

    //处理比例
    public static BigDecimal getPercent(BigDecimal percentage) {
        BigDecimal divide = BigDecimal.ZERO;
        if (percentage != null) {
            BigDecimal bigDecimal = new BigDecimal(100);
            divide = BigDecimal.valueOf(DcUtil.divide(percentage, bigDecimal));
        }
        return divide;
    }


    public static TBParam toOtaParam(BangInn bangInn, Company company, Integer sj,OtaInfoRefDto otaInfoRefDto) {
        String priceModel = otaInfoRefDto.getUsedPriceModel().name();
        TBParam tbParam = new TBParam();
        tbParam.setInnId(String.valueOf(bangInn.getInnId()));
        tbParam.setAccountId(String.valueOf(bangInn.getAccountId()));
        if (otaInfoRefDto.getCommissionPercentDto()!=null){
            tbParam.setCommissionPercent(new BigDecimal(otaInfoRefDto.getCommissionPercentDto().getCommissionPercent()));
        }else {
            tbParam.setCommissionPercent(new BigDecimal(1));
        }
        tbParam.setPriceModel(priceModel);
        tbParam.setSj(sj==1);
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        tbParam.setsJiaModel(priceModel);
        tbParam.setCompanyCode(company.getCompanyCode());
        tbParam.setUpDown(true);
        return  tbParam;
    }

    public static List<ProxyInns> toProxyInns(List<BangInnDto> bangInnDtoList, Company company){
        if (!CollectionUtils.isEmpty(bangInnDtoList)){
            List<ProxyInns> list = new ArrayList<ProxyInns>();
            ProxyInns p = null;
            String room_type = null;
            String roomStatus = null;
            List<PricePattern> pricePatterns = null;
            for (BangInnDto b:bangInnDtoList){
                p = new ProxyInns();
                p.setPricePatterns(pricePatterns);
                p.setInnId(b.getInnId());
                p.setAccountId(b.getAccountId());
                p.setAccountIdDi(b.getAccountIdDi());
                room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), b.getAccountId().toString(), CommonApi.ROOM_TYPE);
                roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), b.getAccountId().toString(), CommonApi.roomStatus);
                p.setRoomTypeUrl(room_type);
                p.setRoomStatusUrl(roomStatus);
                list.add(p);
            }
            return list;
        }
        return null;
    }

    /**
     * 获取每一天开始时间
     *
     * @param beginDate
     * @return
     */
    public static String getDayBeafore(String beginDate) {
        if (StringUtils.isNotEmpty(beginDate)) {
            Date date = DateUtil.parse(beginDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }

    /**
     * 获取每一天结束时间
     *
     * @param endDate
     * @return
     */
    public static String getDayEnd(String endDate) {
        if (StringUtils.isNotEmpty(endDate)) {
            Date date = DateUtil.parse(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }

    /**
     * 转换携程的日期
     *
     * @param arrival
     * @return
     */
    public static String getDateString(String arrival) {
        if (StringUtils.isNotEmpty(arrival)) {
            String[] arry = arrival.split("T");
            if (arry.length > 1) {
                return arry[0] + " " + arry[1];
            } else {
                return arry[0];
            }
        }
        return null;
    }

    /**
     * 将日期装换为utc时间
     *
     * @param date
     * @return
     */
    public static String getDateStringFormat(Date date) {
        if (null != date) {
            String format = DateUtil.format(date, "yyyy-MM-dd");
            return format + "T00:00:00";
        }
        return null;
    }

    /**
     * 处理价格
     * @param roomPrice 房型原价
     * @param parseDate 房型价格日期
     * @param commission 佣金比例
     * @param priceDto 增减价
     */
    public static  Double price(Double roomPrice ,Date parseDate,OtaCommissionPercentDto commission,OtaRoomPriceDto priceDto){
        if (commission != null && commission.getsJiaModel().equals(UsedPriceModel.MAI2DI.name())) {
            roomPrice = TomsUtil.price(roomPrice, new BigDecimal(commission.getCommissionPercent()));
        }
        if (priceDto!=null){
            double value = priceDto.getValue();
            Date startDate1 = priceDto.getStartDate();
            Date endDate1 = priceDto.getEndDate();
            if (parseDate.getTime() >= startDate1.getTime() && endDate1.getTime() >= parseDate.getTime()) {
                roomPrice = roomPrice + value;
            }
        }
        return  roomPrice;
    }

    /**
     * 房仓 携程 组装数据
     * @param tbParam
     * @param bangInn
     * @param omsInnDto
     */
    public static void sjModel(TBParam tbParam,BangInn bangInn,InnDto omsInnDto){
        if (Constants.DI.equals(tbParam.getsJiaModel()) && tbParam.getAccountId() == null) {
            if (!tbParam.isSj()) {
                bangInn.setAccountIdDi(null);
            } else {
                bangInn.setSj(1);
            }
        } else {
            BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
        }
    }

    /**
     * 推送过来的xml 拼接accountId
     * @param pushRoomList
     */
    public static String pushXml(List<PushRoom> pushRoomList) {

        if (!CollectionUtils.isEmpty(pushRoomList)){
            StringBuffer buffer = new StringBuffer();
            for (PushRoom r:pushRoomList){
                buffer.append(" accountId:").append(r.getRoomType().getAccountId());
            }
            return  buffer.toString();
        }
        return null;
    }

    /**
     * 得到系统当前时间的前30分钟,间隔3分钟
     *
     * @return
     */
    public static Map<String, String> getFifteenDate() {
        Map<String, String> map = new HashMap<>();
        long time = new Date().getTime();
        long l = (time / 1000 / 60 - 30) * 60 * 1000;
        String fifteenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(l);
        long l1 = (time / 1000 / 60 - 27) * 60 * 1000;
        String fourteenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(l1);
        map.put("fifteen", fifteenDate);
        map.put("fourteen", fourteenDate);
        return map;
    }


    /**
     *
     * @param otaPriceModel 价格模式对象
     */
    public static String obtOtaPriceModelId(OtaPriceModelDto otaPriceModel) {
        String otaPriceModelId=null;
        if (StringUtils.isEmpty(otaPriceModel.getId())){
            otaPriceModelId = otaPriceModel.getUuid();
        }else {
            otaPriceModelId = otaPriceModel.getId();
        }
        return otaPriceModelId;
    }

    public static String obtOtaInnOtaId(OtaInnOtaDto otaInnOta) {
        String otaInnOtaId = null;
        if (StringUtils.isEmpty(otaInnOta.getId())){
            otaInnOtaId = otaInnOta.getUuid();
        }else {
            otaInnOtaId = otaInnOta.getId();
        }
        return otaInnOtaId;
    }

    /**
     * 构建 OtaRoomPriceDto 对象
     * @param companyId 公司id
     * @param roomTypeId 房型id
     * @param otaInfoId 渠道id
     * @param price 价格增减价对象
     * @param innId 客栈id
     * @param userId 修改人
     */
    public static OtaRoomPriceDto buildRoomPrice(String companyId,Integer roomTypeId,String otaInfoId,AddFangPrice price,Integer innId,String userId){
        OtaRoomPriceDto priceDto = new OtaRoomPriceDto(companyId,roomTypeId,otaInfoId);
        priceDto.setStartDateStr(price.getStartDateStr());
        priceDto.setEndDateStr(price.getEndDateStr());
        priceDto.setValue(price.getPriceChange());
        priceDto.setInnId(innId);
        priceDto.setModifierId(userId);
        priceDto.setRoomTypeName(price.getRoomTypeName());
        return  priceDto;
    }


    public static void obtNull(Object o){
        o = null;
        System.gc();
    }
}
