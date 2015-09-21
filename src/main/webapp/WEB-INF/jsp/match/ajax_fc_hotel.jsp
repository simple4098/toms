<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

