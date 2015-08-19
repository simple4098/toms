<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<link href="<%=basePath%>/assets/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">


<div class="modal-content">
    <div class="table-header" style="background-color:gainsboro;text-align: center">
             ${inn.innName}
    </div>
    <div class="modal-body">
        <form class="form-horizontal" action="<c:url value="/user/update_user"/>" method="post" role="form">
            <input name="innId" value="${inn.innId}">
            <c:forEach items="${orderConfigList}" var="c">
                ${c.otaInfo} <c:if test="${c.status==1}"><input name="status" type="radio" value="1" checked/></c:if>
                <c:if test="${c.status==0}"> <input name="status" type="radio" value="0" checked /></c:if><br>
            </c:forEach>
            <div class="space-4"></div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info btn-sub"  type="button" data-dismiss="modal" id="userPlusBtn">
                        <i class="icon-ok bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

