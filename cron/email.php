<?php
if( php_sapi_name() != 'cli' )
    die("You don't have permissions to run this file");
	
require_once('database.php');

$from = "To Do Application <to-do@domain.com>";
$default_headers = "From: $from\r\n";
$default_headers .= "Reply-To: $from\r\n";
$default_headers .= "Return-Path: $from\r\n";
$default_headers .= "MIME-Version: 1.0\r\n";
$default_headers .= "Content-type: text/html; charset=iso-8859-1\r\n";

$query = $connection->prepare("SELECT `task_id`, `user_firstname`, `user_surname`, `user_email`, `task_name`, `task_priority`, `task_color`, `task_description`, `task_attendees`, `task_date` FROM `tasks` AS t INNER JOIN `users` AS u ON u.user_id = t.user_id WHERE `task_date` >= ADDDATE(NOW(), INTERVAL 15 MINUTE) AND `task_date` <= ADDDATE(NOW(), INTERVAL 20 MINUTE)");
$query->execute();

$query->bind_result($id, $firstname, $surname, $email, $name, $priority, $color, $description, $attendees, $date);

while ($query->fetch()) {
	$to = "$firstname $surname <$email>";
	$headers = $default_headers;
	if(!empty($attendees)){
		$headers .= $headers . "\nBcc: $attendees";
	}
	
	$subject = "Upcoming priority $priority task - $name";
	$message = "Don't forget $name is at $date!\r\n";
	$message = "<html>\r\n" .
		"<head><title>Upcoming priority $priority task - $name</title></head>\r\n" .
		"<body>\r\n" . 
		"<p>Upcoming priority $priority task - $name</p>\r\n" .
		"<p>Don't forget $name is at $date!<br/>\r\n" .
		"$description\r\n" .
		"</p>\r\n" .
		"</body></html>\r\n";	
	
	mail($to, $subject, $message, $headers);
}

$query->close();

?>