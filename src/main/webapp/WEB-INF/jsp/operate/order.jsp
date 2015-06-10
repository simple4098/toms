<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>订单来源分析</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>

<div class="container">
    <div class="clearfix select-area">
        <div class="pull-right">
            <form id="orderId" method="POST">
                <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">

                <input type="hidden" class="orderD-url" data-url="<c:url value="/operate/ajax/orderDetail.json"/>"/>
                <select class="form-control" id="fast_select">
                    <option>快捷日期</option>
                    <option>昨日</option>
                    <option>本月</option>
                    <option>近7天</option>
                    <option>近30天</option>
                </select>
                <input readonly class="date-input" name="startDate" type="text" id="from_datepicker" placeholder="请选择开始日期">
                <span>至</span>
                <input readonly class="date-input" name="endDate" type="text" id="to_datepicker" placeholder="请选择结束日期">
                <!-- <select class="selectpicker"> -->
                <select class="form-control" name="tagId" id="kz-tags"></select>
                <select class="form-control" name="innId" id="kz_item"></select>
                <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                    搜索
                    <i class="icon-search icon-on-right bigger-110"></i>
                </button>
            </form>
        </div>
    </div>
    <div class="data-count-container">
        <div class="row data-count-box">
            <div class="col-md-4">
                <p>订单总量</p>
                <p class="text-center color-green"><span id="orderNum">0</span></p>
            </div>
            <div class="col-md-4">
                <p>实住间夜数</p>
                <p class="text-center color-green"><span id="realLiveNum">0</span>间夜</p>
                <p class="text-center color-gray" id="emptyAndTotalRoom">总数0间夜；空置0间夜</p>
            </div>

            <div class="col-md-4">
                <p>房费收入</p>
                <p class="text-center color-green"><span id="incomeId">0</span>元</p>
            </div>
        </div>
    </div>
    <div class="data-map-container">
        <div class="row map-channel-box">
            <div class="col-md-6">
                <div id="map_channel"></div>
            </div>
            <div class="col-md-6">
                <table class="table table-hover">
                    <tbody id="orderSourceId">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/js/echarts-all.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/dateSelecter.js"></script>
<script src="<%=basePath%>/assets/js/map_channel.js"></script>

</body>
</html>