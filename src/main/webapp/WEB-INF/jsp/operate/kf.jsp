<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>客服资料分析</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>
<c:set value="${customer.pagination}" var="page"/>

<div class="container">
    <div class="clearfix select-area">
        <div class="pull-right">
            <form id="kfId" method="POST">
                <input type="hidden" class="data-url" data-url="<c:url value="/ajax/label.json"/>">
                <input type="hidden" id="pageId" name="page" value="${paramDto.page}" />
                <select class="form-control" id="fast_select">
                    <option>快捷日期</option>
                    <option>昨日</option>
                    <option>本月</option>
                    <option>近7天</option>
                    <option>近30天</option>
                </select>
                <input  class="date-input" name="startDate" value="${paramDto.startDate}" type="text" id="from_datepicker" placeholder="请选择开始日期">
                <span>至</span>
                <input  class="date-input" name="endDate" type="text" value="${paramDto.endDate}" id="to_datepicker" placeholder="请选择结束日期">
                <!-- <select class="selectpicker"> -->
                <select class="form-control" name="tagId" id="kz-tags-ac"></select>
                <select class="form-control" name="innId" id="kz_item-ac"></select>
                <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                    搜索
                    <i class="icon-search icon-on-right bigger-110"></i>
                </button>
            </form>
        </div>
    </div>
    <div class="container color-green">
        <p>所选时间段内，共接待 <span>${(empty customer.totalNum)?0:customer.totalNum}</span> 位客人，分布在 <span>${(empty customer.totalCityNum)?0:customer.totalCityNum}</span> 个城市。</p>
    </div>
    <div class="container table-wrap">
        <table class="table">
            <thead>
            <tr>
                <th>城市</th>
                <th>用户数</th>
                <th></th>
                <th>占比</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${customer.innCustomer}" var="c">
                    <tr>
                        <td>${c.city}</td>
                        <td>${c.num}</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar progress-bar progress-bar-success" style="width:${c.percent}%;"></div>
                            </div>
                        </td>
                        <td>
                            <div class="easy-pie-chart percentage" data-percent="${c.percent}" data-color="#87CEEB">
                                <span class="percent">${c.percent}</span>%
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${not empty customer.innCustomer}">
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
    <c:if test="${empty customer.innCustomer}">
        暂无数据
    </c:if>

</div>

<script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="<%=basePath%>/assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="<%=basePath%>/assets/js/tomato.min.js"></script>
<script src="<%=basePath%>/assets/js/dateSelecter.js"></script>
<script src="<%=basePath%>/assets/js/keHuZiLiaoFenXi.js"></script>
<script src="<%=basePath%>/assets/js/head_inn_.js"></script>
<script type="text/javascript">
    $(function(){
        var tagId ='${paramDto.tagId}';
        var innId ='${paramDto.innId}';
        var url= $('.data-url').attr('data-url');
        $("#from_datepicker").val('${paramDto.startDate}');
        $("#to_datepicker").val('${paramDto.endDate}');
        obt(tagId,innId,url);
        $('#myButton').on('click', function(){
            $("#pageId").attr("value",1);
            obt(tagId,innId,url);
            $("#kfId").submit();
        })
    })
    //分页方法
    function page(page){
        $("#pageId").attr("value",page);
        $("#kfId").submit();
    }

   /* function obt(){
        var tagId ='${paramDto.tagId}';
        var innId ='${paramDto.innId}';
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
                    var k ='';
                    if(tagId==json.data[attr].innLabelId){
                        k ='selected';
                    }
                    aLabel += "<option "+k+" value='"+json.data[attr].innLabelId+"'>"+json.data[attr].innLabelName+"</option>";
                }
                // 遍历获取客栈列表
                function getInnName(num){
                    aList = "";
                    for(var innList in json.data[num].bangInnList){
                        var k ='';
                        if(innId==json.data[num].bangInnList[innList].innId){
                            k ='selected';
                        }
                        aList += "<option "+k+" value='"+json.data[num].bangInnList[innList].innId+"'>"+json.data[num].bangInnList[innList].innName+"</option>"
                    };
                }
                // 默认加载第一个列表
                getInnName(0);
                // 联动刷新客栈列表
                $('#kz-tags').change(function(){
                    var num = $(this).children(':selected').index();
                    getInnName(num);
                    $('#kz_item').html(aList);
                })
                // 写入DOM
                $('#kz-tags-kf').html(aLabel);
                $('#kz_item-kf').html(aList);
                //加载运营概况数据；

            }
        });
    }*/

</script>
</body>
</html>