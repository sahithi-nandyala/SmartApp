
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>  
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/resources/css/home.css" rel="stylesheet">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<title>Home</title>
</head>

<body>
<div class="wrapper">
<div class="logocontainer">
<a href="/SMART"><img src="/SMART/resources/img/SMART_Logo.png" alt="logo"></a>
  <div class="topnav-right">
	<a href="/SMART" class="class1">Home</a>
	<a href="#" class="class4">Help</a>
	<a href="login" class="class6">Login</a>
	<a href="registerAs" class="class7">Sign Up</a>
  </div>
</div>
<div class="whatissmart">
<input class="read-more-state" id="post-1" type="checkbox">
<div class="read-more-wrap">
<img src="/SMART/resources/img/bannerSMART.png" alt="intelligence" class="banner1">
    <br>
<p class="read-more-target">SMART stands for Student Mental Model Analyzer for Research and Teaching. SMART uses students’ textual explanations (e.g. a short essay) to assess and represent the students’ understandings of a problem situation. SMART can be applied to various disciplines for diverse learning problems, such as complex problems in STEM, science text comprehension, and general reading comprehension.</p>
  <p class="read-more-target"><br>SMART is an innovation learning technology to reinforce instruction. For example, in the context of flipped classrooms, SMART can deliver online learning materials and enable students to prepare proper learner understandings of the topic in preparation of active learning in classrooms. For most classrooms, SMART can help teachers to adapt problem-centered pedagogies to individual students by informing them of individual students’ learning progressions toward building an appropriate knowledge structure.</p>
  </div>
 <label for="post-1" class="read-more-trigger"></label>
</div>

<p></p>
<div class="howitworks"><div class="heading1h">How does SMART work?</div>
<img src="/SMART/resources/img/SMARTChart.png" alt="smartchart" class="banner2">
<p class="heading1i">Teachers and Researchers</p>
<p><strong>Step 1: Make your classrooms</strong></p>
<p>Create your SMART class in a minute. It can either be a virtual class or one that is paired with your face-to-face classroom.</p>
<br>
<p><strong>Step 2: Create assignments
  </strong></p>
<p>You can make a series of assignments per project with no limitation. The format of the problem is entirely up to you. You can have videos, images, and hyperlinks in your problem descriptions.</p>
<br>
<p><strong>Step 3: Assessment and feedback
  </strong></p>
<p>SMART automatically analyzes students&rsquo; progressions. You can monitor both class-level and individual-level changes. You can provide personalized learning support on top of automatic formative feedback with the SMART system.</p>
<p>&nbsp;</p>
<p class="heading1i">Students</p>
<p><strong>Step 1: Add your classes</strong></p>
<p>Create your SMART account first and add the &ldquo;class ID&rdquo; your teacher shares with you. That&rsquo;s all. You will be connected to the class. </p>
<br>
<p><strong>Step 2: Create your responses</strong></p>
<p>After joining a class, you will have the assignments on your dashboard. Follow the assignment&rsquo;s instructions and create a written response to a given problem. SMART will automatically analyze your levels of understandings and provide you with adaptive feedback. You can create multiple responses over time to improve your deep understanding of a problem situation.</p>
<p>&nbsp;</p>
<br>
<br>
<br>
<div class="heading1h">SMART Approach</div>
<p>A students&rsquo; natural language responses provide an individual&rsquo;s verbalized descriptions about a problem situation. SMART automatically analyzes the students&rsquo; written responses to elicit visually rendered concept maps. Concept maps are assumed to represent the students&rsquo; internal knowledge structures (i.e., mental models).</p>
<img src="/SMART/resources/img/conceptchart.png" alt="intelligence" class="banner2">
<p>&nbsp;</p>
<br>
<br>
<br>
<div class="heading1h"><a name="AboutUs"></a>About Us</div>
<br><br>
<p class="heading2h"><img src="/SMART/resources/img/Kim.png" alt="Dr. Kim" align="right">Founder and PI: Dr. Min Kyu Kim</p>
<p> Assistant professor of Learning Sciences at Georgia State University</p><br>
<p><strong><em>“Most students can master learning goals if they are provided with appropriate adaptive support.” </em></strong></p><br>
<p>Based  on this belief, Dr. Kim’s research has pursued the advancement of learning  technologies that accurately assess students’ knowledge levels and relevant  characteristics and immediately provide feedback to learners. SMART’s development  built on the methodologies and tools developed by his prior studies.</p>
<p><br>
  </p>
<p><br>
  <br>
</p>
<p><img src="/SMART/resources/img/colorbar.png" alt="colorbar"></p><br>
<p class="heading2h"><img src="/SMART/resources/img/Swathi.jpg" alt="Swathi" style="width:155px;height:200px; box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.2), 0 10px 20px 0 rgba(0, 0, 0, 0.19);" align="right">Lead Developer: Swathi Kiran Reddy Pallamreddy, M.S.</p>
<p>Master of Science in Computer Science<br>
  Computer Science, Georgia State University, May 2018</p><br>
<p><strong>Major Contributions:</strong></p>
<ul>
  <li> System architecting and database design</li>
  <li> Natural language processing, social network analysis, data visualization</li>
  <li> Web programming </li>
</ul>
<p></p>
<p><br>
  <br>
</p>
<p><br>
</p>
<p><img src="/SMART/resources/img/colorbar.png" alt="colorbar"></p><br>
<p class="heading2h"><img src="/SMART/resources/img/Sheela.png" alt="Sheela" align="right">Developer: Shyama Bhuvanendran Sheela </p>
<p>Master’s student of Computer Science at Georgia State University</p><br>
<p><strong>Major Contributions:</strong></p>
<ul>
  <li> Natural language processing, social network analysis, data visualization</li>
  <li> Web programming </li>
  </ul>
<p><br>
  </p>
<p><br>
  <br>
</p>
<p><img src="/SMART/resources/img/colorbar.png" alt="colorbar"></p><br>
<p class="heading2h"><img src="/SMART/resources/img/Hicks.png" alt="Hicks" align="right">Web/Graphic Designer: Timothy Alex Hicks</p>
<p>Doctoral student of Instructional Design and Technology at Georgia State University </p><br>
<p><strong>Major Contributions:</strong></p>
<ul>
  <li> Web design</li>
  <li> Web programming <br>
  </li>
</ul>
<br>
<br><br><br>
<div class="heading1h">Publications</div>
<p>Our research informs all of the details of SMART technology. We keep up our studies to validate and improve SMART features by expanding our SMART applications in various contexts, from K12 through higher education, with diverse subject disciplines, such as STEM and literacy education. SMART is an open system! We are seeking research collaborators who are interested in using SMART to create interdisciplinary research projects.</p><br>
<p>Kim, M., Zouaq, A., &amp; Kim, S. (2016). Automatic detection of expert models: The exploration of expert modeling methods applicable to technology-based assessment and instruction. <em>Computers &amp; Education</em>, <em>101</em>, 55-69. <a href="/SMART/resources/files/1_C&E_2016.pdf">Full Text (PDF</a>)</p><br>
<p>Kim, M. (2015). Models of learning progress in solving complex problems: expertise development in teaching and learning. <em>Contemporary Educational Psychology</em>, <em>42</em>, 1-16. <a href="/SMART/resources/files/2_CEDPSYCH_2015.pdf">Full Text (PDF</a>)</p>
<br>
<p>Kim, M. (2013). Concept map engineering: Methods and tools based on the semantic relation approach. <em>Educational Technology Research and Development</em>, <em>61</em>(6), 951-978. <a href="/SMART/resources/files/3_ETRD_2013.pdf">Full Text (PDF</a>)</p>
<br>
<p>Kim, M. (2012). Theoretically grounded guidelines for assessing learning progress: Cognitive changes in ill-structured complex problem-solving contexts. <em>Educational Technology Research and Development</em>, <em>60</em>(4), 601-622. <a href="/SMART/resources/files/4_ETRD_2012.pdf">Full Text (PDF</a>)</p>
<br>
<p>Kim, M. (2012). Cross-validation study on methods and technologies to assess mental models in a complex problem-solving situation. <em>Computers in Human Behavior</em>, <em>28</em>(2), 703-717. <a href="/SMART/resources/files/5_CHB_2012.pdf">Full Text (PDF</a>)<br>
</p>
<p>&nbsp;</p>

</div>
</div>

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