<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/userLogin" method="post">
    姓名：<input type="text" name="username"> <br><br>
    密码：<input type="text" name="password"> <br><br>
    <input type="submit" value="登陆">
</form>
</body>
</html>
