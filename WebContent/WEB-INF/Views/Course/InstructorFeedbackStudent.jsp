<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="fr">
<head>
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/feedbackStudent.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/feedbackStudent.js"></script>
<script src="http://d3js.org/d3.v4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>
<script>
function openTab(evt, divName) {
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(divName).style.display = "block";
    evt.currentTarget.className += " active";
}

function closeeditform(e)
{
	var p= e.parentElement;
	p.parentElement.style.display="none";
};

function closePopUp(target_id){
	document.getElementById(target_id).style.display = "none";	
	}
window.onload = function() {
	document.getElementById("conceptbtn").click();
	displayExpertBase();
	<% int researcherid= (int)session.getAttribute("researcherid");
	if(researcherid==-1) { %>

		document.getElementById("infodownload").style.display="none";

		<% } %>
}

function displayExpertFullDiv()
{
	document.getElementById('modelcontainer').innerHTML=document.getElementById('expertfulldiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	displayExpert();
}

function displayStudentFullDiv()
{
	document.getElementById('modelcontainer').innerHTML=document.getElementById('expertfulldiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	displayStudent();
}

function displayStudent(){
    
    d3.select("#svgButton").selectAll("*").remove();
	var svg= d3.select("#svgButton");
    
    var chartDiv = document.getElementById("modelcontainer");
    var width = chartDiv.clientWidth;
    var height = chartDiv.clientHeight;
    
    svg
    .attr("width", width)
    .attr("height", height);
    
    var g = svg.append("g")

    .attr("class", "everything");

    var zoom_handler = d3.zoom()
        .on("zoom", zoom_actions);
    
 	var nodes_data=${modelname.studentNodes} ;
 	var links_data=${modelname.studentLinks} ;


	var simulation = d3.forceSimulation()
	 .nodes(nodes_data);
	var link_force =  d3.forceLink(links_data)
	     .id(function(d) { return d.name; });
	simulation
	.force("charge_force", d3.forceManyBody().strength(chargeVal))
	.force("center_force", d3.forceCenter(width/2, height/2))
	.force("links",link_force.distance(90).strength(1));
	
	simulation.on("tick", tickActions );
	
	var textElements = g.append('g')
	.selectAll('text')
	.data(nodes_data)
	.enter().append('text')
	.text(node => node.name)
	.attr('font-size', textFont)	
	.attr('font-family', '"Trebuchet MS", Arial, Helvetica, sans-serif;')
	.attr('fill', textColor)
	.attr('dx', 20)
	.attr('dy', 4);  
	
	var link = g.append("g")
	.attr("class", "links")
	.selectAll("line")
	.data(links_data)
	.enter().append("line")
	.attr("stroke-width", strokeWidth)
	.style("stroke", linkColour);    
	
	var node = g.append("g")
	.selectAll("circle")
	.data(nodes_data)
	.enter()
	.append("circle")
	.attr("r", circleRadius)
	.attr("fill", circleColour);
	
	var drag_handler = d3.drag()
	.on("start", drag_start)
	.on("drag", drag_drag)
	.on("end", drag_end);   
	
	drag_handler(node);
	zoom_handler(svg);   

	function zoom_actions(){
	    g.attr("transform", d3.event.transform)
	}
	
	function circleColour(d){
		if(d.type =="G"){
			return "darkGreen";
		} 
		else if(d.type =="Y"){
			return "#566573";
		} 
		else if(d.type=="R"){
			return "darkRed";
		}
		else {
			return "lightGray";
		}
	}
	
	function circleRadius(d){
		if(d.type =="N"){
			return 5;
		} else {
			return 7;
		}
	}
	
	function textFont(d){
		if(d.type =="N"){
			return 10;
		} else {
			return 15;
		}
	}
	
	function textColor(d){
		if(d.type =="N"){
			return "lightGray";
		} 
		else {
			return "black";
		}
	}
	
	function chargeVal(d){
		if(d.type =="N"){
			return -500;
		} else {
			return -500;
		}
	}
	
	function linkColour(d){
		if(d.type == "A"){
			return "#E0B506";
		}
		else if(d.type == "G"){
			return "darkGreen";
		}
		else if(d.type == "R"){
			return "darkRed";
		}
		else if(d.type == "Y"){
			return "yellow";
		}
		else {
			return "lightGray";
		}
	}
	function strokeWidth(d){
		if(d.type == "A"){
			return 3;
		} 
		else if(d.type == "G"){
			return 3;
		} 
		else if(d.type == "R"){
			return 3;
		} 
		else {
			return 1;
		}
	}
	
	function drag_start(d) {
		if (!d3.event.active) simulation.alphaTarget(0.3).restart();
		d.fx = d.x;
		d.fy = d.y;
	}
	
	function drag_drag(d) {
		d.fx = d3.event.x;
		d.fy = d3.event.y;
	}
	
	function drag_end(d) {
		if (!d3.event.active) simulation.alphaTarget(0);
		d.fx = null;
		d.fy = null;
	}
			
	function tickActions() {
		node
		.attr("cx", function(d) { 
				d.x= Math.max(7, Math.min(width - 7, d.x)); 
				return d.x;
			})
		.attr("cy", function(d) { 
				d.y= Math.max(7, Math.min(height - 7, d.y)); 
				return d.y;
			});
		
		textElements
		.attr("x", node => node.x)
		.attr("y", node => node.y);
		
		link
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });
	} 
}   


function displayExpert(){
	d3.select("#svgButton").selectAll("*").remove();
	var svg= d3.select("#svgButton");
    
    var chartDiv = document.getElementById("modelcontainer");
    var width = chartDiv.clientWidth;
    var height = chartDiv.clientHeight;
    
    svg
    .attr("width", width)
    .attr("height", height);
    
    var g = svg.append("g")

    .attr("class", "everything");

    var zoom_handler = d3.zoom()
        .on("zoom", zoom_actions);
    
	var nodes_data=${modelname.nodes} ;
	var links_data=${modelname.links} ;
	
	var simulation = d3.forceSimulation()
	.nodes(nodes_data);
	var link_force =  d3.forceLink(links_data)
            .id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(40).strength(1));

simulation.on("tick", tickActions );


var textElements = g.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('font-family', '"Trebuchet MS", Arial, Helvetica, sans-serif;')
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = g.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour); 

var node = g.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);
    		
var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node);
zoom_handler(svg);   
function zoom_actions(){
    g.attr("transform", d3.event.transform)
}

function circleColour(d){
	if(d.type =="G"){
		return "black";
	} 
	else if(d.type =="Y"){
		return "#566573";
	} 
	else if(d.type=="R"){
		return "darkRed";
	}
	else {
		return "lightGray";
	}
}

function circleRadius(d){
	if(d.type =="N"){
		return 5;
	} else {
		return 7;
	}
}
function textFont(d){
	if(d.type =="N"){
		return 10;
	} else {
		return 15;
	}
}

function textColor(d){
	if(d.type =="N"){
		return "lightGray";
	} 
	else {
		return "black";
	}
}

function chargeVal(d){
	if(d.type =="N"){
		return -500;
	} else {
		return -500;
	}
}

function linkColour(d){
	if(d.type == "A"){
		return "#E0B506";
	}
	else if(d.type == "G"){
		return "darkGreen";
	}
	else if(d.type == "R"){
		return "darkRed";
	}
	else if(d.type == "Y"){
		return "yellow";
	}
	else {
		return "lightGray";
	}
}


function strokeWidth(d){
	if(d.type == "A"){
		return 3;
	} 
	else if(d.type == "G"){
		return 3;
	} 
	else if(d.type == "R"){
		return 3;
	} 
	else {
		return 1;
	}
}

function drag_start(d) {
	if (!d3.event.active) simulation.alphaTarget(0.3).restart();
	d.fx = d.x;
	d.fy = d.y;
}

function drag_drag(d) {
	d.fx = d3.event.x;
	d.fy = d3.event.y;
}


function drag_end(d) {
	if (!d3.event.active) simulation.alphaTarget(0);
	d.fx = null;
	d.fy = null;
}

function tickActions() {
	node
	.attr("cx", function(d) { 
			d.x= Math.max(7, Math.min(width - 7, d.x)); 
			return d.x;
		})
	.attr("cy", function(d) { 
			d.y= Math.max(7, Math.min(height - 7, d.y)); 
			return d.y;
		});
	
	textElements
	.attr("x", node => node.x)
	.attr("y", node => node.y);
	
	link
	.attr("x1", function(d) { return d.source.x; })
	.attr("y1", function(d) { return d.source.y; })
	.attr("x2", function(d) { return d.target.x; })
	.attr("y2", function(d) { return d.target.y; });
} 
}



function displayExpertBase(){
	
		var svg= d3.select("svg");
	    
	    var chartDiv = document.getElementById("container");
	    var width = chartDiv.clientWidth;
	    var height = chartDiv.clientHeight;
	    
	    svg
	    .attr("width", width)
	    .attr("height", height);
	    
	    var g = svg.append("g")

	    .attr("class", "everything");

	    var zoom_handler = d3.zoom()
	        .on("zoom", zoom_actions);
	
	var tip = d3.tip()
    	.attr('class', 'd3-tip')
    	.offset([-10, 0])
    	.html(function (d) {
        	return d.concepts + "";
    	})
    	svg.call(tip);
    
	var nodes_data=${modelname.studentFeedbackNodes} ;
	var links_data=${modelname.studentFeedbackLinks} ;


	var simulation = d3.forceSimulation()
		.nodes(nodes_data);
	var link_force =  d3.forceLink(links_data)
    	.id(function(d) { return d.name; });


    
simulation
	.force("charge_force", d3.forceManyBody().strength(chargeVal))
	.force("center_force", d3.forceCenter(width/2, height/2))
	.force("links",link_force.distance(50).strength(1));

simulation.on("tick", tickActions );


var textElements = g.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('font-family', '"Trebuchet MS", Arial, Helvetica, sans-serif;')
.attr('fill', textColor)
.attr('dx', 15)
.attr('dy', 4);  

var link = g.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);     

var node = g.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour)
.on("mouseover", tip.show) 
.on("mouseout", tip.hide);


var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node);
zoom_handler(svg);   
function zoom_actions(){
    g.attr("transform", d3.event.transform)
}

function circleColour(d){
	if(d.type =="G"){
		return "#8bc34a";
	} 
	else if(d.type =="Y"){
		return "#e91e63";
	} 
	else if(d.type=="R"){
		return "#fb8c00";
	}
	else {
		return "lightGray";
	}
}

function circleRadius(d){
	if(d.type =="N"){
		return 2;
	} else {
		return 5;
	}
}

function textFont(d){
	if(d.type =="N"){
		return 9;
	} else {
		return 15;
	}
}

function textColor(d){
	if(d.type =="N"){
		return "lightGray";
	} 
	else {
		return "black";
	}
}

function chargeVal(d){
	if(d.type =="N"){
		return -3000;
	} else {
		return -3000;
	}
}

function linkColour(d){
	if(d.type == "A"){
		return "black";
	}
	else if(d.type == "G"){
		return "#8bc34a";
	}
	else if(d.type == "R"){
		return "#fb8c00";
	}
	else if(d.type == "Y"){
		return "#e91e63";
	}
	else {
		return "lightGray";
	}
}

function strokeWidth(d){
	if(d.type == "A"){
		return 2;
	} 
	else if(d.type == "G"){
		return 2;
	} 
	else if(d.type == "R"){
		return 2;
	} 
	else {
		return 1;
	}
}

function drag_start(d) {
	if (!d3.event.active) simulation.alphaTarget(0.3).restart();
	d.fx = d.x;
	d.fy = d.y;
}

function drag_drag(d) {
	d.fx = d3.event.x;
	d.fy = d3.event.y;
}


function drag_end(d) {
	if (!d3.event.active) simulation.alphaTarget(0);
	d.fx = null;
	d.fy = null;
}

function tickActions() {
	node
	.attr("cx", function(d) { 
			d.x= Math.max(7, Math.min(width - 7, d.x)); 
			return d.x;
		})
	.attr("cy", function(d) { 
			d.y= Math.max(7, Math.min(height - 7, d.y)); 
			return d.y;
		});
	
	textElements
	.attr("x", node => node.x)
	.attr("y", node => node.y);
	
	link
	.attr("x1", function(d) { return d.source.x; })
	.attr("y1", function(d) { return d.source.y; })
	.attr("x2", function(d) { return d.target.x; })
	.attr("y2", function(d) { return d.target.y; });
} 
}
function displayExpertResponsePopUp(){
	document.getElementById('myModalExpertResponse').style.display = "block";
}
function displayStudentResponsePopUp(){
	document.getElementById('myModalStudentResponse').style.display = "block";
}
function displayKSComparisonPopUp(){
	document.getElementById('myModalKSComparison').style.display = "block";
}
function displayThinkMorePopUp(){
	document.getElementById('myModalThinkMore').style.display = "block";
}

</script>
<style>
body{
font:calibri 12px;
}

.conceptheading{
	width: 95%;
	font-size: 15px;
    font-weight: bold;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    margin-top: 10px;
    margin-left: 5px;
}

.missing{
	font-size: 15px;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    margin-top: 5px;
    margin-left: 5px;
}


.tab {
    overflow: hidden;
    border: 1px solid #ccc;
    background-color: #f1f1f1;
}

/* Style the buttons that are used to open the tab content */
.tab button {
    background-color: inherit;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    transition: 0.3s;
    color: black;
    font-weight: bold;
    font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
    font-size: 15px;
}
.help{
	font-size: 28px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#fff;
    text-align: left;
    position: relative;
    margin-top: 20px;
    margin-left: 20px;
    padding: 10px;
    float: right;
    background-color: #214cb8; /*change to blue */
    border-radius: 50%;
}
#modelcontainerExpertResponse, #modelcontainerStudentResponse, #modelcontainerKSComparison, #modelcontainerThinkMore{
font-size:14px;
margin: 5px;

border: 1px solid black;
}

/*#modelcontainerThinkMore{
font-size:30px;
margin: 5px;

}*/
.help1{
	font-size: 28px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#fff;
    text-align: left;
    position: relative;
    margin-top: 20px;
    /*margin-left: 20px;*/
    /*margin-left: 4%;*/
    left: -51%;
    padding: 10px;
    float: right;
    background-color: #214cb8; /*change to blue */
    border-radius: 50%;
}
	
.help2{
	font-size: 28px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#fff;
    text-align: left;
    position: relative;
    margin-top: 20px;
    /*margin-left: 20px;*/
    /*margin-left: 4%;*/
    left: -6.5%;
    padding: 10px;
    float: right;
    background-color: #214cb8; /*change to blue */
    border-radius: 50%;
}

/* Change background color of buttons on hover */
.tab button:hover {
    background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
    background-color: #ccc;
}

/* Style the tab content */
#concetps, #links, .tabcontent {
    display: none;
    padding: 6px 12px;
    border: 1px solid #fff;
    border-top: none;
    height: 85%;
    overflow: scroll;
}

.links line {
  stroke: #999;
  stroke-opacity: 0.6;
}

.nodes circle {
  stroke: black ;
  stroke-width: 0px;
}

.d3-tip {
  line-height: 1;
  font-weight: bold;
  font-size:12px;
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  padding: 12px;
  background: #fdf2e9;
  color: #000;
  border-radius: 2px;
  pointer-events: none;
}

.d3-tip:after {
  box-sizing: border-box;
  display: inline;
  font-size: 10px;
  width: 100%;
  line-height: 1;
  color: rgba(0, 0, 0, 0.8);
  position: absolute;
  pointer-events: none;
}

.d3-tip.n:after {
  content: "\25BC";
  margin: -1px 0 0 0;
  top: 100%;
  left: 0;
  text-align: center;
}

.modal {
	display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    left: 0;
    top: 0;
    width: 100%;
    height:95vh;
    background-color: white;
    border:none;
}


#myModalExpertResponse{
      display: none;
    position: fixed;
    z-index: 1;
    left: 40%;
    top: 10%;
    width: 45%;
    height: 40%;
    background-color: white;
    overflow-y: visible;
}

#myModalThinkMore{
    display: none;
    position: fixed;
    z-index: 1;
    left: 45%;
    top: 40%;
    width: 45%;
    height: 40%;
    background-color: white;
    overflow-y: visible;
}

#myModalStudentResponse{
   display: none;
    position: fixed;
    z-index: 1;
    left: 3%;
    top: 20%;
    width: 45%;
    height: 40%;
    background-color: white;
    overflow-y: visible;
}
#myModalKSComparison
{
    display: none;
    position: fixed;
    z-index: 1;
    left: 40%;
    top: 25%;
    width: 45%;
    height: 40%;
    background-color: #fefefe;
    overflow-y: visible;
       
}


/* Modal Content */
#modelcontainer, #modelcontainerExpertResponse, #modelcontainerStudentResponse {
	
    position: absolute;
    width: 50%;
    border: none;
    height: 70vh;
    left: 20%;
    font-size: 50px:
}

#modelcontainerKSComparison {
position: absolute;
    width: 50%;
    border: none;
    height: 70vh;
    left: 20%;
    font-size: 50px: 
    }
    
#modelcontainerThinkMore {
position: absolute;
    width: 50%;
    border: none;
    height: 70vh;
    left: 20%;
    font-size: 50px: 
    }

#descriptionEdit span
{
background-color:#f7f799;
}
.prevresponses{
    margin-left: 20px;
}
</style>
<%
int userid=0;
if(session.getAttribute("userid") == null){
	response.sendRedirect("login");
}
else userid = (int) session.getAttribute("userid");
//session.setAttribute("userid",userid);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student Feedback</title>
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
<p class="goback"><a href='<%=session.getAttribute("assignid")%>'>back to assignment</a></p>
  <br>
  <p class="heading3a">
  Results and Feedback (Assignment: ${modelname.assignmentDetails.title})
 </p>
 <br><br><br>
 <h4>Hi. this feedback aims to “HELP” you build up a better understanding.  Keep on improving! To write a revised version, return to the assignment page.
 </h4>
 <br>
 <br>
  <div class="maincontent3">
 <h3>Previous responses</h3>
 <br>
 <div class="prevresponses">
<table>
<tr><td>
<table>
<c:forEach var="response" items="${modelname.allresponses}">
	<tr><th>
		
			<form:form id="displayresponse" action="displayresponsetoinstructor" method="post">    
	           <input type="hidden" name="text"  value="${response.text}" />
			   <input type="hidden" name="modelID" value="${response.expertID}"/>
			   <input type="hidden" name="studentresponseid" value="${response.studentresponseid}" />
			   <input type="hidden" name="model" value="student" />			
			   <c:if test="${modelname.studentresponseid eq response.studentresponseid}">
		 			<img src="${pageContext.request.contextPath}/resources/img/tick.png" width="20px" height="20px"/ style="margin-left: -20px;">
				</c:if>   
			   <input class="button" type="submit" value="${response.title} (${response.createdDateTime})" />
			   
	       </form:form>
	</th></tr>
	</c:forEach>
	</table></td>
	<td>
	<table id="infodownload" class="table" style="width:100%; padding:10px;float:right" >
	<tr>
		<td><input type="button" class="button2" value="Download All Metrics Information" onClick="location.href='downloadStudent.CSV/${modelname.studentresponseid}'" id="downloadResultsButton"/></td>
	</tr>
	</table>
	</td></tr>
	
</table>
</div>
<div class="expertResponseDiv">
<div class="heading3" style="float:left;">
	 	Expert Response: 
	 </div>
	 <div class="help" id="expertResponseHelp" onclick="displayExpertResponsePopUp()">
        Help
    </div>
	 	<div name="expertresponse" style="position: relative; width: 100%; height: 70%; border-radius: 6px;border: 6px solid #81b0b2; border-radius: 5px;margin: 10px; font-size:16px; font-family:Lato; padding:10px; cursor:not-allowed; overflow:auto;" id="descriptionEdit" >${modelname.expert.text}</div>
</div>

<div class="yourResponseDiv">
<div class="heading3b" style="float:left;">
	 	 Student Response: 
	 </div>
	 <div class="help" id="expertResponseHelp" onclick="displayStudentResponsePopUp()">
        Help
    </div>
	 	<div name="yourresponse"  style="width:100%; height: 70%; position: relative; border-radius: 6px;border: 6px solid #005cb9; border-radius: 5px; margin: 10px; font-size:18px; font-family:Lato; padding:10px; cursor:not-allowed; overflow:auto;" id="descriptionEdit" >${modelname.text}</div>
</div>
    <div class="heading4b">Think More About!</div> 
    <div class="help2" id="thinkMoreHelp" onclick="displayThinkMorePopUp()">
        Help
    </div>
 <div class="heading4">
 		Key Knowledge Structure (KS) Comparison
 	</div>
 	<div class="help1" id="expertResponseHelp" onclick="displayKSComparisonPopUp()">
        Help
    </div>
    
<div id="container" class="graphdiv">

	 <div class="buttonsdiv">
<table style="background-color:#00738c; margin-top:5px;">
 <tr> 
 <td>
 	<input type="button" class="button" value="Expert's Full KS" onClick="displayExpertFullDiv()"/>
 </td>
 <td>
 	<input type="button" class="button" value="Student's Full KS" onclick="displayStudentFullDiv()"/>
 </td>
 </table>
 </div>
	<svg xmlns="http://www.w3.org/2000/svg" style="position:relative; max-width:100%; max-height:450px;"></svg>
	<script src="http://d3js.org/d3.v4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>
</div>
    <div class="feedbackdiv">
	  <div class="tab">
	    <button id="conceptbtn" class="tablinks" onclick="openTab(event, 'concepts')">Missing Concepts</button>
	   	<button id="linksbtn" class="tablinks" onclick="openTab(event, 'links')">Missing Links</button>
	  	<button id="synonymsbtn" class="tablinks" onclick="openTab(event, 'synonyms')">Synonyms</button>
	 
	 </div> 
	  <div id="concepts" class="tabcontent">
	<p class="conceptheading">
	  Think more about how to include the following “missing” key ideas in your response: </p><br /><br />
	  <table>
	  <c:forEach var="element" items="${modelname.missingConcepts}">
	  <tr>
	  	<td class="missing"><span class="missingdot"></span> ${element}</td>
	  </tr>
	  </c:forEach>
	  </table>
	</div>
	
	<div id="links" class="tabcontent">
	  <p class="conceptheading">
	  Think more about how to describe the following “missing” relations between key concepts:  </p><br /><br />
	   <table>
	  <c:forEach var="element" items="${modelname.missingLinksForDisplay}">
	  <tr>
	  	<td class="missing"><span class="missingdot"></span> ${element}</td>
	  </tr>
	  </c:forEach>
	  </table>
	</div>
	
	<div id="synonyms" class="tabcontent">
	  <p class="conceptheading">
	  Here is the reference key concept - synonym information:  </p><br /><br />
	  <table>
	  <c:forEach var="element" items="${modelname.expert.keyConceptSynonyms}">
	  <c:if test="${not empty element.value}">
	  <tr>
	  	<td class="missing"><span class="missingdot"></span> ${element.key} : ${element.value}</td>
	  </tr>
	  </c:if>
	  </c:forEach>
	  </table>
	</div> 
</div>

<div class="legenddiv">
<span class="greendot"></span> Correct&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span class="orangedot"></span> Missing&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<span class="purpledot"></span> Wrong
</div>

<div class="notediv">
Note: To see supporting ideas, mouse over a key concept (a dot in the graph)
<br />
<br />
</div>


</div>
<div id="myModal" class="modal">
	<div id="modelcontainer"></div>
</div>
<div id="myModalExpertResponse" class="modal" style="height:30%">
<input type="button" class="close" onClick="closePopUp('myModalExpertResponse')" value="X" />
	<div id="modelcontainerExpertResponse">
	
	<!--   style="border:black 1px solid;"-->
	
	<h2 style="font-size:20px"><span style="color:#042559">Help:</span><span style="color:#4185f2">Expert Response</span></h2>
	<p>This an exemplary response created by an expert. You are encouraged to revise your work by including key concepts (highlighted) and their propositional relations (e.g., A affected B). 
	</p>
	<ul>
	<li>Focus on key concepts (highlighted).
	</li>
	<li>Try to describe the relationships between key concepts. 
	</li>
	<li>Enrich your description with supportive concepts (bolded).
	</li>
	</ul>
	</div>
</div>

<!--  student response modal -->
<div id="myModalStudentResponse" class="modal" style="height:25%">
<input type="button" class="close" onClick="closePopUp('myModalStudentResponse')" value="X" />
	<div id="modelcontainerStudentResponse">
	<!-- style="border:black 1px solid;" -->
	
	<h2 style="font-size:20px"><span style="color:#042559"> Help:</span><span style="color:#4185f2"> Student Response</span></h2>
	<p>It shows your latest response in which key concepts (matched with those used by the expert response) are highlighted.</p>
	<ul>
	<li>Compare your work to the expert’s example.
	</li>
	<li>Find what key concepts and the descriptions of the relationships between key concepts you have missed. 
	</li>
	<li>Try to create a revised version by including missing key concepts and their relationships. 
	</li>
	<li>To make another version, return to the assignment page.
	</li>
	</ul>
	</div>
</div>


<!--  KSComparison modal -->
<div id="myModalKSComparison" class="modal" style="height:40%">
<input type="button" class="close" onClick="closePopUp('myModalKSComparison')" value="X" />
	<div id="modelcontainerKSComparison"> <!--  style="border:black 1px solid;"-->
	
	<h2 style="font-size:20px"><span style="color:#042559">Help:</span><span style="color:#4185f2"> Key Knowledge Structure</span></h2>
	<p>The key knowledge structure (K-KS) consists with key concepts and their relations (propositions) extracted from the expert example.  You are encouraged to create a similar K-KS by revising your response with the suggested key concepts and propositions. 
</p>
	<ul>
	<li><span style="color:#099910">Green: key concepts and relationships found in your response.</span>
	</li>
	<li><span style="color:#e8761a">Orange: key concepts and relationships that you may consider in your next version.</span>  
	</li>
	<li>Try to create a revised version by including missing key concepts and their relationships. 
	</li>
	</ul>
	<p>More...</p>
	<ul>
	<li>Roll over your mouse on a key concept to see associated supportive concepts.
	</li>
	<li>Click full KS tabs to see the expert’s entire knowledge map and yours in which a K-KS is embedded.  
	</li>
	</ul>
	</div>
</div>


<!--  Think More About modal -->
<div id="myModalThinkMore" class="modal" style="height:20%">
<input type="button" class="close" onClick="closePopUp('myModalThinkMore')" value="X" />
	<div id="modelcontainerThinkMore"> <!--  style="border:black 1px solid;"-->
	
	<h2 style="font-size:20px"><span style="color:#042559">Help:</span><span style="color:#4185f2"> Think More About!</span></h2>
	<p>This summarizes key concepts and their relations (links) that you have missed in your response.  The lists are the same with those you can find from the expert model.</p>
	<ul>
	 <li>See how those key concepts and relations are used in the expert model. 
	 </li>
	 <li>Try to use suggested key concepts and relations in your revised version.  
	 </li>
	</ul>
	</div>
</div>
<div id="expertfulldiv" style="display: none;">
	<input type="button" class="close" onClick="closeeditform(this)" value="X" />
		<svg id="svgButton" style="position:relative;" xmlns="http://www.w3.org/2000/svg"></svg>
		<script src="http://d3js.org/d3.v4.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>

</div>

<div id="studentfulldiv" style="display: none;">
	<input type="button" class="close" onClick="closeeditform(this)" value="X" />
		<svg id="studentsvgButton" xmlns="http://www.w3.org/2000/svg" style="position:relative;" ></svg>
		<script src="http://d3js.org/d3.v4.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>

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

</html>

