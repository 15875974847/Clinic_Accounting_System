// stop event propagation of the form if all parameters are equal
$('editPersonalInfoForm').on('submit', function(e) {
    if(document.getElementById("oldFirstnameInfo").value === document.getElementById("newFirstnameInfo").value
        && document.getElementById("oldMidnameInfo").value === document.getElementById("newMidnameInfo").value
        && document.getElementById("oldLastnameInfo").value === document.getElementById("newLastnameInfo").value
        && document.getElementById("oldEmailInfo").value === document.getElementById("newEmailInfo").value
        && document.getElementById("oldPhoneInfo").value === document.getElementById("newPhoneInfo").value
        && document.getElementById("oldDOBInfo").value === document.getElementById("newDOBInfo").value
        && document.getElementById("oldAddressInfo").value === document.getElementById("newAddressInfo").value
        && document.getElementById("oldSpecInfo").value === document.getElementById("newSpecInfo").value
        && document.getElementById("oldDegreeInfo").value === document.getElementById("newDegreeInfo").value
    ){
        alert("You entered same information, we cannot proceed form submission!");
        e.preventDefault();
    }
});
