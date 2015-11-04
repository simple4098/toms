
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
        <button class="button white-f fan-hui" data-url="<c:url value='/distribution/fangPriceDetail'/>?innId=${bangInn.innId}&otaInfoId=${otaInfoId}" > << 取消并返回 </button>
        <button class="button white-f mai-fang" data-url-fan-hui="<c:url value='/distribution/fangPriceDetail'/>?innId=${bangInn.innId}&otaInfoId=${otaInfoId}" data-url="<c:url value='/distribution/ajax/sync.json'/>" account_id="${bangInn.accountId}" inn_id="${bangInn.innId}" ota_info_id="${otaInfoId}" >保存并同步推送价格</button>
    </div>
    <div class="room-status-box" style="width:1024px;" id="roomTypeContainerId">
    <c:if test="${not empty list }">
        <div class="table-left" style="overflow-x: hidden;">
            <table class="table table-bordered">
                <tr class="active">
                    <td>房型名称</td>
                </tr>
                <c:forEach items="${list}" var="v">
                    <tr>
                        <td >${v.roomTypeName}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="table-right"  style="overflow-x: hidden;">
            <table class="table table-bordered table-hover room-status-table" style="width:1024px;">
               <tr>
                   <td><span class="ts-set" >特殊价格策略设置</span>
                   <span class="add-head">（在客栈卖价基础上加、减价；输入框中只能输入数字，根据 “+，-” 来区分价格增加、减少）</span>
                   </td>
               </tr>
                <c:forEach items="${list}" var="v">
                    <tr>
                        <td class="room-fc">
                            <input roomtypeId="${v.roomTypeId}" roomTypeName="${v.roomTypeName}"  name="startDateStr"  value="" type="text" class="from_datepicker" placeholder="请选择开始日期">
                           至
                            <input   name="endDateStr"  value=""  type="text" class="to_datepicker" placeholder="请选择结束日期">
                            在卖价基础上 <input type="text" name="roomValue" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
    <c:if test="${empty list}">
        <div class="alert alert-danger center">
            没有房型数据,请选择分类/客栈查询房态房量
        </div>
    </c:if>
        </div>
</div>
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script src="<c:url value='/assets/js/dateSelecter_room_price.js'/>"></script>
<script src="<c:url value='/js/add-price.js'/>"></script>
<script type="text/javascript">
    $(".fan-hui").bind("click",function(){
        var _this = $(this);
        window.location.href = _this.attr("data-url");
    })
</script>

