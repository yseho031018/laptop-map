<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Place Detail</title>
</head>
<body>
	<h1>장소 상세 화면</h1>
	<p>장소 상세 화면입니다.</p>
	<a href="<c:url value='/places'/>">
		<button type="button">장소 화면으로 이동</button>
	</a><br>
	<a href="<c:url value='/'/>">
		<button type="button">메인 화면으로 이동</button>
	</a>
</body>
</html>
