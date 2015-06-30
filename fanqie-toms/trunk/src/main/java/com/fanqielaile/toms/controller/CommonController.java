package com.fanqielaile.toms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公用控制器
 * Created by wangdayin on 2015/5/18.
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController {
    /**
     * 异常统一处理类
     *
     * @param model
     * @param message
     * @return
     */
    @RequestMapping("error")
    public String toError(Model model, String message) {
        model.addAttribute("msg", message);
        return "error";
    }
}
