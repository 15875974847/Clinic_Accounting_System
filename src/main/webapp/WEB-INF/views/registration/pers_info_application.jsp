<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<!-- Date picker css -->
	<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css"/>
	<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="../res/images/icons/favicon.ico">
	<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="../css/myBackgrounds.css">
	
	
	<!-- JQuery, Bootstrap 4 -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<!-- Date picker -->
	<script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
	
    <!-- My JS -->
	<script type="text/javascript" src="../js/utils/utils.js"></script>
	<script type="text/javascript" src="../js/registration/datePickerConfig.js"></script>

  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">

	<div class = "container">
	
		<div class="text-center mt-5 mb-2">
			<h2 class="bg-secondary text-white">&darr;Please, fill up patient's personal info application&darr;</h2>
		</div>

		<form action="submitPersonalInformationForm" method="post">
			<div class = "row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="firstname"><b>First Name:</b></label>
					<input class="form-control" id="firstname" maxlength = 20 name="firstname" required />
				</div>
			</div>
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="midname"><b>Middle Name:</b></label>
					<input class="form-control" id="midname"  maxlength = 20 name="midname" required />
				</div>
			</div>			
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="lastnameInfo"><b>Last Name:</b></label>
					<input class="form-control" id="lastname"  maxlength = 20 name="lastname" required />
				</div>
			</div>
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="dobDatePicker"><b>Date of birth:</b></label>
					<input class="form-control" id="dobDatePicker" name="dob" value="1990-12-12" required />
				</div>
			</div>
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="phone"><b>Phone:</b></label>
					<input class="form-control" id="phone" maxlength=25 name="phone" required />
				</div>
			</div>
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="email"><b>Email:</b></label>
					<input class="form-control" id="email" maxlength=30 name="email" required />
				</div>
			</div>
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<label class="bg-dark text-white" for="homeAddress"><b>Home address:</b></label>
					<input class="form-control" id="homeAddress" maxlength=40 name="homeAddress" required />
				</div>
			</div>
			
			<div class="row justify-content-md-center my-3">
				<div class="col-6">
					<button type="submit" class="btn btn-primary form-control">Apply and sign in</button>
				</div>
			</div>
		</form>
	</div>


  </body>
</html>