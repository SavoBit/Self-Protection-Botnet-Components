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

package selfnet;

import java.net.InetAddress;

public abstract class Botnet {
	public static enum Type {
		zeus
	}

	public static enum BotType {
		real, fake
	}

	protected InetAddress ccIP;
	protected int ccPort;

	protected int frequency;

	// Type of bot (real or fake)
	protected Botnet.BotType botType;

	// Local IP address of the fake bot
	protected InetAddress botIP;
	// Local port of the fake bot
	protected int botPort;

	protected boolean verbose;

	public Botnet(InetAddress ccIP) {
		this.ccIP = ccIP;
	}

	public InetAddress getBotnetIP() {
		return ccIP;
	}

	public void setBotnetPort(int ccPort) {
		this.ccPort = ccPort;
	}

	public int getBotnetPort() {
		return ccPort;
	}

	public void setBotType(Botnet.BotType botType) {
		this.botType = botType;
	}

	public Botnet.BotType getBotType() {
		return botType;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setBotIP(InetAddress botIP) {
		this.botIP = botIP;
	}

	public InetAddress getBotIP() {
		return botIP;
	}

	public void setBotPort(int botPort) {
		this.botPort = botPort;
	}

	public int getBotPort() {
		return botPort;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public boolean getVerbose() {
		return verbose;
	}

	public abstract void registerZombie();

	public abstract void requestCommand();
}
