<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/29
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:set value="${pagination}" var="page"/>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">客栈列表</h3>

                    <div class="table-header">
            <span style="text-align: center">
            已加盟客栈：<span style="color:red;font-size: x-large">
              <c:if test="${not empty data}">${page.rowsCount}</c:if>
            </span>家
              </span>
                        <span style="float: right;">
                            <button type="button" class="btn btn-sm btn-success btn-new-inn" data-toggle="modal"
                                    data-target="#myModal">新增客栈
                            </button>
                        </span>
                    </div>

                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead>
                            <form class="form-page" action="<c:url value="/inn_manage/find_inns"/>" method="post">
                                <input type="hidden" id="pageId" name="page" value="${page.page}"/>
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
                                                <option
                                                        <c:if test="${innLabel == l.id}">selected</c:if>
                                                        value="${l.id}">${l.labelName}</option>
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
                                                <option
                                                        <c:if test="${userId == u.id}">selected</c:if>
                                                        value="${u.id}">${u.userName}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </th>
                                <th>正式加入时间</th>
                                <th>操作</th>
                            </tr>
                            </form>
                            </thead>

                            <tbody class="table-data">
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>${d.innName}</td>
                                        <td>${d.innCode}</td>
                                        <td>${d.mobile}</td>
                                        <td>${d.labelName}</td>
                                        <td>${d.code}</td>
                                        <td>${d.userName}</td>
                                        <td>${d.bangDataFormat}</td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green"
                                                   href="<c:url value="/inn_manage/to_update_inn?id="/>${d.id}"/>
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
                        <c:if test="${vs.count ==10}">
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
    </div>
    <!-- /.col -->

</div>
<!-- /.row -->
<script type="text/javascript">
    $('.btn-new-inn').on('click', function () {
        layer.alert('提示信息：新增绑定客栈的文案', {icon: 6});
    });
    $('.inn-label').on('change', function () {
        $("#pageId").attr("value", 1);
        $('.form-page').submit();
    });
    $('.user-id').on('change', function () {
        $("#pageId").attr("value", 1);
        $('.form-page').submit();
    });
    //分页方法
    function page(page) {
        $("#pageId").attr("value", page);
        $('.form-page').submit();
    }
</script>
<script src="<%=basePath%>/js/my-system.js"/>
</body>
</html>
