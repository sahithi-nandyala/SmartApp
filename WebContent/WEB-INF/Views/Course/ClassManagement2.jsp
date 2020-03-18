<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%
int userid=0;
if(session.getAttribute("userid") == null){
	response.sendRedirect("login");
}
else userid = (int) session.getAttribute("userid");

//session.setAttribute("userid",userid);
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Class Management</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table.css" />">--%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/classManagement.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/button.css" />">
<script src="${pageContext.request.contextPath}/resources/js/classManagement.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<script>
$(document).ready(function() 
	    { 
	       /*  $("#table").tablesorter();  */
	    } 
); 
/* 
function movetotop(x) { 
	    $(x).parent().prependTo("#myTable");
} */

window.onload = function() 
{
/* 	document.getElementById("classNameExistError").style.visibility = "none";
		if(${model.exist}==1){
			 document.getElementById("classNameExistError").innerHTML = "*Class Name already exist!";
			 document.getElementById("classNameExistError").style.visibility = "visible";
			if(${model.id}!=-1){
				displayeditclassform(${model.id});
			}
			else{
				displayeditclassform();
			}
		} */
		
		<%int classIdSelected = (int) session.getAttribute("classIdSelected");%>
		displayClassdetails(<%= classIdSelected%>);
};

function closeeditform(e)
{
	document.getElementById("classNameExistError").style.visibility = "none";
	var p= e.parentElement;
	p.parentElement.style.display="none";
};

function selectAll(x){
	name ="studentIds"+x;
	if (document.getElementById("selectall").checked==true){
		document.getElementsByClassName(name).checked=true;
	}
}

function removeStudent(){
	document.getElementById("classRemoveForm").submit();
}

function toggle(source, x) {
	name ="studentIds"+x;
	//alert(name);
	  checkboxes = document.getElementsByClassName(name);
	  for(var i=0, n=checkboxes.length;i<n;i++) {
	    checkboxes[i].checked = source.checked;
	  }
}
<%-- <% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid==-1) { %>
function disableremove(x){
	name ="studentIds"+x;
	buttonid="studentremove"+x;
	//alert(name);
	checkstatus=0;
	  checkboxes = document.getElementsByClassName(name);
	  for(var i=0, n=checkboxes.length;i<n;i++) {
	    //checkboxes[i].checked = source.checked;
	    if(checkboxes[i].checked==true){
	    		checkstatus=1;
	    }
	  }
	if(checkstatus==1){
		document.getElementById(buttonid).disabled = false;
	}
	else{
		document.getElementById(buttonid).disabled = true;
	}
} --%>
	
	function disableremoveclass(){
		name ="classIds";
		buttonid="removeClassButton";
		//alert(name);
		checkstatus=0;
		  checkboxes = document.getElementsByName(name);
		  for(var i=0, n=checkboxes.length;i<n;i++) {
		    //checkboxes[i].checked = source.checked;
		    if(checkboxes[i].checked==true){
		    		checkstatus=1;
		    }
		  }
		if(checkstatus==1){
			document.getElementById(buttonid).disabled = false;
		}
		else{
			document.getElementById(buttonid).disabled = true;
		}
	}

</script>
<style>
.classdetails{
	padding: 4px;
}


/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	/* padding-top: 100px; */ /* Location of the box */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}



.error
{
	font-size: 12px;
    font-weight: normal;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#DC143C;
}



.classes
{
	font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
}
.classes dd
{
	display:none;
	background-color: #f2f2f2;
}
.classes dt
{
	cursor: pointer;
}

.classes dt:hover
{
	color:red;
}

.heading1{
	font-size: 25px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    margin-top: 75px;
    margin-left: 25px;
}

.normalmargin{
	margin-left: 30px;
}

.setheightcss{
	height: 30px;
	letter-spacing: 1px;
}

.stubutton {
    border: none;
    color: #ffffff;
    padding: 5px 5px 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px 2px;
    font-weight: normal;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    cursor: pointer;
    text-shadow: 0px 0px 0px #666666;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	background-color: RGB(42, 49 , 119); 
  	padding: 9px;
    border: 1px solid #D7ECF5;
    width: 100px;
}

.downloadbutton {
    border: none;
    color: black;
    padding: 5px 5px 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px 2px;
    font-weight: normal;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    cursor: pointer;
    text-shadow: 0px 0px 0px #666666;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	background-color: #D5D7D9; 
  	padding: 9px;
    border: 1px solid #C9C6C5;
    /* box-shadow: 4px 4px 7px #CFD1D3; */
}

.button{
	width: 100px;
}


.addstudentheading{
	font-size: 18px;
	font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-weight:normal;
	padding: 10px;
}

.stubutton:disabled,
stubutton[disabled]{
/* 	background-color: #FFEDED; 
	border: 1px solid #EFDEDE; */
	cursor:not-allowed;
	color:#9F9B9B;
}

.button:disabled,
button[disabled]{
	/* background-color: #808080; 
	border: 10px solid #808080; */
	cursor:not-allowed;
	color:#9F9B9B;
}

</style>
<script type="text/javascript">
if(sessionStorage.getItem('FirstName')!=null)
{
var userid=sessionStorage.getItem('FirstName');

}
</script>
</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a><div class="topnav-right">
  	<a href="InstructorDashboard" class="class1">Dashboard</a>
    <a href="myStudents" class="class2">Classes</a>
    <a href="ProjectManagement" class="class3">Projects</a>
    <a href="#" class="class4">Help</a>
	<a href="instructorProfile" class="class5">My Profile</a>
	<a href="signout" class="class7">Log out</a>
  </div></div>
	<p class="heading1b">Class List</p>
<br>

	<input type="button" class="downloadbutton" onClick="location.href='Classes.CSV'" class="normalmargin" style="margin-left: 30px;" value="Download All Rosters" />
	<br />
	<br />
	<table border=1 id="myTable" class="table normalmargin">
		<form:form id="classRemoveForm" action="removeClasses" method="post"
			modelAttribute="classDetails">
			<thead><tr>
				<!-- <th>Select</th> -->
				<th id="classnameheader">Class Name</th>
			</tr></thead>
			<c:forEach var="element" items="${model.classStudentList}">
				<tbody><tr>
					<%-- <td align="center"><input type="checkbox" name="classIds"
						value="<c:out value="${element.key.classId}"/>" /></td> --%>
					<td id="classnamecolumn" onclick="displayClassdetails(${element.key.classId});" style="cursor:pointer;">
						<div class="classes">
							&nbsp;&nbsp;<input type="checkbox" name="classIds" onChange="disableremoveclass()"
						value="<c:out value="${element.key.classId}"/>" />&nbsp;&nbsp;<span onClick="displayClassdetails(${element.key.classId})">${element.key.className}</span>
						</div>
					</td>
					<td>
						<div class="classesdesc" id="${element.key.classId}"
							style="display: none;">
				
							<input type="button" value="Edit" class="button" 
											onClick="displayeditclassform(${element.key.classId})"/>

							<p class="classdesc classdetails setheightcss"><b>Class ID:</b>&nbsp;
								${element.key.classId}</p>
							<p class="classdesc classdetails setheightcss"><b>Class Name:</b>&nbsp;${element.key.className}
								<input type="hidden" value="${element.key.className}" class="${element.key.classId} testboxcss" readonly/></p>
							<p class="classdesc classdetails setheightcss"><b>Class Description:</b>&nbsp;${element.key.description}
							<input type="hidden" value="${element.key.description}" class="${element.key.classId} testboxcss" readonly/></p>
							<table border=1 width=100% id="table" class="${element.key.classId}">
								
									<thead><tr>
												<th style="width:5%;">&nbsp;&nbsp;<input type="checkbox" id="selectall" onClick="toggle(this, ${element.key.classId}); disableremove(${element.key.classId});"/></th><th>&nbsp;&nbsp;First Name</th>
												<th>Username</th>
												<th>Email</th>
												<th>School Name</th>
												</tr>
									<tr style="display:none"><td><input id="classIdSelected" type="hidden" name="classIdSelected" /></td>
									</tr></thead>
									<tbody>
									<c:forEach var="stu" items="${element.value}"><tr id="studentdetailsrow">
												<td class="container" style="width:5%;">&nbsp;&nbsp;<input type="checkbox" id="studentIds" onChange="disableremove(${element.key.classId})" name="studentIds" class="studentIds${element.key.classId}" value="<c:out value="${stu.userID}"/>" /></td><td>&nbsp;&nbsp;${stu.firstName}</td>
												<td>${stu.userName}</td>
												<td>${stu.email}</td>
												<td>${stu.schoolName}</td>
												<td style= "display:none"><input id="classId" type="hidden" name="classId"
																value="${element.key.classId}" /></td>
												</tr></c:forEach>
										</tbody>
									<tr style="border:none;">

<%-- 										<td style="border:none;"><input type="SUBMIT" value="Remove" class="stubutton" id="studentremove${element.key.classId}"
											onclick="return confirm('Are you sure you want to remove selected students?');" name="studentremove${element.key.classId}" disabled/></td>
										<td style="border:none;"><input id="studentbtn" type="button" class="stubutton" value="Add"
											onClick="displaystudentform(${element.key.classId})" disabled/></td>
 --%>
										<td style="border:none;"><input type="SUBMIT" value="Remove" class="stubutton" id="studentremove${element.key.classId}"
											onclick="return confirm('Are you sure you want to remove selected students?');" name="studentremove${element.key.classId}"/></td>
										<td style="border:none;"><input id="studentbtn" type="button" class="stubutton" value="Add"
											onClick="displaystudentform(${element.key.classId})" /></td>

									</tr>
							</table>
						</div>
					</td>
				</tr></tbody>
			</c:forEach>
			<tr>

				<td> <input type="SUBMIT" class="button" value="Remove" id="removeClassButton"
					onclick="return confirm('Are you sure you want to remove selected classes?')" name="submit" disabled/>&nbsp;&nbsp;
				<input class="button" id="classbtn" type="button" value="Add"
					onClick="displayclassform()" /></td>

			</tr>
		</form:form>
	</table>

	<div id="myModal" class="modal">
		<div id="modelcontainer"></div>
	</div>

	<div id="classformdiv" style="display: none;">
		<input type="button" class="close" onClick="closeeditform(this)" value="X" />
		<form:form id="classform" action="addclass" method="post">
			<h3 class="addstudentheading">Enter Class Details</h3>
			<table style="margin: auto;">
				<tr valign="top">
					<td class="labelcss">Class Name:</td>
					<td><input type="text" class="textboxcss" placeholder="Enter Class Name" name="className" id="classNameEdit" required />&nbsp;<span id="classNameExistError" class="error" style="visibility:none"></span></td>
				</tr>
				<tr valign="top">
					<td class="labelcss">Description:</td>
					<td><textarea name="description" class="textboxcss" placeholder="Enter Class Description" style="border-radius: 6px; padding: 10px; border: 1px solid #C9C6C5;font-size:12px;" id="descriptionEdit" required></textarea></td>
				</tr>
				<tr>

					<td><input id="teacherid" type="hidden" name="teacherId"
						value="${model.instructordetails.userID}" /></td>
					<td><input id="classIdForEdit" type="hidden" name="classId"/></td>
				</tr>

				<tr><td></td>
					<td><input class="button" type="submit" value="Submit" /></td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="studentdiv" style="display: none;">
		<input type="button" class="close" onClick="closeeditform(this)" value="X" />
		<form:form id="student" action="addstudent" method="post"
			modelAttribute="userDetails">
			<h3 class="addstudentheading" style="margin-left:30%;">Add Student</h3>
			<br>
			<table style="margin: auto; width:80%;">
				<tr>
					<td class="labelcss">Username:</td>
					<td><input type="text" name="userName" class="textboxcss" placeholder="Enter Student's Username" required /></td>
				</tr>
				<tr>
					<td><input id="classIdForForm" type="hidden" name="classId" /></td>
					<td><input class="stubutton" type="submit" value="Add" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	<br />
	<br />
	<br />
	<br />
    </div>
   <p></p><br>
<div class="footer">
<table width="100%" cellspacing="10px" border="0" align="center">
  <tbody><tr>
    <td width="30%" valign="bottom" align="left"><h3>SMART</h3>
    <p>30 Pryor St SW, Atlanta, GA 30303 </p></td>
    <td width="70%" valign="bottom" align="right"><a href="/SMART/#AboutUs">About Us</a> | <a href="Contact">Contact</a> | <a href="TermsOfService">Terms of Usage</a> | <a href="PrivacyPolicy">Privacy Policy</a></td>
  </tr>
</tbody></table>
</div>
</body>
</html>