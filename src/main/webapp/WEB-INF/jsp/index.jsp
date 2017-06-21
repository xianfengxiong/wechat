<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>welcome</title>
    <meta charset="UTF-8">
</head>
<body>
    Welcome, your detail info list :
    <ul>
        <li>account:${user.account}</li>
        <li>password:${user.password}</li>
        <li>openid:${user.openid}</li>
        <li>nickname:${user.nickname}</li>
        <li>sex:${user.sex}</li>
        <li>province:${user.province}</li>
        <li>city:${user.city}</li>
        <li>country:${user.country}</li>
        <li><img src="${user.headimgurl}"></li>
        <li>unionid:${user.unionid}</li>
    </ul>
</body>
</html>
