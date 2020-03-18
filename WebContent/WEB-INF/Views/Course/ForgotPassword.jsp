<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign in</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
function showDiv(elem){
	   if(elem.value == "student"){
	      document.getElementById('studentdiv').style.display = "block";
	      document.getElementById('notstudentdiv').style.display = "none";
	   }
	   else if((elem.value == "teacher")||(elem.value == "researcher")){
		      document.getElementById('studentdiv').style.display = "none";
		      document.getElementById('notstudentdiv').style.display = "block";
		   }
	   else{
		   document.getElementById('studentdiv').style.display = "none";
		   document.getElementById('notstudentdiv').style.display = "none";
	   }
}

</script>
<style>
#loggedinmsg
{
	font-weight:bold;
	text-align:center;
	color:red;
}
.textboxcss{
	font: normal arial;
	height: 40px;
	width: 100%;
	font-size: 15px;
	letter-spacing: 2px;
	text-align: center;
	border: 2px solid #F0F0F0;
	top-margin: 10px;
}

.labelcss{
	font: normal arial;
  	color: #2BC4B9;
  	text-align: left;
  	font-size: 15px;
    font-weight: normal;
    letter-spacing: 1px;
    margin-top: 10px;
}

.table2css {
	width: 400px;
	border-collapse: separate;
    border-spacing: 10px;
    text-align: center;
}

.tablecss {
	width: 400px;
	border-collapse: separate;
    border-spacing: 10px;
    text-align: left;
}

.signupbtn{
	border: none;
    padding: 20px 40px 20px 40px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 20px;
    margin: 4px 2px;
    font-weight: bold;
    font-family: Arial;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
    cursor: pointer;
    text-shadow: 0px 0px 0px #666666;
  	-webkit-box-shadow: 4px 4px 7px #666666;
  	-moz-box-shadow: 4px 4px 7px #666666;
  	box-shadow: 4px 4px 7px #666666;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	color: white; 
  	background-color: #0A76DC;
}

.signupbtn:hover {
    background-color: #DA6C1A;
    color: white;
    border: 2px solid #DA6C1A;
}
select 
{ 
		font-size: 15px;
          padding:10px;
         -webkit-appearance: none;
         -moz-appearance: none;
          appearance: none;
          border: 2px solid #F0F0F0;

        }
</style>
</head>
<link href="${pageContext.request.contextPath}/resources/css/login.css"
    rel="stylesheet"/>
 <link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
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

<div class="sign">FORGOT PASSWORD <span style="color:#f442dc;">${model.loginStatus}</span></div>

<div id="loginform" class="login">
            <center>
            <table class="tablecss" style="text-align: left;"><tr>
            <td>
				<label for="email" class="labelcss"><b>Account Type</b></label>
			</td>
			</tr>
			<tr>
            <td> <select name="userType" onChange="showDiv(this)">
            		<option value="">Select Account Type</option>
		  		<option value="teacher">Teacher</option>
		  		<option value="student">Student</option>
		  		<option value="researcher">Researcher</option>
			</select></td></tr>
			</table>
			<div id="notstudentdiv" style="display:none;">
			<form:form method="post" action="newpasswordrequest" modelattribute="userdetails">
				<table class="tablecss">
		        <tr><td>
		             <input type="hidden" name="userType" value="notstudent"/>
				    <label for="username" class="labelcss"><b>Username</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="text" class="textboxcss" placeholder="Enter Username" name="userName" id="userName" required>
					</td>
				</tr>
				<tr><td>
				    <label for="email" class="labelcss"><b>Email</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="text" class="textboxcss" placeholder="Enter Email" name="email" id="email" required>
					</td>
				</tr>
				</table>
				<table class="table2css">
				    <tr><td>
				    <div class="clearfix">
				      <button type="submit" class="signupbtn">Request New Password</button>
				    </div>
				    </td></tr>
    				</table>
	            </form:form>
            </div>
            <div id="studentdiv" style="display:none;">
				<form:form method="post" action="newpasswordrequest" modelattribute="userdetails">
				<table class="tablecss">
		        <tr><td>
		             <input type="hidden" name="userType" value="student"/>
				    <label for="username" class="labelcss"><b>Username</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="text" class="textboxcss" placeholder="Enter Username" name="userName" id="userName" required>
					</td>
				</tr>
				<tr><td>
				    <label for="email" class="labelcss"><b>Sign-up question: What is your favorite teacher's name?</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="text" class="textboxcss" placeholder="Enter Answer" name="answer" id="answer" required>
					</td>
				</tr>
				</table>
				<table class="table2css">
				    <tr><td>
				    <div class="clearfix">
				      <button type="submit" class="signupbtn">Reset Password</button>
				    </div>
				    </td></tr>
    				</table>
	            </form:form>
            </div>
            </center>
</div></div></div>
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