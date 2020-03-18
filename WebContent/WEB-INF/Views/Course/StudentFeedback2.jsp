<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Display Graph</title>
 <style>

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
  padding: 12px;
  background: #000;
  color: #fff;
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

</style>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-container">
    <div id="dummy"></div>
    <div id="controls" class="w3-bar w3-black">
	    <button class="w3-bar-item w3-button tablink w3-red" id="expert-button1" onclick="displayExpert(event)">EXPERT COMPLETE GRAPH</button>
	    <button class="w3-bar-item w3-button tablink" id="student-button1" onclick="displayStudent(event)">STUDENT COMPLETE GRAPH</button>
    	<button class="w3-bar-item w3-button tablink" id="expert-button2" onclick="displayExpertBase(event)">EXPERT-BASED COMPARISON</button>
    </div>
    <table border=1>
    <tr>
    <td>No Of concepts</td>
    <td>${modelname.noOfConcepts }</td>
    </tr>
    <tr>
    <td>No of Relations</td>
    <td>${modelname.noOfRelations }</td>
    </tr>
    <tr>
    <td>Average Degree</td>
    <td>${modelname.avgdegree }</td>
    </tr>
    <tr>
    <td>Mean Distance</td>
    <td>${modelname.meandistance }</td>
    </tr>
    <tr>
    <td>Density</td>
    <td>${modelname.density }</td>
    </tr>
    <tr>
    <td>Diameter</td>
    <td>${modelname.diameter }</td>
    </tr>
    <tr>
    <td>Recall KeyConcepts</td>
    <td>${modelname.recallkeyconcepts}</td>
    </tr>
    <tr>
    <td>Recall Key links</td>
    <td>${modelname.recallKeylinks}</td>
    </tr>
    <tr><td>Subgraph</td>
    <td>${modelname.subgraphs}</td></tr>

    
    </table>
 </body>
	<svg width="1000" height="1000"></svg>
	<script src="http://d3js.org/d3.v4.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3-tip/0.7.1/d3-tip.min.js"></script>
    <script type="text/javascript">
    
    document.getElementById("expert-button1").click();
	  
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
				return "black";
			} 
			else if(d.type =="Y"){
				return "black";
			} 
			else if(d.type=="R"){
				return "darkRed";
			}
			else if(d.type=="K"){
				return "black";
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
				return 12;
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
				return "black";
			} 
			else if(d.type =="Y"){
				return "black";
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
				return "black";
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
	    		    	
	    	svg= d3.select("svg")
	    	.attr("width", width)
	        .attr("height", height);
	    	
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
			.force("links",link_force.distance(130).strength(1));
	
		simulation.on("tick", tickActions );
	
		var node = svg.append("g")
			.selectAll("circle")
			.data(nodes_data)
			.enter()
			.append("circle")
			.attr("r", circleRadius)
			.attr("fill", circleColour)
			.on("mouseover", tip.show) 
		    .on("mouseout", tip.hide);
		
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
				return -5000;
			} else {
				return -5000;
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
				return "blue";
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
    </script>
</html>
