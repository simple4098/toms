<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${rateList}" var="ra">
  <tr>
    <td>${ra.ratePlanCode}</td>
    <td>${ra.ctripRatePlanName}</td>
    <td>${ra.currency.value}</td>
    <td>预付</td>
  <!--   <td>使用中</td> -->
  </tr>
</c:forEach>
<script >

  //删除价格计划
  $(".delRatePlanClass").on("click",function(){
    var _this = $(this);
    var url = _this.attr("data-url");
    $.ajax({
      type:"post",
      url:url,
      dataType:"json",
      success:function(data){
        if(data.status=="200"){
          window.location.href = window.location.href;
        }else{
          layer.msg(data.message);
        }
      },error:function(data){
        layer.msg(date.message);
      }
    })
  })

</script>
