<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign in</title>
<link href="/SMART/resources/css/login.css"
    rel="stylesheet"/>
 <link href="/SMART/resources/css/home.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
</script>
<style>
#loggedinmsg
{
	font-weight:bold;
	text-align:center;
	color:red;
}
.textboxcss{
	font: Lato, normal arial;
	height: 40px;
	width: 300px;
	font-size: 15px;
	letter-spacing: 2px;
	text-align: center;
	border: 2px solid #F0F0F0;
	top-margin: 10px;
}

.labelcss{
	font: Lato, normal arial;
  	color: #000000;
  	text-align: left;
  	font-size: 18px;
    font-weight: normal;
    letter-spacing: 1px;
    margin-top: 10px;
}
.tempcss{
	font: Lato, normal arial;
  	color: black;
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
    font-family: Lato, Arial;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
    cursor: pointer;
   	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	color: white; 
  	background-color: #00738c;
}

.signupbtn:hover {
    background-color: #81b0b2;
    color: white;
    border: 2px solid #81b0b2;
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
            <table class="tablecss" style="text-align: center; width:70%;"><tr>
            <td>
				<label for="email" class="tempcss"><b>A temporary password has been emailed to your registered email. Enter the temporary password below.</b></label>
			</td>
			</tr>
			</table>
			<div id="notstudentdiv">
			<form:form method="post" action="changetemppassword" modelattribute="userdetails">
				<table class="tablecss">
		        <tr><td>
				    <label for="username" class="labelcss"><b>Username</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="text" class="textboxcss" placeholder="Enter Username" name="userName" id="userName" required>
					</td>
				</tr>
				<tr><td>
				    <label for="temppassword" class="labelcss"><b>Temporary Password</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="password" class="textboxcss" placeholder="Enter Temporary Password" name="tempPassword" id="tempPassword" required>
					</td>
				</tr>
				<tr><td>
				    <label for="newpassword" class="labelcss"><b>New Password</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="password" class="textboxcss" placeholder="Enter New Password" name="password" id="password" required>
					</td>
				</tr>
				<tr><td>
				    <label for="confirmpassword" class="labelcss"><b>Confirm Password</b></label>
				    </td>
				    </tr>
					<tr>
				    <td>
				    <input type="password" class="textboxcss" placeholder="Confirm Password" name=confirmpassword id="confirmpassword" required>
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