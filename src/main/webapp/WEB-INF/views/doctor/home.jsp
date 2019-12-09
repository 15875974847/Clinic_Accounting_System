<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII" %>
<%@ page isELIgnored = "false" %>
<%@ page session = "true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
  <head>
	<title>Doctors's Home Page</title>
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
<!-- Custom checkboxes -->
	<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
<!-- My CSS -->
	<link rel="stylesheet" type="text/css" href="../css/myBackgrounds.css">
	
	
<!-- JQuery, Bootstrap 4, Buttons, Responsivness, selection here -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap4.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
<!-- Custom checkbox -->
	<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>

<!-- My scripts -->
	<script type="text/javascript" src="../../utils/utils.js"></script> 
	<script type="text/javascript" src="../js/doctor/home/datePickerConfig.js"></script>
	<script type="text/javascript" src="../js/doctor/home/dataTableConfig.js"></script>
	
	<%-- Injecting error message if we have one --%>
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
				<a class="nav-item nav-link active" href="home">Home Page<span class="sr-only">(current)</span></a>
				<a class="nav-item nav-link" href="account">Account</a>
				<a class="nav-item nav-link" href="pers_info">Personal Information</a>
				<a class="nav-item nav-link" href="see_doctor">See Doctor</a>
				<a class="nav-item nav-link" href="find_patient">Find Patient</a>
				<a class="nav-item nav-link" href="my_appointments">My Appointments</a>
			</div>
		</div>
		<form class="form-inline" action="/sign_out" method = "post">
			<button class="btn btn-info btn-lg my-2 my-sm-0" type="submit">Sign out</button>
		</form>
	</nav>
	
	<!-- content here -->
	<div class = "container">

	    <div class="text-center my-5">
            <h1 class="text-primary"><b>Welcome, doctor ${requestScope.user}!!!</b></h1>
        </div>

        <div class="text-center my-2">
            <h3 class="bg-secondary text-white">&darr;Over here events available for you&darr;</h3>
        </div>
	
		<div class="table-responsive">
            <table id="dtEvents" class="table table-hover table-bordered table-sm bg-info text-white">
                <thead>
                    <tr class="bg-dark text-white">
						<th class="th-sm">ID</th>
                        <th class="th-sm">Event</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Start date</th>
                        <th class="th-sm">End Date</th>
						<th class="th-sm">Only for personal</th>
                    </tr>
                </thead>

                <tbody>
					<c:forEach var="event" items="${requestScope.events}">
						<tr>
							<td>${event.id}</td>
							<td>${event.header}</td>
							<td>${event.content}</td>
							<td>${event.startDate.toString()}</td>
							<td>${event.endDate.toString()}</td>
							<td>
								<c:choose>
									<c:when test = "${event.onlyForPersonal == true}>
										Yes
									</c:when>
									<c:otherwise>
										No
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
                </tbody>

                <thead>
                    <tr class="bg-dark text-white">
                        <th class="th-sm">ID</th>
                        <th class="th-sm">Event</th>
                        <th class="th-sm">Description</th>
                        <th class="th-sm">Start date</th>
                        <th class="th-sm">End Date</th>
						<th class="th-sm">Only for personal</th>
                    </tr>
                </thead>
            </table>
        </div> 
	</div>
	
	<!-- ====== Edit Personal Info Modal Form ====================================================-->
	<div class="modal fade" id="addNewEventModal" tabindex="-1" role="dialog" aria-labelledby="addNewEventModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title text-center" id="addNewEventModalLabel"><b>Add new event</b></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action = "/doctor/addNewEvent" method = "post">
					<div class="modal-body">
						<div class="row justify-content-md-center my-3">
							<div class="col-8">
								<div class="row my-2">
									<label for="newEventHeader"><b>Header:</b></label>
									<input class="form-control" id="newEventHeader" name = "header" maxlength=50 required></input>   
								</div>
								<div class="row my-2">
									<label for="newEventContent"><b>Content:</b></label>
									<textarea class="form-control" id="newEventContent" name = "content" rows=10 required></textarea>   
								</div>
								<div class="row my-2">
									<label for="newEventStartDate"><b>Start date:</b></label>
									<input class="form-control" id="newEventStartDate" name = "start_date" required></input>   
								</div>
								<div class="row my-2">
									<label for="newEventEndDate"><b>End date:</b></label>
									<input class="form-control" id="newEventEndDate" name = "end_date" required></input>   
								</div>
								<div class="row my-2">
									<div class="form-check form-check-inline">
										<label for="newEventOnlyForPersonalCB" class="form-check-label">Only for personal</label>
										<input type="checkbox" id="newEventOnlyForPersonalCB" name = "only_for_personal" checked data-toggle="toggle" data-on="Yes" data-off="No" data-onstyle="success" data-offstyle="danger">
									</div>
								</div>
							</div>
						</div>
					</div><!-- end of modal body -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Add event</button>
					</div>
				</form>
			</div><!--modal content-->
		</div><!--modal dialog-->
	</div><!--main modal content division-->

	<div id="snackbar"></div>

  </body>
</html>



