$(document).ready(function () {

    // working with DataTable API
    let table = $('#dtEvents').DataTable({
        "pagingType": "full_numbers",
        "order": [[ 0, "asc" ]],
        "dom": "<'row mx-3 my-2 font-italic'<'col-4'<'dtEventsButtonToolbar'>><'col-8'f>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row mx-3 my-2'<'col-6'i><'col-6'p>>",
        "buttons": [
            {
                text: 'Add event',
                action: function ( e, dt, node, config ) {
                    // for now nothing is here
                },
                className: 'btn btn-primary btn-form-toggling'
            }
        ],
        "columnDefs": [
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

    // setting content to aforementioned table button's toolbar
    $("div.dtEventsButtonToolbar").html(
        '<div>'+
            '<button id="endAppointmentButton" class="btn btn-primary" data-toggle="modal" data-target="#addNewEventModal">Add event</button>'+
        '</div>'
    );

});