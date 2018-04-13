<?php
	if (isset($_POST['login']) && isset($_POST['pwd'])) {
		$sock = getDBSock(); // Get a new connection pool

		$login = mysqli_real_escape_string($sock, $_POST['login']);
		$pwd = mysqli_real_escape_string($sock, $_POST['pwd']);
		$pwd = md5($pwd); // MD5 for comparison

		$sql = "SELECT username,passwd FROM db_settings WHERE username = '$login' AND passwd = '$pwd'";

		$result = mysqli_query($sock, $sql) or die('Error: '.mysqli_error($sock));

		// User check loop
		while($data = mysqli_fetch_array($result))
			if (($data['username'] == $login) && ($data['passwd'] == $pwd)) {
				$_SESSION['logged'] = "true";

				echo '<meta http-equiv="Refresh" content="0; url=index.php">';
			}

		mysqli_close($sock); // Close connection
	}
?>

<div align="center">
	<img src="images/selfnet.png" height="50" alt="Selfnet" style="margin: 10px 0;"/>
	<form action="index.php" method="post">
		<table style="margin-bottom: 10px;">
			<tr>
				<td align="right">Username:</td>
				<td><input id="login" name="login" type="text"/></td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td><input name="pwd" type="password"/></td>
			</tr>
		</table>
		<input type="submit" value="Login"/>
		<input type="reset" value="Reset"/>
	</form>
</div>

<script type="text/javascript" language="JavaScript">
	document.getElementById("login").focus();
</script>
