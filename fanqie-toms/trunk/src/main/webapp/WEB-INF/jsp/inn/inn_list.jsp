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
                            <button type="button" class="btn btn-sm btn-success btn-new-inn" data-toggle="modal"
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

                            <tbody class="table-data">

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
    $('.btn-new-inn').on('click', function () {
        layer.alert('提示信息：新增绑定客栈的文案', {icon: 6});
    });
    //页面加载完成执行
    $(function () {
        var innLabelId = $('.inn-label').val();
        var userId = $('.user-id').val();
        layer.load();

        $.ajax({
            url: '<c:url value="/inn_manage/find_inns_data.json"/>',
            type: 'post',
            dataType: 'json',
            data: 'innLabelId=' + innLabelId + "&userId=" + userId,
            success: function (data) {
                if (data.data.length != 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        var text = "<tr><td>" + data.data[i].innName + "</td><td>" + data.data[i].innCode + "</td><td>" + data.data[i].mobile + "</td><td>" + data.data[i].labelName + "</td>" +
                                "<td>" + data.data[i].code + "</td><td>" + data.data[i].userName + "</td><td>" + data.data[i].bangDataFormat + "</td>" +
                                "<td><div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'><a class='green' href='<c:url value="/inn_manage/to_update_inn?id="/>" + data.data[i].id + "'><i class='icon-pencil bigger-130'></i></a></div></td></tr>";
                        $('.table-data').append(text);
                    }
                    layer.closeAll('loading');
                }
            }
        })
    });
    $('.inn-label').on('change', function () {
        $('.table-data td').remove();
        var innLabelId = $('.inn-label').val();
        var userId = $('.user-id').val();
        layer.load();
        $.ajax({
            url: '<c:url value="/inn_manage/find_inns_data.json"/>',
            type: 'post',
            dataType: 'json',
            data: 'innLabelId=' + innLabelId + "&userId=" + userId,
            success: function (data) {
                if (data.data.length != 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        var text = "<tr><td>" + data.data[i].innName + "</td><td>" + data.data[i].innCode + "</td><td>" + data.data[i].mobile + "</td><td>" + data.data[i].labelName + "</td>" +
                                "<td>" + data.data[i].code + "</td><td>" + data.data[i].userName + "</td><td>" + data.data[i].bangDataFormat + "</td>" +
                                "<td><div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'><a class='green' href='<c:url value="/inn_manage/to_update_inn?id="/>" + data.data[i].id + "'><i class='icon-pencil bigger-130'></i></a></div></td></tr>";
                        $('.table-data').append(text);
                    }
                    layer.closeAll('loading');
                } else {
                    layer.closeAll('loading');
                    layer.msg('没有找到任何数据，请重试');
                }
            },
            error: function (data) {
                alert(data.message);
            }
        });
    });
    $('.user-id').on('change', function () {
        $('.table-data td').remove();
        var innLabelId = $('.inn-label').val();
        var userId = $('.user-id').val();
        layer.load();
        $.ajax({
            url: '<c:url value="/inn_manage/find_inns_data.json"/>',
            type: 'post',
            dataType: 'json',
            data: 'innLabelId=' + innLabelId + "&userId=" + userId,
            success: function (data) {
                if (data.data.length != 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        var text = "<tr><td>" + data.data[i].innName + "</td><td>" + data.data[i].innCode + "</td><td>" + data.data[i].mobile + "</td><td>" + data.data[i].labelName + "</td>" +
                                "<td>" + data.data[i].code + "</td><td>" + data.data[i].userName + "</td><td>" + data.data[i].bangDataFormat + "</td>" +
                                "<td><div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'><a class='green' href='<c:url value="/inn_manage/to_update_inn?id="/>" + data.data[i].id + "'><i class='icon-pencil bigger-130'></i></a></div></td></tr>";
                        $('.table-data').append(text);
                    }
                    layer.closeAll('loading');
                } else {
                    layer.closeAll('loading');
                    layer.msg('没有找到任何数据，请重试');
                }
            },
            error: function (data) {
                alert(data.message);
            }
        });
    });
</script>
<script src="<%=basePath%>/js/my-system.js"/>
</body>
</html>
