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
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/news-center.css'/>">

</head>
<body>
    <div class="manual-order1">
        <div class="manual-order-icon">
            <ul>
                <toms:authorize uri="/personality/info/otherConsumer">
                <a href="<c:url value="/personality/info/otherConsumer"/>">
                <Li ms-click="divDisplayFun">
                    <dl>
                        <dd>
                            <dl class="gongneng-icon"><i class="icon-coffee ON"></i> </dl>
                        </dd>
                        <dd class="other-pay-manage">其它消费管理</dd>
                    </dl>
                </Li>
                </a>
                </toms:authorize>
                <toms:authorize uri="/personality/info/myself_channel_page">
                <a href="<c:url value="/personality/info/myself_channel_page"/>">
                <Li ms-click="divDisplayFun">
                    <dl>
                        <dd>
                            <dl class="gongneng-icon"><i class="icon-picture on"></i> </dl>
                        </dd>
                        <dd class="other-pay-manage">自定义渠道</dd>
                    </dl>
                </Li>
                </a>
                </toms:authorize>
                <toms:authorize uri="/personality/info/pms_channel_name_page">
                <a href="<c:url value="/personality/info/pms_channel_name_page"/>">
                    <Li ms-click="divDisplayFun">
                        <dl>
                            <dd>
                                <dl class="gongneng-icon"><i class="icon-film on"></i> </dl>
                            </dd>
                            <dd class="other-pay-manage">PMS渠道名设置</dd>
                        </dl>
                    </Li>
                </a>
                </toms:authorize>
            </ul>
        </div>
    </div>

</body>
</html>