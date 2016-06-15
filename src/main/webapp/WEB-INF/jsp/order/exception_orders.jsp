<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<title>异常订单管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <form class="form-page" name="form-page" id="form-page"
          action="<c:url value="/exceptionOrder/find_exceptionOrders"/>"
          method="post">
        <input type="hidden" class="pageId" id="pageId" name="page"/>
    </form>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="tabbable">
                        <ul class="nav nav-tabs" id="myTab">
                        </ul>
                    </div>
                    <div class="table-header">
                        <span style="color: darkorchid">注：请慎重操作</span>
                    </div>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>渠道来源</th>
                                <th>渠道订单号</th>
                                <th>渠道订单状态</th>
                                <th>订单状态</th>
                                <th>付款状态</th>
                                <th>客栈名称</th>
                                <th>房型</th>
                                <th>房间数</th>
                                <th style="width: 180px;">住离日期</th>
                                <th>总价</th>
                                <th>预付金额</th>
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
                                        <td>
                                                ${d.otaOrderStatus.text}
                                        </td>
                                        <td>${d.orderStatus.text}</td>
                                        <td>${d.feeStatus.text}</td>
                                        <td>${d.orderInnName}</td>
                                        <td>${d.orderRoomTypeName}</td>
                                        <td>${d.homeAmount}</td>
                                        <td class="hidden-240"><fmt:formatDate value="${d.liveTime}"
                                                                               pattern="yyyy-MM-dd"/>/<fmt:formatDate
                                                value="${d.leaveTime}" pattern="yyyy-MM-dd"/></td>
                                        <td>
                                            <c:if test="${d.orderSource == 'HAND'}">
                                                ${d.prepayPrice}
                                            </c:if>
                                            <c:if test="${d.orderSource != 'HAND'}">
                                                ${d.totalPrice}
                                            </c:if>
                                        </td>
                                        <td>${d.prepayPrice}</td>
                                        <td><fmt:formatDate value="${d.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>
                                            <button class="btn btn-danger create-order-oms"
                                                    data-url="<c:url value="/order/create_order_oms.json?id=${d.id}"/>"
                                                    type="button" data-toggle="modal" data-target="#createOrderOms">
                                                下单到OMS
                                            </button>
                                            <button class="btn btn-pink cancel-order-oms"
                                                    data-url="<c:url value="/order/cancel_order_oms.json?id=${d.id}"/>"
                                                    type="button" data-toggle="modal" data-target="#cancelOrderOms">
                                                取消订单
                                            </button>
                                            <button class="btn btn-pink close-order"
                                                    data-url="<c:url value="/exceptionOrder/close_order.json?id=${d.id}"/>"
                                                    type="button" data-toggle="modal" data-target="#closeOrder">
                                                关闭订单
                                            </button>
                                            <button class="btn btn-pink oms-order-status"
                                                    data-url="<c:url value="/exceptionOrder/find_oms_order_status.json?id=${d.id}"/>"
                                                    type="button">
                                                oms订单状态
                                            </button>
                                            <button class="btn btn-pink pms-order-status"
                                                    data-url="<c:url value="/exceptionOrder/find_pms_order_status.json?id=${d.id}"/>"
                                                    type="button">
                                                pms订单状态
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
        <%-- <c:if test="${not empty data && page.pageCount>1}">--%>
            <!-- PAGE CONTENT ENDS -->
        <toms:page linkUrl="/exceptionOrder/find_exceptionOrders" pagerDecorator="${pageDecorator}"/>
        <%-- </c:if>--%>
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
<%--下单到oms--%>
<!-- Modal -->
<div class="modal fade" id="createOrderOms" tabindex="-1" role="dialog" aria-labelledby="myRefuesOrder"
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
                您选择“下单到OMS”操作,系统将在客栈PMS中生成该订单
            </div>
            <input type="hidden" class="create-order-oms-url"/>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success btn-create-order-oms">确认</button>
            </div>
        </div>
    </div>
</div>
<%--取消订单同步oms--%>
<!-- Modal -->
<div class="modal fade" id="cancelOrderOms" tabindex="-1" role="dialog" aria-labelledby="myConfirmNoOrder"
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
                您选择“取消订单”操作，系统将在客栈PMS中取消订单！
            </div>
            <input type="hidden" class="cancel-order-oms-url"/>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success btn-cancel-order-oms">确认</button>
            </div>
        </div>
    </div>
</div>
<%--关闭订单--%>
<!-- Modal -->
<div class="modal fade" id="closeOrder" tabindex="-1" role="dialog" aria-labelledby="myCloseOrder"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-default" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myCloseOrder">提示信息</h4>
            </div>
            <div class="modal-body">
                <span style="color: red">注：关闭订单，订单状态保持跟ota渠道一致</span> <br/>
                您选择“关闭订单”操作，订单状态保持跟ota渠道一致！
            </div>
            <input type="hidden" class="close-order-url"/>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success btn-close-order">确认</button>
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>/js/order.js"></script>
