$(document).ready(function () {
    // working with DataTable API
    let table = $('#dtEvents').DataTable({
        "pagingType": "full_numbers", 																				//  first, last, next, prev and nums
        "order": [[ 0, "asc" ]],																					// firstly, sort by first row and in asc order

        "select": {"style": 'multiple'},																			// multiple selection
        "processing": true,																							// processing sign on inserting, search and sort
        "responsive": true																							// and lastly it's set responsive property
    });
});