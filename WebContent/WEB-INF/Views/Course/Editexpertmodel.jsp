<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/SMART/resources/css/home.css" rel="stylesheet">
<link href="/SMART/resources/css/button2.css" rel="stylesheet">
<title>Update</title>
<style>
table th{
  font: bold 18px Lato;
  vertical-align: top;
  padding: 10px;
  text-align: right;
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
  
<p class="heading2a">Update Reference (Expert Model)</p>  
<br>               
       <form:form action="updateexpertmodel" method="post">
		<table>		
		 <tr>    
          <th>Reference Title: </th>   
          <td><input type="hidden" name="modelID" value="${model.expertID}"/>
          <input type="text" name="title" style="padding:5px; width:75%; height:20px; font-size:16px;" value="${model.title}"/>  
          </td>  
         </tr>    
         <tr>    
          <th>Textual explanation:</th>    
          <td><textarea name="text" rows="15" cols="110" style="padding:5px; width:75%; font-size:16px;" wrap="soft" required>${model.text} </textarea></td>  
         </tr>                  
         <tr>    
          <td></td>    
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
			<td><input name="submit" class="button2" type="submit" value="Next" onClick="document.getElementById('myModal').style.display='block';"/>
			<input type="button"class="button2" value=Cancel onClick="window.location.href = '<%=session.getAttribute("assignid")%>';"/></td>
		</tr>
		</table>
	</form:form>
	</div>

	
<div id="myModal" class="modal">
  <div id="modelcontainer">
  <div id="loader"></div>
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
<style>

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
<script type="text/javascript">
window.onload = function() {
<% int researcherid= (int)session.getAttribute("researcherid");
if(researcherid!=-1) { %>

//	document.getElementById("researchercontent").style.display="block";
	document.getElementById("teachercontent").style.display="none";
	<%
	}
	else
	{%>
	document.getElementById("researchercontent").style.display="block";
//	document.getElementById("teachercontent").style.display="block";
	<% } %>
}
</script>
</html>