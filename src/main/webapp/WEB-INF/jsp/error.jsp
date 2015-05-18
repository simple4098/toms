<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
  <title>错误页面</title>
</head>
<body>
<div>
  <h2>操作失败</h2>
  <c:if test="${not empty msg}">
    <h1>失败原因：${msg}</h1>
    <a href="<%=basePath%>login">返回登录页面</a>
  </c:if>
  <c:if test="${empty msg}">Sorry, you have no access！</c:if>
</div>
</body>
</html>
