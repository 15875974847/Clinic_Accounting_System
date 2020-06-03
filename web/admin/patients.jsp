<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Admin's Patients Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
<!-- Latest Bootstrap ================================================-->
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
	<link rel="stylesheet" type="text/css" href="${context}/static/css/myBackgrounds.css">
	
	
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
	
<!-- My scripts -->
	<script type="text/javascript" src="${context}/static/js/utils/utils.js"></script>
	<script type="text/javascript" src="${context}/static/js/admin/patients/dataTableConfig.js"></script>
	<script type="text/javascript" src="${context}/static/js/admin/patients/datePickerConfig.js"></script>
	
	<%-- Injecting message only if we have one --%>
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
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="home">Home</a>
				<a class="nav-item nav-link" href="events">Events</a>
				<a class="nav-item nav-link" href="doctors">Doctors</a>
				<a class="nav-item nav-link active" href="patients">Patients<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="appointments">Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="/sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>
	
	<!-- content here -->
	<div class = "container">

        <div class="text-center my-3">
            <h3 class="bg-secondary text-white">&darr;Here you can manage patients&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtPatients" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
						<th class="th-sm">Role</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Date of birth</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Email</th>
						<th class="th-sm">Address</th>
						<th class="th-sm">Medical history</th>
                        <th class="th-sm">Username</th>
						<th class="th-sm">Password</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="patient" items="${requestScope.patients}">
						<tr>
							<td>${patient.id}</td>
							<td>${patient.user.role}</td>
							<td>${patient.firstname}</td>
							<td>${patient.middlename}</td>
							<td>${patient.lastname}</td>
							<td>${patient.dob}</td>
							<td>${patient.phone}</td>
							<td>${patient.email}</td>
							<td>${patient.address}</td>
							<td>${patient.medicalHistory}</td>
							<td>${patient.user.username}</td>
							<td>${patient.user.password}</td>
						</tr>
					</c:forEach>
                </tbody>

                <tfoot>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
						<th class="th-sm">Role</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Date of birth</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Email</th>
						<th class="th-sm">Address</th>
						<th class="th-sm">Medical history</th>
                        <th class="th-sm">Username</th>
						<th class="th-sm">Password</th>
                    </tr>
                </tfoot>
            </table>
        </div> 
		
	</div>
	
	<!-- ====== Edit Patient Modal Form ====================================================-->
	<div class="modal fade" id="editPatientModal" tabindex="-1" role="dialog" aria-labelledby="editPatientModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title text-center" id="editPatientModalLabel"><b>Edit patient information</b></h2>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "/admin/editPatient" method = "post">
					<div class="modal-body">
					
						<input type="hidden" id="editPatientID" name="patientID" />
					
						<div class="row my-2 mx-2">
						
							<div class="col-6">
								<div class="row my-2">
									<h4 class="bg-secondary text-danger text-center">&darr;Old patient's account information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="oldPatUsername"><b>Username:</b></label>
									<input class="form-control" id="oldPatUsername" readonly></input>   
								</div>
								<div class="row my-2">
									<label for="oldPatPassword"><b>Password:</b></label>
									<input class="form-control" id="oldPatPassword" readonly></input>   
								</div>
							</div>
							
							<div class="col-6">
								<div class="row mt-4 mb-2">
									<h4 class="bg-secondary text-success text-center">&darr;New patient's account information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="newPatUsername"><b>Username:</b></label>
									<input class="form-control" id="newPatUsername" name = "username" maxlength=20 required></input>   
								</div>
								<div class="row my-2">
									<label for="newPatPassword"><b>Password:</b></label>
									<input class="form-control" id="newPatPassword" name = "password" maxlength=20 required></input>   
								</div>
							</div>
							
						</div>
						
						
						<div class="row my-2 mx-2">
						
							<div class="col-6">
								<div class="row my-2">
									<h4 class="bg-secondary text-danger text-center">&darr;Old patient's personal information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="oldPatFirstname"><b>First Name:</b></label>
									<input class="form-control" id="oldPatFirstname" readonly></input>   
								</div>
								<div class="row my-2">
									<label for="oldPatMidname"><b>Middle Name:</b></label>
									<input class="form-control" id="oldPatMidname" readonly></input>
								</div>
								<div class="row my-2">
									<label for="oldPatLastname"><b>Last Name:</b></label>
									<input class="form-control" id="oldPatLastname" readonly></input>
								</div>
								<div class="row my-2">
									<label for="oldPatDOB"><b>Date of birth:</b></label>
									<input class="form-control" id="oldPatDOB" value="1990-12-12" readonly></input>
								</div>
								<div class="row my-2">
									<label for="oldPatPhone"><b>Phone:</b></label>
									<input class="form-control" id="oldPatPhone" readonly></input>
								</div>
								<div class="row my-2">
									<label for="oldPatEmail"><b>Email:</b></label>
									<input class="form-control" id="oldPatEmail" readonly></input>
								</div>
								<div class="row my-2">
									<label for="oldPatAddress"><b>Address:</b></label>
									<input class="form-control" id="oldPatAddress" readonly></input>
								</div>
							</div>
							
							<div class="col-6">
								<div class="row my-2">
									<h4 class="bg-secondary text-success text-center">&darr;New patient's personal information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="newPatFirstname"><b>First Name:</b></label>
									<input class="form-control" id="newPatFirstname" name = "firstname" maxlength=20 required></input>   
								</div>
								<div class="row my-2">
									<label for="newPatMidname"><b>Middle Name:</b></label>
									<input class="form-control" id="newPatMidname" name = "midname" maxlength=20 required></input>
								</div>
								<div class="row my-2">
									<label for="newPatLastname"><b>Last Name:</b></label>
									<input class="form-control" id="newPatLastname" name = "lastname" maxlength=20 required></input>
								</div>
								<div class="row my-2">
									<label for="newPatDOB"><b>Date of birth:</b></label>
									<input class="form-control" id="newPatDOB" name = "dob" value="1990-12-12" required></input>
								</div>
								<div class="row my-2">
									<label for="newPatPhone"><b>Phone:</b></label>
									<input class="form-control" id="newPatPhone" name = "phone" maxlength=25 required></input>
								</div>
								<div class="row my-2">
									<label for="newPatEmail"><b>Email:</b></label>
									<input class="form-control" id="newPatEmail" name = "email" maxlength=30 required></input>
								</div>
								<div class="row my-2">
									<label for="newPatAddress"><b>Address:</b></label>
									<input class="form-control" id="newPatAddress" name = "address" maxlength=50 required></input>
								</div>
							</div>
							
						</div>
							
					</div><!-- end of modal body -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Edit patient info</button>
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