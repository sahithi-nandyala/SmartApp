<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reference Model Form</title>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<style>
table.Assignmentdetails {
	 color: #333; /* Lighten up font color */
   font-family: Lato, Helvetica, Arial, sans-serif; /* Nicer font */
   font-size: 18px;
    border-collapse: collapse; 
    border-spacing: 0; 
  
}

table.Assignmentdetails td {
	padding:10px; 
  text-align: left;
			padding-left: 30px;
}
table.Assignmentdetails th {
	font-size: 22px;
   text-align: right;
		padding: 20px 0 20px 0;
	vertical-align: top;
}

.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto;/* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    text-align:center;
    valign:middle;
    padding:left:50px;
}

/* Modal Content */
#modelcontainer {
  /*   background-color: #fefefe; */
    margin: auto;
    padding: 20px;
/*     border: 1px solid #888; */
    width: 20%;
    height:20%;
    overflow: auto;
    text-align:center;
}

#loader {
  border: 11px solid #f3f3f3;
  border-radius: 50%;
  width: 50px;
  height: 50px;
  -webkit-animation: spin 2s linear infinite; /* Safari */
  animation: spin 2s linear infinite;
  text-align:center;
   border-top: 11px solid #5784cc;
 border-bottom: 11px solid #5784cc;
}


/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>

<script>

function addrows()
{
	var now=document.getElementById('keyconcepts');
	var row=document.createElement("tr");
	row.innerHTML="<input name='manualKeyconcepts' type='text' /><input type='button' style='background-color:white;border: none;' value='x' onClick='deleterows(this)' />";
	 if(now.nextSibling){
		 now.parentNode.insertBefore(row,now.nextSibling);
		  }else{
			  now.parentNode.appendChild(row);
		  }
}

function deleterows(e)
{

	e.closest('tr').remove();
}
</script>
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
<p class="goback"><a href='<%=session.getAttribute("assignid")%>'>back to Assignment</a></p>
<br>
<p class="heading3a">Create a Reference (Expert Model)</p> 
<div class="maincontent">
<form:form id="feedback" modelAttribute="courseDetails"	action="feedback" method="post">
       <table class="Assignmentdetails" cellspacing="20">
       <tr>
       <th>Assignment ID:</th><td>${model.referencedetails.assignmentID}</td>
       </tr>
     	 <tr>
     	 <th>Assignment Title:</th><td> ${model.referencedetails.assignTitle}</td>
       </tr>
		 <tr valign="top">    
          <th>Reference Title:</th>   
          <td><input type="text" name="title" style="border-radius:2px; margin-top:15px; height:30px; width:700px; border:1px solid #97a675; font-size:16px;"  /></td>  
         </tr>    
         <tr valign="top">    
          <th>Textual explanation:</th>    
          <td><textarea name="text" rows="15" cols="75" wrap="soft" style="border-radius:2px; margin-top:15px; width:700px; border:1px solid #97a675; font-size:16px; padding:5px;; overflow:auto;" required></textarea></td>  
         </tr>                  
         <tr>    
            <td><input type="hidden" name="model" value="expert" /></td>  
         </tr>	
         <tr id="researchercontent">
	         <th>Automatic analysis:</th>
			 <td>
			 	<select name="centrality">
			 	  <option value="CC">Clustering Coefficient</option>
				  <option value="BC">Betweenness Centrality</option>
				  <option value="PR">Page Rank</option>
				  <option value="CL">Closeness Centrality</option>
				  <option value="EV">Eigen Vector Centrality</option>		  
				</select>
			</td>
         </tr>       		
		<tr id="teachercontent">
		 <th>Automatic analysis:</th>
		 <td><select name="centrality">
		 	  <option value="CC">Metric 1</option>
			  <option value="BC">Metric 2</option>		  
			</select>
		</td></tr>
		<tr><th>Cut-off point:</th><td><input type="text" name="threshold" value="75" />%</td>
		 </tr>
		 <tr>
			<td></td>
			<td><input name="submit" type="submit" value="Next" onClick="document.getElementById('myModal').style.display='block';"/></td>
		</tr>
		</table>
	</form:form>
	</div>
<div id="myModal" class="modal">
  <div id="modelcontainer">
  <div id="loader"></div>
  </div>
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

<script type="text/javascript">
window.onload = function() {
<% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid!=-1) { %>

	//document.getElementById("researchercontent").style.display="block";
	document.getElementById("teachercontent").style.display="none";
	var elem = document.getElementById('teachercontent');
    elem.parentNode.removeChild(elem);
    return false;
	<%
	}
	else
	{%>
	//document.getElementById("researchercontent").style.display="none";
	var elem = document.getElementById('researchercontent');
    elem.parentNode.removeChild(elem);
    return false;
//	document.getElementById("teachercontent").style.display="block";
	<% } %>
}
</script>


</html>