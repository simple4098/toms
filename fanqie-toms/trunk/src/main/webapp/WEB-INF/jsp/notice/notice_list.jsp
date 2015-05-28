<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/27
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>消息通知</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            消息通知
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12 col-sm-4 widget-container-span">
            <!-- PAGE CONTENT BEGINS -->

            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>通知模板：</h5>
                    <select name="notice" class="notice" data-url="<c:url value="/system/find_notice.json?id="/>">
                        <option value="" selected>请选择</option>
                        <c:if test="${not empty data}">
                            <c:forEach items="${data}" var="d">
                                <option value="${d.id}">${d.noticeTitle}</option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <div class="widget-toolbar">
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main">
                        <p class="alert alert-info">
                            <textarea class="notice-content" rows="10" cols="65" style="resize: none"></textarea>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-4 widget-container-span">
            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>选择接收的客栈</h5>

                    <div class="widget-toolbar">

                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main">
                        <p class="alert alert-info">
                            <span class="label label-lg label-purple arrowed">
                                    <input type="checkbox" class="all-inn" style="padding-top: 10px"/>全选所有客栈
                            </span>
                            <br/><br/>
                            <c:if test="${not empty bangInnList}">
                                <c:forEach items="${bangInnList}" var="bi">
                                    <span class="label label-lg label-purple arrowed">
                                    <input type="checkbox" class="${bi.innLabelId} inn-label"
                                           style="padding-top: 10px"/>${bi.innLabelName}
                                    </span>
                                    <br/>
                                    <c:if test="${not empty bi.bangInnList}">
                                        <c:forEach items="${bi.bangInnList}" var="inn">
                                            &nbsp;&nbsp;
                                            <input type="checkbox" name="innId" class="${bi.innLabelId}-child inn"
                                                   value="${inn.id}"/>${inn.innName}<br/>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-sm widget-container-span">
            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>请选择发送的方式：</h5>
          <span style="padding-left: 60px;">
          <input type="checkbox"/>短信发送
            </span>
          <span style="padding-left: 80px">
          <input type="checkbox" style="padding-left: 20%"/>系统弹窗
            </span>
          <span style="padding-left: 100px;">
          <button class="btn btn-success">确认发送</button>
          </span>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>

