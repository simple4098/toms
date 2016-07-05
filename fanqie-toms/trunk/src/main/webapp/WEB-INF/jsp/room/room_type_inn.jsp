<%--
  Created by IntelliJ IDEA.
  User: jame
  Date: 2016/7/5
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>客栈列表</title>
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>/js/my-system.js"/>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/pages.css"/>
</head>
<body>

<div class="table-responsive">
    <table id="sample-table-2" class="table table-striped table-bordered table-hover">
        <thead style="font-size: 14px;">
        <tr>
            <th width="100">客栈名称</th>
            <th width="100">操作</th>
        </tr>
        </thead>
        <tbody class="table-data" style="font-size: 14px;">
        <c:if test="${not empty data}">
            <c:forEach items="${data}" var="d">
                <tr>
                    <td>${d.innName}</td>
                    <td>
                        <a href="#" onclick="getDetail('${d.id}');">查看房态</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>

<c:set value="${pagination}" var="page"/>

<c:if test="${page.pageCount>1}">
    <toms:page linkUrl="" pagerDecorator="${pageDecorator}"/>
</c:if>
<c:if test="${empty data}">
    <div class="alert alert-danger center">
        没有数据,请筛选条件
    </div>
</c:if>


<script>

    //分页方法
    function page(page) {
        $("#pageId").attr("value", page);
        $("#pageNo").attr("value", page);
        $('#myButton').click();
//        $('.form-page').submit();
    }

</script>



</body>
</html>
