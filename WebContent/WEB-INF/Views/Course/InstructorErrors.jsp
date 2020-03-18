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
    position: absolute;
    margin-top: 100px;
    text-align:center;
    width:100%;
}

.message{
	padding: 10px;
	color:#585757;
	font-size:20px;
	font-weight:normal;
	position: relative;
	text-align:center;
}

#displayerrordiv{
	margin-left: 20px;
	margin-top: 50px;
}

#backButton{
	margin-top: 30px;
	border: none;
    color: black;
    padding: 10px 10px 10px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 20px 2px;
    font-weight: normal;
    font-family: Lato, Arial;
    cursor: pointer;
    text-shadow: 0px 0px 0px #666666;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	background-color: #D5D7D9; 
    border: 1px solid #C9C6C5;
}

</style>

<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
<%
int userid=0;
if(session.getAttribute("userid") == null){
	response.sendRedirect("login");
}
else userid = (int) session.getAttribute("userid");
//session.setAttribute("userid",userid);
%>
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
<center>
<p class="heading1">
${model.heading}
</p>
 <br />
 <br />
 <br />
 <br />
 <br />
 <br />
 <div id="displayerrordiv">
 <p class="message">${model.message}</p>
 <input type="button" id="backButton" OnClick="location.href='${model.backLink}'" value="${model.backText}"/>
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