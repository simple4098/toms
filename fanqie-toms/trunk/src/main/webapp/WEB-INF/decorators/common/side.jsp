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
            <!-- #sidebar-shortcuts -->

            <ul class="nav nav-list">
                <li <c:if test="${fn:contains(url, '/system/login_success')}">class="active"</c:if>>
                    <a href="<c:url value="/system/login_success"/>">
                        <i class="icon-dashboard"></i>
                        <span class="menu-text"> 首页 </span>
                    </a>
                </li>

                <li>
                    <a href="typography.html">
                        <i class="icon-text-width"></i>
                        <span class="menu-text"> 房态数量 </span>
                    </a>
                </li>

                <li <c:if test="${fn:contains(url,'/operate/')}">class="open active"</c:if>>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-desktop"></i>
                        <span class="menu-text"> 运营报表 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="<c:url value='/operate/qs'/>">
                                <i class="icon-double-angle-right"></i>
                                运营趋势
                            </a>
                        </li>

                        <li>
                            <a href="<c:url value='/operate/kf'/>">
                                <i class="icon-double-angle-right"></i>
                                客户资料分析
                            </a>
                        </li>

                        <li>
                            <a href="<c:url value='/operate/order'/>">
                                <i class="icon-double-angle-right"></i>
                                订单来源分析
                            </a>
                        </li>

                    </ul>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-list"></i>
                        <span class="menu-text"> 客栈管理 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="tables.html">
                                <i class="icon-double-angle-right"></i>
                                客栈列表
                            </a>
                        </li>

                        <li>
                            <a href="jqgrid.html">
                                <i class="icon-double-angle-right"></i>
                                客栈活跃表
                            </a>
                        </li>
                    </ul>
                </li>

                <li
                        <c:if test="${fn:contains(url, '/notice/find_notices')}">class="active"</c:if> >
                    <a href="<c:url value="/notice/find_notices"/>" class="dropdown-toggle">
                        <i class="icon-edit"></i>
                        <span class="menu-text"> 消息通知 </span>
                    </a>
                </li>

                <li
                        <c:if test="${fn:contains(url, '/user/find_users')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/system/find_notices')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/system/find_labels')}">class="open active"</c:if>
                        <c:if test="${fn:contains(url, '/user/find_user')}"> class="open active"</c:if>
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
                        <li
                                <c:if test="${fn:contains(url, '/system/find_notices')}">class="active" </c:if> >
                            <a href="<c:url value="/system/find_notices"/>">
                                <i class="icon-double-angle-right"></i>
                                通知模板
                            </a>
                        </li>

                        <li
                                <c:if test="${fn:contains(url, '/system/find_labels')}">class="active" </c:if> >
                            <a href="<c:url value="/system/find_labels"/>">
                                <i class="icon-double-angle-right"></i>
                                客栈标签
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.nav-list -->

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
                   data-icon2="icon-double-angle-right"></i>
            </div>

            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) {
                }
            </script>
        </div>

