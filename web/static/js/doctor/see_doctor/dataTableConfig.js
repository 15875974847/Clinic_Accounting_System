// wrapping our script to anonimus and self-executed function to avoid variable collisions
$(document).ready(function () {

    // working with DataTable API
    let table = $('#dtDoctors').DataTable({
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
            const selectedDocInfoRows = table.rows(indexes).data().toArray();
            // setting tableDoctorID to selectedDoctorID, so it will be ready to go to server
            document.getElementById("selectedDoctorID").value = selectedDocInfoRows[0][0];             // 0:0 cuz we have only 1 row and id is 1 column
        } );

});