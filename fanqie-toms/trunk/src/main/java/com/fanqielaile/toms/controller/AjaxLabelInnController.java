package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.helper.BangInnDataCheckHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private ICompanyService companyService;

    @RequestMapping("/label")
    public void label(Model model){
        UserInfo currentUser = getCurrentUser();
        List<BangInn> bangInnAndLabel = this.bangInnService.findBangInnAndLabel(currentUser);
        model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        model.addAttribute(Constants.DATA, bangInnAndLabel);
    }

    /**
     * OMS接口：根据客栈id，查询第三方公司
     *
     * @param model
     * @param innId
     */
    @RequestMapping("/find_companys")
    public void findConpany(Model model, int innId) {
        List<BangInnDto> results = this.bangInnService.findCompanyByInnId(innId);
        if (null != results) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, results);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "系统内部错误！");
        }
    }

    /**
     * 根据公司名称或公司唯一查询公司信息
     *
     * @param model
     * @param company
     */
    @RequestMapping("find_company")
    public void findCompany(Model model, Company company) {
        List<Company> companyList = this.companyService.findCompanyByCompany(company);
        if (null != companyList) {
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, companyList);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "系统内部错误！");
        }
    }

    /**
     * 新增绑定客栈
     *
     * @param model
     * @param bangInnDto
     */
    @RequestMapping("add_bang_inn")
    public void addBangInn(Model model, BangInnDto bangInnDto) {
        if (BangInnDataCheckHelper.checkBangInn(bangInnDto)) {
            bangInnDto.setBangDate(new Date());
            this.bangInnService.addBanginn(bangInnDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "请检查传递的参数！");
        }
    }
}
