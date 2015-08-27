package com.fanqielaile.toms.support.util;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.UserInfo;

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
            inventoryRate.setPrice(price * 100);
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
}
