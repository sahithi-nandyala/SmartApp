<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${model.title}</title>
<style>
.heading1{
	font-size: 25px;
    font-weight: bold;
    font-family: Lato, Arial;
    color:#000000;
    margin-top: 100px;
    text-align:center;
	text-align: center;
	width: 100%;
}

.message{
	padding: 10px;
	color:#585757;
	font-size:20px;
	font-weight:normal;
	text-align:center;
	
	
}

#displayerrordiv{
	margin-left: auto;
	margin-right: auto;
}

#backButton{
	margin-top:30px;
		margin-left: auto;
	margin-right: auto;
}

</style>

<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">

</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
  <div class="topnav-right">
	<a href="/SMART" class="class1">Home</a>
	<a href="#" class="class4">Help</a>
	<a href="login" class="class6">Login</a>
	<a href="registerAs" class="class7">Sign Up</a>
  </div>
</div>
<center>
<p class="heading1">
${model.heading}
</p>
 <br />
 <div id="displayerrordiv">
 <p class="message">${model.message}</p>
 <input type="button" id="backButton" OnClick="location.href='${model.backLink}'" value="${model.backText}" class="button"/>
</div>
</center>
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