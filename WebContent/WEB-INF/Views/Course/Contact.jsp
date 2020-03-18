<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>Contact</title>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
<%-- <script src="${pageContext.request.contextPath}/resources/js/sample.js"></script> --%>
<style>
.textboxcss{
	font: Lato, normal arial;
	height: 40px;
	width: 30%;
	font-size: 15px;
	letter-spacing: 2px;
	
	border: 2px solid #cbcbcb;
	top-margin: 10px;
}
.textbox2css{
	height: 300px;
	font: Lato, normal arial;
	width: 30%;
	font-size: 15px;
	letter-spacing: 2px;
	border: 2px solid #cbcbcb;
	top-margin: 10px;
	}
</style>
</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
  <div class="topnav-right">
	<a href="/SMART" class="class1">Home</a>
	<a href="help" class="class4">Help</a>
	<a href="login" class="class6">Login</a>
	<a href="registerAs" class="class7">Sign Up</a>
  </div>
</div>
<p class="heading1a">Contact Us</p>
<br><br>
<div class="maincontent3">
<p class="heading1i"><strong>Submit a request</strong></p>


<form:form action="contactus" modelAttribute="maildetails" method="POST">
<p>Email:</p> <input type="text" name="email" class="textboxcss">
<p>Subject:</p> <input type="text" name="Subject" class="textboxcss">
<p>Request Type:</p>
<select name="request" size="1">
<option value="Tech issue">Tech Support</option>
<option value="Question">Question</option>
<option value="Comment">Comment</option>
</select>
<br />
<p>Description:</p><textarea name="description" class="textbox2css"></textarea><br />
<input type="submit" value="Send" class="button"><input type="reset" value="Clear" class="button">
</form:form>


</div></div>
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