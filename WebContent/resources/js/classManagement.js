
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
function displaystudentdetails(x){
	var students=document.getElementsByClassName('studentsdesc');
	 for(i=0; i<students.length; i++) {
		 students[i].style.display = "none";
	    }
	 document.getElementById(x).style.display = "block";
	
}
function displayClassdetails(x)
{
	document.getElementById('classIdSelected').value=x;
	document.getElementById('myTable').style.width="90%";
	var classes=document.getElementsByClassName('classesdesc');
	 for(i=0; i<classes.length; i++) {
		 classes[i].style.display = "none";
	    }
	 var students=document.getElementsByClassName('studentsdesc');
	 for(i=0; i<students.length; i++) {
		 students[i].style.display = "none";
	    }
	 document.getElementById(x).style.display = "block";
}

function displayclassform()
{
	document.getElementById("classNameExistError").style.visibility = "none";
	document.getElementById('classNameEdit').value="";
	document.getElementById('descriptionEdit').value="";
	document.getElementById('modelcontainer').innerHTML=document.getElementById('classformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	//document.getElementById("project").style.display="block";
	document.getElementById('classIdForEdit').value=-1;
}

function displayeditclassform(x)
{
	document.getElementById("classNameExistError").style.visibility = "none";
	document.getElementById('modelcontainer').innerHTML=document.getElementById('classformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	//document.getElementById("project").style.display="block";
	var details = document.getElementsByClassName(x);
	var y = details[0].value;
	var z= details[1].value;
	document.getElementById('classIdForEdit').value=x;
	document.getElementById('classNameEdit').value=y;
	document.getElementById('descriptionEdit').value=z;
	
}

function displaystudentform(classId)
{
	document.getElementById('myModal').style.display = "none";
	document.getElementById('classIdForForm').value=classId;
	document.getElementById('modelcontainer').innerHTML=document.getElementById('studentdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	
}