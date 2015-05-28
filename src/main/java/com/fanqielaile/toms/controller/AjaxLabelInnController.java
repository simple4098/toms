package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.support.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC : ajax请求控制器
 * @author : 番茄木-ZLin
 * @data : 2015/5/28
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/ajax")
public class AjaxLabelInnController extends BaseController{
    @Resource
    private IBangInnService bangInnService;

    @RequestMapping("/label")
    public void label(Model model){
        UserInfo currentUser = getCurrentUser();
        List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, bangInnAndLabel);
    }
}
