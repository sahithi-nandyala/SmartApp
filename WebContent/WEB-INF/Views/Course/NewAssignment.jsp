<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/myproject.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/button2.css" rel="stylesheet" />

<%-- 	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.css">
 
    <!-- Include Editor style. -->
    <link href="${pageContext.request.contextPath}/resources/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/css/froala_style.min.css" rel="stylesheet" type="text/css" />
  
 --%>
<style>
#assignform
{
	padding:0px 5px;

}
</style>
<title>New Assignment</title>
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
  
<p class="heading2a">New Assignment</p>
  
  <div id="assignform" >
	<form:form id="assign" action="addassignment" method="post">
	<table cellspacing="20px">	
	<tr valign="top">    
	      <th>Assignment Title: </th>   
          <td><input id="assigntitle" type="text" name="title"  style="width:75%; height:35px; font-size:16px; padding-left: 3px; border:2px solid #00738c; border-radius:2px;" /></td>  
         </tr> 
         <tr valign="top">    
          <th>Directions:</th>    
          <td><textarea id="directions" name="directions" rows="10" cols="75" style="width:75%; padding-left:3px; border:2px solid #00738c; border-radius: 2px; font-size:16px;" wrap="soft" autofocus required> </textarea></td>  
         </tr>
		<tr valign="top">    
          <th>Description:</th>    
          <td><textarea id="description" name="description" rows="15" cols="75" style="width:75%; padding-left:3px; border:2px solid #00738c; border-radius: 2px; font-size:16px;" wrap="soft" autofocus required> </textarea></td>  
         </tr>	
	
         <tr>
         	<th style="padding-top:20px;">Status:</th>
         	<td style="padding-top:20px;"><input type="radio" name=status value="open" checked/> OPEN &nbsp; &nbsp;<input type="radio" name=status value="close"/> CLOSE &nbsp; &nbsp;<input type="radio" name=status value="draft"/> DRAFT</td>
         </tr>
		<tr>
			<td><input id="projectId" type="hidden" name="projectId" value="${model.projectId}"/></td>
			<td><br><input type="submit" class="button2" value="Save & Continue" /> &nbsp; <input type="button" class="button2" value="Preview" onClick="preview()"/> &nbsp; <input type="button" class="button2" value="Cancel" onClick="JavaScript:window.location='ProjectManagement';"/></td>
		</tr>
	</table>
	</form:form>
</div>

<div id="myModal" class="modal">
  <div id="modelcontainer">
  </div>
</div>

<div id="previewdiv" style="display:none;">
    <input type="button" class="close" onClick="closebox()" value="x" />
    <br>
    <p class="heading2a">Preview</p>
    <br>
    <br>
    <br>
	<table>	
		<tr>    
	      <td>Assignment Title: </td>   
          <td><div id="assigntitleprev" ></div></td>  
         </tr> 
         <tr>    
          <td>Directions:</td>    
          <td><div id="directionsprev" class="fr-view" style="border:1px solid black;width:95%;" > </div></td>  
         </tr>
		<tr>    
          <td>Description:</td>    
          <td><div id="descriptionprev" class="fr-view" style="border:1px solid black;width:95%;"> </div></td>  
         </tr>	
	
         <tr>
         	<td>Status:</td>
         	<td><input type="radio" name=statusprev value="open" checked/> OPEN &nbsp; <input type="radio" name=statusprev value="close"/> CLOSE <input type="radio" name=status value="draft"/> DRAFT</td>
         </tr>
	</table>
</div>	

 <!-- Include external JS libs. -->
<!--     <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/codemirror.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.25.0/mode/xml/xml.min.js"></script>
  -->
<%--     <!-- Include Editor JS files. -->
     <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/froala_editor.pkgd.min.js"></script>
  --%>
<script>
function preview()
{
	document.getElementById('assigntitleprev').innerHTML=document.getElementById('assigntitle').value;
	document.getElementById('directionsprev').innerHTML=document.getElementById('directions').value;
	document.getElementById('descriptionprev').innerHTML=document.getElementById('description').value;
	var status = document.getElementsByName('status');
	var status_value;
	for(var i = 0; i < status.length; i++){
	    if(status[i].checked){
	    	status_value = status[i].value;
	    	break;
	    }
	}
	var statusprev = document.getElementsByName('statusprev');
	for(var i = 0; i < statusprev.length; i++){
	    if(status[i].value==status_value){
	    	status[i].checked=true;;
	    	break;
	    }
	}
	
	document.getElementById('modelcontainer').innerHTML=document.getElementById('previewdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
}
function closebox()
{
	document.getElementById('myModal').style.display = "none";
}


/* $(function() {
	$('textarea#directions').froalaEditor();
	$('textarea#description').froalaEditor();
}); */

</script>
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