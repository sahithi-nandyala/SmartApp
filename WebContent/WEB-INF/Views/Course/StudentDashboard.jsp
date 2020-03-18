<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<link href="/SMART/resources/css/home.css" rel="stylesheet">
<link href="/SMART/resources/css/studentdashboard.css" rel="stylesheet">
<%
int userid=0;
if(session.getAttribute("userid") == null){
	response.sendRedirect("login");
}
else userid = (int) session.getAttribute("userid");

int researcherid= (int)session.getAttribute("researcherid");
//session.setAttribute("userid",userid);
%>

</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
<div class="topnav-right">
  	<a href="StudentDashboard" class="class2">My Classes</a>
	<a href="#" class="class4">Help</a>
	<a href="studentProfile" class="class5">My Profile</a>
	<a href="signout" class="class7">Log out</a>
  </div></div>
  
<div style="text-align:center;background-color:RGB(42, 49 , 119);border-bottom:2px solid RGB(239, 144 , 79);padding:20px;margin:0px;">
<form:form  action="addingclasstomydashboad" method="post">   

<input type="text" name="inputclassID" placeholder="Enter Class Code" style="height:30px;"/> 
<input class="button" type="submit" value="Add Class" <%if (researcherid!=-1){ %>disabled<%} %>/>
</form:form>
</div>
<br>
<p class="heading1a">My Classes</p>
<div id="mysmartdiv">
		<table id="mysmartdivheading">
		<tr>
			<td>MY SMART!</td>
		</tr>
		</table>
		<p id="stats">STATS</p>
		<div id="lefttop"><p class="numbers" style="color:#25B3E4">${model.totalclasses}</p><p class="desc">Classes</p></div>
		<div id="righttop"><p class="numbers" style="color:#47CD55">${model.totalassignments}</p><p class="desc">Assignments</p></div>
		<p id="recentassignments"><br>RECENT ASSIGNMENTS</p>
		<table style="padding:10px; font-size:15px; color:#626567;">	
		<c:forEach var="assign" items="${model.recentassignments}" >
			 <tr><td style="padding:3px;">
				<span onClick="JavaScript:window.location='displayAssignment/${assign.assgnmntId}';"  style="cursor:pointer; font-size:16px; color:black;">${assign.dateCreated}&nbsp;&nbsp;&nbsp;<b>${assign.title}</b></span>
				
			</td></tr> 
		</c:forEach> 
		</table>
</div>
<div id="myprojectlistdiv">
<table class="maintable">
<tr>
	<td>
		<div id="classes">
		
		<c:set var="emptymsg" value="${model.emptymsg}" />
		<c:choose>
			<c:when test="${emptymsg.equals('yes')}">
			 <div>No assignments</div>
			</c:when>
			<c:otherwise>      
		      <div>
				<c:forEach var="assign" items="${model.assignmentdetails}" >	
					<form:form  action="removeclassfromdashboad" method="post"> 
					<div class="tooltip">${assign.key.className} &nbsp;<input type="hidden" name="inputclassID" value="${assign.key.classId}" /> 
							<input class="deleteclass" type="submit" value="X" onClick="Confirm('Are you sure you what to delete the class?')"/>
		 					 <span class="tooltiptext">Class code  ${assign.key.classId}</span>
						</div>						
					</form:form>
						<table>
						<tr>
						<c:forEach var="details" items="${assign.value}">
							<td >
							<div class="projectsneeded"  onClick="JavaScript:window.location='displayAssignment/${details.assgnmntId}';">
								${details.title}
								<div class="imagedivneeded">
									<img src="${pageContext.request.contextPath}/resources/img/book.jpg" style="margin:auto; max-width: 100%;max-height: 100%;display:block" />
								</div>
							</div>
							
							
							</td>
						</c:forEach>
						</tr>
						</table>
					<br><br>
				</c:forEach>
				
			  </div>
		    </c:otherwise>
		</c:choose>
		</div>
	</td>

</tr>
</table>
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