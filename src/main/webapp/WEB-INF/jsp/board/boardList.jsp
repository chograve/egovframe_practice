<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>

<script type="text/javascript">
// 페이지 이동 함수
function movePage(page){
	location.href="<%=request.getContextPath()%>/boardList.do?pageNo=" + page
} 
</script>

</head>
<body>
	<table border="1">
		<tr>
			<td>아이디</td>
			<td>제목</td>
			<td>글쓴시간</td>
			<td>글쓴이</td>
			<td>조회수</td>
		</tr>
		<c:forEach var="board" items="${list}">
			<tr>
				<td>${board.boardid}</td>
				<td style="width: 200px"><a
					href="boardView.do?board_id=${board.boardid}">${board.title}</a></td>
				<td style="width: 200px">${board.writetime}</td>
				<td>${board.userid}</td>
				<td>${board["seecount"]}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5" style="text-align: center;"><ui:pagination
					paginationInfo="${paginationInfo}" type="image"
					jsFunction="movePage" /></td>
		</tr>
	</table>

</body>
<style>
table {
	border-collapse: collapse;
	margin: 100px auto;
	width: 800px;
	height: auto;
}

th, td {
	width: 100px;
	height: 50px;
	text-align: center;
	border: 1px solid #000;
	vertical-align: top; /* 위 */
	vertical-align: bottom; /* 아래 */
	vertical-align: middle; /* 가운데 */
}
</style>
</html>