<?php
	$sock = getDBSock();
	$sql = "SELECT * FROM db_settings";
	$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));

	// Getting settings as array
	$data = mysqli_fetch_array($result);

 	$GLOBALS["username"] = $data['username'];

	$GLOBALS["botsperpage"] = $data['botsperpage'];
	$GLOBALS["botsoffline"] = $data['botsoffline'];
	$GLOBALS["showoffline"] = $data['showoffline'];
	$GLOBALS["botsdead"] = $data['botsdead'];
	$GLOBALS["showdead"] = $data['showdead'];

	mysqli_close($sock);
?>
