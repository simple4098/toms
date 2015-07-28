package com.fanqielaile.toms.support.filter;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * DESC : 及时推送过滤器
 * @author : 番茄木-ZLin
 * @data : 2015/7/27
 * @version: v1.0.0
 */
public class RouterRestFilter implements Filter {
    private static  final Logger log = LoggerFactory.getLogger(RouterRestFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String xml = HttpClientUtil.convertStreamToString(request.getInputStream());
        log.info("===========start==============");
        log.info(xml);
        //String xml ="<PushRoomType><list><RoomType><AccountId>123</AccountId><RoomTypeId>1018376</RoomTypeId><RoomTypeName>房型1</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2015-07-27</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>1</RoomNum></RoomDetail><RoomDetail><RoomDate>2015-07-28</RoomDate><RoomPrice>2.0</RoomPrice><PriRoomPrice>1.0</PriRoomPrice><RoomNum>2</RoomNum></RoomDetail></RoomDetails></RoomType></list></PushRoomType>";
        if (!StringUtils.isEmpty(xml)){
            String rootElementName = "";
            try {
                rootElementName = XmlDeal.getRootElementString(xml);
            } catch (Exception e) {
                log.error("RouterRestFilter 解析xml失败",e);
            }
            StringBuilder uri =new StringBuilder("/fanqieService/").append(rootElementName);
            uri.append("?pushXml=").append(xml);
            request.getRequestDispatcher(uri.toString()).forward(request, response);
        }else {
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
