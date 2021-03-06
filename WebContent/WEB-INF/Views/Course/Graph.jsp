<!DOCTYPE html>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<html>
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
<body>
The Graph will be displayed in this page. 
<form:form id="displayStudentModelGraph" modelAttribute="graphVizDetails"
	action="displayStudentModelGraph" method="post">
	<table>
		
		<tr>
			<td></td>
			<td><input type="submit" value="Display Student Model Graph" /></td>
		</tr>
	</table>
</form:form>
</body>
<svg width="1500" height="2000"></svg>
<script src="https://d3js.org/d3.v4.min.js"></script>
<script>
//create somewhere to put the force directed graph
//function handleClick(){
var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");
    
//characters
var nodes_data;
nodes_data = ${courseDetails.nodes} ;
var links_data;
links_data = ${courseDetails.links} ;


//set up the simulation 
var simulation = d3.forceSimulation()
                    .nodes(nodes_data);
                    
                    
//add forces
//we're going to add a charge to each node 
//also going to add a centering force
//and a link force
var link_force =  d3.forceLink(links_data)
                        .id(function(d) { return d.name; });
simulation
    .force("charge_force", d3.forceManyBody().strength(chargeVal))
    .force("center_force", d3.forceCenter(width/2, height/2))
    .force("links",link_force.distance(100).strength(1));

simulation.on("tick", tickActions );

//draw circles for the links 
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


//draw lines for the links 
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
    
//same as u sing .call on the node variable as in https://bl.ocks.org/mbostock/4062045 
drag_handler(node)



/** Functions **/

//Function to choose what color circle we have
//Let's return blue for males and red for females
function circleColour(d){
    if(d.type =="G"){
        return "darkGreen";
    } 
    else if(d.type =="Y"){
        return "darkBlue";
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
//Function to choose the line colour and thickness 
//If the link type is "A" return green 
//If the link type is "E" return red 
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


//drag handler
//d is the node 
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
    //update circle positions each tick of the simulation 
    node
        .attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; });
    
    textElements
    		.attr("x", node => node.x)
    		.attr("y", node => node.y);
        
    //update link positions 
    //simply tells one end of the line to follow one node around
    //and the other end of the line to follow the other node around
    link
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });
      } 
</script>
</html>
