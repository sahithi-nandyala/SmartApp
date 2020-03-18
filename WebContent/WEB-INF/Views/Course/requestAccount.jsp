<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<title></title>
<link href="/SMART/resources/css/RegistrationForm.css"
    rel="stylesheet"/>
<script src="/SMART/resources/js/registrationStudent.js"></script>
<link href="/SMART/resources/css/home.css" rel="stylesheet">
<body id="body">
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
<br />
<br />
<center>
<form:form action="requestaccount" modelAttribute="maildetails" method="post">
  <div >
    <h1 class="heading1">Request Account!</h1>
    <ul class="errorMessages"></ul>
    <table class="tablecss">
    <tr><th></th></tr>
    <tr>
    <td>
	<label for="message" class="labelcss"><b>Enter Message</b></label>&nbsp;&nbsp;<span id="schoolNameError" class="error"></span>
	</td>
	</tr>
	<tr>
	<td>
    <textarea name="message"  style="width:100%; height: 70%; position: relative; border-radius: 6px;border: 1px solid #C9C6C5;margin: 10px; font-size:13px; padding:10px; overflow:auto;" id="descriptionEdit" required></textarea>
    </td>
    </tr>
    <tr style="display:none"><td>
    		<input type="hidden" name="firstName" value="${userDetails.firstName}"/>
    		<input type="hidden" name="userName" value="${userDetails.userName}"/>
    		<input type="hidden" name="email" value="${userDetails.email}"/>
    		<input type="hidden" name="schoolName" value="${userDetails.schoolName}"/>
    		<input type="hidden" name="userType" value="${userDetails.userType}"/></td></tr>
   	</table>
    <table class="table2css">
    <tr><td>
    <div class="clearfix">
      <button type="submit" class="signupbtn">Request Account</button>
    </div>
    </td></tr>
    </table>
  </div>
</form:form>
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

