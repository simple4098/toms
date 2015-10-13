package com.fanqielaile.toms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DESC : 手动执行
 * @author : 番茄木-ZLin
 * @data : 2015/10/13
 * @version: v1.0.0
 */
@Controller("/mt")
public class MTController {

    @RequestMapping("/view")
    public String mt(){

        return "/mt_view";
    }
}
