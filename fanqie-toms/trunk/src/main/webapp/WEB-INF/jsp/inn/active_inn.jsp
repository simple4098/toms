<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>客栈活跃报表</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>
<c:set value="${inn.pagination}" var="page"/>
<div class="container">
    <div class="select-area">
        <form id="innActiveId" method="post" action="<c:url value="/inn_manage/activeInn"/> ">
            <input type="hidden" id="pageId" name="page" value="${paramDto.page}" />
            <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
            <input class="date-input" name="startDate" type="text" id="from_datepicker">
            <select class="form-control" name="tagId" id="kz-tags-ac"></select>
            <select class="form-control" name="innId" id="kz_item-ac"></select>
            <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                搜索
                <i class="icon-search icon-on-right bigger-110"></i>
            </button>
        </form>
    </div>
    <h3 class="head-tip" style="margin-bottom: 16px;display: inline-block;padding: 0 8px;line-height: 2;">
        黄色格子：创建入住订单；绿色格子：创建预订订单；蓝色格子：有过系统能够记录下来的任意一个操作（除去预定、入住操作）；白色格子：未操作过系统<br>
        颜色覆盖权重：黄色＞绿色＞蓝色＞白色</h3>
    <div class="table-box">
        <table class="table table-bordered table-user-active">
            <thead>
            <tr>
                <th>客栈名称</th>
                <th>注册手机号</th>
                <c:forEach begin="1" step="1" end="${inn.maxLen}" var="v">
                    <th>${v}</th>
                </c:forEach>

            </tr>
            </thead>

            <tbody>
            <c:forEach items="${inn.innActiveList}" var="v">
                <tr>
                    <td>${v.innName}</td>
                    <td>${v.mobile}</td>
                    <c:forEach items="${v.activeList}" var="va">
                        <td class="color-${va}"></td>
                    </c:forEach>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${not empty inn.innActiveList && page.pageCount>1}">
        <div class="container">
            <div class="text-center">
                <ul class="pagination">

                    <li <c:if test="${page.page==1}">class="disabled"</c:if>>
                        <a <c:if test="${page.page!=1}">onclick="page(${page.page-1})"</c:if>>
                            <i class="icon-double-angle-left"></i>
                        </a>
                    </li>

                    <c:forEach begin="1" end="${page.pageCount}" step="1" varStatus="vs"  var="p">
                        <c:if test="${vs.count<11}">
                            <li <c:if test="${page.page==p}">class="active"</c:if>>
                                <a onclick="page(${p})">${p}</a>
                            </li>
                        </c:if>
                        <c:if test="${vs.count==10}">
                            <li>
                                <a >...</a>
                            </li>
                        </c:if>
                        <c:if test="${vs.count>10}">
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
    <c:if test="${empty inn.innActiveList}">
        <div class="alert alert-danger center">
            没有数据,请重新筛选条件
        </div>
    </c:if>
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<%--<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>--%>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/dateSelecter.js"></script>
<script src="<%=basePath%>/assets/js/KeZhanHuoYueBiao.js"></script>
<script src="<%=basePath%>/assets/js/head_inn_.js"></script>
<script type="text/javascript">
    $(function(){
        var tagId ='${paramDto.tagId}';
        var innId ='${paramDto.innId}';
        var url= $('.data-url').attr('data-url');
        if('${paramDto.startDate}'){
            $("#from_datepicker").val('${paramDto.startDate}');
        }else{
            $('#from_datepicker').val( TC.plusDate(new Date(), '-1', 'd', 'yyyy-MM'));
        }

        $( "#from_datepicker" ).datepicker({
            showOtherMonths: true,
            selectOtherMonths: true,
            dateFormat: 'yy-mm',
            maxDate: new Date()
        });
        obt(tagId,innId,url);
        $('#myButton').on('click', function(){
            $("#pageId").attr("value",1);
            obt(tagId,innId,url);
            $("#innActiveId").submit();
        })
    })
    //分页方法
    function page(page){
        $("#pageId").attr("value",page);
        $("#innActiveId").submit();
    }



</script>
</body>
</html>
