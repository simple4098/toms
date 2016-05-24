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
  <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
  <script src="<c:url value='/assets/layer/layer.js'/>"></script>
</head>
<body>
<div class="container">
  <ul class="nav-bar clearfix" style=" height: 52px;">
  <c:forEach items="${infoList}" var="o">
    <li <c:if test="${o.otaType.name()=='QUNAR'}">class="background"</c:if>><a href="<c:url value="/innMatch/match?otaInfoId=${o.otaInfoId}"/>">${o.otaInfo}</a></li>
  </c:forEach>
</ul>
    <c:set value="${pagination}" var="page"/>
  <div class="select-area">
    <form id="inn-search" action="<c:url value="/innMatch/match"/>"  method="POST">
      <input type="text" id="fromData" name="fromData" value="">
      <input type="hidden" id="pageId" name="page" value="${page.page}"/>
      <input type="hidden" id="otaInfoId" name="otaInfoId" value="${otaInfoId}"/>
      <select class="form-control" id="kz-tags" name="innLabelId" >
        <option value="" selected>客栈分类</option>
        <c:if test="${not empty labels}">
          <c:forEach items="${labels}" var="l">
            <option <c:if test="${bangInnDto.innLabelId == l.id}">selected</c:if> value="${l.id}">${l.labelName}</option>
          </c:forEach>
        </c:if>
      </select> &nbsp;
      <input type="text" value="${bangInnDto.keywords}" placeholder="请输入客栈名称" name="keywords">
      <select class="form-control" name="innStatus">
        <option value selected>客栈匹配状态</option>
        <option <c:if test="${bangInnDto.innStatus.name()=='MATCH'}"> selected </c:if> value="MATCH">匹配成功</option>
        <option <c:if test="${bangInnDto.innStatus.name()=='NOT_MATCH'}"> selected </c:if> value="NOT_MATCH">未匹配</option>
      </select>
      <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
        搜索
        <i class="icon-search icon-on-right bigger-110"></i>
      </button>
      <button type="button" id="exportButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
        导出excel
      </button>
    </form>
  </div>
  <hr>
  <p>共搜索出<span class="color-red">${page.rowsCount}</span>家客栈</p>
  <table class="table table-bordered">
    <tr>
      <td>客栈名称</td>
      <td>联系号码</td>
      <td>匹配状态</td>
      <td>操作</td>
    </tr>
    <c:forEach items="${list}" var="o">
      <tr>
        <td>${o.innName}(${o.innId})</td>
        <td>${o.mobile}</td>
        <td><c:if test="${empty o.otaInnOtaId}">未匹配</c:if><c:if test="${not empty o.otaInnOtaId}">匹配成功</c:if></td>
        <%--<td><a class="btn btn-primary btn-sm" href="<c:url value="/innMatch/matchDetail?innId=${o.innId}&otaInfoId=${otaInfoId}"/>">管理酒店</a></td>--%>
        <td>
          <a class="btn btn-primary btn-sm" sj="${empty o.otaInnOtaId?'1':'0'}" otaInfoId="${otaInfoId}" innId="${o.innId}" >
            <c:if test="${empty o.otaInnOtaId}">上架</c:if><c:if test="${not empty o.otaInnOtaId}">下架</c:if>
          </a>
        </td>
      </tr>
    </c:forEach>

  </table>
</div>
<c:if test="${page.pageCount>1}">
<toms:page linkUrl="/innMatch/match"  pagerDecorator="${pageDecorator}"/>
</c:if>
<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script>

    $('#myButton').on('click', function(){
      $("#pageId").attr("value", 1);
      var obj = $('#inn-search');
      obj.attr("action","<c:url value="/innMatch/match"/>");
      obj.attr("target","");
      obj.submit();
    })

    $('#exportButton').on('click', function(){
      var obj = $('#inn-search');
      obj.attr("action","<c:url value="/innMatch/ajax/zhExport"/>");
      obj.attr("target","_blank");
      obj.submit();
    })

    $(".btn-primary").on('click',function(){
      if(window.confirm('你确定要更新此渠道客栈的信息?')){
        var i = layer.load(0);
        var _this = $(this);
        var otaInfoId = _this.attr("otaInfoId");
        var innId = _this.attr("innId");
        var sj = _this.attr("sj");
        var url = '<c:url value="/innMatch/update_inn_ota"/>';
        $.ajax({
          data:{"otaInfoId":otaInfoId,"innId":innId,"sj":sj},
          type:'post',
          dataType:'json',
          url:url,
          success:function(data){
            if(data.status=='200'){
              $('#inn-search').submit();
              /* window.location.href = window.location.href;*/
            }else{
              layer.msg("失败:"+data.message);
              layer.close(i);
            }
          },error:function(data){
            layer.msg("失败:"+data.message);
            layer.close(i);
          }
        })
      }

    });


    function page(page) {
      $("#pageId").attr("value", page);
      $('#inn-search').submit();
    }


</script>
</body>
</html>