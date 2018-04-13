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

import java.net.InetAddress;
import java.net.UnknownHostException;

import selfnet.Botnet;
import selfnet.botnet.ZeusBotnet;

public class ZombieBot {
	// Botnet configuration
	private Botnet botnet;

	/**
	 * Set a botnet configured with the params received by parameter.
	 * 
	 * @param botnet botnet configuration.
	 */
	public void setBotnet(Botnet botnet) {
		this.botnet = botnet;
	}

	/**
	 * Get the botnet in which the current bot is (has been) recruited.
	 * 
	 * @return the botnet configuration.
	 */
	public Botnet getBotnet() {
		return botnet;
	}

	/**
	 * Send a request command to the C&C server.
	 */
	public void launchBot() {
		try {
			Thread.sleep(botnet.getFrequency() * 1000);
		} catch (InterruptedException iexcp) {
			Thread.currentThread().interrupt();
		}

		// Request a command to the C&C server
		botnet.requestCommand();
	}

	/**
	 * Usage information to show.
	 */
	private static void usage() {
		System.out.println("Usage: ZombieBot [OPTIONS] <C&C IP address>\n\nOptions available:");
		System.out.println("  -port <C&C port>\tPort where the C&C is listening (80 by default)");
		System.out.println("  -type <botnet type>\tList of types of botnets currently supported: zeus");
		System.out.println("  -uid <bot identifier>\tOptional. UID of the bot for its identification in the C&C server");
		System.out.println("  -freq <frequency>\tTime in seconds for requesting commands to the C&C server");
		System.out.println("  -bot <bot type>\tOptional. Type of bot for executing command-based responses: [real|fake] (real by default)");
		System.out.println("  -laddress <address>\tOptional. Local inet address to bind sockets asking the C&C server");
		System.out.println("  -lport <port>\t\tOptional. Local port to enable the vSwitch's diversion rule");
		System.out.println("  -v [true|false]\tOptional. Enable verbose output (false by default)");

		System.exit(-1);
	}

	/**
	 * Application to configure a given botnet, recruit the bot as real or fake and start its execution.
	 * 
	 * @param args botnet configuration as parameters.
	 */
	public static void main(String[] args) {
		ZombieBot bot = new ZombieBot();

		// Parse arguments for setting up the botnet
		if (args.length == 0 || args.length % 2 == 0)
			usage();

		// Extract the C&C IP address
		InetAddress ccIP = null;

		try {
			ccIP = InetAddress.getByName(args[args.length - 1]);
		} catch (UnknownHostException uhexcp) {
			System.out.println("C&C IP address <" + uhexcp.getMessage() + "> is not correct");

			System.exit(-1);
		}

		// Check that all required params have been defined
		boolean paramsOk = false;

		// List of parameters
		Botnet.BotType botType = Botnet.BotType.real;
		String botUID = null;
		int ccPort = 80;
		int frequency = 1;
		boolean verbose = false;

		InetAddress botIP = null;
		int botPort = 0;

		boolean nextItem = false;
		for (int i = 0; i < args.length - 1; i++) {
			if (nextItem) {
				nextItem = false;
				continue;
			}

			switch (args[i]) {
			case "-port":
				try {
					ccPort = Integer.parseInt(args[i + 1]);
					nextItem = true;
				} catch (NumberFormatException nfexcp) {
					System.out.println("C&C port <" + args[i + 1] + "> is not correct because an integer is expected");

					System.exit(-1);
				}

				break;

			case "-type":
				try {
					if (Botnet.Type.valueOf(args[i + 1]) == Botnet.Type.zeus)
						bot.setBotnet(new ZeusBotnet(ccIP));
					else {
						usage();

						System.exit(-1);
					}
				} catch (IllegalArgumentException iaexcp) {
					System.out.println("Botnet type <" + args[i + 1] + "> is not supported");

					System.exit(-1);
				}

				paramsOk = true;
				nextItem = true;

				break;

			case "-freq":
				try {
					frequency = Integer.parseInt(args[i + 1]);
					nextItem = true;

					if (frequency <= 0) {
						System.out.println("Frequency <" + args[i + 1] + "> has to be an integer greater than zero");

						System.exit(-1);
					}
				} catch (NumberFormatException nfexcp) {
					System.out.println("Frequency <" + args[i + 1] + "> is not correct because an integer is expected");

					System.exit(-1);
				}

				break;

			case "-uid":
					botUID = args[i + 1];
					nextItem = true;

				break;

			case "-bot":
				try {
					Botnet.BotType type = Botnet.BotType.valueOf(args[i + 1]);
					if (type != Botnet.BotType.real && type != Botnet.BotType.fake)
						throw new IllegalArgumentException();
				} catch (IllegalArgumentException iaexcp) {
					System.out.println("Type of bot <" + args[i + 1] + "> must be 'real' or 'fake'");

					System.exit(-1);
				}

				botType = Botnet.BotType.valueOf(args[i + 1]);
				nextItem = true;

				break;

			case "-laddress":
				try {
					botIP = InetAddress.getByName(args[i + 1]);
					nextItem = true;
				} catch (UnknownHostException uhexcp) {
					System.out.println("Bot local IP address <" + uhexcp.getMessage() + "> is not correct");

					System.exit(-1);
				}

				break;

			case "-lport":
				try {
					botPort = Integer.parseInt(args[i + 1]);
					nextItem = true;

					if (botPort <= 49151) {
						System.out.println("Bot local port <" + args[i + 1] + "> has to be an integer greater than 49151 for being a private port");

						System.exit(-1);
					}
				} catch (NumberFormatException nfexcp) {
					System.out.println("Bot local port <" + args[i + 1] + "> is not correct because an integer is expected");

					System.exit(-1);
				}

				break;
				
			case "-v":
				verbose = Boolean.parseBoolean(args[i + 1]);
				nextItem = true;

				break;

			default:
				usage();

				break;
			}
		}

		if (!paramsOk) {
			usage();

			System.exit(-1);
		}

		// Set the required parameters and register the zombie as a new bot
		Botnet botnet = bot.getBotnet();
		botnet.setBotnetPort(ccPort);
		botnet.setBotType(botType);
		botnet.setFrequency(frequency);
		botnet.setVerbose(verbose);

		botnet.setBotIP(botIP);
		botnet.setBotPort(botPort);

		if (botnet instanceof ZeusBotnet && botUID != null)
			((ZeusBotnet) botnet).updateUID(botUID);

		// Just register the zombie when it is real
		if (botType == Botnet.BotType.real)
			botnet.registerZombie();

		// Launch the bot application when all required params have been typed
		while (true)
			bot.launchBot();
	}
}
