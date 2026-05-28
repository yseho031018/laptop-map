<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Place Detail</title>
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
</head>
<body>
	<main class="app-shell">
		<section class="page-header">
			<div>
				<p class="eyebrow">Place Detail</p>
				<h1>장소 상세</h1>
				<p class="page-description">선택한 장소의 상세 정보를 확인하는 화면입니다.</p>
			</div>
			<a class="button button-secondary" href="<c:url value='/'/>">메인 화면</a>
		</section>

		<section class="panel">
			<h2>상세 정보</h2>
			<p class="meta-text">장소 상세 화면입니다.</p>
			<div class="action-row">
				<a class="button button-primary" href="<c:url value='/places'/>">장소 목록</a>
				<a class="button button-secondary" href="<c:url value='/'/>">메인 화면</a>
			</div>
		</section>
	</main>
</body>
</html>
