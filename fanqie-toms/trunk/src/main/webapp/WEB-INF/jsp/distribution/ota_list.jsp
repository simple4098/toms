<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <c:forEach items="${otaList}" var="ot">
        ${ot.otaInfo} -  ${ot.appKey} -  ${ot.appSecret}-  ${ot.sessionKey}
        <c:if test="${empty ot.otaInfoId}">
            申请开通
        </c:if>
        <c:if test="${not empty ot.otaInfoId}">
            已开通
        </c:if>
    </c:forEach>
</body>
</html>
