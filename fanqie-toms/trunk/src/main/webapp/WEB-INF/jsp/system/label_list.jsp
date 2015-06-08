<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/26
  Time: 18:17
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
    <title>客栈分类</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            客栈分类
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <c:if test="${not empty data}">
            <c:forEach items="${data}" var="d">
                <div class="col-xs-12 col-sm-2 widget-container-span">
                    <!-- PAGE CONTENT BEGINS -->

                    <div class="widget-box">
                        <div class="widget-header">
                            <div class="widget-toolbar">
                                <a class="update-label" data-toggle="modal" data-target="#updateLabel"
                                   data-url="<c:url value="/system/find_label.json?id=${d.id}"/>"
                                   data-whatever="${d.id}">
                                    <i class="icon-cog"></i>
                                </a>
                                <a class="delete-label" data-toggle="modal" data-target="#myModal"
                                   data-url="<c:url value="/system/delete_label.json?id=${d.id}"/>"
                                   data-whatever="${d.id}">
                                    <i class="icon-remove"></i>
                                </a>
                                <a class="add-label" data-toggle="modal" data-target="#addLabel">
                                    <i class="icon-plus"></i>
                                </a>
                                    <%--<a class="share-label" data-toggle="modal" data-target="#shareLabel">--%>
                                    <%--<i class="icon-share"></i>--%>
                                    <%--</a>--%>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <input type="hidden" name="id" value="${d.id}" class="id"/>

                                <p class="alert alert-info">
                                        ${d.labelName}
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
                <input name="labelId" id="label-id" class="label-id" type="hidden"/>
                <input type="hidden" class="data-url"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">确认</button>
            </div>
        </div>
    </div>
</div>
<%--新增分类弹出层--%>
<div class="modal fade " id="addLabel" tabindex="-1" role="dialog" aria-labelledby="addLabelLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addLabelLabel">新增分类</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal new-label" action="<c:url value="/system/create_inn_label"/>" method="post"
                      role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 分类名称 </label>

                        <div class="col-sm-9">
                            <input type="text" maxlength="10" id="form-field-1" name="labelName"
                                   value="" placeholder="名称" class="col-xs-10 col-sm-5 label-name"/>
                            <span class="help-label-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info btn-sub btn-new-label" type="submit">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                            &nbsp; &nbsp; &nbsp;

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
<%--编辑分类弹出层--%>
<div class="modal fade" id="updateLabel" tabindex="-1" role="dialog" aria-labelledby="updateLabelLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="updateLabelLabel">编辑分类</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal update-label-form" action="<c:url value="/system/update_label"/>"
                      method="post" role="form">
                    <input type="hidden" name="id" class="labelId"/>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 分类名称 </label>

                        <div class="col-sm-9">
                            <input type="text" maxlength="10" id="form-field-2" name="labelName"
                                   value="" placeholder="名称" class="col-xs-10 col-sm-5 label-name"/>
                            <span class="help-label-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            &nbsp;
                            &nbsp;
                            &nbsp;
                            <button class="btn btn-info btn-sub btn-update-label" type="submit">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                            <%--&nbsp; &nbsp; &nbsp;--%>
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
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>
