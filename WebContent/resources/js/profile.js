
var modal = document.getElementById('myModal');

var close = document.getElementsByClassName("close");
for(i=0; i<close.length; i++) {
	close[i].onclick = function() {
		document.getElementById('myModal').style.display = "none";
	}
   }
window.onclick = function(event) {
 if (event.target == modal) {
	 document.getElementById('myModal').style.display = "none";
 }
}

function displayeditprofileform(x)
{
	document.getElementById('modelcontainer').innerHTML=document.getElementById('profileformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	document.getElementById('userIdForEdit').value=x;
	
}
