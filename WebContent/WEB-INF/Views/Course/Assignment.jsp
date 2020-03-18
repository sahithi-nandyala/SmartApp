<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="${pageContext.request.contextPath}/resources/css/assignment.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/myproject.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button2.css" rel="stylesheet">

<%-- <script type="text/javascript">
window.onload = function() {
<% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid!=-1) { %>

	document.getElementById("editassign").style.display="none";
	document.getElementById("addclassfom").style.display="none";
	var asignsbutton=document.getElementsByClassName("deleteclass");
	for(var i=0;i<asignsbutton.length;i++)
	{
		asignsbutton[i].style.display="none";
	}
	
	var eles=document.getElementsByClassName("modeloption");
	for(var i=0;i<eles.length;i++)
		{
		eles[i].style.display="none";
		}


<% }%>
}
</script> --%>

<title>Assignment</title>
</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a><div class="topnav-right">
  	<a href="InstructorDashboard" class="class1">Dashboard</a>
    <a href="myStudents" class="class2">Classes</a>
    <a href="ProjectManagement" class="class3">Projects</a>
    <a href="#" class="class4">Help</a>
	<a href="instructorProfile" class="class5">My Profile</a>
	<a href="signout" class="class7">Log out</a>
  </div></div>
  <a href="ProjectManagement">back to projects</a>
<br><br><p class="heading3a">${model.displayedAssignment.title}</p> 
<br><br><br>
<div class="container">
<div class-"maincontent">
<table class="Assignmentdetails">
	<tr>
		<th>Assignment ID</th>
		<td>${model.displayedAssignment.assgnmntId}</td>
	</tr>
	<tr>
		<th>Assignment Title</th>
		<td>${model.displayedAssignment.title}</td>
	</tr>
	<%-- <tr>
		<th>Description</th>
		<td>${model.displayedAssignment.description}</td>
	</tr>
		<tr>
		<th>Directions</th>
		<td>${model.displayedAssignment.directions}</td>
	</tr> --%>
	<tr>
		<th>Status</th>
		<td>${model.displayedAssignment.status}</td>
	</tr>
	<tr>
		<td colspan="2">
		<form:form action="editassignmentpage" method="post">
				<input type="hidden" name="assgnmntId" value="${model.displayedAssignment.assgnmntId}"/>
				<%-- <input type="hidden" name="title" value="${model.displayedAssignment.title}"/>
				<input type="hidden" name="directions" value="${model.displayedAssignment.directions}"/>
				<input type="hidden" name="description" value="${model.displayedAssignment.description}"/>
				<input type="hidden" name="status" value="${model.displayedAssignment.status}"/> --%>
				<input id="editassign" class="button2" type="submit" value="Edit"/>
				</form:form>
		</td>
	</tr>
</table>

<table class="classform">	
	<tr>
		<td style="font-size:18px;border-bottom:2px sotted gray;width:20%;"><b>Assigned Classes</b></td>
		<td>
		 <!-- ******************* Add Class form ************************************ -->
			<form:form id="addclassfom" action="addclasstoassignment" method="post">
				<input type="hidden" name="assgnmntId" value="${model.displayedAssignment.assgnmntId}" />
                <input type="submit" class="button2" value="Add Class"/>
<%-- 				<input type="hidden" name="title" value="${model.displayedAssignment.title}"/>
				<input type="hidden" name="description" value="${model.displayedAssignment.description}"/>
				<input type="hidden" name="directions" value="${model.displayedAssignment.directions}"/>
				<input type="hidden" name="status" value="${model.displayedAssignment.status}"/> --%>
				<select id="addclass" name="selectedclassIDtoadd">				
					<option value=null></option>
						<c:forEach var="classoptions" items="${model.classdetails}" >	
							<option value="${classoptions.classId}">${classoptions.className}</option>
						</c:forEach>

				</select>		
				
			</form:form>
			<br>
		</td>
	</tr>
	<tr>		
		<td colspan="2">
			<table class="modal">
			 <tr>
			 	
			 	<th>Class ID </th>
			 	<th>Class Name </th>
			 	<th>Total Students </th>
			 	<th>Responses</th>
			 	<th></th>
			 	<th></th>
			 </tr>
			<c:forEach var="classoptions" items="${model.displayedAssignment.classdetails}" >	
			<tr>	
			<%-- <td><input type="checkbox" name="classdelete" value="${classoptions.classId}"/></td> --%>	
			<td>${classoptions.classId}</td>
			<td> ${classoptions.className} </td>	
			<td>${classoptions.studentcount}</td>	
			<td>${classoptions.studentresponses}</td>	
			<td> <form:form action="instructorResults" method="post">
				<input type="hidden" name="classId" value="${classoptions.classId}"/>
				<input class="button" type="submit" value="Results"/>
				</form:form>
			</td>
			<td><input class="deleteclass button" type="button" name="delete" value = "Delete" style="font-size:10px;" onClick="deleteclass('${classoptions.classId}')" /></td>
	    	</tr>
	    	</c:forEach>
	    	</table>
		</td>
	</tr>
</table>

<table class="classform">
	<tr>
		<td style="font-size:18px;border-bottom:2px sotted gray;width:20%; padding-bottom:30px;"><b>Reference Models</b></td></tr>
<tr><td>
		<!-- ************************************************** Add Reference Model form ************************************************ -->
		<form:form id="addreferencemodel" action="referenceModel" method="post">
		<input type="hidden" name="assignmentID" value="${model.displayedAssignment.assgnmntId}" />
		<input type="hidden" name="assignTitle" value="${model.displayedAssignment.title}" />
		<input type="submit" class="button2" value="Add Reference Model" />
		</form:form>
		<br>
	</td>
	</tr>

	<tr>
	<td colspan="2">
		<!-- ************************************************Displaying reference Models **************************************************-->
		<table class="modal">
		<tr>
			<th>Selected model</th>
			<th>Id</th>
			<th>Title</th>
			<th>Created Date</th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
		<c:set var="choosenid" value="${model.displayedAssignment.choosenmodelID }" />
		<c:forEach var="references" items="${model.referencemodels}" >			
					<tr>
					<c:set var="refID" value="${references.modelID }" />
					<c:choose>
		         	<c:when test="${choosenid == refID}">
		         	<td><img src="${pageContext.request.contextPath}/resources/img/tick.png" width="20px" height="20px"/></td>
		         	<td>${references.modelID}</td>
		         	<td><form:form action="displayexpertgraph" method="post">
						<input id="modelID" name="modelID" type="hidden" value="${references.modelID }" />
						<input class="refmodeltitle" type="submit" value="${references.title}" />
						</form:form></td>	
		         	<td>${references.createdon}</td>
		         	<td></td>	         	
					<td></td>
					<td></td>
		         	</c:when>
		         	<c:otherwise>
		         	<td></td>
		         	<td>${references.modelID}</td>
					<td><form:form action="displayexpertgraph" method="post">
						<input id="modelID" name="modelID" type="hidden" value="${references.modelID }" />
						<input class="refmodeltitle" type="submit" value="${references.title}" />
						</form:form></td>
					<td>${references.createdon}</td>
					<form:form action="expertmodel" method="post">
						<input type="hidden" name="assignmentID" value="${model.displayedAssignment.assgnmntId}" />
						<input type="hidden" name="modelID" value="${references.modelID}" />
						<td><input class="modeloption button" type="submit" name="select" value="Select" onClick="return confirm('Are you sure you want to change the reference? Students could have submitted responses based on the current selected reference')"/></td>
						<td><input class="modeloption button" type="submit" name="edit" value="Edit"/></td>
						<td><input class="modeloption button" type="submit" name="remove" value="Delete" onClick="return confirm('Are you sure you want to delete the expert model?')"/></td>
					</form:form>
										
					</c:otherwise>
					</c:choose>
					
					</tr>
					
		</c:forEach>
		</table>
	</td>
	</tr>
</table>
</div>
</div></div>
<p></p><br>
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