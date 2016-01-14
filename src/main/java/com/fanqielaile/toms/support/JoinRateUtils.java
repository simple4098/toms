package com.fanqielaile.toms.support;


import com.fanqie.jw.dto.InventoryRelation;
import com.fanqie.jw.dto.RoomPriceRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *    合并Rate节点。避免同价格每天一个rate节点。
 *    避免连接众荟时，Rate节点过来出现 ReadTimeOut  Exception
 *
 *
 */
public class JoinRateUtils {


    private static final Logger LOGGER = LoggerFactory.getLogger(JoinRateUtils.class);

    private static  final String format = "yyyy-MM-dd";


    /**
     *   传入的日期字符串转为Java对象
     * @param str
     *        日期字符串
     * @return
     *        日期对象
     */
    private  static Date strToDate(String str){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return  sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error("合并rate节点出错:",e);
            throw new RuntimeException(e);
        }
    }


    /**
     *  减去天数的日期。并返回对应的日期字符串
     * @param date
     *          需要减去的日期对象
     * @param days
     *          减去的天数
     */
    public  static String minusDaysToStr(Date date,int days){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,days);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return  sdf.format(new Date(c.getTimeInMillis()));
    }






    /**
     * 合并房价节点
     * @param relations
     */
    public static  List<RoomPriceRelation> joinRoomPriceRate(List<RoomPriceRelation> relations){
        Collections.sort(relations,new RateOrder());
        String lastMoney = "-1";
        RoomPriceRelation rr = null;
        List<RoomPriceRelation> newRoomPriceRelations = new ArrayList<RoomPriceRelation>();
        for (RoomPriceRelation relation : relations) {
                if(relation.getAmountBeforeTax().equals(lastMoney)){//如果当前节点的价格等同上一次节点价格。当前Rate节点就可以合并
                    rr.setEnd(relation.getEnd());
                }else{//触发新的Rate节点
                    if(null!=rr){
                        rr.setEnd(dealEndDate(rr.getEnd()));
                        newRoomPriceRelations.add(rr);
                    }
                    rr = copyRateData(relation);
                    lastMoney = rr.getAmountBeforeTax();
                }
            }
        rr.setEnd(dealEndDate(rr.getEnd()));
        newRoomPriceRelations.add(rr);
        return newRoomPriceRelations;
    }



    /**
     * 合并库存
     * @param relations
     */
    public static  List<InventoryRelation> joinInventory(List<InventoryRelation> relations){
        Collections.sort(relations, new InventoryOrder());
        int lastCount = -1;
        InventoryRelation ir = null;
        List<InventoryRelation> newInventoryRelations = new ArrayList<InventoryRelation>();
        for (InventoryRelation relation : relations) {
            if(relation.getInventoryCount()==lastCount){//如果当前节点的库存等同上一次节点库存。当前Inventory节点就可以合并
                ir.setEnd(relation.getEnd());
            }else{//触发新的Rate节点
                if(null!=ir){
                    ir.setEnd(dealEndDate(ir.getEnd()));
                    newInventoryRelations.add(ir);
                }
                ir = copyInventoryData(relation);
                lastCount = ir.getInventoryCount();
            }
        }
        ir.setEnd(dealEndDate(ir.getEnd()));
        newInventoryRelations.add(ir);
        return newInventoryRelations;
    }

    /**
     *       把老的库存关系数据复制到新的对象中
     * @param relation
     *         老的关系
     * @return
     *         新的关系
     */
    private static InventoryRelation copyInventoryData(InventoryRelation relation) {
        InventoryRelation ir = new InventoryRelation();
        ir.setStart(relation.getStart());
        ir.setEnd(relation.getEnd());
        ir.setUsed(relation.getUsed());
        ir.setInventoryCount(relation.getInventoryCount());
        return ir;
    }

    /**
     *  处理接受时间
     * @param date 需要处理日期字符串
     */
    private static String dealEndDate(String date) {
        return minusDaysToStr(strToDate(date),-1);
    }

    /**
     *    复制数据到新的对象
     * @param relation
     *         被复制的关系对象
     * @return
     *        新的对象
     */
    private static RoomPriceRelation copyRateData(RoomPriceRelation relation) {
        RoomPriceRelation rr = new RoomPriceRelation();
        rr.setUsed(relation.getUsed());
        rr.setStart(relation.getStart());
        rr.setAmountAfterTax(relation.getAmountAfterTax());
        rr.setAmountBeforeTax(relation.getAmountBeforeTax());
        rr.setEnd(relation.getEnd());
        return rr;
    }

    /**
     *  日期排序工具类房价
     */
   static class RateOrder implements Comparator<RoomPriceRelation> {
        @Override
        public int compare(RoomPriceRelation o1, RoomPriceRelation o2) {
            return strToDate(o1.getStart()).compareTo(strToDate(o2.getStart()));
        }
    }


    /**
     *  日期排序工具类库存
     */
    static class InventoryOrder implements Comparator<InventoryRelation> {
        @Override
        public int compare(InventoryRelation o1, InventoryRelation o2) {
            return strToDate(o1.getStart()).compareTo(strToDate(o2.getStart()));
        }
    }

}
