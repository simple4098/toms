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
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<title>订单管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/ace.min.css">
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <form class="form-page" name="form-page" id="form-page" action="<c:url value="/order/find_orders"/>" method="post">
        <input type="hidden" class="pageId" id="pageId" name="page"/>
        <input type="hidden" name="searchType" value="${order.searchType}"/>
        <input type="hidden" name="beginDate" value="${order.beginDate}"/>
        <input type="hidden" name="endDate" value="${order.endDate}"/>
    </form>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <h4 class="smaller blue"><a href="<c:url value="/order/find_non_orders"/>">待处理订单</a> <a
                            href="<c:url value="/order/find_orders"/> ">已处理订单</a></h4>

                    <form class="search-form" name="search-form" action="<c:url value="/order/find_orders"/>"
                          method="post">
                        <div>
                            日期选择：
                            <select name="searchType" class="search-type">
                                <option value="">请选择</option>
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
                            <input class="begin-date" type="text" placeholder="开始时间" name="beginDate"
                                   value="${order.beginDate}"/>
                            至 <input class="end-date" type="text"
                                                                        value="${order.endDate}" name="endDate"
                                                                        placeholder="截止时间"/>
                            <button type="submit" class="btn-info btn-search">查询</button>
                        </div>
                    </form>
                    <%-- <div class="table-header">
                         所有筛选条件下，共有 <span
                             style="color: red;font-size: x-large;">${orderPrice == null ? 0 : orderPrice.acceptOrder}</span>
                         个已接受订单，总价共 <span
                             style="color: red;font-size: x-large;">${orderPrice == null ? 0 : orderPrice.allTotalPrice}</span>
                         元，
                         预付金额共 <span
                             style="color: red;font-size: x-large;">${orderPrice == null ? 0 : orderPrice.allPrePrice}</span>
                         元，成本价共 <span
                             style="color: red;font-size: x-large;">${orderPrice == null ? 0 : orderPrice.allCostPrice}</span>
                         元，
                         OTA佣金共 <span
                             style="color: red;font-size: x-large;">${orderPrice == null ? 0 : orderPrice.allPayPrice}</span>
                         元
                     </div>--%>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>
                                    <select name="channelSource">
                                        <option value="">渠道来源</option>
                                        <c:if test="${not empty orderSource}">
                                            <c:forEach items="${orderSource}" var="sor">
                                                <option value="${sor.channelSource}">${sor.channelSource.text}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </th>
                                <th>渠道订单号</th>
                                <th>
                                    <select name="orderStatus">
                                        <option value="">订单状态</option>
                                        <option value="CONFIM_AND_ORDER">已确认并下单</option>
                                        <option value="CONFIM_NO_ORDER">已确认但不下单</option>
                                        <option value="REFUSE">已拒绝</option>
                                        <option value="HAND_REFUSE">手动拒绝</option>
                                        <option value="HAND_ORDER">手动下单</option>
                                    </select>
                                </th>

                                <th>
                                    客栈名称
                                </th>
                                <th>客人姓名</th>
                                <th>房型</th>
                                <th>房间数</th>
                                <th class="hidden-240">住离日期</th>
                                <th>总价/预付金额</th>
                                <th>成本价</th>
                                <th>OTA佣金</th>
                                <th>下单时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody style="font-size: 14px;">
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>
                                        <td>
                                                ${d.channelSource.text}
                                        </td>
                                        <td>${d.channelOrderCode}</td>
                                        <td>${d.orderStatus.text}</td>
                                        <td>${d.innName}</td>
                                        <td>${d.guestName}</td>
                                        <td>${d.roomTypeName}</td>
                                        <td>${d.homeAmount}</td>
                                        <td class="hidden-240"><fmt:formatDate value="${d.liveTime}"
                                                                               pattern="yyyy-MM-dd"/>/<fmt:formatDate
                                                value="${d.leaveTime}" pattern="yyyy-MM-dd"/></td>
                                        <td>${d.totalPrice}/${d.prepayPrice}</td>
                                        <td>${d.costPrice}</td>
                                        <td>${d.OTAPrice}</td>
                                        <td><fmt:formatDate value="${d.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
            <div class="container">
                <div class="text-center">
                    <ul class="pagination">

                        <li <c:if test="${page.page==1}">class="disabled"</c:if>>
                            <a <c:if test="${page.page!=1}">onclick="page(${page.page-1})"</c:if>>
                                <i class="icon-double-angle-left"></i>
                            </a>
                        </li>

                        <c:forEach begin="1" end="${page.pageCount}" step="1" varStatus="vs" var="p">
                            <c:if test="${vs.count<11}">
                                <li <c:if test="${page.page==p}">class="active"</c:if>>
                                    <a onclick="page(${p})">${p}</a>
                                </li>
                            </c:if>
                            <c:if test="${vs.count==10}">
                                <li>
                                    <a>...</a>
                                </li>
                            </c:if>
                            <c:if test="${vs.count >10}">
                                <c:if test="${vs.count==page.pageCount}">
                                    <li <c:if test="${page.page==p}">class="active"</c:if>>
                                        <a onclick="page(${p})">${p}</a>
                                    </li>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        <c:if test="${page.page!=page.pageCount}">
                            <li>
                                <a onclick="page(${page.page+1})">
                                    <i class="icon-double-angle-right"></i>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
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
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">订单详细信息展示</h4>
            </div>
            <div class="modal-body">
                <div>
                    <label class="order-status"></label><br/>
                    <label class="inn-name"></label><br/>
                    <label class="guest-name"></label><br/>
                    <label class="guest-mobile"></label><br/>
                    <label class="room-type"></label><br/>
                    <table border="1">
                        <thead>房价：</thead>
                        <tbody>
                        <tr class="daily-info-time"></tr>
                        <tr class="daily-price"></tr>
                        </tbody>
                    </table>
                    <br/>
                    <label class="order-total">订单总额：</label><br/>
                    <label class="order-pre">预付金额：</label><br/>
                    <label>操作人：系统</label><br/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/js/order.js"></script>


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