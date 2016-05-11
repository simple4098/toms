<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/20
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<c:set var="url"
       value='${requestScope["org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"]}'
       scope="request"/>
        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) {
                }
            </script>
            <input type="hidden" class="room-type-url" id="newsCenterUrl" <toms:authorize uri="/message/*"> data-url="<c:url value="/message/query_change_price_list.json"/>" </toms:authorize>/>
            <input type="hidden" class="room-type-url" id="queryNotReadCountUrl" <toms:authorize uri="/message/*">  data-url="<c:url value="/message/query_not_read_count.json"/>"</toms:authorize>/>
            <input type="hidden" class="room-type-url" id="queryChangeMessageUrl"  <toms:authorize uri="/message/*">  data-url="<c:url value="/message/query_change_price_message.json"/>"</toms:authorize>/>
            <input type="hidden" class="room-type-url" id="changeMessageStatus"  <toms:authorize uri="/message/*">  data-url="<c:url value="/message/change_message_status.json"/>"</toms:authorize>/>
        <%--/message/query_change_price_message.json--%>
            <!-- #sidebar-shortcuts -->
            <ul class="nav nav-list ul-parent">
                <li <c:if test="${fn:contains(url, '/system/login_success')}">class="active"</c:if>>
                    <a href="<c:url value="/system/login_success"/>">
                        <i class="icon-dashboard"></i>
                        <span class="menu-text"> 首页 </span>
                    </a>
                </li>
                <toms:authorize uri="/oms/obtRoomType">
                <li <c:if test="${fn:contains(url, '/oms/obtRoomType')}">class="active"</c:if>>
                    <a href="<c:url value='/oms/obtRoomType'/>">
                        <i class="icon-text-width"></i>
                        <span class="menu-text"> 房态数量 </span>
                    </a>
                </li>
                </toms:authorize>
                <li data-name="li-parent" <c:if test="${fn:contains(url,'/operate/')}">class="open active"</c:if>>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-desktop"></i>
                        <span class="menu-text"> 运营报表 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <toms:authorize uri="/operate/qs">
                        <li <c:if test="${fn:contains(url,'/operate/qs')}">class="active"</c:if>>
                            <a href="<c:url value='/operate/qs'/>">
                                <i class="icon-double-angle-right"></i>
                                运营趋势
                            </a>
                        </li>
                        </toms:authorize>
                        <toms:authorize uri="/operate/kf">
                        <li <c:if test="${fn:contains('/operate/kf',url)}">class="active"</c:if>>
                            <a href="<c:url value='/operate/kf?page=1'/>">
                                <i class="icon-double-angle-right"></i>
                                客栈客户分析
                            </a>
                        </li>
                        </toms:authorize>
                        <toms:authorize uri="/operate_test/customer_analysis">
                        <li <c:if test="${fn:contains('/operate/customer_analysis',url)}">class="active"</c:if>> 
                            <a href="<c:url value='/operate/customer_analysis'/>">
                                <i class="icon-double-angle-right"></i>
                                客户资料分析
                            </a>
                        </li>
                        </toms:authorize> 

                        <toms:authorize uri="/operate/order">
                        <li <c:if test="${fn:contains(url,'/operate/order')}">class="active"</c:if>>
                            <a href="<c:url value='/operate/order'/>">
                                <i class="icon-double-angle-right"></i>
                                订单来源分析
                            </a>
                        </li>
                        </toms:authorize>

                    </ul>
                </li>

                <li data-name="li-parent-1"
                        <c:if test="${fn:contains(url, '/inn_manage/find_inns')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/inn_manage/to_update_inn')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/inn_manage/activeInn')}">class="open active"</c:if>
                        >
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-list"></i>
                        <span class="menu-text"> 客栈管理 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <toms:authorize uri="/inn_manage/find_inns">
                        <li <c:if test="${fn:contains(url, '/inn_manage/find_inns')}">class="active" </c:if> >
                            <a href="<c:url value="/inn_manage/find_inns"/>">
                                <i class="icon-double-angle-right"></i>
                                客栈列表
                            </a>
                        </li>
                        </toms:authorize>
                        <toms:authorize uri="/inn_manage/activeInn">
                        <li <c:if test="${fn:contains(url, '/inn_manage/activeInn')}">class="active" </c:if> >
                            <a href="<c:url value="/inn_manage/activeInn?page=1"/> ">
                                <i class="icon-double-angle-right"></i>
                                客栈活跃表
                            </a>
                        </li>
                        </toms:authorize>
                    </ul>
                </li>
                <li data-name="li-parent-3"
                    <c:if test="${fn:contains(url, '/distribution/otaList') || fn:contains(url, '/order/find_orders') || fn:contains(url, '/order/find_non_orders') ||
                                    fn:contains(url, '/order/find_pay_back_orders')
                 || fn:contains(url, '/distribution/orderConfig') || fn:contains(url, '/distribution/fangPrice') ||fn:contains(url, '/distribution/orderConfig')
                 || fn:contains(url, '/innMatch/match')|| fn:contains(url, '/distribution/addFangPrice') || fn:contains(url, '/exceptionOrder/find_exceptionOrders')}">class="open active" </c:if>  >
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-list-alt"></i>
                        <span class="menu-text"> 分销管理 </span>
                        <b class="arrow icon-angle-down"></b>
                    </a>
                    <ul class="submenu">
                        <toms:authorize uri="/exceptionOrder/find_exceptionOrders">
                            <li
                                    <c:if test="${fn:contains(url, '/exceptionOrder/find_exceptionOrders')}">class="active" </c:if> >
                                <a href="<c:url value="/exceptionOrder/find_exceptionOrders"/>">
                                    <i class="icon-double-angle-right"></i>
                                    异常订单管理
                                </a>
                            </li>
                        </toms:authorize>
                        <toms:authorize uri="/innMatch/">
                            <li
                                    <c:if test="${fn:contains(url, '/innMatch/')}">class="active"</c:if> >
                                <a href="<c:url value="/innMatch/match"/>">
                                    <i class="icon-double-angle-right"></i>
                                    客栈匹配
                                </a>
                            </li>
                        </toms:authorize>
                        <toms:authorize uri="/order/find_orders">
                            <li
                                    <c:if test="${fn:contains(url, '/order/find_orders') || fn:contains(url, '/order/find_non_orders') ||
                                    fn:contains(url, '/order/find_pay_back_orders')
                                    }">class="active" </c:if> >
                                <a href="<c:url value="/order/find_orders"/>">
                                    <i class="icon-double-angle-right"></i>
                                    订单管理
                                </a>
                            </li>
                        </toms:authorize>
                        <toms:authorize uri="/distribution/fangPrice">
                            <li
                                    <c:if test="${fn:contains(url, '/distribution/fangPrice')||fn:contains(url, '/distribution/addFangPrice')}">class="active"</c:if> >
                                <a href="<c:url value="/distribution/fangPrice"/>">
                                    <i class="icon-double-angle-right"></i>
                                    房价管理
                                </a>
                            </li>
                        </toms:authorize>
                        <toms:authorize uri="/distribution/orderConfig">
                            <li <c:if test="${fn:contains(url, '/distribution/orderConfig')}">class="active" </c:if> >
                                <a href="<c:url value="/distribution/orderConfig"/>">
                                    <i class="icon-double-angle-right"></i>
                                    接单设置
                                </a>
                            </li>
                        </toms:authorize>
                    </ul>
                </li>
                <toms:authorize uri="/notice/find_notices">
                <li
                        <c:if test="${fn:contains(url, '/notice/find_notices')}">class="active"</c:if> >
                    <a href="<c:url value="/notice/find_notices"/>" class="dropdown-toggle">
                        <i class="icon-edit"></i>
                        <span class="menu-text"> 消息通知 </span>
                    </a>
                </li>
                </toms:authorize>
                <li data-name="li-parent-2"
                        <c:if test="${fn:contains(url, '/user/find_users')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/system/find_notices') || fn:contains(url, '/system/find_room_images')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/system/find_labels') || fn:contains(url, '/system/images') || fn:contains(url, '/system/find_company')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/user/find_user') || fn:contains(url, '/system/update_notice_page') || fn:contains(url, '/system/find_companys')}"> class="open active"</c:if>
                        >
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-tag"></i>
                        <span class="menu-text"> 设置 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <toms:authorize uri="/user/find_users">
                        <li <c:if test="${fn:contains(url, '/user/find_users')}">class="active"</c:if>>
                            <a href="<c:url value="/user/find_users"/>">
                                <i class="icon-double-angle-right"></i>
                                账号设置
                            </a>
                        </li>
                        </toms:authorize>
                        <toms:authorize uri="/system/find_notices">
                        <li
                                <c:if test="${fn:contains(url, '/system/find_notices')}">class="active" </c:if> >
                            <a href="<c:url value="/system/find_notices"/>">
                                <i class="icon-double-angle-right"></i>
                                通知模板
                            </a>
                        </li>
                        </toms:authorize>

                        <toms:authorize uri="/system/find_labels">
                        <li
                                <c:if test="${fn:contains(url, '/system/find_labels')}">class="active" </c:if> >
                            <a href="<c:url value="/system/find_labels"/>">
                                <i class="icon-double-angle-right"></i>
                                客栈分类
                            </a>
                        </li>
                        </toms:authorize>
                        <toms:authorize uri="/system/images">
                            <li
                                    <c:if test="${fn:contains(url, '/system/images')}">class="active" </c:if> >
                                <a href="<c:url value="/system/images"/>">
                                    <i class="icon-double-angle-right"></i>
                                    图片管理
                                </a>
                            </li>
                        </toms:authorize>
                        <toms:authorize uri="/system/find_companys">
                            <li
                                    <c:if test="${fn:contains(url, '/system/find_companys')}">class="active" </c:if>
                                    <c:if test="${fn:contains(url, '/system/find_company')}">class="active" </c:if>
                                    >
                                <a href="<c:url value="/system/find_companys"/>">
                                    <i class="icon-double-angle-right"></i>
                                    公司管理
                                </a>
                            </li>
                        </toms:authorize>
                    </ul>
                </li>
                <toms:authorize uri="/personality/function">
                    <li <c:if test="${fn:contains(url, '/personality/')}">class="active"</c:if>>
                        <a href="<c:url value='/personality/function'/>">
                            <i class="icon-text-width"></i>
                            <span class="menu-text"> 个性功能 </span>
                        </a>
                    </li>
                </toms:authorize>
            </ul>
            <toms:authorize uri="/message/*">
                <div class="news-center">
                    <a class="dropdown-toggle" href="#" id="showNewsList">
                        <i class="icon-envelope">
                            <span class="badge badge-success" id="newsAccount">0</span>
                        </i>

                    </a>
                </div>
            </toms:authorize>
            <!-- /.nav-list -->

            <%--<div class="sidebar-collapse" id="sidebar-collapse">--%>
            <%--<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"--%>
            <%--data-icon2="icon-double-angle-right"></i>--%>
            <%--</div>--%>
            <audio src="<c:url value='/voice/mp.mp3'/>" id="MP">
                Your browser does not support the audio element.
            </audio>
            <div class="news-center-dialog" id="newsCenter">
                <div class="top">
                    <h3>消息<a class="fr pack-up" id="packUp">收起</a></h3>
                </div>
                <div class="center">
                    <p class="click-read-new-message"><a>有新的改价消息，点击查看。</a></p>
                    <ul id="newsList">
                        <%--<li>
                            <h4><i class="no-read"></i>XX客栈改价提醒<span class="fr">2016-03-10 15:30</span></h4>
                            <p>
                                大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了大床房、标准间、豪华房改价了！（时间范围：2016.04.01~2016.05.30）
                            </p>
                        </li>
                        <li>
                            <h4><i class="no-read"></i>XX客栈改价提醒<span class="fr">2016-03-10 15:30</span></h4>
                            <p>
                                大床房、标准间、豪华房改价了！（时间范围：2016.04.01~2016.05.30）
                            </p>
                        </li>--%>
                    </ul>
                </div>
                <div class="bottom">
                    <div id="pagination">
                        <ul class="pagination">
                            <li id="Previous">
                                <a href="#" aria-label="Previous">
                                    &laquo;
                                </a>
                            </li>

                            <li id="Next">
                                <a href="#" aria-label="Next">
                                    &raquo;
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) {
                }
            </script>
        </div>
<script>
    if ($('li[data-name="li-parent"]').find('ul').find('li').length == 0) {
        $('li[data-name="li-parent"]').remove();
    }
    if ($('li[data-name="li-parent-1"]').find('ul').find('li').length == 0) {
        $('li[data-name="li-parent-1"]').remove();
    }
    if ($('li[data-name="li-parent-2"]').find('ul').find('li').length == 0) {
        $('li[data-name="li-parent-2"]').remove();
    }
    if ($('li[data-name="li-parent-3"]').find('ul').find('li').length == 0) {
        $('li[data-name="li-parent-3"]').remove();
    }
</script>
<script src="<c:url value='/js/news-center.js'/>"></script>

<%--新增权限--%>

