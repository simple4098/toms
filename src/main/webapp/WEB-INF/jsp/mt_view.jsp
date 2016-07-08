<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>欢迎页面</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/userSet.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/innRelation.css'/>">

</head>

<body>
<div class="widget-body">
    <div class="widget-main">


        <hr>

        <div>
            <label for="form-field-9">客栈innId,以逗号分隔','</label>
            <textarea class="form-control limited" name="innIds" id="form-field-9" maxlength="100" style="margin: 0px -0.34375px 0px 0px; height: 61px; width: 516px;"></textarea>
            <br>
            <button class="btn btn-info" type="button">
                <i class="icon-ok bigger-110"></i> 提交
            </button>
        </div>
    </div>
</div>
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/layer/layer.js'/>"></script>
<script type="text/javascript">
    $(".btn-info").on("click",function(){
        var i = layer.load(0);
        var innIds = $("#form-field-9").val();
        var r = new RegExp("([0-9]+[,]?)+");
        if(!r.test(innIds)){
            layer.msg("请输入正确格式的数据");
            layer.close(i);
            return false;
        }
        $.ajax({
            data:{"innIds":innIds},
            type:'post',
            dataType:'json',
            url:'<c:url value="/mt/credit.json"/>',
            success:function(data){
                if(data.status=='200'){
                    layer.msg("更新成功");
                }else{
                    layer.msg("更新失败:"+data.message);
                }
                layer.close(i);
            },error:function(data){
                layer.msg("失败:"+data.message);
                layer.close(i);
            }
        })
    })
</script>
</body>

</html>

