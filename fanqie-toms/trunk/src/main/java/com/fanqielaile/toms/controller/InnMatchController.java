package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/innMatch")
public class InnMatchController extends BaseController {
    @Resource
    private IBangInnService bangInnService;
    @Resource
    private InnLabelService innLabelService;
    @Resource
    private IOtaInfoService otaInfoService;

    //匹配列表
    @RequestMapping("/match")
    public String matchList(Model model,BangInnDto bangInnDto,@RequestParam(defaultValue = "1", required = false) int page){
        UserInfo userInfo = getCurrentUser();
        List<OtaInfoRefDto> infoRefDtoList = otaInfoService.findOtaInfoListByCompanyId(userInfo.getCompanyId());
        BangInnDto innDto = BangInnDto.userInfoToBangInnDto(userInfo,bangInnDto,infoRefDtoList);
        List<InnLabel> innLabels = innLabelService.findLabelsByCompanyId(innDto.getCompanyId());
        List<BangInn> bangInns = bangInnService.findFcBangInn(bangInnDto, new PageBounds(page, 1));
        model.addAttribute("labels", innLabels);
        model.addAttribute("list",bangInns);
        model.addAttribute("bangInnDto",bangInnDto);
        Paginator paginator = ((PageList) bangInns).getPaginator();
        model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
        model.addAttribute("infoList", infoRefDtoList);
        return "/match/inn_match_list";
    }
    //渠道
    @RequestMapping("/{otaInfoId}")
    public String matchOtaList(BangInnDto bangInnDto,@PathVariable String otaInfoId){
        UserInfo userInfo = getCurrentUser();

        return "/match/inn_match_list";
    }
}
