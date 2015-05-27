<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>账号设置</title>
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
</head>
<body>
<div class="page-content">

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">账号设置</h3>
                    <div class="table-header">
                        ${company}
                        <span style="float: right;">
                            <button type="button" class="btn btn-sm btn-success btn-new" data-toggle="modal"
                                    data-target="#addUser">新增员工
                            </button>
                        </span>
                    </div>

                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>类型</th>
                                <th>登陆账号</th>
                                <th class="hidden-480">联系电话</th>

                                <th>
                                    姓名
                                </th>
                                <th class="hidden-480">权限</th>
                                <th class="hidden-480">操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>
                                                ${d.userType.text}
                                        </td>
                                        <td>${d.loginName}</td>
                                        <td class="hidden-480">${d.telephone}</td>
                                        <td>${d.userName}</td>

                                        <td class="hidden-480">
                                            <button type="button" data-whatever="${d.id}"
                                                    class="btn btn-info btn-sm permission-btn" data-toggle="modal"
                                                    data-target="#myModal">
                                                权限
                                            </button>
                                        </td>

                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green" href="<c:url value="/user/find_user?id=${d.id}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </a> &nbsp;&nbsp;
                                                <button type="button" data-whatever="${d.id}"
                                                        data-url="<c:url value="/user/delete_user.json?id=${d.id}"/>"
                                                        class="btn btn-danger btn-sm del-btn" data-toggle="modal"
                                                        <c:if test="${d.isHaveInn}">data-target="#myModalShare"</c:if>
                                                        <c:if test="${!d.isHaveInn}">data-target="#myModal"</c:if>
                                                        >
                                                    删除
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->
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
                <input name="user-id" id="user-id-1" class="user-id" type="hidden"/>
                <input type="hidden" class="data-url"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">确认</button>
            </div>
        </div>
    </div>
</div>
<%--删除弹出层分配客栈--%>
<!-- Modal -->
<div class="modal fade" id="myModalShare" tabindex="-1" role="dialog" aria-labelledby="myModalShareLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalShareLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>该员工帐号下有正在管理中的客栈，
                    若要删除，先将客栈交接到其他帐号！</p><br/>

                <div class="user-body">

                </div>

                <input name="user-id" id="user-id" class="user-id" type="hidden"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit-1">确认删除</button>
            </div>
        </div>
    </div>
</div>
<%--新增员工--%>
<div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="addUserLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addUserLabel">新增员工</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="<c:url value="/user/update_user"/>" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 登录账号 </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-1" name="loginName"
                                   value="" placeholder="登录账号" class="col-xs-10 col-sm-5 login-name"
                                   data-url="<c:url value="/user/check_user_name.json"/>"/>
                            <span class="help-login-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 密 码 </label>

                        <div class="col-sm-9">
                            <input type="password" name="password" id="form-field-2" placeholder="密码"
                                   class="col-xs-10 col-sm-5 ace pawd"/>
                        <span class="help-password col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 联系电话 </label>

                        <div class="col-sm-9">
                            <input type="text" class="col-xs-10 col-sm-5 ace tel" id="form-input-readonly"
                                   name="telephone"
                                   value=""/>
                        <span class="help-tel col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-4">姓 名</label>

                        <div class="col-sm-9">
                            <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName" value=""
                                   id="form-field-4" placeholder="真实姓名"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                            <div class="space-2"></div>

                            <div class="help-block" id="input-size-slider"></div>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info btn-sub" type="button" data-toggle="modal"
                                    data-target="#permissionPage">
                                <i class="icon-ok bigger-110"></i>
                                下一步
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn" type="reset">
                                <i class="icon-undo bigger-110"></i>
                                清空
                            </button>
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
<script src="<%=basePath%>/js/user_list.js"></script>
</body>
</html>
