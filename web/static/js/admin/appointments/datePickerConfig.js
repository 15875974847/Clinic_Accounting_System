$(document).ready(function () {
    $('#deleteAppointmentsAfterDate').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd',                      // ISO date format
    });
    document.getElementById("deleteAppointmentsAfterDate").value = returnCurrentDateIn_yyyyMMdd_Format();
});