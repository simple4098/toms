
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>客栈特殊价设置</title>
    <link href="<c:url value='/assets/css/pages.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/assets/css/fang_price_detail.css'/>" rel="stylesheet"/>

</head>
<div class="page-content">

    <div class="row" style=" height: 197px; ">
        <div class="col-xs-12">
            <h3 class="header smaller lighter blue">${bangInn.innName}</h3>
            <div class="inn-rooms">
                  <p>成：成本价（代销商最终需要同番茄来了结算的金额）  &nbsp; &nbsp; &nbsp; 特殊价格策略:<span class="span-green-ba">-10</span> </p>
                  <p>售：零售价（代销商在卖房官网设置的最终展示的卖价） &nbsp; &nbsp; &nbsp;<span class="span-green">注：最终以特殊价格策略处理后的价格同步到卖房网站</span></p>
            </div>
        </div>
        <button class="button white-f"   account_id="${bangInn.accountId}" inn_id="${bangInn.innId}" ota_info_id="${otaInfoId}" >修改价格</button>

    </div>
    <input type="hidden" inn_id="${bangInn.innId}" ota_info_id="${otaInfoId}" id="fangPriceDetailId"  value="<c:url value="/distribution/fangPriceDetail"/>"/>
    <div class="room-status-box" style="width:1024px;" id="roomTypeContainerId">
    <c:set value="${roomType.list}" var="list"/>
    <c:if test="${not empty roomType.list && not empty roomType.roomDates}">
        <div class="table-left">
            <table class="table table-bordered">
                <tr class="success">
                    <th colspan="2">
                        <span id="prevM">&lt;</span>
                        <!-- <form> -->
                        <input readonly class="date-input-2" type="text" value="${paramDto.startDate}" id="from_datepicker">
                        <input style="display: none" readonly value="${paramDto.endDate}" type="text" id="to_datepicker">
                        <!-- </form> -->
                        <span id="nextM">&gt;</span>
                    </th>
                </tr>
                <%--<tr class="active"><td colspan="2">房型</td></tr>--%>
                <c:forEach items="${roomType.list}" var="v">
                    <tr class="active"><td style="height: 67px;">${v.roomTypeName}</td></tr>
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
               <%-- <tr>
                    <c:forEach  begin="1" step="1" end="${roomType.roomDates.size()}">
                        <td>成本/售价</td>
                    </c:forEach>
                </tr>--%>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="v">
                    <tr>
                        <c:forEach items="${v.roomDetail}" var="vv">
                            <td><c:if test="${!(empty vv.priceValue)}"><span class="span-green-td">${vv.priceValue}</span></c:if><div class="price-td prcie-c">成:${ vv.costPrice}</div><div class="price-td">售:${vv.roomPrice}</div></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <c:if test="${empty roomType.list || empty roomType.roomDates}">
        <div class="alert alert-danger center">
            没有房型数据,请选择分类/客栈查询房态房量
        </div>
    </c:if>
        </div>
</div>
<%--<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>--%>
<%--<script src="<c:url value='/assets/layer/layer.js'/>"></script>--%>
<%--<script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>--%>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<%--<script src="<c:url value='/assets/js/tomato.min.js'/>"></script>--%>

<script src="<c:url value='/assets/js/fang-price.js'/>"></script>
<script type="text/javascript">
    $(".white-f").bind("click",function(){
        var _this = $(this);
        var innId = _this.attr("inn_id");
        var otaInfoId = _this.attr("ota_info_id");
        var url = '<c:url value="/distribution/addFangPrice"/>?innId='+innId+'&otaInfoId='+otaInfoId;
        window.location.href = url;
    })
</script>



