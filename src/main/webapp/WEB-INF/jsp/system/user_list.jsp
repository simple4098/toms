<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>账号设置</title>
    <%--<script src="<%=basePath%>/assets/js/jquery.dataTables.min.js"></script>--%>
    <%--<script src="<%=basePath%>/assets/js/jquery.dataTables.bootstrap.js"></script>--%>
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
                            <a class="table-header" href="#">新增员工</a>
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
                                    <span class="label label-sm label-warning">
                                    <c:if test="${d.dataPermission == 1}">查看所有客栈</c:if>
                                    <c:if test="${d.dataPermission == 0}">查看自己客栈</c:if>
                                    </span>
                                        </td>

                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green" href="<c:url value="/user/find_user?id=${d.id}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </a>

                                                <a class="red del" data="${d.id}" href="#">
                                                    <i class="icon-trash bigger-130"></i>
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


<script type="text/javascript">
    jQuery(function ($) {
        $('.del').on('click', function () {
            var data = $(this).attr('data');
            layer.confirm('是否删除？', {
                btn: ['确认', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                $.ajax({
                    url: '<c:url value="/user/delete_user.json?id="/>' + data,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (!data.status) {
                            layer.msg(data.message);
                            window.location.reload();
                        } else {
                            layer.msg(data.message);
                            window.location.reload();
                        }
                    },
                    error: function () {
                        //do same thing!
                    }
                });
            }, function () {
//                layer.msg('奇葩么么哒', {shift: 6});
            });
        });
    })
</script>
</body>
</html>
