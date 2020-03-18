<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/SMART/resources/css/home.css" rel="stylesheet" />
<link href="/SMART/resources/css/button.css" rel="stylesheet" />
<link href="/SMART/resources/css/table.css" rel="stylesheet" />
<title>Dashboard</title>
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
    margin-bottom:30px;
}

.container{
	margin-top: 80px;
	margin-left: 20px;
	padding:10px;
	position: absolute;
	width:100%;
}

#searchResultTable td{
	padding:10px;
}

#searchResultTable th{
	padding:20px;
}

.textboxcss{
	font: Lato, normal arial;
	height: 40px;
	width: 80%;
	font-size: 15px;
	letter-spacing: 2px;
	text-align: center;
	border: 2px solid #F0F0F0;
	top-margin: 10px;
}

.button:hover{
    background-color: #00738c;
      -webkit-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  -moz-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  -o-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
  transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1);
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


<p class="heading1a">
 My Dashboard
 </p>
 <div id="searchform" >
  <div class="container">
    <h1 class="heading2">Search Results</h1>
    <div style="height:500px;overflow:auto;">
    <table class="table" id="searchResultTable" style="width:75%;">
    <tr>
    <th>User ID</th>
    <th>User Name</th>
    <th>Password</th>
    <th>First Name</th>
    <th>School Name</th>
    <th>Role</th>
    <th>Email</th>
    <th>Account Status</th>
    <th>View Account</th>
    <th>Delete</th>
    </tr>
    <c:forEach var="element" items="${model.userList}">
    <tr>
    <form:form action="loginasuser" modelAttribute="userDetails" style="border:1px solid #ccc;width:50%" method="post">
    		<td>${element.userID}</td>
    		<td>${element.userName}</td>
    		<td>${element.password}</td>
    		<td>${element.firstName}</td>
    		<td>${element.schoolName}</td>
    		<td>${element.userType}</td>
    		<td>${element.email}</td>
    		<td>${element.status}</td>
    		<td style="display:none"><input type="hidden" name=userID value=${element.userID} /></td>
    		<td><input class="button" type="submit" name="login" value="View"/></td>
    		<td><input class="button" type="submit" name="delete" value="Delete" onClick="return confirm('Are you sure you want to delete this user?')"/></td>
    	</form:form>
    </tr>
    </c:forEach>
    </table>
    </div>
    </div>
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