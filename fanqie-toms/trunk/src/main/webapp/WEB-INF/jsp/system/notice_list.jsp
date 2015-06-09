<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/26
  Time: 16:05
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
    <title>通知模板</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            通知模板
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <c:if test="${not empty data}">
            <c:forEach items="${data}" var="d">
                <div class="col-xs-12 col-sm-3 widget-container-span">
                    <!-- PAGE CONTENT BEGINS -->

                    <div class="widget-box">
                        <div class="widget-header">
                            <h5>${d.noticeTitle}</h5>

                            <div class="widget-toolbar">
                                <a href="<c:url value="/system/update_notice_page?id=${d.id}"/>">
                                    <i class="icon-cog"></i>
                                </a>
                                <a class="delete-notice" data-toggle="modal" data-target="#myModal"
                                   data-url="<c:url value="/system/delete_notice.json?id=${d.id}"/>"
                                   data-whatever="${d.id}">
                                    <i class="icon-remove"></i>
                                </a>
                                <a class="add-notice" data-toggle="modal" data-target="#addNotice">
                                    <i class="icon-plus"></i>
                                </a>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <input type="hidden" name="id" value="${d.id}" class="id"/>

                                <p class="alert alert-info">
                                    <textarea readonly rows="5" cols="40"
                                              style="width: 90%;height: 10%;">${d.noticeContent}</textarea>

                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<%--删除弹出层--%>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                是否删除？
                <input name="noticeId" id="notice-id" class="notice-id" type="hidden"/>
                <input name="dataUrl" class="data-url" type="hidden"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">确认</button>
            </div>
        </div>
    </div>
</div>
<%--新增模板弹出层--%>
<div class="modal fade" id="addNotice" tabindex="-1" role="dialog" aria-labelledby="addNoticeLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addNoticeLabel">新增模板</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal notice-form" action="<c:url value="/system/create_notice"/>" method="post"
                      role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 模板标题 </label>

                        <div class="col-sm-9">
                            <input type="text" maxlength="10" id="form-field-1" name="noticeTitle"
                                   value="" placeholder="标题" class="col-xs-10 col-sm-8 notice-title"/>
                            <span class="help-notice-title col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 模板内容 </label>

                        <div class="col-sm-9">
                            <textarea maxlength="70" type="text" cols="10" rows="4" name="noticeContent"
                                      id="form-field-2" placeholder="内容"
                                      class="col-xs-10 col-sm-8 ace notice-content"></textarea>
                        <span class="help-notice-content col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info btn-sub" type="submit">
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
            <%--<div class="modal-footer">--%>
            <%--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>--%>
            <%--<button type="button" class="btn btn-primary btn-submit">确认</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>
