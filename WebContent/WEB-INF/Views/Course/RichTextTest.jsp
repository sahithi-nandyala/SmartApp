<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>
<script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form id="displayStudentGraph" modelAttribute="details" action="richtextdemo2" method="post">
	<textarea rows="4" cols="50" name="description" id="description">
	At w3schools.com you will learn how to make a website. We offer free tutorials in all web development technologies. 
	</textarea>
	<input type="submit" value="Submit" />
</form:form>
</body>
</html>