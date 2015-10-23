package com.fanqielaile.toms.support.util;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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

    //日期库存json字符串 - 根据dateList 把库存设置为0
    public static String obtInventory(RoomTypeInfo roomTypeInfo,List<String> dateList){
        Inventory inventory =null;
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (RoomDetail roomDetail:roomTypeInfo.getRoomDetail()){
            inventory = new Inventory();
            inventory.setDate(roomDetail.getRoomDate());
            if (dateList.contains(roomDetail.getRoomDate())){
                inventory.setQuota(Constants.ZERO_QUOTA);
            }else {
                inventory.setQuota(roomDetail.getRoomNum() == null ? Constants.ZERO_QUOTA : roomDetail.getRoomNum());
            }
            inventoryList.add(inventory);
        }
        return JacksonUtil.obj2json(inventoryList);
    }

    public static String obtInventoryRate(RoomTypeInfo r,OtaPriceModelDto priceModelDto){
        List<InventoryRate> inventoryRateList = new ArrayList<InventoryRate>();
        InventoryPrice inventoryPrice = new InventoryPrice();
        InventoryRate inventoryRate = null;
        double price = 0;
        for (RoomDetail detail : r.getRoomDetail()) {
            inventoryRate = new InventoryRate();
            inventoryRate.setDate(detail.getRoomDate());
            price = new BigDecimal(detail.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
            //tp店价格为分，我们自己系统价格是元
            inventoryRate.setPrice(price * Constants.tpPriceUnit);
            inventoryRateList.add(inventoryRate);
        }
        inventoryPrice.setInventory_price(inventoryRateList);
        return  JacksonUtil.obj2json(inventoryPrice);
    }
    public static String obtInventoryRate(RoomTypeInfo r,OtaPriceModelDto priceModelDto,OtaRoomPriceDto priceDto){
        List<InventoryRate> inventoryRateList = new ArrayList<InventoryRate>();
        InventoryPrice inventoryPrice = new InventoryPrice();
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
            for (RoomDetail detail : r.getRoomDetail()) {
                inventoryRate = new InventoryRate();
                inventoryRate.setDate(detail.getRoomDate());
                price = new BigDecimal(detail.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
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
            inventoryPrice.setInventory_price(inventoryRateList);
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
}
