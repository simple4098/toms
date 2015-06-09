<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
  <title>运营趋势</title>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>

<div class="container">
  <div class="clearfix select-area">
    <div class="pull-right">
      <form id="qsId" method="POST">
        <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
        <input type="hidden" class="operate-url" data-url="<c:url value="/operate/qsDetail.json"/>">
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
        <select class="form-control " id="kz_item" name="innId"></select>
        <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">搜索
          <i class="icon-search icon-on-right bigger-110"></i>
        </button>
      </form>
    </div>
  </div>
  <div class="data-count-container">
    <div class="row data-count-box">
      <div class="col-md-3">
        <p>营业收入</p>
        <p class="text-center color-green"><span id="totalIncome">0</span>元</p>
      </div>
      <div class="col-md-3">
        <p>实住间夜数</p>
        <p class="text-center color-green"><span id="realLiveNum">0</span>间夜</p>
        <p class="text-center color-gray" id="emptyAndTotalRoom"></p>
      </div>
      <div class="col-md-3">
        <p>入住率</p>
        <p class="text-center color-green"><span id="livePercent">0</span>%</p>
      </div>
      <div class="col-md-3">
        <p>间夜均价</p>
        <p class="text-center color-green"><span id="avgId">0</span>元</p>
      </div>
    </div>
  </div>
  <div class="data-map-container">
    <ul class="nav nav-tabs" role="tablist" id="myTab">
      <li role="presentation" class="active"><a href="#tab_yingYeShouRu" aria-controls="tab_yingYeShouRu" role="tab" data-toggle="tab">营业收入</a></li>
      <li role="presentation"><a href="#tab_jianYeShu" aria-controls="tab_jianYeShu" role="tab" data-toggle="tab">间夜数</a></li>
      <li role="presentation"><a href="#tab_ruZhuLv" aria-controls="tab_ruZhuLv" role="tab" data-toggle="tab">入住率</a></li>
      <li role="presentation"><a href="#tab_jianYeJunJia" aria-controls="tab_jianYeJunJia" role="tab" data-toggle="tab">间夜均价</a></li>
    </ul>

    <div class="tab-content data-map-box">
      <!-- 营业收入 -->
      <div role="tabpanel" class="tab-pane fade in active" id="tab_yingYeShouRu">
        <div id="yingYeShouRu" class="data-map-area"></div>
      </div>
      <!-- 间夜数 -->
      <div role="tabpanel" class="tab-pane fade" id="tab_jianYeShu">
        <div id="jianYeShu" class="data-map-area"></div>
      </div>
      <!-- 入住率 -->
      <div role="tabpanel" class="tab-pane fade" id="tab_ruZhuLv">
        <div id="ruZhuLv" class="data-map-area"></div>
      </div>
      <!-- 间夜均价 -->
      <div role="tabpanel" class="tab-pane fade" id="tab_jianYeJunJia">
        <div id="jianYeJunJia" class="data-map-area"></div>
      </div>
    </div>
  </div>

</div>

<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<%--<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>--%>
<script src="<%=basePath%>/assets/js/echarts-all.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/dateSelecter.js"></script>
<script src="<%=basePath%>/assets/js/dataCount.js"></script>
</body>
</html>