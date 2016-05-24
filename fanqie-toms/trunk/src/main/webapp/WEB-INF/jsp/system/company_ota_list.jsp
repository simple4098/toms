<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<title>账号设置</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/userSet.css'/>">
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/layer/layer.js'/>"></script>
<script src="<c:url value='/js/my-system.js'/>"></script>
<div class="page-content">
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter blue">${companyName}-渠道列表</h3>
                    <div class="table-responsive">
                        <table id="sample-table-2" class="table table-striped table-bordered table-hover">
                            <thead style="font-size: 14px;">
                            <tr>
                                <th>渠道名称</th>
                                <th>appKey</th>
                                <th>appSecret</th>
                                <th>sessionKey</th>
                                <th>vendorId</th>
                                <th>开关</th>
                                <th class="hidden-480">修改</th>
                            </tr>
                            </thead>

                            <tbody style="font-size: 14px;">
                            <c:if test="${not empty data}">
                                <c:forEach items="${data}" var="d">
                                    <tr>

                                        <td>${d.otaInfo}</td>
                                        <td>${d.appKey}</td>
                                        <td>${d.appSecret}</td>
                                        <td>${d.sessionKey}</td>
                                        <td>${d.vendorId}</td>
                                        <td>${d.status==1?"开":"关"}</td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                                <a class="green" href="<c:url value="/system/find_company_ota_info?otaInfoRefId=${d.id}&companyId=${companyId}"/>">
                                                    <i class="icon-pencil bigger-130"></i>
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
        <c:if test="${empty data}">
            <div class="alert alert-danger center">
                没有数据,请筛选条件
            </div>
        </c:if>
    </div>
</div>
<script src="<c:url value='/assets/js/jquery-ui-1.10.3.full.min.js'/>"></script>
<script src="<c:url value='/js/company-list.js'/>"></script>
