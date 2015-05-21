<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
  <title>错误页面</title>
</head>
<body>
<div class="page-content">
  <div class="row">
    <div class="col-xs-12">
      <!-- PAGE CONTENT BEGINS -->

      <div class="error-container">
        <div class="well">
          <h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="icon-random"></i>
												500
											</span>
            <c:if test="${empty msg}"> Something Went Wrong </c:if>
            <c:if test="${not empty msg}"> ${msg} </c:if>
          </h1>

          <hr/>
          <h3 class="lighter smaller">
            But we are working
            <i class="icon-wrench icon-animated-wrench bigger-125"></i>
            on it!
          </h3>

          <div class="space"></div>

          <div>
            <h4 class="lighter smaller">Meanwhile, try one of the following:</h4>

            <ul class="list-unstyled spaced inline bigger-110 margin-15">
              <li>
                <i class="icon-hand-right blue"></i>
                Read the faq
              </li>

              <li>
                <i class="icon-hand-right blue"></i>
                Give us more info on how this specific error occurred!
              </li>
            </ul>
          </div>

          <hr/>
          <div class="space"></div>
          <div class="center">
            <a href="javascript:history.go(-1);" class="btn btn-grey">
              <i class="icon-arrow-left"></i>
              Go Back
            </a>

            <a href="<c:url value="/logout"/>" class="btn btn-primary">
              <i class="icon-dashboard"></i>
              Login
            </a>
          </div>
        </div>
      </div>

      <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
  </div>
  <!-- /.row -->
</div>
<!-- /.page-content -->

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
  <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>
</body>
</html>
