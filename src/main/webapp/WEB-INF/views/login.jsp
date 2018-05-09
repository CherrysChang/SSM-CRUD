<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <%--默认情况下，页面中的链接（包括样式表、脚本和图像的地址）都是相对于当前页面的地址（即：浏览器地址栏里的请求URL），现在可以使用 <base> 标签中的href属性来设置，所有的“相对基准URL”。--%>
    <%--在实际应用中，可以把<base>标签在通用的jsp中，然后每个JSP可以通过<%@ include %>的方式引入进来。--%>
    <%--<base href="${pageContext.request.contextPath}/">--%><%--此种方式在IE下不起作用，换成下面那种“完全的绝对路径”的写法就可以了--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css">--%>
    <link rel="stylesheet" href="static/css/login.css">
    <link rel="stylesheet" href="static/css/sign-up-login.css">
    <%--设置了base标签后，这里是相对base的路径--%>
    <script type="text/javascript" src="static/js/jquery-1.12.4.min.js"></script>
</head>
<body>
<!-- begin -->
<div id="login">
    <input id="tab-1" type="radio" name="tab" class="sign-in hidden" checked/>
    <input id="tab-2" type="radio" name="tab" class="sign-up hidden"/>
    <input id="tab-3" type="radio" name="tab" class="sign-out hidden"/>
    <!-- 登录页面 -->
    <div class="wrapper">
        <div class="login sign-in-htm">
            <form action="${pageContext.request.contextPath }/userLogin" method="post"
                  class="container offset1 loginform">
                <div class="owl-login">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad">
                    <%--<input type="hidden" name="_csrf" value="9IAtUxV2CatyxHiK2LxzOsT6wtBE6h8BpzOmk=">--%>
                    <div class="control-group">
                        <div class="controls">
                            <label for="username" class="control-label fa fa-user"></label>
                            <input id="username" type="text" name="username" placeholder="请输入用户名" tabindex="1"
                                   autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="password" class="control-label fa fa-asterisk"></label>
                            <input id="password" type="password" name="password" placeholder="请输入密码" tabindex="2"
                                   class="form-control input-medium">
                        </div>
                    </div>
                    <span style="color: red">${errmsg}</span>
                </div>
                <div class="form-actions">
                    <a href="#" tabindex="4" class="btn pull-left btn-link text-muted" onclick="goto_forget()">忘记密码?</a>
                    <a href="#" tabindex="5" class="btn btn-link text-muted" onclick="goto_register()">注册</a>
                    <button type="submit" tabindex="3" class="btn btn-primary">登录</button>
                </div>
            </form>
        </div>

        <!-- 忘记密码页面 -->
        <div class="login sign-out-htm">
            <form action="#" method="post" class="container offset1 loginform">
                <!-- 猫头鹰控件 -->
                <div class="owl-login">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad">
                    <div class="control-group">
                        <div class="controls">
                            <label for="forget-username" class="control-label fa fa-user"></label>
                            <input id="forget-username" type="text" name="username" placeholder="请输入用户名" tabindex="1"
                                   autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="forget-password" class="control-label fa fa-asterisk"></label>
                            <input id="forget-password" type="password" name="password" placeholder="请重置密码" tabindex="2"
                                   class="form-control input-medium">
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <a href="#" tabindex="4" class="btn pull-left btn-link text-muted" onClick="goto_login()">返回登录</a>
                    <button type="button" tabindex="3" class="btn btn-primary" onClick="forget()">重置密码</button>
                </div>
            </form>
        </div>
        <!-- 注册页面 -->
        <div class="login sign-up-htm">
            <form action="#" method="post" class="container offset1 loginform">
                <!-- 猫头鹰控件 -->
                <div class="owl-login">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad">
                    <div class="control-group">
                        <div class="controls">
                            <label for="register-username" class="control-label fa fa-user"></label>
                            <input id="register-username" type="text" name="username" placeholder="请输入用户名" tabindex="1"
                                   autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="register-password" class="control-label fa fa-asterisk"></label>
                            <input id="register-password" type="password" name="password" placeholder="请输入密码" tabindex="2"
                                   class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="register-repassword" class="control-label fa fa-asterisk"></label>
                            <input id="register-repassword" type="password" name="password" placeholder="请确认密码" tabindex="3"
                                   class="form-control input-medium">
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <a href="#" tabindex="5" class="btn pull-left btn-link text-muted" onClick="goto_login()">返回登录</a>
                    <button type="button" tabindex="4" class="btn btn-primary" onClick="register()">注册</button>
                </div>
            </form>
        </div>

    </div>
</div>
<!-- end -->
<script type="text/javascript">
    $(function () {
        /*ps：选择器中的逗号是两个条件的合集，第一个条件中的空格前面的部分不能带到下一个选择器中。
        selector1,selector2,selectorN形式是 取多个选择器的并集(组合选择器)*/
        $('#login #password,#login #forget-password,#login #register-password,#login #register-repassword').focus(function () {
            $('.owl-login').addClass('password');
        }).blur(function () {
            $('.owl-login').removeClass('password');
        });
    });

    function goto_forget(){
        $("#forget-username").val("");
        $("#forget-password").val("");
        $("#forget-code").val("");
        $("#tab-3").prop("checked",true);
    }

    function goto_login() {
        $("#login-username").val("");
        $("#login-password").val("");
        $("#tab-1").prop("checked",true);
    }

    function goto_register() {
        $("#register-username").val("");
        $("#register-password").val("");
        $("#register-repassword").val("");
        $("#tab-2").prop("checked",true);
    }
</script>
</body>
</html>
