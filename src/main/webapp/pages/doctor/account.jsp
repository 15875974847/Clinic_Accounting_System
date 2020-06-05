<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Doctor's Account Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
<!-- Latest Bootstrap and datatables Bootstrap API ================================================-->
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css"/>
<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="${context}/static/css/myBackgrounds.css">
	
<!-- JQuery, Bootstrap 4 -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>
	
	<%-- Injecting error message if we have one --%>
	<c:if test = "${sessionScope.message != null}">
		<%-- Snackbar css and js function definition --%>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/snackbar.css">
		<script type="text/javascript" src="${context}/static/js/snackbar.js"></script>
		<%-- Displaying snackbar with error --%>
		<script>$(document).ready(function () {showPopupSnackbar('${sessionScope.message}');});</script>
	</c:if>
	
  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">
	
	<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarAltContent" aria-controls="navbarAltContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarAltContent">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="home">Home Page</a>
				<a class="nav-item nav-link active" href="account">Account<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="pers_info">Personal Information</a>
				<a class="nav-item nav-link" href="see_doctor">See Doctor</a>
				<a class="nav-item nav-link" href="find_patient">Find Patient</a>
				<a class="nav-item nav-link" href="my_appointments">My Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="../sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>

	<div class="container">
			<div class="text-center my-3">
				<h3 class="bg-secondary text-white">&darr;Account Information&darr;</h3>
			</div>
	
			<div class = "row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="username"><b>Username:</b></label>
					<input class="form-control" id="username" value="${requestScope.username}" readonly></input>   
				</div>
			</div>
			<div class = "row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="username"><b>Password:</b></label>
					<input class="form-control" id="password" value="${requestScope.password}" readonly></input>   
				</div>
			</div>
			
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<button class = "form-control btn btn-primary" data-toggle="modal" data-target="#editAccountInfoModal">Edit Account Information</button>
				</div>
			</div>
	</div>
	
	<!-- ====== Edit Personal Info Modal Form ====================================================-->
	<div class="modal fade" id="editAccountInfoModal" tabindex="-1" role="dialog" aria-labelledby="editAccountInfoLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-center" id="editAccountInfoLabel"><b>Edit Account Information</b></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "editAccountInfo" method = "post">
					<div class="modal-body">
						<div class="row col-12">
							<div class="col-6">
								<h5 class="text-center text-danger">Old Account Info</h5>
								<div class="col-12 my-2">
									<label for="oldUsername"><b>Username:</b></label>
									<input class="form-control" name="oldUsername" id="oldUsername" value="${requestScope.username}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldPassword"><b>Password:</b></label>
									<input class="form-control" name="oldPassword" id="oldPassword" value="${requestScope.password}" readonly></input>   
								</div>
							</div>
							<div class="col-6">
								<h5 class="text-center text-success">New Account Info</h5>
								<div class="col-12 my-2">
									<label for="newUsername"><b>Username:</b></label>
									<input class="form-control" name="newUsername" id="newUsername" value="" maxlength=30 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newPassword"><b>Password:</b></label>
									<input class="form-control" name="newPassword" id="newPassword" value="" maxlength=30 required></input>   
								</div>
							</div>
						</div>
					</div><!-- end of modal body -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Edit</button>
					</div>
				</form>
			</div><!--modal content-->
		</div><!--modal dialog-->
	</div><!--main modal content division-->

	<%-- Injecting message only if we have one --%>
	<c:if test = "${sessionScope.message != null}">
		<div id="snackbar"></div>
	</c:if>
	
  </body>
</html>