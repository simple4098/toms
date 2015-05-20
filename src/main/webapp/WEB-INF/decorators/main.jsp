<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/20
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><sitemesh:write property="title"/></title>
    <link href="<%=basePath%>/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
    <script src="<%=basePath%>/assets/js/ace-extra.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-skins.min.css"/>

    <sitemesh:write property="head"/>

</head>
<body>
<div>
    <jsp:include page="/WEB-INF/decorators/common/header.jsp"/>
    <sitemesh:write property="side"/>
    <jsp:include page="/WEB-INF/decorators/common/side.jsp"/>
    <div class="main">
        <sitemesh:write property="body"/>
    </div>
</div>
</body>


</html>
