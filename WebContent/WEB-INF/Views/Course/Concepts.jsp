<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/dragdrop.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button2.css" rel="stylesheet">
<title>Concepts</title>
<style>
.button2{
	  font-size: 24px;}
.button2:hover{
	  font-size: 24px;
}
.button2:active{
	  font-size: 24px;
}
</style>
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
<p class="goback"><a href='<%=session.getAttribute("assignid")%>'>back to assignment</a></p>
 <p class="heading2a">Reference Title (Optional):&nbsp;${modelname.title}</p> 
<br>
<input id="expertid" type="hidden" value="${modelname.expertID}" />

<table class="table" style="width:100%;padding:10px;">
<tr>
	<%-- <td>&nbsp;&nbsp;<label for="classId" class="labelcss"><b>Class ID&nbsp;:</b>&nbsp;${model.classDetails.classId}</label></td> --%>
	 <td><label for="className" class="labelcss"><b>Textual Explanation:</b>&nbsp;</label></td></tr>
	 <tr><td><div style="width:80%;border-radius: 5px;border: 3px solid #00738c;margin: 10px; font-size:13px; padding:10px; overflow:auto;cursor:not-allowed" id="descriptionEdit" >${modelname.text}</div></td>
</tr>
</table>
<table class="concepttable">
	<tr>
	<td>
			<h3>Key Concepts</h3>
		<ol id="key" data-draggable="target"> 
		<c:forEach var="kconcept" items="${modelname.keyConcepts}" >
			<li data-draggable="item">${kconcept}</li>
		</c:forEach>
		</ol>
	</td>
	<td>
			<h3>Basic Concepts</h3>
		<ol id="basic" data-draggable="target">
				<c:forEach var="bconcept" items="${modelname.bConcepts}" >
			<li data-draggable="item">${bconcept}</li>
		</c:forEach>
		</ol>
	</td>
	</tr>
</table>

<table class="concepttable">
	<tr>
	<td>
		<h3>Key Concept Synonyms</h3>
	</td>
	</tr>
	<tr>
	<td>
		<table border="1" id="kconceptSynonymsTable">
			<c:forEach var="kconcept" items="${modelname.keyConcepts}" varStatus="keyConceptIndex">
				<tr>
					<td><b><span id="keyConceptText_${keyConceptIndex.index}">${kconcept}</span></b></td>
					<td><input id="keyConceptSynonym_${keyConceptIndex.index}" name="keyConceptSynonym_${keyConceptIndex.index}" type="text" value="${modelname.keyConceptSynonyms[kconcept]}"/></td>
				</tr>
			</c:forEach>
		</table>
	</td>
	</tr>
</table>

<table class="buttontable">
	<tr>
	 <td><input class="button2" type="button" value="Save" onClick="submitallconcepts()" /></td>
	  <td>
		 <form:form id="feedbacksubmit" action="displayexpertgraph" method="post" style="display:none;">
			<input id="modelID" name="modelID" type="hidden" value="${modelname.expertID}" />
			<input  class="button2" type="submit" value="Feedback"  />
		 </form:form>
	 </td>
	 
	</tr>
	<tr>
		<td colspan="2"><br><br> **NOTE : Interchange key concepts and basic concepts by dragging the concepts and dropping them onto either of the boxes.  <br><br><br></td>
	</tr>
</table>

<script src="${pageContext.request.contextPath}/resources/js/dragableitems.js"></script>
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