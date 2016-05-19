<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/6
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fs"%>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>订单管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/ace.min.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/css/select2.min.css"/>
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
</head>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <input id="findOrders" type="hidden" data-url="<c:url value="/order/find_orders"/>"/>
    <form class="form-page" name="form-page" id="form-page" action="<c:url value="/order/find_orders"/>" method="post">
        <input type="hidden" class="pageId" id="pageId" name="page"/>
        <input type="hidden" name="searchType" value="${order.searchType}"/>
        <input type="hidden" name="beginDate" value="${order.beginDate}"/>
        <input type="hidden" name="endDate" value="${order.endDate}"/>
        <input type="hidden" name="channelSource" class="channel-source-text" value="${order.channelSource}"/>
        <input type="hidden" name="selectStatusString" class="order-status-text" value="${selectStatusString}"/>
        <input type="hidden" name="channelOrderCode" class="channel-order-code" value="${order.channelOrderCode}"/>
        <textarea style="display:none" cols="20" rows="5" name="operatorsJson"  class="operatorsJson-form">${operatorsJson}</textarea>
        <input type="hidden" name="selectedOperators"  class="selectedOperators-form" value="${selectedOperators}"/>
       	<input type="hidden" name="orderInnName" class="innName-form" value="${order.orderInnName }"/>
    </form>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="tabbable">
                        <ul class="nav nav-tabs" id="myTab">
                            <li>
                                <a href="<c:url value="/order/find_non_orders"/>">
                                    <span class="badge badge-danger">*</span>
                                    待处理--未确认订单
                                </a>
                            </li>

                            <li>
                                <a href="<c:url value="/order/find_pay_back_orders"/>">
                                    <span class="badge badge-danger">*</span>
                                    待处理--申请退款订单
                                </a>
                            </li>

                            <li class="active">
                                <a href="<c:url value="/order/find_orders"/>">
                                    已处理订单
                                </a>
                            </li>
                        </ul>
                    </div>
                    <form class="search-form" name="search-form" action="<c:url value="/order/find_orders"/>"
                          method="post">
                        <div style="margin-top:10px">
                            日期选择：
                            <select name="searchType" class="search-type">
                                <option
                                        <c:if test="${order.searchType == 'order_time'}">selected</c:if>
                                        value="order_time">下单日期
                                </option>
                                <option
                                        <c:if test="${order.searchType == 'live_time'}">selected</c:if>
                                        value="live_time">入住日期
                                </option>
                                <option
                                        <c:if test="${order.searchType == 'leave_time'}">selected</c:if>
                                        value="leave_time">离店日期
                                </option>
                            </select>
                            <input id="startDate" class="begin-date" type="text" placeholder="开始时间" name="beginDate"
                                   value="${order.beginDate}"/>
                            至 <input id="endDate" class="end-date" type="text"
                                     value="${order.endDate}" name="endDate"
                                     placeholder="截止时间"/>
                            关键字：<input type="text" value="${order.channelOrderCode}" class="keyword"
                                       name="channelOrderCode"
                                       placeholder="渠道订单号  手机号" id=""/>
                        </div>
                        <div class="query-list">
                            <div>
                                渠道来源选择：
                                <select name="channelSource" class="channel-source">
                                    <option value="">渠道来源</option>

                                    <c:if test="${not empty orderSource}">
                                        <c:forEach items="${orderSource}" var="sor">
                                            <option
                                                    <c:if test="${sor.channelSource == order.channelSource}">selected</c:if>
                                                    value="${sor.channelSource}">${sor.channelSource.text}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <div>
                                酒店选择：
                                <select class="select-hotel" name="orderInnName">
                                    <option value="">请选择酒店</option>
                                    <c:if test="${not empty inns}">
                                        <c:forEach items="${inns}" var="inn">
                                            <option
                                                    <c:if test="${inn.innName == order.orderInnName}">selected</c:if>
                                                    value="${inn.innName}">${inn.innName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                             <div class="select-operator">
                         操作人选择：        
                                 <input type="text" id="selectOperator" name="selectedOperators" value="${selectedOperators}" autocomplete="off">
                                 <ul id="operatorList">
                                 <c:if test="${not empty operators }">
	                                 	<c:forEach items="${operators}" var="op">
	                                 		<li><label><input type="checkbox" <c:if test="${op.selected == true}"> checked</c:if> name="name"><span>${op.userName }</span><input type="hidden" name="userid" value="${op.id}"></label></li>
	                                 		
	                                 	</c:forEach>
	                                 	
                                 </c:if>
                                 	<%-- <li><input type="text" style="display:none" id="operatorsJson" name="operatorsJson" value="${operatorsJson}"/></li> --%>
                                 	<li><textarea style="display:none" cols="20" rows="5" id="operatorsJson" name="operatorsJson" >${operatorsJson}</textarea></li>
                                     <li id="enterOperators"><label class="enteroperator"><a>确认</a></label></li>
                                 </ul>
                             </div>
                             <div class="select-order-status">
                                 订单状态选择：
                                 <input type="text" id="orderStatus" name="selectStatusString" value="${selectStatusString}" autocomplete="off">
                                 <ul id="orderStatusList">
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'自动接受')}"> checked</c:if> name="status-name"><span>自动接受</span></label></li>
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'人工确认并下单')}"> checked</c:if> name="status-name"><span>人工确认并下单</span></label></li>
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'人工确认但不下单')}"> checked</c:if> name="status-name"><span>人工确认但不下单</span></label></li>
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'自动拒绝')}"> checked</c:if> name="status-name"><span>自动拒绝</span></label></li>
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'人工拒绝')}"> checked</c:if> name="status-name"><span>人工拒绝</span></label></li>
                                 	<li><label><input type="checkbox" <c:if test="${fs:contains(selectStatusString,'已取消')}"> checked</c:if> name="status-name"><span>已取消</span></label></li>
                                 <%-- <c:if test="${not empty orderStatusList }">
	                                 	<c:forEach items="${orderStatusList}" var="order">
	                                 		<li><label><input type="checkbox" <c:if test="${order.selected == true}"> checked</c:if> name="name"><span>${order.userName }</span><input type="hidden" name="userid" value="${op.id}"></label></li>
	                                 		
	                                 	</c:forEach>
	                                 	
                                 </c:if> --%>
                                 	<%-- <li><input type="text" style="display:none" id="operatorsJson" name="operatorsJson" value="${operatorsJson}"/></li> --%>
                                     <li id="enterOrderStatus"><label class="enteroperator"><a>确认</a></label></li>
                                 </ul>
                             </div>
                        <div class="query-btn">
                            <button type="submit" class="btn-info btn-search">查询</button>
                            <button type="button" style="float: right" class="btn-success btn-export-form">导出订单</button>
                        </div>
                    </form>
                    <form id="export-order-form" action="<c:url value="/order/order_export"/>" target="_blank"
                          method="post">
                        <input type="hidden" name="searchType" class="search-type-form">
                        <input type="hidden" name="channelOrderCode" class="channel-order-code-form">
                        <input type="hidden" name="channelSource" class="channel-source-form">
                        <!-- <input type="hidden" name="orderStatus" class="order-status-form"> -->
                        <input type="hidden" name="beginDate" class="begin-date-form">
                        <input type="hidden" name="endDate" class="end-date-form">
                        <input type="hidden" name="operatorsJson" class="operatorsJson-form"/>
                        <input type="hidden" name="selectedOperators"  class="selectedOperators-form" >
                        <input type="hidden" name="selectStatusString"  class="selectStatusString-form" >
                        <input type="hidden" name="orderInnName" class="innName-form"/>
                    </form>
                   </div>
                    <div class="form-group">
                        <h3>营业汇总</h3>
                        <div>
                            <ul class="yingyehuizong">
                                <li>
                                    <dl>
                                        <dd>间夜数</dd>
                                        <dd>${count.orderNightNumber}</dd>
                                    </dl>
                                </li>
                                <li>
                                    <dl>
                                        <dd>总营业额</dd>
                                        <dd>${count.totalPrice}</dd>
                                    </dl>
                                </li>
                                <li>
                                    <dl>
                                        <dd>房费成本</dd>
                                        <dd>${count.totalCostPrice}</dd>
                                    </dl>
                                </li>
                                <c:if test="${fs:length(count.otherConsumer)>0}">
                                	 <li>
                                    <dl>
                                        <dd>其它消费项目</dd>
                                        <dd>
                                            <ul class="other-items">
                                                <li>
                                                    <dl>
                                                        <dd>&nbsp;</dd>
                                                        <dd>成本</dd>
                                                        <dd>数量</dd>
                                                    </dl>
                                                </li>
                                                <c:forEach items="${count.otherConsumer}" var="other">
                                                <li>
                                                    <dl>
                                                        <c:if test="${empty other.consumerProjectName}">
                                                        <dd>&nbsp;</dd>
                                                        </c:if>
                                                        <dd>${other.consumerProjectName}</dd>
                                                        <dd>${other.totalCost}</dd>
                                                        <dd>${other.nums}</dd>
                                                    </dl>
                                                </li>
                                                </c:forEach>
                                                
                                            </ul>
                                        </dd>
                                    </dl>
                                </li>
                                </c:if>
                               
                                <li>
                                    <dl>
                                        <dd>利</dd>
                                        <dd>${count.profit}</dd>
                                    </dl>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>
                                    渠道来源
                                </th>
                                <th>渠道订单号</th>
                                <th>
                                	订单状态
                                    <%-- <select name="orderStatus" class="order-status">
                                        <option selected value="">订单状态</option>
                                        <option
                                                <c:if test="${order.orderStatus == 'ACCEPT'}">selected</c:if>
                                                value="ACCEPT">自动接收
                                        </option>
                                        <option
                                                <c:if test="${order.orderStatus == 'CONFIM_AND_ORDER'}">selected</c:if>
                                                value="CONFIM_AND_ORDER">人工确认并下单
                                        </option>
                                        <option
                                                <c:if test="${order.orderStatus == 'CONFIM_NO_ORDER'}">selected</c:if>
                                                value="CONFIM_NO_ORDER">人工确认但不下单
                                        </option>
                                        <option
                                                <c:if test="${order.orderStatus == 'REFUSE'}">selected</c:if>
                                                value="REFUSE">自动拒绝
                                        </option>
                                        <option
                                                <c:if test="${order.orderStatus == 'HAND_REFUSE'}">selected</c:if>
                                                value="HAND_REFUSE">人工拒绝
                                        </option>
                                        <option
                                                <c:if test="${order.orderStatus == 'CANCEL_ORDER'}">selected</c:if>
                                                value="CANCEL_ORDER">已取消
                                        </option>
                                    </select> --%>
                                </th>
                                <th>付款状态</th>
                                <th>
                                    客栈名称
                                </th>
                                <th>客人姓名<br>手机号</th>
                                <th>房型</th>
                                <th>房间数</th>
                                <th class="hidden-240">住离日期</th>
                                <th>总价/预付金额</th>
                                <th>成本</th>
                                <th>下单时间</th>
                                <th>操作人</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody style="font-size: 14px;">
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>
                                                ${d.channelSource.text}
                                                    <c:if test="${d.channelSource == 'FC'}">(${d.partnerCode})</c:if>
                                        </td>
                                        <td>${d.channelOrderCode}</td>
                                        <td>${d.orderStatus.text}</td>
                                        <td>${d.feeStatus.text}</td>
                                        <td>${d.innName}</td>
                                        <td>${d.guestName}<br>${d.guestMobile}</td>
                                        <c:if test="${d.channelSource != 'HAND_ORDER'}">
                                        <td>${d.roomTypeName}</td>
                                        <td>${d.homeAmount}</td>
                                        </c:if>
                                        <c:if test="${d.channelSource == 'HAND_ORDER'}">
                                            <td>
                                                <c:if test="${not empty d.dailyInfoses}">
                                                    <c:forEach items="${d.dailyInfoses}" var="dd">
                                                        ${dd.roomTypeName}<br>
                                                    </c:forEach>
                                                </c:if>
                                            </td>
                                            <td><c:if test="${not empty d.dailyInfoses}">
                                                <c:forEach items="${d.dailyInfoses}" var="dd">
                                                    ${dd.roomTypeNums}<br>
                                                </c:forEach>
                                            </c:if>
                                            </td>
                                        </c:if>
                                        <td class="hidden-240"><fmt:formatDate value="${d.liveTime}"
                                                                               pattern="yyyy-MM-dd"/>/<fmt:formatDate
                                                value="${d.leaveTime}" pattern="yyyy-MM-dd"/></td>
                                        <td>
                                            <c:if test="${d.channelSource =='HAND_ORDER'}">
                                                ${d.prepayPrice}
                                            </c:if>
                                            <c:if test="${d.channelSource != 'HAND_ORDER'}">
                                                ${d.totalPrice}
                                            </c:if>
                                        /${d.prepayPrice}</td>
                                        <td>${d.costPrice}</td>
                                        <td><fmt:formatDate value="${d.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><c:if test="${empty d.operator}">系统</c:if>${d.operator}</td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <button class="btn-order" type="button"
                                                        data-url="<c:url value="/order/find_order.json?id=${d.id}"/>"
                                                        data-toggle="modal" data-target="#myModal">
                                                    <i class="icon-pencil bigger-130"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${not empty data && page.pageCount>1}">
            <!-- PAGE CONTENT ENDS -->
            <toms:page linkUrl="/order/find_orders" pagerDecorator="${pageDecorator}"/>
        </c:if>
        <c:if test="${empty data}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
    </div>
    <!-- /.col -->

</div>
<!-- /.row -->
<!-- Modal -->
<%--操作弹出层--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 710px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close close-btn" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">订单详细信息展示</h4>
            </div>
            <input type="hidden" class="cancel-hand_order-url" value="<c:url value="/order/cancel_hand_order.json"/>"/>
            <input type="hidden" class="cancel-order-id" name="orderId" value=""/>

            <div class="modal-body">
                <div>
                    <div class="form-group" style="border-bottom: 1px solid #ccc">
                        <label class="order-status col-sm-6"></label>
                        <label class="inn-name col-sm-6"></label>
                        <label class="guest-name col-sm-6"></label>
                        <label class="guest-mobile col-sm-6"></label>
                    </div>
                    <h4 style="margin-bottom:10px">预订房型及单价</h4>
                    <div style="border-bottom:1px solid #ccc" id="roomTypesInfo" class="form-group"></div>
                    <div class="form-group" style="border-bottom:1px solid #ccc" id="orderOtherPriceList">
                        <h4 style="margin-bottom:10px">其它消费项目</h4>
                    </div>
                    <div>
                        <ul class='room-types-info'>
                            <li>总成本</li>
                            <li>
                                <dl id="otherTotalCost">
                                    <dd>房费总成本:<span id="costPrice"></span></dd>
                                </dl>
                            </li>
                        </ul>
                    </div>
                    <div class="form-group">
                        <label>订单总价/预付金额:<span id="orderPrice"></span></label><br>
                        <label>利润：<span id="profit"></span></label><br>
                        <label>操作人：<span id="operator"></span></label><br/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" style="float: left" class="btn btn-success btn-cancel-order">取消订单</button>
                <button type="button" class="btn btn-default close-btn" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</html>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/js/order.js"></script>
<script src="<c:url value='/js/select2.full.js'/>"></script>
<script>
    $(document).ready(function () {
        $(".select-hotel").select2();
    });
</script>


<script>

    $.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '<上月',
        nextText: '下月>',
        currentText: '今天',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月',
            '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一', '二', '三', '四', '五', '六',
            '七', '八', '九', '十', '十一', '十二'],
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '年'
    };
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    $('.begin-date,.end-date').datepicker({
        showOtherMonths: true,
        selectOtherMonths: false,
    });


</script>
