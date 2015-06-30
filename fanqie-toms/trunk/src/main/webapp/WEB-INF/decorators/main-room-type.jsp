<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><sitemesh:write property="title"/></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/ace.min.css">
    <sitemesh:write property="head"/>
</head>
<body>
    <jsp:include page="/WEB-INF/decorators/common/header.jsp"/>
    <%--<div class="main-container" id="main-container">--%>
        <script type="text/javascript">
            try {
                ace.settings.check('main-container', 'fixed')
            } catch (e) {
            }
        </script>
      <%--  <div class="main-container-inner">--%>
            <a class="menu-toggler" id="menu-toggler" href="#">
                <span class="menu-text"></span>
            </a>

            <sitemesh:write property="side"/>
            <jsp:include page="/WEB-INF/decorators/common/side.jsp"/>

            <div class="ftfl-content">
                <sitemesh:write property="body"/>
            </div>
        <%--</div>--%>
    <script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>/assets/js/ace.min.js"></script>
</body>


</html>
