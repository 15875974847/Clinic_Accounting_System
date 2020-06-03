<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
  <head>
	<title>Admin's Doctors Page</title>
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
	<script type="text/javascript" src="${context}/static/js/admin/doctors/dataTableConfig.js"></script>
	<script type="text/javascript" src="${context}/static/js/admin/doctors/datePickerConfig.js"></script>
	
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
				<a class="nav-item nav-link active" href="doctors">Doctors<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="patients">Patients</a>
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
            <h3 class="bg-secondary text-white">&darr;Here you can manage doctors&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtDoctors" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Date of birth</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Email</th>
						<th class="th-sm">Address</th>
                        <th class="th-sm">Specialization</th>
						<th class="th-sm">Degree</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="doctor" items="${requestScope.doctors}">
						<tr>
							<td>${doctor.id}</td>
							<td>${doctor.staffEntity.patient.firstname}</td>
							<td>${doctor.staffEntity.patient.middlename}</td>
							<td>${doctor.staffEntity.patient.lastname}</td>
							<td>${doctor.staffEntity.patient.dob}</td>
							<td>${doctor.staffEntity.patient.phone}</td>
							<td>${doctor.staffEntity.patient.email}</td>
							<td>${doctor.staffEntity.patient.address}</td>
							<td>${doctor.specialization}</td>
							<td>${doctor.degree}</td>
						</tr>
					</c:forEach>
                </tbody>

                <tfoot>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">First Name</th>
                        <th class="th-sm">Middle Name</th>
                        <th class="th-sm">Last Name</th>
						<th class="th-sm">Date of birth</th>
						<th class="th-sm">Phone</th>
						<th class="th-sm">Email</th>
						<th class="th-sm">Address</th>
                        <th class="th-sm">Specialization</th>
						<th class="th-sm">Degree</th>
                    </tr>
                </tfoot>
            </table>
        </div> 
		
	</div>
	
	<!-- ====== Add new Doctor Modal Form ====================================================-->
	<div class="modal fade" id="addNewDoctorModal" tabindex="-1" role="dialog" aria-labelledby="addNewDoctorModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title text-center" id="addNewDoctorModalLabel"><b>Add new doctor</b></h2>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "/admin/addNewDoctor" method = "post">
					<div class="modal-body">
						<div class="row justify-content-md-center my-3">
							<div class="col-8">
							
								<div class="row mt-4 mb-2">
									<h4 class="bg-secondary text-white text-center">&darr;Doctor's account information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="newDocsUsername"><b>Username:</b></label>
									<input class="form-control" id="newDocsUsername" name = "username" maxlength=20 required></input>   
								</div>
								<div class="row my-2">
									<label for="newDocsPassword"><b>Password:</b></label>
									<input class="form-control" id="newDocsPassword" name = "password" maxlength=20 required></input>   
								</div>
							
								<div class="row mt-4 mb-2">
									<h4 class="bg-secondary text-white text-center">&darr;Doctor's personal information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="newDocsFirstname"><b>First Name:</b></label>
									<input class="form-control" id="newDocsFirstname" name = "firstname" maxlength=20 required></input>   
								</div>
								<div class="row my-2">
									<label for="newDocsMidname"><b>Middle Name:</b></label>
									<input class="form-control" id="newDocsMidname" name = "midname" maxlength=20 required></input>
								</div>
								<div class="row my-2">
									<label for="newDocsLastname"><b>Last Name:</b></label>
									<input class="form-control" id="newDocsLastname" name = "lastname" maxlength=20 required></input>
								</div>
								<div class="row my-2">
									<label for="newDocsDOB"><b>Date of birth:</b></label>
									<input class="form-control" id="newDocsDOB" name = "dob" value="1990-12-12" required></input>
								</div>
								<div class="row my-2">
									<label for="newDocsPhone"><b>Phone:</b></label>
									<input class="form-control" id="newDocsPhone" name = "phone" maxlength=25 required></input>
								</div>
								<div class="row my-2">
									<label for="newDocsEmail"><b>Email:</b></label>
									<input class="form-control" id="newDocsEmail" name = "email" maxlength=30 required></input>
								</div>
								<div class="row my-2">
									<label for="newDocsAddress"><b>Address:</b></label>
									<input class="form-control" id="newDocsAddress" name = "address" maxlength=50 required></input>
								</div>
								
								<div class="row mt-4 mb-2">
									<h4 class="bg-secondary text-white text-center">&darr;Doctor's clinic information&darr;</h4>
								</div>
								<div class="row my-2">
									<label for="newDocsSalary"><b>Salary:</b></label>
									<input type="number" class="form-control" id="newDocsSalary" name = "salary" min="20000" max="400000" required></input>   
								</div>
								<div class="row my-2">
									<label for="newDocsDegree"><b>Degree:</b></label>
									<input class="form-control" id="newDocsDegree" name = "degree" maxlength=40 required></input>   
								</div>
								<div class="row my-2">
									<label for="newDocsSpecialization"><b>Specialization:</b></label>
									<input class="form-control" id="newDocsSpecialization" name = "specialization" maxlength=40 required></input>   
								</div>
								
							</div>
						</div>
					</div><!-- end of modal body -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Add doctor</button>
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