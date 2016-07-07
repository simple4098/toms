<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>自定义渠道</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/jquery-ui-1.10.3.full.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/ace.min.css'/>">
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/news-center.css'/>">
</head>
<body>
<div class="container ms-controller" ms-controller="otherPayManage">
  <div class="qita-xiaofei-guanli">
    <h4 style="position:relative">
      PMS渠道来源名设置
      <input id="onoffButton" type="button" data-url="<c:url value="/myselfChannel/update_company_pms_channel_status.json"/>"
             data-status="${status}"
      <c:if test="${!status}"> class="off_button"</c:if>
      <c:if test="${status}"> class="on_button"</c:if> title="点击开启" ms-click="divDisplayIsopenedFun">
      <a class="fr btn btn-primary" onclick="history.go(-1)">返回</a>
    </h4>

    <div class="unopened-content">
      <p class="unopened-con-p"> 功能开启后，可以设置酒店PMS的渠道来源名称；若不设置，则统一称为“其他渠道”。
      </p>
      <p> <tip style="color: red">注</tip>：此名称一旦设置，无法修改。若要删除请联系番茄来了。</p>
    </div>
    <div class="notopened-content" ms-css-display="divDisplayIsopened[0]"></div>
    <div class="opened-content" ms-css-display="divDisplayIsopened[1]">
      <c:if test="${empty data}">
      <p><a class="btn btn-primary" ms-click="addOtherPayItemFun">+PMS渠道名</a></p>
      </c:if>

      <div>
        <c:if test="${not empty data}">
        <ul class="opened-con-ul">
          <li class="col-sm-2 items-title">
            <dl>
              <dd>PMS渠道名</dd>
            </dl>
          </li>
          <li class="col-sm-10 items-detail">
            <dl>
              <dd>${data.pmsChannelName}</dd>
            </dl>
          </li>
        </ul>
        </c:if>
      </div>
    </div>
  </div>
  <div class="popups">
    <div class="modal fade" id="addPayItems">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增PMS渠道名</h4>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="col-sm-4 control-label no-padding-right"> 渠道名
              </label>

              <div class="col-sm-8">
                <input type="text" autocomplete="off" value="" maxlength="4"
                       class="ipt pms-channel-name"/>
              </div>
            </div>
          </div>
          <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
              <button class="btn" data-dismiss="modal">
                <i class="icon-undo bigger-110"></i>
                取消
              </button>
              &nbsp; &nbsp; &nbsp;
              <button class="btn btn-info save-pms-channel-name" type="button"
                      id="saveMyselfChannel"
                      data-url="<c:url value="/pmsChannelName/create_pms_channel_name.json"/>">
                <i class="icon-ok bigger-110"></i>
                确认
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
   <%-- <div class="modal fade" id="editPayItems">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">编辑自定义渠道名称</h4>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label class="col-sm-4 control-label no-padding-right"> 渠道名称
              </label>

              <div class="col-sm-8">
                <input type="text" autocomplete="off" value=""
                       class="ipt myself-channel-name-update" />
              </div>
            </div>
          </div>
          <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
              <button class="btn" data-dismiss="modal">
                <i class="icon-undo bigger-110"></i>
                取消
              </button>
              &nbsp; &nbsp; &nbsp;
              <button class="btn btn-info update-myself-channel" type="button">
                <i class="icon-ok bigger-110"></i>
                确认
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="deletePayItems">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">删除自定义渠道名称</h4>
          </div>
          <input type="hidden" id="data-url-id" value="">

          <div class="modal-body">
            您确定要删除该渠道名称吗？
          </div>
          <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
              <button class="btn" data-dismiss="modal">
                <i class="icon-undo bigger-110"></i>
                取消
              </button>
              &nbsp; &nbsp; &nbsp;
              <button class="btn btn-info delete-myself-channel" type="button">
                <i class="icon-ok bigger-110"></i>
                确认
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>--%>
  </div>
</div>
<script src="<c:url value='/assets/js/jquery-2.0.3.min.js'/>"></script>
<script src="<c:url value='/assets/js/avalon.js'/>"></script>
<script src="<c:url value='/js/other-pay-manage.js'/>"></script>
<script src="<c:url value='/js/myself-channel.js'/>"></script>
<%--<script src="<c:url value='/assets/js/bootstrap.min.js'/>"></script>

--%>
<script>
  $('.consumer-delete').bind("click", function () {
    var url = $(this).attr("data-url");
    $("#data-url-id").val(url);

  })
</script>
</body>
</html>
