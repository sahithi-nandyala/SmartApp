<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Display Graph</title>
    <script type='text/javascript' src="http://labratrevenge.com/d3-tip/javascripts/d3.tip.v0.6.3.js"> </script>
 <style>

.links line {
  stroke: #999;
  stroke-opacity: 0.6;
}

.nodes circle {
  stroke: black ;
  stroke-width: 0px;
}

</style>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-container">
    <div id="dummy"></div>
    <div id="controls" class="w3-bar w3-black">
	    <button class="w3-bar-item w3-button tablink w3-red" id="expert-button1" onclick="displayExpert(event)">EXPERT COMPLETE GRAPH</button>
	    <button class="w3-bar-item w3-button tablink" id="student-button1" onclick="displayStudent(event)">STUDENT COMPLETE GRAPH</button>
    	<button class="w3-bar-item w3-button tablink" id="expert-button2" onclick="displayExpertBase(event)">EXPERT-BASED COMPARISON</button>
    	<button class="w3-bar-item w3-button tablink" id="student-button2" onclick="displayStudentBase(event)">STUDENT-BASED COMPARISON</button>
    	<button class="w3-bar-item w3-button tablink" id="expert-button3" onclick="displayExpertSingle(event)">EXPERT ONE DISTANCE</button>
    	<button class="w3-bar-item w3-button tablink" id="student-button3" onclick="displayStudentSingle(event)">STUDENT ONE DISTANCE</button>
    </div>
 </body>
	<svg width="1000" height="1000"></svg>
	<script src="https://d3js.org/d3.v4.min.js"></script>
    <script type="text/javascript">
    
    document.getElementById("expert-button1").click();
	
function displayStudentSingle(evt){
    	
		var i, x, tablinks;
    	tablinks = document.getElementsByClassName("tablink");
    	for (i = 0; i < tablinks.length; i++) {
    	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
    	}
		evt.currentTarget.className += " w3-red";
	
    	var svg;
    	d3.select("svg").selectAll("*").remove();
    	svg= d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");
        
		var nodes_data=${modelname.singleStudentNodes} ;
		var links_data=${modelname.singleStudentLinks} ;
    

		var simulation = d3.forceSimulation()
		.nodes(nodes_data);
		var link_force =  d3.forceLink(links_data)
				.id(function(d) { return d.name; });
		simulation
		.force("charge_force", d3.forceManyBody().strength(chargeVal))
		.force("center_force", d3.forceCenter(width/2, height/2))
		.force("links",link_force.distance(100).strength(1));

		simulation.on("tick", tickActions );

		var node = svg.append("g")
		.selectAll("circle")
		.data(nodes_data)
		.enter()
		.append("circle")
		.attr("r", circleRadius)
		.attr("fill", circleColour);

		var textElements = svg.append('g')
		.selectAll('text')
		.data(nodes_data)
		.enter().append('text')
		.text(node => node.name)
		.attr('font-size', textFont)
		.attr('fill', textColor)
		.attr('dx', 20)
		.attr('dy', 4);  

		var link = svg.append("g")
		.attr("class", "links")
		.selectAll("line")
		.data(links_data)
		.enter().append("line")
		.attr("stroke-width", strokeWidth)
		.style("stroke", linkColour);        


		var drag_handler = d3.drag()
		.on("start", drag_start)
		.on("drag", drag_drag)
		.on("end", drag_end);   

		drag_handler(node)

		function circleColour(d){
			if(d.type =="G"){
			return "darkGreen";
			} 
			else if(d.type =="Y"){
			return "darkGreen";
			} 
			else if(d.type=="R"){
			return "darkRed";
			}
			else if(d.type=="K"){
				return "darkRed";
			}
			else {
			return "lightGray";
			}
		}

		function circleRadius(d){
			if(d.type =="N"){
			return 5;
			} 
			else if(d.type=="K"){
				return 7;
			}
			else {
			return 10;
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
			return "black";
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
			.attr("cx", function(d) { return d.x; })
			.attr("cy", function(d) { return d.y; });

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
    
    
    function displayExpertSingle(evt){
    	
		var i, x, tablinks;
    	tablinks = document.getElementsByClassName("tablink");
    	for (i = 0; i < tablinks.length; i++) {
    	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
    	}
		evt.currentTarget.className += " w3-red";
	
    	var svg;
    	d3.select("svg").selectAll("*").remove();
    	svg= d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");
        
		var nodes_data=${modelname.singleExpertNodes} ;
		var links_data=${modelname.singleExpertLinks} ;
    

		var simulation = d3.forceSimulation()
		.nodes(nodes_data);
		var link_force =  d3.forceLink(links_data)
		.id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

var node = svg.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);

var textElements = svg.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = svg.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);        


var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node)

function circleColour(d){
if(d.type =="G"){
return "darkGreen";
} 
else if(d.type =="Y"){
return "darkGreen";
} 
else if(d.type=="R"){
return "darkRed";
}
else if(d.type=="K"){
	return "darkRed";
}
else {
return "lightGray";
}
}

function circleRadius(d){
if(d.type =="N"){
return 5;
}
else if(d.type=="K"){
	return 7;
} else {
return 10;
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
return "black";
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
.attr("cx", function(d) { return d.x; })
.attr("cy", function(d) { return d.y; });

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
    

    function displayExpert(evt){
    	
    		var i, x, tablinks;
	    	tablinks = document.getElementsByClassName("tablink");
	    	for (i = 0; i < tablinks.length; i++) {
	    	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
	    	}
    		evt.currentTarget.className += " w3-red";
    	
	    	var svg;
	    	d3.select("svg").selectAll("*").remove();
	    	svg= d3.select("svg"),
	        width = +svg.attr("width"),
	        height = +svg.attr("height");
	        
	    var nodes_data=${modelname.nodes} ;
	    var links_data=${modelname.links} ;
	    
	
	    var simulation = d3.forceSimulation()
        .nodes(nodes_data);
var link_force =  d3.forceLink(links_data)
            .id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

var node = svg.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);

var textElements = svg.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = svg.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);        
    

var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node)

function circleColour(d){
if(d.type =="G"){
return "darkGreen";
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
return 10;
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
return "black";
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
.attr("cx", function(d) { return d.x; })
.attr("cy", function(d) { return d.y; });

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
    
 
 function displayStudent(evt){

 	
		var i, x, tablinks;
 	tablinks = document.getElementsByClassName("tablink");
 	for (i = 0; i < tablinks.length; i++) {
 	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
 	}
		evt.currentTarget.className += " w3-red";
	
 	var svg;
 	d3.select("svg").selectAll("*").remove();
 	svg= d3.select("svg"),
     width = +svg.attr("width"),
     height = +svg.attr("height");
     
 var nodes_data=${modelname.studentNodes} ;
 var links_data=${modelname.studentLinks} ;
 

 var simulation = d3.forceSimulation()
 .nodes(nodes_data);
var link_force =  d3.forceLink(links_data)
     .id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

var node = svg.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);

var textElements = svg.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = svg.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);        


var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node)

function circleColour(d){
if(d.type =="G"){
return "darkGreen";
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
return 10;
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
return "black";
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
.attr("cx", function(d) { return d.x; })
.attr("cy", function(d) { return d.y; });

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
    
    
    function displayStudentBase(evt){
    	
	    	var i, x, tablinks;
	    	tablinks = document.getElementsByClassName("tablink");
	    	for (i = 0; i < tablinks.length; i++) {
	    	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
	    	}
		evt.currentTarget.className += " w3-red";
    	
	    	var svg;
	    	
	    	d3.select("svg").selectAll("*").remove();
	    	
	    	svg= d3.select("svg"),
	        width = +svg.attr("width"),
	        height = +svg.attr("height");
	        
	    var nodes_data=${modelname.studentBaseNodes} ;
	    var links_data=${modelname.studentBaseLinks} ;
	    
	
	    var simulation = d3.forceSimulation()
        .nodes(nodes_data);
var link_force =  d3.forceLink(links_data)
            .id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

var node = svg.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);

var textElements = svg.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = svg.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);        
    

var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node)

function circleColour(d){
if(d.type =="G"){
return "darkGreen";
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
return 10;
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
return "black";
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
.attr("cx", function(d) { return d.x; })
.attr("cy", function(d) { return d.y; });

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
    
    function displayExpertBase(evt){
    	
    	var i, x, tablinks;
    	tablinks = document.getElementsByClassName("tablink");
    	for (i = 0; i < tablinks.length; i++) {
    	    tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
    	}
	evt.currentTarget.className += " w3-red";
	
    	var svg;
    	
    	d3.select("svg").selectAll("*").remove();
    	
    	svg= d3.select("svg"),
        width = +svg.attr("width"),
        height = +svg.attr("height");
        
    var nodes_data=${modelname.expertBaseNodes} ;
    var links_data=${modelname.expertBaseLinks} ;
    

    var simulation = d3.forceSimulation()
    .nodes(nodes_data);
var link_force =  d3.forceLink(links_data)
        .id(function(d) { return d.name; });
simulation
.force("charge_force", d3.forceManyBody().strength(chargeVal))
.force("center_force", d3.forceCenter(width/2, height/2))
.force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

var node = svg.append("g")
.selectAll("circle")
.data(nodes_data)
.enter()
.append("circle")
.attr("r", circleRadius)
.attr("fill", circleColour);

var textElements = svg.append('g')
.selectAll('text')
.data(nodes_data)
.enter().append('text')
.text(node => node.name)
.attr('font-size', textFont)
.attr('fill', textColor)
.attr('dx', 20)
.attr('dy', 4);  

var link = svg.append("g")
.attr("class", "links")
.selectAll("line")
.data(links_data)
.enter().append("line")
.attr("stroke-width", strokeWidth)
.style("stroke", linkColour);        


var drag_handler = d3.drag()
.on("start", drag_start)
.on("drag", drag_drag)
.on("end", drag_end);   

drag_handler(node)

function circleColour(d){
if(d.type =="G"){
return "darkGreen";
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
return 10;
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
return "black";
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
.attr("cx", function(d) { return d.x; })
.attr("cy", function(d) { return d.y; });

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
    </script>
</html>
