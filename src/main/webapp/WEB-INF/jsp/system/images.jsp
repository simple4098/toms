<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/21
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <title>客栈图片</title>
</head>
<div class="main-content">
    <c:if test="${not empty bangInns}">
        <c:forEach items="${bangInns}" var="b">
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        <a href="<c:url value="/system/find_room_images?id=${b.id}"/>">
                                ${b.innName}
                        </a>
                    </h1>
                </div>
                <!-- /.page-header -->
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row-fluid">
                            <ul class="ace-thumbnails">
                                <c:if test="${not empty b.innDto}">
                                    <c:forEach items="${b.innDto.imgList}" var="img">
                                        <li>
                                            <a href="${imgUrl}${img.imgUrl}" target="_blank" title="${img.imgName}"
                                               data-rel="colorbox">
                                                <img style="width:100px;height: 100px;" alt="150x150"
                                                     src="${imgUrl}${img.imgUrl}"/>
                                            </a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${empty b.innDto.imgList}">
                                        <span>无</span>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty b.innDto}">
                                    <span>无</span>
                                </c:if>

                            </ul>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </c:forEach>
    </c:if>
</div>
<!-- /.main-content -->
