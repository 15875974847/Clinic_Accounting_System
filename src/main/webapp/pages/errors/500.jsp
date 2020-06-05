<%@ page language="java" contentType="text/html; charset=US-ASCII"
pageEncoding="US-ASCII"%>
<%@ page session = "false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
<head>
  	<meta charset="utf-8">
	<title>500</title>
	<meta name="description" content="Clinic Accounting System internal error page.">
	<meta name="author" content="AT">
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
	<link rel="stylesheet" type="text/css" href="${context}/static/css/errorPage.css">
</head>

<body>
	<div class="wrapper">
		<div class="box">
			<h1>500</h1>
			<p>Sorry, it's me, not you.</p>
			<p>&#58;&#40;</p>
			<p><a href="javascript:window.location.reload(true)">Let me try again!</a></p>
		</div>
	</div>
</body>
</html>