<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>客栈匹配列表</title>
<%--  <link rel="stylesheet" type="text/css" href="/assets/css/normalize.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/jquery-ui-1.10.3.full.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/ace.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/innRelation.css">--%>

  <link rel="stylesheet" type="text/css" href="/assets/css/userSet.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/jquery-ui-1.10.3.full.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/ace.min.css">
  <link rel="stylesheet" type="text/css" href="/assets/css/innRelation.css">
  <script src="/assets/js/jquery-2.0.3.min.js"></script>
  <script src="/assets/layer/layer.js"></script>
</head>
<body>
<div class="container">
 <%-- <ul class="nav-bar clearfix">
    <c:forEach items="${infoList}" var="o">
      <li><a href="<c:url value="/innMatch/match?otaInnOtaId=${o.otaInfoId}"/>">${o.otaInfo}</a></li>
    </c:forEach>
  </ul>--%>
    <c:set value="${pagination}" var="page"/>
  <div class="select-area">
    <form id="inn-search" action="<c:url value="/innMatch/match"/> " method="POST">
      <input type="hidden" id="pageId" name="page" value="${page.page}"/>
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
        <td>${o.innName}</td>
        <td>${o.mobile}</td>
        <td><c:if test="${empty o.otaInnOtaId}">未匹配</c:if><c:if test="${not empty o.otaInnOtaId}">匹配成功</c:if></td>
        <td><a class="btn btn-primary btn-sm" href="<c:url value="/innMatch/matchDetail?innId=${o.innId}"/>">管理酒店</a></td>
      </tr>
    </c:forEach>

  </table>
</div>
<c:if test="${page.pageCount>1}">
  <!-- PAGE CONTENT ENDS -->
  <div class="container">
    <div class="text-center">
      <ul class="pagination">
        <li <c:if test="${page.page==1}">class="disabled"</c:if>>
          <a <c:if test="${page.page!=1}">onclick="page(${page.page-1})"</c:if>>
            <i class="icon-double-angle-left"></i>
          </a>
        </li>

        <c:forEach begin="1" end="${page.pageCount}" step="1" varStatus="vs" var="p">
          <c:if test="${vs.count<11}">
            <li <c:if test="${page.page==p}">class="active"</c:if>>
              <a onclick="page(${p})">${p}</a>
            </li>
          </c:if>
          <c:if test="${vs.count ==10}">
            <li>
              <a>...</a>
            </li>
          </c:if>
          <c:if test="${vs.count >10}">
            <c:if test="${vs.count==page.pageCount}">
              <li <c:if test="${page.page==p}">class="active"</c:if>>
                <a onclick="page(${p})">${p}</a>
              </li>
            </c:if>
          </c:if>
        </c:forEach>
        <c:if test="${page.page!=page.pageCount}">
          <li>
            <a onclick="page(${page.page+1})">
              <i class="icon-double-angle-right"></i>
            </a>
          </li>
        </c:if>
      </ul>
    </div>
  </div>
</c:if>
<script src="/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script>

    $('#myButton').on('click', function(){
      $("#pageId").attr("value", 1);
       $('#inn-search').submit();
    })

    function page(page) {
      $("#pageId").attr("value", page);
      $('#inn-search').submit();
    }


</script>
</body>
</html>