<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/table.css" rel="stylesheet" />
<title>Dashboard</title>
<% int display = (int) session.getAttribute("admin");%>
<script>
</script>
<style>
.heading1{
	font-size: 25px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    position: absolute;
    margin-top: 100px;
    margin-left: 20px;
}

.heading2{
	font-size: 23px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    position: absolute;
    margin-top: 130px;
    margin-left: 20px;
    position: absolute;
}

.container{
	margin-top: 160px;
	margin-left: 20px;
	padding:10px;
	position: absolute;
	width:100%;
}

.container2{
	margin-top: 650px;
	margin-left: 20px;
	padding:10px;
	position: absolute;
	width:100%;
	margin-bottom: 50px;
}

.container3{
	margin-top: 900px;
	margin-left: 20px;
	padding:10px;
	position: absolute;
	width:100%;
	margin-bottom: 50px;
}

.container4{
	margin-top: 1150px;
	margin-left: 20px;
	padding:10px;
	position: absolute;
	width:100%;
	margin-bottom: 50px;
}


#myTable td{
	padding:20px;
}

#myTable th{
	padding:20px;
	text-align:center;
	padding-left:20px;
	width:40%;
	font-weight: bold;
}

.textboxcss{
	font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
	height: 30px;
	width: 80%;
	font-size: 15px;
	letter-spacing: 2px;
	text-align: center;
	border: 2px solid #F0F0F0;
	top-margin: 10px;
}
.tablecss {
	width: 400px;
	align: left;
	border-collapse: separate;
    border-spacing: 10px;
    text-align: left;
}

#body{
font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
}
</style>
</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
  <div class="topnav-right">
  	<a href="ResearcherDashboard" class="class1">My Dashboard</a>
	<a href="signout" class="class7">Log out</a>
  </div></div>
<p class="heading1a">My Dashboard</p>
 <div id="searchform" style="border:none;">
<form:form action="searchUser" modelAttribute="userDetails" method="post">
  <div class="container">

    <table class="table" style="width:60%;" id="myTable">
    <tr>
    <th>
    <label for="Role">Role</label></th>
   <td> <select name="userType">
    		<option value="">All</option>
  		<option value="teacher">Teacher</option>
  		<option value="student">Student</option>
  		<option value="researcher">Researcher</option>
	</select></td>
    </tr>
    <tr><th>
	<label for="schoolName">School Name</label></th>
    <td><input type="text" name="schoolName" class="textboxcss"></td>
    </tr>
    <tr><th>
    <label for="firstName">First Name</label></th>
    <td><input type="text" name="firstName" class="textboxcss"></td>
    </tr>
    <tr><th>
    <label for="userName">User Name</label></th>
    <td><input type="text" name="userName" class="textboxcss"/></td>
    </tr>
    <tr><th>
    <label for="email">Email</label></th>
    <td><input type="text" name="email" class="textboxcss"></td>
    </tr>
    </table>
    <center>
    <table class="table" style="width:60%; border:none;" id="myTable">
    <tr style="border:none;"><td style="border:none;">
     <div class="clearfix">
      <button type="submit" class="button" style="padding:10px; padding-left:20px; padding-right:20px;">Search</button></div>
      </td></tr>
     </table>
     </center>
     </div>
</form:form>
</div>
<%if (display==1) {%>
 <div class="container2">
 <form:form action="approveResearcher" modelAttribute="userDetails" method="post">
 <h3>Approve Instructor/Researcher Account</h3>
 <br />
  <br />
 <table class="table" id="myTable" style="width:60%;">
 <tr><th>
	<label for="userName">Username:</label></th>
    <td><input type="text" name="userName" class="textboxcss" required></td>
    </tr>
   <tr style="border:none;">
  <td style="border:none;"><button type="submit" class="button" style="padding:10px; padding-left:20px; padding-right:20px;">Approve Account</button></td>
  </tr>
 </table>
 </form:form>
 </div>
 <%} %>
 <%if (display==1) {%>
 <div class="container3">
 <form:form action="approveUpgradeRequest" modelAttribute="userDetails" method="post">
 <h3>Approve Upgrade to Researcher Request</h3>
 <br />
  <br />
 <table class="table" id="myTable" style="width:60%;">
 <tr><th>
	<label for="userName">Username:</label></th>
    <td><input type="text" name="userName" class="textboxcss" required></td>
    </tr>
   <tr style="border:none;">
  <td style="border:none;"><button type="submit" class="button" style="padding:10px; padding-left:20px; padding-right:20px;">Approve Account</button></td>
  </tr>
 </table>
 </form:form>
 </div>
 <%} %>
 <%if (display==1) {%>
 <div class="container4">
 <form:form action="validateUser" modelAttribute="userDetails" method="post">
 <h3>Validate Deleted Account</h3>
 <br />
  <br />
 <table class="table" id="myTable" style="width:60%;">
 <tr><th>
	<label for="userName">User ID:</label></th>
    <td><input type="text" name="userID" class="textboxcss" required></td>
    </tr>
   <tr style="border:none;">
  <td style="border:none;"><button type="submit" class="button" style="padding:10px; padding-left:20px; padding-right:20px;">Validate User</button></td>
  </tr>
 </table>
 </form:form>
 </div>
 <%} %>
 </div>
 <p></p>
<!-- <div class="footer">
<table width="100%" cellspacing="10px" border="0" align="center">
  <tbody><tr>
    <td width="30%" valign="bottom" align="left"><h3>SMART</h3>
    <p>30 Pryor St SW, Atlanta, GA 30303 </p></td>
    <td width="70%" valign="bottom" align="right"><a href="/SMART/#AboutUs">About Us</a> | <a href="Contact">Contact</a> | <a href="TermsOfService">Terms of Usage</a> | <a href="PrivacyPolicy">Privacy Policy</a></td>
  </tr>
</tbody></table>
</div> -->
</body>
</html>