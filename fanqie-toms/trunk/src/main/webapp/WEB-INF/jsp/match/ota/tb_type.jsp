<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>客栈匹配列表</title>

  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/userSet.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/innRelation.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/animate.css'/>">
  <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
  <script src="<c:url value='/assets/layer/layer.js'/>"></script>
</head>
<body>
<div class="container container-custom">
  <ul class="nav-bar clearfix" style=" height: 63px;">
    <c:forEach items="${infoList}" var="o">
      <li <c:if test="${o.otaType.name()=='TB'}">class="background"</c:if>><a  href="<c:url value="/innMatch/match?otaInfoId=${o.otaInfoId}"/>">${o.otaInfo}</a></li>
    </c:forEach>
  </ul>
  <h3 class="header smaller lighter blue">
    选择直连去啊的方式
    <span>（一旦选择无法回退更改，请慎重选择）</span>
  </h3>
  <input  id="otaInfoId" type="hidden" value="${otaInfoId}">
  <input  id="data-url"  type="hidden" value="<c:url value="/innMatch/change_type.json"/>">
  <div class="float-box">
      <div class="float-box-left" data-type="NOT_HAVE" >
        <h4 class="blue smaller lighter animated fadeInUp">全新自动上线去啊后台</h4>
        <p class="animated fadeInUp"><span>推荐使用者：</span>去啊店铺新开张，该店铺还未上线售卖过酒店</p>
        <p class="animated fadeInUp"><span>实现方式：</span>系统自动将本系统酒店全新上线到去啊后台</p>
        <p class="animated fadeInUp"><span>实现效果：</span>将本系统酒店全新上线去啊后台。建立本系统酒店和去啊酒店的直连关系</p>
      </div>
    <div class="float-box-right">
      <h4 class="blue smaller lighter animated fadeInUp">匹配去啊有台已有酒店</h4>
      <p class="animated fadeInUp"><span>推荐使用者：</span>在去啊上早已上架酒店进行售卖的卖家</p>
      <p class="animated fadeInUp"><span>实现方式：</span>手动将本系统酒店与去啊已有酒店匹配关联</p>
      <p class="animated fadeInUp"><span>实现效果：</span>仍然沿用已有的去啊酒店及房型信息，只将本系统设置的酒店信息与去啊已有的酒店信息建议直连关系</p>
    </div>
  </div>
  <div class="float-box">

    <div class="float-box-left" data-type="CREDIT" >
      <h4 class="blue smaller lighter animated fadeInUp">淘宝信用住</h4>
      <p class="animated fadeInUp"><span>推荐使用者：</span>去啊店铺新开张，该店铺还未上线售卖过酒店</p>
      <p class="animated fadeInUp"><span>实现方式：</span>系统自动将本系统酒店全新上线到去啊后台</p>
      <p class="animated fadeInUp"><span>实现效果：</span>将本系统酒店全新上线去啊后台。建立本系统酒店和去啊酒店的直连关系</p>
    </div>
  </div>

</div>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script>
  $(".float-box-left").click(function(){
    var _this = $(this);
    var tbType = _this.attr("data-type");
    var data_url = $("#data-url").val();
    var otaInfoId = $("#otaInfoId").val();
    var i = layer.load(0);
    $.ajax({
      url:data_url,
      type:'post',
      dataType:'json',
      data:{"tbType":tbType,"otaInfoId":otaInfoId},
      success:function(data){
        if(data.status=='200'){
          layer.msg("保存成功!");
          layer.close(i);
          window.location.href=window.location.href;
        }else{
          layer.close(i);
          layer.msg("保存失败");
        }

      },error:function(data){
        layer.close(i);
        layer.msg("保存失败");
      }
    })

  })
</script>

</body>
</html>