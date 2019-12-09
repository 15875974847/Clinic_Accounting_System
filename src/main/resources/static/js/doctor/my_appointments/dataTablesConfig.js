// prevent showing modal when button has 'disabled' class
function showModal() {
    if(document.getElementById("endAppointmentButton").classList.contains('disabled') === false){
        $('#endAppointmentModal').modal('show');
    }
}

$(document).ready(function () {
    // working with DataTable API
    // as a patient appointment datatable description
    let asPatientAppointmentTable = $('#dtAsPatientAppointments').DataTable({
        "pagingType": "full_numbers", 																				//  first, last, next, prev and nums
        "order": [[ 0, "asc" ]],																					// firstly, sort by first row and in asc order

        "select": {"style": 'single'},																			// single selection
        "processing": true,																							// processing sign on inserting, search and sort
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
        "dom": "<'row mx-3 my-2'<'col-sm-12 col-md-6'i><'col-sm-12 col-md-6'f>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row mx-3 my-2'<'col-sm-12 col-md-5'<'asDoctorsAppointmentTableManageButtonsToolbar'>><'col-sm-12 col-md-7'p>>",
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
            '<button id="endAppointmentButton" class="btn btn-primary mx-2 disabled" data-toggle="modal" data-target="#endAppointmentModal" onclick="showModal()">End appointment</button>'+
        '</div>'
    );

    // setting event handlers on our table
    asDoctorAppointmentTable
        .on( 'deselect', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                // making button disabled when item deselected
                $("#endAppointmentButton").addClass('disabled');
            }
        } )
        .on( 'select', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                // remembering selected row data
                let selectedAppointment = asDoctorAppointmentTable.rows(indexes).data().toArray();
                // set to form's hidden fields equals selected row data
                document.getElementById("endAppointmentForm_PatientID").value = selectedAppointment[0][3];
                document.getElementById("endAppointmentForm_Date").value = selectedAppointment[0][0];
                document.getElementById("endAppointmentForm_NumberInQueue").value = selectedAppointment[0][1];
                // making buttons clickable when item selected
                $("#endAppointmentButton").removeClass('disabled');
            }
        } );
});