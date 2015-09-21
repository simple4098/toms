<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${rateList}" var="ra">
  <tr>
    <td>${ra.bedType.desc}+${ra.payMethod.value}</td>
    <td>${ra.currency.value}</td>
    <td>${ra.payMethod.value}</td>
    <td>${ra.bedType.desc}</td>
    <td>使用中</td>
    <td ><a href="<c:url value="/innMatch/ajax/delRatePlan"/>?ratePlanId=${ra.id}&innId=${innId}">删除</a></td>
  </tr>
</c:forEach>

