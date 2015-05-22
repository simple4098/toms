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
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-skins.min.css"/>

    <script src="<%=basePath%>/assets/js/ace-extra.min.js"></script>
    <sitemesh:write property="head"/>

</head>
<body>
    <jsp:include page="/WEB-INF/decorators/common/header.jsp"/>

    <div class="main-container" id="main-container">
        <script type="text/javascript">
            try {
                ace.settings.check('main-container', 'fixed')
            } catch (e) {
            }
        </script>
        <div class="main-container-inner">
            <a class="menu-toggler" id="menu-toggler" href="#">
                <span class="menu-text"></span>
            </a>

            <sitemesh:write property="side"/>
    <jsp:include page="/WEB-INF/decorators/common/side.jsp"/>

            <div class="main-content">
                <div class="breadcrumbs" id="breadcrumbs">
                    <script type="text/javascript">
                        try {
                            ace.settings.check('breadcrumbs', 'fixed')
                        } catch (e) {
                        }
                    </script>
                </div>
                <sitemesh:write property="body"/>
            </div>
            <!-- /.main-content -->
        </div>

    </div>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>


    <script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>/assets/js/typeahead-bs2.min.js"></script>

    <!-- page specific plugin scripts -->

    <!-- ace scripts -->

    <script src="<%=basePath%>/assets/js/ace-elements.min.js"></script>
    <script src="<%=basePath%>/assets/js/ace.min.js"></script>
</body>


</html>
