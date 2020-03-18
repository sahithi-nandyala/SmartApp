<?php
if(isset($_POST['email'])) {
     
    // CHANGE THE TWO LINES BELOW
    $email_to = "thicks@student.gsu.edu";
     
    $email_subject = "website html form submissions";
     
     
    function died($error) {
        // your error code can go here
        echo "We are very sorry, but there were error(s) found with the form you submitted. ";
        echo "These errors appear below.<br /><br />";
        echo $error."<br /><br />";
        echo "Please go back and fix these errors.<br /><br />";
        die();
    }
     
    // validation expected data exists
    if(!isset($_POST['email']) ||
        !isset($_POST['subject']) ||
        !isset($_POST['request']) ||
        !isset($_POST['description']) ||
        died('We are sorry, but there appears to be a problem with the form you submitted.');       
    }
     
    $email = $_POST['email']; // required
    $subject = $_POST['subject']; // required
    $request = $_POST['request']; // required
    $description = $_POST['description']; // required
     
    $error_message = "";
    $email_exp = '/^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$/';
  if(!preg_match($email_exp,$email_from)) {
    $error_message .= 'The Email Address you entered does not appear to be valid.<br />';
  }

  if(strlen($comments) < 2) {
    $error_message .= 'The Comments you entered do not appear to be valid.<br />';
  }
  if(strlen($error_message) > 0) {
    died($error_message);
  }
    $email_message = "Form details below.\n\n";
     
    function clean_string($string) {
      $bad = array("content-type","bcc:","to:","cc:","href");
      return str_replace($bad,"",$string);
    }

    $email_message .= "Email: ".clean_string($email_from)."\n";    
    $email_message .= "Subject: ".clean_string($subject)."\n";
    $email_message .= "Request: ".clean_string($request)."\n";
    $email_message .= "Description: ".clean_string($description)."\n";
     
     
// create email headers
$headers = 'From: '.$email_from."\r\n".
'Reply-To: '.$email_from."\r\n" .
'X-Mailer: PHP/' . phpversion();
@mail($email_to, $email_subject, $email_message, $headers);  
?>
 
<!-- place your own success html below -->
<p> 
Thank you for contacting us. We will be in touch with you very soon.
</p> 
<p><a href="/SMART">go back</a></p>
<?php
}
die();
?>

