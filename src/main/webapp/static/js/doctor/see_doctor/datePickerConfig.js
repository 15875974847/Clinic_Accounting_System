$(document).ready(function () {
    $('#appointmentDatePicker').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd'                       // choose american date format
    });
    document.getElementById("appointmentDatePicker").value = returnCurrentDateIn_yyyyMMdd_Format();
});