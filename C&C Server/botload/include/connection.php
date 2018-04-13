<?php
	// Global variables
	$GLOBALS["dbhost"] = "localhost";
	$GLOBALS["dbname"] = "botload";
	$GLOBALS["dbuser"] = "root";
	$GLOBALS["dbpass"] = "test";

	// Start a new connection to database
	function getDBSock() {
		$sock = mysqli_connect($GLOBALS["dbhost"], $GLOBALS["dbuser"], $GLOBALS["dbpass"]);
		if (!$sock)
			die('Error: '.mysqli_error($sock));

		mysqli_select_db($sock, $GLOBALS["dbname"]) or die('Error: '.mysqli_error($sock));

		return $sock;
	}
?>
