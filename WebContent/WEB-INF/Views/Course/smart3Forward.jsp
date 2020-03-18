<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>  
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>Home</title>
</head>

<body>

<%

String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length());


String site = new String(baseURL + "/SMART3");
response.setStatus(response.SC_MOVED_TEMPORARILY);
response.setHeader("Location", site); 

%>


<!--  jsp:forward page="../SMART3" /-->

</body>
</html>