<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<title>订单管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <form class="form-page" name="form-page" id="form-page" action="<c:url value="/order/find_apply_cancel_orders"/>"
          method="post">
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
                                    <span class="badge badge-danger">${pagination.rowsCount}</span>
                                    待处理--申请退款订单
                                </a>
                            </li>
                            <li class="active">
                                <a href="<c:url value="/order/find_apply_cancel_orders"/>">
                                    <span class="badge badge-danger">*</span>
                                    待处理--信用住申请取消订单
                                </a>
                            </li>

                            <li>
                                <a href="<c:url value="/order/find_orders"/>">
                                    已处理订单
                                </a>
                            </li>
                        </ul>
                    </div>


                    <%--<h4 class="smaller blue"><a href="<c:url value="/order/find_non_orders"/>">待处理--未确认订单</a> <a href="<c:url value="/order/find_pay_back_orders"/>">待处理--申请退款订单</a>  <a--%>
                    <%--href="<c:url value="/order/find_orders"/> ">已处理订单</a>  </h4>--%>
                    <%--<div class="table-header">
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
                    <div class="table-header">
                        <span style="color: white">注：请和淘宝酒店管理后台选择相同的操作执行</span>
                    </div>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>渠道来源</th>
                                <th>渠道订单号</th>
                                <th>订单状态</th>

                                <th>
                                    客栈名称
                                </th>
                                <th>客人姓名</th>
                                <th>房型</th>
                                <th>房间数</th>
                                <th style="width: 180px;">住离日期</th>
                                <th>总价</th>
                                <th>预付金额</th>
                                <th>成本价</th>
                                <th>下单时间</th>
                                <th>查看</th>
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
                                        <td>
                                            <c:if test="${d.channelSource =='HAND_ORDER'}">
                                                ${d.prepayPrice}
                                            </c:if>
                                            <c:if test="${d.channelSource != 'HAND_ORDER'}">
                                                ${d.totalPrice}
                                            </c:if>
                                        </td>
                                        <td>${d.prepayPrice}</td>
                                        <td>${d.costPrice}</td>
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
                                        <td>
                                            <button class="btn btn-danger apply-back-sure"
                                                    data-url="<c:url value="/order/agree_cancel_order.json?id=${d.id}"/>"
                                                    type="button" data-toggle="modal" data-target="#payBackSure">同意取消
                                            </button>
                                            <button class="btn btn-pink refuse-apply-back"
                                                    data-url="<c:url value="/order/refuse_cancel_order.json?id=${d.id}"/>"
                                                    type="button" data-toggle="modal" data-target="#refusePayBack">
                                                拒绝取消
                                            </button>
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
                    <label class="cancel-remark"></label><br/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<%--同意退款--%>
<!-- Modal -->
<div class="modal fade" id="applyBackSure" tabindex="-1" role="dialog" aria-labelledby="myRefuesOrder"
     aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myRefuesOrder">提示信息</h4>
            </div>
            <div class="modal-body">
                <span style="color: red">注：请和淘宝酒店管理后台选择相同的操作执行</span>
                <br/>
                您选择“同意退款”操作,系统将在客栈PMS中取消该订单<br/>
                请根据实际情况选择是否扣款<br/>
                <input type="radio" value='true' name='refundStatus' />扣款
                <input type="radio" value='false' name='refundStatus' style='margin-left:20px;'/>不扣款
            </div>
            <input type="hidden" class="apply-back-sure-url"/>
            <input type="hidden" class="apply-back-sure-refundStatus"/>
            

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success btn-apply-back-sure">确认</button>
            </div>
        </div>
    </div>
</div>
<%--拒绝退款--%>
<!-- Modal -->
<div class="modal fade" id="refusePayBack" tabindex="-1" role="dialog" aria-labelledby="myConfirmNoOrder"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-default" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myConfirmNoOrder">提示信息</h4>
            </div>
            <div class="modal-body">
                <span style="color: red">注：请和淘宝酒店管理后台选择相同的操作执行</span> <br/>
                您选择“拒绝退款”操作，系统将在客栈PMS中保留该订单！
            </div>
            <input type="hidden" class="refuse-apply-back-url"/>
            

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success btn-refuse-apply-back-sure">确认</button>
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath%>/js/order.js"></script>
