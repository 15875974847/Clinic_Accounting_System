$(document).ready(function () {
    $('#newEventStartDate').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd',                      // choose american date format
    });
    document.getElementById("newEventStartDate").value = returnCurrentDateIn_yyyyMMdd_Format();

    $('#newEventEndDate').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd',                       // choose american date format
    });
    document.getElementById("newEventEndDate").value = returnCurrentDateIn_yyyyMMdd_Format();
});