package com.fanqielaile.toms.controller;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OrderMethod;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IUserInfoService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangdayin on 2015/6/19.
 * OTA对接接口
 */
@Controller
@RequestMapping("")
public class OTAManageController extends BaseController {
    @Resource
    private IOrderService orderService;
    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private Md5PasswordEncoder passwordEncoder;

    /**
     * 淘宝调用的接口
     *
     * @return
     */
    @RequestMapping("taobaoService")
    @ResponseBody
    public Object TBService(HttpServletRequest request) throws Exception {
        //TODO 如果采用流的方式传递参数，需要处理成字符串
        String xmlStr = HttpClientUtil.convertStreamToString(request.getInputStream());
        Result result = new Result();
        if (StringUtils.isNotEmpty(xmlStr)) {
            //接口调用验证用户
            UserInfo userNameAndPassword = OrderMethodHelper.getUserNameAndPassword(xmlStr);
            UserInfo userInfo = (UserInfo) userInfoService.loadUserByUsername(userNameAndPassword.getUsername());
            if (null != userInfo) {
                //验证用户密码
                if (userInfo.getPassword().equals(passwordEncoder.encodePassword(userNameAndPassword.getPassword(), ""))) {
                    //得到跟节点
                    String rootElementString = XmlDeal.getRootElementString(xmlStr);
                    //根据根节点判断执行的方法
                    if (rootElementString.equals(OrderMethod.BookRQ.name())) {
                        //创建订单
                        Order order = orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
                        result.setResultCode("1");
                        result.setMessage(order.getId());
                    } else if (rootElementString.equals(OrderMethod.CancelRQ.name())) {
                        //取消订单
                        boolean flag = orderService.cancelOrder(xmlStr, ChannelSource.TAOBAO);
                        if (flag) {
                            result.setResultCode("0");
                            result.setMessage("取消订单成功");
                        } else {
                            result.setResultCode("-209");
                            result.setMessage("取消订单失败");
                        }
                    } else if (rootElementString.equals(OrderMethod.PaySuccessRQ.name())) {
                        //付款成功回调
                        boolean flag = orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO, getCurrentUser());
                        if (flag) {
                            result.setResultCode("0");
                            result.setMessage("付款成功");
                        } else {
                            result.setResultCode("-400");
                            result.setMessage("付款失败");
                        }
                    } else {
                        throw new TomsRuntimeException("xml参数错误");
                    }
                }
            } else {
                result.setMessage("创建订单失败");
                result.setResultCode("-400");
            }
        } else {
            result.setMessage("创建订单失败，原因：参数不正确");
            result.setResultCode("-400");
        }
        return result;
    }
}
