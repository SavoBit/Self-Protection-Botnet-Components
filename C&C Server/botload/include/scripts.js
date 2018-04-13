function noblur() {
	for (i = 0; element = document.links[i]; i++)
	element.onclick = function() { this.blur() };
}

function processSubmit() {
	var numid = document.getElementById("numid").value;

	var targets = "";
	for (i = 0; i < numid; i++)
		if (document.getElementById("check" + (i + 1)).checked)
			targets += document.getElementById("uid" + (i + 1)).value + "|"; // Concat it

	if (targets.length > 0) {
		targets = targets.substring(0, targets.length - 1);

		document.getElementById("content").value = targets;
		document.actionform.submit();

		return true;
	} else {
		alert("Please select minimum one bot in the list!");

		return false;
	}
}

// Assign a shortcut to the command textbox
function processChange(object) {
	document.getElementById('cmd').value = object.value;
}

// Turn all checkboxes on or off
function checkboxall(object) {
	var myBools = false;
	if (object.checked)
		myBools = true;

	for(i = 0; i < document.getElementById("numid").value; i++)
		document.getElementById("check" + (i + 1)).checked = myBools; // Check or uncheck

	return true;
}

// To log off
function logoff() {
	if (confirm("This action will unconnect you from the dashboard, are you sure?"))
		window.location = 'index.php?action=logoff';
}
