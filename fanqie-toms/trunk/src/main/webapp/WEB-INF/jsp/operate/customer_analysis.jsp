<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
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
    <title>客户资料分析</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/assets/css/pages.css">
</head>
<body>
<c:set value="${pagination}" var="page"/>
<c:set value="${paginationCity}" var="pageCity"/>

<div class="container">
    <div class="clearfix select-area">
        <div class="pull-right">
            <form id="analysisId" method="POST" action="<c:url value="/operate/customer_analysis"/> ">
                <input type="hidden" id="pageId" name="page" value="${customerParamDto.page}" />
                <input type="hidden" id="cityPageId" name="cityPage" value="${customerParamDto.cityPage}" />
                <input type="hidden" id="provinceId" name="province" value="${customerParamDto.province}" />
                <select class="form-control" id="fast_select" name="quickTime">
                    <option value="快捷日期" <c:if test="${customerParamDto.quickTime == '快捷日期' }">selected</c:if>>快捷日期</option>
                    <option value="本月" <c:if test="${customerParamDto.quickTime == '本月' }">selected</c:if>>本月</option>
                    <option value="昨日" <c:if test="${customerParamDto.quickTime == '昨日' }">selected</c:if>>昨日</option>
                    <option value="近7天" <c:if test="${customerParamDto.quickTime == '近7天' }">selected</c:if>>近7天</option>
                    <option value="近30天" <c:if test="${customerParamDto.quickTime == '近30天' }">selected</c:if>>近30天</option>
                </select>
                <input  class="date-input" name="startDate" type="text" id="from_datepicker" placeholder="请选择开始日期">
                <span>至</span>
                <input  class="date-input" name="endDate" type="text" id="to_datepicker" placeholder="请选择结束日期">
                <!-- <select class="selectpicker"> -->
                <button type="button" id="myButton" data-loading-text="搜索中..." class="btn btn-purple btn-sm search-btn" autocomplete="off">
                    搜索
                    <i class="icon-search icon-on-right bigger-110"></i>
                </button>
            </form>
        </div>
    </div>
    <%-- <div class="color-green">
        <p style="padding-left:200px">省份分布&nbsp;所选时间段内，共接待 <span>${(empty customer.totalNum)?0:customer.totalNum}</span> 位客人</p>
    </div> --%>
    <div class="table-format"> 
        <table class="table">
            <thead>
            <tr>
            	<th><p>省份分布</p></th>
            	<th colspan=3><p>所选时间段内，共接待 <span>${(empty totalGuestCount)?0:totalGuestCount}</span> 位客人</p></th>
            </tr> 
           
            </thead>
            <tbody>
             <tr>
                <td>省份柱状图</td>
                <td>省份</td>
                <td>用户数</td>
               	<td>百分比</td> 
            </tr>
            <c:forEach items="${provinceAnalysisList }" var="p">
            	<tr>
            		<td class="bar-chart" style="padding-left: 0px;padding-right: 0px;border-bottom:solid white;">
            			<div class="div-chart" style="width:${p.provinceGuestCount/totalGuestCount*100 }%">
            			</div>
            		</td>
            		<td>${p.province}</td>
            		<td>${p.provinceGuestCount }</td>
            		<td><fmt:formatNumber type="number" value="${p.provinceGuestCount/totalGuestCount*100 }" maxFractionDigits="1"></fmt:formatNumber>%</td> 
            	</tr>
            </c:forEach>
                <tr>
                	<td></td>
                	<td id="province-td" colspan=3> <c:if test="${not empty provinceAnalysisList && page.pageCount>1}">
           				 <toms:page linkUrl="/operate/customer_analysis" pagerDecorator="${pageDecorator}"/>
        			</c:if></td>
                </tr>
            </tbody>
        </table>
    </div>
    <div>
    	<p class="text-format">城市分布
	    	<select class="provinceList" name="province">
	    		<option value="">全国</option>
	    		<c:forEach items="${provinceAnalysisList }" var="p">
	    			<option value="${p.province }" <c:if test="${customerParamDto.province == p.province }"> selected</c:if> >${p.province }</option>
	    		</c:forEach>
	    	</select>
    	</p>
    	<div class="table-format-city">
    		 <table class="table">
            	<thead>
            		<tr>
	            		<th>城市柱状图</th>
	               		<th>城市</th>
	               		<th>用户数</th>
	               		<th>百分比</th> 
            		</tr>
            	</thead>
            	<tbody>
            		 <c:forEach items="${cityAnalysisList }" var="c">
		            	<tr>
		            		<td class="bar-chart" style="padding-left: 0px;padding-right: 0px;border-bottom:solid white;">
		            			<div class="div-chart" style="width:${c.cityGuestCount/c.provinceGuestCount*100}%">
		            			</div>
		            		</td>
		            		<td>${c.city}</td>
		            		<td>${c.cityGuestCount }</td>
		            		<td><fmt:formatNumber type="number" value="${c.cityGuestCount/c.provinceGuestCount*100 }" maxFractionDigits="1"></fmt:formatNumber>%</td> 
		            	</tr>
		            </c:forEach>
            		<tr>
	                	<input id="page-val" type="hidden" page-change="false" value=""/>
	                	<input id="page-val-down" type="hidden" page-change="false" value=""/>
	                	<input id="page-val-up" type="hidden" page-change="false" value=""/>
	                	<td></td>
	                	<td id="city-td" colspan=3> <c:if test="${not empty cityAnalysisList && pageCity.pageCount>1}">
	           				 <toms:page linkUrl="/operate/customer_analysis" pagerDecorator="${pageDecoratorCity}"/>
	        			</c:if></td>
                	</tr>
            	</tbody>
            </table>
    	</div>
    </div>
    <%-- <c:if test="${empty customer.innCustomer}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
    </c:if> --%>

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
        $("#from_datepicker").val('${customerParamDto.startDate}');
        $("#to_datepicker").val('${customerParamDto.endDate}');
        $('#myButton').on('click', function(){
            $("#pageId").attr("value",1);
            $("#cityPageId").attr("value",1);
            
            $("#provinceId").val($(".provinceList").val());
            $("#analysisId").submit();
        })
    })
    $("#province-td").click(function(){
    	if($("#page-val").attr("page-change") == 'true'){
    		$("#page-val-up").val($("#page-val").val());
    		$("#page-val-up").attr("page-change",'true');
    	}
    	if($("#page-val-up").attr("page-change") == 'true'){
    		//page
    		$("#from_datepicker").val('${customerParamDto.startDate}');
       		$("#to_datepicker").val('${customerParamDto.endDate}');
    		$("#fast_select").val('${customerParamDto.quickTime}');
    		$("#pageId").attr("value",$("#page-val-up").val());
            $("#analysisId").submit();
    	}
		$("#page-val-up").attr("page-change",'false');
		$("#page-val").attr("page-change",'false');
		$("#page-val-down").attr("page-change",'false');
    })
    
    $("#city-td").click(function(){
    	if($("#page-val").attr("page-change") == 'true'){
    		$("#page-val-down").val($("#page-val").val());
    		$("#page-val-down").attr("page-change",'true');
    	}
    	if($("#page-val-down").attr("page-change") == 'true'){
    		//page
    		$("#from_datepicker").val('${customerParamDto.startDate}');
       		$("#to_datepicker").val('${customerParamDto.endDate}');
    		$("#fast_select").val('${customerParamDto.quickTime}');
    		$("#cityPageId").attr("value",$("#page-val-down").val());
            $("#analysisId").submit();
    	}
		$("#page-val-up").attr("page-change",'false');
		$("#page-val").attr("page-change",'false');
		$("#page-val-down").attr("page-change",'false');
    })
    //分页方法
    function page(page){
    	$("#page-val").val(page);
    	$("#page-val").attr("page-change",'true');
    } 
  
	$('.provinceList').change(function() {
		$("#from_datepicker").val('${customerParamDto.startDate}');
		$("#to_datepicker").val('${customerParamDto.endDate}');
		$("#fast_select").val('${customerParamDto.quickTime}');
		$("#provinceId").val($('.provinceList').val());
		$("#cityPageId").attr("value", 1);
		$("#analysisId").submit();
	});
</script>
</body>
</html>