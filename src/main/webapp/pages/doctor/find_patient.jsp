<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Doctors's Find Patient Page</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
	<meta name="author" content="Artique">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- My Page Favicon -->
	<link rel="icon" type="image/png" href="${context}/static/images/icons/favicon.ico">
<!-- Latest Bootstrap and datatables Bootstrap API ================================================-->
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css"/>
<!-- Latest Datatable's Bootstrap API ==============================================================-->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"/>
<!-- Datatable's Buttons, Responsivness and Selection ==============================================-->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.bootstrap4.min.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap4.min.css"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/select/1.3.1/css/select.bootstrap4.min.css"/>

	
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

<!-- Font Awesome for Glyphs -->
	<script src="https://kit.fontawesome.com/7685c16a3d.js" crossorigin="anonymous"></script>
	
<!-- My scripts -->
	<script type="text/javascript" src="${context}/static/js/doctor/find_patient/dataTableConfig.js"></script>
	
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
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarAltContent" aria-controls="navbarAltContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarAltContent">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="home">Home Page</a>
				<a class="nav-item nav-link" href="account">Account</a>
				<a class="nav-item nav-link" href="pers_info">Personal Information</a>
				<a class="nav-item nav-link" href="see_doctor">See Doctor</a>
				<a class="nav-item nav-link active" href="find_patient">Find Patient<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="my_appointments">My Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="../sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>
	
	<!-- content here -->
	<div class = "container">

        <div class="text-center my-3">
            <h3 class="bg-secondary text-white">&darr;All patients here&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtPatients" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Dob</th>
                        <th class="th-sm">Email</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Address</th>
						<th class="th-sm">Medical History</th>
						<th class="th-sm">Edit</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="patient" items="${requestScope.patients}">
						<tr>
							<td>${patient.id}</td>
							<td>${patient.firstname}</td>
							<td>${patient.middlename}</td>
							<td>${patient.lastname}</td>
							<td>${patient.dob.toString()}</td>
							<td>${patient.email}</td>
							<td>${patient.phone}</td>
							<td>${patient.address}</td>
							<td>${patient.medicalHistory}</td>
							<td>
								<div class="row justify-content-center mx-1">
									<button class="btn" data-toggle="modal" data-target="#editMedicalHistoryModal">
										<i class="fas fa-edit"></i>
									</button>
								</div>
							</td>	
						</tr>
					</c:forEach>
                </tbody>

                <tfoot>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Dob</th>
                        <th class="th-sm">Email</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Address</th>
						<th class="th-sm">Medical History</th>
						<th class="th-sm">Edit</th>
                    </tr>
                </tfoot>
            </table>
        </div> 
	</div>
	
	<!-- ====== Date picker modal ====================================================-->
	<div class="modal fade" id="editMedicalHistoryModal" tabindex="-1" role="dialog" aria-labelledby="editMedicalHistoryModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center" id="editMedicalHistoryModalLabel"><b>Here you can edit patient's medical history</b></h3>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "changePatientsMedicalHistory" method = "post">
					<input type="hidden" name="patientID" id="selectedPatientID"></input>
					<div class="modal-body">
						<div class="form-row my-3">
							<label class="text-danger" for="oldMedHistory"><b>Old medical history</b></label>	
							<textarea class="form-control" id="oldMedHistory" rows=5 readonly></textarea>
						</div>
						<div class="form-row my-3">
							<label class="text-danger" for="newMedHistory"><b>New medical history</b></label>	
							<textarea class="form-control" name="newMedicalHistory" id="newMedHistory" rows=5></textarea>
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