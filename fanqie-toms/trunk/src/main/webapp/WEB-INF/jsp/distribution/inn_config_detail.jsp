<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<link href="<%=basePath%>/assets/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">


<div class="modal-content">
    <div class="table-header" style="background-color:gainsboro;text-align: center">
             ${inn.innName}
    </div>
    <div class="modal-body">
        <form id="form-order-configId"  method="post" role="form">
            <input name="innId" type="hidden" value="${inn.innId}">
            <input id="urlId" type="hidden" value="<c:url value="/distribution/ajax/saveConfig.json"/>">
            <c:forEach items="${orderConfigList}" var="c">
                ${c.otaInfo}<input type="hidden" name="otaInfoId" value="${c.otaInfoId}"> <input name="status${c.otaInfoId}" type="radio" value="1" <c:if test="${c.status==1}">checked</c:if> />人工手动确认
                 <input name="status${c.otaInfoId}" type="radio" value="0" <c:if test="${c.status==0}"> checked </c:if> />系统自动确认<br>
            </c:forEach>
            <span style="text-align: center" id="msgId"></span>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info btn-sub"  type="button" data-dismiss="modal" id="userPlusBtn">
                        <i class="icon-ok bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<script>
        $("#userPlusBtn").on("click",function(){
            var data = $("#form-order-configId").serialize();
            var url = $("#urlId").val();
           /* console.log("data:"+data);
            console.log("url"+url);*/
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                data:$("#form-order-configId").serialize(),
                success:function(data){
                   /* console.log("status"+data.status);*/
                    if(data.status){
                        $("#msgId").html("保存成功!");
                    }else{
                        $("#msgId").html("保存失败!");
                    }
                    /*var  i =layer.load(2, {time: 2*1000});
                    console.log("=="+i)
                    if(i==1){
                        parent.window.location.href =  parent.window.location.href
                    }*/
                }
            })
        })
</script>
