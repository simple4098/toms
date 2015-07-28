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
                <h1 style="float: left">
                    <a class="inn-name" data-toggle="modal" data-target="#addUser">
                            ${bangInn.innName}
                    </a>

                    &nbsp;&nbsp;
                    <span style="float: right"><h6>
                        <a target="_blank" href="<c:url value="/system/find_room_images?id=${bangInn.id}"/>">
                            房型图片
                        </a>
                    </h6></span>
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
<div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="addUserLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addUserLabel">客栈基本信息</h4>
            </div>
            <div class="modal-body">
                <c:if test="${not empty bangInn.innDto}">
                    <form class="form-horizontal" method="post" role="form">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 客栈名称 </label>

                            <div class="col-sm-9">
                                <input type="text" id="form-field-1" name="loginName"
                                       value="${bangInn.innName}" class="col-xs-10 col-sm-5 login-name"/>
                                <span class="help-login-name col-xs-12 col-sm-7"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right " for="form-field-2"> 地址 </label>

                            <div class="col-sm-9">
                                <input type="text" name="password" id="form-field-2" value="${bangInn.innDto.addr}"
                                       class="col-xs-10 col-sm-5 ace pawd"/>
                        <span class="help-password col-xs-12 col-sm-7">

											</span>
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-input-readonly">
                                百度地图纬度 </label>

                            <div class="col-sm-9">
                                <input type="text" class="col-xs-10 col-sm-5 ace tel" id="form-input-readonly"
                                       name="telephone"
                                       value="${bangInn.innDto.baiduLat}"/>
                        <span class="help-tel col-xs-12 col-sm-7">

											</span>
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">百度地图经度</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.baiduLon}"
                                       id="form-field-4"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">所在城市</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.city}"
                                       id="form-field-5"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">所在区/县</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.county}"
                                       id="form-field-6"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">前台电话</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.frontPhone}"
                                       id="form-field-7"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客栈简介</label>

                            <div class="col-sm-9">
                                <textarea>${bangInn.innDto.innInfo}</textarea>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">官方微博</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.microblog}"
                                       id="form-field-11"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">所在省</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.province}"
                                       id="form-field-12"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">公众微信号</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.pubWechat}"
                                       id="form-field-13"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">可接待人数</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.recNum}"
                                       id="form-field-14"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">房间数</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.roomNum}"
                                       id="form-field-15"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客服名称</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.serName}"
                                       id="form-field-16"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客服电话</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.serPhone}"
                                       id="form-field-17"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客服QQ</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.serQq}"
                                       id="form-field-18"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">客服微信</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.serWechat}"
                                       id="form-field-19"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">腾讯地图纬度</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.txLat}"
                                       id="form-field-21"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right" for="form-field-4">腾讯地图经度</label>

                            <div class="col-sm-9">
                                <input class="col-xs-10 col-sm-5 ace user-name" type="text" name="userName"
                                       value="${bangInn.innDto.txLon}"
                                       id="form-field-22"/>
                        <span class="help-name col-xs-12 col-sm-7">

											</span>

                                <div class="space-2"></div>

                            </div>
                        </div>
                        <div class="space-4"></div>
                        <c:if test="${not empty bangInn.innDto.facilitiesMap}">
                            <c:forEach items="${bangInn.innDto.facilitiesMap}" var="f">
                                <span class="btn btn-danger btn-sm tooltip-error" data-rel="tooltip"
                                      data-placement="top" title="${f.name}">${f.name}</span>
                            </c:forEach>
                        </c:if>
                    </form>
                </c:if>
            </div>
            <%--<div class="modal-footer">--%>
            <%--<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>--%>
            <%--<button type="button" class="btn btn-primary btn-submit">确认</button>--%>
            <%--</div>--%>
        </div>
    </div>
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
