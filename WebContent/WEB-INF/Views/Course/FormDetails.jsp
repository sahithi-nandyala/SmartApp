<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<html>
  <head>
  <link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
  
  </head>
  <body>
  <div class="wrapper">
  <div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
<div class="topnav-right">
	<a href="/SMART" class="class1">Home</a>
 	<a href="#" class="class4">Help</a>
	<a href="login" class="class6">Login</a>
	<a href="registerAs" class="class7">Register</a>
   	<a href="problemStatement">Add Assignment</a>
	<a href="studentSolution">Add Student Solution</a>
  </div>
</div>
        <p class="heading1a">Student Solution</p>  
       <form:form id="displayStudentGraph" modelAttribute="courseDetails" action="displayStudentGraph" method="post">    
        <table >    
         <tr>    
          <td>Title: </td>   
          <td><input type="text" name="title"  /></td>  
         </tr>    
         <tr>    
          <td>Text:</td>    
          <td><textarea name="text" ></textarea></td>  
         </tr>     
         <tr>    
          <td>ExpertModel ID:</td>    
          <td><input type="text" name="ExpertID" /></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><input type="hidden" name="model" value="student" /></td>  
         </tr>
         <tr>    
          <td> </td>    
          <td><input type="submit" value="Submit" /></td>    
         </tr>    
        </table>    
       </form:form>   
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