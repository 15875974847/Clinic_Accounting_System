$(document).ready(function () {

    // working with DataTable API
    let patientsTable = $('#dtPatients').DataTable({
        "pagingType": "full_numbers",
        "order": [[ 0, "asc" ]],
        "dom": "<'row mx-3 my-2 font-italic'<'col-6'<'dtPatientsButtonToolbar'>><'col-6'f>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row mx-3 my-2'<'col-6'i><'col-6'p>>",
        "columnDefs": [
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [ 10 ],
                "visible": false,
                "searchable": false
            },
            {
                "targets": [ 11 ],
                "visible": false,
                "searchable": false
            }
        ],
        "select": {"style": 'single'},
        "processing": true,
        "responsive": true
    });

    // setting content to aforementioned table button's toolbar
    $("div.dtPatientsButtonToolbar").html(
        '<div class="row col-12">'+
            '<button id="editPatientButton" class="btn btn-primary mx-2 disabled" disabled data-toggle="modal" data-target="#editPatientModal">Edit</button>'+
            '<form action="/admin/deletePatient" method="post">' +
                '<input type="hidden" id="deletePatientID" name="patientID"/>' +
                '<button type="submit" id="deletePatientButton" class="btn btn-secondary mx-2 disabled" disabled >Delete</button>' +
            '</form>'+
        '</div>'
    );

    // setting custon action on select and deselect row
    patientsTable
        .on( 'deselect', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                // changing edit button style
                $("#editPatientButton").addClass('disabled');
                // changing edit button property
                $('#editPatientButton').prop('disabled', true);
                // changing delete button style
                $("#deletePatientButton").addClass('disabled');
                // changing delete button property
                $('#deletePatientButton').prop('disabled', true);
            }
        } )
        .on( 'select', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                let selectedPatient = patientsTable.rows(indexes).data().toArray();
                // set eventID to know which one to delete
                document.getElementById("editPatientID").value = selectedPatient[0][0];
                document.getElementById("deletePatientID").value = selectedPatient[0][0];

                document.getElementById("oldPatUsername").value = selectedPatient[0][10];
                document.getElementById("oldPatPassword").value = selectedPatient[0][11];
                document.getElementById("oldPatFirstname").value = selectedPatient[0][2];
                document.getElementById("oldPatMidname").value = selectedPatient[0][3];
                document.getElementById("oldPatLastname").value = selectedPatient[0][4];
                document.getElementById("oldPatDOB").value = selectedPatient[0][5];
                document.getElementById("oldPatPhone").value = selectedPatient[0][6];
                document.getElementById("oldPatEmail").value = selectedPatient[0][7];
                document.getElementById("oldPatAddress").value = selectedPatient[0][8];

                document.getElementById("newPatUsername").value = selectedPatient[0][10];
                document.getElementById("newPatPassword").value = selectedPatient[0][11];
                document.getElementById("newPatFirstname").value = selectedPatient[0][2];
                document.getElementById("newPatMidname").value = selectedPatient[0][3];
                document.getElementById("newPatLastname").value = selectedPatient[0][4];
                document.getElementById("newPatDOB").value = selectedPatient[0][5];
                document.getElementById("newPatPhone").value = selectedPatient[0][6];
                document.getElementById("newPatEmail").value = selectedPatient[0][7];
                document.getElementById("newPatAddress").value = selectedPatient[0][8];
                // changing edit button style
                $("#editPatientButton").removeClass('disabled');
                // changing edit button property
                $('#editPatientButton').prop('disabled', false);
                // changing delete button style
                $("#deletePatientButton").removeClass('disabled');
                // changing delete button property
                $('#deletePatientButton').prop('disabled', false);
            }
        } );

});