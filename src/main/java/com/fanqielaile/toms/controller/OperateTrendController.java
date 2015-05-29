package com.fanqielaile.toms.controller;


import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOperateTrendService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

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

    @Resource
    private IOperateTrendService operateTrendService;
    @Resource
    private IOrderService orderService;

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

    /**
     * 客服资料分析
     * @param paramDto  查询参数
     * @param page 当前页
     */
    @RequestMapping("/kf")
    public String kf(Model model,ParamDto paramDto,@RequestParam(defaultValue = "1",required = false)Integer page){
        try {
            UserInfo currentUser = getCurrentUser();
            paramDto.setUserId(currentUser.getId());
            paramDto.setCompanyId(currentUser.getCompanyId());
            paramDto.setDataPermission(currentUser.getDataPermission()==1);
            paramDto.setPage(page);
            CustomerDto customer = operateTrendService.findCustomer(paramDto);
            model.addAttribute("customer",customer);
        } catch (Exception e) {
            logger.error("趋势报表异常",e);
        }
        return "/operate/kf";
    }

    /**
     * 订单来源页面
     * @param paramDto 查询参数
     */
    @RequestMapping("/order")
    public String orderView(Model model,ParamDto paramDto){

        return "/operate/order";
    }
    /**
     * 订单来源
     * @param paramDto 查询参数
     */
    @RequestMapping("/ajax/order")
    public void order(Model model,ParamDto paramDto){
        try {
            paramDto.setStartDate("2015-05-23");
            paramDto.setEndDate("2015-05-23 23:59:59");
            paramDto.setInnId(753);
            paramDto.setUserId("2df7667a-6cf4-4320-8449-6483c643ea62");
            OrderSourceDto sourceDto = orderService.findOrderSourceDto(paramDto);
            model.addAttribute("orderSource",sourceDto);
        } catch (Exception e) {
            logger.error("趋势报表异常",e);
            throw new TomsRuntimeException("error error error",e);
        }
    }

    /**
     * 订单来源详情
     * @param paramDto 查询参数
     */
    @RequestMapping("/ajax/orderDetail")
    public void orderD(Model model,ParamDto paramDto){
        try {
            paramDto.setStartDate("2015-05-23");
            paramDto.setEndDate("2015-05-23 23:59:59");
            paramDto.setInnId(753);
            paramDto.setUserId("2df7667a-6cf4-4320-8449-6483c643ea62");
            Map<String, Object> orderSourceDetail = orderService.findOrderSourceDetail(paramDto);
            model.addAttribute("data",orderSourceDetail.get("data"));
            model.addAttribute("list",orderSourceDetail.get("list"));
        } catch (Exception e) {
            logger.error("趋势报表异常",e);
            throw new TomsRuntimeException("error error error",e);
        }
    }


}
