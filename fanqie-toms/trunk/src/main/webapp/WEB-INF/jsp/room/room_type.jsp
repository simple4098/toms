<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>房态房量</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>
<div >
    <div class="select-area">
        <form>
            <input type="hidden" id="dataUrlId" data-url="<c:url value="/oms/ajax/obtRoomType"/>">
            <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
            <select class="form-control" id="kz-tags-r"></select>
            <select class="form-control" id="kz_item-r"></select>
            <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                搜索
                <i class="icon-search icon-on-right bigger-110"></i>
            </button>
        </form>
    </div>
    <c:set value="${roomType.list}" var="list"/>
        <div class="room-status-box" id="roomTypeContainerId">
            <c:if test="${not empty list}">
            <div class="table-left">
                <table class="table table-bordered">
                    <tr class="success">
                        <td colspan="2">
                            <span id="prevM">&lt;</span>
                            <!-- <form> -->
                            <input readonly class="date-input-2" type="text" id="from_datepicker">
                            <input  readonly type="text" id="to_datepicker">
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
                            <th>${vv}</th>
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
        </div>
</div>
<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/room-type.js"></script>

</body>
</html>
