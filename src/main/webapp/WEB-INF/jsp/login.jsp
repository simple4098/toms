<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>登录</title>
  <meta name="keywords" content="登录">
  <meta name="description" content="登录">
  <style type="text/css">
    html, body {
      background: none
    }
  </style>
</head>
<body>
<div class="login_bg">
  <div class="login_w">
    <h1>登录</h1>

    <div class="lg_main clearfix">
      <div class="lg_pic"></div>
      <form id="login" action="<c:url value="/login_process"/>" class="form" method="post">
        <ul>
          <li>
            <input type="text" name="username" id="user" value="" placeholder="帐号"
                   class="user-box :required :advice;tips">
          </li>
          <li>
            <input type="password" name="password" id="password" value="" placeholder="密码"
                   class="password-box :required :advice;tips">
          </li>
          <!--<li><input type="text" class="yanzheng" placeholder="验证信息">
          <span class="code" id="codeImg" furl="https://user.funi.com/user/getRondImg.json" style="background: url(https://user.funi.com/user/getRondImg.json)  0 0 no-repeat" alt="看不清，换一张" title="看不清，换一张">验证码</span>
</li>-->
          <li><input type="submit" class="btn" value="登 录">
          </li>
        </ul>
      </form>

    </div>
  </div>
</div>
</body>
</html>
