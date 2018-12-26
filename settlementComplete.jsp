<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="3;URL=HomeAction" >

<link rel="stylesheet" href="./css/hibiscus.css">
<link rel="shortcut icon" href="./images/favicon.ico">
<title>決済完了</title>
</head>

<body>
	<jsp:include page="header.jsp" />

	<div>
		<h1>決済完了画面</h1>
		<div class="complete">決済が完了しました。</div>
	</div>

	<%@ include file="footer.jsp" %>
</body>

</html>