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

public class Execute extends Thread {
	private String command;

	private boolean verbose;

	public Execute(String command, boolean verbose) {
		this.command = command;
		this.verbose = verbose;

		start();
	}

	public void run() {
		if (verbose)
			System.out.println("Executing command (ID: " + this.getId() + ") - Command = " + command);

		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception excp) {
			; // Do nothing
		}

		if (verbose)
			System.out.println("Command execution finished (ID: " + this.getId() + ")");
	}
}
