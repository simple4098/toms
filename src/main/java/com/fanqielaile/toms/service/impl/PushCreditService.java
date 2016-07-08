package com.fanqielaile.toms.service.impl;

import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.service.IPushCreditService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frd
 *         2016/7/8.
 */
@Service
public class PushCreditService implements IPushCreditService {

    @Override
    public void pushCredit(String innIds) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("innIds",innIds);
        String json = JacksonUtil.obj2json(map);
        String httpKvPost = HttpClientUtil.httpKvPost(CommonApi.pushCredit, json);
        JSONObject jsonInn = JSONObject.fromObject(httpKvPost);
        //客栈
        if (!TomsConstants.SUCCESS.equals(jsonInn.get("status").toString())) {
            throw new Exception("推送信用住异常");
        }

    }
}
