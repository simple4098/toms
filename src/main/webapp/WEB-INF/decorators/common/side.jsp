<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/20
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url"
       value='${requestScope["org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"]}'
       scope="request"/>
<style type="text/css">
    <!--
    * {
        margin: 0;
        padding: 0;
        border: 0;
    }

    body {
        font-family: arial, 宋体, serif;
        font-size: 12px;
    }

    #nav {
        width: 180px;
        line-height: 24px;
        list-style-type: none;
        text-align: left;
        /*定义整个ul菜单的行高和背景色*/
    }

    /*==================一级目录===================*/
    #nav a {
        width: 160px;
        display: block;
        padding-left: 20px;
        /*Width(一定要)，否则下面的Li会变形*/
    }

    #nav li {
        /*background:#CCC; /!*一级目录的背景色*!/*/
        border-bottom: #FFF 5px solid; /*下面的一条白边*/
        float: left;
        /*float：left,本不应该设置，但由于在Firefox不能正常显示
        继承Nav的width,限制宽度，li自动向下延伸*/
    }

    #nav li a:hover {
        background: #CC0000; /*一级目录onMouseOver显示的背景色*/
    }

    #nav a:link {
        color: #666;
        text-decoration: none;
    }

    #nav a:visited {
        color: #666;
        text-decoration: none;
    }

    #nav a:hover {
        color: #FFF;
        text-decoration: none;
        font-weight: bold;
    }

    /*==================二级目录===================*/
    #nav li ul {
        list-style: none;
        text-align: left;
    }

    #nav li ul li {
        /*background: #EBEBEB; /!*二级目录的背景色*!/*/
        border-bottom: white 5px solid;
        padding-top: 5px;
    }

    #nav li ul a {
        padding-left: 20px;
        width: 160px;
        /* padding-left二级目录中文字向右移动，但Width必须重新设置=(总宽度-padding-left)*/
    }

    /*下面是二级目录的链接样式*/
    #nav li ul a:link {
        color: #666;
        text-decoration: none;
    }

    #nav li ul a:visited {
        color: #666;
        text-decoration: none;
    }

    #nav li ul a:hover {
        color: #F3F3F3;
        text-decoration: none;
        font-weight: normal;
        background: #CC0000;
        /* 二级onmouseover的字体颜色、背景色*/
    }

    /*==============================*/
    #nav li:hover ul {
        left: auto;
    }

    #nav li.sfhover ul {
        left: auto;
    }

    #content {
        clear: left;
    }

    #nav ul.collapsed {
        display: none;
    }

    -->
    #PARENT {
        width: 300px;
        padding-left: 20px;
    }
</style>
<script type=text/javascript><!--
var LastLeftID = "";
function menuFix() {
    var obj = document.getElementById("nav").getElementsByTagName("li");

    for (var i = 0; i < obj.length; i++) {
        obj[i].onmouseover = function () {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        obj[i].onMouseDown = function () {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        obj[i].onMouseUp = function () {
            this.className += (this.className.length > 0 ? " " : "") + "sfhover";
        }
        obj[i].onmouseout = function () {
            this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
        }
    }
}
function DoMenu(emid) {
    var obj = document.getElementById(emid);
    obj.className = (obj.className.toLowerCase() == "expanded" ? "collapsed" : "expanded");
    if ((LastLeftID != "") && (emid != LastLeftID)) //关闭上一个Menu
    {
        document.getElementById(LastLeftID).className = "collapsed";
    }
    LastLeftID = emid;
}
function GetMenuID() {
    var MenuID = "";
    var _paramStr = new String(window.location.href);
    var _sharpPos = _paramStr.indexOf("#");

    if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1) {
        _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
    }
    else {
        _paramStr = "";
    }

    if (_paramStr.length > 0) {
        var _paramArr = _paramStr.split("&");
        if (_paramArr.length > 0) {
            var _paramKeyVal = _paramArr[0].split("=");
            if (_paramKeyVal.length > 0) {
                MenuID = _paramKeyVal[1];
            }
        }
    }

    if (MenuID != "") {
        DoMenu(MenuID)
    }
}
GetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果
menuFix();
--></script>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) {
                }
            </script>

            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button class="btn btn-success">
                        <i class="icon-signal"></i>
                    </button>

                    <button class="btn btn-info">
                        <i class="icon-pencil"></i>
                    </button>

                    <button class="btn btn-warning">
                        <i class="icon-group"></i>
                    </button>

                    <button class="btn btn-danger">
                        <i class="icon-cogs"></i>
                    </button>
                </div>

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                    <span class="btn btn-warning"></span>

                    <span class="btn btn-danger"></span>
                </div>
            </div>
            <!-- #sidebar-shortcuts -->
            <ul id="nav" class="nav nav-list">
                <li>
                    <a href="#">
                        <i class="icon-dashboard"></i>
                        <span class="menu-text"> 首页 </span>
                    </a>
                </li>

                <li class="active">
                    <a href="#">
                        <i class="icon-text-width"></i>
                        <span class="menu-text"> 房态数量 </span>
                    </a>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle" onclick="DoMenu('ChildMenu1')">
                        <i class="icon-desktop"></i>
                        <span class="menu-text"> 运营报表 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>
                    <ul class="submenu collapsed" id="ChildMenu1">
                        <li>
                            <a href="elements.html">
                                <i class="icon-double-angle-right"></i>
                                运营趋势
                            </a>
                        </li>

                        <li>
                            <a href="buttons.html">
                                <i class="icon-double-angle-right"></i>
                                客户资料分析
                            </a>
                        </li>
                        <li>
                            <a href="buttons.html">
                                <i class="icon-double-angle-right"></i>
                                订单来源分析
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle" onclick="DoMenu('ChildMenu2')">
                        <i class="icon-edit"></i>
                        <span class="menu-text"> 客栈管理 </span>

                        <b class="arrow icon-angle-down"></b>
                        <ul class="submenu collapsed" id="ChildMenu2">
                            <li>
                                <a href="buttons.html">
                                    <i class="icon-double-angle-right"></i>
                                    客栈列表
                                </a>
                            </li>
                            <li>
                                <a href="buttons.html">
                                    <i class="icon-double-angle-right"></i>
                                    客栈活跃表
                                </a>
                            </li>
                        </ul>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="icon-list-alt"></i>
                        <span class="menu-text"> 消息通知 </span>
                    </a>
                </li>
                <li>
                    <a href="#" class="dropdown-toggle" onclick="DoMenu('ChildMenu3')">
                        <i class="icon-tag"></i>
                        <span class="menu-text"> 设置 </span>

                        <b class="arrow icon-angle-down"></b>
                    </a>
                    <ul class="submenu collapsed" id="ChildMenu3">
                        <li>
                            <a href="elements.html">
                                <i class="icon-double-angle-right"></i>
                                账号设置
                            </a>
                        </li>

                        <li>
                            <a href="buttons.html">
                                <i class="icon-double-angle-right"></i>
                                通知模板
                            </a>
                        </li>
                        <li>
                            <a href="buttons.html">
                                <i class="icon-double-angle-right"></i>
                                客栈标签
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>

        </div>
    </div>
</div>
