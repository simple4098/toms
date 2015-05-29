<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/29
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>客栈列表</title>
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
                    <h3 class="header smaller lighter blue">客栈列表</h3>

                    <div class="table-header">
            <span style="text-align: center">
            已加盟客栈：<span style="color:red;font-size: x-large">
              <c:if test="${not empty data}">${data.size()}</c:if>
            </span>家
              </span>
                        <span style="float: right;">
                            <button type="button" class="btn btn-sm btn-success btn-new" data-toggle="modal"
                                    data-target="#myModal">新增客栈
                            </button>
                        </span>
                    </div>

                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>客栈名称</th>
                                <th>番茄账户名</th>
                                <th class="hidden-480">联系号码</th>

                                <th width="200">
                                    <select name="innLabelId" class="inn-label"
                                            data-url="<c:url value="/inn_manage/find_inns.json"/>">
                                        <option value="" selected>客栈标签</option>
                                        <c:if test="${not empty labels}">
                                            <c:forEach items="${labels}" var="l">
                                                <option value="${l.id}">${l.labelName}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </th>
                                <th class="hidden-480">加盟编号</th>
                                <th width="200">
                                    <select name="userId" class="user-id"
                                            data-url="<c:url value="/inn_manage/find_inns.json"/>">
                                        <option value="" selected>所属管理员</option>
                                        <c:if test="${not empty userInfos}">
                                            <c:forEach items="${userInfos}" var="u">
                                                <option value="${u.id}">${u.userName}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </th>
                                <th>正式加入时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>
                                                ${d.innName}
                                        </td>
                                        <td>${d.innCode}</td>
                                        <td class="hidden-480">${d.mobile}</td>
                                        <td>${d.labelName}</td>

                                        <td class="hidden-480">
                                                ${d.code}
                                        </td>

                                        <td>
                                                ${d.userName}
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${d.bangDate}" pattern="yyyy-MM-dd"/>
                                        </td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green"
                                                   href="<c:url value="/inn_manage/to_update_inn?id=${d.id}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </a>
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
<%--新增客栈弹出层--%>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增客栈操作方式</h4>
            </div>
            <div class="modal-body">
                操作步骤的文案
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>
