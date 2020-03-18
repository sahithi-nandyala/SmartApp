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

<style>
#assignform
{
	padding:0px 100px;
	margin-top: 50px;
}

</style>
<title>New Assignment</title>
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
	<form:form id="assign" action="updateassignment" method="post">
<p class="heading2a">Edit Assignment</p>
<br>
	<table class="assign2table" cellspacing="20px">	
	<tr>    
	      <th align="right">ID: </th>   
          <td><input type="hidden" name="assgnmntId" value="${model.assgnmntId}"/>${model.assgnmntId}</td>  
         </tr>
	<tr  valign="top">    
	      <th>Assignment Title: </th>   
          <td><input id="assigntitle" type="text" name="title" style="width:75%; height:25px; font-size:16px; padding:5px; border:2px solid #97a675; border-radius:2px;"  value="${model.title}"/></td>  
         </tr> 
         <tr  valign="top">    
          <th>Directions:</th>    
          <td><textarea id="directions" name="directions" rows="10" cols="75" style="width:75%; padding:5px; border:2px solid #97a675; border-radius:2px; font-size:16px;" wrap="soft"  required>${model.directions} </textarea></td>  
         </tr>
		<tr  valign="top">    
          <th>Description:</th>    
          <td><textarea id="description" name="description" rows="15" cols="75" style="width:75%; padding:5px; border:2px solid #97a675; border-radius:2px; font-size:16px;" wrap="soft" required>${model.description}  </textarea></td>  
         </tr>	
	
         <tr>
         	<th style="padding-top:20px;">Status:</th>
         	<td style="padding-top:20px;"><c:set var="status" value="${model.status}"/>
         	<c:choose>
         	<c:when test= "${status == 'open'}">
	         	<input type="radio" name=status value="open" checked/> OPEN &nbsp; <input type="radio" name=status value="close"/> CLOSE &nbsp; <input type="radio" name=status value="draft"/> DRAFT</td>
	         	</c:when>
	         	<c:when test= "${status == 'close'}">
	         	<input type="radio" name=status value="open" /> OPEN &nbsp; <input type="radio" name=status value="close" checked/> CLOSE &nbsp; <input type="radio" name=status value="draft"/> DRAFT</td>
	         	</c:when>
	         	<c:when test= "${status == 'draft'}">
	         	<input type="radio" name=status value="open" /> OPEN &nbsp; <input type="radio" name=status value="close" /> CLOSE &nbsp; <input type="radio" name=status value="draft" checked/> DRAFT</td>
	         	</c:when>
	         	 <c:otherwise>
  				 <input type="radio" name=status value="open" checked/> OPEN &nbsp; <input type="radio" name=status value="close" /> CLOSE &nbsp; <input type="radio" name=status value="draft"/> DRAFT</td>
	         	</c:otherwise>
         	</c:choose> 
         </tr>
         
         <tr>
         	<th style="padding-top:20px;">Type:</th>
         	<td style="padding-top:20px;"><c:set var="type" value="${model.type}"/>
         	<c:choose>
         	<c:when test= "${type == 'type1'}">
	         	<input type="radio" name=type value="type1" checked/> TYPE1 &nbsp; <input type="radio" name=type value="type2"/> TYPE2 &nbsp; <input type="radio" name=type value="both"/> BOTH</td>
	         	</c:when>
	         	<c:when test= "${type == 'type2'}">
	         	<input type="radio" name=type value="type1" /> TYPE1 &nbsp; <input type="radio" name=type value="type2" checked/> TYPE2 &nbsp; <input type="radio" name=type value="both"/> BOTH</td>
	         	</c:when>
	         	<c:when test= "${type == 'both'}">
	         	<input type="radio" name=type value="type1" /> TYPE1 &nbsp; <input type="radio" name=type value="type2" /> TYPE2 &nbsp; <input type="radio" name=type value="both" checked/> BOTH</td>
	         	</c:when>
	         	 <c:otherwise>
  				 <input type="radio" name=type value="type1" checked/> TYPE1 &nbsp; <input type="radio" name=type value="type2" /> TYPE2 &nbsp; <input type="radio" name=type value="both"/> BOTH</td>
	         	</c:otherwise>
         	</c:choose> 
         </tr>
         
		 <tr>
			<td></td>
			<td style="padding-top:20px;"><input type="submit" class="button2" name="edit"  value="Done" />&nbsp;&nbsp; <input type="button" class="button2" value="Preview" onClick="preview()"/> &nbsp; <input type="button" class="button2" value="Cancel" onClick="JavaScript:window.location='<%=session.getAttribute("assignid")%>';"/></td>
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
		<tr valign="top">    
	      <td>Assignment Title: </td>   
          <td><div id="assigntitleprev" style="font-size:16px;"></div></td>  
         </tr> 
         <tr valign="top">    
          <td>Directions:</td>    
          <td><div id="directionsprev" class="fr-view" style="padding:5px; border:1px solid black; width:75%; font-size:16px;"> </div></td>  
         </tr>
		<tr valign="top">    
          <td>Description:</td>    
          <td><div id="descriptionprev" class="fr-view" style="padding:5px; border:1px solid black; width:75%; font-size:16px;"> </div></td>           </tr>	
	
         <tr>
         	<td>Status:</td>
         	<td><input type="radio" name=statusprev value="open" checked/> OPEN &nbsp; <input type="radio" name=statusprev value="close"/> CLOSE <input type="radio" name=status value="draft"/> DRAFT</td>
         </tr>
         
         <tr>
         	<td>Type:</td>
         	<td><input type="radio" name=type value="type1" checked/> TYPE1 &nbsp; <input type="radio" name=type value="type2"/> TYPE2 <input type="radio" name=type value="type2"/> TYPE2</td>
         </tr>
	</table>
</div>	
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
</script>
<p></p>
<br>
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