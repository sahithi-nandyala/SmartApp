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
int researcherid= (int)session.getAttribute("researcherid");

//session.setAttribute("userid",userid);
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table.css" />"> --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/button.css" />">
<script src="${pageContext.request.contextPath}/resources/js/jquery.tablesorter.js"></script>
<script src="/SMART/resources/js/profile.js"></script>
<link href="/SMART/resources/css/home.css" rel="stylesheet">
<link href="/SMART/resources/css/profile.css" rel="stylesheet">
<script>
$(document).ready(function() 
	    { 
	        $("#table").tablesorter(); 
	    } 
); 

function validateValues(){
	  if(validateSchoolname()){
		 document.getElementById("schoolNameError").style.visibility = "hidden"; 
			 if(validateEmail()){ 
				 document.getElementById("validEmailError").style.visibility = "hidden";
					 return true;
				 }
			 else{
				 document.getElementById("validEmailError").innerHTML = "*Not a valid Email!";
				 document.getElementById("validEmailError").style.visibility = "visible";
			 }
		  }
	 else{
		document.getElementById("schoolNameError").innerHTML = "*Should contain atleast 3 characters!";
		document.getElementById("schoolNameError").style.visibility = "visible";
	 } 
	 return false;
}

function validateEmail(){
	 var email=document.getElementById("email").value;  
	  if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
	   {
	     return true;
	   }
	     return false;
}


function validateSchoolname(){
	 var sName=document.getElementById("schoolName").value;  
	  if (sName.length >= 3)
	   {
	     return true;
	   }
	     return false;
}

</script>
<style>
.error
{
	font-size: 13px;
    font-weight: normal;
    font-family: Arial;
    color:#DC143C;
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
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
<div class="topnav-right">
 <a href="InstructorDashboard" class="class1">Dashboard</a>
 <a href="myStudents" class="class2">Classes</a>
 <a href="ProjectManagement" class="class3">Projects</a>
 <a href="#" class="class4">Help</a>
 <a href="instructorProfile" class="class5">My Profile</a>
 <a href="signout" class="class7">Log out</a>
  </div></div>
<div class="maincontent">
	<center>
		<form:form id="profileForm">
				<input type="button" value="Edit Profile" class="button" 
											onClick="displayeditprofileform(${model.userDetails.userID})"/>
				<br />
				<br />
				<table id="table" width=50%>
					<tr><th>User ID:</th>
					<td>${model.userDetails.userID}</td></tr>
					<tr><th>First Name:</th>
					<td>${model.userDetails.firstName}</td></tr>
					<tr><th>Username:</th>
					<td>${model.userDetails.userName}</td></tr>
					<tr><th>Role:</th>
					<td>${model.userDetails.userType}</td></tr>
					<tr><th>School Name:</th>
					<td>${model.userDetails.schoolName}</td></tr>
					<tr><th>Email:</th>
					<td>${model.userDetails.email}</td></tr>
				</table>
		</form:form>

	</center>
	<div id="myModal" class="modal">
		<div id="modelcontainer"></div>
	</div>

	<div id="profileformdiv" style="display: none;">
		<a href="" class="close">&times;</a>
		<form:form id="profileform" action="editProfile" method="post" modelAttribute="userDetails" onsubmit="return validateValues()">
			<h3 class="heading1">Edit Profile</h3>
			<table id="tableedit">
				<tr>
					<th>First Name</th>
					<td><input type="text" class="textboxcss" style="cursor:pointer" name="firstName" value="${model.userDetails.firstName}"/></td>
				</tr>
				<tr>
					<th>Username</th>
					<td><input type="text" class="textboxcss"  name="userName" value="${model.userDetails.userName}" readonly/></td>
				</tr>
				<tr>
					<th>School Name&nbsp;&nbsp;<p><span id="schoolNameError" class="error"></span></p></th>
					<td><input type="text" class="textboxcss" style="cursor:pointer" id="schoolName" name="schoolName" value="${model.userDetails.schoolName}"/></td>
				</tr>
				<tr>
					<th>Email&nbsp;&nbsp;<p><span id="validEmailError" class="error"></span></p></th>
					<td><input type="text" class="textboxcss" style="cursor:pointer" id="email" name="email" value="${model.userDetails.email}"/></td>
				</tr>
				<tr style="display:none">
					<td><input id="userIdForEdit" type="hidden" name="userID"/></td>
				</tr>
				
			</table>
			<table id="tableedit">
				<tr>
					<td><center><input type="SUBMIT" value="Submit" class="button"/></center></td>
				</tr>
			</table>
		</form:form>
	</div><br>
	
	<div style="text-align:center"> <a href="upgradeToResearcher">Upgrade me to Researcher</a></div>
	</div>
</div>
<p></p>
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