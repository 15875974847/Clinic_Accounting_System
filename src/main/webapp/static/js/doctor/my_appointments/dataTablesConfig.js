$(document).ready(function () {
    // working with DataTable API
    // as a patient appointment datatable description
    let asPatientAppointmentTable = $('#dtAsPatientAppointments').DataTable({
        "pagingType": "full_numbers", 																			// first, last, next, prev and nums
        "order": [[ 0, "asc" ]],																			    // firstly, sort by first row and in asc order

        "select": {"style": 'single'},																			// single selection
        "processing": true,																						// processing sign on inserting, search and sort
        "responsive": true,																						// and lastly it's set responsive property
        "columnDefs": [                                                                                         // don't show id                                                                                            // making id field not visible for user
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ]
    });

    // as a doctor appointment datatable description
    let asDoctorAppointmentTable = $('#dtAsDoctorAppointments').DataTable({
        "pagingType": "full_numbers",
        "order": [[ 0, "asc" ]],
        "dom": "<'row mx-3 my-2'<'col-sm-12 col-md-6'<'asDoctorsAppointmentTableManageButtonsToolbar'>><'col-sm-12 col-md-6'f>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row mx-3 my-2'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        "select": {"style": 'single'},
        "processing": true,
        "responsive": true,
        "columnDefs": [                                                                                                                                                                                        // making id field not visible for user
            {
                "targets": [ 3 ],
                "visible": false,
                "searchable": false
            }
        ]
    });

    // setting content to aforementioned table button's toolbar
    $("div.asDoctorsAppointmentTableManageButtonsToolbar").html(
        '<div>'+
            '<button id="closeAppointmentButton" class="btn btn-primary mx-2 disabled">Close appointment</button>'+
        '</div>'
    );

    // set click listener on 'end appointment' button
    $("#closeAppointmentButton").click(function(){
        if($("#closeAppointmentButton").hasClass('disabled')) {
            e.stopPropagation();
        } else{
            // show modal if button isn't disabled
            $("#closeAppointmentModal").modal("show");
        }
    });

    // setting event handlers on our table
    asDoctorAppointmentTable
        .on( 'deselect', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                // making button disabled when item deselected
                $("#closeAppointmentButton").addClass('disabled');
            }
        } )
        .on( 'select', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                // remember selected row data
                let selectedAppointment = asDoctorAppointmentTable.rows(indexes).data().toArray();
                // set to form's hidden fields equals selected row data
                document.getElementById("closeAppointmentForm_PatientID").value = selectedAppointment[0][3];
                document.getElementById("closeAppointmentForm_Date").value = selectedAppointment[0][0];
                document.getElementById("closeAppointmentForm_NumberInQueue").value = selectedAppointment[0][1];
                // making buttons clickable when item selected
                $("#closeAppointmentButton").removeClass('disabled');
            }
        } );
});