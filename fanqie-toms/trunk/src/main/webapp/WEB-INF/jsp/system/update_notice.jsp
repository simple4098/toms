<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/22
  Time: 9:39
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
    <title>编辑模板</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            编辑模板
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <form class="form-horizontal" action="<c:url value="/system/update_notice"/>" method="post" role="form">
                <input type="hidden" value="${data.id}" name="id"/>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 模板标题 </label>

                    <div class="col-sm-9">
                        <input type="" id="form-field-1" name="noticeTitle"
                               value="${data.noticeTitle}" placeholder="模板标题" class="col-xs-10 col-sm-5 notice-title"/>
            <span class="help-notice-title col-xs-12 col-sm-7">

											</span>

                    </div>
                </div>

                <div class="space-4"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 模板内容 </label>

                    <div class="col-sm-9">
                        <input type="text" name="noticeContent" id="form-field-2" placeholder="模板内容"
                               value="${data.noticeContent}"
                               class="col-xs-10 col-sm-5 ace notice-content"/>
                        <span class="help-notice-content col-xs-12 col-sm-7">

											</span>
                    </div>
                </div>

                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" type="submit">
                            <i class="icon-ok bigger-110"></i>
                            Submit
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset">
                            <i class="icon-undo bigger-110"></i>
                            Reset
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(function ($) {
        var span = '<span class="middle" name="middle" disabled="false" style="color: red">此项必填</span>';
        $('.btn-info').on('click', function () {
            $('.help-notice-title .middle').remove();
            $('.help-notice-content .middle').remove();
            if ($('.notice-title').val() == null || $('.notice-title').val() == '') {
                $('.help-notice-title').append(span);
                return false;
            }
            if ($('.notice-content').val() == null || $('.notice-content').val() == '') {
                $('.help-notice-content').append(span);
                return false;
            }
        });
    });
</script>
</body>
</html>
