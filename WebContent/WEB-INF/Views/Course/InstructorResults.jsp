	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%
int userid=0;
if(session.getAttribute("userid") == null){
	response.sendRedirect("login");
}
else userid = (int) session.getAttribute("userid");
//session.setAttribute("userid",userid);
%>

<title>Results</title>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table.css" />"> --%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/instructorResults.css">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style>
.btn {
    border: none;
    color: black;
    padding: 5px 5px 5px 5px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    font-weight: bold;
    font-family: Lato, Arial;
    cursor: pointer;
    text-shadow: 0px 0px 0px #666666;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	background-color: #C9C6C5; 
    border: 10px solid #C9C6C5;
}
</style>
</head>
<body id="body">
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
<p class="goback"><a href='<%=session.getAttribute("assignid")%>'>back to assignment</a></p>
<p class="heading2a">Results</p>
<br />
<table class="table" style="width:70%">
<tr>
	<td><label for="assgnmntId" class="labelcss"><b>Assignment ID:</b>&nbsp;${model.assignmentDetails.assgnmntId} </label></td></tr>
	 <tr><td><label for="title" class="labelcss"><b>Assignment Name:</b>&nbsp;${model.assignmentDetails.title}</label></td>
</tr>
<tr>
	<%-- <td>&nbsp;&nbsp;<label for="classId" class="labelcss"><b>Class ID&nbsp;:</b>&nbsp;${model.classDetails.classId}</label></td> --%>
	 <td><label for="className" class="labelcss"><b>Class Name:</b>&nbsp;${model.classDetails.className}&nbsp;(${model.classDetails.classId})</label></td>
</tr>
</table>
<br />
<br />
<table class="table studentTable" style="width:70%">
<thead><tr>
	<th>Username</th>
	<th>Email</th>
 	<th>First Name</th>
	<th>Recall C</th>
	<th>Recall P</th>
	<th>Details</th>
	</tr>
</thead>
<tbody>
	<c:forEach var="element" items="${model.studentResultList}">
	<tr>
		<form:form id="displayStudentGraph" modelAttribute="courseDetails" action="getStudentResponseFeedback" method="post">    
		<td>${element.key.userName}</td>
		<td>${element.key.email}</td>
		<td>${element.key.firstName}</td>
		<td>${element.value.recallkeyconcepts}</td>
		<td>${element.value.recallKeylinks}</td>
		<td><input type="submit" value="Details" class="btn"/></td> 
		<td style="display:none"><input type="hidden" name="userId" value=${element.key.userID} />
		<input type="hidden" name="studentresponseid" value=${element.value.studentresponseid} />
		<input type="hidden" name="assignmentID" value="${model.assignmentDetails.assgnmntId}" /></td>
		</form:form>
	</tr>
	</c:forEach>
</tbody>
</table>
<br />
<br />
<form:form id="registerUser" modelAttribute="model"
	action="Results.CSV" method="post">
<table class="table" style="width:30%">
	<tr>
		<td><label for="assgnmntId" class="labelcss"><b>Summary of the Class Results: </b></label></td>
		<td><input type="submit" class="downloadbutton" value="Download All Results" id="downloadResultsButton"/></td>
		</tr>
	<tr style="display:none">
		<td>
		<input type="text" name="classId" value="${model.classDetails.classId}" />
		<input type="text" name="assignmentId" value="${model.assignmentDetails.assgnmntId}" />
		<input type="text" name="teacherId" value="${model.instructordetails.userID}" />
		</td>
	</tr>
</table>
</form:form>
<form:form id="researchcontent" modelAttribute="model"
	action="StudentModel.CSV" method="post">
<table class="table" style="width:30%">
	<tr>
		<td><label for="assgnmntId" class="labelcss"><b>Student Model Metrices: </b></label></td>
		<td><input type="submit" class="downloadbutton" value="Download all metric information" id="downloadResultsButton"/></td>
		</tr>
	<tr style="display:none">
		<td>
		<input type="text" name="classId" value="${model.classDetails.classId}" />
		<input type="text" name="assignmentId" value="${model.assignmentDetails.assgnmntId}" />
		<input type="text" name="teacherId" value="${model.instructordetails.userID}" />
		</td>
	</tr>
</table>
</form:form>
<br />
<br />
<table class="table studentTable resultTable" style="width:60%">
	<tr>
		<th>Number of Students: </th>
		<td> ${model.numberOfStudents} </td>
		<th> Responses: </th>
		<td> ${model.numberOfResponses} </td>
	</tr>
	<tr>
		<th> Average Recall-C: </th>
		<td> ${model.avgRecallC} </td>
		<th> Average Recall-P: </th>
		<td> ${model.avgRecallP} </td>
	</tr>
</table>
<br>
<table class="table studentTable conceptable" style="width:60%">
	<tr>
		<th> Most Missing Concepts</th>
		<th> Most Missing Relations</th>
	</tr>
	<tr valign="top">
	<td><c:forEach var="element" items="${model.mostMissingConcepts}">
	 &nbsp;&nbsp;${element.key} (${element.value})<br /></c:forEach>
	 </td>
	 <td><c:forEach var="element" items="${model.mostMissingLinks}">
	 	&nbsp;&nbsp;
	 	<%-- <c:forEach var="rel" items="${element.key}">
	 	${rel} 
	 	</c:forEach> --%>
	 	${element.key[0]} - ${element.key[1]}
	 	(${element.value})<br />
	 </c:forEach>
	 </td>
	</tr>
</table>
<br />
<br />
<br />
<br />
<br />
<br />
<br />
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
<%=session.getAttribute("researcherid") %>
<script type="text/javascript">
window.onload = function() {
<% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid==-1) { %>

	document.getElementById("researchcontent").style.display="none";
	<%
	}
	else
	{%>
	//document.getElementById("researchercontent").style.display="block";
	<% } %>
}
</script>
</html>

