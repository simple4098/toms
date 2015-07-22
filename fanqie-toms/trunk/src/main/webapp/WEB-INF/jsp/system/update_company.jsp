<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>修改公司基本信息</title>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            修改信息 <div class="alert alert-danger center">
            公司信息的修改必须联系研发同学，有很多数据是对接要用的数据。谨慎操作!!!
        </div>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <form id="form-id-companyId" class="form-horizontal" >
                <input type="hidden" name="id" value="${data.id}"/>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 公司名称 </label>
                    <div class="col-sm-9">
                        <input type="text" id="form-field-1" name="companyName"
                               value="${data.companyName}" placeholder="公司名称" class="col-xs-10 col-sm-5 "/>
                        <span class="help-inline col-xs-12 col-sm-7"> </span>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 公司编码 </label>
                    <div class="col-sm-9">
                        <input type="text" name="companyCode" id="form-field-2" placeholder="公司编码" readonly
                              value="${data.companyCode}" class="col-xs-10 col-sm-5 ace "/>
                        <span class="help-password col-xs-12 col-sm-7"></span>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> OTAID </label>
                    <div class="col-sm-9">
                        <input type="text" class="col-xs-10 col-sm-5 ace " id="form-input-readonly" name="otaId"
                               value="${data.otaId}"/>
                        <span class="help-tel col-xs-12 col-sm-7"></span>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 渠道用户名 </label>
                    <div class="col-sm-9">
                        <input type="text" class="col-xs-10 col-sm-5 ace "  name="userAccount"
                               value="${data.userAccount}"/>
                        <span class="help-tel col-xs-12 col-sm-7"></span>
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly"> 渠道密码 </label>
                    <div class="col-sm-9">
                        <input type="text" class="col-xs-10 col-sm-5 ace "  name="userPassword"
                               value="${data.userPassword}"/>
                        <span class="help-tel col-xs-12 col-sm-7"></span>
                    </div>
                </div>

                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        &nbsp; &nbsp; &nbsp;
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info" type="submit">
                            <i class="icon-ok bigger-110"></i> 确认
                        </button>

                        <%--<button class="btn" id="bta-reset-id">
                         <i class="icon-undo bigger-110"></i>
                          返回
                        </button>--%>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/my-system.js"></script>
<script type="text/javascript">
    $(function(){
      /*  $("#bta-reset-id").on('click',function(){
            window.location.href='<c:url value='/system/find_companys'/>';
        })*/

        $(".btn-info").bind("click",function(){
            $.ajax({
                url: '<c:url value="/system/update_company.json"/>',
                type: 'post',
                dataType: 'json',
                data: $('#form-id-companyId').serialize(),
                success: function (data) {
                    if (data.status) {
                        layer.alert(data.message, {icon: 6}, function () {
                            window.location.reload();
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
