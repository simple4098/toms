<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/14
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" action="<c:url value="/system/create_permission"/>">
    url:
    <input type="text" name="url" value="" placeholder="权限的url"/>
    权限描述:
    <input placeholder="权限名称" value="" name="permissionName"/>

    <div>
        <input type="submit" value="submit"/>
    </div>
</form>
</body>
</html>
