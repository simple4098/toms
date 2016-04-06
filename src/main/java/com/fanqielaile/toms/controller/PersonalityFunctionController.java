package com.fanqielaile.toms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/25
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/personality")
public class PersonalityFunctionController extends BaseController {

    @RequestMapping("/function")
    public String orderExport(){

        return "/personality/function";
    }

}
