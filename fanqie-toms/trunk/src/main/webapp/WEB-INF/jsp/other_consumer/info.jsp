
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>其他消费</title>
    <%--<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/normalize.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/bootstrap.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/font-awesome.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/openChannerl.css'/>">--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/news-center.css'/>">
</head>
<body>
<div class="container ms-controller" ms-controller="otherPayManage">
    <div class="qita-xiaofei-guanli">
        <h4 style="position:relative">
            其它消费管理 <input id="onoffButton" type="button" data-url="<c:url value="/personality/info/updateFunction.json"/>" data-status="${status}" <c:if test="${!status}"> class="off_button"</c:if><c:if test="${status}"> class="on_button"</c:if> title="点击开启" ms-click="divDisplayIsopenedFun">
            <a class="fr btn btn-primary" onclick="history.go(-1)">返回</a>
        </h4>
        <div class="unopened-content">
            <p class="unopened-con-p">开启该功能后，在手动下单时可以记录除房费以外的其他消费信息；该订单其他消费信息只有分销商自己能看到记录，客栈方依然只能接收到房间消费信息！
            </p>
        </div>
        <div class="notopened-content" ms-css-display="divDisplayIsopened[0]"></div>
        <div class="opened-content"  ms-css-display="divDisplayIsopened[1]">
            <p><a class="btn btn-primary" ms-click="addOtherPayItemFun">+新增其它消费项目</a></p>
            <div>
                 <c:forEach items="${data}" var="d">
                     <ul class="opened-con-ul">
                        <li class="col-sm-2 items-title">
                            <dl>
                                <dd>${d.consumerProjectName}</dd>
                                <dd><a ms-click="editPayItemFun" data-url="<c:url value='/personality/info/editView.json'/>?consumerInfoId=${d.id}">编辑</a><a data-toggle="modal" class="consumer-delete" data-url="<c:url value='/personality/info/delete.json'/>?consumerInfoId=${d.id}" data-target="#deletePayItems">删除</a></dd>
                            </dl>
                        </li>
                        <c:if test="${!empty d.otherList}">
                            <li class="col-sm-10 items-detail">
                        </c:if>
                        <c:forEach items="${d.otherList}" var="info">
                            <dl>
                                <dd><c:if test="${info.status}"><span>√</span></c:if> ${info.priceName}</dd>
                                <dd>${info.price}</dd>
                            </dl>
                        </c:forEach>
                        <c:if test="${!empty d.otherList}">
                           </li>
                        </c:if>
                     </ul>
                    </c:forEach>
            </div>
        </div>
    </div>
    <div class="popups">
        <div class="modal fade" id="addPayItems">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增消费项目</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 消费项目名称
                            </label>
                            <div class="col-sm-8">
                                <input type="text" data-tips="如：“车票”、“门票” " autocomplete="off" value="" maxlength="5" placeholder="如：“车票”、“门票” " class="ipt" ms-duplex="addOtherPayItem.consumerProjectName"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 价格类型名称
                            </label>
                            <label class="col-sm-4 control-label no-padding-right"> 价格
                            </label>
                            <label class="col-sm-4 control-label no-padding-right"> 默认显示并设为必填写项
                            </label>
                        </div>
                        <div class="form-group" ms-repeat="addOtherPayItem.otherList">
                            <div class="col-sm-4">
                                <input type="text" data-tips="如：“全票”、“会员价” " autocomplete="off" value="" maxlength="5" placeholder="如：“全票”、“会员价” " class="ipt" ms-duplex="el.priceName"/>
                            </div>
                            <div class="col-sm-4">
                                <input type="text" data-tips="请填写正整数 " autocomplete="off" value="" maxlength="4" placeholder="请填写正整数 " class="ipt" ms-duplex="el.price"/>
                            </div>
                            <div class="col-sm-4">
                                <input type="checkbox" ms-duplex-checked="el.status"/>
                            </div>
                        </div>
                        <a class="btn btn-primary add-price-type" ms-click="addPriceType('add')">+新增价格类型</a>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info btn-hand-make-order" type="button" ms-click="saveOtherPayItem" id="saveOtherPayItem" data-url="<c:url value="/personality/info/addConsumerInfo.json"/>">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="editPayItems">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">编辑消费项目</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 消费项目名称
                            </label>
                            <div class="col-sm-8">
                                <input type="text" data-tips="如：“车票”、“门票” " autocomplete="off" value="" placeholder="如：“车票”、“门票” " class="ipt" ms-duplex="eidtPayItem.consumerProjectName"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 价格类型名称
                            </label>
                            <label class="col-sm-4 control-label no-padding-right"> 价格
                            </label>
                            <label class="col-sm-4 control-label no-padding-right"> 默认显示并设为必填写项
                            </label>
                        </div>
                        <div class="form-group" ms-repeat="eidtPayItem.otherList">
                            <div class="col-sm-4">
                                <input type="text" data-tips="如：“全票”、“会员价” " autocomplete="off" value="" maxlength="5" placeholder="如：“全票”、“会员价” " class="ipt" ms-duplex="el.priceName"/>
                            </div>
                            <div class="col-sm-4">
                                <input type="text" data-tips="请填写正整数 " autocomplete="off" value="" maxlength="4" placeholder="请填写正整数 " class="ipt" ms-duplex="el.price"/>
                            </div>
                            <div class="col-sm-4">
                                <input type="checkbox" ms-duplex-checked="el.status"/>
                                <a class="reduce-icon fr" ms-click="$remove">-</a>
                            </div>
                        </div>
                        <a class="btn btn-primary add-price-type" ms-click="addPriceType('edit')">+新增价格类型</a>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info btn-hand-make-order" type="button" ms-click="saveEditPayItem" data-url="<c:url value="/personality/info/updateConsumerInfo.json"/>">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="deletePayItems">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">删除消费项目</h4>
                    </div>
                    <input type="hidden" id="data-url-id" value="">
                    <div class="modal-body">
                        您确定要删除该消费项目吗？
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info btn-hand-make-order  btn-hand-make-order-delete" type="button">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div></div>
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/js/avalon.js'/>"></script>
<script src="<c:url value='/js/other-pay-manage.js'/>"></script>
<%--<script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>

--%>
<script >
    $('.btn-hand-make-order-delete').bind("click",function(){
        var url = $("#data-url-id").val();
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if(data.status==200){
                    window.location.href='/personality/info/otherConsumer';
                }else{
                    layer.msg(data.message);
                }

            },
            error: function () {
                layer.msg('系统错误');
            }
        })


    });

    $('.consumer-delete').bind("click",function(){
        var url = $(this).attr("data-url");
        $("#data-url-id").val(url);

    })
</script>
</body>
</html>
