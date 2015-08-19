
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>客栈接单设置</title>
    <script src="<%=basePath%>/assets/js/jquery-2.0.3.min.js"></script>
    <script src="<%=basePath%>/js/my-system.js"/>
    <script src="<%=basePath%>/assets/layer/layer.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/pages.css"/>
</head>
<body>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">客栈接单设置</h3>
                    <div class="table-header" style="background-color:beige">
                        <span style="text-align: center;color:#393939" >
                        <span class="blue-order"> 自</span>：系统自动执行确认      <span class="red-order">手 </span>：人工手动执行确认
                        <span class="rosybrown-order"> 默认接单机制：系统自动确认接单</span>  </span>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <form class="form-search" action="<c:url value="/inn_manage/find_inns"/>" method="post">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-8">
                                        <div class="input-group">
                                            <input type="text" value="${keywords}" name="keywords"
                                                   class="form-control search-query" placeholder="请输入关键字、客栈名称"/>
																	<span class="input-group-btn">
																		<button type="submit"
                                                                                class="btn btn-purple btn-sm">
                                                                            Search
                                                                            <i class="icon-search icon-on-right bigger-110"></i>
                                                                        </button>
																	</span>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <form class="form-page" action="<c:url value="/distribution/orderConfig"/>" method="post">
                                <input type="hidden" id="pageId" name="page" value="${page.page}"/>
                            <tr>
                                <th>客栈名称</th>
                                <th width="200">
                                    <select name="innLabelId" class="inn-label" data-url="<c:url value="/distribution/orderConfig"/>">
                                        <option value="" selected>客栈分类</option>
                                        <c:if test="${not empty labels}">
                                            <c:forEach items="${labels}" var="l">
                                                <option <c:if test="${innLabel == l.id}">selected</c:if>
                                                        value="${l.id}">${l.labelName}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </th>
                                <c:forEach items="${otaList}" var="ota">
                                    <th>${ota.otaInfo}</th>
                                </c:forEach>
                                <th>操作</th>
                            </tr>
                            </form>
                            </thead>

                            <tbody class="table-data" style="font-size: 14px;">
                            <c:if test="${not empty orderConfigDtoList}">
                                <c:forEach items="${orderConfigDtoList}" var="d">
                                    <tr>
                                        <td>${d.innName}</td>
                                        <td>${d.labelName}</td>
                                        <c:forEach items="${d.value}" var="v">
                                            <c:if test="${v eq '手动'}">
                                                <td><span class="red-order">${v}</span></td>
                                            </c:if><c:if test="${v eq '自动'}">
                                                <td><span class="blue-order">${v}</span></td>
                                            </c:if>
                                        </c:forEach>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a>
                                                <i class="icon-pencil bigger-130 order-config-detail" data-url="<c:url value="/distribution/ajax/orderConfigDetail?innId="/>${d.innId}"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
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
        <c:if test="${empty orderConfigDtoList}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
    </div>
    <!-- /.col -->

</div>
<!-- /.row -->
<script type="text/javascript">

   $('.order-config-detail').on('click',function(){
       var url = $(this).attr('data-url');
       $.ajax({
           type:'POST',
           url:url,
           dataType:'html',
           success:function(data){
               layer.open({
                   title:"接单设置",
                   type: 1,
                   shift: 1,
                   area: ['516px', '400px'],
                   shadeClose: true, //开启遮罩关闭
                   content: data
               });
           }
       })

   })

    $('.inn-label').on('change', function () {
        $("#pageId").attr("value", 1);
        $('.form-page').submit();
    });
    $('.user-id').on('change', function () {
        $("#pageId").attr("value", 1);
        $('.form-page').submit();
    });
    //分页方法
    function page(page) {
        $("#pageId").attr("value", page);
        $('.form-page').submit();
    }
</script>
</body>
</html>
