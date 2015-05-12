<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/12
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>
  <h2>操作失败</h2>

  <div class="content">
    <dl class="lp_tips clearfix disable">
      <dt></dt>
      <dd>
        <h3 style="border-bottom:none;height:auto">${msg}</h3>
        <c:if test="${empty msg}"><h2>对不起你没有权限访问！</h2></c:if>
        <p><a href="javascript:history.go(-1);">返回上一步</a><span></span></p>
      </dd>
    </dl>
  </div>
</div>
</body>
</html>
