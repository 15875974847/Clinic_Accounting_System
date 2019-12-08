<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
	<title>Patient's Appointments</title>
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

<!-- My scripts -->
	<script type="text/javascript" src="../js/patient/my_appointments/dataTableConfig.js"></script>
  </head>
  
  <body class = "bg-light-green-mari" style="font-family: Ubuntu">
	
	<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="home">Home Page</a>
				<a class="nav-item nav-link" href="account">Account</a>
				<a class="nav-item nav-link" href="pers_info">Personal Information</a>
				<a class="nav-item nav-link" href="see_doctor">See Doctor</a>
				<a class="nav-item nav-link active" href="my_appointments">My Appointments<span class="sr-only">(current)</span></a>
			</div>
		</div>
		<form class="form-inline" action="/sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>
	
	<!-- content here -->
	<div class = "container">

        <div class="text-center my-3">
            <h3 class="bg-secondary text-white">&darr;Your appointments here&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtAppointments" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
                        <th class="th-sm">Doc's Specialization</th>
                        <th class="th-sm">Doc's Name</th>
                        <th class="th-sm">Doc's Midname</th>
                        <th class="th-sm">Doc's Lastname</th>
						<th class="th-sm">App's Date</th>
						<th class="th-sm">Number in queue</th>
						<th class="th-sm">Comment</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="appointment" items="${requestScope.appointments}">
						<tr>
							<td>${appointment.appointmentID.doctor.specialization}</td>
							<td>${appointment.appointmentID.doctor.staffEntity.userInfo.firstName}</td>
							<td>${appointment.appointmentID.doctor.staffEntity.userInfo.middleName}</td>
							<td>${appointment.appointmentID.doctor.staffEntity.userInfo.lastName}</td>
							<td>${appointment.appointmentID.date.toString()}</td>
							<td>${appointment.appointmentID.numberInQueue}</td>
							<td>${appointment.comment}</td>
						</tr>
					</c:forEach>
                </tbody>

                <thead>
                    <tr class="bg-dark text-white">
                        <th class="th-sm">Doc's Specialization</th>
                        <th class="th-sm">Doc's Name</th>
                        <th class="th-sm">Doc's Midname</th>
                        <th class="th-sm">Doc's Lastname</th>
						<th class="th-sm">App's Date</th>
						<th class="th-sm">Number in queue</th>
						<th class="th-sm">Comment</th>
                    </tr>
                </thead>
            </table>
        </div> 
	</div>

  </body>
</html>