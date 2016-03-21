<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="toms" uri="http://www.fanqielaile.com/jsp/tag/toms" %>
<div class="widget-body">
  <div class="widget-main ">
    <form class="form-horizontal" role="form">
      <input name="otaInfoId" id="otaInfoId" type="hidden" value="${otaInfoId}">
      <input name="otaType" id="otaTypeId" type="hidden" value="TB">
      <div class="row">
        <div class="col-xs-4">
          <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="appKey">AppKey</label>
            <div class="col-xs-9">
              <input type="text" id="appKey" name="appKey" placeholder="AppKey" class="col-xs-12">
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="appSecret">AppSecret</label>
            <div class="col-xs-9">
              <input type="text" id="appSecret" name="AppSecret" placeholder="AppSecret" class="col-xs-12">
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" for="sessionKey">SessionKey</label>
            <div class="col-xs-9">
              <input type="text" id="sessionKey" name="sessionKey" placeholder="SessionKey" class="col-xs-12">
            </div>
          </div>
          <div class="form-group">
            <label class="col-xs-3 control-label no-padding-right" >价格模式</label>
            <div class="col-xs-9">
              <toms:usedPriceModel/><br>
            </div>
          </div>
          <div class="form-field-tips" id="error"></div>
        </div>
        <div class="col-xs-8">
          <div class="well">
            <h4 class="green smaller lighter">如何获取APP KEY</h4>
            <a target="_blank" href="http://note.youdao.com/share/web/file.html?id=fb73d4ad44cf37f43f095c864e2db111&type=note">
              淘宝开放平台申请创建“去啊旅行-酒店”应用教程
            </a>
          </div>
        </div>
      </div>
      <div class="clearfix form-actions">
        <div class="col-xs-12">
          <button class="btn btn-info"  f-url="<c:url value="/innMatch/match?otaInfoId=${otaInfoId}"/>" data-url="<c:url value="/innMatch/vetted.json"/>" id="sub-id" type="button"> <i class="icon-ok bigger-110"></i>
          验证
          </button>
          &nbsp; &nbsp; &nbsp;
          <button class="btn" type="reset"> <i class="icon-undo bigger-110"></i>
            重置
          </button>
        </div>
      </div>
    </form>
  </div>
</div>
