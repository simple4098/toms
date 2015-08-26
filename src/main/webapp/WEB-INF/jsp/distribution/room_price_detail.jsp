<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<link href="<%=basePath%>/assets/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.min.css"/>
<link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/userSet.css">
<style>
  .rosybrown-order{color: #AB5D00}
</style>


<div class="modal-content">
  <div class="table-header" style="background-color:beige;text-align: center">
    <span class="rosybrown-order">${roomTypeName}</span>
  </div>
  <div class="modal-body">
    <form id="form-order-configId" class="form-horizontal"  method="post" >
      <input type="hidden" value="${param.innId}" name="innId">
      <input type="hidden" value="${param.roomTypeId}" name="roomTypeId">
      <input type="hidden" value="${param.otaInfoId}" name="otaInfoId">
      <input type="hidden" value="${roomTypeName}" name="roomTypeName">
      <div class="form-group">
        <div class="col-sm-9">
        <input readonly class="date-input" name="startDateStr" <c:if test="${not empty roomPrice.startDate}"> value="<fmt:formatDate value="${roomPrice.startDate}" pattern="yyyy-MM-dd"/>"</c:if> type="text" id="from_datepicker" placeholder="请选择开始日期">
        <span>至</span>
        <input readonly class="date-input" name="endDateStr" <c:if test="${not empty roomPrice.endDate}"> value="<fmt:formatDate value="${roomPrice.endDate}" pattern="yyyy-MM-dd"/>"</c:if>  type="text" id="to_datepicker" placeholder="请选择结束日期">
        </div>
      </div>
      <div class="space-4"></div>
      <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 在卖价基础上 </label>
        <div class="col-sm-9">
          <input type="text" name="value" id="value_id" value="${roomPrice.value}" class="col-xs-10 col-sm-5 ace pawd" placeholder="比如：+20;-20"/>
          <span style="color:red">只能输入数字;根据‘+，-’来区分价格增加减少;</span>
        </div>
      </div>
      <div class="space-4"></div>
      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info btn-sub " type="button" data-dismiss="modal" id="userPlusBtnId">
            <i class="icon-ok bigger-110"></i>
            保存
          </button>
        </div>
      </div>
    </form>
  </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/dateSelecter_room_price.js"></script>
<script src="<%=basePath%>/assets/layer/layer.js"></script>
<script type="text/javascript">
  $("#userPlusBtnId").on("click",function(){
    var from_date = $("#from_datepicker").val();
    var to_date = $("#to_datepicker").val();
    var value = $("#value_id").val();
    var reg = new RegExp("^[+-]?[0-9]+(.[0-9]{1,3})?$");
    if(from_date.length==0){
       alert("开始时间不能为空!");
      return false;
    }if(to_date.length==0){
       alert("结束时间不能为空!");
      return false;
    }if(value.length==0){
       alert("增减值不能为空");
      return false;
    }
    if(!reg.test(value)){
      alert("请输入数字!");
      return false;
    }
    var url = '<c:url value="/distribution/ajax/saveRoomPrice.json"/>';

    $.ajax({
      type:'POST',
      url:url,
      dataType:'json',
      data:$("#form-order-configId").serialize(),
      success:function(data){
        if(data.status){
            parent.window.location.href =  parent.window.location.href
        }else{
          alert(data.message);
        }
      },error:function(data){
         alert("保存异常，请稍后再试;");
      }
    })
  })
  //关闭窗口 绑定事件
  $(".layui-layer-close").bind("click",function(){
    parent.window.location.href =  parent.window.location.href
  })
</script>
