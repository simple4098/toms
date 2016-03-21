<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>填写渠道信息</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/userSet.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/innRelation.css'/>">
  <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
  <script src="<c:url value='/assets/layer/layer.js'/>"></script>
</head>
<body>
<div class="container">
  <ul class="nav-bar clearfix" style=" height: 63px;">
    <c:forEach items="${infoList}" var="o">
      <li <c:if test="${o.otaType.name()=='CREDIT'}">class="background"</c:if>><a  href="<c:url value="/innMatch/match?otaInfoId=${o.otaInfoId}"/>">${o.otaInfo}</a></li>
    </c:forEach>
  </ul>
  <div class="widget-box widget-custom">
    <div class="widget-header widget-header-flat">
      <h4 class="smaller">去啊信用住</h4>
    </div>
     <c:import url="tb-common.jsp"></c:import>
  </div>

</div>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script src="<c:url value='/js/match/vetted.js'/>"></script>
</body>
</html>