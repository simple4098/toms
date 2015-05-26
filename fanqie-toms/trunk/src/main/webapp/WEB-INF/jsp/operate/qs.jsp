<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/assets/css/pages.css">
</head>
<body>
<div class="container">
  <div>
    <select class="selectpicker">
      <option>Mustard</option>
      <option>Ketchup</option>
      <option>Relish</option>
    </select>
  </div>
  <div class="data-count-container">
    <div class="row data-count-box">
      <div class="col-md-3">
        <p>营业收入</p>
        <p class="text-center color-green"><span>${operateTrend.totalIncome}</span>元</p>
      </div>
      <div class="col-md-3">
        <p>实住间夜数</p>
        <p class="text-center color-green"><span>${operateTrend.realLiveNum}</span>间夜</p>
        <p class="text-center color-gray">总数${operateTrend.totalRoomNum}间夜；空置${operateTrend.emptyRoomNum}间夜</p>
      </div>
      <div class="col-md-3">
        <p>入住率</p>
        <p class="text-center color-green"><span>${operateTrend.livePercent*100}</span>%</p>
      </div>
      <div class="col-md-3">
        <p>间夜均价</p>
        <p class="text-center color-green"><span>400</span>元</p>
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

<%--  <script type="text/javascript">

    $.ajax({
      url: 'http://localhost:8083/api/toms/order.json',
      type: 'post',
      dataType: 'json',
      /*data: name + '=' + value + '&id=${data.id}',*/
      success: function (data) {
        alert(data);

      },
      error: function (data) {
        alert("error"+data);
      }
    });

  </script>--%>
</body>
</html>
