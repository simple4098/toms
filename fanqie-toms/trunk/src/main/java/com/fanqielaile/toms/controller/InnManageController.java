package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.support.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 客栈管理
 * Created by wangdayin on 2015/5/15.
 */
@Controller
@RequestMapping("inn_manage")
public class InnManageController extends BaseController {
    @Resource
    private IBangInnService bangInnService;

    /**
     * 查询标签对应的客栈
     *
     * @param model
     */
    @RequestMapping("find_label_inn")
    public void findLabelInn(Model model) {
        UserInfo currentUser = getCurrentUser();
        List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, bangInnAndLabel);
    }


}
