
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <title>客栈特殊价设置</title>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/pages.css"/>
</head>
<div class="page-content">

    <div class="row">
        <div class="col-xs-12">
            <h3 class="header smaller lighter blue">客栈房型价格设置</h3>
            <a class="btn  btn-sub tp-price" style="background-color:#CE6613 !important; margin-left: 995px" account_id="${bangInn.accountId}" inn_id="${bangInn.innId}" ota_info_id="${otaInfoId}" >同步价格到卖房网站</a>
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                                <tr>
                                    <th>房型名称</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody class="table-data" style="font-size: 14px;">
                            <c:if test="${not empty list}">
                                <c:forEach items="${list}" var="d">
                                    <tr>
                                        <td>${d.roomTypeName}</td>
                                        <td> <a class="btn btn-info btn-sub room-price-class" data-url="<c:url value="/distribution/ajax/room_price_detail"/>" type_name="${d.roomTypeName}" inn_id="${innId}" ota_info_id="${otaInfoId}" room_type_id="${d.roomTypeId}">价格设置</a></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty list}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
    </div>
    <!-- /.col -->
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/js/fang-peice.js"></script>
<%--<script>
    $('.room-price-class').on('click', function () {
        var otaInfoId = $(this).attr('ota_info_id');
        var roomTypeId = $(this).attr('room_type_id');
        var innId = $(this).attr('inn_id');
        var roomTypeName = $(this).attr('type_name');

        var url = '<c:url value="/distribution/ajax/room_price_detail"/>'+"?innId="+innId+"&otaInfoId="+otaInfoId+"&roomTypeId="+roomTypeId+"&roomTypeName="+roomTypeName;
        var $_this = $(this);
        var b = !$_this.hasClass("push");
        if(b) {
        $_this.addClass("push");
        $.ajax({
            type:'POST',
            url:url,
            dataType:'html',
            success:function(data){
                layer.open({
                    title:"房型特殊价格设置",
                    type: 1,
                    shade: false,
                    area: ['516px', '400px'],
                    shadeClose: true, //开启遮罩关闭
                    content: data
                });
            },error:function(data){
                alert(data);
             }
            })

        }

    });

    $('.tp-price').on('click', function () {
        var $this = $(this);
        if( !$this.hasClass('push')){
            layer.load(0, {time: 3*1000});
            $this.addClass("push");
            var otaInfoId = $(this).attr('ota_info_id');
            var innId = $(this).attr('inn_id');
            var accountId = $(this).attr('account_id');
            var url = '<c:url value="/distribution/ajax/tpPrice.json"/>'+"?innId="+innId+"&otaInfoId="+otaInfoId+"&accountId="+accountId
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                success:function(data){
                    $this.removeClass("push");
                    if(!data.status){
                        layer.alert("推送失败,检查是否房价信息存在!");
                    }else{
                        layer.msg('同步完成');
                    }
                },error:function(data){
                    $this.removeClass("push");
                    layer.alert("同步失败!")
                }
            })
        }else{
            layer.alert("正在同步请稍后...")
        }

    });
</script>--%>



