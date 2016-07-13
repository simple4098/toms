<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>自定义渠道</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/news-center.css'/>">
</head>
<body>
<div class="container ms-controller" ms-controller="otherPayManage">
    <div class="qita-xiaofei-guanli">
        <h4 style="position:relative">
            手动下单自定义渠道设置
            <input id="onoffButton" type="button" data-url="<c:url value="/myselfChannel/update_company_channel_status.json"/>"
                   data-status="${status}"
            <c:if test="${!status}"> class="off_button"</c:if>
            <c:if test="${status}"> class="on_button"</c:if> title="点击开启" ms-click="divDisplayIsopenedFun">
            <a class="fr btn btn-primary" onclick="history.go(-1)">返回</a>
        </h4>

        <div class="unopened-content">
            <p class="unopened-con-p">自定义渠道功能开启后，可以添加自定义渠道名，手动下单时可以选择，方便后期和此渠道结算！
            </p>
        </div>
        <div class="notopened-content" ms-css-display="divDisplayIsopened[0]"></div>
        <div class="opened-content" ms-css-display="divDisplayIsopened[1]">
            <p><a class="btn btn-primary" ms-click="addOtherPayItemFun">+新增自定义渠道</a></p>

            <div>
                <ul class="opened-con-ul">
                    <li class="col-sm-2 items-title">
                        <dl>
                            <dd>默认渠道</dd>
                        </dl>
                    </li>
                    <li class="col-sm-10 items-detail">
                        <dl>
                            <dd>淘宝</dd>
                        </dl>
                        <dl>
                            <dd>房仓</dd>
                        </dl>
                        <dl>
                            <dd>众荟</dd>
                        </dl>
                        <dl>
                            <dd>携程</dd>
                        </dl>
                        <dl>
                            <dd>去哪儿</dd>
                        </dl>
                    </li>
                </ul>
                <c:if test="${not empty data}">
                <ul class="opened-con-ul">
                    <li class="col-sm-2 items-title">
                        <dl>
                            <dd>自定义渠道</dd>
                        </dl>
                    </li>
                    <li class="col-sm-10 items-detail">
                        <c:forEach items="${data}" var="d">
                        <dl>
                            <dd>
                                <a href="#" style="margin-left: 5px" class="update-myself-channel-url" data-url="<c:url value="/myselfChannel/update_myself_channel.json?id=${d.id}"/>" data-toggle="modal" data-target="#editPayItems">
                                <i class="icon-cog" style="font-size: 20px" title="编辑"></i></a>
                                <a  href="#" class="delete-myself-channel-url" data-url="<c:url value="/myselfChannel/delete_myself_channel.json?id=${d.id}"/>"  data-toggle="modal" data-target="#deletePayItems">
                                    <i class="icon-remove" style="font-size: 20px" title="删除"></i></a>
                            </dd>
                            <dd>${d.channelName}</dd>
                        </dl>
                        </c:forEach>
                    </li>
                </ul>
                </c:if>
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
                        <h4 class="modal-title">新增自定义渠道</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 渠道名称
                            </label>

                            <div class="col-sm-8">
                                <input type="text" autocomplete="off" value="" maxlength="8"
                                        class="ipt myself-channel-name"/>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info save-myself-channel" type="button"
                                    id="saveMyselfChannel"
                                    data-url="<c:url value="/myselfChannel/create_myself_channel.json"/>">
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
                        <h4 class="modal-title">编辑自定义渠道名称</h4>
                        <input type="hidden" class="update-channel-url" value="">
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-4 control-label no-padding-right"> 渠道名称
                            </label>

                            <div class="col-sm-8">
                                <input type="text" autocomplete="off" value="" maxlength="8"
                                       class="ipt myself-channel-name-update" />
                            </div>
                        </div>
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info update-myself-channel" type="button">
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
                        <h4 class="modal-title">删除自定义渠道名称</h4>
                    </div>
                    <input type="hidden" class="delete-channel-url" id="data-url-id" value="">

                    <div class="modal-body">
                        您确定要删除该渠道名称吗？
                    </div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn" data-dismiss="modal">
                                <i class="icon-undo bigger-110"></i>
                                取消
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-info delete-myself-channel" type="button">
                                <i class="icon-ok bigger-110"></i>
                                确认
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/js/avalon.js'/>"></script>
<script src="<c:url value='/js/other-pay-manage.js'/>"></script>
<script src="<c:url value='/js/myself-channel.js'/>"></script>
<%--<script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>

--%>
<script>
    $('.btn-hand-make-order-delete').bind("click", function () {
        var url = $("#data-url-id").val();
        $.ajax({
            url: url,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if (data.status == 200) {
                    window.location.href = '/personality/info/otherConsumer';
                } else {
                    layer.msg(data.message);
                }

            },
            error: function () {
                layer.msg('系统错误');
            }
        })


    });

    $('.consumer-delete').bind("click", function () {
        var url = $(this).attr("data-url");
        $("#data-url-id").val(url);

    })
</script>
</body>
</html>
