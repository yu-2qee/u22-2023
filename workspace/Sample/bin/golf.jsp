<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ゴルフスコア入力</title>
</head>
<body>
    <h1>ゴルフスコア入力プログラム（1〜18ホール）</h1>

    <!-- スコア入力フォーム -->
    <form action="golfResult.jsp" method="post">
        <label for="score">スコアを入力してください: </label>
        <input type="text" id="score" name="score" required />
        <input type="submit" value="送信" />
    </form>
</body>
</html>
