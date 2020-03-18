<?php
$name = $_POST['name'];
$email = $_POST['email'];
$subject = $_POST['subject'];
$request = $_POST['request'];
$description = $_POST['description'];
$formcontent=" From: $email \n Email: $email \n Subject: $subject \n Request: $request \n Description: $description";
$recipient = "thicks@student.gsu.edu";
$subject = "Contact Form";
$mailheader = "From: SMART \r\n";
mail($recipient, $subject, $formcontent, $mailheader) or die("Error!");
echo "Thank You! We will review your message soon." . " -" . "<a href='http://smart-dev.cehd.gsu.edu:8080/SMART/' style='text-decoration:none;color:#ff0099;'> Return Home</a>";
header( "refresh:2;url=http://smart-dev.cehd.gsu.edu:8080/SMART/" );
?>