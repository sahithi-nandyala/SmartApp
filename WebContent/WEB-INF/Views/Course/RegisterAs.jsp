<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>

<head>
<title>Home</title>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/registerCSS.css" />">
<%-- <script src="${pageContext.request.contextPath}/resources/js/sample.js"></script> --%>
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

<form:form id="registerUser" modelAttribute="userDetails"
	action="registerUser" method="post">
	<center>
	  <p class="iama">I would like to register as a </p></center>
	
 	</br />
 	<div>
 	<center>
		<button class="button button1 button2pos button3color" value="teacher" name="userType">Teacher</button>
		&nbsp;&nbsp;&nbsp;
	 	<button class="button button1 button3pos button2color" value="student" name="userType">Student</button>
 	</center>

 	</div>
 	<br>
 	<div>
		<center>
 		<button class="button button1 button1pos button1color" value="researcher" name="userType">Researcher</button>
 		</center>
 	</div>
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