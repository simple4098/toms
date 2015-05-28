package com.fanqielaile.toms.controller;


import com.fanqie.core.domain.InnCustomer;
import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * DESC : 趋势报表
 * @author : 番茄木-ZLin
 * @data : 2015/4/22
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/operate")
public class OperateTrendController extends BaseController  {
    private static Logger logger = LoggerFactory.getLogger(OperateTrendController.class);

    //运营趋势页面
    @RequestMapping("/qs")
    public String qs(){
        return "/operate/qs";
    }

    //ajax 读取运营大概信息
    @RequestMapping("/ajax/qs")
    public void ajaxQs(Model model,ParamDto paramDto){
        try {
            UserInfo currentUser = getCurrentUser();
            paramDto.setUserId(currentUser.getId());
            paramDto.setCompanyId(currentUser.getCompanyId());
            String gets = HttpClientUtil.httpPost(CommonApi.QS, paramDto);
            OperateTrend operateTrend = JacksonUtil.json2obj(gets, OperateTrend.class);
            model.addAttribute("operateTrend",operateTrend);
        } catch (IOException e) {
            logger.error("趋势报表异常",e);
        }
    }

    //运营趋势折线图
    @RequestMapping("/qsDetail")
    public void qsDetail(Model model,ParamDto paramDto){
        try {
            UserInfo currentUser = getCurrentUser();
            paramDto.setUserId(currentUser.getId());
            paramDto.setCompanyId(currentUser.getCompanyId());
            String gets = HttpClientUtil.httpPost(CommonApi.QSDetail, paramDto);
            JSONObject jsonObject = JSONObject.fromObject(gets);
            Object result = jsonObject.get("result");
            model.addAttribute("result",result);
        } catch (IOException e) {
            logger.error("趋势报表异常",e);
        }

    }

    @RequestMapping("/kf")
    public String kf(Model model,ParamDto paramDto,@RequestParam(defaultValue = "1",required = false)Integer page){
        try {
           // String gets = HttpClientUtil.httpGets("http://localhost:8083/api/toms/operate.json");
            paramDto.setStartDate("2015-04-01 00:00:00");
            paramDto.setEndDate("2015-04-03 23:59:00");
            paramDto.setTagId("1");
            paramDto.setUserId("2df7667a-6cf4-4320-8449-6483c643ea62");
            paramDto.setInnId(12087);
            paramDto.setDataPermission(true);
            paramDto.setCompanyId("d0392bc8-131c-48a4-846e-c81c66097781");
            paramDto.setTagId("d51c1bad-56a4-420b-aea2-fcace22af546");
            paramDto.setPage(page);
            String kf = HttpClientUtil.httpPost(CommonApi.KF, paramDto);
            String kf_d = HttpClientUtil.httpPost(CommonApi.KF_D, paramDto);
            JSONObject jsonObject = JSONObject.fromObject(kf);
            JSONObject kfDObject = JSONObject.fromObject(kf_d);
            Object rows = kfDObject.get("rows");
            Pagination pagination = JacksonUtil.json2obj(kfDObject.get("pagination").toString(),Pagination.class);
            List<InnCustomer> innCustomer  = JacksonUtil.json2list(rows.toString(), InnCustomer.class);
            Integer totalCityNum =(Integer) jsonObject.get("totalCityNum");
            Integer totalNum =(Integer)jsonObject.get("totalNum");
            model.addAttribute("totalCityNum",totalCityNum);
            model.addAttribute("totalNum",totalNum);
            model.addAttribute("innCustomerList",innCustomer);
            model.addAttribute("pagination",pagination);
        } catch (IOException e) {
            logger.error("趋势报表异常",e);
        }
        return "/operate/kf";
    }

    /**
     * 订单来源
     * @param paramDto 查询参数
     */
    @RequestMapping("/order")
    public String order(Model model,ParamDto paramDto){
        try {
            paramDto.setStartDate("2015-05-23");
            paramDto.setEndDate("2015-05-23 23:59:59");
            paramDto.setInnId(753);
            paramDto.setUserId("2df7667a-6cf4-4320-8449-6483c643ea62");
            String  order = HttpClientUtil.httpPost(CommonApi.ORDER, paramDto);
            JSONObject jsonObject = JSONObject.fromObject(order);
            if (jsonObject.get("obj")!=null){
               OrderSourceDto orderSource  = JacksonUtil.json2obj(jsonObject.get("obj").toString(), OrderSourceDto.class);
               model.addAttribute("orderSource",orderSource);
            }
            if (jsonObject.get("rows")!=null){
                Object rows = jsonObject.get("rows");
                List<OrderSource> list  = JacksonUtil.json2list(rows.toString(), OrderSource.class);
                model.addAttribute("list",list);
            }

        } catch (Exception e) {
            logger.error("趋势报表异常",e);
            throw new TomsRuntimeException("error error error",e);
        }
        return "/operate/order";
    }


}
