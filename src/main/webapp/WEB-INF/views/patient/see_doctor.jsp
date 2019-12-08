<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
	<title>Patient's See Doctor Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="../res/images/icons/favicon.ico">
<!-- Latest Bootstrap and datatables Bootstrap API ================================================-->
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css"/>
<!-- Latest Datatable's Bootstrap API ==============================================================-->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"/>
<!-- Datatable's Buttons, Responsivness and Selection ==============================================-->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.bootstrap4.min.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap4.min.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.bootstrap4.min.css"/>
<!-- Date picker css -->
	<link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css"/>	
	
<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="../css/myBackgrounds.css">
	
	
<!--JQuery, Bootstrap 4, Buttons, Responsivness, selection here-->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>

<!-- Date picker -->
	<script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
<!-- Font Awesome for Glyphs -->
	<script src="https://kit.fontawesome.com/7685c16a3d.js" crossorigin="anonymous"></script>
	
<!-- My scripts -->
	<script type="text/javascript" src="../../utils/utils.js"></script> 
	<script type="text/javascript" src="../js/patient/see_doctor/dataTableConfig.js"></script>
	<script type="text/javascript" src="../js/patient/see_doctor/datePickerConfig.js"></script>
	
	<%-- Injecting message only if we have one --%>
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
				<a class="nav-item nav-link" href="pers_info">Personal Information</a>
				<a class="nav-item nav-link active" href="see_doctor">See Doctor<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="my_appointments">My Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="/sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>
	
	<!-- content here -->
	<div class = "container">

        <div class="text-center my-3">
            <h3 class="bg-secondary text-white">&darr;Over here all doctors available for you&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtDoctors" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
                        <th class="th-sm">Specialization</th>
						<th class="th-sm">Degree</th>
						<th class="th-sm">See Doctor</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="doctor" items="${requestScope.doctors}">
						<tr>
							<td>${doctor.id}</td>
							<td>${doctor.staffEntity.userInfo.firstName}</td>
							<td>${doctor.staffEntity.userInfo.middleName}</td>
							<td>${doctor.staffEntity.userInfo.lastName}</td>
							<td>${doctor.specialization}</td>
							<td>${doctor.degree}</td>
							<td>
								<div class="row justify-content-center">
									<button class="btn" data-toggle="modal" data-target="#datePickerModal">
										<i class=" fas fa-address-book"></i>
									</button>
								</div>
							</td>	
						</tr>
					</c:forEach>
                </tbody>

                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
                        <th class="th-sm">Specialization</th>
						<th class="th-sm">Degree</th>
						<th class="th-sm">See Doctor</th>
                    </tr>
                </thead>
            </table>
        </div> 
	</div>
	
	<!-- ====== Date picker modal ====================================================-->
	<div class="modal fade" id="datePickerModal" tabindex="-1" role="dialog" aria-labelledby="datePickerModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-center" id="datePickerModalLabel"><b>Choose date please</b></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "/patient/makeAppointment" method = "post">
					<div class="modal-body">
						<div class="container my-5">
							<input type="hidden" id="selectedDoctorID" name="doctorID" value="">
							<div class="row justify-content-center my-3">
								<input class="form-control" id="appointmentDatePicker" name="date" value="2020-12-12" required readonly></input> 
							</div>
							<div class="row justify-content-center my-3">
								<label for="formCommentSection"> You can leave the comment if you want: </label>
								<textarea class="form-control" id="formCommentSection" name="comment" maxlength="50"></textarea> 
							</div>
						</div>
					</div><!-- end of modal body -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Make An Appointment</button>
					</div>
				</form>
			</div><!--modal content-->
		</div><!--modal dialog-->
	</div><!--main modal content division-->

	<div id="snackbar"></div>    <!-- this bar will be showing notifications and errors -->
	
	

  </body>
</html>