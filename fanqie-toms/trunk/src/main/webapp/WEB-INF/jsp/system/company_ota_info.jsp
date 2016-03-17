<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>修改公司渠道-【${data.otaInfo}】基本信息</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            修改信息 <div class="alert alert-danger center">
            修改公司渠道-【${data.otaInfo}】基本信息
        </div>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <form id="form-id-companyId" class="form-horizontal" >
                <input type="hidden" name="id" value="${data.id}"/>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> appKey </label>
                    <div class="col-sm-9">
                        <input type="text" class="col-xs-10 col-sm-5 ace " id="form-input-readonly" name="appKey"
                               value="${data.appKey}"/>
                        <span class="help-tel col-xs-12 col-sm-7"></span>
                    </div>
                </div>
               <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> appSecret </label>
                    <div class="col-sm-9">
                        <input type="text" class="col-xs-10 col-sm-5 ace "  name="appSecret"
                               value="${data.appSecret}"/>
                        <span class="help-tel col-xs-12 col-sm-7"></span>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                   <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> sessionKey </label>
                   <div class="col-sm-9">
                       <input type="text" class="col-xs-10 col-sm-5 ace "  name="sessionKey"
                              value="${data.sessionKey}"/>
                       <span class="help-tel col-xs-12 col-sm-7"></span>
                   </div>
               </div>
               <div class="space-4"></div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 开关 </label>
                    <div class="col-sm-9">
                        <input type="radio"  name="status" value="1" <c:if test="${data.status==1}">checked</c:if>> ON
                        <input type="radio"  name="status" value="0" <c:if test="${data.status==0}">checked</c:if>> OFF
                    </div>
                </div>
                <div class="space-4"></div>

                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        &nbsp; &nbsp; &nbsp;
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info" type="submit">
                            <i class="icon-ok bigger-110"></i> 确认
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
<script type="text/javascript">
    $(function(){
        $(".btn-info").bind("click",function(){
            $.ajax({
                url: '<c:url value="/system/update_company_otaInfo.json"/>',
                type: 'post',
                dataType: 'json',
                data: $('#form-id-companyId').serialize(),
                success: function (data) {
                    if (data.status) {
                        layer.alert(data.message, {icon: 6}, function () {
                            window.location.href = '<c:url value="/system/find_company_ota"/>?id=${param.companyId}';
                        });

                    } else {
                        layer.alert(data.message, {icon: 5});
                    }
                },
                error: function (data) {
                    layer.alert(data.message, {icaon: 5})
                }
            });
        })
    })
</script>
</body>
</html>
