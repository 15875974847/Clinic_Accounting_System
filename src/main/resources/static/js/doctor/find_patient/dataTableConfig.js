$(document).ready(function () {

    // working with DataTable API
    let table = $('#dtPatients').DataTable({
        "pagingType": "full_numbers", 																				//  first, last, next, prev and nums
        "order": [[ 0, "asc" ]],																					// firstly, sort by first row and in asc order
        "select": {"style": 'single'},																				// only single selection!
        "processing": true,																							// processing sign on inserting, search and sort
        "responsive": true,																							// and lastly it's set responsive property
        "columnDefs": [                                                                                             // making id field not visible for user
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ]
    });

    // setting event handlers on our table
    table
        .on( 'select', function ( e, dt, type, indexes ) {
            // remembering rows(actually, row) we are selected
            let selectedDocInfoRows = table.rows(indexes).data().toArray();
            // when table row is selected set selectedPatientID equals to patientID, so it will be ready to go to server
            document.getElementById("selectedPatientID").value = selectedDocInfoRows[0][0];         // column 0 is id as usual
            document.getElementById("oldMedHistory").value = selectedDocInfoRows[0][8];             // 8 - because in this column we have selected patient's medical history
            document.getElementById("newMedHistory").value = selectedDocInfoRows[0][8];             // duplicating old and new info
        } );

});