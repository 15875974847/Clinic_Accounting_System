$(document).ready(function () {
    $('#newDOBInfo').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd'                       // choose american date format
    });
    document.getElementById("newDOBInfo").value = returnCurrentDateIn_yyyyMMdd_Format();
});