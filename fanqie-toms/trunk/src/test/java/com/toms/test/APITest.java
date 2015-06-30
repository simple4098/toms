package com.toms.test;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.request.XhotelAddRequest;
import com.taobao.api.request.XhotelGetRequest;
import com.taobao.api.response.UserSellerGetResponse;
import com.taobao.api.response.XhotelAddResponse;
import com.taobao.api.response.XhotelGetResponse;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public class APITest {
    protected static String url = "http://gw.api.tbsandbox.com/router/rest";//沙箱环境调用地址
    //正式环境需要设置为:http://gw.api.taobao.com/router/rest
    protected static String appkey = "1023192376";
    protected static String appSecret = "sandboxfbdf281c93b167601781cd228";
    protected static String sessionkey = "6102630889b6592676681403674c57dec774131f5d37e973636630123"; //如 沙箱测试帐号sandbox_c_1授权后得到的sessionkey
    public static void testUserGet() {
        TaobaoClient client=new DefaultTaobaoClient(url, appkey, appSecret);
        XhotelAddRequest req=new XhotelAddRequest();
        req.setOuterId("2015062301034");
        req.setName("沙箱测试003");
        req.setUsedName("沙箱测试003");
        req.setCity(110100L);
        try {
            XhotelAddResponse response = client.execute(req , sessionkey);
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public static void   get(){
        TaobaoClient client=new DefaultTaobaoClient(url, appkey,appSecret );
        XhotelGetRequest req=new XhotelGetRequest();
        req.setHid(16496002123l);
        try {
            XhotelGetResponse response = client.execute(req , sessionkey);
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //APITest.testUserGet();
        APITest.get();
    }

}
