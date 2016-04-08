<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>个性化功能</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">

</head>
<body>
<div>
  <toms:authorizeConsumer uri="/personality/info/otherConsumer"/>
 </div>
</body>
</html>