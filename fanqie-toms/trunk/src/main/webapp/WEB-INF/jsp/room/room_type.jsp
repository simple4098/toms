<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <meta charset="utf-8">
    <title>房态房量</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">--%>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/select2.min.css"/>
    <link href="<c:url value='/assets/css/pages.css'/>" rel="stylesheet"/>
    <script src="<c:url value='/js/select2.full.js'/>"></script>
</head>
<div >
    <div class="select-area">
        <form class="form-data">
            <input type="hidden" class="room-type-url" id="roomTypeUrl"
                   data-url="<c:url value="/order/find_room_type.json"/>"/>
            <input type="hidden" class="room-num-url" id="roomNumUrl"
                   data-url="<c:url value="/order/find_room_num.json"/>"/>
            <input type="hidden" id="dataUrlId" data-url="<c:url value="/oms/ajax/obtRoomType"/>">
            <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
            <input type="hidden" class="room-type-url" id="manualUrl" data-url="<c:url value="/personality/info/manual.json"/>"/>
            <select class="form-control" id="kz-tags-r"></select>
            <select class="form-control js-example-basic-single" id="kz_item-r"></select>
            <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                搜索
                <i class="icon-search icon-on-right bigger-110"></i>
            </button>
            <input type="radio" class="maiAccount" name="maiAccount" checked value="1"/>卖价
            <input type="radio" class="maiAccount" name="maiAccount" value="0"/>活动价(采用底价模式)
        </form>
    </div>
    <div class="btn-box clearfix">
        <button data-toggle="modal" data-target="#hangOrder" class="btn btn-success hand-btn" disabled id="manualOrder">手动下单</button>
    </div>

    <div class="room-status-box" id="roomTypeContainerId">

    </div>
</div>


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
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> 渠道来源
                        </label>
                        <div class="col-sm-9">
                            <select name="channelSource" id="channelSource">
                                <option value="TAOBAO">淘宝</option>
                                <option value="FC">天下房仓</option>
                                <option value="ZH">众荟</option>
                                <option value="XC">携程</option>
                                <option value="QUNAR">去哪儿</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> 渠道订单号
                        </label>
                        <div class="col-sm-9">
                            <input type="text" id="channelOrderCode" placeholder="渠道订单号" class="ipt"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>客人姓名
                        </label>
                        <div class="col-sm-3">
                            <input type="text" id="guestName" placeholder="请填写客人姓名" class="ipt"/>
                        </div>
                        <label class="col-sm-2 control-label no-padding-right"> <span class="red">*</span>手机号
                        </label>
                        <div class="col-sm-4">
                            <input type="text" id="guestMobile" placeholder="请填写客人联系方式" class="ipt"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>入住时间
                        </label>
                        <div class="col-sm-3">
                            <input type="text"  id="liveTimeString" placeholder="请选择入住日期" class="ipt"/>
                            <p class="check-time"><a id="todayCheckin">今日入住</a><a id="tomarowCheckin">明日入住</a></p>
                        </div>
                        <label class="col-sm-2 control-label no-padding-right"> <span class="red">*</span>离店时间</label>
                        <div class="col-sm-4">
                            <input type="text" id="leaveTimeString" placeholder="请选择离店日期" class="ipt"/>
                            <p class="check-time"><a id="oneNight">住1晚</a><a id="twoNight">住2晚</a><a id="threeNight">住3晚</a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>房型及房间数量
                        </label>
                        <div class="col-sm-9" id="roomOperate">
                            <div class="mgb-10 room-operate room-type-operate">
                                <select class="selectRoomType">
                                    <option>请选择房型</option>
                                </select>
                                <a class="reduce-icon" class="roomTypeNumReduce">-</a>
                                <input type="text" placeholder="房间数量" class="roomNumber" readonly>
                                <a class="plus-icon" class="roomTypeNumPlus">+</a>
                                <a class="remove-room-type">移除房型</a>
                            </div>
                            <div class="mgb-10 add-room-type">
                                <a id="addRoomType" class="btn">新增房型</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="notNeedListIdDev">
                        <label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>其它消费
                        </label>
                        <div class="col-sm-9">
                            <div class="mgb-10 col-sm-12" id="otherList">
                                <%--<label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>门票（全票）</label>
                                <div class="col-sm-3"><input type="text" data-tips="填写消费数量" autocomplete="off" value="" placeholder="填写消费数量" class="ipt"></div>
                                <label class="col-sm-3 control-label no-padding-right"> <span class="red">*</span>门票（全票）</label>
                                <div class="col-sm-3"><input type="text" data-tips="填写消费数量" autocomplete="off" value="" placeholder="填写消费数量" class="ipt"></div>--%>
                            </div>
                            <div class="mgb-10 col-sm-12 room-operate notNeedListId">
                                <div class="col-sm-12 select-other-pay">
                                    <div class="col-sm-6">
                                        <select style="margin-left:45px;" class="notNeedList">
                                            <option>请选择消费项目</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-6">
                                        <a class="reduce-icon">-</a>
                                        <input type="text" placeholder="填写消费数量" id="otherPaynumber" class="other-pay-number">
                                        <a class="plus-icon">+</a>
                                    </div>

                                </div>
                            </div>
                            <div style="margin-top: 15px;margin-left: 80px" id="addOtherPayItem">
                                <a class="btn">新增其它消费项目</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"><span class="red">*</span> 订单总销售额
                        </label>
                        <div class="col-sm-9">
                            <input type="text" id="payment" placeholder="订单总销售额" class="ipt"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> 备注
                        </label>
                        <div class="col-sm-9">
                            <input type="text" id="comment" placeholder="备注" class="ipt"/>
                        </div>
                    </div>
                    <input type="hidden" class="account-id" name="bangInnId"/>
                    <input type="hidden" class="tag-id" name="tagId"/>
                    <input type="hidden" class="type-name" name="roomTypeName"/>
                    <input type="hidden" class="max-num" value=""/>
                    <input type="hidden" class="mai-account" name="maiAccount"/>
                    <input type="hidden" class="room_type_name" name="orderRoomTypeName">

                   <%-- <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-11"> 渠道订单号
                        </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-11" name="channelOrderCode" data-tips="渠道订单号"
                                   autocomplete="off"
                                   value="" placeholder="渠道订单号" class="col-xs-10 col-sm-5 channel-order-code"
                                    />
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 姓名
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-sm-9">
                            <input type="text" id="form-field-1" name="guestName" data-tips="姓名" autocomplete="off"
                                   value="" placeholder="客人姓名" class="col-xs-10 col-sm-5 guest-name"
                                    />
                            <span class="help-guest-name col-xs-12 col-sm-7"></span>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="tel"> 手机号
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-sm-9 ">
                            <input type="text" name="guestMobile" id="tel" placeholder="客人联系方式" data-tips="手机号"
                                   autocomplete="off"
                                   class="col-xs-10 col-sm-5 guest-mobile"/>
                        <span class="help-guest-mobile col-xs-12 col-sm-7">

											</span>
                        </div>
                    </div>

                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="inTime"> 入住时间
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-xs-4">
                            <div class="input-group input-group-sm">
                                <input type="text" name="liveTimeString" id="inTime" data-tips="入住时间" autocomplete="off"
                                       class="form-control live-time"/>
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
                        <label class="col-sm-3 control-label no-padding-right" for="outTime"> 离店时间
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-xs-4">
                            <div class="input-group input-group-sm">
                                <input type="text" name="leaveTimeString" id="outTime" data-tips="离店时间"
                                       autocomplete="off"
                                       class="form-control leave-time"/>
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
                        <label class="col-sm-3 control-label no-padding-right" for="roomType">房 型
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-sm-1">
                            <select name="roomTypeId" class="room-type" id="roomType">
                                <option value="">--请选择--</option>
                            </select>
                        <span class="help-name col-xs-12 col-sm-7">
											</span>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="roomNum">房间数
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-sm-1">
                            <input class="home-amount" id="roomNum" name="homeAmount" data-tips="房间数" type="text"
                                   readonly
                                   value="1"/>
                        <span class="help-name col-xs-12 col-sm-7">
											</span>
                        </div>
                    </div>
                    <div class="space-4"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right " for="totalPrice"> 销售总价
                            <tip style="color: red">*</tip>
                        </label>

                        <div class="col-sm-9 ">
                            <input type="text" name="payment" id="totalPrice" placeholder="订单总价" data-tips="销售总价"
                                   autocomplete="off"
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

                    <div class="space-4"></div>--%>

                </form>
                <div class="tips" style="color: red"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn" data-dismiss="modal">
                            <i class="icon-undo bigger-110"></i>
                            取消
                        </button>
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn btn-info btn-hand-make-order" id="saveManualOrder"
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
<%--<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>--%>
<%--<script src="<c:url value='/assets/layer/layer.js'/>"></script>
<script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>--%>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script src="<c:url value='/assets/js/tomato.min.js'/>"></script>
<script src="<c:url value='/assets/js/room-type.js'/>"></script>
<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script src="<c:url value='/js/manual-order.js'/>"></script>
<script>
    $(document).ready(function () {
        $(".js-example-basic-single").select2();
    });
</script>
