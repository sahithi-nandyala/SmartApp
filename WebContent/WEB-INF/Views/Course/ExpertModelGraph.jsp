<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/button.css" />
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">
<style>
.studentTable  {
    border-bottom: 5px solid #00738c;
}
.studentTable th{
    font-size: 18px;
    font-family: Lato;
    width: 50%;
   }
.studentTable td{
     font-family: Lato;
    width: 50%;
   }
.labelcss{
    font-family: Lato;
    font-size: 18px;
}
.heading1{
	font-size: 25px;
    font-weight: bold;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    color:#000000;
    text-align: left;
    margin-top: 10px;
    margin-left: 25px;
}


.table {
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    border-collapse: collapse;
    /* width: 100%; */
    top-margin: 100px;
    margin-left: 25px;
    border-radius: 6px;
}

.table td, .table th {
    /* border: 1px solid #ddd; */
    padding: 2px;
    width: 50%;
  
}

/* .table tr:nth-child(even){background-color: #f2f2f2;} */

/* .table tr:hover {background-color: #ddd;} */

.table th {
    padding-top: 10px;
    padding-bottom: 10px;
    text-align: center;
    color: #000000;
}


.graphdiv {
    width: 75%;
    height: 800px;
    border: 2px solid #00738c;
    border-radius: 6px;
    padding: 1px;
    /*  margin: 0 1%;*/
    margin-left: 30px;
    margin-top: 20px;
    margin-bottom: 30px;
    /*margin: 25px 20px 20px 20px;*/
    float: left;
    position:relative;
    background-color:white;
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
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    font-size: 15px;
}

/* Change background color of buttons on hover */
.tab button:hover {
    background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
    background-color: #ccc;
}


.buttonsdiv{
	position: relative;
    margin-left: 25px;
    margin-top:20px;
}

.button {
    border: none;
    color: black;
    padding: 10px 10px 10px 10px;
    text-align: center;
    text-decoration: none;
    font-family: Lato, "Trebuchet MS", Arial, Helvetica, sans-serif;
    display: inline-block;
    font-size: 15px;
    margin: 4px 2px;
    font-weight: normal;
    cursor: pointer;
  	-webkit-border-radius: 6;
  	-moz-border-radius: 6;
  	border-radius: 6px;
  	background-color: #00738c; 
    border: 1px solid #00738c;
}

.button:hover {
    background-color: #81b0b2;
}

.studentTable td, .studentTable th{
 border: 1px solid #ddd;
 padding: 10px;
 }
 
.noofwords th{
	width: 35%;
}
.resultTable th{
	width: 35%;
}

.conceptable{
	width: 50%;
	margin-bottom: 40px;
	background-color:white;
}
.conceptable th{
    width: 50%;
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

.centered{
    margin-left: auto;
    margin-right: auto;
}
.labelcss{
    font-family: Lato;
    font-size: 18px;
}

#descriptionEdit {
background-color:#f7f799;
   width: 78%;
    border: 2px solid #00738c;
    border-radius: 6px;
    word-spacing:10px;
    background-color:white;
    margin: 10px; 
    font-size:13px;
    padding:10px; 
    overflow:auto; 
    cursor:not-allowed;
}

#descriptionEdit span
{
background-color:#f7f799;
}
</style>
<script>

window.onload = function() {
	displayExpert();
	<% int researcherid= (int)session.getAttribute("researcherid");
	if(researcherid==-1) { %>

		document.getElementById("infodownload").style.display="none";

		<% } %>
};


function displayExpert(){
	document.getElementById("displayExpertButton").style.backgroundColor = "black";
	document.getElementById("displayExpertButton").style.color="white";
	document.getElementById("displayExpertBaseButton").style.backgroundColor = "#D5D7D9";
	document.getElementById("displayExpertBaseButton").style.color="black";
	
	d3.select("svg").selectAll("*").remove();
	
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
		return -150;
	} else {
		return -150;
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

	
	document.getElementById("displayExpertBaseButton").style.backgroundColor = "black";
	document.getElementById("displayExpertBaseButton").style.color="white";
	document.getElementById("displayExpertButton").style.backgroundColor = "#D5D7D9";
	document.getElementById("displayExpertButton").style.color="black";
	
	d3.select("svg").selectAll("*").remove();
	
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
    
	var nodes_data=${modelname.expertBaseNodes} ;
	var links_data=${modelname.expertBaseLinks} ;


	var simulation = d3.forceSimulation()
		.nodes(nodes_data);
	var link_force =  d3.forceLink(links_data)
    	.id(function(d) { return d.name; });


    
simulation
	.force("charge_force", d3.forceManyBody().strength(chargeVal))
	.force("center_force", d3.forceCenter(width/2, height/2))
	.force("links",link_force.distance(60));

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
		return "#566573";
	} 
	else if(d.type =="Y"){
		return "darkGreen";
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
		return "#E0B506";
	}
	else if(d.type == "R"){
		return "darkRed";
	}
	else if(d.type == "Y"){
		return "purple";
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

function downloadexpertmodel()
{
	$.ajax({
	    type : "POST",
	    url : "downloadexpert",
	    data : {
	        expid: ${modelname.expertID}
	    },
	    success : function(response) {
	       alert("download complete");
	    },
	    error : function(e) {
	       alert("download error");
	    }
	}); 
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Expert Model</title>
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
<br>
<p class="heading2a">View the Reference (Expert Model)</p>
<br>
<table class="table" style="width:50%; padding:10px;">
<tr>
	<td><label for="assgnmntId" class="labelcss"><b>Assignment ID:</b>&nbsp;<%=session.getAttribute("assignid")%> </label></td></tr>
<tr>	 <td><label for="title" class="labelcss"><b>Assignment Title:</b>&nbsp;Test</label></td>
</tr>
</table>

<table class="table" style="width:50%;padding:10px;">
<tr>
<td><label for="assgnmntId" class="labelcss"><b>Reference Title (Optional):</b>&nbsp;${modelname.title} </label></td>
</tr>
</table>
<table class="table" style="width:95%;padding:10px;">
<tr>
	<%-- <td>&nbsp;&nbsp;<label for="classId" class="labelcss"><b>Class ID&nbsp;:</b>&nbsp;${model.classDetails.classId}</label></td> --%>
	 <td><label for="className" class="labelcss"><b>Textual Explanation:</b>&nbsp;</label></td></tr>
	 <tr><td><div name="description" id="descriptionEdit">${modelname.text}</div></td>
</tr>
</table>
<br />
<table class="table" style="width:50%;padding:10px;">
<tr><td>&nbsp;&nbsp;<label for="className" class="labelcss"><b>Expert Concept Map:</b>&nbsp;</label></td></tr>
</table>
<div class="buttonsdiv">
<table>
 <tr>
 <td>
 	<input type="button" id="displayExpertButton" class="button" value="Expert Concept Map (Full)" onClick="displayExpert();"/>
 </td>
 <td>
 	<input type="button" id="displayExpertBaseButton" class="button" value="Expert Concept Map (Key)" onclick="displayExpertBase();"/>
 </td>
 </table>
 </div>
<div id="container" class="graphdiv">
	<svg xmlns="http://www.w3.org/2000/svg" style="position:relative; max-width:100%;"></svg>
	<script src="http://d3js.org/d3.v4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>
</div>
<table id="infodownload" class="table" style="width:40%; padding:10px;" style="display:none;">
	<tr>
		<td><label for="assgnmntId" class="labelcss"><b>Information of the Expert Model: </b></label></td>
		<td><input type="button" class="downloadbutton" value="Download All Metrics Information" onClick="location.href='downloadexpert.CSV/${modelname.expertID}'" id="downloadResultsButton"/></td>
	</tr>
</table>
<br />
<br />
<table class="table studentTable noofwords" style="width:60%">
	<tr>
		<th>Total Number of Words: </th>
		<td> ${modelname.noOfWords} </td>
	</tr>
</table>
<table class="table studentTable resultTable" style="width:60%">
	<tr><th> Total Number of Concepts: </th>
		<td> ${modelname.noOfConcepts} </td></tr>
	<tr><th> Total Number of Relations: </th>
		<td> ${modelname.noOfRelations} </td>
	</tr>
</table>
<table class="table studentTable conceptable" style="width: 60%; border-radius: 6px;border: 2px solid #97a675;">
	<tr>
		<th> Key Concepts (${modelname.noOfKeyConcepts} )</th>
		<th> Key Relations (${modelname.noOfKeyLinks})</th>
	</tr>
	<tr valign="top">
	<td><c:forEach var="element" items="${modelname.keyConcepts}">
	 &nbsp;&nbsp;${element}<br /></c:forEach>
	 </td>
	 <td><c:forEach var="element" items="${modelname.keyRelations}">
	 	&nbsp;&nbsp;
	 	<%-- <c:forEach var="rel" items="${element.key}">
	 	${rel} 
	 	</c:forEach> --%>
	 	${element[0]} - ${element[1]}<br />
	 </c:forEach>
	 </td>
	</tr>
</table>
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