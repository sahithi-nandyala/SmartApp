if(sessionStorage.getItem('FirstName')!=null)
{
var userid=sessionStorage.getItem('FirstName');

}

var modal = document.getElementById('myModal');

var close = document.getElementsByClassName("close");
for(i=0; i<close.length; i++) {
	close[i].onClick = function() {
		document.getElementById('myModal').style.display = "none";
	
	}
   }
window.onclick = function(event) {
 if (event.target == modal) {
	 document.getElementById('myModal').style.display = "none";
 }
}

function closebox()
{
	document.getElementById('myModal').style.display = "none";
}
function displayProjectdetails(x)
{
	if(document.getElementById(x).style.display=='none')
	document.getElementById(x).style.display = "block";	
	else
		document.getElementById(x).style.display = "none";
 
}

function displayprojectform()
{
	document.getElementById('modelcontainer').innerHTML=document.getElementById('projectformdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	//document.getElementById("project").style.display="block";
}

function displayassignform(projectid)
{
	document.getElementById('projectId').value=projectid;
	document.getElementById('modelcontainer').innerHTML=document.getElementById('assignmentdiv').innerHTML;
	document.getElementById('myModal').style.display = "block";
	
}

function closeeditform(e)
{
	e.parentNode.style.display="none";
}
function editproject(pid)
{
	/*var cboxes = document.getElementsByName('removeprojects');
	var len = cboxes.length;
	 var choices=[];
	 var g=0;
	for (var i=0; i<len; i++) {
    	if(cboxes[i].checked)
    		{
    			g++;
    			choices[g]=cboxes[i].value;
    			
    		}
    }
	
	if(g>1)
		alert("Select only one project to edit");
	else if(g==1)
		{
		var id=choices[1];
	document.getElementById(id+'edit').style.display="block";
		}
	else
		alert("Select a project to edit");*/
	
//	document.getElementById(pid+'edit').style.display="block";
	
	document.getElementById('modelcontainer').innerHTML=document.getElementById(pid+'edit').innerHTML;
	document.getElementById('myModal').style.display = "block";
	
}
function getremovingprojects()
{
	 var cboxes = document.getElementsByName('removeprojects');
	    var len = cboxes.length;
	    var checkedprojects="";
	    var choices=[];
	    var g=0;
	    for (var i=0; i<len; i++) {
	    	if(cboxes[i].checked)
	    		{
	    		checkedprojects+=cboxes[i].value +" ";
	    		choices[g]=cboxes[i].value;
	    		g++;
	    		}	        
	    }
	    if(g>1)
			alert("Select one project to delete");
		else if(g==1)
		{
	    var decision=confirm("Are you sure you want to remove project "+checkedprojects+" ?  If you delete it, you will lose all the information including associated assignments and student records. Do you still want to delete it?");
	    if (decision==false)
	    	{
	    	
	    	}
	    else
	    	{
		    	$.ajax({
		    	    type : "POST",
		    	    url : "removeprojects",
		    	    data : {
		    	        myArray: choices //notice that "myArray" matches the value for @RequestParam
		    	                   //on the Java side
		    	    },
		    	    success : function(response) {
		    	    	location.reload();
		    	    },
		    	    error : function(e) {
		    	    	location.reload();
		    	    }
		    	}); 
		    	
		    	
	    	}
	    
		}
}

function deleteclass(id)
{
	
	 /*var cboxes = document.getElementsByName('classdelete');
	    var len = cboxes.length;
	    var choices=[];
	    var g=0;
	    choices[0]=id;
	    for (var i=0; i<len; i++) {
	    	if(cboxes[i].checked)
	    		{
	    		g++;
	    		choices[g]=cboxes[i].value;
	    		
	    		}	        
	    		}*/
	    var decision=confirm("Are you sure you want to remove classes from the assignment ?");
	    if(decision==true)
	    	{
	    	$.ajax({
	    	    type : "POST",
	    	    async:false,
	    	    url : "removeclassesfromassignment",
	    	    data : {
	    	        classid: id //notice that "myArray" matches the value for @RequestParam
	    	                   //on the Java side
	    	    },
	    	    success : function(response) {
	    	    	location.reload();
	    	    },
	    	    error : function(e) {
	    	    	location.reload();
	    	    }
	    	}); 
	    	location.reload();
	    	}
	    
}
function displayeditform()
{
	document.getElementById('myModal').style.display = "block";
}