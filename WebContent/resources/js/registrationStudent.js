
 function validateValues(){
	  if(validateSchoolname()){
		 document.getElementById("schoolNameError").style.visibility = "hidden"; 
		 if(validateUsername()){
			 document.getElementById("userNameError").style.visibility = "hidden";
			 if(validateEmail()){ 
				 document.getElementById("validEmailError").style.visibility = "hidden";
				 if(matchemail()){
					 document.getElementById("emailError").style.visibility = "hidden";
					 if(validatePassword()){
						 document.getElementById("validPasswordError").style.visibility = "hidden";
						 if(matchpass()){
							 document.getElementById("passwordError").style.visibility = "hidden";
							 if(validateAnswer()){
								 document.getElementById("answerError").style.visibility = "hidden";
								 return true;
							 }
							 else{
								 document.getElementById("answerError").innerHTML = "*Should contain atleast 3 characters!";
								 document.getElementById("answerError").style.visibility = "visible";
							 }
						 }
						 else{
							 document.getElementById("passwordError").innerHTML = "*Passwords don't match!";
							 document.getElementById("passwordError").style.visibility = "visible";
						 }
					 }
					 else{
						 document.getElementById("validPasswordError").innerHTML = "*Should contain atleast 8 characters!";
						 document.getElementById("validPasswordError").style.visibility = "visible";
					 }
				 }
				 else{
					 document.getElementById("emailError").innerHTML = "*Emails don't match!";
					 document.getElementById("emailError").style.visibility = "visible";
				 }
			 }
			 else{
				 document.getElementById("validEmailError").innerHTML = "*Not a valid Email!";
				 document.getElementById("validEmailError").style.visibility = "visible";
			 }
		  }
		 else{
			 document.getElementById("userNameError").innerHTML = "*Should contain atleast 5 characters!";
			 document.getElementById("userNameError").style.visibility = "visible";
		 }
	 }
	 else{
		document.getElementById("schoolNameError").innerHTML = "*Should contain atleast 3 characters!";
		document.getElementById("schoolNameError").style.visibility = "visible";
	 } 
	 return false;
}
 function matchpass(){  
	 var firstpassword=document.getElementById("password").value;  
	 var secondpassword=document.getElementById("psw_repeat").value;  
	   
	 if(firstpassword==secondpassword){  
	 	return true;  
	 }  
	 else{
	 	return false;  
	 }  
} 
 
 function matchemail(){  
	 var firstemail=document.getElementById("email").value;  
	 var secondemail=document.getElementById("email_repeat").value;  
	 
	 if(!firstemail){
		 return true;
	 }
	   
	 if(firstemail==secondemail){  
	 return true;  
	 }  
	 else{  
	 return false;  
	 }  
} 
 
 function validateEmail(){
	 var email=document.getElementById("email").value;
	 if(!email){
		 return true;
	 }  
	 
	  if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))
	   {
	     return true;
	   }
	     return false;
 }

 function validatePassword(){
	 var password=document.getElementById("password").value;  
	  if (password.length >= 8)
	   {
	     return true;
	   }
	     return false;
 }
 
 function validateUsername(){
	 var uname=document.getElementById("userName").value;  
	  if (uname.length >= 5)
	   {
	     return true;
	   }
	     return false;
 }
 
 function validateSchoolname(){
	 var sName=document.getElementById("schoolName").value;  
	  if (sName.length >= 3)
	   {
	     return true;
	   }
	     return false;
 }
 
 function validateAnswer(){
	 var ans=document.getElementById("answer").value;  
	  if (ans.length >= 3)
	   {
	     return true;
	   }
	     return false;
 }