package com.toms.test;

import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.InventoryRelation;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqie.jw.dto.RoomPriceRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class TestJointWisdom {

    static String innId = "919_380741";

    static String ratePlanCode = "XCBAR";

    static String  roomTypeId = "95659";


    public static void main(String[] args) {

        pushRoomPrice();
       // pushInventory();
    }

    private static void pushRoomPrice() {
        RoomPrice roomPriceRelation = new RoomPrice();
        roomPriceRelation.setStart("2016-01-12");
        roomPriceRelation.setEnd("2016-01-15");

        roomPriceRelation.setInnId(innId);

        roomPriceRelation.setRatePlanCode(ratePlanCode);

        roomPriceRelation.setRoomTypeId(roomTypeId);
        List<RoomPriceRelation> relations = new ArrayList<RoomPriceRelation>();
        roomPriceRelation.setRelations(relations);
        RoomPriceRelation relation1 = new RoomPriceRelation();
        relation1.setStart("2016-01-12");
        relation1.setEnd("2016-01-13");
        relation1.setAmountAfterTax("400");
        relation1.setAmountBeforeTax("400");
        relation1.setUsed(true);
        relations.add(relation1);
        roomPriceRelation.setRelations(relations);
        RoomPriceRelation relation2 = new RoomPriceRelation();
        relation2.setStart("2016-01-13");
        relation2.setEnd("2016-01-15");
        relation2.setAmountAfterTax("50");
        relation2.setAmountBeforeTax("50");
        relation2.setUsed(false);
        relations.add(relation2);
        try {
            // 请求成功
//            JointWisdomARIUtils.pushRoomPrice(roomPriceRelation);
        } catch (Exception e) {
            // 请求失败
            e.printStackTrace();

        }
    }

    private static  void  pushInventory(){


        Inventory inventory = new Inventory();
        inventory.setInnId(innId);
        inventory.setRoomTypeId(roomTypeId);

        List<InventoryRelation> relations = new ArrayList<InventoryRelation>();
        InventoryRelation relation = new InventoryRelation();
        relations.add(relation);
        relation.setStart("2016-01-13");
        relation.setEnd("2016-01-15");
        relation.setInventoryCount(200);
        relation.setUsed(false);
        inventory.setRelations(relations);
        try {
            // 请求成功
//            JointWisdomARIUtils.pushRoomInventory(inventory);
        } catch (Exception e) {
            // 请求失败
            e.printStackTrace();
        }


    }


}
