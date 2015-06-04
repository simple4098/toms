package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.CompanyAjaxDto;
import com.fanqielaile.toms.helper.BangInnDataCheckHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private static Logger logger = LoggerFactory.getLogger(AjaxLabelInnController.class);
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
     * 新增绑定客栈
     *
     * @param
     * @param bangInnDto
     */
    @RequestMapping("add_bang_inn")
    @ResponseBody
    public Object addBangInn(BangInnDto bangInnDto, Model model) {
        logger.info("传入参数", bangInnDto);
        if (BangInnDataCheckHelper.checkBangInn(bangInnDto)) {
            //添加之前检查公司是否存在
            Company checkCompany = this.companyService.findCompanyByCompanyCode(bangInnDto.getCompanyCode());
            if (null != checkCompany) {
                //检查是否重复绑定
                BangInn bangInn = this.bangInnService.findBangInnByCompanyIdAndInnId(checkCompany.getId(), bangInnDto.getInnId());
                if (null == bangInn) {
                    JsonModel jsonModel = new JsonModel();
                    bangInnDto.setBangDate(new Date());
                    bangInnDto.setCompanyId(checkCompany.getId());
                    this.bangInnService.addBanginn(bangInnDto);
                    //添加成功后，绑定公司信息返回
                    jsonModel.setSuccess(true);
                    jsonModel.addAttribute(Constants.DATA, CompanyAjaxDto.toAjaxBangInnDto(checkCompany));
                    return jsonModel;
                } else {
                    return new JsonModel(false, "该客栈已绑定过");
                }
            } else {
                return new JsonModel(false, "请检查公司唯一码是否正确！");
            }
        } else {
            return new JsonModel(false, "请检查传递的参数!");
        }
    }
}
