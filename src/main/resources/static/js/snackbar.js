function showPopupSnackbar(msg){
	const snack = document.getElementById("snackbar");
	snack.innerHTML = msg;
	snack.className = "show";
	setTimeout(function(){ snack.className = snack.className.replace("show", ""); }, 5000);
}
