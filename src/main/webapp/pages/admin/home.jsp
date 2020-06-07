<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Admin's Home Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
<!-- Latest Bootstrap ================================================-->
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css"/>
<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="${context}/static/css/myBackgrounds.css">
	
<!-- JQuery, Bootstrap 4 -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>

  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">
	
	<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarAltContent" aria-controls="navbarAltContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarAltContent">
			<div class="navbar-nav">
				<a class="nav-item nav-link active" href="home">Home<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="events">Events</a>
				<a class="nav-item nav-link" href="doctors">Doctors</a>
				<a class="nav-item nav-link" href="patients">Patients</a>
				<a class="nav-item nav-link" href="appointments">Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="../sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>

	<div class="container mt-2">
		<div class="my-5">
			<h1 class="text-center text-primary">Howdy, admin!</h1>

			<div class="my-5 d-flex align-items-center flex-column justify-content-center h-100">
				<h3 class="text-center bg-secondary text-white">Here u can:</h3>
				<ul>
					<li>Manage events</li>
					<li>Manage doctors</li>
					<li>Manage patients</li>
					<li>Manage appointments</li>
				</ul>
			</div>
		</div>
	</div>
	
  </body>
</html>




