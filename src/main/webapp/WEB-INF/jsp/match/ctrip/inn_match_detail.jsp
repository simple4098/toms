<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>携程客栈匹配详情</title>
  <%--<link rel="stylesheet" type="text/css" href="/assets/css/normalize.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/jquery-ui-1.10.3.full.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/ace.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/innRelation.css">--%>

  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/userSet.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/innRelation.css'/>">
  <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
  <script src="<c:url value='/assets/layer/layer.js'/>"></script>

</head>

<body>
<div class="container">
  <!-- 客栈匹配 -->
  <div class="col-sm-12">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="lighter smaller">客栈匹配<button type="button"  id="goBack" data-url="<c:url value="/innMatch/match?otaInfoId=3"/>"  class="btn btn-primary">返回</button></h4>
      </div>

      <div class="widget-body">
        <div class="widget-main clearfix">
          <div class="col-sm-6 widget-title">番茄来了系统客栈信息</div>
          <div class="col-sm-6 widget-title">匹配携程标准酒店</div>
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


                  <div id="matchSuccessId"  <c:if test="${empty otaInnOtaDto}">style="display: none"</c:if>>
                    <input type="radio" style="display: none" name="fcHotelId" checked value="${fcHotel.parentHotelId}">
                    <div class="result-box">
                      <p>名称：<span>${fcHotel.hotelName}</span></p>
                      <p>电话：<span>暂无</span></p>
                      <p>地址：<span>暂无</span></p>
                    </div>
                  </div>


                  <div id="not-match-id"  <c:if test="${!(empty otaInnOtaDto)}">style="display: none"</c:if>>
                  <div class="inn-search">
                    <span>搜索可匹配标准酒店</span>
                    <input type="text" id="search-id" placeholder="输入酒店关键字搜索" value="${keyword}">
                    <button type="button" data-value="1" data-url="<c:url value="/innMatch/ajax/searchCtripHotel"/>"
                            id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn"
                            autocomplete="off">
                      搜索
                      <i class="icon-search icon-on-right bigger-110"></i>
                    </button>
                    <a class="search-btn-up" href="#" data-url="<c:url value="/innMatch/ajax/searchCtripHotel"/>">上一页</a>
                    &nbsp;
                    <a class="search-btn-down" href="#" data-url="<c:url value="/innMatch/ajax/searchCtripHotel"/>">下一页</a>
                  </div>
                  <div id="contentId">
                    <%--<label>
                    <input type="radio"  name="fcHotelId" value="">
                    <div class="result-box">
                      <p>暂不匹配</p>
                    </div>
                  </label>--%>
                    <c:forEach items="${hotel}" var="ho">
                      <label>
                        <input type="radio" <c:if test="${ho.id==otaInnOtaDto.wgHid}">checked</c:if> name="fcHotelId" value="${ho.parentHotelId}">
                        <div class="result-box">
                          <p>名称：<span>${ho.hotelName}</span></p>
                       <%--    <p>电话：<span>${ho.telephone}</span></p>
                          <p>地址：<span>${ho.hotelAddress}</span></p> --%>
                        </div>
                      </label>
                    </c:forEach>
                  </div>
                  </div>
                </div>
          </div>

          <hr class="hr-2">
          <c:if test="${empty otaInnOtaDto}">
          <button data-url="<c:url value="/innMatch/ajax/ctrip/match.json"/> " class="btn btn-primary" id="btn-primary-id">提交匹配</button>
         </c:if>
          <c:if test="${!(empty otaInnOtaDto)}">
            <button data-url="<c:url value="/innMatch/ajax/ctrip/match.json"/>" class="btn btn-primary btn-primary-cx" id="cxInMatchId">重新匹配</button>
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
          <div class="roomtype-sort"  <c:if test="${!(empty matchRoomTypeList)}"> style="display: none" </c:if>>
            <div class="col-sm-9 widget-title">该客栈房型信息<span>（拖动右边可匹配房型到虚线框内进行1对1匹配）</span></div>
            <div class="col-sm-3 widget-title">匹配携程房型</div>
            <div style="clear: both"></div>
            <hr class="hr-2">
            <div class="clearfix">
              <div class="col-sm-9">
                <div class="col-sm-4" id="innRoomtype">
                  <c:forEach items="${omsRoomTypeList}" var="room">
                    <div class="inn-rooms">
                      <input type="hidden" bedNum="${room.bedNum}" bedLen="${room.bedLen}" bedWid="${room.bedWid}" data-roomTypeId="${room.roomTypeId}" data-roomTypeName="${room.roomTypeName}" data-area="${room.roomArea}">
                      <p class="inn-roomname">${room.roomTypeName}|<span>未匹配</span></p>
                      <p>面积：<c:if test="${!(empty room.roomArea)}">${room.roomArea}平方米</c:if> <c:if test="${empty room.roomArea}">未知</c:if> </p>
                      <p>床数:${room.bedNum} 床长:${room.bedLen} 床宽:${room.bedWid}</p>
                    </div>
                  </c:forEach>
                </div>
                <div class="col-sm-4">
                  <c:forEach items="${omsRoomTypeList}" var="room">
                    <div class="inn-rooms-icon"></div>
                  </c:forEach>
                </div>
                <div class="col-sm-4" id="otaRoomType">
                  <c:forEach items="${omsRoomTypeList}" var="room">
                    <div class="inn-rooms-box connectedSortable">
                    </div>
                  </c:forEach>
                </div>
              </div>
              <div class="col-sm-3 drag-con connectedSortable" id="sortable">
                <c:forEach items="${fcRoomTypeList}" var="room">
                  <div class="inn-rooms">
                    <input type="hidden" data-roomTypeId="${room.roomTypeId}" data-roomTypeName="${room.roomTypeName}">
                    <p>${room.roomTypeName}</p>
                  </div>
                </c:forEach>
              </div>
            </div>
            <hr class="hr-2">
            <button class="btn btn-primary" data-url="<c:url value="/innMatch/ajax/ctrip/matchRoomType.json"/>" id="roomTypeBtn">提交匹配</button>

          </div>
          <c:if test="${!(empty matchRoomTypeList)}">
          <div class="roomtype-table room-list-class">
            <table class="table table-bordered table-hover">
              <thead>
              <tr class="active">
                <td>序号</td>
                <td>酒店房型</td>
                <td>匹配携程房型</td>
                 <td>价格计划Code</td>
                  <td>价格计划名称</td>
                   <td>操作</td>
              </tr>
              </thead>
              <tbody id="roomTypeData">
              <c:forEach items="${matchRoomTypeList}" var="o" varStatus="varS">
              <tr>
                <td>${varS.count}</td>
                <td>
                  <p class="inn-roomname">${o.tomRoomTypeName}|<span>${(empty o.ctripChildRoomTypeId)?'未匹配':'匹配成功'}</span></p>
                </td>
                <td>${o.ctripRoomTypeName}</td>
                <td>${o.ratePlanCode}</td>
                <td>${o.ratePlanCodeName}</td>
                 <td> 
                 	<c:choose>
                 		<c:when test="${  not  empty o.id  }">
                 				<button class="btn btn-xs btn-success pull-right" onclick="editMappingRatePlan(${o.id},'${o.ratePlanCode}')"  data-toggle="modal" data-target="#addPricePlan">编辑房价计划</button>
                 		</c:when>
                 	</c:choose>
                 </td>
         
              </tr>
              </c:forEach>

              </tbody>
            </table>
            <button class="btn btn-primary"  id="rdMatchRoomTypeBtn">房型重新匹配</button>
          </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>
  <!-- 价格计划 -->
  <div class="col-sm-12">
    <div class="widget-box">
      <div class="widget-header">
        <h4 class="lighter smaller">房价计划</h4>
       <!--  <button class="btn btn-xs btn-success pull-right" id="addPriceBtn" data-toggle="modal" data-target="#addPricePlan">房价计划</button> -->
      </div>

      <div class="widget-body">
        <div class="widget-main">
          <table class="table table-bordered table-hover">
            <thead>
            <tr class="active">
            <%--  <td>序号</td>--%>
              <td>价格计划ID</td>
              <td>名称</td>
              <td>币种</td>
              <td>支付类型</td>
        <!--       <td>状态</td> -->
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
      <input type="hidden" value="" id="hiddenRatePlanId"/>
      <div class="modal-body" id="ratePlan-id">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="bangRatePlanId">确定</button>
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
      <input type="hidden" value="" id="hiddenRoomTypeId"/>
      <input type="hidden" value="" id="hiddenRoomTypeId_planId"/>
      <div class="modal-body">
        <p>您确定将此房型上架吗？</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="sjEditSave">确定</button>
      </div>
    </div>
  </div>
</div>
<!-- "下架"弹框 -->
<div class="modal fade" id="roomTypeUp1">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">操作确认</h4>
      </div>
      <input type="hidden" value="" id="fqRoomTypeFcId"/>
      <div class="modal-body">
        <p>您确定将此房型下架吗？</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="xjEditSave">确定</button>
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
        <h4 class="modal-title">房价计划</h4>
      </div>
      <div class="modal-body">
      	<span>房价计划:</span>
      	<select  id="ctrip_rate_plan_code_list"></select>
      	<input  type="hidden"  id="ctrip_previous_mapping_id"  >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal" id="updateMappingRateCodeBtn">确定</button>
      </div>
    </div>
  </div>
</div>
<%--<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>--%>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script src="<c:url value='/js/match_ctrip_hotel.js'/>"></script>
<script type="text/javascript">
  var innId = '${param.innId}';
  //加载价格计划列表
  $.ajax({
    data:{'innId':innId},
    type:'post',
    dataType:'html',
    url:'<c:url value="/innMatch/ajax/ctrip/ratePlanList"/> ',
    success:function(data){
      $(".roomTypeDataClass").html(data)
    },error:function(data){
      alert(data);
    }
  })


  //重新匹配
  $("#rdMatchRoomTypeBtn").on("click",function(){
     $(".roomtype-sort").css("display","block");
     $(".room-list-class").css("display","none");

  });

  //  编辑房价代码
  function editMappingRatePlan(id,curCode){
	  $.ajax({
		    type:'get',
		    dataType:'json',
		    url:'<c:url value="/innMatch/ajax/ctrip/ratePlanListData"/>',
		    success:function(data){
		    	if(data && data.length > 0){
		    		var eleSelect =  $("#ctrip_rate_plan_code_list");	
		    		eleSelect.empty();
		    		for(var i = 0 ; i < data.length ; i++){
		    			var d = data[i];
		    			$("<option>").attr("value",d.ratePlanCode).html(d.ctripRatePlanName).appendTo(eleSelect);
		    		}
		    		eleSelect.val(curCode);
		    		$("#ctrip_previous_mapping_id").val(id);
		    	}
		    }
		});
  };
  
  
  $("#updateMappingRateCodeBtn").click(function(){
	  var newRatePlan = $("#ctrip_rate_plan_code_list").val();
	  var newRatePlanName = $("#ctrip_rate_plan_code_list  option:selected").text();
	  var mappingId = $("#ctrip_previous_mapping_id").val();
	    layer.load(0);
	  $.ajax({
		    type:'POST',
		    dataType:'json',
		      data:{"newPlanCode":newRatePlan,"newPlanCodeName":newRatePlanName,"mappingId":mappingId},
		    url:'<c:url value="/innMatch/ajax/ctrip/mapping/update"/>',
		    success:function(data){
		    	 layer.close(0);
		    	  if(data.status==400){
		                layer.msg("匹配失败:"+data.message);
		            }else{
		            	layer.msg("匹配成功");
		                window.location.href = window.location.href;
		            }
		    }
		});
  });
  
</script>
</body>
</html>
