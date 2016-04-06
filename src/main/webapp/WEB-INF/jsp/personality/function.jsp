<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>开通渠道</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/normalize.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/bootstrap.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/font-awesome.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/openChannerl.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/news-center.css'/>">

</head>
<body>
<div class="ms-controller" ms-controller="otherPayManage">
  <toms:authorizeConsumer uri="/personality/otherConsumer"/>
 </div>
  <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
  <script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>
  <script src="<c:url value='/assets/js/avalon.js'/>"></script>
  <script src="<c:url value='/js/other-pay-manage.js'/>"></script>
</body>
</html>