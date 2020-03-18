<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<title>My Assignment</title>
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


<ol>
<c:forEach var="assign" items="${model.assignmentdetails}" >	
	<li><a href="#${assign.assgnmntId}" onClick="document.getElementById('container').innerHTML=document.getElementById('${assign.assgnmntId}').innerHTML">${assign.title}</a></li>
</c:forEach>
</ol>	

<c:forEach var="assign" items="${model.assignmentdetails}" >
<div id="${assign.assgnmntId}" style="display:none;">
<table>
<tr><td width=50%>	
	<div><p><h2>${assign.title}</h2></p>${assign.description}</div><div><p>Directions</p>${assign.directions}</div>
	</td>
	<td width=50%>
	<form:form  modelAttribute="courseDetails" action="displayStudentGraph" method="post">    
        <table >    
         <tr>    
  
          <td><input type="text" placeholder="Title" name="title"  /></td>  
         </tr>    
         <tr>    
  
          <td><textarea name="text" placeholder="Text" rows="15" cols="110" wrap="soft" ></textarea></td>  
         </tr>     
         <tr>    
          <td><input type="hidden" name="modelID" value="${assign.choosenmodelID}" /></td>    
          <td><input type="hidden" name="model" value="student" /></td>  
         </tr>
         <tr>    
 
          <td><input type="submit" name="submit" value="Submit" /> <input type="submit" name="save" value="Save" /></td>    
         </tr>    
        </table>    
       </form:form>

	</td>
	</tr>
</table>
</div>

</c:forEach>
<div id="container"></div>
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