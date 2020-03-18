<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project Management</title>
<link href="${pageContext.request.contextPath}/resources/css/myproject.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/myproject.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">

<%-- <script type="text/javascript">
window.onload = function() {
<% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid!=-1) { %>

	document.getElementById("projectbtn").style.display="none";
	document.getElementById("remove").style.display="none";
	var asignsbutton=document.getElementsByClassName("addassignment");
	for(var i=0;i<asignsbutton.length;i++)
	{
		asignsbutton[i].style.display="none";
	}
	var eles=document.getElementsByClassName('deleteassign');
	for(var i=0;i<eles.length;i++)
		{
		eles[i].style.display="none";
		}
	var editprojectbutton=document.getElementsByClassName("editproject");
	for(var i=0;i<editprojectbutton.length;i++)
	{
		editprojectbutton[i].style.display="none";
	}

<% }%>
}
</script> --%>

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

<p class="heading1a">My Projects</p>
<div class="maincontent">
<table class="mainproject">
	<c:if test="${empty model.existingprojects}">
	<tr><td>Add your project</td></tr>
	</c:if>
	<c:forEach var="element" items="${model.existingprojects}" >
	 <tr>
		 <td style="vertical-align:top;padding:20px 5px;">
		 <input class="check" type="checkbox" name=removeprojects value="${element.projectId}"/></td>
	 	 
	 	 <td>
				<div class="projects">
					<div class="projecttitle" id="${element.projectName}" >${element.projectName}	
					<input class="editproject" type="button" value = "Edit" onClick="editproject(${element.projectId})" style="border:0px;text-decoration:none;padding:2px 5px;margin:0px 100px;float:right;"/></div>
	    		</div>
    	
    		
	     		<table class="projectdetailstable">
	     		<tr>
	     			<th>Description:</th>
	     			<td><span class="projectdesc">${element.description}</span></td>
	     		</tr>
	     		<tr>
	     			<th>Subject:</th>
	     			<td><span class="projectdesc">${element.subject}</span></td>
	     		</tr>
	    		<tr>
	    			<th>Created on:</th>
	    			<td><span class="projectdesc">${element.dateCreated}</span></td>
	    		</tr>  
	    		<tr>
	    			<td><input class="assignlistbutton" type="button" onClick="displayProjectdetails(${element.projectId})" value="Assignment List" /></td>
	    			<td>			   
		    			<form:form class="addassignment" action="newassignmentform" method="post">
				        <input id="projectId" type="hidden" name="projectId" value="${element.projectId}"/>
				        <input id="assignbtn" class="button2" type="submit" value="New Assignment" />
			    		</form:form>
		    	</td>
	    		</tr>  			    			 
	    		</table>
    			
    <!-- ******************************* Assignments display table**************************** -->		 	
    		<div id="${element.projectId}" class="projectdetails" style="display:none;">
	    		<table class='assigntable'>
	    		  <tr>	    		  
	    		  <th>ID</th>
	    		  <th>Assignment</th>
	    		  <th>Created on</th>
	    		  <th>Status</th>
	    		  <th>&nbsp; </th></tr>
	    			<c:set var="projectid" value="${element.projectId}"/>
	    			<c:forEach var="assign" items="${model.existingassignments}" >	    			
	    				<c:set var="assignprojectid" value="${assign.projectId}"/>
						<c:if test = "${projectid == assignprojectid}">
						<tr>
							<form:form action="assignment" method="post">
							
							<td>${assign.assgnmntId}</td>
							<td>
							<a href="${assign.assgnmntId}">${assign.title}</a></td>
	    			 		<td>								
								${assign.dateCreated}									
	         				 </td>
	    			 		<td>								
								${assign.status}									
	         				 </td>
	         				<td>
	         						<input type="hidden" name="projectId" value="${assign.projectId}"/>
									<input type="hidden" name="assgnmntId" value="${assign.assgnmntId}"/>
<%-- 									<input type="hidden" name="title" value="${assign.title}"/>
									<input type="hidden" name="description" value="${assign.description}"/>
									<input type="hidden" name="directions" value="${assign.directions}"/>
									<input type="hidden" name="status" value="${assign.status}"/> --%>
									<input class="deleteassign button" type="submit" name="delete" value = "Delete" style="font-size:12px;" onClick="confirm('Are you sure you want to delete this assignment?')"/> </td>
	    			 		
	         				 </form:form>
	     				 </tr>
	     				 </c:if>	     				
					</c:forEach>					
	    		  </table>
	    		  

    		</div>
    		
    		<div id="${element.projectId}edit" style="display:none;">
    			<input type="button" class="close" onClick="closebox()" value="x" />
				<form:form id="editprojectform" action="updateproject" method="post" enctype = "multipart/form-data">
				<table cellspacing="10px">					
					<tr>
			              <th>Project Name:</th>
			              <td><input type="text" name="projectName" style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;" value="${element.projectName}"/></td>
			         </tr>
			          <tr>
			               <th>Subject:</th>
			               <td><input type="text" name="subject" style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;" value="${element.subject}" /></td>
			           </tr>
			         <tr valign="top">
			              <th>Description:</th>
			              <td><textarea name="description" rows="8" cols="40" style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;" wrap="soft">${element.description} </textarea></td>
			          </tr>
			         <tr><th>Image:<input type="file" name="file"><br /> </th>
                    <td><img alt="Current Image" src="${pageContext.request.contextPath}/tiles/${element.imagepath}" width="25%" height="25%" style="border:1px solid black;"/><br>Current Image<td></tr>
					
					<tr>					
						<td><input id="teacherid" type="hidden" name="teacherId" value="${model.instructorDetails.userID}"/></td>
						<td><input type="hidden" name="projectId" value="${element.projectId}"/>
						<input type="submit" class="button" value="Update" /></td>
						</tr>
					</table>
			</form:form>
    		</div>
    	 </td>
	 </tr>
	
	</c:forEach>
	<tr>
	 <td colspan=2>
		 <input id="projectbtn" class="button2" type="button" value="Add" onClick="displayprojectform()" style="margin-left: 10px;"/>
		
		<input type="submit" id="remove" class="button2" value = "Remove" onClick="getremovingprojects()"/>

	</td>
	</tr>
	</table>


<div id="myModal" class="modal">
  <div id="modelcontainer">
  </div>
</div>

<!-- project form -->

<div id="projectformdiv" style="display:none;">
    <input type="button" class="close" onClick="closebox()" value="x" />
    <br>
	<form:form id="projectform" action="addproject" method="post" enctype = "multipart/form-data">
	<table cellspacing="10px">
	
		<tr>
                        <th>Project Name:</th>
                        <td><input type="text" name="projectName" style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;"/></td>
                    </tr>
                    <tr>
                        <th>Subject:</th>
                        <td><input type="text" name="subject"  style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;"/></td>
                    </tr>
                    <tr valign="top">
                        <th>Description:</th>
                        <td><textarea name="description" rows="8" cols="40" wrap="soft" style="width:75%; border:2px solid #00738c; border-radius:2px; padding:3px; font-size:16px;"> </textarea></td>
                    </tr>
                    <tr><th>Image:<input type="file" name="file"><br /> </th>
                    <td><img alt="Default Image" src="${pageContext.request.contextPath}/resources/img/book.jpg" width="25%" height="25%" style="border:1px solid black;"/><br>Default Image<td></tr>
					<tr>
		
						<td><input id="teacherid" type="hidden" name="teacherId" value="${model.instructorDetails.userID}"/></td>
						<td><input type="submit" class="button" value="submit" /></td>
					</tr>

	</table>
	</form:form>
</div>
</div></div>	
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