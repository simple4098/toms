
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<head>
    <title>客栈房价设置</title>
    <script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
    <link href="<c:url value='/assets/css/pages.css'/>" rel="stylesheet"/>
</head>
<div class="page-content">
    <c:set value="${pagination}" var="page"/>
    <div class="row">
        <div class="col-xs-12">
            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">客栈房价设置</h3>
                    <div class="widget-body">
                        <div class="widget-main">
                            <form class="form-search" action="<c:url value="/distribution/fangPrice"/>" method="post">
                                <input type="hidden" id="pageId" name="page" value="${page.page}"/>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-8">
                                        <div class="input-group">
                                            <select name="innLabelId" >
                                                <option value="" selected>客栈分类</option>
                                                <c:if test="${not empty labels}">
                                                    <c:forEach items="${labels}" var="l">
                                                        <option <c:if test="${innLabel == l.id}">selected</c:if>
                                                                value="${l.id}">${l.labelName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </select> &nbsp;
                                            <input type="text" value="${keywords}" name="keywords"  class="form-control search-query"  style="width: 250px;"  placeholder="请输入关键字、客栈名称"/>
                                            &nbsp;
											 <button type="submit"  class="btn btn-purple btn-sm">
                                              Search
                                              <i class="icon-search icon-on-right bigger-110"></i>
                                             </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                                <tr>
                                    <th>客栈名称</th>
                                    <th width="200">
                                       客栈分类
                                    </th>
                                    <th>房型价格管理</th>

                                </tr>
                            </thead>

                            <tbody class="table-data" style="font-size: 14px;">
                            <c:if test="${not empty orderConfigDtoList}">
                                <c:forEach items="${orderConfigDtoList}" var="d">
                                    <tr>
                                        <td>${d.innName}</td>
                                        <td>${d.labelName}</td>
                                        <td>
                                            <c:forEach items="${otaList}" var="ota">
                                                <a class="btn btn-info btn-sub" href="<c:url value="/distribution/fangPriceDetail?innId="/>${d.innId}&otaInfoId=${ota.otaInfoId}">${ota.otaInfo}</a>
                                            </c:forEach>
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
            <toms:page linkUrl="/distribution/fangPrice"  pagerDecorator="${pageDecorator}"/>
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
<script>
    //分页方法
    function page(page) {
        $("#pageId").attr("value", page);
        $('.form-search').submit();
    };
    $(".form-search").bind("submit",function(){
        $("#pageId").attr("value", 1);
        $('.form-search').submit();
    })
</script>

