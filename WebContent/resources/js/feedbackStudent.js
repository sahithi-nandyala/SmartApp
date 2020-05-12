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



window.onload = function() {
	document.getElementById("conceptbtn").click();
	displayExpertBase();
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
    var width = 1300;
    var height = 700;
    var nodes_data=${modelname.studentNodes};
 	var links_data=${modelname.studentLinks}; 


	var simulation = d3.forceSimulation()
	 .nodes(nodes_data);
	var link_force =  d3.forceLink(links_data)
	     .id(function(d) { return d.name; });
	simulation
	.force("charge_force", d3.forceManyBody().strength(chargeVal))
	.force("center_force", d3.forceCenter(width/2, height/2))
	.force("links",link_force.distance(90).strength(1));
	
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


function displayExpert(){
	d3.select("#svgButton").selectAll("*").remove();
	var svg= d3.select("#svgButton");
    var width = 1300;
    var height = 700;
    
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


function displayExpertBase(){

	var svg;
	
	var width = document.getElementById("container").offsetWidth;
	var height = document.getElementById("container").offsetHeight;
	svg= d3.select("svg");
	
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
.attr('dx', 15)
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
		return "darkGreen";
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
