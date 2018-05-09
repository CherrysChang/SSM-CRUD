<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <%--默认情况下，页面中的链接（包括样式表、脚本和图像的地址）都是相对于当前页面的地址（即：浏览器地址栏里的请求URL），现在可以使用 <base> 标签中的href属性来设置，所有的“相对基准URL”。--%>
    <%--在实际应用中，可以把<base>标签在通用的jsp中，然后每个JSP可以通过<%@ include %>的方式引入进来。--%>
    <%--<base href="${pageContext.request.contextPath}/">--%><%--此种方式在IE下不起作用，换成下面那种“完全的绝对路径”的写法就可以了--%>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jq22.css">--%>
    <link rel="stylesheet" href="static/css/jq22.css"><%--设置了base标签后，这里是相对base的路径--%>
    <script type="text/javascript" src="static/js/jquery-1.12.4.min.js"></script>
    <script>
        $(function() {
            $('#login #password').focus(function() {
                $('#owl-login').addClass('password');
            }).blur(function() {
                $('#owl-login').removeClass('password');
            });
        });
    </script>
</head>
<body>
<!-- begin -->
<div id="login">
    <div class="wrapper">
        <div class="login">
            <form action="${pageContext.request.contextPath }/userLogin" method="post" class="container offset1 loginform">
                <div id="owl-login">
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
                            <input id="username" type="text" name="username" placeholder="username" tabindex="1" autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="password" class="control-label fa fa-asterisk"></label>
                            <input id="password" type="password" name="password" placeholder="Password" tabindex="2" class="form-control input-medium">
                        </div>
                    </div>
                    <span style="color: red">${errmsg}</span>
                </div>
                <div class="form-actions"><a href="#" tabindex="5" class="btn pull-left btn-link text-muted">Forgot password?</a><a href="#" tabindex="6" class="btn btn-link text-muted">Sign Up</a>
                    <button type="submit" tabindex="4" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- end -->
</body>
</html>
