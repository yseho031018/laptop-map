<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Place List</title>
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
</head>
<body>
	<main class="app-shell">
		<section class="page-header">
			<div>
				<p class="eyebrow">Places</p>
				<h1>장소 목록</h1>
				<p class="page-description">검색 결과와 저장할 장소 정보를 확인하는 화면입니다.</p>
			</div>
			<a class="button button-secondary" href="<c:url value='/'/>">메인
				화면</a>
		</section>

		<section class="panel">
			<h2>검색 결과</h2><br>
			<c:forEach var="place" items="${placeNames}" varStatus="status">
    			<form action="<c:url value='/places/select'/>" method="post">
        			<input type="hidden" name="place_name" value="${place.place_name}">
        			<input type="hidden" name="x" value="${place.x}">
			        <input type="hidden" name="y" value="${place.y}">
	
    		   	 	<button type="submit">
            			${place.place_name}
        			</button>
   				 </form>
   				 
			</c:forEach>
		</section>
	</main>
</body>
</html>
