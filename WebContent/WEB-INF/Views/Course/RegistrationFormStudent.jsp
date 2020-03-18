<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<title></title>
<link href="/SMART/resources/css/RegistrationForm.css"
    rel="stylesheet"/>
<script src="/SMART/resources/js/registrationStudent.js"></script>
<link href="/SMART/resources/css/home.css" rel="stylesheet">
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
<form:form action="registerlogin" modelAttribute="userDetails" method="post" onsubmit="return validateValues()">
  <div >
    <h1 class="heading1">Enter account details!</h1>
    <br>
    <ul class="errorMessages"></ul>
    <table class="tablecss">
    <tr><th></th></tr>
    <tr>
    <td>
	<label for="schoolName" class="labelcss"><b>School Name *</b></label>&nbsp;&nbsp;<span id="schoolNameError" class="error"></span>
	</td>
	</tr>
	<tr>
	<td>
    <input type="text" class="textboxcss" placeholder="Enter School Name" name="schoolName" id="schoolName" required>
    </td>
    </tr>
    <tr><th></th></tr>
    <tr>
    <td>
    <label for="firstName" class="labelcss"><b>First Name *</b></label>
    </td>
    </tr>
	<tr>
	<td>
    <input type="text" class="textboxcss" placeholder="Enter First Name" name="firstName"  id="firstName" required>
    </td>
    </tr>
    <tr><th></th></tr>
    <tr>
    <td>
    <label for="userName" class="labelcss"><b>User Name *</b></label>&nbsp;&nbsp;<span id="userNameError" class="error"></span>
    </td>
    </tr>
	<tr>
    <td>
    <input type="text" class="textboxcss" placeholder="Enter User Name" name="userName" id="userName" required>
    </td>
    </tr>
    <tr><th></th></tr>
    <tr>
    <td>
    <label for="email" class="labelcss"><b>Email</b></label>&nbsp;&nbsp;<span id="validEmailError" class="error"></span>
    </td>
    </tr>
	<tr>
    <td>
    <input type="text" class="textboxcss" placeholder="Enter Email" name="email" id="email">
    </td>
    </tr>
    <tr><th></th></tr>
    <tr>
    <td>
    <label for="email_repeat" class="labelcss"><b>Repeat Email</b></label>&nbsp;&nbsp;<span id="emailError" class="error"></span>
    </td>
    </tr>
	<tr>
    <td>
    <input type="text" class="textboxcss" placeholder="Repeat Email" name="email_repeat" id="email_repeat">
    </td>
    </tr>
    <tr><th></th></tr>
	<tr>
	<td>
    <label for="psw" class="labelcss"><b>Password *</b></label>&nbsp;&nbsp;<span id="validPasswordError" class="error"></span>
    </td>
    </tr>
	<tr>
    <td>
    <input type="password" class="textboxcss" placeholder="Enter Password" name="password" id="password" required>
	</td>
	</tr>
	<tr><th></th></tr>
	<tr>
	<td>
    <label for="psw_repeat" class="labelcss"><b>Repeat Password *</b></label>&nbsp;&nbsp;<span id="passwordError" class="error"></span>
    </td>
    </tr>
	<tr>
    <td>
    <input type="password" class="textboxcss" placeholder="Repeat Password" name="psw_repeat" id="psw_repeat" required>
    </td>
    </tr>
    	<tr><th></th></tr>
	<tr>
	<td>
    <label for="sign_question" class="labelcss"><b>Sign-up question: What is your favorite teacher's name? *</b></label>&nbsp;&nbsp;<span id="answerError" class="error"></span>
   	</td>
    </tr>
	<tr>
    <td>
   	<input type="text" class="textboxcss" placeholder="Enter Answer" name="answer" id="answer" required>
   	</td>
    </tr>
    </table>
    <br />
    <table class="table2css">
    <input type="hidden" name="userType" value="student">
    <tr><td>
    <p>By creating an account, you agree to our <a href="/SMART/TermsOfService" style="color:dodgerblue">Terms</a> & <a href="/SMART/PrivacyPolicy" style="color:dodgerblue">Privacy</a>.</p>
    </td></tr>
    </table>
    <table class="table2css">
    <tr><td>
    <div class="clearfix">
      <button type="submit" class="signupbtn">Sign Up</button>
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

