$(document).ready(function () {
    // add toggling function class description
    $('#dtEvents').on('init.dt', function() {
        $('.btn-form-toggling')
            .attr('data-toggle', 'modal')
            .attr('data-target', '#addNewEventModal');
    });

    // working with DataTable API
    let table = $('#dtEvents').DataTable({
        "pagingType": "full_numbers", 																				//  first, last, next, prev and nums
        "order": [[ 0, "asc" ]],																					// firstly, sort by first row and in asc order
        "dom": 'Bfrtip',                                                                                            // alignment of dt elements
        "buttons": [
            {
                text: 'Add event',
                action: function ( e, dt, node, config ) {
                    // for now nothing is here
                },
                className: 'btn btn-primary btn-form-toggling'
            }
        ],
        "columnDefs": [                                                                                             // making id field not visible for user
            {
                "targets": [ 0 ],
                "visible": false,
                "searchable": false
            }
        ],
        "select": {"style": 'multiple'},																			// multiple selection
        "processing": true,																							// processing sign on inserting, search and sort
        "responsive": true																							// and lastly it's set responsive property
    });
});