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

import java.util.Dictionary;
import java.util.Hashtable;

public class Params {
	private Hashtable<String, String> params;

	public Params() {
		this.params = new Hashtable<String, String>();
	}

	public void addParam(String key, String value) {
		this.params.put(key, value);
	}

	public Dictionary<String, String> getParams() {
		return params;
	}

	@Override
	public String toString() {
		String txt = "";
		for (String key : params.keySet())
			txt += key + "=" + params.get(key) + "&";

		return (!txt.isEmpty()) ? txt.substring(0, txt.length() - 1) : "";
	}
}
