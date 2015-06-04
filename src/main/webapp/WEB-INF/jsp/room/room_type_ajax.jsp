<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set value="${roomType.list}" var="list"/>
<c:if test="${not empty list}">
  <div class="table-left">
    <table class="table table-bordered">
      <tr class="success">
        <td colspan="2">
          <span id="prevM">&lt;</span>
          <!-- <form> -->
          <input readonly class="date-input-2" type="text" value="${paramDto.startDate}" id="from_datepicker">
          <input style="display: none" readonly value="${paramDto.endDate}" type="text" id="to_datepicker">
          <!-- </form> -->
          <span id="nextM">&gt;</span>
        </td>
      </tr>
      <tr class="active"><td colspan="2">房型</td></tr>
      <c:forEach items="${roomType.list}" var="v">
        <tr class="active"><td colspan="2">${v.roomTypeName}</td></tr>
      </c:forEach>
    </table>
  </div>
  <div class="table-right">
    <table class="table table-bordered table-hover room-status-table">

      <thead>
      <tr>
        <c:forEach items="${roomType.roomDates}" var="vv">
          <th><fmt:formatDate value="${vv}" pattern="MM-dd" /> </th>
        </c:forEach>
      </tr>
      <tr>
        <c:forEach  begin="1" step="1" end="${roomType.roomDates.size()}">
          <th>剩余</th>
        </c:forEach>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${list}" var="v">
        <tr>
          <c:forEach items="${v.roomDetail}" var="vv">
            <td>${vv.roomNum}</td>
          </c:forEach>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</c:if>
<c:if test="${empty list}">
  <div class="alert alert-danger center">
    没有数据,请选择分类/客栈查询房态房量
  </div>
</c:if>
<script type="text/javascript">
  /**
   * 房态房量
   */
  /*function getRoomType(postData){
    var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
    $.ajax({
      type:'POST',
      data:postData,
      url:url,
      dataType:'html',
      success:function(data){
        $("#roomTypeContainerId").empty();
        $("#roomTypeContainerId").html(data);
      }
    })
  }*/
  $('#from_datepicker').change(function(){
    var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
    var date = $(this).val();
    var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
    $('#to_datepicker').val( TC.plusDate(date, '30', 'd', 'yyyy-MM-dd') );
    var postDate = {'startDate': $('#from_datepicker').val(), 'endDate': $('#to_datepicker').val(),'tagId':tagId,'accountId':accountId};
    $.ajax({
      type:'POST',
      data: postDate,
      url:url,
      dataType:'html',
      success:function(data){
        $("#roomTypeContainerId").empty();
        $("#roomTypeContainerId").html(data);
      }
    })
  })
  //上一月
  $('#prevM').on('click',function(){
    var date = $('#from_datepicker').val();
    $('#from_datepicker').val( TC.plusDate(date, '-1', 'M', 'yyyy-MM-dd') ).change();
  })
  // 下一月
  $('#nextM').on('click',function(){
    var date = $('#from_datepicker').val();
    $('#from_datepicker').val( TC.plusDate(date, '1', 'M', 'yyyy-MM-dd') ).change();
  })

 /* $('#myButton').on('click', function(){
    var startDate = $('#from_datepicker').val(),
            endDate = $('#to_datepicker').val(),
            tagId = $('#kz-tags-r').val(),
            accountId = $('#kz_item-r').val(),
            postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId};
    getRoomType( postData );
  })*/
</script>
