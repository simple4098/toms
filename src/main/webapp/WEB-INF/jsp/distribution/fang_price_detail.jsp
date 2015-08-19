
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <title>客栈接单设置</title>
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/pages.css"/>
</head>
<div class="page-content">

    <div class="row">
        <div class="col-xs-12">
            <h3 class="header smaller lighter blue">客栈房型价格设置</h3>
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                                <tr>
                                    <th>房型名称</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="table-data" style="font-size: 14px;">
                            <c:if test="${not empty list}">
                                <c:forEach items="${list}" var="d">
                                    <tr>
                                        <td>${d.roomTypeName}</td>
                                        <td>价格设置</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty list}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->


