$(document).ready(function () {

    // working with DataTable API
    $('#dtAppointments').DataTable({
        "pagingType": "full_numbers",
        "order": [[ 0, "asc" ]],
        "dom": "<'row mx-3 my-2 font-italic'<'col-6'<'dtAppointmentsButtonToolbar'>><'col-6'f>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row mx-3 my-2'<'col-6'i><'col-6'p>>",
        "select": {"style": 'single'},
        "processing": true,
        "responsive": true
    });

    // setting content to aforementioned table button's toolbar
    $("div.dtAppointmentsButtonToolbar").html(
        '<div class="row col-12">'+
            '<button id="deleteButton" class="btn btn-primary mx-2" data-toggle="modal" data-target="#deleteAppointmentsModal">Delete by date</button>'+
        '</div>'
    );

});