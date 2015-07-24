<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/21
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <title>客栈图片</title>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/select2.min.css"/>
    <script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'></script>
    <script src='<%=basePath%>/js/select2.full.js'></script>
</head>
<div class="main-content">
    <form action="<c:url value="/system/images"/>" method="post">
        <div>
            &nbsp;&nbsp;&nbsp;
            <div class="ddd">
                <select name="id" class="js-example-basic-single inn-name" style="width: 300px;">
                    <option value="">--请选择--</option>
                    <c:if test="${not empty bangInns}">
                        <c:forEach items="${bangInns}" var="b">
                            <option
                                    <c:if test="${bangInn.id == b.id}">selected</c:if>
                                    value="${b.id}">${b.innName}</option>
                        </c:forEach>
                    </c:if>
                </select>
                &nbsp;&nbsp;&nbsp;
                <button class="btn-success">查询</button>
            </div>
        </div>
    </form>
    <c:if test="${not empty bangInn}">
        <div class="page-content">
            <div class="page-header">
                <h1>
                    <a href="<c:url value="/system/find_room_images?id=${bangInn.id}"/>">
                            ${bangInn.innName}
                    </a>
                </h1>
            </div>
            <!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="row-fluid">
                        <ul class="ace-thumbnails">
                            <c:if test="${not empty bangInn.innDto}">
                                <c:forEach items="${bangInn.innDto.imgList}" var="img">
                                    <li>
                                        <a href="${imgUrl}${img.imgUrl}" target="_blank" title="${img.imgName}"
                                           data-rel="colorbox">
                                            <img style="width:100px;height: 100px;" alt="150x150"
                                                 src="${imgUrl}${img.imgUrl}.200x200.${img.suffix}"/>
                                        </a>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty bangInn.innDto.imgList}">
                                    <span>无</span>
                                </c:if>
                            </c:if>
                            <c:if test="${empty bangInn.innDto}">
                                <span>无</span>
                            </c:if>

                        </ul>
                    </div>
                    <!-- PAGE CONTENT ENDS -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </div>
            <!-- /.page-content -->
    </c:if>
</div>

<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script>
    $(document).ready(function () {
        $(".js-example-basic-single").select2();
    });
    //    $(document).ready(function(){
    //        var data = [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }];
    //        $(".js-example-basic-single").select2({
    //            data: data
    //        })
    //    })

</script>
<!-- /.main-content -->
