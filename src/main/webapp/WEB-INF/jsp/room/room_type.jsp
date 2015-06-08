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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>
<div class="container">
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
<%--<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>--%>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<%--<script src="<%=basePath%>/assets/js/dateSelecter.js"></script>--%>
<%--<script src="<%=basePath%>/assets/js/roomNum.js"></script>--%>
<script type="text/javascript">
    //ajax 获取标签楼盘
    $(function(){
        var url= $('.data-url').attr('data-url');
        $.ajax({
            type:'GET',
            url:url+"?v"+new Date().getTime(),
            dataType:'json',
            success:function(json){
                var aLabel = ""; //存放客栈标签
                var aList = "";  //存放客栈列表
                // 遍历获取客栈标签
                for(var attr in json.data){
                    aLabel += "<option value='"+json.data[attr].innLabelId+"'>"+json.data[attr].innLabelName+"</option>";
                }
                // 遍历获取客栈列表
                function getInnName(num){
                    aList = "<option value>--请选择客栈--</option>";
                    for(var innList in json.data[num].bangInnList){
                        aList += "<option value='"+json.data[num].bangInnList[innList].accountId+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
                    };
                }
                // 默认加载第一个列表
                getInnName(0);
                // 联动刷新客栈列表
                $('#kz-tags-r').change(function(){
                    var num = $(this).children(':selected').index();
                    getInnName(num);
                    $('#kz_item-r').html(aList);
                })
                // 写入DOM
                $('#kz-tags-r').html(aLabel);
                $('#kz_item-r').html(aList);
                var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
                var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId};
                getRoomType(postData);
            }
        });
    })
    $(function(){
        // datepicker
        $( "#from_datepicker, #to_datepicker" ).datepicker({
            showOtherMonths: true,
            selectOtherMonths: true,
            dateFormat: 'yy-mm-dd',
            maxDate: new Date()
        });
        // 日期初始化，默认选择昨天
        $('#from_datepicker, #to_datepicker').val( TC.plusDate(new Date(), '-1', 'd', 'yyyy-MM-dd'));
    })
    /**
     * 房态房量
     */
    function getRoomType(postData){
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
    }
    $('#from_datepicker').change(function(){
        var url = $("#dataUrlId").attr("data-url")+"?v="+new Date().getTime();
        var tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
        var date = $(this).val();
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

    $('#myButton').on('click', function(){
        var startDate = $('#from_datepicker').val(), endDate = $('#to_datepicker').val(), tagId = $('#kz-tags-r').val(), accountId = $('#kz_item-r').val();
        var postData = {'startDate': startDate, 'endDate': endDate, 'tagId': tagId, 'accountId': accountId};
        getRoomType( postData );
    })

</script>
</body>
</html>
