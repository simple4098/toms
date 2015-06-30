<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/29
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>修改绑定客栈信息</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            修改绑定客栈
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <form class="form-horizontal update-inn" action="<c:url value="/inn_manage/update_inn"/>" method="post"
                  role="form">
                <input type="hidden" name="id" value="${data.id}"/>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 客栈名称： </label>

                    <div class="col-sm-9">
                        <label class=" control-label no-padding-right">${data.innName}</label>

                    </div>
                </div>

                <div class="space-4"></div>

                <%--<div class="form-group">--%>
                <%--<label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 番茄账户名： </label>--%>

                <%--<div class="col-sm-9">--%>
                <%--<label class=" control-label no-padding-left ">${data.innCode}</label>--%>
                <%--</div>--%>
                <%--</div>--%>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 联系号码： </label>

                    <div class="col-sm-9">
                        <label class=" control-label no-padding-left ">${data.mobile}</label>
                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客栈分类：</label>

                    <div class="col-sm-9">
                        <select name="innLabelId">
                            <c:if test="${not empty labels}">
                                <c:forEach var="l" items="${labels}">
                                    <option value="${l.id}"
                                            <c:if test="${data.innLabelId == l.id}">selected</c:if> >${l.labelName}</option>
                                </c:forEach>
                            </c:if>
                            <option value="">请选择</option>
                        </select>

                        <div class="space-2"></div>

                        <div class="help-block" id="input-size-slider-1"></div>
                    </div>
                </div>
                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-4">加盟编号：</label>

                    <div class="col-sm-9">
                        <input type="text" class="col-xs-3 col-sm-3 ace code" name="code"
                               data-url="<c:url value="/inn_manage/check_code.json?id=${data.id}"/>"
                               value="${data.code}"/>
                   <span class="help-code col-xs-12 col-sm-7">

											</span>

                        <div class="space-2"></div>

                        <div class="help-block" id="input-size-slider-2"></div>
                    </div>
                </div>
                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-4">所属管理员：</label>

                    <div class="col-sm-9">
                        <select name="userId">
                            <c:if test="${not empty userInfos}">
                                <c:forEach var="u" items="${userInfos}">
                                    <option value="${u.id}"
                                            <c:if test="${data.userId == u.id}">selected</c:if> >${u.userName}</option>
                                </c:forEach>
                            </c:if>
                            <option value="">请选择</option>
                        </select>

                        <div class="space-2"></div>

                        <div class="help-block" id="input-size-slider"></div>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info bang-inn-update" type="submit">
                            <i class="icon-ok bigger-110"></i>
                            确认
                        </button>

                        <%--<button class="btn" type="reset">--%>
                        <%--<i class="icon-undo bigger-110"></i>--%>
                        <%--清空--%>
                        <%--</button>--%>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>

