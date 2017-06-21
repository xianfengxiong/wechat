<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信绑定</title>
    <meta charset="UTF-8">
</head>
<body>
    <form action="/wx/bind" method="post">
        <table>
            <tr>
                <td><label>帐号:<input type="text" name="account"></label></td>
            </tr>
            <tr>
                <td>
                    <label>密码:<input type="password" name="password"></label>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" name="openid" value="${openid}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="ok">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
