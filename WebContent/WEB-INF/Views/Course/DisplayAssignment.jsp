<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet"> 
<link href="${pageContext.request.contextPath}/resources/css/responses.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/feedbackStudent.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/button.css" rel="stylesheet">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<%int researcherid= (int)session.getAttribute("researcherid"); %>
<title>Assignment</title>
<script>
window.onload = function(){
	//document.getElementById('curve_chart').style.="hidden";
	//document.getElementById('recall_table').style.visibility="hidden";
	//document.getElementById('type1_response').style.visibility="hidden";
	//document.getElementById('type2_response').style.visibility="hidden";
	//document.getElementById('recalltable_type2').style.visibility="hidden";
	//document.getElementById("type1_response").style.display='none';
	//document.getElementById("type2_response").style.display='none';
 document.getElementById("colorCodes").style.display='none';
}
 function displayNewResponses_type1(){
	 //document.getElementById('curve_chart').style.visibility="visible";
		//document.getElementById('recall_table').style.visibility="visible"; 
		//document.getElementById('recalltable_type2').style.visibility="hidden";
		//document.getElementById('type1_response').style.display="block";
		 document.getElementById('curve_chart').style.display="block";
		document.getElementById('recall_table').style.display="block"; 
		document.getElementById('recalltable_type2').style.display="none";
		//displayallmyresponse();
		document.getElementById("responses").innerHTML= document.getElementById("recalltable").innerHTML;
		
 }
 function displayNewResponses_type2(){
		//document.getElementById('curve_chart').style.visibility="hidden";
		//document.getElementById('recall_table').style.visibility="hidden";
		//document.getElementById('type2_response').style.display="block";
		//document.getElementById('recalltable_type2').style.display="block"; 
		document.getElementById('curve_chart').style.display="none";
		document.getElementById('recall_table').style.display="none"; 
		document.getElementById("responses").innerHTML=document.getElementById("recalltable_type2").innerHTML;
		document.getElementById("colorCodes").style.display='block';
	 
 }
 
 
 

</script>
</head>
<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a><div class="topnav-right">
  	<a href="../StudentDashboard" class="class2">My Classes</a>
	<a href="#" class="class4">Help</a>
	<a href="../studentProfile" class="class5">My Profile</a>
	<a href="../signout" class="class7">Log out</a>
  </div></div>
 <p class="goback"><a href="../StudentDashboard">Back to my assignments</a></p>
  	<br>
<p class="heading2a">Assignment: ${model.displayedassignment.title}</p>

<p class="directions"><b>Directions: </b>${model.displayedassignment.directions}</p>
<br>

<div class="description">
<div class="descriptiontext">
${model.displayedassignment.description}
</div>
</div>
<div class="responsebox">
<table>
			<table>
				<tr>
					<td><div>
					
<span class="buttons" onClick="displayresponse();">My previous response</span>&nbsp;
					<c:set var="type" value="${model.displayedassignment.type}"/>
				<%-- <c:choose>
				<c:when test = "${type == 'type1'}">
			--%>	
			<span class="buttons" onClick='displaymyresponseType("${type}")'>See all my responses</span>
			<%--</c:when> 
			<c:when test = "${type == 'type2'}">
				
			<span class="buttons" onClick="displayNewResponses_type2()">See all my responses</span>
			</c:when>
			<c:when test = "${type == 'both'}">
				
			<span class="buttons" onClick="displayNewResponses_both()">See all my responses</span>
			</c:when>
			</c:choose>
			--%>
			
					</div>
					<br/>
					<div id="colorCodes">
			<span class="greendot"></span> Excellent&nbsp;
			<span class="bluedot"></span> Nice&nbsp;
           <span class="orangedot"></span> Improving&nbsp;
          
           <span class="reddot"></span>Need More&nbsp; 
           </div>
				<div id="type2_response_parent">
				<div>
				
				<!-- use c:choose conditions here -->
				  
				<c:set var="type" value="${model.displayedassignment.type}"/>
				<c:choose> 
				<c:when test = "${type == 'both'}">			
				<button type="button" id="type1_response" onclick="displaymyresponseType('type1')"><span style = "background-color: #005cb9; color: #ffffff;">Type1</span></button>
                <button type="button" id="type2_response" onclick="displaymyresponseType('type2')"><span style = "background-color: #005cb9;color: #ffffff;">Type2</span></button>	
				</c:when> 
				  
			    <%-- 
			    <c:otherwise>
				 <button type="button" id="type1_response" onclick="displayNewResponses_type1()"><span style = "background-color: #005cb9; color: #ffffff;">Type1</span></button>
                <button type="button" id="type2_response" onclick="displayNewResponses_type2()"><span style = "background-color: #005cb9;color: #ffffff;">Type2</span></button>	
  				 </c:otherwise>
  				--%>
				</c:choose>
				 
				</div>
				<div id="responses">
				<div>
				</div>
				</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>	
			
		<td class="newresponse"><form:form id="displayStudentGraph" modelAttribute="courseDetails" action="displayStudentGraph" method="post">    
	        <div >  
            	<h3>New Response </h3>
		          
			          <textarea name="text" style="border:2px solid; border-radius: 4px; padding:10px;" wrap="soft" ></textarea>
			          
			          <input type="hidden" name="modelID" value="${model.displayedassignment.choosenmodelID}"/>
			          <input type="hidden" name="model" value="student" />
			          <p>
			         	 <br>
			         	 <input class="button" type="submit" value="Submit" />
			          </p>
		      </div>    
	       </form:form> 
	    </td>
	</tr>
</table>

<div id="content" style="display:none;">
	<c:forEach var="response" items="${model.allresponses}" varStatus="status">
	<c:if test="${status.last}">
		<div id="recentresponse"><br><br>${response.text}</div>
	</c:if>

	</c:forEach>
</div>


<div id="recalltable" style="display:none;">
	<br><br>
	<div id="curve_chart" style="height:350px;">
	
	</div>
	<br>
	<table class="recall" id="recall_table">
		<tr>
			<th></th>
			<th>Recall-C</th>
			<th>Recall-P</th>
		</tr>
		<c:forEach var="response" items="${model.allresponses}">
		<tr>
			<th>
			<form:form id="displayresponse" action="displayresponse" method="post">    
	           <input type="hidden" name="text"  value="${response.text}" />
			   <input type="hidden" name="modelID" value="${response.expertID}"/>
			   <input type="hidden" name="studentresponseid" value="${response.studentresponseid}" />
			   <input type="hidden" name="model" value="student" />
			   <input class="button" type="submit" value="${response.title} (${response.createdDateTime})" />
	       </form:form>
	       </th>
			<td>${response.recallkeyconcepts}</td>
			<td>${response.recallKeylinks}</td>
		</tr>
		</c:forEach>
	</table>
</div>
<div id="recalltable_type2" style="display:none;">
	
	<table class="recall" id="recall_table_t">
		
		<c:forEach var="response" items="${model.allresponses}" varStatus="feedback_loop">
		<tr>
			<th>
			<form:form id="displayresponse" action="displayresponse" method="post">    
	           <input type="hidden" name="text"  value="${response.text}" />
			   <input type="hidden" name="modelID" value="${response.expertID}"/>
			   <input type="hidden" name="studentresponseid" value="${response.studentresponseid}" />
			   <input type="hidden" name="model" value="student" />
			   <input class="button feedbackbtn" type="submit" value="${response.title} (${response.createdDateTime})" id="recall_btn${feedback_loop.index}"/>
			  
	       </form:form>
	       </th>
			<td id="feedback_message${feedback_loop.index}">
			<script>
		
			
			if(${response.recallkeyconcepts} >= 0 && ${response.recallkeyconcepts}<=0.25)
			{
			document.getElementById("recall_btn${feedback_loop.index}").style.backgroundColor='red';
			
				document.getElementById("feedback_message${feedback_loop.index}").innerText = 'Focus on key concepts and their relations revise your response';
				
		    }
			else if(${response.recallkeyconcepts} > 0.25 && ${response.recallkeyconcepts} <= 0.5)
			{
				
				document.getElementById("recall_btn${feedback_loop.index}").style.backgroundColor='orange';
				document.getElementById("feedback_message${feedback_loop.index}").innerText = 'Need improvement.Keep on!';
			}
			
			else if(${response.recallkeyconcepts} > 0.5 && ${response.recallkeyconcepts} <= 0.75)
			{
				
				document.getElementById("recall_btn${feedback_loop.index}").style.backgroundColor='blue';
				document.getElementById("feedback_message${feedback_loop.index}").innerText = 'Very good.  Make it even better.';
			}
			
			else
			{
				
				document.getElementById("recall_btn${feedback_loop.index}").style.backgroundColor='green';
				document.getElementById("feedback_message${feedback_loop.index}").innerText = 'Looks similar to an expert model.';
			}
			
			
			</script></td>
			
		</tr>
		</c:forEach>
	</table>
</div>
</div></div>

<script type="text/javascript">


<c:choose>
<c:when test="${empty model.allresponses}">
document.getElementById("responses").innerHTML="No previous response";
</c:when>
<c:otherwise>
document.getElementById("responses").innerHTML=document.getElementById("recentresponse").innerHTML;
</c:otherwise>
</c:choose>

function displayresponse()
{	
	document.getElementById("responses").innerHTML=document.getElementById("recentresponse").innerHTML;
	if(document.getElementById("type1_response")!== null && document.getElementById("type2_response")!== null ){
	document.getElementById("type1_response").style.display='none';
	document.getElementById("type2_response").style.display='none';
	}
	document.getElementById("colorCodes").style.display='none';
}

function displaymyresponseType(type)
{
	console.log(type);
	document.getElementById("responses").innerHTML="";
  switch(type){
  case 'type1':
	  displayNewResponses_type1();
	  displayallmyresponses();
	  break;
  case 'type2':
	  displayNewResponses_type2();
	  break;
 default:
	 document.getElementById("type1_response").style.display='block';
	document.getElementById("type2_response").style.display='block';
     break;
  }
}
	
	function displayallmyresponses(){
	google.charts.load('current', {'packages':['corechart']});
     google.charts.setOnLoadCallback(drawChart);
	
     function drawChart() {
       var data = google.visualization.arrayToDataTable([
       	['Responses', 'Recall C', 'Recall P'],
       	<c:forEach var="response" items="${model.allresponses}">
       	['${response.title}', ${response.recallkeyconcepts}, ${response.recallKeylinks}],
       	</c:forEach>
       ]);

       var options = {
         title: '         See how you progress!!',
         curveType: 'function',
         legend: { position: 'bottom' },
         vAxis: {
             minValue: 0,
             ticks: [0, .25, .5, .75, 1]
           }
       };

       var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

       chart.draw(data, options);
     }
     
     <c:if test="${empty model.allresponses}">
 	document.getElementById("responses").innerHTML="No previous response";
 	</c:if>
}
</script>
<!-- <script type="text/javascript">

      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
	
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
        	['Responses', 'Recall c', 'Recall P'],
        	<c:forEach var="response" items="${model.allresponses}">
        	['${response.title}', ${response.recallkeyconcepts}, ${response.recallKeylinks}],
        	</c:forEach>
        ]);

        var options = {
          title: 'Student Performance',
          curveType: 'function',
          legend: { position: 'bottom' }
         
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script> -->
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