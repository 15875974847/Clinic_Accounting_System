<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
	<title>Doctor's Personal Info Page</title>
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
	<script type="text/javascript" src="../js/doctor/pers_info/datePickerConfig.js"></script>
	<script type="text/javascript" src="../js/doctor/pers_info/validators.js"></script>
	
	<%-- Injecting message if we only have one --%>
	<c:if test = "${sessionScope.message != null}">
		<%-- Snackbar css and js function definition --%>
		<link rel="stylesheet" type="text/css" href="../css/snackbar.css">
		<script type="text/javascript" src="../js/snackbar.js"></script>
		<%-- Displaying snackbar with error --%>
		<script>$(document).ready(function () {showPopupSnackbar('${sessionScope.message}');});</script>
	</c:if>
  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">
	
	<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="home">Home Page</a>
				<a class="nav-item nav-link" href="account">Account</a>
				<a class="nav-item nav-link active" href="pers_info">Personal Information<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="see_doctor">See Doctor</a>
				<a class="nav-item nav-link" href="find_patient">Find Patient</a>
				<a class="nav-item nav-link" href="my_appointments">My Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="../sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>

	<div class = "container">
		<div class="text-center mt-3 mb-2">
			<h3 class="bg-secondary text-white">&darr;Personal information as a patient&darr;</h3>
		</div>

		<div class = "row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="firstnameInfo"><b>First Name:</b></label>
				<input class="form-control" id="firstnameInfo" value="${requestScope.doctor.staffEntity.userInfo.firstName}" readonly></input>   
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="midnameInfo"><b>Middle Name:</b></label>
				<input class="form-control" id="midnameInfo" value="${requestScope.doctor.staffEntity.userInfo.middleName}" readonly></input>  
			</div>
		</div>			
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="lastnameInfo"><b>Last Name:</b></label>
				<input class="form-control" id="lastnameInfo" value="${requestScope.doctor.staffEntity.userInfo.lastName}" readonly></input>  
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="emailInfo"><b>Email:</b></label>
				<input class="form-control" id="emailInfo" value="${requestScope.doctor.staffEntity.userInfo.email}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="phoneInfo"><b>Phone:</b></label>
				<input class="form-control" id="phoneInfo" value="${requestScope.doctor.staffEntity.userInfo.phone}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="dobInfo"><b>Date of birth:</b></label>
				<input class="form-control" id="dobInfo" value="${requestScope.doctor.staffEntity.userInfo.dateOfBirth}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="addressInfo"><b>Address:</b></label>
				<input class="form-control" id="addressInfo" value="${requestScope.doctor.staffEntity.userInfo.address}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="medHistoryInfo"><b>Medical history:</b></label>
				<textarea class="form-control" id="medHistoryInfo" readonly>${requestScope.doctor.staffEntity.userInfo.medicalHistory}</textarea>
			</div>
		</div>
		
		
		<div class="text-center mt-5 mb-2">
			<h3 class="bg-secondary text-white">&darr;Personal information as a doctor&darr;</h3>
		</div>
		
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="specializationInfo"><b>Specialization:</b></label>
				<input class="form-control" id="specializationInfo" value="${requestScope.doctor.specialization}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="degreeInfo"><b>Degree:</b></label>
				<input class="form-control" id="degreeInfo" value="${requestScope.doctor.degree}" readonly></input>
			</div>
		</div>
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<label class="bg-dark text-white" for="salaryInfo"><b>Salary:</b></label>
				<input class="form-control" id="salaryInfo" value="${requestScope.doctor.staffEntity.salary} $$$" readonly></input>
			</div>
		</div>
		
		<div class="row justify-content-md-center my-3">
			<div class="col-6">
				<button class = "form-control btn btn-primary" data-toggle="modal" data-target="#editPersonalInfoModal">Edit Personal Information</button>
			</div>
		</div>
	</div>
	
	<!-- ====== Edit Personal Info Modal Form ====================================================-->
	<div class="modal fade" id="editPersonalInfoModal" tabindex="-1" role="dialog" aria-labelledby="editPersonalInfoLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-center" id="editPersonalInfoLabel"><b>Edit Personal Information</b></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="editPersonalInfoForm" action = "/doctor/editPersonalInfo" method = "post">
					<div class="modal-body">
						<div class="row col-12">
							<div class="col-6">
								<h5 class="text-center text-danger">Old Personal Info</h5>
								<div class="col-12 my-2">
									<label for="oldFirstnameInfo"><b>First Name:</b></label>
									<input class="form-control" id="oldFirstnameInfo" value="${requestScope.doctor.staffEntity.userInfo.firstName}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldMidnameInfo"><b>Middle Name:</b></label>
									<input class="form-control" id="oldMidnameInfo" value="${requestScope.doctor.staffEntity.userInfo.middleName}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldLastnameInfo"><b>Last Name:</b></label>
									<input class="form-control" id="oldLastnameInfo" value="${requestScope.doctor.staffEntity.userInfo.lastName}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldEmailInfo"><b>Email:</b></label>
									<input class="form-control" id="oldEmailInfo" value="${requestScope.doctor.staffEntity.userInfo.email}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldPhoneInfo"><b>Phone:</b></label>
									<input class="form-control" id="oldPhoneInfo" value="${requestScope.doctor.staffEntity.userInfo.phone}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldDOBInfo"><b>Date of birth:</b></label>
									<input class="form-control" id="oldDOBInfo" value="${requestScope.doctor.staffEntity.userInfo.dateOfBirth}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldAddressInfo"><b>Address:</b></label>
									<input class="form-control" id="oldAddressInfo" value="${requestScope.doctor.staffEntity.userInfo.address}" readonly></input>   
								</div>
								
								<div class="col-12 my-2">
									<label for="oldSpecInfo"><b>Specialization:</b></label>
									<input class="form-control" id="oldSpecInfo" value="${requestScope.doctor.specialization}" readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="oldDegreeInfo"><b>Degree:</b></label>
									<input class="form-control" id="oldDegreeInfo" value="${requestScope.doctor.degree}" readonly></input>   
								</div>
								
							</div>
							<div class="col-6">
								<h5 class="text-center text-success">New Personal Info</h5>
								<div class="col-12 my-2">
									<label for="newFirstnameInfo"><b>First Name:</b></label>
									<input class="form-control" name="newFirstname" id="newFirstnameInfo" value="${requestScope.doctor.staffEntity.userInfo.firstName}" maxlength=20 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newMidnameInfo"><b>Middle Name:</b></label>
									<input class="form-control" name="newMidname" id="newMidnameInfo" value="${requestScope.doctor.staffEntity.userInfo.middleName}" maxlength=20 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newLastnameInfo"><b>Last Name:</b></label>
									<input class="form-control" name="newLastname" id="newLastnameInfo" value="${requestScope.doctor.staffEntity.userInfo.lastName}" maxlength=20 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newEmailInfo"><b>Email:</b></label>
									<input class="form-control" name="newEmail" id="newEmailInfo" value="${requestScope.doctor.staffEntity.userInfo.email}" maxlength=30 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newPhoneInfo"><b>Phone:</b></label>
									<input class="form-control" name="newPhone" id="newPhoneInfo" value="${requestScope.doctor.staffEntity.userInfo.phone}" maxlength=25 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newDOBInfo"><b>Date of birth:</b></label>
									<input class="form-control" name="newDOB" id="newDOBInfo" value="2020-12-12" required readonly></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newAddressInfo"><b>Address:</b></label>
									<input class="form-control" name="newAddress" id="newAddressInfo" value="${requestScope.doctor.staffEntity.userInfo.address}" maxlength=40 required></input>   
								</div>
								
								<div class="col-12 my-2">
									<label for="newSpecInfo"><b>Specialization:</b></label>
									<input class="form-control" name="newSpecialization" id="newSpecInfo" value="${requestScope.doctor.specialization}" maxlength=40 required></input>   
								</div>
								<div class="col-12 my-2">
									<label for="newDegreeInfo"><b>Degree:</b></label>
									<input class="form-control" name="newDegree" id="newDegreeInfo" value="${requestScope.doctor.degree}" maxlength=40 required></input>   
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

	<div id="snackbar"></div>    <!-- this bar will be showing notifications and errors -->
	

  </body>
</html>