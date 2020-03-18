<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Class Management</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/button.css" />">
<script src="${pageContext.request.contextPath}/resources/js/jquery.tablesorter.js"></script>
<script>
$(document).ready(function() 
	    { 
	        $("#table").tablesorter(); 
	    } 
); 
</script>
<style>
th {
    cursor: pointer;
}
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	padding-top: 100px; /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content */
#modelcontainer {
	background-color: #fefefe;
	margin: auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
}

/* The Close Button */
.close {
	color: #ffffff;
	float: right;
	font-size: 24px;
	font-weight: bold;
	text-decoration: none;
    background-color: #00738c;
    padding: 0px 20px 4px 20px;
    border: solid 1px;
    border-radius: 6px;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
    background-color: #81b0b2;
     padding: 0px 20px 4px 20px;
    border: solid 1px;
    border-radius: 6px;
}
</style>
<script type="text/javascript">
if(sessionStorage.getItem('FirstName')!=null)
{
var userid=sessionStorage.getItem('FirstName');

}

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
	document.getElementById('classNameEdit').value="";
	document.getElementById('descriptionEdit').value="";
	document.getElementById('modelcontainer').innerHTML=document.getElementById('classformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	//document.getElementById("project").style.display="block";
	document.getElementById('classIdForEdit').value=-1;
}

function displayeditclassform(x)
{
	document.getElementById('modelcontainer').innerHTML=document.getElementById('classformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	//document.getElementById("project").style.display="block";
	document.getElementById('classIdForEdit').value=x;
	/* document.getElementById('classNameEdit').value=y;
	document.getElementById('descriptionEdit').value=z; */
	
}

function displaystudentform(classId)
{
	document.getElementById('myModal').style.display = "none";
	document.getElementById('classIdForForm').value=classId;
	document.getElementById('modelcontainer').innerHTML=document.getElementById('studentdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	
}
</script>
</head>
<body>
	Hello ${model.instructordetails.firstName }!
	<br />
	<br />
	<p>Class List:</p>
	<a href='Classes.CSV' > Download Class List </a>
	<table border=1 width=100% id="myTable" class="table">
		<form:form id="classRemoveForm" action="removeClasses" method="post"
			modelAttribute="classDetails">
			<thead><tr>
				<!-- <th>Select</th> -->
				<th>Class Name</th>
			</tr></thead>
			<c:forEach var="element" items="${model.classStudentList}">
				<tbody><tr>
					<%-- <td align="center"><input type="checkbox" name="classIds"
						value="<c:out value="${element.key.classId}"/>" /></td> --%>
					<td>
						<div class="classes">
							<input type="checkbox" name="classIds"
						value="<c:out value="${element.key.classId}"/>" />&nbsp;&nbsp;<span onClick="displayClassdetails(${element.key.classId})">${element.key.className}</span>
						</div>
					</td>
					<td>
						<div class="classesdesc" id="${element.key.classId}"
							style="display: none;">
							<input type="button" value="Edit Class" class="button" 
											onClick='displayeditclassform(${element.key.classId})'/>
							<p class="classdesc"><b>Class ID:</b>&nbsp;
								${element.key.classId}</p>
							<p class="classdesc"><b>Class Name:</b>&nbsp;
								${element.key.className}</p>
							<p class="classdesc"><b>Class Description:</b>&nbsp;
								${element.key.description}</p>
							<table border=1 width=100% id="table">
								<%-- <form:form id="studentRemoveForm" action="removeStudents"
									method="post" modelAttribute="studentRemoveDetails"> --%>
									<thead><tr>
										<!-- <th>Select</th> -->
										<th id="stunameheader">Student Name</th>
									</tr>
									<tr style="display:none"><td><input id="classIdSelected" type="hidden" name="classIdSelected" /></td>
									</tr></thead>
									<c:forEach var="stu" items="${element.value}">
										<tbody><tr>
											<%-- <td align="center"><input type="checkbox"
												name="studentIds" value="<c:out value="${stu.userID}"/>" />
											</td> --%>
											<td id="studentNameColumn">
												<div class="students"><input type="checkbox"
												name="studentIds" value="<c:out value="${stu.userID}"/>" />&nbsp;&nbsp;
													<span onClick="displaystudentdetails(${stu.classStudentId})">${stu.firstName}</span>
												</div>
											</td>
											<td>
												<div class="studentsdesc" id="${stu.classStudentId}"
													style="display: none;"><center>
													<p><b>STUDENT DETAILS</b></p>
													<p><b>First Name:</b>&nbsp;
														${stu.firstName}</p>
													<p><b>Username:</b>&nbsp;
														${stu.userName}</p>
													<p><b>Email:</b>&nbsp;
														${stu.email}</p>
													<p><b>School Name:</b>&nbsp;
														${stu.schoolName}</p></center>
													<input id="classId" type="hidden" name="classId"
																value="${element.key.classId}" />
												</div>
											</td>
										</tr></tbody>
									</c:forEach>
									<tr>
										<td><input type="SUBMIT" value="Remove Selected Students" class="button" id="removeStudentButton"
											onclick="return confirm('Are you sure you want to remove selected students?')" />&nbsp;&nbsp;
										<input id="studentbtn" type="button" class="button" 
											value="Add Student"
											onClick="displaystudentform(${element.key.classId})" /></td>
									</tr>
							</table>
						</div>
					</td>
				</tr></tbody>
			</c:forEach>
			<tr>
				<td><input type="SUBMIT" class="button" value="Remove Selected Classes" id="removeClassButton"
					onclick="return confirm('Are you sure you want to remove selected classes?')" name="submit"/>&nbsp;&nbsp;
				<input class="button" id="classbtn" type="button" value="Add Class"
					onClick="displayclassform()" /></td>
			</tr>
		</form:form>
	</table>

	<div id="myModal" class="modal">
		<div id="modelcontainer"></div>
	</div>

	<div id="classformdiv" style="display: none;">
		<a href="" class="close">&times;</a>
		<form:form id="classform" action="addclass" method="post">
			<h3>Enter Class Details:</h3>
			<table>
				<tr>
					<td>Class Name:</td>
					<td><input type="text" name="className" id="classNameEdit"/></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><input type="text" name="description" id="descriptionEdit"/></td>
				</tr>
				<tr>

					<td><input id="teacherid" type="hidden" name="teacherId"
						value="${model.instructordetails.userID}" /></td>
					<td><input id="classIdForEdit" type="hidden" name="classId"/></td>
				</tr>

				<tr>
					<td><input type="submit" value="submit" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="studentdiv" style="display: none;">
		<a href="" class="close">&times;</a>
		<form:form id="student" action="addstudent" method="post"
			modelAttribute="userDetails">
			<h3>Add Student</h3>
			<br>
			<table>
				<tr>
					<td>School Name:</td>
					<td><input type="text" name="schoolName" /></td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><input type="text" name="firstName" /></td>
				</tr>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" /></td>
				</tr>
				<tr>
					<td><input id="classIdForForm" type="hidden" name="classId" /></td>
					<td><input type="submit" value="Add" /></td>
				</tr>
			</table>
		</form:form>
	</div>
   
</body>
</html>