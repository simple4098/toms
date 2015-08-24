<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>房态房量</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">

</head>
<body>
<div >
    <div class="select-area">
        <form class="form-data">
            <input type="hidden" id="dataUrlId" data-url="<c:url value="/oms/ajax/obtRoomType"/>">
            <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
            <select class="form-control" id="kz-tags-r"></select>
            <select class="form-control" id="kz_item-r"></select>
            <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                搜索
                <i class="icon-search icon-on-right bigger-110"></i>
            </button>
        </form>
    </div>
    <div style="margin-left: 94%">
        <button data-toggle="modal" data-target="#hangOrder" class="btn btn-success hand-btn" disabled>手动下单</button>
    </div>
    <c:set value="${roomType.list}" var="list"/>
        <div class="room-status-box" id="roomTypeContainerId">
            <c:if test="${not empty list}">
            <div class="table-left">
                <table class="table table-bordered">
                    <tr class="success">
                        <td colspan="2">
                            <span id="prevM">&lt;</span>
                            <!-- <form> -->
                            <input readonly class="date-input-2" type="text" id="from_datepicker">
                            <input  readonly type="text" id="to_datepicker">
                            <!-- </form> -->
                            <span id="nextM">&gt;</span>
                        </td>
                    </tr>
                    <tr class="active"><td colspan="2">房型</td></tr>
                    <c:forEach items="${roomType.list}" var="v">
                        <tr class="active"><td colspan="2">${v.roomTypeName}</td></tr>
                    </c:forEach>
                </table>
            </div>
            <div class="table-right">
                <table class="table table-bordered table-hover room-status-table">

                    <thead>
                    <tr>
                        <c:forEach items="${roomType.roomDates}" var="vv">
                            <th>${vv}</th>
                        </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach  begin="1" step="1" end="${roomType.roomDates.size()}">
                            <th>剩余</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="v">
                        <tr>
                            <c:forEach items="${v.roomDetail}" var="vv">
                                <td>${vv.roomNum}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:if>
            <c:if test="${empty list}">
                <div class="alert alert-danger center">
                    没有数据,请选择分类/客栈查询房态房量
                </div>
            </c:if>
        </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/room-type.js"></script>
<%--手动下单--%>
<div class="modal fade" id="hangOrder" tabindex="-1" role="dialog" aria-labelledby="hangOrderModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="hangOrderModal">手动下单</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal hand-order-form" id="hand-order-form" role="form">
                    <input type="hidden" class="account-id" name="accountId"/>
                    <input type="hidden" class="tag-id" name="tagId"/>
                    <input type="hidden" class="room-type-name" name="roomTypeName">

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 姓名 </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-1" name="guestName"
                                   value="" placeholder="客人姓名" class="col-xs-10 col-sm-5 guest-name"
                                    />
                            <span class="help-guest-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 手机号 </label>

                        <div class="col-sm-9 ">
                            <input type="text" name="guestMobile" id="form-field-2" placeholder="客人联系方式"
                                   class="col-xs-10 col-sm-5 guest-mobile"/>
                        <span class="help-guest-mobile col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="datepicker"> 入住时间 </label>

                        <div class="col-xs-4">
                            <div class="input-group input-group-sm">
                                <input type="text" name="liveTime" id="datepicker" class="form-control live-time"/>
													<span class="input-group-addon">
														<i class="icon-calendar"></i>
													</span>
                            </div>
                        <span class="help-tel col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="datepicker1"> 离店时间 </label>

                        <div class="col-xs-4">
                            <div class="input-group input-group-sm">
                                <input type="text" name="leaveTime" id="datepicker1" class="form-control leave-time"/>
													<span class="input-group-addon">
														<i class="icon-calendar"></i>
													</span>
                            </div>
                        <span class="help-tel col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="roomTypeId">房 型</label>

                        <div class="col-sm-1">
                            <select name="roomTypeId" class="room-type" id="roomTypeId">
                                <option value="">--请选择--</option>
                            </select>
                        <span class="help-name col-xs-12 col-sm-7">
											</span>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="spinner">房间数</label>

                        <div class="col-sm-1">
                            <input class="home-amount" id="spinner" name="homeAmount" type="text" value="1"/>
                        <span class="help-name col-xs-12 col-sm-7">
											</span>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="totalPrice"> 销售总价 </label>

                        <div class="col-sm-9 ">
                            <input type="text" name="totalPrice" id="totalPrice" placeholder="订单总价"
                                   class="col-xs-10 col-sm-5 total-price"/>
                        <span class="help-guest-mobile col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="comment"> 备注 </label>

                        <div class="col-sm-9 ">
                            <textarea type="text" name="comment" id="comment" placeholder="特殊要求"
                                      class="col-xs-10 col-sm-5 comment"></textarea>
                        <span class="help-guest-mobile col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                </form>

                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn" data-dismiss="modal">
                            <i class="icon-undo bigger-110"></i>
                            取消
                        </button>
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info btn-hand-make-order"
                                data-url="<c:url value="/order/hand_make_order.json"/>" type="button">
                            <i class="icon-ok bigger-110"></i>
                            确认
                        </button>
                    </div>
                </div>
            </div>
            <%--<div class="modal-footer">--%>
            <%--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>--%>
            <%--<button type="button" class="btn btn-primary btn-submit">确认</button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>
<script>
    $("#datepicker").datepicker({
        showOtherMonths: true,
        selectOtherMonths: false,
    });
    $("#datepicker1").datepicker({
        showOtherMonths: true,
        selectOtherMonths: false,
    });
    //spinner
    var spinner = $("#spinner").spinner({
        create: function (event, ui) {
            //add custom classes and icons
            $(this)
                    .next().addClass('btn btn-success btn-homeAmount-add').html('<i class="icon-plus"></i>')
                    .next().addClass('btn btn-danger btn-homeAmount').html('<i class="icon-minus"></i>')

            //larger buttons on touch devices
//            if(ace.click_event == "tap") $(this).closest('.ui-spinner').addClass('ui-spinner-touch');
            $('.btn-homeAmount').attr('disabled', true);
        }

    });
    $('.btn-homeAmount').on('click', function () {
        var value = $('.home-amount').val();
        if (value <= 1) {
            $('.btn-homeAmount').attr('disabled', true);
            $('.btn-homeAmount-add').attr('disabled', false);
        } else {
            $('.btn-homeAmount').attr('disabled', false);
            $('.btn-homeAmount-add').attr('disabled', false);
        }
    });
    $('.btn-homeAmount-add').on('click', function () {
        var value = $('.home-amount').val();
        if (value > 9) {
            $('.btn-homeAmount').attr('disabled', false);
            $('.btn-homeAmount-add').attr('disabled', true);
        } else {
            $('.btn-homeAmount').attr('disabled', false);
            $('.btn-homeAmount-add').attr('disabled', false);

        }
    });
    $('.btn-hand-make-order').on('click', function () {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '<c:url value="/order/hand_make_order.json"/>?guestName=' + $('.guest-name').val() + '&guestMobile=' + $('.guest-mobile').val()
            + '&liveTimeString=' + $('.live-time').val() + '&leaveTimeString=' + $('.leave-time').val() + '&roomTypeId=' + $('.room-type').val() + '&homeAmount=' + $('.home-amount').val()
            + '&payment=' + $('.total-price').val() + '&comment=' + $('.comment').val() + '&accountId=' + $('.account-id').val() + '&tagId=' + $('.tag-id').val() + '&roomTypeName=' + $('.room-type').children('option:selected').text(),
            success: function (data) {
                if (data.status) {
                    layer.alert('提示信息：下单成功', {icon: 6}, function () {
                        window.location.reload();
                    });
                } else {
                    layer.alert('提示信息：下单失败,请检查所有参数是否完整=>' + data.message, {icon: 5}, function () {
                        window.location.reload();
                    });
                }
            }
        })
    });
</script>
</body>
</html>
