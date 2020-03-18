<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>

<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/instructordashbaord.css" rel="stylesheet" />
<%int researcherid= (int)session.getAttribute("researcherid"); %>
<style>
/* .heading1{
	font-size: 25px;
    font-weight: bold;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    position: absolute;
    margin-top: 100px;
    margin-left: 20px;
}

#mysmartdiv{

	width: 27%;
	height: 500px;
	background-color: white;
	float: right;
	border: 1px solid #909497;
	margin-top: 75px;
	overflow: auto;
}

#mysmartdivheading{
	width: 100%;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	background-color:RGB(42, 49 , 119);
	color: white;
	font-weight: bold;
	font-size: 25px;
	position: relative;
	padding: 10px;
	text-align: center;
}

#stats{
	padding: 10px;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	padding-top: 10px;
	font-weight: bold;
	font-size: 15px;
	color: black;
}

#recentassignments{
	margin-top: 150px;
	padding: 10px;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	padding-top: 10px;
	font-weight: bold;
	font-size: 15px;
	color: black;
}
#lefttop{
	width: 49%;
	height: 15%;
	position: relative;
	float: left;
	text-align: center;
	/* padding: 10px; 
	border-right: 1px solid #999E9E;
}
#righttop{
	width: 49%;
	height: 15%;
	position: relative;
	float: right;
	text-align: center;
	/* padding: 10px; 
	
	
}
*/

#leftbottom{
	width: 49%;
	height: 15%;
	position: relative;
	float: left;
	text-align: center;
	/* padding: 10px; */
	border-right: 1px solid #999E9E;
	
}
#rightbottom{
	width: 49%;
	height: 15%;
	position: relative;
	float: right;
	text-align: center;
	/* padding: 10px; */
	
	
}

.numbers{
	font-size: 35px;
	
}

.desc{
	font-size: 15px;
	font-weight: bold;
}

.projects{
	width: 160px;
	height: 160px;
	position: relative;
	float: left;
	text-align: center;
	padding: 20px;
	background-color:#DEDDDB;
	margin: 20px;
	box-shadow: 4px 4px 7px #666666;
	cursor: pointer;

}

.projects{
   white-space: nowrap; 

    overflow: hidden;
    text-overflow: ellipsis; 
}
.projectsneeded{
	width: 160px;
	height: 160px;
	position: relative;
	float: left;
	text-align: center;
	padding: 20px;
	background-color:#DEDDDB;
	margin: 20px;
	cursor: pointer;
	color: #706869;
}


.imagediv{
	padding: 10px;
	background-color: white;
	margin: 10px;
	height: 65%;
	width: 70%;

}

.imagedivneeded{	
	padding: 10px;
	/* background-color: #ADB1D2; */
	background-color: white;
	margin: 10px;
	height: 65%;
	width: 70%;
} */
</style>
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

<p class="heading1a">
 My Dashboard
 </p>
 
 <div id="mysmartdiv">
	<table id="mysmartdivheading">
	<tr>
		<td>MY SMART!</td>
	</tr>
	</table>
	<p id="stats">STATS</p>
	<div id="lefttop"><p class="numbers" style="color:#25B3E4">${model.numberOfProjects}</p><p class="desc">Projects</p></div>
	<div id="righttop"><p class="numbers" style="color:#47CD55">${model.numberOfClasses}</p><p class="desc">Classes</p></div>
	<div id="rightbottom"><p class="numbers" style="color:#EA356C">${model.numberOfStudents}</p><p class="desc">Students</p></div>
	<div id="leftbottom"><p class="numbers" style="color:#7C35EA">${model.numberOfAssignments}</p><p class="desc">Assignments</p></div>
	<br>
	
	<p id="recentassignments"><b>RECENT ASSIGNMENTS</b></p>
	<table style="padding:10px; font-size:15px; color:#626567;">
	<c:forEach var="element" items="${model.recentassignmentList}"><tr><td style="padding:3px;">
		<span onClick="location.href='${element.assgnmntId}'"  style="cursor:pointer; font-size:18px; color:black;">${element.dateCreated}&nbsp;&nbsp;&nbsp;<b>${element.title}</b></span>
		<input type="hidden" name="assgnmntId" value="${element.assgnmntId}" /><input type="hidden" name="display" value ="${element.title}"/>
	</td></tr></c:forEach>
	</table>
</div>
<div id="myprojectlistdiv">
<c:forEach var="element" items="${model.projectList}">
	<div id="project1" class="projects" onClick="JavaScript:window.location='ProjectManagement';"><span><b>${element.projectName}</b></span><div class="imagediv"><img src="${pageContext.request.contextPath}/tiles/${element.imagepath}" style="margin:auto; max-width: 100%;max-height: 100%; display:block"/></div></div>
</c:forEach>
<c:forEach var="i" begin="0" end="${model.projectsNeeded}">
	<div id="project1" class="projectsneeded" onClick="JavaScript:window.location='ProjectManagement';">Register new project<div class="imagedivneeded"><img src="${pageContext.request.contextPath}/resources/img/plus.png" style="margin:auto; max-width: 100%;max-height: 100%; display:block" /></div></div>
</c:forEach>
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