// this func is used to display messages for 3 secs
function showPopupSnackbar(msg){
	var x = document.getElementById("snackbar");
	document.getElementById("snackbar").innerHTML = msg;
	x.className = "show";
	setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
}
