<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>客栈匹配详情</title>
  <link rel="stylesheet" type="text/css" href="/assets/css/normalize.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/jquery-ui-1.10.3.full.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/ace.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/innRelation.css">
</head>

<body>
<div class="container">
  <!-- 客栈匹配 -->
  <div class="col-sm-12">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="lighter smaller">客栈匹配</h4>
      </div>

      <div class="widget-body">
        <div class="widget-main clearfix">
          <div class="col-sm-6 widget-title">番茄来了系统客栈信息</div>
          <div class="col-sm-6 widget-title">匹配天下房仓标准酒店</div>
          <div style="clear: both"></div>
          <hr class="hr-2">
          <div class="clearfix">
            <div class="col-sm-6 widget-con">
              <input type="hidden" id="innId" value="${inn.innId}"/>
              <p>名称：<span>${inn.innName}</span><em><c:if test="${empty otaInnOtaDto}">未匹配</c:if><c:if test="${!empty otaInnOtaDto}">匹配成功</c:if></em></p>
              <p>电话：<span>${inn.mobile}</span></p>
              <p>地址：<span>${omsInn!=null?omsInn.addr:'暂无'}</span></p>
            </div>

              <div class="col-sm-6 widget-con">
                <c:if test="${not empty fcHotel}">

                    <input type="radio" style="display: none" name="fcHotelId" checked value="${fcHotel.hotelId}">
                    <div class="result-box">
                      <p>名称：<span>${fcHotel.hotelName}</span></p>
                      <p>电话：<span>${fcHotel.telephone}</span></p>
                      <p>地址：<span>${fcHotel.hotelAddress}</span></p>
                    </div>

                </c:if>
                <c:if test="${not empty hotel}">
                  <div class="inn-search">
                    <span>搜索可匹配标准酒店</span>
                    <input type="text" id="search-id" placeholder="输入酒店关键字搜索">
                    <button type="button" data-url="<c:url value="/innMatch/ajax/searchInn"/>" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                      搜索
                      <i class="icon-search icon-on-right bigger-110"></i>
                    </button>
                  </div>
                  <div id="contentId">
                    <label>
                    <input type="radio"  name="fcHotelId">
                    <div class="result-box">
                      <p>暂不匹配</p>
                    </div>
                  </label>
                    <c:forEach items="${hotel}" var="ho">
                      <label>
                        <input type="radio" name="fcHotelId" value="${ho.hotelId}">
                        <div class="result-box">
                          <p>名称：<span>${ho.hotelName}</span></p>
                          <p>电话：<span>${ho.telephone}</span></p>
                          <p>地址：<span>${ho.hotelAddress}</span></p>
                        </div>
                      </label>
                    </c:forEach>
                  </div>
                </c:if>
                </div>

          </div>
          <hr class="hr-2">
          <c:if test="${empty otaInnOtaDto}">
          <button data-url="<c:url value="/innMatch/ajax/match.json"/> " class="btn btn-primary">提交匹配</button>
         </c:if>
        </div>
      </div>
    </div>
  </div>
  <!-- 房型匹配 -->
  <div class="col-sm-12">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="lighter smaller">房型匹配</h4>
      </div>

      <div class="widget-body">
        <div class="widget-main clearfix">
          <div class="roomtype-sort">
            <div class="col-sm-9 widget-title">该客栈房型信息<span>（拖动右边可匹配房型到虚线框内进行1对1匹配）</span></div>
            <div class="col-sm-3 widget-title">匹配天下房仓房型</div>
            <div style="clear: both"></div>
            <hr class="hr-2">
            <div class="clearfix">
              <div class="col-sm-9">
                <div class="col-sm-4" id="innRoomtype">
                  <c:forEach items="${omsRoomTypeList}" var="room">
                    <div class="inn-rooms">
                      <input type="hidden" value="${room.roomTypeId}">
                      <p class="inn-roomname">${room.roomTypeName}|<span>未匹配</span></p>
                      <p>面积：${room.roomArea}平方米</p>
                    </div>
                  </c:forEach>
                  <div class="inn-rooms">
                    <input type="hidden" value="0002">
                    <p class="inn-roomname">大床房|<span>未匹配</span></p>
                    <p>面积：20平方米</p>
                  </div>
                  <div class="inn-rooms">
                    <input type="hidden" value="0003">
                    <p class="inn-roomname">大床房|<span>未匹配</span></p>
                    <p>面积：20平方米</p>
                  </div>
                </div>
                <div class="col-sm-4">
                  <c:forEach items="${omsRoomTypeList}" var="room">
                    <div class="inn-rooms-icon"></div>
                  </c:forEach>
                </div>
                <div class="col-sm-4" id="otaRoomType">
                  <c:forEach items="omsRoomTypeList" var="room">
                    <div class="inn-rooms-box connectedSortable">
                    </div>
                  </c:forEach>
                </div>
              </div>
              <div class="col-sm-3 drag-con connectedSortable" id="sortable">
                <c:forEach items="${fcRoomTypeList}" var="room">
                  <div class="inn-rooms">
                    <input type="hidden" value="${room.roomTypeId}">
                    <p>${room.roomTypeName}</p>
                <%--    <p>面积：20平方米</p>--%>
                  </div>
                </c:forEach>
               <%-- <div class="inn-rooms">
                  <input type="hidden" value="1">
                  <p>1--大床房</p>
                  <p>面积：20平方米</p>
                </div>
                <div class="inn-rooms">
                  <input type="hidden" value="2">
                  <p>2--大床房</p>
                  <p>面积：20平方米</p>
                </div>
                <div class="inn-rooms">
                  <input type="hidden" value="3">
                  <p>3--大床房</p>
                  <p>面积：20平方米</p>
                </div>--%>
              </div>
            </div>
            <hr class="hr-2">
            <button class="btn btn-primary" id="roomTypeBtn">提交匹配</button>
          </div>
          <div class="roomtype-table">
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <td>序号</td>
                <td>酒店房型</td>
                <td>匹配房仓标准房型</td>
                <td>绑定价格计划</td>
                <td>操作</td>
              </tr>
              </thead>
              <tbody id="roomTypeData">
              </tbody>
            </table>
            <button class="btn btn-primary" id="roomTypeBtn">房型重新匹配</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 价格计划 -->
  <div class="col-sm-12">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="lighter smaller">新增房价计划</h4>
        <button class="btn btn-xs btn-success pull-right" id="addPriceBtn" data-toggle="modal" data-target="#addPricePlan">新增房价计划</button>
      </div>

      <div class="widget-body">
        <div class="widget-main">
          <table class="table table-bordered table-hover">
            <thead>
            <tr class="active">
            <%--  <td>序号</td>
              <td>价格计划ID</td>--%>
              <td>名称</td>
              <td>币种</td>
              <td>支付类型</td>
              <td>床型</td>
              <td>状态</td>
              <td>操作</td>
            </tr>
            </thead>
            <tbody id="roomTypeData" class="roomTypeDataClass">

            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- "编辑"弹框 -->
<div class="modal fade" id="editPopups">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">选择价格计划</h4>
      </div>
      <div class="modal-body">
        <select>
          <option value="1001">大床+预付（1001）</option>
          <option value="1001">大床+预付（2002）</option>
          <option value="1001">大床+预付（3001）</option>
          <option value="1001">大床+预付（4001）</option>
        </select>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="editSave">确定</button>
      </div>
    </div>
  </div>
</div>
<!-- "上架"弹框 -->
<div class="modal fade" id="roomTypeUp">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">操作确认</h4>
      </div>
      <div class="modal-body">
        <p>您确定将此房型上架吗？</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="editSave">确定</button>
      </div>
    </div>
  </div>
</div>
<!-- 新增房价计划 -->
<div class="modal fade" id="addPricePlan">
  <input type="hidden" id="saveRatePlanUrlId" value="<c:url value="/innMatch/ajax/saveRatePlan"/> "/>
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">新增房价计划</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
          <div class="form-group form-group-sm">
            <label class="col-sm-3 control-label">货币种类</label>
            <div class="col-sm-8">
              <select name="currency" id="currencyId">
                <option value="CNY">人民币</option>
                <option value="HKD">港元</option>
                <option value="MOP">澳门元</option>
                <option value="USD">美元</option>
                <option value="TWD">台币</option>
                <option value="THB">泰铢</option>
                <option value="SGD">新加坡元</option>
                <option value="MYR">马币</option>
                <option value="JPY">日元</option>
                <option value="KRW">韩元</option>
                <option value="CAD">加元</option>
              </select>
            </div>
          </div>
          <div class="form-group form-group-sm">
            <label class="col-sm-3 control-label">支付类型</label>
            <div class="col-sm-8">
              <select name="payMethod" id="payMethodId">
                <option value="pre_pay">预付</option>
                <option value="pay">面付</option>
              </select>
            </div>
          </div>
          <div class="form-group form-group-sm">
            <label class="col-sm-3 control-label">床型</label>
            <div class="col-sm-8">
              <select name="bedType" id="bedTypeId">
                <option value="onlyBed">单床</option>
                <option value="BigBed">大床</option>
                <option value="DoubleBed">双床</option>
                <option value="ThreeBed">三床</option>
                <option value="FourBed">四床</option>
              </select>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="userPlusBtn">确定</button>
      </div>
    </div>
  </div>
</div>
<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="/js/match_inn.js"></script>
<script type="text/javascript">
  var innId = '${param.innId}';
  //加载价格计划列表
  $.ajax({
    data:{'innId':innId},
    type:'post',
    dataType:'html',
    url:"/innMatch/ajax/ratePlanList",
    success:function(data){
      $(".roomTypeDataClass").html(data)
    },error:function(data){
      alert(data);
    }
  })

  $(".del_rate").bind("click",function(){
    var _this = $(this);
    var id = _this.attr("data");
    if(confirm("确定要删除数据吗")){
      $.ajax({
        data:{'ratePlanId':id},
        type:'post',
        dataType:'json',
        url:"/innMatch/ajax/delRatePlan",
        success:function(data){
          $(".roomTypeDataClass").html(data)
        },error:function(data){
          alert(data);
        }
      })
    }else{
      return false;
    }

  })
</script>
</body>
</html>
