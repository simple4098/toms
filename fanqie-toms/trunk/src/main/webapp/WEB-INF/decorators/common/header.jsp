<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/20
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<link rel="stylesheet" href="<%=basePath%>/assets/css/pages.css"/>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<div class="navbar navbar-default" style="height: 10px" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <div class="logo">第三方运营管理系统</div>
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="<%=basePath%>/assets/avatars/user1.jpg" alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎,</small>
									<toms:currentUser/>
								</span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a data-toggle="modal" data-target="#updatePassword" class="update-password">
                                <i class="icon-cog"></i>
                                重置密码
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<c:url value="/logout" />">
                                <i class="icon-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.ace-nav -->
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>
<%--修改密码弹出层--%>
<div class="modal fade" id="updatePassword" tabindex="-1" role="dialog" aria-labelledby="addLabelLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addLabelLabel">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal update-password" action="<c:url value="/user/update_password"/>"
                      method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 新密码 </label>

                        <div class="col-sm-9">
                            <input type="password" id="form-field-1" name="password"
                                   value="" placeholder="密码" class="col-xs-10 col-sm-5 paswd"/>
                            <span class="help-password-head col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info btn-sub-head" type="submit">
                                <i class="icon-ok bigger-110"></i>
                                确认
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
<script src="<%=basePath%>/js/my-system.js"></script>