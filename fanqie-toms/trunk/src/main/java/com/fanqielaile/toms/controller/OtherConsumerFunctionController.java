package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOtherConsumerInfoService;
import com.fanqielaile.toms.support.tag.AuthorizeTagConsumer;
import com.fanqielaile.toms.support.util.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/25
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/personality")
public class OtherConsumerFunctionController  extends BaseController {
    @Resource
    private IOtherConsumerInfoService otherConsumerInfoService;

    @RequestMapping("/otherConsumer")
    public String orderExport(Model model){
        UserInfo currentUser = getCurrentUser();
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext context = webApplicationContext.getServletContext();
        String contextPath = context.getContextPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorizeTagConsumer authorizeTagConsumer = new AuthorizeTagConsumer();
        boolean b = authorizeTagConsumer.isAllow(contextPath, "/personality/otherConsumer", "GEI", authentication);
        if (b && currentUser!=null){
            List<OtherConsumerInfoDto> otherConsumerList = otherConsumerInfoService.findOtherConsumerInfo(currentUser.getCompanyId());
            model.addAttribute(Constants.DATA,otherConsumerList);
        }else {
            return "/other_consumer/info_error";
        }
        return "/other_consumer/info";
    }

}
