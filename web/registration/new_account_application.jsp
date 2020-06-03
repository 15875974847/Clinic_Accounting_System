<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Registration</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- Latest Bootstrap and datatables Bootstrap API ================================================-->
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css"/>
	<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
	<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="${context}/static/css/myBackgrounds.css">
	
	
	<!-- JQuery, Bootstrap 4 -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>

	
	<%-- Injecting message if we only have one --%>
	<c:if test = "${sessionScope.message != null}">
		<%-- Snackbar css and js function definition --%>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/snackbar.css">
		<script type="text/javascript" src="${context}/static/js/snackbar.js"></script>
		<%-- Displaying snackbar with error --%>
		<script>$(document).ready(function () {showPopupSnackbar('${sessionScope.message}');});</script>
	</c:if>
	
  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">

	<div class = "container">
		<div class = "my-5">
		
			<div class="mt-5 mb-3">
				<div class="text-center">
					<h2 class="bg-secondary text-white">&darr;Please, enter username and password&darr;</h2>
				</div>
			</div>

			<form action="/registration/submitNewAccountApplicationForm" method="post">
				<div class = "row justify-content-center">
					<div class="col-6 my-3">
						<label class="bg-dark text-white" for="usernameInput"><b>Username:</b></label>
						<input type="text" class="form-control" name="reg_username" id="usernameInput" required />  
					</div>
				</div>
				<div class = "row justify-content-center">
					<div class="col-6 mt-3 mb-2">
						<label class="bg-dark text-white" for="passwordInput"><b>Password:</b></label>
						<input type="password" class="form-control" name="reg_password" id="passwordInput" required/> 
					</div>
				</div>
				<div class = "row justify-content-center">
					<div class="col-6 mt-2">
						<button type = "submit" class="btn btn-primary form-control">Submit</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<c:if test = "${sessionScope.message != null}">
		<div id="snackbar"></div>    
	</c:if>

  </body>
</html>