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
    <form class="form-page" name="form-page" id="form-page" action="<c:url value="/user/find_companys"/>" method="post">
    </form>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">公司管理</h3>

                    <div class="table-header">
                        <span style="float: right;">
                            <button type="button" class="btn btn-sm btn-success btn-new" data-toggle="modal"
                                    data-target="#addUser">新增公司
                            </button>
                        </span>
                    </div>

                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>公司名称</th>
                                <th>公司编码</th>
                                <th class="hidden-480">OTAID</th>

                                <th>
                                    账户名称
                                </th>
                                <th>账户密码</th>
                                <th class="hidden-480">权限</th>
                                <th class="hidden-480">操作</th>
                            </tr>
                            </thead>

                            <tbody style="font-size: 14px;">
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>
                                                ${d.companyName}
                                        </td>
                                        <td>${d.companyCode}</td>
                                        <td class="hidden-480">${d.otaId}</td>
                                        <td>${d.userAccount}</td>
                                        <td>${d.userPassword}</td>
                                        <td class="hidden-480">
                                            <button type="button" data-whatever="${d.id}"
                                                    class="btn btn-info btn-sm permission-btn " data-toggle="modal"
                                                    data-target="#jurisdiction"
                                                    data-url="<c:url value="/system/find_company_permission.json?companyId=${d.id}"/>">
                                                权限
                                            </button>
                                        </td>

                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green" href="<c:url value="/system/find_company?id=${d.id}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </a> &nbsp;&nbsp;
                                                <button type="button" data-whatever="${d.id}" data-url="<c:url value="/system/delete_company.json?companyId=${d.id}"/>" data-target="#myModal"
                                                        class="btn btn-danger btn-sm del-btn"  data-toggle="modal">
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
                <input type="hidden" class="del-url"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-del">确认</button>
            </div>
        </div>
    </div>
</div>
<%--新增公司--%>
<div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="addUserLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addUserLabel">新增公司</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="<c:url value="/user/update_user"/>" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 公司名称 </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-1" name="loginName"
                                   value="" placeholder="公司名称" class="col-xs-10 col-sm-5 login-name"
                                   data-url="<c:url value="/user/check_user_name.json"/>"/>
                            <span class="help-login-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> OTAID </label>

                        <div class="col-sm-9">
                            <input type="text" name="password" id="form-field-2" placeholder="OTAID"
                                   class="col-xs-10 col-sm-5 ace pawd"/>
                        <span class="help-password col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 账户名称 </label>

                        <div class="col-sm-9">
                            <input type="text" class="col-xs-10 col-sm-5 ace tel" id="form-input-readonly"
                                   placeholder="账户名称"
                                   name="telephone"
                                   value=""/>
                        <span class="help-tel col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-4">账户密码</label>

                        <div class="col-sm-9">
                            <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName" value=""
                                   id="form-field-4" placeholder="账户密码"/>
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
                <h4 class="modal-title" id="myModalLabelPermission">设置该公司权限</h4>
            </div>
            <div class="modal-body">
                <form class="update-permission-form" id="permission-form"
                      action="<c:url value="/system/update_company_permission.json"/>" method="post">
                    <input type="hidden" class="permission-user-id" name="companyId"/>

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
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" data-url="<c:url value="/system/update_company_permission.json"/>"
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
                    <input type="hidden" class="login-name-permission" name="companyName"/>
                    <input type="hidden" class="password-permission" name="otaId"/>
                    <input type="hidden" class="telephone-permission" name="userAccount"/>
                    <input type="hidden" class="user-name-permission" name="userPassword"/>

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
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" data-url="<c:url value="/system/create_company.json"/>"
                        class="btn btn-success btn-new-permission">确定
                </button>
                <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/js/company-list.js"></script>
