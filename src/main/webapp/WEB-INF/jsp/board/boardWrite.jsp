<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="boardInsert.do" method="post">
	제목 <input type="text" name = "title" id = "title" style="padding-bottom: 10px"><br/>
	본문 <textarea type="text" name = "content" id = "content" style="height:500px;width:500px;padding-bottom: 10p"></textarea><br/>
	<input type="submit" value="제출">
	<a href="/boardList.do"><input type="button" value="목록"></a>
</form>

</body>
</html>