<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
    <title>账号设置</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
    <script src="<%=basePath%>/js/my-system.js"></script>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <form class="form-page" name="form-page" id="form-page" action="<c:url value="/user/find_users"/>" method="post">
        <input type="hidden" class="pageId" id="pageId" name="page"/>
    </form>
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
                            <thead style="font-size: 14px;">
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

                            <tbody style="font-size: 14px;">
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
                                                    class="btn btn-info btn-sm permission-btn " data-toggle="modal"
                                                    data-target="#jurisdiction"
                                                    data-url="<c:url value="/user/user_permission.json?"/>userId=${d.id}">
                                                权限
                                            </button>
                                        </td>

                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green" href="<c:url value="/user/find_user?id=${d.id}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </a> &nbsp;&nbsp;
                                                <button type="button" data-whatever="${d.id}"
                                                        del-url="<c:url value="/user/only_delete_user.json?id=${d.id}"/>"
                                                        data-url="<c:url value="/user/delete_user.json?id=${d.id}"/>"
                                                        json-url="<c:url value="/user/find_other_user.json?id=${d.id}"/>"
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
        <c:if test="${not empty data && page.pageCount>1}">
        <!-- PAGE CONTENT ENDS -->
        <div class="container">
            <div class="text-center">
                <ul class="pagination">

                    <li <c:if test="${page.page==1}">class="disabled"</c:if>>
                        <a <c:if test="${page.page!=1}">onclick="page(${page.page-1})"</c:if>>
                            <i class="icon-double-angle-left"></i>
                        </a>
                    </li>

                    <c:forEach begin="1" end="${page.pageCount}" step="1" varStatus="vs" var="p">
                        <c:if test="${vs.count<11}">
                            <li <c:if test="${page.page==p}">class="active"</c:if>>
                                <a onclick="page(${p})">${p}</a>
                            </li>
                        </c:if>
                        <c:if test="${vs.count==10}">
                            <li>
                                <a>...</a>
                            </li>
                        </c:if>
                        <c:if test="${vs.count >10}">
                            <c:if test="${vs.count==page.pageCount}">
                                <li <c:if test="${page.page==p}">class="active"</c:if>>
                                    <a onclick="page(${p})">${p}</a>
                                </li>
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <c:if test="${page.page!=page.pageCount}">
                        <li>
                            <a onclick="page(${page.page+1})">
                                <i class="icon-double-angle-right"></i>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
        </c:if>
        <c:if test="${empty data}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
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
                <input type="hidden" class="del-url"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-del">确认</button>
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
            <form class="form-horizontal delete-user" action="<c:url value="/user/delete_user"/>" method="post"
                  role="form">
            <div class="modal-body">
                <p>该员工帐号下有正在管理中的客栈，
                    若要删除，先将客栈交接到其他帐号！</p><br/>
                <div class="user-body">
                    将该帐号下的客栈全部交接给：
                    <select name="replaceUserId" class="user-list">
                        <option value="">请选择</option>
                    </select>
                </div>
                <input name="id" id="user-id" class="user-id" type="hidden"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-del-1">确认删除</button>
            </div>
            </form>
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
                            <button class="btn btn-info btn-sub" type="button" data-dismiss="modal" id="userPlusBtn"
                                    >
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
<%--修改权限列表--%>
<div class="modal fade" id="jurisdiction" tabindex="-1" role="dialog" aria-labelledby="myModalLabelPermission"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelPermission">设置该员工权限</h4>
            </div>
            <div class="modal-body">
                <form class="update-permission-form" id="permission-form"
                      action="<c:url value="/user/update_permission"/>" method="post">
                    <input type="hidden" class="permission-user-id" name="userId"/>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="ckAll">全选
                        </label>
                    </div>
                    <hr>
                    <ul class="check-list">
                        <c:if test="${not empty permissions}">
                            <c:forEach items="${permissions}" var="p">
                                <li>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" class="p-check ${p.id}" name="permissionIds"
                                               id="inlineCheckbox1" value="${p.id}">${p.permissionName}
                                    </label>
                                </li>
                            </c:forEach>
                        </c:if>
                        <br>
                    </ul>
                    <hr>
                    <br/>
                    <!-- 单选框 -->
                    <label class="radio-inline">
                        <input type="radio" class="dataPermission0" name="dataPermission" id="inlineRadio1" value="0">只看自己的客栈
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="dataPermission" class="dataPermission1" id="inlineRadio2" value="1">能看所有的客栈
                    </label>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" data-url="<c:url value="/user/update_permission.json"/>"
                        class="btn btn-success btn-update-permission" data-dismiss="modal">确定
                </button>
                <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
    </div>
</div>
<%--新增权限--%>
<div class="modal fade" id="jurisdictionnew" tabindex="-1" role="dialog" aria-labelledby="myModalLabelPermissionnew"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelPermissionnew">设置该员工权限</h4>
            </div>
            <div class="modal-body">
                <form class="new-permission-form" id="permission-form-new" action="<c:url value="/user/create_user"/>"
                      method="post">
                    <input type="hidden" class="login-name-permission" name="loginName"/>
                    <input type="hidden" class="password-permission" name="password"/>
                    <input type="hidden" class="telephone-permission" name="telephone"/>
                    <input type="hidden" class="user-name-permission" name="userName"/>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" id="ckAll-new">全选
                        </label>
                    </div>
                    <hr>
                    <ul class="check-list">
                        <c:if test="${not empty permissions}">
                            <c:forEach items="${permissions}" var="p">
                                <li>
                                    <label class="checkbox-inline">
                                        <input type="checkbox" class="p-check ${p.id}" name="permissionIds"
                                               id="inlineCheckbox1" value="${p.id}">${p.permissionName}
                                    </label>
                                </li>
                            </c:forEach>
                        </c:if>
                        <br>
                    </ul>
                    <hr>
                    <br/>
                    <!-- 单选框 -->
                    <label class="radio-inline">
                        <input type="radio" name="dataPermission" class="dataPermission3" id="inlineRadio3" value="0">只看自己的客栈
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="dataPermission" class="dataPermission4" id="inlineRadio4" value="1">能看所有的客栈
                    </label>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" data-url="<c:url value="/user/create_user.json"/>"
                        class="btn btn-success btn-new-permission">确定
                </button>
                <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/js/user-list.js"></script>
