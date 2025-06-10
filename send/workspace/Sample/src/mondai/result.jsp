<%@ page import="PasswordHasher" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String password = request.getParameter("password");
    String hash = PasswordHasher.hash(password);
%>
<html>
<head>
    <title>ハッシュ結果</title>
</head>
<body>
    <h2>パスワードのハッシュ結果</h2>
    <p><strong>元のパスワード:</strong> <%= password %></p>
    <p><strong>SHA-256 ハッシュ:</strong><br><%= hash %></p>
    <a href="index.jsp">戻る</a>
</body>
</html>
