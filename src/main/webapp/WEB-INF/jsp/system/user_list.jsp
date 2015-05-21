<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>账号设置</title>
</head>
<style type="text/css">
    .list-tab {
        border: 1px;
    }
</style>
<body>
<div>
    <h1>账号设置</h1>
</div>
<div>
    <table class="list-tab">
        <thead>
        <tr>
            <td>类型</td>
            <td>登录账号</td>
            <td>联系电话</td>
            <td>姓名</td>
            <td>权限</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty data}">
            <c:forEach items="${data}" var="d">
                <tr>
                    <td>${d.userType.text}</td>
                    <td>${d.loginName}</td>
                    <td>${d.telephone}</td>
                    <td>${d.userName}</td>
                    <td>${d.dataPermission}</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
