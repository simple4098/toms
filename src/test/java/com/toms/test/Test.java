package com.toms.test;


import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.enums.CurrencyCode;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class Test {
    public static void main(String[] args) throws Exception {


       /* Random r=new Random();
        int nextInt = r.nextInt(90000);
        int i=nextInt+10000;
        System.out.println(i);
        System.out.println(nextInt);*/

        CurrencyCode[] values = CurrencyCode.values();
        for (CurrencyCode c:values){
            System.out.println(c.name()+"-"+c.getValue());
        }

    }
}
