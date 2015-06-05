<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/27
  Time: 17:07
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
    <title>消息通知</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            消息通知
        </h1>
    </div>
    <!-- /.page-header -->
    <form name="data-form" class="data-form" action="<c:url value="/notice/send_message.json"/>">
    <div class="row">
        <div class="col-xs-12 col-sm-4 widget-container-span">
            <!-- PAGE CONTENT BEGINS -->

            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>通知模板：</h5>
                    <select name="noticeId" class="notice" data-url="<c:url value="/system/find_notice.json?id="/>">
                        <option value="" selected>请选择</option>
                        <c:if test="${not empty data}">
                            <c:forEach items="${data}" var="d">
                                <option value="${d.id}">${d.noticeTitle}</option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <div class="widget-toolbar">
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main">
                        <p class="alert alert-info">
                            <textarea class="notice-content" maxlength="70" rows="10" cols="65"
                                      style="resize: none"></textarea>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-sm-4 widget-container-span">
            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>选择接收的客栈</h5>

                    <div class="widget-toolbar">

                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="ckAll">选择所有客栈
                            </label>
                        </div>
                        <hr class="hr-2">
                        <div class="checkUnit">
                            <c:if test="${not empty bangInnList}">
                                <c:forEach items="${bangInnList}" var="bi">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" class="checkbox"
                                                   id="${bi.innLabelName}">${bi.innLabelName}
                                        </label>
                                    </div>
                                    <c:if test="${not empty bi.bangInnList}">
                                        <c:forEach items="${bi.bangInnList}" var="inn">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="innId" data-name="${bi.innLabelName}"
                                                           value="${inn}"
                                                           class="checkbox sub">${inn.innName}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <hr class="hr-2">
                                </c:forEach>
                            </c:if>
                            <hr class="hr-2">
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-xs-12 col-sm widget-container-span">
            <div class="widget-box">
                <div class="widget-header" style="text-align: center">
                    <h5>请选择发送的方式：</h5>
          <span style="padding-left: 60px;">
          <input type="checkbox" name="sendType" value="MESSAGE"/>短信发送
            </span>
          <span style="padding-left: 80px">
          <input name="sendType" value="POPUP" type="checkbox" style="padding-left: 20%"/>系统弹窗
            </span>
          <span style="padding-left: 100px;">
          <button class="btn btn-success btn-send">确认发送</button>
          </span>
                </div>

            </div>
        </div>
    </div>
    </form>
</div>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script>
    $('.btn-send').on('click', function () {
        $.ajax({
            url: '<c:url value="/notice/send_message.json"/>',
            type: 'post',
            dataType: 'json',
            data: $('.form-data').serialize(),
            success: function (data) {
                if (data.status) {
                    layer.alert('提示信息：发送成功！', {icon: 6});
                }
            },
            error: function () {
                layer.msg("系统错误!");
            }
        })
    });
    $(function () {
        // 全选&&反选 所有客栈
        $("#ckAll").click(function () {
            $("input.checkbox").prop("checked", this.checked);
        });

        $("input.checkbox").click(function () {
            var $subs = $("input.checkbox");
            $("#ckAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
        });
        // 全选&&反选 分组客栈
        $("input.checkbox").click(function () {
            if (!$(this).attr('id')) {
                var keywords = $(this).attr('data-name'),
                        $subs = $("input[data-name=" + keywords + "]"),
                        $parent = $("#" + keywords);
                $parent.prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
            }
            else {
                var id = $(this).attr('id');
                $("input[data-name=" + id + "]").prop("checked", this.checked);
            }
        });
    });
</script>

<script src="<%=basePath%>/js/my-system.js"></script>
</body>
</html>

