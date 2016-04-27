<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<c:set value="${roomType.roomStatus}" var="list"/>
<c:if test="${not empty roomType.roomStatus && not empty roomType.roomDates}">
  <div class="table-left">
    <table class="table table-bordered">
      <input type="hidden" id="maiAccountId" value="${paramDto.maiAccount}"/>
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
      <c:forEach items="${roomType.roomStatus}" var="v">
        <tr class="active"><td colspan="2" style="height: 67px;">${v.roomTypeName}</td></tr>
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
      <tr>
        <c:forEach  begin="1" step="1" end="${roomType.roomDates.size()}">
          <th>剩余/价格</th>
        </c:forEach>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${list}" var="v">
        <tr>
          <c:forEach items="${v.roomDetail}" var="vv">
            <td style="height: 67px;">${(empty vv.roomNum)?0: vv.roomNum}/${vv.roomPrice}</td>
          </c:forEach>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</c:if>
<c:if test="${empty roomType.roomStatus || empty roomType.roomDates}">
  <div class="alert alert-danger center">
    没有房型数据,请选择分类/客栈查询房态房量
  </div>
</c:if>
<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/assets/js/room-type-ajax.js"></script>
