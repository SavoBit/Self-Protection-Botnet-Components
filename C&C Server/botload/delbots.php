<?php
	include('include/connection.php');

	$sock = getDBSock();

	// Remove all bots recruited in the C&C server
	$sql = "DELETE FROM db_bots";
	mysqli_query($sock, $sql) or die(mysqli_error($sock));

	mysqli_close($sock);
?>
