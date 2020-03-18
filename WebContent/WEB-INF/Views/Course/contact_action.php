<?php
    $name = $_POST['email'];
	$email = $_POST['email'];
    $subject = $_POST['subject'];
	$request = $_POST['request'];
	$description = $_POST['description'];
	$message = $_POST['description']
   
    $from = 'From: SMART'; 
    $to = 'thicks@student.gsu.edu'; 
    $subject = 'Contact Form Message From SMART';
	
	$body = "From: $email\n E-Mail: $email\n Subject: $subject\n Request: $request\n Message: $description\n";
	
if ($_POST['submit']) {
    if ($email != '' && $description != '') {
        if ($human == '72') {				 
            if (mail ($to, $subject, $body, $from)) { 
	        echo '<p>Your message has been sent!</p>';
	    } else { 
	        echo '<p>Something went wrong, go back and try again!</p>'; 
	    } 
	} else if ($_POST['submit'] && $human != '72') {
	    echo '<p>You answered the anti-spam question incorrectly!</p>';
	}
    } else {
        echo '<p>You need to fill in all required fields!!</p>';
    }
}

header("Refresh: 5; url=/SMART");
?>






